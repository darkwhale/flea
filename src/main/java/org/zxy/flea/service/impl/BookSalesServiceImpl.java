package org.zxy.flea.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.BookSalesVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.BookSales;
import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.enums.SalesStatusEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.BookSalesCreateForm;
import org.zxy.flea.form.BookSalesUpdateForm;
import org.zxy.flea.mapper.BookSalesRepository;
import org.zxy.flea.service.BookSalesService;
import org.zxy.flea.util.ImageUtil;
import org.zxy.flea.util.KeyUtil;
import org.zxy.flea.util.SalesRedisUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookSalesServiceImpl implements BookSalesService {

    @Resource
    private BookSalesRepository bookSalesRepository;

    @Resource
    private SalesRedisUtil salesRedisUtil;

    @Resource
    private KeyUtil keyUtil;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private CampusServiceImpl campusService;

    @Override
    public List<BookSales> getListByUserId(String userId) {
        return bookSalesRepository.findAllByUserId(userId);
    }

    @Override
    public BookSales create(BookSalesCreateForm bookSalesCreateForm, String userId) {

        BookSales bookSales = new BookSales();
        BeanUtils.copyProperties(bookSalesCreateForm, bookSales);

        // 设置id
        String key = keyUtil.genUniqueKey();
        bookSales.setSalesId(key);
        bookSales.setUserId(userId);

        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", key, bookSalesCreateForm.getImage_info());
        if (imagePath != null) {
            bookSales.setIcon(imagePath);
        }

        bookSalesRepository.save(bookSales);

        // 添加入redis;
        salesRedisUtil.add(key);

        return bookSales;
    }

    @Override
    public BookSales update(BookSalesUpdateForm bookSalesUpdateForm, String userId) {
        BookSales bookSales = bookSalesRepository.findBySalesId(bookSalesUpdateForm.getSalesId());

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (bookSales == null || !bookSales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        BeanUtils.copyProperties(bookSalesUpdateForm, bookSales);
        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", bookSales.getSalesId(), bookSalesUpdateForm.getImage_info());
        if (imagePath != null) {
            bookSales.setIcon(imagePath);
        }

        bookSalesRepository.save(bookSales);

        return bookSales;
    }

    @Override
    public BookSales delete(String salesId, String userId) {
        BookSales bookSales = bookSalesRepository.findBySalesId(salesId);

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (bookSales == null || !bookSales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        bookSalesRepository.delete(bookSales);

        // 从redis删除
        salesRedisUtil.delete(salesId);

        // 删除图像；
        if(bookSales.getIcon() != null) {
            String imagePath = FleaConst.IMAGE_DIR + bookSales.getIcon();
            amqpTemplate.convertAndSend(FleaConst.AMQP_QUEUE, imagePath);
        }

        return bookSales;
    }

    @Override
    public void deleteAll(String userId) {

        List<BookSales> bookSalesList = getListByUserId(userId);

        // 删除数据库数据;
        bookSalesRepository.deleteAll(bookSalesList);

        // todo 删除图像;
        List<String> imagePathList = bookSalesList.stream()
                .map(e -> FleaConst.IMAGE_DIR + e.getIcon()).collect(Collectors.toList())
                .stream()
                .filter(Objects::nonNull).collect(Collectors.toList());

        amqpTemplate.convertAndSend(FleaConst.AMPQ_QUEUE_BATCH, imagePathList);

        //删除redis;
        List<String> salesIdList = bookSalesList.stream().map(BookSales::getSalesId).collect(Collectors.toList());
        salesRedisUtil.delete(salesIdList);
    }

    @Override
    public BookSales getBookSalesInfo(String bookSalesId) {
        return bookSalesRepository.findBySalesId(bookSalesId);
    }

    @Override
    public BookSales onSale(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.ON_SALE.getCode());
    }

    @Override
    public BookSales schedule(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.SCHEDULE.getCode());    }

    @Override
    public void rub(String userId) {
        List<BookSales> bookSalesList = getListByUserId(userId);

        for (BookSales bookSales : bookSalesList) {
            bookSales.setRubTime(bookSales.getRubTime() + 1);
        }
        bookSalesRepository.saveAll(bookSalesList);
    }

    @Override
    public BookSales offSale(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.OFF_SALE.getCode());    }

    private BookSales changeStatus(String salesId, String userId, Integer status) {
        BookSales bookSales = bookSalesRepository.findBySalesId(salesId);
        // 查询为null，或用户不一致，则抛查询失败异常；
        if (bookSales == null || !bookSales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        bookSales.setStatus(status);
        bookSalesRepository.save(bookSales);

        // 操作redis；
        if (status.equals(SalesStatusEnum.ON_SALE.getCode())) {
            salesRedisUtil.add(salesId);
        }else {
            salesRedisUtil.delete(salesId);
        }

        return bookSales;
    }

    @Override
    public List<BookSales> getList(int size) {
        if (size < 0 || size > 40) {
            size = 20;
        }
        List<String> salesIdList = salesRedisUtil.getRandSalesIdList(size);
        List<BookSales> result = bookSalesRepository.findAllBySalesIdIn(salesIdList);

        Random random = new Random(System.currentTimeMillis());
        Collections.shuffle(result, random);
        return result;
    }

    @Override
    public BookSalesVO converter(BookSales bookSales) {
        BookSalesVO bookSalesVO = new BookSalesVO();
        BeanUtils.copyProperties(bookSales, bookSalesVO);

        Map<Integer, Campus> campusMap = campusService.getCampusMap();

        if (bookSales.getSalesCampusId() != null) {
            bookSalesVO.setSalesCampus(campusMap.get(bookSales.getSalesCampusId()).getCampusName());
        }

        return bookSalesVO;
    }

    @Override
    public List<BookSalesVO> converter(List<BookSales> bookSalesList) {
        return bookSalesList.stream().map(this::converter).collect(Collectors.toList());
    }
}

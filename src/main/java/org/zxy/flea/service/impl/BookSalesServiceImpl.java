package org.zxy.flea.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.BookSalesVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.enums.SalesStatusEnum;
import org.zxy.flea.enums.SalesTypeEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.BookSalesCreateForm;
import org.zxy.flea.form.BookSalesUpdateForm;
import org.zxy.flea.mapper.SalesRepository;
import org.zxy.flea.service.BookSalesService;
import org.zxy.flea.util.ImageUtil;
import org.zxy.flea.util.KeyUtil;
import org.zxy.flea.util.SalesRedisUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookSalesServiceImpl implements BookSalesService {

    @Resource
    private SalesRepository salesRepository;

    @Resource
    private SalesRedisUtil salesRedisUtil;

    @Resource
    private KeyUtil keyUtil;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private CampusServiceImpl campusService;

    @Override
    public List<Sales> getListByUserId(String userId) {
        return salesRepository.findAllBySalesTypeAndUserId(SalesTypeEnum.BOOK.getCode(), userId);
    }

    @Override
    public List<Sales> getOtherListByUserId(String userId) {
        return salesRepository.findAllByStatusAndSalesTypeAndUserId(
                SalesStatusEnum.ON_SALE.getCode(),
                SalesTypeEnum.BOOK.getCode(),
                userId);
    }

    @Override
    public Sales create(BookSalesCreateForm bookSalesCreateForm, String userId) {

        Sales sales = new Sales();
        BeanUtils.copyProperties(bookSalesCreateForm, sales);

        // 设置id
        String key = keyUtil.genUniqueKey();
        sales.setSalesId(key);
        sales.setUserId(userId);
        sales.setSalesType(SalesTypeEnum.BOOK.getCode());

        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", key, bookSalesCreateForm.getImage_info());
        if (imagePath != null) {
            sales.setIcon(imagePath);
        }

        salesRepository.save(sales);

        // 添加入redis;
        salesRedisUtil.add(key);

        return sales;
    }

    @Override
    public Sales update(BookSalesUpdateForm bookSalesUpdateForm, String userId) {
        Sales sales = salesRepository.findBySalesTypeAndSalesId(SalesTypeEnum.BOOK.getCode(), bookSalesUpdateForm.getSalesId());

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (sales == null || !sales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        BeanUtils.copyProperties(bookSalesUpdateForm, sales);
        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", sales.getSalesId(), bookSalesUpdateForm.getImage_info());
        if (imagePath != null) {
            sales.setIcon(imagePath);
        }

        salesRepository.save(sales);

        return sales;
    }

    @Override
    public Sales delete(String salesId, String userId) {
        Sales sales = salesRepository.findBySalesTypeAndSalesId(SalesTypeEnum.BOOK.getCode(), salesId);

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (sales == null || !sales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        salesRepository.delete(sales);

        // 从redis删除
        salesRedisUtil.delete(salesId);

        // 删除图像；只删除非默认的图像；
        if(sales.getIcon() != null) {
            String imagePath = FleaConst.IMAGE_DIR + sales.getIcon();
            amqpTemplate.convertAndSend(FleaConst.AMQP_QUEUE, imagePath);
        }

        return sales;
    }

    @Override
    public void deleteAll(String userId) {

        List<Sales> bookSalesList = getListByUserId(userId);

        // 删除数据库数据;
        salesRepository.deleteAll(bookSalesList);

        List<String> imagePathList = bookSalesList.stream()
                .map(e -> FleaConst.IMAGE_DIR + e.getIcon()).collect(Collectors.toList())
                .stream()
                .filter(Objects::nonNull).collect(Collectors.toList());

        amqpTemplate.convertAndSend(FleaConst.AMPQ_QUEUE_BATCH, imagePathList);

        //删除redis;
        List<String> salesIdList = bookSalesList.stream().map(Sales::getSalesId).collect(Collectors.toList());
        salesRedisUtil.delete(salesIdList);
    }

    @Override
    public Sales getSalesInfo(String bookSalesId) {
        return salesRepository.findBySalesTypeAndSalesId(SalesTypeEnum.BOOK.getCode(), bookSalesId);
    }

    @Override
    public Sales onSale(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.ON_SALE.getCode());
    }

    @Override
    public Sales schedule(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.SCHEDULE.getCode());    }

    @Override
    public void rub(String userId) {
        List<Sales> salesList = getListByUserId(userId);

        for (Sales sales : salesList) {
            sales.setRubTime(sales.getRubTime() + 1);
        }
        salesRepository.saveAll(salesList);
    }

    @Override
    public Sales offSale(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.OFF_SALE.getCode());    }

    @Override
    public Page<BookSalesVO> converter(Page<Sales> salesPage, Pageable pageable) {

        List<BookSalesVO> bookSalesVOList = salesPage.getContent().stream()
                .map(this::converter).collect(Collectors.toList());

        return new PageImpl<>(bookSalesVOList, pageable, salesPage.getTotalElements());
    }

    @Override
    public Page<Sales> getListByCampusId(Integer salesCampusId, Pageable pageable) {

        if (salesCampusId == null) {
            return salesRepository.findAllBySalesTypeAndStatusOrderByUpdateTimeDesc(SalesTypeEnum.BOOK.getCode(), SalesStatusEnum.ON_SALE.getCode(), pageable);
        }else {
            return salesRepository.findAllBySalesTypeAndStatusAndSalesCampusIdOrderByUpdateTimeDesc(SalesTypeEnum.BOOK.getCode(), SalesStatusEnum.ON_SALE.getCode(), salesCampusId, pageable);
        }

    }

    private Sales changeStatus(String salesId, String userId, Integer status) {
        Sales sales= salesRepository.findBySalesTypeAndSalesId(SalesTypeEnum.BOOK.getCode(), salesId);
        // 查询为null，或用户不一致，则抛查询失败异常；
        if (sales == null || !sales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        sales.setStatus(status);
        salesRepository.save(sales);

        // 操作redis；
        if (status.equals(SalesStatusEnum.ON_SALE.getCode())) {
            salesRedisUtil.add(salesId);
        }else {
            salesRedisUtil.delete(salesId);
        }

        return sales;
    }

    @Override
    public BookSalesVO converter(Sales sales) {
        BookSalesVO bookSalesVO = new BookSalesVO();
        BeanUtils.copyProperties(sales, bookSalesVO);

        Map<Integer, Campus> campusMap = campusService.getCampusMap();

        if (sales.getSalesCampusId() != null) {
            bookSalesVO.setSalesCampus(campusMap.get(sales.getSalesCampusId()).getCampusName());
        }

        return bookSalesVO;
    }

    @Override
    public List<BookSalesVO> converter(List<Sales> salesList) {
        return salesList.stream().map(this::converter).collect(Collectors.toList());
    }
}

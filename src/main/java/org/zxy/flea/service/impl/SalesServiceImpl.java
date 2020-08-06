package org.zxy.flea.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.SalesVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.enums.SalesStatusEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.SalesCreateForm;
import org.zxy.flea.form.SalesUpdateForm;
import org.zxy.flea.mapper.SalesRepository;
import org.zxy.flea.service.SalesService;
import org.zxy.flea.util.ImageUtil;
import org.zxy.flea.util.KeyUtil;
import org.zxy.flea.util.SalesRedisUtil;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {

    @Resource
    private SalesRedisUtil salesRedisUtil;

    @Resource
    private SalesRepository salesRepository;

    @Resource
    private KeyUtil keyUtil;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public List<Sales> getListByUserId(String userId) {
        return salesRepository.findAllByUserId(userId);
    }

    @Override
    public Sales create(SalesCreateForm salesCreateForm, String userId) {

        Sales sales = new Sales();
        BeanUtils.copyProperties(salesCreateForm, sales);

        // 设置id
        String key = keyUtil.genUniqueKey();
        sales.setSalesId(key);
        sales.setUserId(userId);

        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", key, salesCreateForm.getImage_info());
        if (imagePath != null) {
            sales.setIcon(imagePath);
        }

        salesRepository.save(sales);

        // 添加入redis;
        salesRedisUtil.add(key);

        return sales;
    }

    @Override
    public Sales update(SalesUpdateForm salesUpdateForm, String userId) {
        Sales sales = salesRepository.findBySalesId(salesUpdateForm.getSalesId());

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (sales == null || !sales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        BeanUtils.copyProperties(salesUpdateForm, sales);
        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", sales.getSalesId(), salesUpdateForm.getImage_info());
        if (imagePath != null) {
            sales.setIcon(imagePath);
        }

        salesRepository.save(sales);

        return sales;
    }

    @Override
    public Sales getSalesInfo(String salesId) {
        return salesRepository.findBySalesId(salesId);
    }

    @Override
    public Sales delete(String salesId, String userId) {
        Sales sales = salesRepository.findBySalesId(salesId);

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (sales == null || !sales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        salesRepository.delete(sales);

        // 从redis删除
        salesRedisUtil.delete(salesId);

        String imagePath = FleaConst.IMAGE_DIR + sales.getIcon();
        amqpTemplate.convertAndSend(FleaConst.AMQP_QUEUE, imagePath);

        return sales;
    }

    @Override
    public Sales onSale(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.ON_SALE.getCode());
    }

    @Override
    public Sales schedule(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.SCHEDULE.getCode());
    }

    @Override
    public Sales offSale(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.OFF_SALE.getCode());
    }

    private Sales changeStatus(String salesId, String userId, Integer status) {
        Sales sales = salesRepository.findBySalesId(salesId);

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
    public SalesVO converter(Sales sales) {
        SalesVO salesVO = new SalesVO();
        BeanUtils.copyProperties(sales, salesVO);

        return salesVO;
    }

    @Override
    public void deleteAll(String userId) {
        List<Sales> salesList = salesRepository.findAllByUserId(userId);

        // 删除数据库数据;
        salesRepository.deleteAll(salesList);

        // todo 删除图像;
        List<String> imagePathList = salesList.stream().map(Sales::getIcon).collect(Collectors.toList());
        amqpTemplate.convertAndSend(FleaConst.AMQP_QUEUE, imagePathList);

        //删除redis;
        List<String> salesIdList = salesList.stream().map(Sales::getSalesId).collect(Collectors.toList());
        salesRedisUtil.delete(salesIdList);
    }

    @Override
    public List<SalesVO> converter(List<Sales> salesList) {
        return salesList.stream().map(e -> {
            SalesVO salesVO = new SalesVO();
            BeanUtils.copyProperties(e, salesVO);
            return salesVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Sales> getList(int size) {

        if (size < 0 || size > 40) {
            size = 20;
        }

        List<String> salesIdList = salesRedisUtil.getRandSalesIdList(size);
        List<Sales> result = salesRepository.findAllBySalesIdIn(salesIdList);

        Random random = new Random(System.currentTimeMillis());
        Collections.shuffle(result, random);
        return result;
    }
}

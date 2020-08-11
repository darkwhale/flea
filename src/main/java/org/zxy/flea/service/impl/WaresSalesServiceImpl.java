package org.zxy.flea.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.WaresSalesVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.enums.SalesStatusEnum;
import org.zxy.flea.enums.SalesTypeEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.WaresSalesCreateForm;
import org.zxy.flea.form.WaresSalesUpdateForm;
import org.zxy.flea.mapper.SalesRepository;
import org.zxy.flea.service.WaresSalesService;
import org.zxy.flea.util.ImageUtil;
import org.zxy.flea.util.KeyUtil;
import org.zxy.flea.util.SalesRedisUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WaresSalesServiceImpl implements WaresSalesService {

    @Resource
    private SalesRepository salesRepository;

    @Resource
    private KeyUtil keyUtil;

    @Resource
    private SalesRedisUtil salesRedisUtil;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private AddressServiceImpl addressService;

    @Override
    public List<Sales> getOtherListByUserId(String userId) {
        return salesRepository.findAllByStatusAndSalesTypeAndUserId(
                SalesStatusEnum.ON_SALE.getCode(),
                SalesTypeEnum.WARES.getCode(),
                userId
        );
    }

    @Override
    public List<Sales> getListByUserId(String userId) {
        return salesRepository.findAllBySalesTypeAndUserId(SalesTypeEnum.WARES.getCode(), userId);
    }

    @Override
    public Sales create(WaresSalesCreateForm waresSalesCreateForm, String userId) {

        Sales sales = new Sales();
        BeanUtils.copyProperties(waresSalesCreateForm, sales);

        // 设置id
        String key = keyUtil.genUniqueKey();
        sales.setSalesId(key);
        sales.setUserId(userId);
        sales.setSalesType(SalesTypeEnum.WARES.getCode());

        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", key, waresSalesCreateForm.getImage_info());
        if (imagePath != null) {
            sales.setIcon(imagePath);
        }

        salesRepository.save(sales);

        // 添加入redis;
        salesRedisUtil.add(key);

        return sales;
    }

    @Override
    public Sales update(WaresSalesUpdateForm waresSalesUpdateForm, String userId) {
        Sales sales = salesRepository.findBySalesTypeAndSalesId(SalesTypeEnum.WARES.getCode(), waresSalesUpdateForm.getSalesId());

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (sales == null || !sales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        BeanUtils.copyProperties(waresSalesUpdateForm, sales);
        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", sales.getSalesId(), waresSalesUpdateForm.getImage_info());
        if (imagePath != null) {
            sales.setIcon(imagePath);
        }

        salesRepository.save(sales);

        return sales;
    }

    @Override
    public Sales delete(String salesId, String userId) {
        Sales sales = salesRepository.findBySalesTypeAndSalesId(SalesTypeEnum.WARES.getCode(), salesId);

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (sales == null || !sales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        salesRepository.delete(sales);

        // 从redis删除
        salesRedisUtil.delete(salesId);

        // 删除图像；
        if(sales.getIcon() != null) {
            String imagePath = FleaConst.IMAGE_DIR + sales.getIcon();
            amqpTemplate.convertAndSend(FleaConst.AMQP_QUEUE, imagePath);
        }

        return sales;
    }

    @Override
    public void deleteAll(String userId) {
        List<Sales> salesList = getListByUserId(userId);

        // 删除数据库数据;
        salesRepository.deleteAll(salesList);

        // todo 删除图像;
        List<String> imagePathList = salesList.stream()
                .map(e -> FleaConst.IMAGE_DIR + e.getIcon()).collect(Collectors.toList())
                .stream()
                .filter(Objects::nonNull).collect(Collectors.toList());

        amqpTemplate.convertAndSend(FleaConst.AMPQ_QUEUE_BATCH, imagePathList);

        //删除redis;
        List<String> salesIdList = salesList.stream().map(Sales::getSalesId).collect(Collectors.toList());
        salesRedisUtil.delete(salesIdList);
    }

    @Override
    public void rub(String userId) {
        List<Sales> salesList = getListByUserId(userId);

        for (Sales sales : salesList) {
            sales.setRubTime(sales.getRubTime() + 1);
        }
        salesRepository.saveAll(salesList);
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
        Sales sales = salesRepository.findBySalesTypeAndSalesId(SalesTypeEnum.WARES.getCode(), salesId);
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
    public WaresSalesVO converter(Sales sales) {
        WaresSalesVO waresSalesVO = new WaresSalesVO();
        BeanUtils.copyProperties(sales, waresSalesVO);

        Map<Integer, Address> addressMap = addressService.getAddressList();

        if (sales.getSalesAddressId() != null) {
            waresSalesVO.setSalesAddress(addressMap.get(sales.getSalesAddressId()).toString());
        }

        return waresSalesVO;
    }

    @Override
    public List<WaresSalesVO> converter(List<Sales> waresSalesList) {
        return waresSalesList.stream().map(this::converter).collect(Collectors.toList());
    }

    @Override
    public Page<Sales> getListByAddressId(Integer salesAddressId, Pageable pageable) {

        if (salesAddressId == null) {
            return salesRepository.findAllBySalesTypeAndStatusOrderByUpdateTimeDesc(SalesTypeEnum.WARES.getCode(), SalesStatusEnum.ON_SALE.getCode(), pageable);
        }else {
            return salesRepository.findAllBySalesTypeAndStatusAndSalesCampusIdOrderByUpdateTimeDesc(SalesTypeEnum.WARES.getCode(), SalesStatusEnum.ON_SALE.getCode(), salesAddressId, pageable);
        }

    }

    @Override
    public Page<WaresSalesVO> converter(Page<Sales> waresSalesPage, Pageable pageable) {
        List<WaresSalesVO> waresSalesVOList = waresSalesPage.getContent().stream()
                .map(this::converter).collect(Collectors.toList());

        return new PageImpl<>(waresSalesVOList, pageable, waresSalesPage.getTotalElements());
    }
}

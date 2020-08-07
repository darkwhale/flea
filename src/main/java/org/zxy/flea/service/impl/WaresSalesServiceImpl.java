package org.zxy.flea.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.WaresSalesVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.dataobject.WaresSales;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.enums.SalesStatusEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.WaresSalesCreateForm;
import org.zxy.flea.form.WaresSalesUpdateForm;
import org.zxy.flea.mapper.WaresSalesRepository;
import org.zxy.flea.service.WaresSalesService;
import org.zxy.flea.util.ImageUtil;
import org.zxy.flea.util.KeyUtil;
import org.zxy.flea.util.SalesRedisUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WaresSalesServiceImpl implements WaresSalesService {

    @Resource
    private WaresSalesRepository waresSalesRepository;

    @Resource
    private KeyUtil keyUtil;

    @Resource
    private SalesRedisUtil salesRedisUtil;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private AddressServiceImpl addressService;

    @Override
    public List<WaresSales> getListByUserId(String userId) {
        return waresSalesRepository.findAllByUserId(userId);
    }

    @Override
    public WaresSales create(WaresSalesCreateForm waresSalesCreateForm, String userId) {

        WaresSales waresSales = new WaresSales();
        BeanUtils.copyProperties(waresSalesCreateForm, waresSales);

        // 设置id
        String key = keyUtil.genUniqueKey();
        waresSales.setSalesId(key);
        waresSales.setUserId(userId);

        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", key, waresSalesCreateForm.getImage_info());
        if (imagePath != null) {
            waresSales.setIcon(imagePath);
        }

        waresSalesRepository.save(waresSales);

        // 添加入redis;
        salesRedisUtil.add(key);

        return waresSales;
    }

    @Override
    public WaresSales update(WaresSalesUpdateForm waresSalesUpdateForm, String userId) {
        WaresSales waresSales = waresSalesRepository.findBySalesId(waresSalesUpdateForm.getSalesId());

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (waresSales == null || !waresSales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        BeanUtils.copyProperties(waresSalesUpdateForm, waresSales);
        // 设置图像
        String imagePath = ImageUtil.saveImage("sales", waresSales.getSalesId(), waresSalesUpdateForm.getImage_info());
        if (imagePath != null) {
            waresSales.setIcon(imagePath);
        }

        waresSalesRepository.save(waresSales);

        return waresSales;
    }

    @Override
    public WaresSales delete(String salesId, String userId) {
        WaresSales waresSales = waresSalesRepository.findBySalesId(salesId);

        // 查询为null，或用户不一致，则抛查询失败异常；
        if (waresSales == null || !waresSales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        waresSalesRepository.delete(waresSales);

        // 从redis删除
        salesRedisUtil.delete(salesId);

        // 删除图像；
        if(waresSales.getIcon() != null) {
            String imagePath = FleaConst.IMAGE_DIR + waresSales.getIcon();
            amqpTemplate.convertAndSend(FleaConst.AMQP_QUEUE, imagePath);
        }

        return waresSales;
    }

    @Override
    public void deleteAll(String userId) {
        List<WaresSales> waresSalesList = getListByUserId(userId);

        // 删除数据库数据;
        waresSalesRepository.deleteAll(waresSalesList);

        // todo 删除图像;
        List<String> imagePathList = waresSalesList.stream()
                .map(e -> FleaConst.IMAGE_DIR + e.getIcon()).collect(Collectors.toList())
                .stream()
                .filter(Objects::nonNull).collect(Collectors.toList());

        amqpTemplate.convertAndSend(FleaConst.AMPQ_QUEUE_BATCH, imagePathList);

        //删除redis;
        List<String> salesIdList = waresSalesList.stream().map(WaresSales::getSalesId).collect(Collectors.toList());
        salesRedisUtil.delete(salesIdList);
    }

    @Override
    public void rub(String userId) {
        List<WaresSales> waresSalesList = getListByUserId(userId);

        for (WaresSales waresSales : waresSalesList) {
            waresSales.setRubTime(waresSales.getRubTime() + 1);
        }
        waresSalesRepository.saveAll(waresSalesList);
    }

    @Override
    public WaresSales getBookSalesInfo(String waresSalesId) {
        return waresSalesRepository.findBySalesId(waresSalesId);
    }

    @Override
    public WaresSales onSale(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.ON_SALE.getCode());
    }

    @Override
    public WaresSales schedule(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.SCHEDULE.getCode());
    }

    @Override
    public WaresSales offSale(String salesId, String userId) {
        return changeStatus(salesId, userId, SalesStatusEnum.OFF_SALE.getCode());
    }

    private WaresSales changeStatus(String salesId, String userId, Integer status) {
        WaresSales waresSales = waresSalesRepository.findBySalesId(salesId);
        // 查询为null，或用户不一致，则抛查询失败异常；
        if (waresSales == null || !waresSales.getUserId().equals(userId)) {
            throw new FleaException(ResponseEnum.SALES_NOT_EXIST);
        }

        waresSales.setStatus(status);
        waresSalesRepository.save(waresSales);

        // 操作redis；
        if (status.equals(SalesStatusEnum.ON_SALE.getCode())) {
            salesRedisUtil.add(salesId);
        }else {
            salesRedisUtil.delete(salesId);
        }

        return waresSales;
    }

    @Override
    public List<WaresSales> getList(int size) {
        if (size < 0 || size > 40) {
            size = 20;
        }
        List<String> salesIdList = salesRedisUtil.getRandSalesIdList(size);
        List<WaresSales> result = waresSalesRepository.findAllBySalesIdIn(salesIdList);

        Random random = new Random(System.currentTimeMillis());
        Collections.shuffle(result, random);
        return result;    }

    @Override
    public WaresSalesVO converter(WaresSales waresSales) {
        WaresSalesVO waresSalesVO = new WaresSalesVO();
        BeanUtils.copyProperties(waresSales, waresSalesVO);

        Map<Integer, Address> addressMap = addressService.getAddressList();

        if (waresSales.getSalesAddressId() != null) {
            waresSalesVO.setSalesAddress(addressMap.get(waresSales.getSalesAddressId()).toString());
        }

        return waresSalesVO;
    }

    @Override
    public List<WaresSalesVO> converter(List<WaresSales> waresSalesList) {
        return waresSalesList.stream().map(this::converter).collect(Collectors.toList());
    }
}

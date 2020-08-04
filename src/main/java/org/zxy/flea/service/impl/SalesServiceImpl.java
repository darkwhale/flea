package org.zxy.flea.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.SalesVO;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.SalesCreateForm;
import org.zxy.flea.form.SalesUpdateForm;
import org.zxy.flea.mapper.SalesRepository;
import org.zxy.flea.service.SalesService;
import org.zxy.flea.util.ImageUtil;
import org.zxy.flea.util.KeyUtil;
import org.zxy.flea.util.SalesRedisUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {

    @Resource
    private SalesRedisUtil salesRedisUtil;

    @Resource
    private SalesRepository salesRepository;

    @Resource
    private KeyUtil keyUtil;

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

        return sales;
    }

    @Override
    public SalesVO converter(Sales sales) {
        SalesVO salesVO = new SalesVO();
        BeanUtils.copyProperties(sales, salesVO);

        return salesVO;
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
        return salesRepository.findAllBySalesIdIn(salesIdList);
    }
}

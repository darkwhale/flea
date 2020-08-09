package org.zxy.flea.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.AmoyVO;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.mapper.SalesRepository;
import org.zxy.flea.service.AmoyService;
import org.zxy.flea.util.SalesRedisUtil;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AmoyServiceImpl implements AmoyService {

    @Resource
    private SalesRepository salesRepository;

    @Resource
    private SalesRedisUtil salesRedisUtil;

    @Resource
    private AddressServiceImpl addressService;

    @Resource
    private CampusServiceImpl campusService;

    @Override
    public AmoyVO converter(Sales sales) {
        AmoyVO amoyVO = new AmoyVO();
        BeanUtils.copyProperties(sales, amoyVO);

        Map<Integer, Campus> campusMap = campusService.getCampusMap();
        Map<Integer, Address> addressList = addressService.getAddressList();


        if (sales.getSalesCampusId() != null) {
            amoyVO.setSalesCampus(campusMap.get(sales.getSalesCampusId()).getCampusName());
        }
        if (sales.getSalesAddressId() != null) {
            amoyVO.setSalesAddress(addressList.get(sales.getSalesAddressId()).toString());
        }

        return amoyVO;
    }

    @Override
    public Page<Sales> findByName(String salesName, String synopsis) {
        return null;
    }

    @Override
    public Page<AmoyVO> converter(Page<Sales> salesPage, Pageable pageable) {

        List<AmoyVO> amoyVOList = salesPage.getContent().stream().map(this::converter).collect(Collectors.toList());
        return new PageImpl<>(amoyVOList, pageable, salesPage.getTotalElements());
    }

    @Override
    public List<AmoyVO> getList(int size) {
        if (size < 0 || size > 40) {
            size = 20;
        }
        List<String> salesIdList = salesRedisUtil.getRandSalesIdList(size);

        List<Sales> salesList = salesRepository.findAllBySalesIdIn(salesIdList);

        Random random = new Random(System.currentTimeMillis());
        Collections.shuffle(salesList, random);

        return salesList.stream().map(this::converter).collect(Collectors.toList());

    }
}

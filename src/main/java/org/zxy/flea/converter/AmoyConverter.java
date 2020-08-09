package org.zxy.flea.converter;

import org.springframework.beans.BeanUtils;
import org.zxy.flea.VO.AmoyVO;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.dataobject.BookSales;
import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.dataobject.WaresSales;
import org.zxy.flea.enums.SalesTypeEnum;
import org.zxy.flea.service.impl.AddressServiceImpl;
import org.zxy.flea.service.impl.CampusServiceImpl;

import javax.annotation.Resource;
import java.util.Map;

public class AmoyConverter {

    @Resource
    private AddressServiceImpl addressService;

    @Resource
    private CampusServiceImpl campusService;

    public AmoyVO converter(BookSales bookSales) {
        AmoyVO amoyVO = new AmoyVO();
        BeanUtils.copyProperties(bookSales, amoyVO);
        amoyVO.setSalesType(SalesTypeEnum.BOOK.getCode());

        Map<Integer, Campus> campusMap = campusService.getCampusMap();
        if (bookSales.getSalesCampusId() != null) {
            amoyVO.setSalesCampus(campusMap.get(bookSales.getSalesCampusId()).getCampusName());
        }

        return amoyVO;
    }

    public AmoyVO converter(WaresSales waresSales) {
        AmoyVO amoyVO = new AmoyVO();
        BeanUtils.copyProperties(waresSales, amoyVO);
        amoyVO.setSalesType(SalesTypeEnum.WARES.getCode());

        Map<Integer, Address> addressList = addressService.getAddressList();
        if (waresSales.getSalesAddressId() != null) {
            amoyVO.setSalesAddress(addressList.get(waresSales.getSalesAddressId()).toString());
        }

        return amoyVO;
    }
}

package org.zxy.flea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zxy.flea.VO.WaresSalesVO;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.form.WaresSalesCreateForm;
import org.zxy.flea.form.WaresSalesUpdateForm;

import java.util.List;

public interface WaresSalesService {

    List<Sales> getListByUserId(String userId);

    List<Sales> getOtherListByUserId(String userId);

    Sales create(WaresSalesCreateForm waresSalesCreateForm, String userId);

    Sales update(WaresSalesUpdateForm waresSalesUpdateForm, String userId);

    Sales delete(String salesId, String userId);

    void deleteAll(String userId);

    void rub(String userId);

    Sales onSale(String salesId, String userId);

    Sales schedule(String salesId, String userId);

    Sales offSale(String salesId, String userId);
    
    WaresSalesVO converter(Sales waresSales);

    List<WaresSalesVO> converter(List<Sales> waresSalesList);


    Page<Sales> getListByAddressId(Integer salesAddressId, Pageable pageable);

    Page<WaresSalesVO> converter(Page<Sales> waresSalesPage, Pageable pageable);
}

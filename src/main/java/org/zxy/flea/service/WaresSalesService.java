package org.zxy.flea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zxy.flea.VO.WaresSalesVO;
import org.zxy.flea.dataobject.WaresSales;
import org.zxy.flea.form.WaresSalesCreateForm;
import org.zxy.flea.form.WaresSalesUpdateForm;

import java.util.List;

public interface WaresSalesService {

    List<WaresSales> getListByUserId(String userId);

    WaresSales create(WaresSalesCreateForm waresSalesCreateForm, String userId);

    WaresSales update(WaresSalesUpdateForm waresSalesUpdateForm, String userId);

    WaresSales delete(String salesId, String userId);

    void deleteAll(String userId);

    void rub(String userId);

    WaresSales getBookSalesInfo(String waresSalesId);

    WaresSales onSale(String salesId, String userId);

    WaresSales schedule(String salesId, String userId);

    WaresSales offSale(String salesId, String userId);

    List<WaresSales> getList(int size);

    WaresSalesVO converter(WaresSales waresSales);

    List<WaresSalesVO> converter(List<WaresSales> waresSalesList);


    Page<WaresSales> getListByAddressId(Integer salesAddressId, Pageable pageable);

    Page<WaresSalesVO> converter(Page<WaresSales> waresSalesPage, Pageable pageable);
}

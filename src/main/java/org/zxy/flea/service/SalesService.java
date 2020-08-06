package org.zxy.flea.service;

import org.zxy.flea.VO.SalesVO;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.form.SalesCreateForm;
import org.zxy.flea.form.SalesUpdateForm;

import java.util.List;

public interface SalesService {

    List<Sales> getListByUserId(String userId);

    Sales create(SalesCreateForm salesCreateForm, String userId);

    Sales update(SalesUpdateForm salesUpdateForm, String userId);

    Sales getSalesInfo(String salesId);

    Sales delete(String salesId, String userId);

    void deleteAll(String userId);

    Sales onSale(String salesId, String userId);

    Sales schedule(String salesId, String userId);

    Sales offSale(String salesId, String userId);

    List<Sales> getList(int size);

    SalesVO converter(Sales sales);

    List<SalesVO> converter(List<Sales> salesList);
}

package org.zxy.flea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zxy.flea.VO.BookSalesVO;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.form.BookSalesCreateForm;
import org.zxy.flea.form.BookSalesUpdateForm;

import java.util.List;

public interface BookSalesService {
    List<Sales> getListByUserId(String userId);

    Sales create(BookSalesCreateForm bookSalesCreateForm, String userId);

    Sales update(BookSalesUpdateForm bookSalesUpdateForm, String userId);

    Sales delete(String salesId, String userId);

    void deleteAll(String userId);

    void rub(String userId);

    Sales getSalesInfo(String salesId);

    Sales onSale(String salesId, String userId);

    Sales schedule(String salesId, String userId);

    Sales offSale(String salesId, String userId);

    BookSalesVO converter(Sales sales);

    List<BookSalesVO> converter(List<Sales> salesList);

    Page<Sales> getListByCampusId(Integer salesCampusId, Pageable pageable);

    Page<BookSalesVO> converter(Page<Sales> salesPage, Pageable pageable);

}

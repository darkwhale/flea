package org.zxy.flea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zxy.flea.VO.BookSalesVO;
import org.zxy.flea.dataobject.BookSales;
import org.zxy.flea.form.BookSalesCreateForm;
import org.zxy.flea.form.BookSalesUpdateForm;

import java.util.List;

public interface BookSalesService {
    List<BookSales> getListByUserId(String userId);

    BookSales create(BookSalesCreateForm bookSalesCreateForm, String userId);

    BookSales update(BookSalesUpdateForm bookSalesUpdateForm, String userId);

    BookSales delete(String salesId, String userId);

    void deleteAll(String userId);

    void rub(String userId);

    BookSales getBookSalesInfo(String bookSalesId);

    BookSales onSale(String salesId, String userId);

    BookSales schedule(String salesId, String userId);

    BookSales offSale(String salesId, String userId);

    List<BookSales> getList(int size);

    BookSalesVO converter(BookSales bookSales);

    List<BookSalesVO> converter(List<BookSales> bookSalesList);

    Page<BookSales> getListByCampusId(Integer salesCampusId, Pageable pageable);

    Page<BookSalesVO> converter(Page<BookSales> bookSalesPage, Pageable pageable);

}

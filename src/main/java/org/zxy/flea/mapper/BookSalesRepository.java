package org.zxy.flea.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.BookSales;

import java.util.List;

public interface BookSalesRepository extends JpaRepository<BookSales, String> {

    List<BookSales> findAllByUserId(String userId);

    BookSales findBySalesId(String bookSalesId);

    /**
     * 根据id集合查询商品，需要配合redis；
     * @param salesIdList 商品id列表
     * @return 返回商品列表
     */
    List<BookSales> findAllBySalesIdIn(List<String> salesIdList);

}

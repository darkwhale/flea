package org.zxy.flea.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zxy.flea.dataobject.Sales;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, String> {

    List<Sales> findAllBySalesTypeAndUserId(Integer salesType, String userId);

    List<Sales> findAllByStatusAndSalesTypeAndUserId(Integer status, Integer salesType, String userId);

    Sales findBySalesTypeAndSalesId(Integer salesType, String salesId);

    /**
     * 根据id集合查询商品，需要配合redis；
     * @param salesIdList 商品id列表
     * @return 返回商品列表
     */
    List<Sales> findAllBySalesIdIn(List<String> salesIdList);

    Page<Sales> findAllBySalesTypeAndStatusOrderByUpdateTimeDesc(Integer salesType, Integer status, Pageable pageable);

    Page<Sales> findAllBySalesTypeAndStatusAndSalesCampusIdOrderByUpdateTimeDesc(Integer salesType, Integer status, Integer salesCampusId, Pageable pageable);

    Page<Sales> findAllBySalesTypeAndStatusAndSalesAddressIdOrderByUpdateTimeDesc(Integer salesType, Integer status, Integer salesAddressId, Pageable pageable);

//    Page<Sales> findAllBySalesNameLikeOrSynopsisLikeAndStatusOrderByUpdateTimeDesc(String salesName, String synopsis, Integer status, Pageable pageable);

    @Query(value = "select * from sales where status = 0 and concat(sales_name, ' ', items) regexp :keyword", nativeQuery = true)
    Page<Sales> findAllByNames(String keyword, Pageable pageable);


    @Query(value = "select * from sales where status = 0 and concat(sales_name, ' ', items) regexp :keyword1 and " +
            "concat(sales_name, ' ', items) regexp :keyword2", nativeQuery = true)
    Page<Sales> findAllByNames(String keyword1, String keyword2, Pageable pageable);


    @Query(value = "select * from sales where status = 0 and concat(sales_name, ' ', items) regexp :keyword1 and " +
            "concat(sales_name, ' ', items) regexp :keyword2 and " +
            "concat(sales_name, ' ', items) regexp :keyword3", nativeQuery = true)
    Page<Sales> findAllByNames(String keyword1, String keyword2, String keyword3, Pageable pageable);


    @Query(value = "select * from sales where status = 0 and concat(sales_name, ' ', items) regexp :keyword1 and " +
            "concat(sales_name, ' ', items) regexp :keyword2 and " +
            "concat(sales_name, ' ', items) regexp :keyword3 and " +
            "concat(sales_name, ' ', items) regexp :keyword4", nativeQuery = true)
    Page<Sales> findAllByNames(String keyword1, String keyword2, String keyword3, String keyword4, Pageable pageable);


    @Query(value = "select * from sales where status = 0 and concat(sales_name, ' ', items) regexp :keyword1 and " +
            "concat(sales_name, ' ', items) regexp :keyword2 and " +
            "concat(sales_name, ' ', items) regexp :keyword3 and " +
            "concat(sales_name, ' ', items) regexp :keyword4 and " +
            "concat(sales_name, ' ', items) regexp :keyword5", nativeQuery = true)
    Page<Sales> findAllByNames(String keyword1, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);
}

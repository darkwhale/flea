package org.zxy.flea.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.Sales;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, String> {

    List<Sales> findAllBySalesTypeAndUserId(Integer salesType, String userId);

    Sales findBySalesTypeAndSalesId(Integer salesType, String salesId);

    /**
     * 根据id集合查询商品，需要配合redis；
     * @param salesIdList 商品id列表
     * @return 返回商品列表
     */
    List<Sales> findAllBySalesIdIn(List<String> salesIdList);

    Page<Sales> findAllBySalesTypeAndStatusOrderByUpdateTimeDesc(Integer salesType, Integer status, Pageable pageable);

    Page<Sales> findAllBySalesTypeAndStatusAndSalesCampusIdOrderByUpdateTimeDesc(Integer salesType, Integer status, Integer salesCampusId, Pageable pageable);

    Page<Sales> findAllBySalesNameLikeOrSynopsisLikeAndStatusOrderByUpdateTimeDesc(String salesName, String synopsis, Integer status, Pageable pageable);
}

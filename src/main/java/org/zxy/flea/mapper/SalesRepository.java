package org.zxy.flea.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.Sales;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, String> {

    // 查询商品;
    List<Sales> findAllByUserId(String userId);

    Sales findBySalesId(String salesId);

    /**
     * 根据id集合查询商品，需要配合redis；
     * @param salesIdList 商品id列表
     * @return 返回商品列表
     */
    List<Sales> findAllBySalesIdIn(List<String> salesIdList);

}

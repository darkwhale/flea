package org.zxy.flea.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.WaresSales;

import java.util.List;

public interface WaresSalesRepository extends JpaRepository<WaresSales, String> {

    List<WaresSales> findAllByUserId(String userId);

    WaresSales findBySalesId(String salesId);

    /**
     * 根据id集合查询商品，需要配合redis；
     * @param salesIdList 商品id列表
     * @return 返回商品列表
     */
    List<WaresSales> findAllBySalesIdIn(List<String> salesIdList);

    Page<WaresSales> findAllByStatusOrderByUpdateTimeDesc(Integer status, Pageable pageable);

    Page<WaresSales> findAllByStatusAndSalesAddressIdOrderByUpdateTimeDesc(Integer status, Integer addressId, Pageable pageable);
}

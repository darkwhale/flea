package org.zxy.flea.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.WaresBooth;

public interface WaresBoothRepository extends JpaRepository<WaresBooth, String> {
    WaresBooth findByUserId(String userId);

    Page<WaresBooth> findAllByOrderByUpdateTimeDesc(Pageable pageable);

    /**
     * 根据地址查询
     */
    Page<WaresBooth> findAllByAddressIdOrderByUpdateTimeDesc(Integer addressId, Pageable pageable);

}

package org.zxy.flea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zxy.flea.VO.AmoyVO;
import org.zxy.flea.dataobject.Sales;

import java.util.List;

public interface AmoyService {

    List<AmoyVO> getList(int size);

    AmoyVO converter(Sales sales);

    Page<Sales> findByName(String salesName, String synopsis);

    Page<AmoyVO> converter(Page<Sales> salesPage, Pageable pageable);
}

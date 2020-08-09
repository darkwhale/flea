package org.zxy.flea.service.impl;

import org.springframework.stereotype.Service;
import org.zxy.flea.VO.AmoyVO;
import org.zxy.flea.converter.AmoyConverter;
import org.zxy.flea.dataobject.BookSales;
import org.zxy.flea.dataobject.WaresSales;
import org.zxy.flea.mapper.BookSalesRepository;
import org.zxy.flea.mapper.WaresSalesRepository;
import org.zxy.flea.service.AmoyService;
import org.zxy.flea.util.SalesRedisUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AmoyServiceImpl implements AmoyService {

    @Resource
    private BookSalesRepository bookSalesRepository;

    @Resource
    private WaresSalesRepository waresSalesRepository;

    @Resource
    private SalesRedisUtil salesRedisUtil;

    @Resource
    private AmoyConverter amoyConverter;

    @Override
    public List<AmoyVO> getList(int size) {
        if (size < 0 || size > 40) {
            size = 20;
        }
        List<String> salesIdList = salesRedisUtil.getRandSalesIdList(size);

        List<BookSales> bookSalesResult = bookSalesRepository.findAllBySalesIdIn(salesIdList);
        List<WaresSales> waresSalesResult = waresSalesRepository.findAllBySalesIdIn(salesIdList);

        List<AmoyVO> bookAmoy = bookSalesResult.stream()
                .map(e -> amoyConverter.converter(e)).collect(Collectors.toList());

        List<AmoyVO> waresAmoy = waresSalesResult.stream()
                .map(e -> amoyConverter.converter(e)).collect(Collectors.toList());

        List<AmoyVO> result = new ArrayList<>();
        result.addAll(bookAmoy);
        result.addAll(waresAmoy);

        Random random = new Random(System.currentTimeMillis());
        Collections.shuffle(result, random);

        return result;
    }
}

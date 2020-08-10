package org.zxy.flea.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zxy.flea.VO.AmoyVO;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.service.impl.AmoyServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/amoy")
@RestController
public class AmoyController {

    @Resource
    private AmoyServiceImpl amoyService;

    @GetMapping("/list")
    ResponseVO<List<AmoyVO>> list(@RequestParam(value = "size", defaultValue = "20") Integer size) {
        List<AmoyVO> amoyVOList = amoyService.getList(size);

        return ResponseVOUtil.success(amoyVOList);
    }

    @GetMapping("/findByKeyword")
    ResponseVO<Page<AmoyVO>> findByKeyWord(@RequestParam("keyword") String keyword,
                                           @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        Page<Sales> salesPage = amoyService.findByName(keyword, PageRequest.of(pageNum, pageSize));

        Page<AmoyVO> amoyVOPage = amoyService.converter(salesPage, PageRequest.of(pageNum, pageSize));

        return ResponseVOUtil.success(amoyVOPage);
    }

}

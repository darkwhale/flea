package org.zxy.flea.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zxy.flea.VO.AmoyVO;
import org.zxy.flea.VO.ResponseVO;
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

}

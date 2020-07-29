package org.zxy.flea.controller;


import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.form.CampusForm;
import org.zxy.flea.service.impl.CampusServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/campus")
public class CampusController {

    @Resource
    private CampusServiceImpl campusService;

    @PostMapping("/add")
    ResponseVO<Campus> add(@RequestParam String campusName) {
        Campus campus = campusService.add(campusName);

        return ResponseVOUtil.success(campus);
    }

    @PostMapping("/update")
    ResponseVO<Campus> update(@Valid @RequestBody CampusForm campusForm) {
        Campus campus = campusService.update(campusForm);

        return ResponseVOUtil.success(campus);
    }

    @PostMapping("/delete")
    ResponseVO<Campus> delete(@RequestParam Integer campusId) {
        Campus campus = campusService.delete(campusId);

        return ResponseVOUtil.success(campus);
    }

    @GetMapping("/getList")
    ResponseVO<List<Campus>> getList() {
        List<Campus> campusList = campusService.getList();
        return ResponseVOUtil.success(campusList);
    }

}

package org.zxy.flea.controller;

import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.VO.SalesVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.form.SalesCreateForm;
import org.zxy.flea.form.SalesUpdateForm;
import org.zxy.flea.service.impl.SalesServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Resource
    private SalesServiceImpl salesService;

    @GetMapping("/userSales")
    ResponseVO<List<SalesVO>> userSales(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        List<Sales> salesList = salesService.getListByUserId(user.getUserId());

        List<SalesVO> salesVOList = salesService.converter(salesList);
        return ResponseVOUtil.success(salesVOList);
    }

    @PostMapping("/create")
    ResponseVO<SalesVO> create(@Valid @RequestBody SalesCreateForm salesCreateForm,
                               HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        Sales sales = salesService.create(salesCreateForm, user.getUserId());
        SalesVO salesVO = salesService.converter(sales);

        return ResponseVOUtil.success(salesVO);
    }

    @PostMapping("/update")
    ResponseVO<SalesVO> update(@Valid @RequestBody SalesUpdateForm salesUpdateForm,
                               HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        Sales sales = salesService.update(salesUpdateForm, user.getUserId());
        SalesVO salesVO = salesService.converter(sales);

        return ResponseVOUtil.success(salesVO);
    }

    @PostMapping("/delete")
    ResponseVO<SalesVO> delete(@RequestParam("salesId") String salesId,
                               HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        Sales sales = salesService.delete(salesId, user.getUserId());
        SalesVO salesVO = salesService.converter(sales);

        return ResponseVOUtil.success(salesVO);
    }

    @PostMapping("/onSale")
    ResponseVO<SalesVO> onSale(@RequestParam("salesId") String salesId,
                               HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        Sales sales = salesService.onSale(salesId, user.getUserId());
        SalesVO salesVO = salesService.converter(sales);

        return ResponseVOUtil.success(salesVO);
    }

    @PostMapping("/schedule")
    ResponseVO<SalesVO> schedule(@RequestParam("salesId") String salesId,
                                 HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        Sales sales = salesService.schedule(salesId, user.getUserId());
        SalesVO salesVO = salesService.converter(sales);

        return ResponseVOUtil.success(salesVO);
    }

    @PostMapping("/offSale")
    ResponseVO<SalesVO> offSale(@RequestParam("salesId") String salesId,
                                HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        Sales sales = salesService.offSale(salesId, user.getUserId());
        SalesVO salesVO = salesService.converter(sales);

        return ResponseVOUtil.success(salesVO);
    }

    @GetMapping("/salesInfo")
    ResponseVO<SalesVO> salesInfo(@RequestParam("salesId") String salesId) {
        Sales sales = salesService.getSalesInfo(salesId);
        SalesVO salesVO = salesService.converter(sales);

        return ResponseVOUtil.success(salesVO);
    }

    @GetMapping("/getList")
    ResponseVO<List<SalesVO>> getList(@RequestParam(value = "size", defaultValue = "20") Integer size) {
        List<Sales> salesList = salesService.getList(size);

        List<SalesVO> salesVOList = salesService.converter(salesList);

        return ResponseVOUtil.success(salesVOList);
    }

}

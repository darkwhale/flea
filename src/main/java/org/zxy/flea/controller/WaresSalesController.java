package org.zxy.flea.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.VO.WaresSalesVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.form.WaresSalesCreateForm;
import org.zxy.flea.form.WaresSalesUpdateForm;
import org.zxy.flea.service.impl.WaresSalesServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/waresBooth")
public class WaresSalesController {

    @Resource
    private WaresSalesServiceImpl waresSalesService;


    @GetMapping("/userSales")
    ResponseVO<List<WaresSalesVO>> userSales(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        List<Sales> bookSalesList = waresSalesService.getListByUserId(user.getUserId());

        List<WaresSalesVO> waresSalesVOList = waresSalesService.converter(bookSalesList);
        return ResponseVOUtil.success(waresSalesVOList);
    }

    @GetMapping("/salesList")
    ResponseVO<List<WaresSalesVO>> salesList(@RequestParam("userId") String userId) {

        List<Sales> waresSalesList = waresSalesService.getOtherListByUserId(userId);

        List<WaresSalesVO> waresSalesVOList = waresSalesService.converter(waresSalesList);
        return ResponseVOUtil.success(waresSalesVOList);
    }

    @PostMapping("/rub")
    ResponseVO rub(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        waresSalesService.rub(user.getUserId());
        return ResponseVOUtil.success();
    }

    @PostMapping("/close")
    ResponseVO close(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        waresSalesService.deleteAll(user.getUserId());
        return ResponseVOUtil.success();
    }

    @PostMapping("/create")
    ResponseVO<WaresSalesVO> create(@Valid @RequestBody WaresSalesCreateForm waresSalesCreateForm,
                                    HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales waresSales = waresSalesService.create(waresSalesCreateForm, user.getUserId());

        WaresSalesVO waresSalesVO = waresSalesService.converter(waresSales);

        return ResponseVOUtil.success(waresSalesVO);
    }

    @PostMapping("/update")
    ResponseVO<WaresSalesVO> update(@Valid @RequestBody WaresSalesUpdateForm waresSalesUpdateForm,
                                    HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales waresSales = waresSalesService.update(waresSalesUpdateForm, user.getUserId());

        WaresSalesVO waresSalesVO = waresSalesService.converter(waresSales);

        return ResponseVOUtil.success(waresSalesVO);
    }

    @PostMapping("/delete")
    ResponseVO<WaresSalesVO> delete(@RequestParam("salesId") String salesId,
                                   HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales waresSales = waresSalesService.delete(salesId, user.getUserId());

        WaresSalesVO waresSalesVO = waresSalesService.converter(waresSales);

        return ResponseVOUtil.success(waresSalesVO);
    }

    @PostMapping("/on_sale")
    ResponseVO<WaresSalesVO> onSale(@RequestParam("salesId") String salesId,
                                    HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales waresSales = waresSalesService.onSale(salesId, user.getUserId());
        WaresSalesVO waresSalesVO = waresSalesService.converter(waresSales);

        return ResponseVOUtil.success(waresSalesVO);
    }

    @PostMapping("/schedule")
    ResponseVO<WaresSalesVO> schedule(@RequestParam("salesId") String salesId,
                                      HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales waresSales = waresSalesService.schedule(salesId, user.getUserId());
        WaresSalesVO waresSalesVO = waresSalesService.converter(waresSales);

        return ResponseVOUtil.success(waresSalesVO);
    }

    @PostMapping("/off_sale")
    ResponseVO<WaresSalesVO> offSale(@RequestParam("salesId") String salesId,
                                     HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales waresSales = waresSalesService.offSale(salesId, user.getUserId());
        WaresSalesVO waresSalesVO = waresSalesService.converter(waresSales);

        return ResponseVOUtil.success(waresSalesVO);
    }


    @GetMapping("/getByAddress")
    ResponseVO<Page<WaresSalesVO>> getByAddress(@RequestParam(value = "addressId", required = false) Integer addressId,
                                              @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        Page<Sales> waresSalesPage = waresSalesService.getListByAddressId(addressId, PageRequest.of(pageNum, pageSize));
        Page<WaresSalesVO> waresSalesVOPage = waresSalesService.converter(waresSalesPage, PageRequest.of(pageNum, pageSize));

        return ResponseVOUtil.success(waresSalesVOPage);
    }


}

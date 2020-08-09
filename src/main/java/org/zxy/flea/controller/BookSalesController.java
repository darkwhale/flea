package org.zxy.flea.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.BookSalesVO;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.Sales;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.form.BookSalesCreateForm;
import org.zxy.flea.form.BookSalesUpdateForm;
import org.zxy.flea.service.impl.BookSalesServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bookBooth")
public class BookSalesController {

    @Resource
    private BookSalesServiceImpl bookSalesService;

    @GetMapping("/userSales")
    ResponseVO<List<BookSalesVO>> userSales(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        List<Sales> bookSalesList = bookSalesService.getListByUserId(user.getUserId());

        List<BookSalesVO> bookSalesVOList = bookSalesService.converter(bookSalesList);
        return ResponseVOUtil.success(bookSalesVOList);
    }

    @GetMapping("/salesList")
    ResponseVO<List<BookSalesVO>> salesList(@RequestParam("userId") String userId) {

        List<Sales> bookSalesList = bookSalesService.getListByUserId(userId);

        List<BookSalesVO> bookSalesVOList = bookSalesService.converter(bookSalesList);
        return ResponseVOUtil.success(bookSalesVOList);
    }

    @PostMapping("/rub")
    ResponseVO rub(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        bookSalesService.rub(user.getUserId());
        return ResponseVOUtil.success();
    }

    @PostMapping("/close")
    ResponseVO close(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        bookSalesService.deleteAll(user.getUserId());
        return ResponseVOUtil.success();
    }

    @PostMapping("/create")
    ResponseVO<BookSalesVO> create(@Valid @RequestBody BookSalesCreateForm bookSalesCreateForm,
                                 HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales bookSales = bookSalesService.create(bookSalesCreateForm, user.getUserId());

        BookSalesVO bookSalesVO = bookSalesService.converter(bookSales);

        return ResponseVOUtil.success(bookSalesVO);
    }

    @PostMapping("/update")
    ResponseVO<BookSalesVO> update(@Valid @RequestBody BookSalesUpdateForm bookSalesUpdateForm,
                                   HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales bookSales = bookSalesService.update(bookSalesUpdateForm, user.getUserId());

        BookSalesVO bookSalesVO = bookSalesService.converter(bookSales);

        return ResponseVOUtil.success(bookSalesVO);
    }

    @PostMapping("/delete")
    ResponseVO<BookSalesVO> delete(@RequestParam("salesId") String salesId,
                                   HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales bookSales = bookSalesService.delete(salesId, user.getUserId());

        BookSalesVO bookSalesVO = bookSalesService.converter(bookSales);

        return ResponseVOUtil.success(bookSalesVO);
    }

    @PostMapping("/on_sale")
    ResponseVO<BookSalesVO> onSale(@RequestParam("salesId") String salesId,
                                   HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales bookSales = bookSalesService.onSale(salesId, user.getUserId());
        BookSalesVO bookSalesVO = bookSalesService.converter(bookSales);

        return ResponseVOUtil.success(bookSalesVO);
    }

    @PostMapping("/schedule")
    ResponseVO<BookSalesVO> schedule(@RequestParam("salesId") String salesId,
                                   HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales bookSales = bookSalesService.schedule(salesId, user.getUserId());
        BookSalesVO bookSalesVO = bookSalesService.converter(bookSales);

        return ResponseVOUtil.success(bookSalesVO);
    }

    @PostMapping("/off_sale")
    ResponseVO<BookSalesVO> offSale(@RequestParam("salesId") String salesId,
                                   HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);
        Sales bookSales = bookSalesService.offSale(salesId, user.getUserId());
        BookSalesVO bookSalesVO = bookSalesService.converter(bookSales);

        return ResponseVOUtil.success(bookSalesVO);
    }

    @GetMapping("/getByCampus")
    ResponseVO<Page<BookSalesVO>> getByCampus(@RequestParam(value = "campusId", required = false) Integer campusId,
                                              @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        Page<Sales> bookSalesPage = bookSalesService.getListByCampusId(campusId, PageRequest.of(pageNum, pageSize));
        Page<BookSalesVO> bookSalesVOPage = bookSalesService.converter(bookSalesPage, PageRequest.of(pageNum, pageSize));

        return ResponseVOUtil.success(bookSalesVOPage);
    }



}

package org.zxy.flea.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.BookBooth;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.form.BookBoothForm;
import org.zxy.flea.service.impl.BookBoothServiceImpl;
import org.zxy.flea.service.impl.UserServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/bookBooth")
public class BookBoothController {

    @Resource
    private BookBoothServiceImpl bookBoothService;

    @Resource
    private UserServiceImpl userService;

    @PostMapping("/create")
    ResponseVO<BookBooth> create(@Valid @RequestBody BookBoothForm bookBoothForm,
                                 HttpSession session) {

        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        BookBooth bookBooth = bookBoothService.create(bookBoothForm, user.getUserId());

        return ResponseVOUtil.success(bookBooth);
    }

    @PostMapping("/close")
    ResponseVO<BookBooth> close(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        BookBooth bookBooth = bookBoothService.close(user.getUserId());

        return ResponseVOUtil.success(bookBooth);
    }

    @GetMapping("/getListByName")
    ResponseVO<Page<BookBooth>> getListByName(@RequestParam("boothName") String boothName,
                                              @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<BookBooth> bookBoothPage = bookBoothService.getBoothList(boothName, PageRequest.of(pageNum, pageSize));
        return ResponseVOUtil.success(bookBoothPage);
    }

    @GetMapping("/getList")
    ResponseVO<Page<BookBooth>> getList(@RequestParam(value = "campusId", defaultValue = "-1") Integer campusId,
                                        @RequestParam(value = "addressId", defaultValue = "-1") Integer addressId,
                                        @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<BookBooth> bookBoothPage = null;

        if (campusId.equals(-1) && addressId.equals(-1)) {
             bookBoothPage = bookBoothService.getBoothList(PageRequest.of(pageNum, pageSize));
        }

        if (campusId.equals(-1) && !addressId.equals(-1)) {
            bookBoothPage = bookBoothService.getBoothListByAddress(addressId, PageRequest.of(pageNum, pageSize));
        }

        if (!campusId.equals(-1) && addressId.equals(-1)) {
            bookBoothPage = bookBoothService.getBoothListByCampus(campusId, PageRequest.of(pageNum, pageSize));
        }

        if (!campusId.equals(-1) && !addressId.equals(-1)) {
            bookBoothPage = bookBoothService.getBoothList(PageRequest.of(pageNum, pageSize));
        }

        return ResponseVOUtil.success(bookBoothPage);
    }

}

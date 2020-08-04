package org.zxy.flea.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.BookBoothVO;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.BookBooth;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.form.BookBoothForm;
import org.zxy.flea.service.impl.BookBoothServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/bookBooth")
public class BookBoothController {

    @Resource
    private BookBoothServiceImpl bookBoothService;


    @PostMapping("/modify")
    ResponseVO<BookBoothVO> modify(@Valid @RequestBody BookBoothForm bookBoothForm,
                                   HttpSession session) {

        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        BookBooth bookBooth = bookBoothService.modify(bookBoothForm, user.getUserId());

        BookBoothVO bookBoothVO = bookBoothService.converter(bookBooth);

        return ResponseVOUtil.success(bookBoothVO);
    }

    @GetMapping("userBooth")
    ResponseVO<BookBoothVO> userBooth(@RequestParam("userId") String userId) {
        BookBooth bookBooth = bookBoothService.getBooth(userId);

        BookBoothVO bookBoothVO = bookBoothService.converter(bookBooth);
        return ResponseVOUtil.success(bookBoothVO);
    }

    @PostMapping("/close")
    ResponseVO<BookBoothVO> close(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        BookBooth bookBooth = bookBoothService.close(user.getUserId());

        BookBoothVO bookBoothVO = bookBoothService.converter(bookBooth);

        return ResponseVOUtil.success(bookBoothVO);
    }

    @PostMapping("/rub")
    ResponseVO<BookBoothVO> rub(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        BookBooth bookBooth = bookBoothService.rub(user.getUserId());

        BookBoothVO bookBoothVO = bookBoothService.converter(bookBooth);

        return ResponseVOUtil.success(bookBoothVO);
    }


    @GetMapping("/bookBooth")
    ResponseVO<BookBoothVO> bookBooth(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        BookBooth bookBooth = bookBoothService.getBooth(user.getUserId());

        BookBoothVO bookBoothVO = bookBoothService.converter(bookBooth);

        return ResponseVOUtil.success(bookBoothVO);
    }

    @GetMapping("/getList")
    ResponseVO<Page<BookBoothVO>> getList(@RequestParam(value = "campusId", defaultValue = "-1") Integer campusId,
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
            bookBoothPage = bookBoothService.getBoothList(campusId, addressId, PageRequest.of(pageNum, pageSize));
        }

        return ResponseVOUtil.success(bookBoothService.converter(bookBoothPage, PageRequest.of(pageNum, pageSize)));
    }

}

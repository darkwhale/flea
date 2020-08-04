package org.zxy.flea.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.VO.WaresBoothVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.dataobject.WaresBooth;
import org.zxy.flea.form.WaresBoothForm;
import org.zxy.flea.service.impl.WaresBoothServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/waresBooth")
public class WaresBoothController {

    @Resource
    private WaresBoothServiceImpl waresBoothService;

    @GetMapping("/waresBooth")
    ResponseVO<WaresBoothVO> waresBooth(HttpSession session) {

        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        WaresBooth waresBooth = waresBoothService.getBooth(user.getUserId());

        WaresBoothVO waresBoothVO = waresBoothService.converter(waresBooth);
        return ResponseVOUtil.success(waresBoothVO);
    }

    @PostMapping("/modify")
    ResponseVO<WaresBoothVO> modify(@Valid @RequestBody WaresBoothForm waresBoothForm,
                                    HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        WaresBooth waresBooth = waresBoothService.modify(waresBoothForm, user.getUserId());
        WaresBoothVO waresBoothVO = waresBoothService.converter(waresBooth);
        return ResponseVOUtil.success(waresBoothVO);
    }

    @PostMapping("/close")
    ResponseVO<WaresBoothVO> close(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        WaresBooth waresBooth = waresBoothService.close(user.getUserId());
        WaresBoothVO waresBoothVO = waresBoothService.converter(waresBooth);
        return ResponseVOUtil.success(waresBoothVO);
    }

    @PostMapping("/rub")
    ResponseVO<WaresBoothVO> rub(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        WaresBooth waresBooth = waresBoothService.rub(user.getUserId());
        WaresBoothVO waresBoothVO = waresBoothService.converter(waresBooth);

        return ResponseVOUtil.success(waresBoothVO);
    }

    @GetMapping("/userBooth")
    ResponseVO<WaresBoothVO> userBooth(@RequestParam("userId") String userId) {
        WaresBooth waresBooth = waresBoothService.getBooth(userId);

        WaresBoothVO waresBoothVO = waresBoothService.converter(waresBooth);

        return ResponseVOUtil.success(waresBoothVO);
    }

    @GetMapping("/getList")
    ResponseVO<Page<WaresBoothVO>> getList(@RequestParam(value = "addressId", defaultValue = "-1") Integer addressId,
                                           @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<WaresBooth> waresBoothPage = null;

        if (addressId.equals(-1)) {
            waresBoothPage = waresBoothService.getBoothList(PageRequest.of(pageNum, pageSize));
        }else {
            waresBoothPage = waresBoothService.getBoothListByCampus(addressId, PageRequest.of(pageNum, pageSize));
        }

        return ResponseVOUtil.success(waresBoothService.converter(waresBoothPage, PageRequest.of(pageNum, pageSize)));
    }


}

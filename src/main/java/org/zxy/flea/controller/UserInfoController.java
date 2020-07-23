package org.zxy.flea.controller;

import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.VO.UserInfoVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.dataobject.UserInfo;
import org.zxy.flea.form.UserInfoForm;
import org.zxy.flea.service.impl.UserInfoServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoServiceImpl userInfoService;

    @GetMapping("/userInfo")
    ResponseVO<UserInfoVO> userInfo(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        UserInfo userInfo = userInfoService.findByUserEmail(user.getEmail());

        UserInfoVO userInfoVO = userInfoService.converter(userInfo);
        return ResponseVOUtil.success(userInfoVO);
    }

    @PostMapping("/modify")
    ResponseVO<UserInfoVO> modify(@Valid @RequestBody UserInfoForm userInfoForm,
                                  HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        UserInfo userInfo = userInfoService.modify(userInfoForm, user.getEmail());

        UserInfoVO userInfoVO = userInfoService.converter(userInfo);
        return ResponseVOUtil.success(userInfoVO);
    }

}

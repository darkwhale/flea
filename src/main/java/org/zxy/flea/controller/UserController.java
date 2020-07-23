package org.zxy.flea.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.VO.UserVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.form.UserLoginForm;
import org.zxy.flea.form.UserRegisterForm;
import org.zxy.flea.service.impl.UserServiceImpl;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @PostMapping("/register")
    ResponseVO<UserVO> register(@Valid @RequestBody UserRegisterForm userRegisterForm) {
        User user = userService.register(userRegisterForm);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        return ResponseVOUtil.success(userVO);
    }

    @PostMapping("/login")
    ResponseVO<UserVO> login(@Valid @RequestBody UserLoginForm userLoginForm,
                             HttpSession session) {
        User user =  userService.login(userLoginForm);

        // 设置session
        session.setAttribute(FleaConst.CURRENT_USER, user);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        return ResponseVOUtil.success(userVO);
    }

    @GetMapping("/user")
    ResponseVO<UserVO> userInfo(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        return ResponseVOUtil.success(userVO);
    }

    @PostMapping("/logout")
    ResponseVO<UserVO> logout(HttpSession session) {
        session.removeAttribute(FleaConst.CURRENT_USER);

        return ResponseVOUtil.success();
    }

}

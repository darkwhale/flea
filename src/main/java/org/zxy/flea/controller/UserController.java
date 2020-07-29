package org.zxy.flea.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.VO.UserVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.form.UserForm;
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

        UserVO userVO = userService.converter(user);

        return ResponseVOUtil.success(userVO);
    }

    @PostMapping("/update")
    ResponseVO<UserVO> update(@Valid @RequestBody UserForm userForm,
                              HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        User update = userService.update(userForm, user.getUserId());
        session.setAttribute(FleaConst.CURRENT_USER, update);

        UserVO userVO = userService.converter(update);

        return ResponseVOUtil.success(userVO);
    }

    @GetMapping("/user")
    ResponseVO<UserVO> userInfo(HttpSession session) {
        User user = (User) session.getAttribute(FleaConst.CURRENT_USER);

        UserVO userVO = userService.converter(user);

        return ResponseVOUtil.success(userVO);
    }

    @PostMapping("/logout")
    ResponseVO logout(HttpSession session) {
        session.removeAttribute(FleaConst.CURRENT_USER);

        return ResponseVOUtil.success();
    }

    @GetMapping("/findByName")
    ResponseVO<Page<User>> findByUsername(@RequestParam("username") String username,
                                          @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<User> userPage = userService.findByUsername(username, PageRequest.of(pageNum, pageSize));
        return ResponseVOUtil.success(userPage);
    }

    @PostMapping("/findById")
    ResponseVO<UserVO> findById(@RequestParam("userId") String userId) {
        User user = userService.findByUserId(userId);

        UserVO userVo = userService.converter(user);

        return ResponseVOUtil.success(userVo);
    }

}

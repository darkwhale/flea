package org.zxy.flea.service.impl;

import org.springframework.beans.BeanUtils;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.VO.UserVO;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.UserLoginForm;
import org.zxy.flea.form.UserRegisterForm;
import org.zxy.flea.mapper.UserRepository;
import org.zxy.flea.service.UserService;
import org.zxy.flea.util.KeyUtil;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;

public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private KeyUtil keyUtil;

    @Override
    public ResponseVO<UserVO> register(UserRegisterForm userRegisterForm) {
        // 判断是否已存在
        User user = userRepository.findByEmail(userRegisterForm.getEmail());
        if (user != null) {
            throw new FleaException(ResponseEnum.USER_EXIST);
        }

        // 插入数据库中
        User newUser = new User();
        BeanUtils.copyProperties(userRegisterForm, newUser);
        newUser.setUserId(keyUtil.genUniqueKey());

        // 插入数据
        userRepository.save(newUser);

        // 返回数据
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(newUser, userVO);

        return ResponseVOUtil.success(userVO);
    }

    @Override
    public ResponseVO<UserVO> login(UserLoginForm userLoginForm) {
        // 查找数据库
        User user = userRepository.findByEmailAndPassword(userLoginForm.getEmail(), userLoginForm.getPassword());
        if (user == null) {
            throw new FleaException(ResponseEnum.USER_NOT_EXIST_OR_PASSWORD_WRONG);
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        return ResponseVOUtil.success(userVO);
    }
}

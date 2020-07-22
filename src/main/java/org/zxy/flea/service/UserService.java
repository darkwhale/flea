package org.zxy.flea.service;

import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.VO.UserVO;
import org.zxy.flea.form.UserLoginForm;
import org.zxy.flea.form.UserRegisterForm;

public interface UserService {

    public ResponseVO<UserVO> register(UserRegisterForm userRegisterForm);

    public ResponseVO<UserVO> login(UserLoginForm userLoginForm);
}

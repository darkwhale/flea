package org.zxy.flea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zxy.flea.VO.UserVO;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.form.UserForm;
import org.zxy.flea.form.UserLoginForm;
import org.zxy.flea.form.UserRegisterForm;

public interface UserService {

    User register(UserRegisterForm userRegisterForm);

    User login(UserLoginForm userLoginForm);

    User update(UserForm userForm, String userId);

    Page<User> findByUsername(String username, Pageable pageable);

    UserVO converter(User user);

    User findByUserId(String userId);
}

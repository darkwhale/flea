package org.zxy.flea.service;

import org.zxy.flea.dataobject.User;
import org.zxy.flea.form.UserLoginForm;
import org.zxy.flea.form.UserRegisterForm;

public interface UserService {

    public User register(UserRegisterForm userRegisterForm);

    public User login(UserLoginForm userLoginForm);
}

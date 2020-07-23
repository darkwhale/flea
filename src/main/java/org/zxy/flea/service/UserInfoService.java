package org.zxy.flea.service;

import org.zxy.flea.dataobject.UserInfo;
import org.zxy.flea.form.UserInfoForm;

public interface UserInfoService {

    /**
     * 通过邮箱查找用户信息
     * @param userEmail
     * @return
     */
    UserInfo findByUserEmail(String userEmail);

    /**
     * 修改或添加用户信息
     * @param userInfoForm
     * @return
     */
    UserInfo modify(UserInfoForm userInfoForm, String userEmail);
}

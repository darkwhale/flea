package org.zxy.flea.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(FleaConst.CURRENT_USER);

        if (user == null) {
            throw new FleaException(ResponseEnum.USER_NOT_LOGIN);
        }

        return true;
    }
}

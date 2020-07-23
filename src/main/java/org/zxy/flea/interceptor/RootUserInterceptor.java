package org.zxy.flea.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.enums.RoleEnum;
import org.zxy.flea.exception.FleaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RootUserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(FleaConst.CURRENT_USER);

        // 用户是否登陆
        if (user == null) {
            throw new FleaException(ResponseEnum.USER_NOT_LOGIN);
        }

        //用户是否是root
        if (!user.getRole().equals(RoleEnum.ROOT.getCode())) {
            throw new FleaException(ResponseEnum.AUTOHORITY_FAILED);
        }

        return true;
    }
}

package org.zxy.flea.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register", "/user/findByName", "/user/findById",
                        "/address/*", "/campus/*", "/bookBooth/salesList", "/waresBooth/salesList", "/amoy/list",
                        "/bookBooth/getByCampus", "/waresBooth/getByAddress", "/amoy/findByKeyword");

        registry.addInterceptor(new RootUserInterceptor())
                .addPathPatterns("/address/*", "/campus/*")
                .excludePathPatterns("/address/floorList", "/address/regionList", "/campus/getList", "/address/lifeList",
                        "/address/studyList", "/address/nonRegionList");
    }
}

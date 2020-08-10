package org.zxy.flea.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(0, "成功"),

    ERROR(-1, "服务器错误"),


    USER_EXIST(1, "用户已存在"),

    USER_NOT_EXIST_OR_PASSWORD_WRONG(2, "用户不存在或密码错误"),

    USER_NOT_LOGIN(3, "用户未登陆"),

    AUTOHORITY_FAILED(4, "权限不足"),

    USER_NOT_EXIST(5, "用户不存在"),


    ADDRESS_NOT_EXIST(10, "地址不存在"),

    CAPMUS_NOT_EXIST(11, "学院不存在"),


    SALES_NOT_EXIST(20, "商品不存在"),


    TYPE_ERROR(30, "错误类型"),


    PARAM_ERROR(50, "输入参数有误"),
    ;

    private Integer code;

    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

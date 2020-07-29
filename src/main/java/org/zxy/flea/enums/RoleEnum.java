package org.zxy.flea.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ROOT(1, "管理员"),

    CUSTOM(0, "客户"),
    ;

    private Integer code;

    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

package org.zxy.flea.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {

    Male(0, "男"),

    FEMALE(1, "女"),

    ;

    private Integer code;

    private String message;

    GenderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

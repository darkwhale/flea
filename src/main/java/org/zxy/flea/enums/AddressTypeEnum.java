package org.zxy.flea.enums;

import lombok.Getter;

@Getter
public enum AddressTypeEnum {

    LIFE_ADDRESS(0, "宿舍"),

    STUDY_ADDRESS(1, "学习"),
    ;


    private Integer code;

    private String message;

    AddressTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

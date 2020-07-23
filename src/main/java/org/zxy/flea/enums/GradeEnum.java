package org.zxy.flea.enums;

import lombok.Getter;

@Getter
public enum GradeEnum {
    Grade_1(1, "大学1年级"),

    Grade_2(2, "大学2年级"),

    Grade_3(3, "大学3年级"),

    Grade_4(4, "大学4年级"),

    ;

    private Integer code;

    private String message;

    GradeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

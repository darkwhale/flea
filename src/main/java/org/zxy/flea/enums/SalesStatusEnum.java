package org.zxy.flea.enums;

import lombok.Getter;

@Getter
public enum SalesStatusEnum {

    ON_SALE(0, "在售"),

    SCHEDULE(1, "已预订"),

    OFF_SALE(2, "已卖出"),
    ;

    private Integer code;

    private String message;

    SalesStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

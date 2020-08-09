package org.zxy.flea.enums;

import lombok.Getter;

@Getter
public enum SalesTypeEnum {

    BOOK(0, "书籍"),

    WARES(1, "商品"),
    ;

    private Integer code;

    private String message;

    SalesTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

package org.zxy.flea.exception;

import lombok.Getter;
import org.zxy.flea.enums.ResponseEnum;

@Getter
public class FleaException extends RuntimeException {

    private Integer code;

    private String message;

    public FleaException(ResponseEnum responseEnum) {
//        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }
}

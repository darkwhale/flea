package org.zxy.flea.VO;

import lombok.Data;
import org.zxy.flea.enums.ResponseEnum;

@Data
public class ResponseVO<T> {

    private Integer code;

    private String message;

    private T data;

    public ResponseVO(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    public ResponseVO(ResponseEnum responseEnum, T data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    public ResponseVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

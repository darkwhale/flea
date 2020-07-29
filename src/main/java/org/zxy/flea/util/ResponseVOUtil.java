package org.zxy.flea.util;

import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.enums.ResponseEnum;

public class ResponseVOUtil {

    public static ResponseVO success() {
        return new ResponseVO(ResponseEnum.SUCCESS);
    }

    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<>(ResponseEnum.SUCCESS, data);
    }

    public static ResponseVO error(ResponseEnum responseEnum) {
        return new ResponseVO(responseEnum);
    }

    public static ResponseVO error(Integer code, String message) {
        return new ResponseVO(code, message);
    }
}

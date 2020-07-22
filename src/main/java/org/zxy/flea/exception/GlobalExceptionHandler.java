package org.zxy.flea.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.util.ResponseVOUtil;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVO validHandler() {
        return ResponseVOUtil.error(ResponseEnum.PARAM_ERROR);
    }

    @ExceptionHandler(FleaException.class)
    public ResponseVO fleaExceptionHandler(FleaException e) {
        return ResponseVOUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseVO nullPointHandler() {
        return ResponseVOUtil.error(ResponseEnum.ERROR);
    }
}

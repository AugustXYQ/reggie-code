package org.itxyq.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/2
 * @description 全局异常处理
 **/
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     * @param ex sql异常
     * @return R<String>
     * @description sql异常处理方法
     **/
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        if (ex.getMessage().contains("Duplicate entry")) {
            String msg = ex.getMessage().split(" ")[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    /**
     * @param ex 自定义异常
     * @return R<String>
     * @description 自定义异常处理方法
     **/
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex) {
        log.error(ex.getMessage(), ex);
        return R.error(ex.getMessage());
    }
}

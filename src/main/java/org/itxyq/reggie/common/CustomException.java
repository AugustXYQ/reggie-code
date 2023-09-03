package org.itxyq.reggie.common;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/3
 * @description 用户自定义异常
 **/
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}

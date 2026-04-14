package com.qqsystem.serve.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理所有异常
    @ExceptionHandler(Exception.class)
    public ResponseResult<?> handleException(Exception e) {
        // 打印异常信息
        e.printStackTrace();
        // 返回服务器错误
        return ResponseResult.serverError("服务器内部错误");
    }

    // 处理参数错误异常
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseResult<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseResult.badRequest(e.getMessage());
    }

    // 处理空指针异常
    @ExceptionHandler(NullPointerException.class)
    public ResponseResult<?> handleNullPointerException(NullPointerException e) {
        return ResponseResult.serverError("服务器内部错误");
    }
}

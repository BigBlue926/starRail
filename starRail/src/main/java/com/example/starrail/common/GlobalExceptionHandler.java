package com.example.starrail.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex. getMessage().split(" ");
            String msg = split[2] + "当前已存在";
            log.info(msg);
            return Result.error(msg);
        }
        return Result.error("失败");
    }
    @ExceptionHandler(CustomException.class)
    public Result exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        return Result.error((ex.getMessage()));

    }
}

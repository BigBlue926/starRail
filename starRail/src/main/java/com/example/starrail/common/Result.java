package com.example.starrail.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Result<T> {
    Integer code;
    String msg;
    T data;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg =msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(T data){
        return new Result<T>(1,"成功",data);
    }
    public static <T> Result<T> success(String msg){
        return new Result<T>(1,msg);
    }
    public static <T> Result<T> error(String msg){
        return new Result<T>(0,msg);
    }
    public static <T> Result<T> LoginSuccess(T data){
        return new Result<T>(1,"登录成功",data);
    }
}

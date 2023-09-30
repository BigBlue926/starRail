package com.example.starrail.common;

public class CustomException  extends RuntimeException{
    public CustomException(String msg){
        super(msg);//调用父类的有参构造函数
    }
}

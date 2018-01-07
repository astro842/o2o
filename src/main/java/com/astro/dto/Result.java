package com.astro.dto;

import lombok.Data;

/**
 * Created by astro on 2018/1/4.
 */
@Data
public class Result<T> {

    private boolean success;
    private T data;
    private String errorMsg;
    private int errorCode;

    public Result() {}

    //成功
    public Result(boolean success,T data){
        this.success=success;
        this.data=data;
    }

    //错误
    public Result(boolean success,int errorCode,String errorMsg) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }
}

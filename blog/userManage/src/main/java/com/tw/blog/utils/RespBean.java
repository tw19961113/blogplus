package com.tw.blog.utils;

import lombok.Data;

@Data
public class RespBean<T> {

    private Integer status;
    private String msg;
    private T data;

    public RespBean(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static RespBean buildResult(Integer status,String msg){
        return new RespBean(status,msg);
    }

    public RespBean(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static RespBean buildResult(Integer status, String msg, Object data){
        return new RespBean(status,msg,data);
    }
}

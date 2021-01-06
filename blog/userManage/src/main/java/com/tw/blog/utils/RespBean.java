package com.tw.blog.utils;

import lombok.Data;

@Data
public class RespBean<T> {

    private Integer status;
    private String msg;
    private T data;

}

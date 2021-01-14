package com.tw.blog.utils;

/**
 * by TanWei 2021/1/14
 **/
public class StringUtils {

    /**
     * 非空判断
     * @param data
     * @return
     */
    public static boolean isNotNull(String data){

        if(!"".equals(data) && null != data) return true;
        return false;
    }

    public static boolean isNull(String data){
        if(null == data || "".equals(data)) return true;
        return false;
    }
}

package com.tw.blog.utils;

/**
 * by TanWei 2021/1/7
 **/
public enum Constant {

    /**
     * 通用
     */
    SUCCESS("成功", 200),
    ERROR("失败",500),
    INVALID_TOKEN("无效的token",401);

    private String desc;//文字描述
    private Integer code; //对应的代码

    /**
     * 私有构造,防止被外部调用
     *
     * @param desc
     */
    private Constant(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    /**
     * 定义方法,返回描述,跟常规类的定义没区别
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 定义方法,返回代码,跟常规类的定义没区别
     *
     * @return
     */
    public int getCode() {
        return code;
    }
}

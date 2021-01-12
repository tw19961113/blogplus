package com.tw.blog.service;

import com.baomidou.mybatisplus.service.IService;
import com.tw.blog.pojo.TCust;

/**
 * by TanWei 2021/1/5
 **/
public interface ICustService extends IService<TCust> {
    /**
     * 登录方法，查询用户
     * @param userName
     * @return
     */
    TCust selectUserByUserName(String userName);
}

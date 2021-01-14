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

    /**
     * 更新用户token
     * @param tCust
     */
    void updateCust(TCust tCust) throws RuntimeException;

    /**
     * 用户token检验
     * @param token
     * @return
     */
    boolean checkToken(String token);

    /**
     * 查找用户
     * @param tCust
     * @return
     */
    TCust selectUser(TCust tCust);
}

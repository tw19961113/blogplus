package com.tw.blog.service;

import com.baomidou.mybatisplus.service.IService;
import com.tw.blog.pojo.TCust;
import com.tw.blog.pojo.TUser;
import org.springframework.stereotype.Service;


/**
 * by TanWei 2021/1/5
 **/
@Service("userService")
public interface IUserService extends IService<TUser> {

}

package com.tw.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tw.blog.mapper.UserMapper;
import com.tw.blog.pojo.TUser;
import com.tw.blog.service.IUserService;
import org.springframework.stereotype.Service;


/**
 * by TanWei 2021/1/5
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,TUser> implements IUserService {

}

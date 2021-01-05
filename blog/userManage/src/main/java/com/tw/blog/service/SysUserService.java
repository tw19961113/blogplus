package com.tw.blog.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tw.blog.mapper.CustMapper;
import com.tw.blog.mapper.UserMapper;
import com.tw.blog.pojo.TCust;
import com.tw.blog.pojo.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * by TanWei 2021/1/5
 **/
@Service
public class SysUserService implements UserDetailsService {

    @Autowired
    private CustMapper custMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       // System.out.println("用户名：" + username);  //日志输出

        Wrapper<TCust> wrapper = new QueryWrapper<TCust>();
        ((QueryWrapper<TCust>)wrapper).eq("login_no",username);
        List<TCust> list = custMapper.selectList(wrapper);
        if (list.size() == 0) {
            throw new UsernameNotFoundException("帐号不存在");
        }
       return list.get(0);
    }
}

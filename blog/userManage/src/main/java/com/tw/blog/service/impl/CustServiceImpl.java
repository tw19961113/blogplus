package com.tw.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tw.blog.mapper.CustMapper;
import com.tw.blog.pojo.TCust;
import com.tw.blog.service.ICustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * by TanWei 2021/1/5
 **/
@Service("custService")
public class CustServiceImpl extends ServiceImpl<CustMapper, TCust> implements ICustService {
    @Autowired
    private CustMapper custMapper;

    @Override
    public TCust selectUserByUserName(String userName) {
        Wrapper wp = new QueryWrapper();
        ((QueryWrapper) wp).eq("login_no",userName);
        List<TCust> list = custMapper.selectList(wp);
        if (null != list && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}

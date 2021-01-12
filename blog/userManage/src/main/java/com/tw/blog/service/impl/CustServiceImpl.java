package com.tw.blog.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tw.blog.mapper.CustMapper;
import com.tw.blog.pojo.TCust;
import com.tw.blog.service.ICustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * by TanWei 2021/1/5
 **/
@Service
public class CustServiceImpl extends ServiceImpl<CustMapper, TCust> implements ICustService {

    @Autowired
    private CustMapper custMapper;

    @Override
    public TCust selectUserByUserName(String userName) {
        TCust tCustTemp = new TCust();
        tCustTemp.setLoginNo(userName);
        TCust tCust = custMapper.selectOne(tCustTemp);
//        if (null != list && list.size() > 0){
//            return list.get(0);
//        }
        return tCust;
    }
}

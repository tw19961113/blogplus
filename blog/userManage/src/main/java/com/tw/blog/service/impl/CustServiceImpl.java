package com.tw.blog.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tw.blog.mapper.CustMapper;
import com.tw.blog.pojo.TCust;
import com.tw.blog.service.ICustService;
import com.tw.blog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * by TanWei 2021/1/5
 **/
@Service
@Slf4j
public class CustServiceImpl extends ServiceImpl<CustMapper, TCust> implements ICustService {

    @Autowired
    private CustMapper custMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public TCust selectUserByUserName(String userName) {
        TCust tCustTemp = new TCust();
        tCustTemp.setLoginNo(userName);
        return custMapper.selectOne(tCustTemp);
    }

    @Override
    public void updateToken(TCust tCust) throws RuntimeException {
        custMapper.updateById(tCust);
    }

    @Override
    public boolean checkToken(String token) {
        //redis key 存放token，value存放时间，token有效时间30分钟
        String time = redisUtil.get(token);
        if (null == time){
            //token不存在
            return false;
        }else {
            Long now = System.currentTimeMillis() - Long.parseLong(time);
            if(now > 1800000){
                //长时间未操作，自动下线
                redisUtil.delete(token);
                try {
                    TCust tCust = new TCust();
                    tCust.setToken(null);
                    updateToken(tCust);
                } catch (RuntimeException e) {
                    log.error("----CustServiceImpl checkLogin updateToken error----",e);
                }finally {
                    return false;
                }
            }else {
                //token合法
                //更新redis里token的时间
                redisUtil.delete(token);
                redisUtil.set(token,String.valueOf(System.currentTimeMillis()));
                return true;
            }
        }
    }
}

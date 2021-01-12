package com.tw.blog.test;

import com.tw.blog.App;
import com.tw.blog.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * by TanWei 2021/1/7
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void redisTest(){
        redisUtil.set("1","hello");
        System.out.println(redisUtil.get("1"));
    }
}

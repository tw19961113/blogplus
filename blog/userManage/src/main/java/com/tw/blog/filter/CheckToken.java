package com.tw.blog.filter;

import com.tw.blog.service.ICustService;
import com.tw.blog.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * by TanWei 2021/1/13
 **/
@Slf4j
public class CheckToken implements HandlerInterceptor {

    @Autowired
    private ICustService custService;

    @Override //进入Controller之前执行该方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录拦截的业务逻辑
        String token = request.getHeader("Authorization");
        log.info("----CheckToken-----" + request.getRequestURI() + "----" + token);
        if(StringUtils.isNull(token)){
            response.setStatus(401);
            return false;
        }
        boolean flag = custService.checkToken(token);
        if(flag){
            return true;
        }else {
            response.setStatus(401);
            return false;
        }
    }
}

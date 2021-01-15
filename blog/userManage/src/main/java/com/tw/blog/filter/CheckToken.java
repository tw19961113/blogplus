package com.tw.blog.filter;

import com.tw.blog.service.ICustService;
import com.tw.blog.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
        //解决跨域问题 preHandle返回false后会终止请求，也就是说后面配置的跨域方法不起作用了，这
        // 也就造成了前端的跨域问题。所以需要在拦截器中配置跨域请求才能解决。
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
//        response.addHeader("Access-Control-Max-Age", "3600");

        if(StringUtils.isNull(token)){
            return false;
        }
        boolean flag = custService.checkToken(token);
        if(flag){
            return true;
        }else {
            return false;
        }
    }
}

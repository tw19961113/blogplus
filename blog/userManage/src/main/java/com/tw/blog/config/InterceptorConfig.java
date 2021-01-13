package com.tw.blog.config;

import com.tw.blog.filter.CheckToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * by TanWei 2021/1/13
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需要拦截的路径，/**表示需要拦截所有请求
        String[]addPathPatterns={"/**"};
        //不需要拦截的路径
        String [] excludePathPaterns={
                "/doLogin",
                "/getVerification"
        };
        //注册token检查拦截器
        registry.addInterceptor(new CheckToken())
                .addPathPatterns(addPathPatterns)
                .excludePathPatterns(excludePathPaterns);
    }
}

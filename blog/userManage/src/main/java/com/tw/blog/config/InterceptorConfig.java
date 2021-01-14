package com.tw.blog.config;

import com.tw.blog.filter.CheckToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * by TanWei 2021/1/13
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 讲拦截器交给spring容器管理，否在无法注入service
     * @return
     */
    @Bean
    public CheckToken checkToken(){
        return new CheckToken();
    }

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
        registry.addInterceptor(checkToken())
                .addPathPatterns(addPathPatterns)
                .excludePathPatterns(excludePathPaterns);
    }
}

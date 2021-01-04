package com.tw.blog;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * by TanWei 2021/1/3
 **/
@SpringBootApplication
@EnableEurekaServer
public class AppMain {
    public static void main(String[] args) {

        SpringApplication.run(AppMain.class,args);
    }
}

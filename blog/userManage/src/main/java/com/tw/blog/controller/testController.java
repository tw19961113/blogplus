package com.tw.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * by TanWei 2021/1/5
 **/
@RestController
public class testController {

    @RequestMapping("/hello")
    public String test(){
        return "Hello";
    }
}

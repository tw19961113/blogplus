package com.tw.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.tw.blog.pojo.TUser;
import com.tw.blog.service.IUserService;
import com.tw.blog.utils.RespBean;
import com.tw.blog.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * by TanWei 2021/1/6
 **/
@RestController
@Slf4j
public class UserController {

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/doLogin")
    public JSONObject doLogin(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello","oo");
        return jsonObject;
    }

    /**
     * 获取图片验证码
     * @return
     */
    @RequestMapping("/getVerification")
    public void getVerification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("------------------获取验证码--------------------");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 存入会话session
        HttpSession session = request.getSession(true);
        // 删除以前的
        session.removeAttribute("verCode");
        session.removeAttribute("codeTime");
        session.setAttribute("verCode", verifyCode.toLowerCase());
        session.setAttribute("codeTime", System.currentTimeMillis());
        // 生成图片
        int w = 100, h = 30;
        OutputStream out = response.getOutputStream();
        VerifyCodeUtils.outputImage(w, h, out, verifyCode);

    }
}

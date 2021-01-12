package com.tw.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.tw.blog.pojo.TCust;
import com.tw.blog.service.ICustService;
import com.tw.blog.utils.Constant;
import com.tw.blog.utils.RedisUtil;
import com.tw.blog.utils.RespBean;
import com.tw.blog.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * by TanWei 2021/1/6
 **/
@RestController
@Slf4j
public class UserController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ICustService custService;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/doLogin")
    public RespBean doLogin(@RequestBody JSONObject jsonObject){
        log.info("----用户登录，入参：",String.valueOf(jsonObject));
        String verificationCode = jsonObject.getString("verificationCode");//验证码
        //首先判断验证码
        String codeTime = redisUtil.get("codeTime"+verificationCode.toLowerCase());
        if("".equals(codeTime)) return RespBean.buildResult(Constant.ERROR.getCode(),"验证码已过期");
        long time = System.currentTimeMillis() - Long.parseLong(codeTime);
        if(time > 1000*120){
            //如果时间超过两分钟，则提示验证码过期
            return RespBean.buildResult(Constant.ERROR.getCode(),"验证码已过期");
        }else if(!verificationCode.equalsIgnoreCase(redisUtil.get("verCode"+verificationCode.toLowerCase()))){
            return RespBean.buildResult(Constant.ERROR.getCode(),"验证码错误");
        }else {
            String userName = jsonObject.getString("userName");
            TCust tCust = custService.selectUserByUserName(userName);
            if(tCust == null){
                return RespBean.buildResult(Constant.ERROR.getCode(),"用户不存在");
            }else {
                String password = jsonObject.getString("password");
                if (!password.equals(tCust.getPasswd())){
                    return RespBean.buildResult(Constant.ERROR.getCode(),"用户名或者密码错误");
                }else {
                    //验证通过
                    //生成token
                    String token = String.valueOf(UUID.randomUUID());
                    //存入redis
                    redisUtil.set("token"+userName,token);
                    return RespBean.buildResult(Constant.SUCCESS.getCode(),"登陆成功", token);
                }
            }
        }
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
        // 存入redis
        // 删除以前的
        redisUtil.delete("verCode");
        redisUtil.delete("codeTime");

        redisUtil.set("verCode"+verifyCode.toLowerCase(),verifyCode.toLowerCase());
        redisUtil.set("codeTime"+verifyCode.toLowerCase(),String.valueOf(System.currentTimeMillis()));
        // 生成图片
        int w = 100, h = 30;
        OutputStream out = response.getOutputStream();
        VerifyCodeUtils.outputImage(w, h, out, verifyCode);
    }
}

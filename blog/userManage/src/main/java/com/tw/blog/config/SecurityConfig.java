package com.tw.blog.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tw.blog.pojo.TUser;
import com.tw.blog.service.SysUserService;
import com.tw.blog.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SysUserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();  //指定加密算法, 在springsecurity中密码必须要加密认证！
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.usernameParameter("username")
			.passwordParameter("password")
			.loginProcessingUrl("/doLogin")
			//.loginPage("/login")  //指定登录页，不设置框架提供测试页
			.successHandler(new AuthenticationSuccessHandler() {

				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {

					response.setContentType("application/json;charset=UTF-8");

					PrintWriter out = response.getWriter();

					//获取用户对象的信息
					TUser user = (TUser) authentication.getPrincipal();
					RespBean respBean = RespBean.ok("登录成功",user);

					//java对象->json字符串
					String json = new ObjectMapper().writeValueAsString(respBean);
					System.out.println(json);

					out.write(json);
					out.flush();
					out.close();
				}
			})
			.failureHandler(new AuthenticationFailureHandler() {

				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					response.setContentType("application/json;charset=UTF-8");
					PrintWriter out = response.getWriter();

					RespBean respBean = RespBean.error("登录失败");
					//判断异常的类型？
					if (exception instanceof LockedException) {
						respBean.setMsg("帐号被锁定");
					} else if(exception instanceof CredentialsExpiredException) {
						respBean.setMsg("密码过期");
					} else if(exception instanceof DisabledException) {
						respBean.setMsg("帐号被禁用");
					} else if(exception instanceof BadCredentialsException) {
						respBean.setMsg("帐号或密码错误");
					}

					out.write(new ObjectMapper().writeValueAsString(respBean));
					out.flush();
					out.close();
				}
			})
			.permitAll()
			.and()
			.logout()
			.logoutSuccessHandler(new LogoutSuccessHandler() {

				@Override
				public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
						throws IOException, ServletException {
					// TODO Auto-generated method stub

				}
			})
			.permitAll()
			.and()
			.csrf().disable();
	}
}

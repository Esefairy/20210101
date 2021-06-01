package com.culture.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity //拦截所有请求 AOP拦截器
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启细粒度控制 判断用户对某个控制层的方法是否具有访问权限 @PreAuthorize
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

    @Autowired
    MyAuthenctiationFailHandler myAuthenctiationFailHandler;


    //授权
    //配置拦截资源 首页所有人可以访问 功能页只有对应有权限的人才能访问 链式编程
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //请求的规则
        http.authorizeRequests()
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui.html/**",
                        "swagger/**",
                        "swagger-ui.html",
                        "/webjars/**",
                        "/swagger-ui.html/*",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/webjars/springfox-swagger-ui/**",
                        "/v2/api-docs",
                        "/META-INF/resources/webjars/**",
                        "/static/upload/**",
                        "/static/upload/culture/**",
                        "/static/upload/avatar/**",
                        "/static/**",//资源放行
                        "/static/index/**",//资源放行
                        "/static/admin/**",//资源放行
                        "/showFmImg/**",//展示封面
                        "/showimage/**",//展示头像
                        "/file/uploadFile",//文件上传,

                        "/index/**",
                        "/admin/**",

                        "/login",
                        "/signup",
                        "/logout",
                        "/index",
                        "/",
                        "/culture",
                        "/culture/index",
                        "/culture/listpage",
                        "/cultureIndex/listpage",
                        "/culture/detail",
                        "/culture/like/*",
                        "/culture/cancel/*",
                        "/sentence",
                        "/about",
                        "/detail",
                        "/center",

                        "/in",
                        "/druid",
                        "/druid/**",
                        "/druid/*",
                        "/druid/login.html",
                        "/toLogin")//后台登录
                .permitAll()//所有人都能访问
                .anyRequest().authenticated();
        //没有权限 开启登录页面
        http.formLogin().loginPage("/") //登录页
                .usernameParameter("username").passwordParameter("password")//设置表单用户名和密码
                .loginProcessingUrl("/login")//登录表单请求
                .successHandler(myAuthenctiationSuccessHandler)//登录成功
                .failureHandler(myAuthenctiationFailHandler);//登录失败

        http.csrf().disable();//关闭csrf
//        http.ignoringAntMatchers("/druid/**");
        http.headers()
                // 关闭 X-Content-Type-Options:nosniff ，使 Druid 页面可以正常显示
                .contentTypeOptions().disable()
                .and();
        http.logout().logoutSuccessUrl("/")
                .invalidateHttpSession(true);//注销成功去首页
        //权限不够返回处理
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest req, HttpServletResponse resp,
                               AccessDeniedException e) throws IOException, ServletException {
                String type = req.getHeader("X-Requested-With");
                if ("XMLHttpRequest".equals(type)) {
                    resp.getWriter().print("403");
                } else {
                    req.getRequestDispatcher("/error403").forward(req, resp);
                }
            }
        });

        //开启记住我 cookie的实现
        http.rememberMe().rememberMeParameter("remember-me");
    }

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

//    $2a$10$YITYi7HjqT2gh8jEF6eyquR/Og0qmYBNT8cQLaEjjS92jcZHwsI9G
//    $2a BCrypt算法版本  $10 算法强度  $YITYi7HjqT2gh8jEF6eyquR 随机生成盐  Og0qmYBNT8cQLaEjjS92jcZHwsI9G hash值
//    public static void main(String[] args) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode("123");
//        System.out.println(encode);
//    }


}


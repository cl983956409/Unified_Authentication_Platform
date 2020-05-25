package com.server.auth.config;

import com.server.auth.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @param
 * @author 程龙[chenglonghy]
 * @return
 * @date 2020/5/15 16:35
 * @history 2020/5/15 16:35 程龙[chenglonghy]  create.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    UserServiceImpl userService;

    /**
     * 登录认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
    ///**
    // * 登录认证
    // */
    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.inMemoryAuthentication()
    //            .withUser("sang")
    //            .password(new BCryptPasswordEncoder().encode("123"))
    //            .roles("admin")
    //            .and()
    //            .withUser("javaboy")
    //            .password(new BCryptPasswordEncoder().encode("123"))
    //            .roles("user");
    //}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin();
    }

}

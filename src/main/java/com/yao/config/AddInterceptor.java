package com.yao.config;

import com.yao.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AddInterceptor extends WebMvcConfigurerAdapter {

    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    //所有的WebMvcConfigurerAdapter组件都会一起起作用
    @Bean //将组件注册在容器
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {


            @Override
            public void addInterceptors(InterceptorRegistry registry) {

                registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
            }
        };
        return adapter;
    }
}

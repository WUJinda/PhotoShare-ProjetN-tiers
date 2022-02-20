package com.example.imageserver.intercptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class Config extends WebMvcConfigurerAdapter {

    @Autowired
    LoginIntercprot loginIntercprot;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntercprot)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/add",
                        "/image/upload",
                        "/static/**",
                        "/upload/**"
                );
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "uploads/");
    }
}

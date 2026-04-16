package com.qqsystem.serve.config;

import com.qqsystem.serve.interceptor.BrowseInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private BrowseInterceptor browseInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册浏览行为拦截器
        registry.addInterceptor(browseInterceptor)
                .addPathPatterns("/drama/detail/**", "/news/detail/**", "/actor/detail/**", "/culture/detail/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源映射，将/drama/**路径映射到upload/drama目录
        registry.addResourceHandler("/drama/**")
                .addResourceLocations("file:///D:/qin-opera-promotion-system/upload/drama/");
    }

}

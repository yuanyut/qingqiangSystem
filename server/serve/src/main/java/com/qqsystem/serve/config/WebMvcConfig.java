package com.qqsystem.serve.config;

import com.qqsystem.serve.interceptor.BrowseInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.Resource;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private BrowseInterceptor browseInterceptor;

    @Resource
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                converters.set(i, mappingJackson2HttpMessageConverter);
                return;
            }
        }
        converters.add(0, mappingJackson2HttpMessageConverter);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173","http://localhost:5174")
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
        String basePath = System.getProperty("user.dir") + "/serve/src/main/resources/upload/";

        registry.addResourceHandler("/upload/avatar/**")
                .addResourceLocations("file:///" + basePath + "avatar/");
        registry.addResourceHandler("/upload/actor/**")
                .addResourceLocations("file:///" + basePath + "actor/");
        registry.addResourceHandler("/upload/drama/**")
                .addResourceLocations("file:///" + basePath + "drama/");
        registry.addResourceHandler("/upload/culture/**")
                .addResourceLocations("file:///" + basePath + "culture/");
    }

}

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
import java.io.File;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private BrowseInterceptor browseInterceptor;

    @Resource
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Resource
    private UploadConfig uploadConfig;

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
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(browseInterceptor)
                .addPathPatterns("/drama/detail/**", "/news/detail/**", "/actor/detail/**", "/culture/detail/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 使用绝对路径，避免工作目录变化导致静态资源无法访问
        String basePath = uploadConfig.getAbsolutePath();
        
        String urlPrefix = uploadConfig.getUrlPrefix();
        if (!urlPrefix.endsWith("/")) {
            urlPrefix += "/";
        }

        String[] subDirs = {"avatar", "actor", "drama", "culture"};
        
        for (String dir : subDirs) {
            String resourceLocation = basePath + File.separator + dir + File.separator;
            File resourceDir = new File(resourceLocation);
            if (!resourceDir.exists()) {
                resourceDir.mkdirs();
            }
            
            String handlerPath = urlPrefix + dir + "/**";
            String location = "file:" + resourceLocation;
            System.out.println("========== 静态资源映射配置 ==========");
            System.out.println("请求路径: " + handlerPath);
            System.out.println("文件位置: " + location);
            
            registry.addResourceHandler(handlerPath)
                    .addResourceLocations(location);
        }
    }

}

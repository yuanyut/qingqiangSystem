package com.qqsystem.serve.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ConfigurationProperties(prefix = "app.upload")
public class UploadConfig {

    private String path = "./upload";
    private String urlPrefix = "/upload";
    private boolean useFullUrl = false;
    private String absolutePath;

    @PostConstruct
    public void init() {
        // 将相对路径转换为绝对路径，避免工作目录变化导致路径错误
        Path p = Paths.get(path).toAbsolutePath().normalize();
        this.absolutePath = p.toString();
        System.out.println("========== UploadConfig 初始化 ==========");
        System.out.println("配置路径: " + path);
        System.out.println("解析后的绝对路径: " + absolutePath);
        
        // 确保目录存在
        File dir = new File(absolutePath);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("创建上传目录: " + absolutePath);
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public boolean isUseFullUrl() {
        return useFullUrl;
    }

    public void setUseFullUrl(boolean useFullUrl) {
        this.useFullUrl = useFullUrl;
    }

    public String getFullPath(String subPath) {
        return absolutePath + (absolutePath.endsWith(File.separator) ? "" : File.separator) + subPath;
    }
}
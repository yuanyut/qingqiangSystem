package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.config.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload/debug")
public class UploadDebugController {

    @Autowired
    private UploadConfig uploadConfig;

    @GetMapping("/info")
    public ResponseResult<Map<String, Object>> getUploadInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("configPath", uploadConfig.getPath());
        info.put("absolutePath", uploadConfig.getAbsolutePath());
        info.put("urlPrefix", uploadConfig.getUrlPrefix());
        info.put("useFullUrl", uploadConfig.isUseFullUrl());
        
        String[] subDirs = {"avatar", "actor", "drama", "culture"};
        for (String dir : subDirs) {
            String dirPath = uploadConfig.getFullPath(dir);
            File dirFile = new File(dirPath);
            info.put("dir_" + dir + "_exists", dirFile.exists());
            info.put("dir_" + dir + "_path", dirPath);
            if (dirFile.exists() && dirFile.isDirectory()) {
                File[] files = dirFile.listFiles();
                info.put("dir_" + dir + "_fileCount", files != null ? files.length : 0);
            }
        }
        
        return ResponseResult.success(info);
    }

    @GetMapping("/file-exists")
    public ResponseResult<Map<String, Object>> checkFileExists(@RequestParam String type, @RequestParam String filename) {
        Map<String, Object> result = new HashMap<>();
        
        String filePath = uploadConfig.getFullPath(type) + File.separator + filename;
        File file = new File(filePath);
        
        result.put("requestedType", type);
        result.put("requestedFilename", filename);
        result.put("fullFilePath", filePath);
        result.put("fileExists", file.exists());
        result.put("isFile", file.isFile());
        if (file.exists()) {
            result.put("fileSize", file.length());
        }
        
        return ResponseResult.success(result);
    }
}

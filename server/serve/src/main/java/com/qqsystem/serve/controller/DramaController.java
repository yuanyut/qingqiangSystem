package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.entity.Drama;
import com.qqsystem.serve.entity.DramaCategory;
import com.qqsystem.serve.service.DramaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/drama")
public class DramaController {

    @Resource
    private DramaService dramaService;

@Value("${app.domain:http://localhost:8081}")
    private String domain;

    @GetMapping("/list")
    public ResponseResult<Map<String, Object>> list(@RequestParam int page,
                                    @RequestParam int size,
                                    @RequestParam(required = false) Long categoryId,
                                    @RequestParam(required = false) String keyword) {

        Map<String, Object> res = new HashMap<>();
        res.put("list", dramaService.pageListWithRelation(page, size, categoryId, keyword, false));
        res.put("total", dramaService.countList(categoryId, keyword, false));
        return ResponseResult.success(res);
    }

    @GetMapping("/admin/list")
    public ResponseResult<Map<String, Object>> adminList(@RequestParam int page,
                                    @RequestParam int size,
                                    @RequestParam(required = false) Long categoryId,
                                    @RequestParam(required = false) String keyword) {

        Map<String, Object> res = new HashMap<>();
        res.put("list", dramaService.pageListWithRelation(page, size, categoryId, keyword, true));
        res.put("total", dramaService.countList(categoryId, keyword, true));
        return ResponseResult.success(res);
    }

    // ⭐ 普通详情
    @GetMapping("/detail/{id}")
    public ResponseResult<Drama> detail(@PathVariable Long id) {
        Drama drama = dramaService.getById(id);
        if (drama != null) {
            return ResponseResult.success(drama);
        } else {
            return ResponseResult.badRequest("剧目不存在");
        }
    }

    // ⭐ 增强详情（核心）
    @GetMapping("/detail/full/{id}")
    public ResponseResult<Drama> detailFull(@PathVariable Long id) {
        Drama drama = dramaService.getDetailWithRelation(id);
        if (drama != null) {
            return ResponseResult.success(drama);
        } else {
            return ResponseResult.badRequest("剧目不存在");
        }
    }

    @PostMapping("/add")
    public ResponseResult<?> add(@RequestBody Drama drama) {
        boolean success = dramaService.add(drama);
        if (success) {
            return ResponseResult.success("添加成功");
        } else {
            return ResponseResult.badRequest("添加失败");
        }
    }

    @PutMapping("/update")
    public ResponseResult<?> update(@RequestBody Drama drama) {
        boolean success = dramaService.update(drama);
        if (success) {
            return ResponseResult.success("更新成功");
        } else {
            return ResponseResult.badRequest("更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> delete(@PathVariable Long id) {
        boolean success = dramaService.delete(id);
        if (success) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.badRequest("删除失败");
        }
    }

    @DeleteMapping("/batch")
    public ResponseResult<?> batchDelete(@RequestBody List<Long> ids) {
        boolean success = dramaService.batchDelete(ids);
        if (success) {
            return ResponseResult.success("批量删除成功");
        } else {
            return ResponseResult.badRequest("批量删除失败");
        }
    }
    
    @GetMapping("/category/list")
    public ResponseResult<List<DramaCategory>> categoryList() {
        List<DramaCategory> categories = dramaService.getCategoryList();
        return ResponseResult.success(categories);
    }
    
    @PostMapping("/upload")
    public ResponseResult<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseResult.badRequest("文件为空");
        }

        String uploadDir = System.getProperty("user.dir") + "/serve/src/main/resources/upload/drama";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        String filePath = uploadDir + File.separator + fileName;

        try {
            file.transferTo(new File(filePath));

            Map<String, String> result = new HashMap<>();
            result.put("url", domain + "/upload/drama/" + fileName);
            return ResponseResult.success(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.badRequest("上传失败");
        }
    }
}
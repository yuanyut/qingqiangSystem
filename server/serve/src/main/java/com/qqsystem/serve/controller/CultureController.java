package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.entity.Content;
import com.qqsystem.serve.service.ContentService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/culture")
public class CultureController {

    @Resource
    private ContentService contentService;

    @GetMapping("/list")
    public ResponseResult<Map<String, Object>> list(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String category) {
        Map<String, Object> res = new HashMap<>();
        List<Content> cultureList = contentService.getCultureList(page, size, category);
        Long total = contentService.countCultureList(category);
        res.put("list", cultureList);
        res.put("total", total);
        return ResponseResult.success(res);
    }

    @GetMapping("/detail/{id}")
    public ResponseResult<Content> detail(@PathVariable Long id) {
        Content content = contentService.getCultureDetail(id);
        if (content != null) {
            return ResponseResult.success(content);
        } else {
            return ResponseResult.badRequest("文化内容不存在");
        }
    }

    @PostMapping("/add")
    public ResponseResult<?> add(@RequestBody Content content) {
        boolean success = contentService.addCulture(content);
        if (success) {
            return ResponseResult.success("添加成功");
        } else {
            return ResponseResult.badRequest("添加失败");
        }
    }

    @PutMapping("/update")
    public ResponseResult<?> update(@RequestBody Content content) {
        boolean success = contentService.updateCulture(content);
        if (success) {
            return ResponseResult.success("更新成功");
        } else {
            return ResponseResult.badRequest("更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> delete(@PathVariable Long id) {
        boolean success = contentService.deleteCulture(id);
        if (success) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.badRequest("删除失败");
        }
    }
}

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
@RequestMapping("/content/culture")
public class ContentController {

    @Resource
    private ContentService contentService;

    // 获取文化资讯分类统计
    @GetMapping("/categories")
    public ResponseResult<List<Map<String, Object>>> getCategories() {
        List<Map<String, Object>> categories = contentService.getCultureCategories();
        return ResponseResult.success(categories);
    }

    // 获取头条资讯
    @GetMapping("/top")
    public ResponseResult<Content> getTop() {
        Content topContent = contentService.getTopCulture();
        if (topContent != null) {
            return ResponseResult.success(topContent);
        } else {
            return ResponseResult.badRequest("暂无头条资讯");
        }
    }

    // 获取最新资讯分页列表
    @GetMapping("/list")
    public ResponseResult<Map<String, Object>> getList(@RequestParam int page, @RequestParam int size) {
        Map<String, Object> res = new HashMap<>();
        List<Content> contentList = contentService.getCultureList(page, size, null, null, 0);
        Long total = contentService.countCultureList(null, null, 0);
        res.put("list", contentList);
        res.put("total", total);
        return ResponseResult.success(res);
    }

    // 管理员获取文化内容列表（支持分页、搜索）
    @GetMapping("/admin/list")
    public ResponseResult<Map<String, Object>> getAdminList(@RequestParam int page, 
                                                           @RequestParam int size, 
                                                           @RequestParam(required = false) String category, 
                                                           @RequestParam(required = false) String keyword,
                                                           @RequestParam(required = false) Integer status) {
        Map<String, Object> res = new HashMap<>();
        List<Content> contentList = contentService.getCultureList(page, size, category, keyword, status);
        Long total = contentService.countCultureList(category, keyword, status);
        res.put("list", contentList);
        res.put("total", total);
        return ResponseResult.success(res);
    }

    // 获取文化内容详情
    @GetMapping("/detail/{id}")
    public ResponseResult<Content> getDetail(@PathVariable Long id) {
        Content content = contentService.getCultureDetail(id);
        if (content != null) {
            return ResponseResult.success(content);
        } else {
            return ResponseResult.badRequest("内容不存在");
        }
    }

    // 新增文化内容
    @PostMapping("/add")
    public ResponseResult<?> add(@RequestBody Content content) {
        // 设置业务类型为文化
        content.setBizType("culture");
        // 设置内容类型为文章
        content.setType("article");
        boolean success = contentService.addCulture(content);
        if (success) {
            return ResponseResult.success("添加成功");
        } else {
            return ResponseResult.badRequest("添加失败");
        }
    }

    // 更新文化内容
    @PutMapping("/update")
    public ResponseResult<?> update(@RequestBody Content content) {
        boolean success = contentService.updateCulture(content);
        if (success) {
            return ResponseResult.success("更新成功");
        } else {
            return ResponseResult.badRequest("更新失败");
        }
    }

    // 删除文化内容
    @DeleteMapping("/{id}")
    public ResponseResult<?> delete(@PathVariable Long id) {
        boolean success = contentService.deleteCulture(id);
        if (success) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.badRequest("删除失败");
        }
    }

    // 批量删除文化内容
    @DeleteMapping("/batch")
    public ResponseResult<?> batchDelete(@RequestBody List<Long> ids) {
        boolean allSuccess = true;
        for (Long id : ids) {
            if (!contentService.deleteCulture(id)) {
                allSuccess = false;
            }
        }
        if (allSuccess) {
            return ResponseResult.success("批量删除成功");
        } else {
            return ResponseResult.badRequest("部分删除失败");
        }
    }

    // 获取热门排行
    @GetMapping("/hot")
    public ResponseResult<List<Content>> getHot(@RequestParam int size) {
        List<Content> hotList = contentService.getHotCulture(size);
        return ResponseResult.success(hotList);
    }

    // 获取推荐阅读
    @GetMapping("/recommend")
    public ResponseResult<List<Content>> getRecommend(@RequestParam int size) {
        List<Content> recommendList = contentService.getRecommendCulture(size);
        return ResponseResult.success(recommendList);
    }

    // 获取媒体聚焦（视频类）
    @GetMapping("/media")
    public ResponseResult<List<Content>> getMedia(@RequestParam int size) {
        List<Content> mediaList = contentService.getMediaCulture(size);
        return ResponseResult.success(mediaList);
    }
}

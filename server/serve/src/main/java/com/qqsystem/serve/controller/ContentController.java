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
        List<Content> contentList = contentService.getCultureList(page, size, null);
        Long total = contentService.countCultureList(null);
        res.put("list", contentList);
        res.put("total", total);
        return ResponseResult.success(res);
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

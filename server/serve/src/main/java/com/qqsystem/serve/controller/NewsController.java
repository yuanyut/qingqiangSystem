package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.entity.News;
import com.qqsystem.serve.service.NewsService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @GetMapping("/list")
    public ResponseResult<Map<String, Object>> list(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) Integer category, @RequestParam(required = false) String keyword) {
        Map<String, Object> res = new HashMap<>();
        res.put("list", newsService.pageList(page, size, category, keyword));
        res.put("total", newsService.countList(category, keyword));
        return ResponseResult.success(res);
    }

    @GetMapping("/detail/{id}")
    public ResponseResult<News> detail(@PathVariable Long id) {
        News news = newsService.getById(id);
        if (news != null) {
            return ResponseResult.success(news);
        } else {
            return ResponseResult.badRequest("新闻不存在");
        }
    }

    @PostMapping("/add")
    public ResponseResult<?> add(@RequestBody News news) {
        boolean success = newsService.add(news);
        if (success) {
            return ResponseResult.success("添加成功");
        } else {
            return ResponseResult.badRequest("添加失败");
        }
    }

    @PutMapping("/update")
    public ResponseResult<?> update(@RequestBody News news) {
        boolean success = newsService.update(news);
        if (success) {
            return ResponseResult.success("更新成功");
        } else {
            return ResponseResult.badRequest("更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseResult<?> delete(@PathVariable Long id) {
        boolean success = newsService.delete(id);
        if (success) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.badRequest("删除失败");
        }
    }

    // 获取新闻分类统计
    @GetMapping("/categories")
    public ResponseResult<List<Map<String, Object>>> getCategories() {
        List<Map<String, Object>> categories = newsService.getNewsCategories();
        return ResponseResult.success(categories);
    }

    // 获取头条资讯
    @GetMapping("/top")
    public ResponseResult<News> getTop() {
        News topNews = newsService.getTopNews();
        if (topNews != null) {
            return ResponseResult.success(topNews);
        } else {
            return ResponseResult.badRequest("暂无头条资讯");
        }
    }

    // 获取热门排行
    @GetMapping("/hot")
    public ResponseResult<List<News>> getHot(@RequestParam int size) {
        List<News> hotList = newsService.getHotNews(size);
        return ResponseResult.success(hotList);
    }

    // 获取推荐阅读
    @GetMapping("/recommend")
    public ResponseResult<List<News>> getRecommend(@RequestParam int size, @RequestParam(required = false) String category) {
        List<News> recommendList = newsService.getRecommendNews(size, category);
        return ResponseResult.success(recommendList);
    }

    // 获取媒体聚焦（视频类）
    @GetMapping("/media")
    public ResponseResult<List<News>> getMedia(@RequestParam int size) {
        List<News> mediaList = newsService.getMediaNews(size);
        return ResponseResult.success(mediaList);
    }
}

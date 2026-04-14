package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.entity.News;
import com.qqsystem.serve.service.NewsService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @GetMapping("/list")
    public ResponseResult<Map<String, Object>> list(@RequestParam int page, @RequestParam int size) {
        Map<String, Object> res = new HashMap<>();
        res.put("list", newsService.pageList(page, size));
        res.put("total", newsService.countList());
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
}

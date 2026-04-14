package com.qqsystem.serve.controller;

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
    public Map<String, Object> list(@RequestParam int page, @RequestParam int size) {
        Map<String, Object> res = new HashMap<>();
        res.put("list", newsService.pageList(page, size));
        res.put("total", newsService.countList());
        return res;
    }

    @GetMapping("/detail/{id}")
    public News detail(@PathVariable Long id) {
        return newsService.getById(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody News news) {
        return newsService.add(news);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody News news) {
        return newsService.update(news);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return newsService.delete(id);
    }
}

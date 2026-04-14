package com.qqsystem.serve.controller;

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
    public Map<String, Object> list(@RequestParam int page, @RequestParam int size) {
        Map<String, Object> res = new HashMap<>();
        List<Content> cultureList = contentService.getCultureList(page, size);
        Long total = contentService.countCultureList();
        res.put("list", cultureList);
        res.put("total", total);
        return res;
    }

    @GetMapping("/detail/{id}")
    public Content detail(@PathVariable Long id) {
        return contentService.getCultureDetail(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Content content) {
        return contentService.addCulture(content);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Content content) {
        return contentService.updateCulture(content);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return contentService.deleteCulture(id);
    }
}

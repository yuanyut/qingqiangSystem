package com.qqsystem.serve.controller;

import com.qqsystem.serve.entity.Drama;
import com.qqsystem.serve.service.DramaService;
import com.qqsystem.serve.service.impl.DramaServiceImpl;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/drama")
public class DramaController {

    @Resource
    private DramaService dramaService;

    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam int page,
                                    @RequestParam int size,
                                    @RequestParam(required = false) Long categoryId) {

        Map<String, Object> res = new HashMap<>();
        res.put("list", dramaService.pageList(page, size, categoryId));
        res.put("total", dramaService.countList(categoryId));
        return res;
    }

    // ⭐ 普通详情
    @GetMapping("/detail/{id}")
    public Drama detail(@PathVariable Long id) {
        return dramaService.getById(id);
    }

    // ⭐ 增强详情（核心）
    @GetMapping("/detail/full/{id}")
    public Drama detailFull(@PathVariable Long id) {
        return ((DramaServiceImpl) dramaService).getDetailWithRelation(id);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Drama drama) {
        return dramaService.add(drama);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Drama drama) {
        return dramaService.update(drama);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return dramaService.delete(id);
    }
}
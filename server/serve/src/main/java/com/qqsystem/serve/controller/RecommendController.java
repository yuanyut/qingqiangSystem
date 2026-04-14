package com.qqsystem.serve.controller;

import com.qqsystem.serve.entity.RecommendItem;
import com.qqsystem.serve.service.RecommendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Resource
    private RecommendService recommendService;

    // 热门推荐
    @GetMapping("/hot")
    public List<RecommendItem> getHotRecommendations() {
        return recommendService.getHotRecommendations();
    }

    // 协同过滤推荐
    @GetMapping("/cf")
    public List<RecommendItem> getCollaborativeFilteringRecommendations(@RequestParam Long userId) {
        return recommendService.getCollaborativeFilteringRecommendations(userId);
    }
}

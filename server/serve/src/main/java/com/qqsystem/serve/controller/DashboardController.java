package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/overview")
    public ResponseResult getOverview() {
        return ResponseResult.success(dashboardService.getOverview());
    }

    @GetMapping("/drama-category")
    public ResponseResult getDramaCategory() {
        return ResponseResult.success(dashboardService.getDramaCategory());
    }

    @GetMapping("/content-trend")
    public ResponseResult getContentTrend(@RequestParam(defaultValue = "day") String type) {
        return ResponseResult.success(dashboardService.getContentTrend(type));
    }

    @GetMapping("/behavior-trend")
    public ResponseResult getBehaviorTrend(@RequestParam(defaultValue = "day") String type) {
        return ResponseResult.success(dashboardService.getBehaviorTrend(type));
    }

    @GetMapping("/drama-top10")
    public ResponseResult getDramaTop10() {
        return ResponseResult.success(dashboardService.getDramaTop10());
    }

    @GetMapping("/quality-score")
    public ResponseResult getQualityScore() {
        return ResponseResult.success(dashboardService.getQualityScore());
    }

    @GetMapping("/geo")
    public ResponseResult getGeoData() {
        return ResponseResult.success(dashboardService.getGeoData());
    }

    @GetMapping("/user-age-distribution")
    public ResponseResult getUserAgeDistribution() {
        return ResponseResult.success(dashboardService.getUserAgeDistribution());
    }
}
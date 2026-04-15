package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.mapper.BehaviorMapper;
import com.qqsystem.serve.mapper.ContentMapper;
import com.qqsystem.serve.mapper.DailyStatsMapper;
import com.qqsystem.serve.mapper.DramaMapper;
import com.qqsystem.serve.mapper.UserMapper;
import com.qqsystem.serve.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DailyStatsMapper dailyStatsMapper;
    @Autowired
    private DramaMapper dramaMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private BehaviorMapper behaviorMapper;

    @Override
    public Map<String, Object> getOverview() {
        Map<String, Object> result = new HashMap<>();
        
        // 获取当天日期
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // 统计各项数据
        Integer todayVisit = dailyStatsMapper.countPvByDate(today);
        Integer pv = dailyStatsMapper.countPvByDate(today);
        Integer uv = dailyStatsMapper.countUvByDate(today);
        Integer dramaCount = dailyStatsMapper.countDrama();
        Integer userTotal = dailyStatsMapper.countTotalUser();
        
        // 处理当天记录不存在的情况，返回0
        result.put("todayVisit", todayVisit != null ? todayVisit : 0);
        result.put("pv", pv != null ? pv : 0);
        result.put("uv", uv != null ? uv : 0);
        result.put("dramaCount", dramaCount != null ? dramaCount : 0);
        result.put("userTotal", userTotal != null ? userTotal : 0);
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getDramaCategory() {
        return dramaMapper.countByCategory();
    }

    @Override
    public List<Map<String, Object>> getContentTrend(String type) {
        // 根据type参数确定查询的天数
        int days = 7; // 默认查询7天
        if ("week".equals(type)) {
            days = 14;
        } else if ("month".equals(type)) {
            days = 30;
        }
        // 查询内容新增趋势数据
        return contentMapper.selectContentTrend(type, days);
    }

    @Override
    public List<Map<String, Object>> getBehaviorTrend(String type) {
        // 根据type参数确定查询的天数
        int days = 7; // 默认查询7天
        if ("week".equals(type)) {
            days = 14;
        } else if ("month".equals(type)) {
            days = 30;
        }
        // 查询行为趋势数据
        return behaviorMapper.selectBehaviorTrend(type, days);
    }

    @Override
    public List<Map<String, Object>> getDramaTop10() {
        // 查询剧目TOP10数据
        return dramaMapper.selectDramaTop10();
    }

    @Override
    public Map<String, Object> getQualityScore() {
        // 实现内容质量维度查询
        Map<String, Object> result = new HashMap<>();
        // 这里可以根据实际需求计算内容质量评分
        // 例如：基于浏览量、点赞数、评论数等指标
        result.put("averageScore", 85);
        result.put("totalContent", 100);
        result.put("highQuality", 60);
        result.put("mediumQuality", 30);
        result.put("lowQuality", 10);
        return result;
    }

    @Override
    public List<Map<String, Object>> getGeoData() {
        // 查询用户所在省份统计数据
        return userMapper.selectUserProvinceStats();
    }
}
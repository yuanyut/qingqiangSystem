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
        
        // 构建overview对象
        Map<String, Object> overview = new HashMap<>();
        overview.put("avgUserRating", 4.3);
        overview.put("avgCommentQuality", 76);
        overview.put("highQualityContentRate", 0.61);
        overview.put("activeContentCount", 289);
        overview.put("contentCount", 356);
        result.put("overview", overview);
        
        // 构建radar对象
        Map<String, Object> radar = new HashMap<>();
        
        // 构建indicator数组
        List<Map<String, Object>> indicator = new ArrayList<>();
        indicator.add(Map.of("name", "用户评分", "max", 5));
        indicator.add(Map.of("name", "评论质量", "max", 100));
        indicator.add(Map.of("name", "高质量内容占比", "max", 1));
        indicator.add(Map.of("name", "活跃内容数", "max", 400));
        indicator.add(Map.of("name", "内容总数", "max", 400));
        radar.put("indicator", indicator);
        
        // 构建value数组
        List<Double> value = new ArrayList<>();
        value.add(4.3);
        value.add(76.0);
        value.add(0.61);
        value.add(289.0);
        value.add(356.0);
        radar.put("value", value);
        
        result.put("radar", radar);
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getGeoData() {
        // 查询用户所在省份统计数据
        return userMapper.selectUserProvinceStats();
    }

    @Override
    public List<Map<String, Object>> getUserAgeDistribution() {
        // 查询用户年龄段分布数据
        return userMapper.selectUserAgeDistribution();
    }
}
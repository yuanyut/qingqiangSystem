package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.DailyStats;
import com.qqsystem.serve.mapper.DailyStatsMapper;
import com.qqsystem.serve.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DailyStatsMapper dailyStatsMapper;

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
}
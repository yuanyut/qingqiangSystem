package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.DailyStats;

public interface DailyStatsMapper {
    void insertOrUpdate(DailyStats dailyStats);
    Integer countPvByDate(String date);
    Integer countUvByDate(String date);
    Integer countNewUserByDate(String date);
    Integer countTotalUser();
    Integer countDrama();
    Integer countContent();
    Integer countBehaviorByDateAndAction(String date, String action);
}
package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.DailyStats;
import com.qqsystem.serve.mapper.DailyStatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class DailyStatsService {

    @Autowired
    private DailyStatsMapper dailyStatsMapper;

    public void generateDailyStats() {
        generateDailyStats(new Date());
    }

    public void generateDailyStats(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String statDate = sdf.format(date);

        // 统计各项数据
        Integer pv = dailyStatsMapper.countPvByDate(statDate);
        Integer uv = dailyStatsMapper.countUvByDate(statDate);
        Integer newUserCount = dailyStatsMapper.countNewUserByDate(statDate);
        Integer userTotalCount = dailyStatsMapper.countTotalUser();
        Integer dramaCount = dailyStatsMapper.countDrama();
        Integer contentCount = dailyStatsMapper.countContent();
        Integer viewCount = dailyStatsMapper.countViewCount();
        Integer likeCount = dailyStatsMapper.countBehaviorByDateAndAction(statDate, "like");
        Integer commentCount = dailyStatsMapper.countBehaviorByDateAndAction(statDate, "comment");
        Integer favoriteCount = dailyStatsMapper.countBehaviorByDateAndAction(statDate, "favorite");
        Integer shareCount = dailyStatsMapper.countBehaviorByDateAndAction(statDate, "share");

        // 创建统计对象
        DailyStats dailyStats = new DailyStats();
        dailyStats.setStatDate(statDate);
        dailyStats.setPv(pv != null ? pv : 0);
        dailyStats.setUv(uv != null ? uv : 0);
        dailyStats.setNewUserCount(newUserCount != null ? newUserCount : 0);
        dailyStats.setUserTotalCount(userTotalCount != null ? userTotalCount : 0);
        dailyStats.setDramaCount(dramaCount != null ? dramaCount : 0);
        dailyStats.setContentCount(contentCount != null ? contentCount : 0);
        dailyStats.setViewCount(viewCount != null ? viewCount : 0);
        dailyStats.setLikeCount(likeCount != null ? likeCount : 0);
        dailyStats.setCommentCount(commentCount != null ? commentCount : 0);
        dailyStats.setFavoriteCount(favoriteCount != null ? favoriteCount : 0);
        dailyStats.setShareCount(shareCount != null ? shareCount : 0);

        // 插入或更新统计数据
        dailyStatsMapper.insertOrUpdate(dailyStats);
    }

    public void generateDailyStats(LocalDate statDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String statDateStr = statDate.format(formatter);

        // 统计各项数据
        Integer pv = dailyStatsMapper.countPvByDate(statDateStr);
        Integer uv = dailyStatsMapper.countUvByDate(statDateStr);
        Integer newUserCount = dailyStatsMapper.countNewUserByDate(statDateStr);
        Integer userTotalCount = dailyStatsMapper.countTotalUser();
        Integer dramaCount = dailyStatsMapper.countDrama();
        Integer contentCount = dailyStatsMapper.countContent();
        Integer viewCount = dailyStatsMapper.countViewCount();
        Integer likeCount = dailyStatsMapper.countBehaviorByDateAndAction(statDateStr, "like");
        Integer commentCount = dailyStatsMapper.countBehaviorByDateAndAction(statDateStr, "comment");
        Integer favoriteCount = dailyStatsMapper.countBehaviorByDateAndAction(statDateStr, "favorite");
        Integer shareCount = dailyStatsMapper.countBehaviorByDateAndAction(statDateStr, "share");

        // 创建统计对象
        DailyStats dailyStats = new DailyStats();
        dailyStats.setStatDate(statDateStr);
        dailyStats.setPv(pv != null ? pv : 0);
        dailyStats.setUv(uv != null ? uv : 0);
        dailyStats.setNewUserCount(newUserCount != null ? newUserCount : 0);
        dailyStats.setUserTotalCount(userTotalCount != null ? userTotalCount : 0);
        dailyStats.setDramaCount(dramaCount != null ? dramaCount : 0);
        dailyStats.setContentCount(contentCount != null ? contentCount : 0);
        dailyStats.setViewCount(viewCount != null ? viewCount : 0);
        dailyStats.setLikeCount(likeCount != null ? likeCount : 0);
        dailyStats.setCommentCount(commentCount != null ? commentCount : 0);
        dailyStats.setFavoriteCount(favoriteCount != null ? favoriteCount : 0);
        dailyStats.setShareCount(shareCount != null ? shareCount : 0);

        // 插入或更新统计数据
        dailyStatsMapper.insertOrUpdate(dailyStats);
    }
}
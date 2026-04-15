package com.qqsystem.serve.task;

import com.qqsystem.serve.service.DailyStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DailyStatsTask {

    @Autowired
    private DailyStatsService dailyStatsService;

    // 每天凌晨1点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void execute() {
        System.out.println("开始执行每日统计快照任务: " + new Date());
        dailyStatsService.generateDailyStats();
        System.out.println("每日统计快照任务执行完成: " + new Date());
    }
}
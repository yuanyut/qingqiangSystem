package com.qqsystem.serve.service;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    Map<String, Object> getOverview();
    List<Map<String, Object>> getDramaCategory();
    List<Map<String, Object>> getContentTrend(String type);
    List<Map<String, Object>> getBehaviorTrend(String type);
    List<Map<String, Object>> getDramaTop10();

    Map<String, Object> getQualityScore();

    List<Map<String, Object>> getGeoData();

    List<Map<String, Object>> getUserAgeDistribution();

    List<Map<String, Object>> getUserGenderDistribution();

    List<Map<String, Object>> getDramaHeatTrend(String type);
}
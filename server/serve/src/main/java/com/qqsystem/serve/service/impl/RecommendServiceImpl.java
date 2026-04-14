package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.RecommendItem;
import com.qqsystem.serve.mapper.RecommendMapper;
import com.qqsystem.serve.service.RecommendService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    private RecommendMapper recommendMapper;

    @Override
    public List<RecommendItem> getHotRecommendations() {
        List<RecommendItem> recommendations = new ArrayList<>();

        // 获取戏剧热门数据
        List<Map<String, Object>> hotDramas = recommendMapper.getHotDramas();
        for (Map<String, Object> drama : hotDramas) {
            RecommendItem item = new RecommendItem();
            item.setId((Long) drama.get("id"));
            item.setType((String) drama.get("type"));
            item.setTitle((String) drama.get("title"));
            item.setCover((String) drama.get("cover"));
            int viewCount = drama.get("view_count") != null ? (int) drama.get("view_count") : 0;
            int likeCount = drama.get("like_count") != null ? ((Number) drama.get("like_count")).intValue() : 0;
            int favoriteCount = drama.get("favorite_count") != null ? ((Number) drama.get("favorite_count")).intValue() : 0;
            item.setScore(viewCount + likeCount + favoriteCount);
            recommendations.add(item);
        }

        // 获取新闻热门数据
        List<Map<String, Object>> hotNews = recommendMapper.getHotNews();
        for (Map<String, Object> news : hotNews) {
            RecommendItem item = new RecommendItem();
            item.setId((Long) news.get("id"));
            item.setType((String) news.get("type"));
            item.setTitle((String) news.get("title"));
            item.setCover((String) news.get("cover"));
            int viewCount = news.get("view_count") != null ? (int) news.get("view_count") : 0;
            int likeCount = news.get("like_count") != null ? ((Number) news.get("like_count")).intValue() : 0;
            int favoriteCount = news.get("favorite_count") != null ? ((Number) news.get("favorite_count")).intValue() : 0;
            item.setScore(viewCount + likeCount + favoriteCount);
            recommendations.add(item);
        }

        // 获取演员热门数据
        List<Map<String, Object>> hotActors = recommendMapper.getHotActors();
        for (Map<String, Object> actor : hotActors) {
            RecommendItem item = new RecommendItem();
            item.setId((Long) actor.get("id"));
            item.setType((String) actor.get("type"));
            item.setTitle((String) actor.get("title"));
            item.setCover((String) actor.get("cover"));
            int viewCount = actor.get("view_count") != null ? (int) actor.get("view_count") : 0;
            int likeCount = actor.get("like_count") != null ? ((Number) actor.get("like_count")).intValue() : 0;
            int favoriteCount = actor.get("favorite_count") != null ? ((Number) actor.get("favorite_count")).intValue() : 0;
            item.setScore(viewCount + likeCount + favoriteCount);
            recommendations.add(item);
        }

        // 按分数排序，返回前10条
        recommendations.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        return recommendations.stream().limit(10).collect(Collectors.toList());
    }

    @Override
    public List<RecommendItem> getCollaborativeFilteringRecommendations(Long userId) {
        // 获取用户行为数据
        List<Map<String, Object>> userBehaviors = recommendMapper.getUserBehaviors(userId);
        if (userBehaviors.isEmpty()) {
            // 如果用户没有行为数据，返回热门推荐
            return getHotRecommendations();
        }

        // 获取所有用户行为数据
        List<Map<String, Object>> allBehaviors = recommendMapper.getAllBehaviors();

        // 计算物品相似度
        Map<String, Map<String, Double>> itemSimilarity = calculateItemSimilarity(allBehaviors);

        // 获取用户已浏览的物品
        List<Map<String, Object>> viewedItems = recommendMapper.getUserViewedItems(userId);
        Set<String> viewedItemKeys = new HashSet<>();
        for (Map<String, Object> item : viewedItems) {
            String type = (String) item.get("target_type");
            Long id = (Long) item.get("target_id");
            viewedItemKeys.add(type + "_" + id);
        }

        // 计算推荐分数
        Map<String, Double> itemScores = new HashMap<>();
        for (Map<String, Object> behavior : userBehaviors) {
            String targetType = (String) behavior.get("target_type");
            Long targetId = (Long) behavior.get("target_id");
            int weight = (int) behavior.get("weight");
            String itemKey = targetType + "_" + targetId;

            // 获取与当前物品相似的物品
            Map<String, Double> similarItems = itemSimilarity.get(itemKey);
            if (similarItems != null) {
                for (Map.Entry<String, Double> entry : similarItems.entrySet()) {
                    String similarItemKey = entry.getKey();
                    if (!viewedItemKeys.contains(similarItemKey)) {
                        double similarity = entry.getValue();
                        double score = similarity * weight;
                        itemScores.put(similarItemKey, itemScores.getOrDefault(similarItemKey, 0.0) + score);
                    }
                }
            }
        }

        // 转换为推荐项
        List<RecommendItem> recommendations = new ArrayList<>();
        for (Map.Entry<String, Double> entry : itemScores.entrySet()) {
            String itemKey = entry.getKey();
            String[] parts = itemKey.split("_");
            if (parts.length == 2) {
                String type = parts[0];
                Long id = Long.parseLong(parts[1]);
                double score = entry.getValue();

                RecommendItem item = new RecommendItem();
                item.setId(id);
                item.setType(type);
                item.setScore(score);
                // 这里简化处理，实际项目中需要根据类型查询标题和封面
                recommendations.add(item);
            }
        }

        // 按分数排序，返回前10条
        recommendations.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        return recommendations.stream().limit(10).collect(Collectors.toList());
    }

    // 计算物品相似度（基于共现关系）
    private Map<String, Map<String, Double>> calculateItemSimilarity(List<Map<String, Object>> allBehaviors) {
        // 构建用户-物品映射
        Map<Long, Set<String>> userItemMap = new HashMap<>();
        for (Map<String, Object> behavior : allBehaviors) {
            Long userId = (Long) behavior.get("user_id");
            String targetType = (String) behavior.get("target_type");
            Long targetId = (Long) behavior.get("target_id");
            String itemKey = targetType + "_" + targetId;

            userItemMap.computeIfAbsent(userId, k -> new HashSet<>()).add(itemKey);
        }

        // 计算物品共现次数
        Map<String, Map<String, Integer>> coOccurrenceMap = new HashMap<>();
        for (Set<String> items : userItemMap.values()) {
            List<String> itemList = new ArrayList<>(items);
            for (int i = 0; i < itemList.size(); i++) {
                String item1 = itemList.get(i);
                for (int j = i + 1; j < itemList.size(); j++) {
                    String item2 = itemList.get(j);
                    // 更新item1和item2的共现次数
                    coOccurrenceMap.computeIfAbsent(item1, k -> new HashMap<>())
                            .put(item2, coOccurrenceMap.get(item1).getOrDefault(item2, 0) + 1);
                    coOccurrenceMap.computeIfAbsent(item2, k -> new HashMap<>())
                            .put(item1, coOccurrenceMap.get(item2).getOrDefault(item1, 0) + 1);
                }
            }
        }

        // 计算物品相似度
        Map<String, Map<String, Double>> itemSimilarity = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : coOccurrenceMap.entrySet()) {
            String item1 = entry.getKey();
            Map<String, Integer> coOccurrences = entry.getValue();
            Map<String, Double> similarities = new HashMap<>();

            for (Map.Entry<String, Integer> coEntry : coOccurrences.entrySet()) {
                String item2 = coEntry.getKey();
                int coCount = coEntry.getValue();
                // 简化的相似度计算：共现次数
                similarities.put(item2, (double) coCount);
            }

            itemSimilarity.put(item1, similarities);
        }

        return itemSimilarity;
    }
}

package com.qqsystem.serve.vo;

import java.util.Date;

public class FavoriteVO {
    private Long id;
    private Long userId;
    private String targetType;
    private Long targetId;
    private String action;
    private Date createTime;
    private String time; // 格式化的时间字符串
    private String title; // 标题
    private String coverUrl; // 封面
    private String subtitle; // 简介
    private String duration; // 时长（可为空）
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getTargetType() {
        return targetType;
    }
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
    public Long getTargetId() {
        return targetId;
    }
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCoverUrl() {
        return coverUrl;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
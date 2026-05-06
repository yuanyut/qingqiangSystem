package com.qqsystem.serve.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Drama {

    private Long id;

    private String name;

    private String cover;

    private String intro;

    private Long categoryId;

    private Integer status;

    private Integer viewCount;

    private Integer likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime publishDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;

    // ⭐ 关联扩展（关键）
    private List<DramaActor> actors;

    private List<Actor> actorDetails;

    private List<Content> contents;
    
    private DramaCategory categoryDetail;
}
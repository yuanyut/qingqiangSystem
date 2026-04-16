package com.qqsystem.serve.entity;

import lombok.Data;

import java.util.Date;
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

    private Date publishDate;

    private Date createTime;

    private Date updateTime;

    // ⭐ 关联扩展（关键）
    private List<DramaActor> actors;

    private List<Actor> actorDetails;

    private List<Content> contents;
    
    private DramaCategory categoryDetail;
}
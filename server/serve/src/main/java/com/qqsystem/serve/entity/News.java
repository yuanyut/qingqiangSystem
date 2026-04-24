package com.qqsystem.serve.entity;

import lombok.Data;

import java.util.Date;

@Data
public class News {

    private Long id;

    private String title;

    private String content;

    private String source;

    private String cover;

    private Integer viewCount;

    private Integer likeCount;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String category;
}

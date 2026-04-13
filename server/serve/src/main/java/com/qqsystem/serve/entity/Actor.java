package com.qqsystem.serve.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Actor {

    private Long id;

    private String name;

    private String avatar;

    private String intro;

    private String roleName;

    private String style;

    private Integer worksCount;

    private Integer viewCount;

    private Integer likeCount;

    private Date joinDate;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}

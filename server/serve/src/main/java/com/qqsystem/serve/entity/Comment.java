package com.qqsystem.serve.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private Long id;

    private Long userId;

    private String targetType;

    private Long targetId;

    private String content;

    private Long parentId;

    private Integer status;

    private Date createTime;

    // 扩展字段，用于返回用户信息
    private String userName;

    private String userAvatar;
}

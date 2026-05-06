package com.qqsystem.serve.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private Long id;

    private Long userId;

    private String targetType;

    private Long targetId;

    private String content;

    private Long parentId;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    // 扩展字段，用于返回用户信息
    private String userName;

    private String userAvatar;
}

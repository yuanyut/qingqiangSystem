package com.qqsystem.serve.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Behavior {

    private Long id;

    private Long userId;

    private String targetType;

    private Long targetId;

    private Date createTime;
}

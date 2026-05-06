package com.qqsystem.serve.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime joinDate;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;

    // 关联扩展
    private List<Drama> dramas;
}

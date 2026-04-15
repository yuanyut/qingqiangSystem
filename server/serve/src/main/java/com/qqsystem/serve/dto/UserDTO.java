package com.qqsystem.serve.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String address;
    private String createTime;
}

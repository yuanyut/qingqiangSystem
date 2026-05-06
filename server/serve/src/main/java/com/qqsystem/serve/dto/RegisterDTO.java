package com.qqsystem.serve.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String nickname;
    private String sex;        // 0女 1男（你自己定）
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private LocalDate birthday;      // 出生日期
}

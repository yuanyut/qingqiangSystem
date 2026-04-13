package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Content;
import org.apache.ibatis.annotations.Param;

public interface ContentMapper {

    Content selectById(@Param("id") Long id);
}

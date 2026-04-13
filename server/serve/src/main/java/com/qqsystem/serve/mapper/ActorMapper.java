package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Actor;
import org.apache.ibatis.annotations.Param;

public interface ActorMapper {

    Actor selectById(@Param("id") Long id);
}

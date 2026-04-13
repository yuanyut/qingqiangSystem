package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.DramaActor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DramaActorMapper {

    List<DramaActor> selectByDramaId(@Param("dramaId") Long dramaId);

    int insert(DramaActor dramaActor);

    int deleteByDramaId(@Param("dramaId") Long dramaId);
}
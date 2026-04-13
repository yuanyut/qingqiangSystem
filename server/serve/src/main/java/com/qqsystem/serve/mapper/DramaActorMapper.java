package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.DramaActor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DramaActorMapper {

    List<DramaActor> selectByDramaId(@Param("dramaId") Long dramaId);

    List<DramaActor> selectByActorId(@Param("actorId") Long actorId);

    int insert(DramaActor dramaActor);

    int deleteByDramaId(@Param("dramaId") Long dramaId);

    int deleteByActorId(@Param("actorId") Long actorId);
}
package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.DramaContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DramaContentMapper {

    List<DramaContent> selectByDramaId(@Param("dramaId") Long dramaId);

    int insert(DramaContent dramaContent);

    int deleteByDramaId(@Param("dramaId") Long dramaId);
}
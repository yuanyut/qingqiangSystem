package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.DramaCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DramaCategoryMapper {
    DramaCategory selectById(@Param("id") Long id);
    
    List<DramaCategory> selectAll();
}
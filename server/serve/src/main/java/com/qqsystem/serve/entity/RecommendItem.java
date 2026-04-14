package com.qqsystem.serve.entity;

import lombok.Data;

@Data
public class RecommendItem {

    private Long id;

    private String type;

    private String title;

    private String cover;

    private double score;
}

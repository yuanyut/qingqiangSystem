<template>
    <div>
      <!-- 统计卡片区 -->
      <div class="stats-grid">
        <div 
          v-for="item in statsCards" 
          :key="item.id"
          class="stat-card"
          :style="{ backgroundColor: item.bgColor }"
        >
          <div class="stat-icon">{{ item.icon }}</div>
          <div class="stat-value">{{ item.value }}</div>
          <div class="stat-label">{{ item.label }}</div>
          <div class="stat-trend" :class="item.trendType">
            {{ item.trend }} 较上周
          </div>
        </div>
      </div>
  
      <!-- 浏览趋势图 -->
      <div class="trend-section">
        <h3>{{ trendData.title }}</h3>
        <div class="trend-chart">
          <div 
            v-for="(item, index) in trendData.days" 
            :key="index"
            class="trend-bar-wrapper"
          >
            <div class="trend-bar-label">{{ item }}</div>
            <div class="trend-bar">
              <div 
                class="trend-bar-fill"
                :style="{ 
                  height: (trendData.values[index] / trendData.maxValue * 100) + '%',
                  backgroundColor: getBarColor(index)
                }"
              ></div>
            </div>
            <div class="trend-value">{{ trendData.values[index] }}</div>
          </div>
        </div>
      </div>
  
      <!-- 内容偏好分析 -->
      <div class="preference-section">
        <h3>{{ preferenceData.title }}</h3>
        <div 
          v-for="item in preferenceData.categories" 
          :key="item.id"
          class="preference-item"
        >
          <div class="preference-header">
            <span class="preference-icon">{{ item.icon }}</span>
            <span class="preference-name">{{ item.name }}</span>
            <span class="preference-count">{{ item.count }}次</span>
            <span class="preference-percentage">{{ item.percentage }}%</span>
          </div>
          <div class="progress-bar">
            <div 
              class="progress-fill"
              :style="{ 
                width: item.percentage + '%',
                backgroundColor: item.color 
              }"
            ></div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { reactive } from 'vue'
  
  // 统计数据
  const statsCards = reactive([
    {
      id: 1,
      label: '收藏数',
      value: 23,
      icon: '⭐',
      trend: '+5',
      trendType: 'up',
      bgColor: '#fff5e6'
    },
    {
      id: 2,
      label: '浏览数',
      value: 156,
      icon: '👁️',
      trend: '+23',
      trendType: 'up',
      bgColor: '#f6ffed'
    },
    {
      id: 3,
      label: '点赞数',
      value: 89,
      icon: '❤️',
      trend: '+8',
      trendType: 'up',
      bgColor: '#fff0f6'
    },
    {
      id: 4,
      label: '粉丝数',
      value: 12,
      icon: '👥',
      trend: '+2',
      trendType: 'up',
      bgColor: '#e6f7ff'
    }
  ])
  
  // 浏览趋势
  const trendData = reactive({
    title: '浏览趋势（近7天）',
    days: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
    values: [23, 28, 35, 42, 38, 45, 52],
    maxValue: 60
  })
  
  // 内容偏好
  const preferenceData = reactive({
    title: '内容偏好分析',
    categories: [
      { id: 1, name: '剧目', icon: '🎬', percentage: 65, count: 102, color: '#f5a623' },
      { id: 2, name: '名家', icon: '🎭', percentage: 45, count: 48, color: '#722ed1' },
      { id: 3, name: '资讯', icon: '📰', percentage: 32, count: 36, color: '#13c2c2' },
      { id: 4, name: '视频', icon: '📺', percentage: 40, count: 52, color: '#fa8c16' }
    ]
  })
  
  // 获取柱状图颜色
  const getBarColor = (index) => {
    const colors = ['#f5a623', '#f5a623', '#f5a623', '#f5a623', '#f5a623', '#ff7a45', '#ff4d4f']
    return colors[index] || '#f5a623'
  }
  </script>
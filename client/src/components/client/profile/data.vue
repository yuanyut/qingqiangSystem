<template>
  <div class="data-analysis">
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

<style scoped>
.data-analysis {
  /* max-width: 1200px; */
  margin: 0 auto;
  padding: 32px 24px;
  background: linear-gradient(135deg, #fefaf5 0%, #fff9f2 100%);
  min-height: 100vh;
}

/* 统计卡片网格 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 24px;
  margin-bottom: 40px;
}

.stat-card {
  padding: 24px;
  border-radius: 24px;
  transition: all 0.3s cubic-bezier(0.2, 0, 0, 1);
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border: 1px solid rgba(0, 0, 0, 0.02);
}

.stat-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(255,255,255,0.3) 0%, rgba(255,255,255,0) 100%);
  pointer-events: none;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 28px -8px rgba(98, 67, 41, 0.15);
}

.stat-icon {
  font-size: 32px;
  margin-bottom: 16px;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  color: #3a2c21;
  margin-bottom: 8px;
  line-height: 1.2;
}

.stat-label {
  font-size: 14px;
  color: #9b8570;
  margin-bottom: 12px;
  font-weight: 500;
}

.stat-trend {
  font-size: 12px;
  padding: 4px 8px;
  border-radius: 20px;
  display: inline-block;
  font-weight: 500;
}

.stat-trend.up {
  background: rgba(82, 196, 26, 0.1);
  color: #52c41a;
}

.stat-trend.down {
  background: rgba(245, 34, 45, 0.1);
  color: #ff4d4f;
}

/* 趋势图区域 */
.trend-section {
  background: white;
  border-radius: 24px;
  padding: 28px 24px;
  margin-bottom: 32px;
  border: 1px solid #f0e6dc;
  transition: all 0.3s ease;
}

.trend-section:hover {
  box-shadow: 0 8px 24px -12px rgba(98, 67, 41, 0.12);
  border-color: #e6d5c4;
}

.trend-section h3 {
  font-size: 20px;
  font-weight: 600;
  margin: 0 0 24px 0;
  color: #3a2c21;
  display: flex;
  align-items: center;
  gap: 10px;
  padding-left: 12px;
  border-left: 4px solid #b87c4e;
}

.trend-chart {
  display: flex;
  justify-content: space-around;
  align-items: flex-end;
  gap: 16px;
  padding: 20px 0;
}

.trend-bar-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.trend-bar-label {
  font-size: 13px;
  color: #9b8570;
  font-weight: 500;
  order: 2;
}

.trend-bar {
  width: 100%;
  max-width: 60px;
  height: 180px;
  background: #f8f2ea;
  border-radius: 12px;
  position: relative;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.2s;
}

.trend-bar:hover {
  transform: scale(1.02);
}

.trend-bar-fill {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  border-radius: 12px 12px 0 0;
  transition: height 0.5s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.05);
}

.trend-value {
  font-size: 12px;
  font-weight: 600;
  color: #b87c4e;
  order: 3;
}

/* 偏好分析区域 */
.preference-section {
  background: white;
  border-radius: 24px;
  padding: 28px 24px;
  border: 1px solid #f0e6dc;
  transition: all 0.3s ease;
}

.preference-section:hover {
  box-shadow: 0 8px 24px -12px rgba(98, 67, 41, 0.12);
  border-color: #e6d5c4;
}

.preference-section h3 {
  font-size: 20px;
  font-weight: 600;
  margin: 0 0 24px 0;
  color: #3a2c21;
  display: flex;
  align-items: center;
  gap: 10px;
  padding-left: 12px;
  border-left: 4px solid #b87c4e;
}

.preference-item {
  margin-bottom: 24px;
}

.preference-item:last-child {
  margin-bottom: 0;
}

.preference-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 10px;
  flex-wrap: wrap;
}

.preference-icon {
  font-size: 20px;
}

.preference-name {
  font-size: 15px;
  font-weight: 500;
  color: #3a2c21;
  flex: 1;
}

.preference-count {
  font-size: 13px;
  color: #9b8570;
}

.preference-percentage {
  font-size: 14px;
  font-weight: 600;
  color: #b87c4e;
  min-width: 45px;
  text-align: right;
}

.progress-bar {
  width: 100%;
  height: 10px;
  background: #f8f2ea;
  border-radius: 10px;
  overflow: hidden;
  position: relative;
}

.progress-fill {
  height: 100%;
  border-radius: 10px;
  transition: width 0.6s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

.progress-fill::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(90deg, rgba(255,255,255,0.2) 0%, rgba(255,255,255,0) 100%);
  animation: shimmer 2s infinite;
}

@keyframes shimmer {
  0% {
    transform: translateX(-100%);
  }
  100% {
    transform: translateX(100%);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .data-analysis {
    padding: 20px 16px;
  }

  .stats-grid {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 16px;
  }

  .stat-card {
    padding: 20px;
  }

  .stat-value {
    font-size: 28px;
  }

  .stat-icon {
    font-size: 28px;
  }

  .trend-section,
  .preference-section {
    padding: 20px 16px;
  }

  .trend-section h3,
  .preference-section h3 {
    font-size: 18px;
  }

  .trend-chart {
    gap: 8px;
  }

  .trend-bar {
    height: 140px;
    max-width: 45px;
  }

  .trend-bar-label {
    font-size: 11px;
  }

  .trend-value {
    font-size: 11px;
  }

  .preference-header {
    gap: 8px;
  }

  .preference-name {
    font-size: 14px;
  }

  .preference-count,
  .preference-percentage {
    font-size: 12px;
  }
}

@media (max-width: 480px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }

  .trend-chart {
    overflow-x: auto;
    justify-content: flex-start;
    padding-bottom: 12px;
  }

  .trend-bar-wrapper {
    min-width: 60px;
  }

  .preference-header {
    gap: 6px;
  }

  .preference-count {
    display: none;
  }
}
</style>
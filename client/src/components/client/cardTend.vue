<script setup lang="ts">
defineProps<{
  id?: string | number
  title?: string
  desc?: string
  up?: string | number
  nowHot?: string | number
}>()
</script>

<template>
  <div class="trend-card">
    <!-- 排名标识 -->
    <div class="card-rank" v-if="id">
      <span class="rank-number">#{{ id }}</span>
    </div>
    
    <!-- 卡片内容区 -->
    <div class="card-content">
      <!-- 标题区域 -->
      <div class="title-section">
        <h3 class="card-title">{{ title || '热门推荐' }}</h3>
        <div class="hot-badge" v-if="up">
          <span class="hot-icon">🔥</span>
          <span class="hot-value">+{{ up }}%</span>
        </div>
      </div>
      
      <!-- 描述区域 -->
      <p class="card-desc">{{ desc || '精彩内容正在热播' }}</p>
      
      <!-- 热度进度条 -->
      <div class="progress-section">
        <div class="progress-header">
          <span class="progress-label">热度指数</span>
          <span class="progress-value">{{ nowHot || 0 }}%</span>
        </div>
        <el-progress 
          :percentage="Number(nowHot) || 0" 
          :stroke-width="8"
          :show-text="false"
          class="custom-progress"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.trend-card {
  position: relative;
  background: linear-gradient(135deg, #ffffff 0%, #fefcf9 100%);
  border-radius: 24px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04), 0 1px 2px rgba(0, 0, 0, 0.02);
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
  border: 1px solid rgba(217, 194, 164, 0.2);
  margin-bottom: 12px;
}

.trend-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 28px rgba(0, 0, 0, 0.12), 0 2px 4px rgba(0, 0, 0, 0.02);
  border-color: rgba(184, 124, 78, 0.3);
}

/* 排名标识 */
.card-rank {
  position: absolute;
  top: 20px;
  right: 105px;
  background: linear-gradient(135deg, #fef3e8 0%, #ffe8d9 100%);
  padding: 4px 12px;
  border-radius: 40px;
  z-index: 1;
}

.rank-number {
  font-size: 13px;
  font-weight: 700;
  background: linear-gradient(135deg, #b87c4e 0%, #9e653d 100%);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  letter-spacing: 0.5px;
}

/* 卡片内容区 */
.card-content {
  padding: 20px 20px 24px 20px;
}

/* 标题区域 */
.title-section {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 12px;
  margin-bottom: 12px;
}

.card-title {
  font-size: 18px;
  font-weight: 700;
  color: #2c241a;
  margin: 0;
  line-height: 1.4;
  letter-spacing: -0.2px;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.hot-badge {
  display: flex;
  align-items: center;
  gap: 4px;
  background: linear-gradient(135deg, #fff5eb 0%, #ffe8d9 100%);
  padding: 4px 10px;
  border-radius: 40px;
  flex-shrink: 0;
}

.hot-icon {
  font-size: 14px;
  animation: pulse 1.5s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.8;
  }
}

.hot-value {
  font-size: 13px;
  font-weight: 700;
  color: #e67e22;
}

/* 描述区域 */
.card-desc {
  font-size: 14px;
  color: #7f6e5c;
  line-height: 1.5;
  margin: 0 0 20px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 进度条区域 */
.progress-section {
  margin-top: 8px;
}

.progress-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 8px;
}

.progress-label {
  font-size: 12px;
  font-weight: 500;
  color: #9b8e7e;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.progress-value {
  font-size: 13px;
  font-weight: 700;
  color: #b87c4e;
}

/* 自定义进度条样式 */
:deep(.custom-progress .el-progress-bar__outer) {
  background-color: #f0e8df;
  border-radius: 20px;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.05);
}

:deep(.custom-progress .el-progress-bar__inner) {
  background: linear-gradient(90deg, #e6b17e 0%, #b87c4e 100%);
  border-radius: 20px;
  transition: width 0.6s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

:deep(.custom-progress .el-progress-bar__inner::after) {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(255, 255, 255, 0.3),
    transparent
  );
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

/* 卡片变体 - 不同排名样式 */
.trend-card[data-rank="1"] .card-rank {
  background: linear-gradient(135deg, #ffd966 0%, #ffb347 100%);
}

.trend-card[data-rank="1"] .rank-number {
  background: white;
  -webkit-background-clip: text;
  background-clip: text;
  color: white;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.trend-card[data-rank="2"] .card-rank {
  background: linear-gradient(135deg, #e8e8e8 0%, #d0d0d0 100%);
}

.trend-card[data-rank="3"] .card-rank {
  background: linear-gradient(135deg, #f5e6d3 0%, #e6ccb3 100%);
}

/* 悬停增强效果 */
.trend-card:hover .card-title {
  color: #b87c4e;
  transition: color 0.2s ease;
}

.trend-card:hover .progress-value {
  transform: scale(1.05);
  transition: transform 0.2s ease;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .trend-card {
    border-radius: 20px;
  }
  
  .card-content {
    padding: 16px 16px 20px 16px;
  }
  
  .card-title {
    font-size: 16px;
  }
  
  .hot-value {
    font-size: 12px;
  }
  
  .card-desc {
    font-size: 13px;
    margin-bottom: 16px;
  }
}

@media (max-width: 480px) {
  .card-rank {
    top: 12px;
    right: 12px;
    padding: 3px 10px;
  }
  
  .rank-number {
    font-size: 11px;
  }
  
  .card-content {
    padding: 14px 14px 18px 14px;
  }
  
  .title-section {
    margin-bottom: 8px;
  }
  
  .card-title {
    font-size: 15px;
  }
  
  .hot-badge {
    padding: 3px 8px;
  }
  
  .hot-icon {
    font-size: 12px;
  }
  
  .hot-value {
    font-size: 11px;
  }
}
</style>
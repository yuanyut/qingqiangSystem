<script setup lang="ts">
const props = defineProps<{
  title?: string
}>()
</script>

<template>
  <div class="recommend-card">
    <!-- 卡片头部 -->
    <div class="card-header">
      <h3 class="card-title">{{ props.title || '推荐内容' }}</h3>
      <div class="header-decoration"></div>
    </div>
    
    <!-- 内容列表 -->
    <div class="card-list">
      <div v-for="(item, index) in 9" :key="index" class="list-item">
        <div class="item-rank">{{ index + 1 }}</div>
        <div class="item-content">
          <span class="item-name">《周仁回府》</span>
          <span class="item-desc">· 经典秦腔剧目</span>
        </div>
        <div class="item-arrow">→</div>
      </div>
    </div>
    
    <!-- 底部查看更多 -->
    <div class="card-footer">
      <span class="more-link">查看更多</span>
      <span class="more-icon">›</span>
    </div>
  </div>
</template>

<style scoped>
.recommend-card {
  width: 320px;
  background: linear-gradient(135deg, #ffffff 0%, #fefcf9 100%);
  border-radius: 24px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06), 0 1px 2px rgba(0, 0, 0, 0.03);
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
  border: 1px solid rgba(217, 194, 164, 0.25);
}

.recommend-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 20px 35px -12px rgba(0, 0, 0, 0.15);
  border-color: rgba(184, 124, 78, 0.4);
}

/* 卡片头部 */
.card-header {
  padding: 20px 20px 12px 20px;
  border-bottom: 2px solid rgba(217, 194, 164, 0.3);
  position: relative;
}

.card-title {
  font-size: 18px;
  font-weight: 700;
  margin: 0;
  background: linear-gradient(135deg, #2c241a 0%, #5c4b3a 100%);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  letter-spacing: -0.3px;
  position: relative;
  display: inline-block;
}

.header-decoration {
  position: absolute;
  bottom: -2px;
  left: 20px;
  width: 40px;
  height: 2px;
  background: linear-gradient(90deg, #e6b17e, #b87c4e);
  border-radius: 2px;
}

/* 内容列表 */
.card-list {
  padding: 8px 0;
  max-height: 360px;
  overflow-y: auto;
}

.card-list::-webkit-scrollbar {
  width: 4px;
}

.card-list::-webkit-scrollbar-track {
  background: #f0e8df;
  border-radius: 4px;
}

.card-list::-webkit-scrollbar-thumb {
  background: #d9c2a4;
  border-radius: 4px;
}

.list-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 20px;
  transition: all 0.25s ease;
  border-left: 3px solid transparent;
}

.list-item:hover {
  background: linear-gradient(90deg, rgba(184, 124, 78, 0.05) 0%, transparent 100%);
  border-left-color: #b87c4e;
  /* transform: translateX(4px); */
}

.item-rank {
  width: 28px;
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #faf8f4 0%, #f5ede2 100%);
  border-radius: 12px;
  font-size: 12px;
  font-weight: 700;
  color: #b87c4e;
  flex-shrink: 0;
  transition: all 0.2s ease;
}

.list-item:hover .item-rank {
  background: linear-gradient(135deg, #b87c4e 0%, #9e653d 100%);
  color: white;
  transform: scale(1.05);
}

.item-content {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  align-items: baseline;
  gap: 4px;
}

.item-name {
  font-size: 14px;
  font-weight: 600;
  color: #2c241a;
  transition: color 0.2s ease;
}

.list-item:hover .item-name {
  color: #b87c4e;
}

.item-desc {
  font-size: 12px;
  color: #9b8e7e;
}

.item-arrow {
  font-size: 16px;
  color: #d9c2a4;
  opacity: 0;
  transform: translateX(-8px);
  transition: all 0.25s ease;
}

.list-item:hover .item-arrow {
  opacity: 1;
  transform: translateX(0);
  color: #b87c4e;
}

/* 卡片底部 */
.card-footer {
  padding: 16px 20px;
  border-top: 1px solid rgba(217, 194, 164, 0.25);
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 6px;
  transition: all 0.2s ease;
}

.more-link {
  font-size: 13px;
  font-weight: 500;
  color: #b87c4e;
  transition: all 0.2s ease;
}

.more-icon {
  font-size: 18px;
  color: #b87c4e;
  transition: transform 0.2s ease;
}

.card-footer:hover {
  background: rgba(184, 124, 78, 0.03);
}

.card-footer:hover .more-link {
  color: #9e653d;
}

.card-footer:hover .more-icon {
  transform: translateX(4px);
}

/* 不同排名的特殊样式 */
.list-item:nth-child(1) .item-rank {
  background: linear-gradient(135deg, #ffd966, #ffb347);
  color: white;
}

.list-item:nth-child(2) .item-rank {
  background: linear-gradient(135deg, #e8e8e8, #d0d0d0);
  color: #5c4b3a;
}

.list-item:nth-child(3) .item-rank {
  background: linear-gradient(135deg, #f5e6d3, #e6ccb3);
  color: #b87c4e;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .recommend-card {
    width: 100%;
    max-width: 320px;
    margin: 0 auto;
    border-radius: 20px;
  }
  
  .card-header {
    padding: 16px 16px 10px 16px;
  }
  
  .card-title {
    font-size: 16px;
  }
  
  .list-item {
    padding: 10px 16px;
  }
  
  .item-rank {
    width: 24px;
    height: 24px;
    font-size: 11px;
    border-radius: 10px;
  }
  
  .item-name {
    font-size: 13px;
  }
  
  .item-desc {
    font-size: 11px;
  }
  
  .card-footer {
    padding: 12px 16px;
  }
}

@media (max-width: 480px) {
  .recommend-card {
    max-width: 100%;
  }
  
  .list-item {
    padding: 8px 12px;
  }
  
  .item-rank {
    width: 22px;
    height: 22px;
    font-size: 10px;
    border-radius: 8px;
  }
  
  .item-name {
    font-size: 12px;
  }
  
  .item-desc {
    font-size: 10px;
  }
}
</style>
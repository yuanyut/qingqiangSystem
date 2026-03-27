<template>
    <div >
      <!-- 头部 -->
      <div class="history-header">
        <h3>👁️ 浏览历史</h3>
        <button class="clear-btn" >清空历史</button>
      </div>
      
      <!-- 筛选栏 -->
      <div class="filter-bar">
        <button
          v-for="filter in filterOptions"
          :key="filter.value"
         
        >
          {{ filter.label }}
        </button>
      </div>
      
      <!-- 历史列表 -->
      <div class="history-list">
        <div v-for="group in browseHistory" :key="group.dateGroup" class="date-group">
          <div class="date-title">{{ group.dateGroup }}</div>
          
          <div class="history-items">
            <div v-for="item in group.list" :key="item.id" class="history-item">
              <img :src="item.coverUrl" :alt="item.title" class="item-cover">
              
              <div class="item-info">
                <div class="item-title">{{ item.title }}</div>
                <div class="item-subtitle">{{ item.subtitle }}</div>
                <div class="item-meta">
                  <span class="item-duration">{{ item.duration }}</span>
                  <span class="item-time">{{ item.time }}</span>
                </div>
              </div>
              
              <div class="item-actions">
                <button class="action-btn" >删除</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue'
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  // 浏览历史数据
  const browseHistory = ref([
    {
      id: 1,
      type: 'drama',
      title: '三滴血',
      subtitle: '李淑芳版',
      coverUrl: '/images/sandixue.jpg',
      duration: '观看 15分钟',
      time: '15:30',
      dateGroup: '今天',
      timestamp: '2024-03-27 15:30:00'
    },
    {
      id: 2,
      type: 'interview',
      title: '秦腔名家李小锋访谈',
      subtitle: '讲述《火焰驹》背后的故事',
      coverUrl: '/images/lixf_interview.jpg',
      duration: '浏览 8分钟',
      time: '14:20',
      dateGroup: '今天',
      timestamp: '2024-03-27 14:20:00'
    },
    {
      id: 3,
      type: 'video',
      title: '火焰驹精彩唱段',
      subtitle: '李小锋主演',
      coverUrl: '/images/huoyanju_clip.jpg',
      duration: '观看 5分钟',
      time: '20:15',
      dateGroup: '昨天',
      timestamp: '2024-03-26 20:15:00'
    },
    {
      id: 4,
      type: 'news',
      title: '秦腔艺术节资讯',
      subtitle: '2024秦腔艺术节精彩回顾',
      coverUrl: '/images/festival_news.jpg',
      duration: '浏览 3分钟',
      time: '10:30',
      dateGroup: '昨天',
      timestamp: '2024-03-26 10:30:00'
    },
    {
      id: 5,
      type: 'video',
      title: '秦腔教学：旦角发声技巧',
      subtitle: '马友仙老师主讲',
      coverUrl: '/images/teaching.jpg',
      duration: '观看 12分钟',
      time: '3月24日',
      dateGroup: '更早',
      timestamp: '2024-03-24 15:20:00'
    },
    {
      id: 6,
      type: 'drama',
      title: '周仁回府',
      subtitle: '任哲中经典版',
      coverUrl: '/images/zhourenhuifu.jpg',
      duration: '观看 45分钟',
      time: '3月23日',
      dateGroup: '更早',
      timestamp: '2024-03-23 19:30:00'
    }
  ])
  
  // 筛选选项
  const filterOptions = ref([
    { value: 'all', label: '全部' },
    { value: 'drama', label: '剧目' },
    { value: 'artist', label: '名家' },
    { value: 'video', label: '视频' },
    { value: 'news', label: '资讯' }
  ])
  
  
  

  </script>
  
  <style scoped>
  .browse-history {
    padding: 20px;
  }
  
  .history-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 12px;
    border-bottom: 1px solid #eee;
  }
  
  .history-header h3 {
    font-size: 18px;
    margin: 0;
  }
  
  .clear-btn {
    padding: 6px 16px;
    background: #f5f5f5;
    border: none;
    border-radius: 16px;
    cursor: pointer;
    color: #666;
    font-size: 13px;
  }
  
  .clear-btn:hover {
    background: #e0e0e0;
  }
  
  .filter-bar {
    display: flex;
    gap: 12px;
    margin-bottom: 24px;
    flex-wrap: wrap;
  }
  
  .filter-btn {
    padding: 6px 16px;
    background: #f5f5f5;
    border: none;
    border-radius: 20px;
    cursor: pointer;
    font-size: 13px;
    transition: all 0.2s;
  }
  
  .filter-btn.active {
    background: #d4451e;
    color: white;
  }
  
  .date-group {
    margin-bottom: 24px;
  }
  
  .date-title {
    font-size: 14px;
    font-weight: 500;
    color: #666;
    margin-bottom: 12px;
    padding-left: 8px;
    border-left: 3px solid #d4451e;
  }
  
  .history-items {
    display: flex;
    flex-direction: column;
    gap: 12px;
  }
  
  .history-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 12px;
    background: #fafafa;
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.2s;
  }
  
  .history-item:hover {
    background: #f0f0f0;
    transform: translateX(4px);
  }
  
  .item-cover {
    width: 60px;
    height: 60px;
    border-radius: 8px;
    object-fit: cover;
  }
  
  .item-info {
    flex: 1;
  }
  
  .item-title {
    font-size: 15px;
    font-weight: 500;
    color: #333;
    margin-bottom: 4px;
  }
  
  .item-subtitle {
    font-size: 12px;
    color: #999;
    margin-bottom: 6px;
  }
  
  .item-meta {
    display: flex;
    gap: 12px;
    font-size: 11px;
    color: #bbb;
  }
  
  .item-duration {
    color: #d4451e;
  }
  
  .item-actions {
    opacity: 0;
    transition: opacity 0.2s;
  }
  
  .history-item:hover .item-actions {
    opacity: 1;
  }
  
  .action-btn {
    padding: 4px 12px;
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 16px;
    cursor: pointer;
    font-size: 12px;
  }
  
  .action-btn:hover {
    background: #ff4d4f;
    color: white;
    border-color: #ff4d4f;
  }
  
  .empty-state {
    text-align: center;
    padding: 60px 20px;
    color: #999;
    font-size: 14px;
  }
  </style>
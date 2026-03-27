<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import type { Component } from 'vue'
import communication from '@/components/client/profile/communication.vue';
import data from '@/components/client/profile/data.vue';
import history from '@/components/client/profile/history.vue';
import favorite from '@/components/client/profile/favorite.vue';
import like from '@/components/client/profile/like.vue';
import setting from '@/components/client/profile/setting.vue';
//组件映射表--使用Record类型
const componentMap: Record<string, Component> = {
  data: data,
  favorite: favorite,
  history: history,
  like: like,
  communication: communication,
  setting: setting
}
//定义侧边的类型
interface SideListItem {
  name: string;
  component: string;
}

const sideList = reactive<SideListItem[]>([
  { name: "数据看板", component: "data" },
  { name: "我的收藏", component: "favorite" },
  { name: "浏览历史", component: "history" },
  { name: "我的点赞", component: "like" },
  { name: "我的评论", component: "communication" },
  { name: "账户设置", component: "setting" },
])

const currentTab = ref<number>(0)

// 使用 computed 自动处理组件获取,这里监听currentTab
const currentComponent = computed<Component>(() => {
  const componentKey:string = sideList[currentTab.value]!.component
  console.log(typeof(componentKey))
  const component = componentMap[componentKey]
   return component!
})

// 简化 change 函数
const change = (item: SideListItem, index: number) => {
  currentTab.value = index
}
</script>

<template>
  <div class="profile-container">
    <!-- 头部个人信息卡片 -->
    <div class="profile-header">
      <div class="avatar-wrapper">
        <el-avatar 
          shape="square" 
          :size="80" 
          src="https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png"
          class="avatar"
        />
      </div>
      
      <div class="user-info">
        <div class="username">username</div>
        <div class="nickname">nickname</div>
        <div class="address">address</div>
        <div class="title">title</div>
        <div class="join-date">加入于 2024年</div>
      </div>
      
      <div class="stats-wrapper">
        <div class="stats">
          <div class="stat-item">
            <div class="stat-number">23</div>
            <div class="stat-label">收藏</div>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <div class="stat-number">23</div>
            <div class="stat-label">浏览</div>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <div class="stat-number">23</div>
            <div class="stat-label">点赞</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 左侧导航菜单 + 内容区 -->
    <div class="main-layout">
      <!-- 左侧导航 -->
      <div class="sidebar">
        <div 
          v-for="(item, index) in sideList" 
          :key="index" 
          class="sidebar-item"
          :class="{ active: currentTab === index }"
          @click="change(item, index)"
        >
          {{ item.name }}
        </div>
      </div>

      <!-- 内容区 -->
      <div class="content-area">
        <component :is="currentComponent" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.profile-container {
  padding: 24px;
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 24px;
}

.avatar-wrapper {
  margin-right: 24px;
}

.user-info {
  flex: 1;
}

.stats-wrapper {
  margin-top: 16px;
}

.stats {
  display: flex;
  align-items: center;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stat-divider {
  width: 1px;
  height: 32px;
  background-color: #ccc;
  margin: 0 16px;
}

.main-layout {
  display: flex;
}

.sidebar {
  width: 200px;
  padding: 12px;
}

.sidebar-item {
  padding: 10px;
  margin-bottom: 8px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.sidebar-item.active {
  background-color: #667eea;
  color: white;
}

.content-area {
  flex: 1;
  padding: 16px;
}

@media (max-width: 768px) {
  .profile-container {
    padding: 16px;
  }
  
  .profile-header {
    flex-direction: column;
    text-align: center;
    padding: 24px;
    gap: 20px;
  }
  
  .user-info {
    text-align: center;
  }
  
  .stats-wrapper {
    width: 100%;
  }
  
  .stats {
    justify-content: center;
  }
  
  .main-layout {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
    position: static;
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    padding: 12px;
  }
  
  .sidebar-item {
    flex: 1;
    min-width: calc(33.333% - 8px);
    text-align: center;
    padding: 10px 12px;
  }
  
  .sidebar-item::before {
    display: none;
  }
  
  .sidebar-item.active {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
  }
  
  .content-area {
    padding: 16px;
  }
}

/* 平滑滚动 */
html {
  scroll-behavior: smooth;
}

/* 自定义滚动条 */
.content-area::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

.content-area::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.content-area::-webkit-scrollbar-thumb {
  background: #cbd5e0;
  border-radius: 4px;
}

.content-area::-webkit-scrollbar-thumb:hover {
  background: #a0aec0;
}
</style>

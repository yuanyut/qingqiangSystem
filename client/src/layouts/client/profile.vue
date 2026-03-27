<script setup lang="ts">
import { ref, reactive } from 'vue'
import type { Component } from 'vue'  // 使用 type 导入类型
import communication from '@/components/client/profile/communication.vue';
import data from '@/components/client/profile/data.vue';
import history from '@/components/client/profile/history.vue';
import favorite from '@/components/client/profile/favorite.vue';
import like from '@/components/client/profile/like.vue';
import setting from '@/components/client/profile/setting.vue';

// 定义组件键的类型
type ComponentKey = 'data' | 'favorite' | 'history' | 'like' | 'communication' | 'setting'

// 定义侧边栏项的类型
interface SideItem {
  name: string
  componentKey: ComponentKey
}

// 组件映射表 - 使用 Record 类型
const componentMap: Record<ComponentKey, Component> = {
  data: data,
  favorite: favorite,
  history: history,
  like: like,
  communication: communication,
  setting: setting
}

const sideList = reactive<SideItem[]>([
  { name: "数据看板", componentKey: "data" },
  { name: "我的收藏", componentKey: "favorite" },
  { name: "浏览历史", componentKey: "history" },
  { name: "我的点赞", componentKey: "like" },
  { name: "我的评论", componentKey: "communication" },
  { name: "账户设置", componentKey: "setting" },
])

const currentTab = ref<number>(0)

// 添加类型断言，确保 componentKey 存在
const currentComponent = ref<Component>(componentMap[sideList[0]!.componentKey])

const change = (item: SideItem, index: number) => {
  currentTab.value = index
  // 通过映射表获取实际的组件对象
  currentComponent.value = componentMap[item.componentKey]
  console.log(currentComponent.value)
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
          <span class="sidebar-item-text">{{ item.name }}</span>
        </div>
      </div>

      <!-- 右侧内容区 -->
      <div class="content-area">
        <KeepAlive>
              <component :is="currentComponent" />
        </KeepAlive>
      
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 整体容器 */
.profile-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

/* 头部个人信息卡片 */
.profile-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 20px;
  padding: 32px 40px;
  margin-bottom: 24px;
  display: flex;
  align-items: center;
  gap: 32px;
  flex-wrap: wrap;
  box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  transition: transform 0.3s ease;
}

.profile-header:hover {
  transform: translateY(-2px);
}

/* 头像区域 */
.avatar-wrapper {
  flex-shrink: 0;
}

.avatar {
  border: 4px solid rgba(255, 255, 255, 0.3);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.avatar:hover {
  transform: scale(1.05);
}

/* 用户信息区域 */
.user-info {
  flex: 1;
  color: white;
}

.username {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 8px;
  letter-spacing: 1px;
}

.nickname {
  font-size: 16px;
  opacity: 0.9;
  margin-bottom: 8px;
}

.address, .title {
  font-size: 14px;
  opacity: 0.85;
  margin-bottom: 4px;
  display: flex;
  align-items: center;
  gap: 6px;
}

.join-date {
  font-size: 13px;
  opacity: 0.8;
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid rgba(255, 255, 255, 0.2);
}

/* 统计数据区域 */
.stats-wrapper {
  flex-shrink: 0;
}

.stats {
  display: flex;
  align-items: center;
  gap: 24px;
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
  padding: 16px 24px;
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.stat-item {
  text-align: center;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.stat-item:hover {
  transform: translateY(-2px);
}

.stat-number {
  font-size: 28px;
  font-weight: 700;
  color: white;
  line-height: 1.2;
}

.stat-label {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.9);
  margin-top: 4px;
  letter-spacing: 1px;
}

.stat-divider {
  width: 1px;
  height: 40px;
  background: rgba(255, 255, 255, 0.3);
}

/* 主体布局 */
.main-layout {
  display: flex;
  gap: 24px;
  background: transparent;
}

/* 左侧导航栏 */
.sidebar {
  width: 240px;
  flex-shrink: 0;
  background: white;
  border-radius: 16px;
  padding: 16px 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  height: fit-content;
  position: sticky;
  top: 24px;
}

.sidebar-item {
  padding: 12px 20px;
  margin-bottom: 4px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #5a6874;
  font-weight: 500;
  position: relative;
  overflow: hidden;
}

.sidebar-item::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 0 2px 2px 0;
  transition: height 0.3s ease;
}

.sidebar-item:hover {
  background-color: #f8f9fc;
  color: #667eea;
  transform: translateX(4px);
}

.sidebar-item.active {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
  color: #667eea;
  font-weight: 600;
}

.sidebar-item.active::before {
  height: 60%;
}

.sidebar-item-text {
  display: block;
  font-size: 15px;
}

/* 右侧内容区 */
.content-area {
  flex: 1;
  background: white;
  border-radius: 16px;
  padding: 24px;
  min-height: 500px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

/* 响应式设计 */
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
<script setup lang="ts">
import { ref, reactive, computed, onMounted, onActivated } from 'vue'
import type { Component } from 'vue'
import communication from '@/components/client/profile/communication.vue';
import data from '@/components/client/profile/data.vue';
import history from '@/components/client/profile/history.vue';
import favorite from '@/components/client/profile/favorite.vue';
import like from '@/components/client/profile/like.vue';
import setting from '@/components/client/profile/setting.vue';
import { getUserStats } from '@/api/user';
import type { UserStats } from '@/api/user';
import { ElMessage } from 'element-plus';
import { useUserInfoStore } from '@/stores/userInfo';
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
  // { name: "数据看板", component: "data" },
  { name: "我的收藏", component: "favorite" },
  { name: "浏览历史", component: "history" },
  { name: "我的点赞", component: "like" },
  // { name: "我的评论", component: "communication" },
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

// 创建userInfoStore实例
const userInfoStore = useUserInfoStore()

// 用户信息和统计数据
const userStats = ref<UserStats | null>(null)
const loading = ref(true)

// 简化 change 函数
const change = (item: SideListItem, index: number) => {
  currentTab.value = index
}

// 加载用户信息和统计数据
const loadUserData = async () => {
  try {
    loading.value = true
    // 验证token并获取用户信息
    await userInfoStore.validateToken()
    
    // 请求统计数据
    const statsRes = await getUserStats()
    
    if (statsRes.code === 200) {
      userStats.value = statsRes.data
    } else {
      ElMessage.error('获取统计数据失败')
    }
  } catch (error) {
    console.error('加载用户数据失败:', error)
    ElMessage.error('加载失败，请检查网络')
  } finally {
    loading.value = false
  }
}

// 页面加载时获取数据
onMounted(() => {
  loadUserData()
})

// 页面激活时重新加载数据（解决收藏数量不更新的问题）
onActivated(() => {
  loadUserData()
})
</script>

<template>
  <div class="profile-container">
    <!-- 头部个人信息卡片 - 渐变背景增强视觉 -->
    <div class="profile-header">
      <div class="avatar-wrapper">
          <el-avatar 
            shape="square" 
            :size="88" 
            :src="userInfoStore.UserInfos.avatar || 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'"
            class="avatar"
          />
        </div>
        
        <div class="user-info">
          <div class="username">{{ userInfoStore.UserInfos.username  }}</div>
          <div class="nickname">昵称：{{ userInfoStore.UserInfos.nickname || '未设置昵称' }}</div>
          <div class="address">地址：未设置地址</div>
          <div class="join-date">加入于 未知</div>
        </div>
      
      <div class="stats-wrapper">
        <div class="stats">
          <div class="stat-item">
            <div class="stat-number">{{ userStats?.favoriteCount || 0 }}</div>
            <div class="stat-label">收藏</div>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <div class="stat-number">{{ userStats?.viewCount || 0 }}</div>
            <div class="stat-label">浏览</div>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <div class="stat-number">{{ userStats?.likeCount || 0 }}</div>
            <div class="stat-label">点赞</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 左侧导航菜单 + 内容区 -->
    <div class="main-layout">
      <!-- 左侧导航 - 现代化卡片式菜单 -->
      <div class="sidebar">
        <div 
          v-for="(item, index) in sideList" 
          :key="index" 
          class="sidebar-item"
          :class="{ active: currentTab === index }"
          @click="change(item, index)"
        >
          <span class="sidebar-icon">
            <!-- <i v-if="item.component === 'data'" class="el-icon-data-line"></i> -->
            <i v-if="item.component === 'favorite'" class="el-icon-star-on"></i>
            <i v-else-if="item.component === 'history'" class="el-icon-time"></i>
            <i v-else-if="item.component === 'like'" class="el-icon-thumb"></i>
            <i v-else-if="item.component === 'communication'" class="el-icon-chat-dot-round"></i>
            <i v-else-if="item.component === 'setting'" class="el-icon-setting"></i>
            <i v-else class="el-icon-menu"></i>
          </span>
          <span class="sidebar-name">{{ item.name }}</span>
        </div>
      </div>

      <!-- 内容区 - 优雅卡片容器 -->
      <div class="content-area">
        <component :is="currentComponent" />
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 全局变量 - 优雅配色 */
:root {
  --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  --primary-light: #8b74d9;
  --primary-dark: #5a67d8;
  --bg-white: #ffffff;
  --bg-gray: #f8fafc;
  --text-dark: #1e293b;
  --text-gray: #64748b;
  --border-light: #eef2f6;
  --shadow-sm: 0 1px 3px rgba(0,0,0,0.05);
  --shadow-md: 0 4px 12px rgba(0,0,0,0.08);
  --shadow-lg: 0 8px 24px rgba(0,0,0,0.12);
  --radius-md: 16px;
  --radius-lg: 24px;
  --transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
}

.profile-container {
  margin: 0 auto;
  /* padding: 28px 32px; */
  background: linear-gradient(180deg, #f9fafc 0%, #ffffff 100%);
  min-height: 100vh;
}

/* 头部个人信息卡片 - 玻璃拟态风格 */
.profile-header {
  background: linear-gradient(145deg, #ffffff 0%, #fefefe 100%);
  backdrop-filter: blur(10px);
  border-radius: var(--radius-lg);
  padding: 32px 36px;
  margin-bottom: 32px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: var(--shadow-md);
  border: 1px solid rgba(255,255,255,0.6);
  transition: var(--transition);
  position: relative;
  overflow: hidden;
}

.profile-header::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: var(--primary-gradient);
}

.profile-header:hover {
  box-shadow: var(--shadow-lg);
  transform: translateY(-2px);
}

.avatar-wrapper {
  margin-right: 28px;
  position: relative;
}

.avatar {
  border-radius: 24px !important;
  box-shadow: 0 8px 20px rgba(0,0,0,0.1);
  transition: var(--transition);
  border: 3px solid rgba(102,126,234,0.2);
}

.avatar:hover {
  transform: scale(1.02);
  border-color: rgba(102,126,234,0.5);
}

.user-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.username {
  font-size: 28px;
  font-weight: 700;
  background: var(--primary-gradient);
  background-clip: text;
  -webkit-background-clip: text;
  color: black;
  letter-spacing: -0.3px;
}

.nickname {
  font-size: 16px;
  color: var(--text-gray);
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
}



.address {
  font-size: 14px;
  color: #94a3b8;
  display: flex;
  align-items: center;
  gap: 6px;
}

.address::before {
  content: '📍';
  font-size: 12px;
}

.title {
  font-size: 13px;
  color: #a78bfa;
  background: #f5f3ff;
  display: inline-block;
  width: fit-content;
  padding: 4px 12px;
  border-radius: 30px;
  font-weight: 500;
  margin-top: 4px;
}

.join-date {
  font-size: 12px;
  color: #cbd5e1;
  margin-top: 2px;
}

.stats-wrapper {
  background: #f8fafc;
  padding: 16px 24px;
  border-radius: 40px;
  box-shadow: inset 0 1px 2px rgba(0,0,0,0.02), 0 2px 4px rgba(0,0,0,0.02);
}

.stats {
  display: flex;
  align-items: center;
  gap: 8px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 8px;
  cursor: default;
  transition: var(--transition);
}

.stat-item:hover .stat-number {
  color: #667eea;
  transform: translateY(-2px);
}

.stat-number {
  font-size: 28px;
  font-weight: 800;
  color: var(--text-dark);
  line-height: 1.2;
  transition: var(--transition);
}

.stat-label {
  font-size: 13px;
  color: var(--text-gray);
  font-weight: 500;
  margin-top: 4px;
}

.stat-divider {
  width: 1px;
  height: 40px;
  background: linear-gradient(180deg, transparent, #e2e8f0, transparent);
}

/* 主布局 - 双栏设计 */
.main-layout {
  display: flex;
  gap: 28px;
  min-height: 500px;
}

/* 左侧导航 - 现代化菜单栏 */
.sidebar {
  width: 200px;
  background: var(--bg-white);
  border-radius: var(--radius-lg);
  padding: 20px 12px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  backdrop-filter: blur(4px);
  transition: var(--transition);
  height: fit-content;
  position: sticky;
  top: 24px;
}

.sidebar-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 18px;
  margin-bottom: 8px;
  border-radius: 14px;
  cursor: pointer;
  transition: var(--transition);
  color: var(--text-gray);
  font-weight: 500;
  position: relative;
}

.sidebar-icon {
  font-size: 20px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 28px;
  transition: var(--transition);
}

.sidebar-name {
  font-size: 15px;
  font-weight: 500;
}

.sidebar-item:hover {
  background: #b3b4b5;
  color: var(--primary-dark);
  transform: translateX(4px);
}

.sidebar-item.active {
  background: var(--primary-gradient);
  color: rgb(96, 96, 96);
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.25);
}

.sidebar-item.active .sidebar-icon {
  color: white;
}

.sidebar-item.active::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 60%;
  background: white;
  border-radius: 3px;
}

/* 内容区域 - 优雅卡片容器 */
.content-area {
  flex: 1;
  background: var(--bg-white);
  border-radius: var(--radius-lg);
  padding: 28px 32px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  transition: var(--transition);
  min-height: 500px;
}

.content-area:hover {
  box-shadow: var(--shadow-md);
}

/* 响应式设计 - 平板与手机 */
@media (max-width: 1024px) {
  .profile-container {
    padding: 20px;
  }
  
  .profile-header {
    flex-wrap: wrap;
    gap: 20px;
    padding: 24px;
  }
  
  .stats-wrapper {
    margin-top: 8px;
    width: 100%;
    justify-content: center;
  }
  
  .stats {
    justify-content: center;
  }
}

@media (max-width: 768px) {
  .profile-container {
    padding: 16px;
  }
  
  .profile-header {
    flex-direction: column;
    text-align: center;
    padding: 28px 20px;
  }
  
  .avatar-wrapper {
    margin-right: 0;
  }
  
  .user-info {
    text-align: center;
    align-items: center;
  }
  
  .title {
    margin: 0 auto;
  }
  
  .stats-wrapper {
    width: 100%;
    padding: 12px 16px;
  }
  
  .stats {
    justify-content: center;
  }
  
  .main-layout {
    flex-direction: column;
    gap: 20px;
  }
  
  .sidebar {
    width: 100%;
    position: static;
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    padding: 16px;
    background: transparent;
    box-shadow: none;
    border: none;
  }
  
  .sidebar-item {
    flex: 1;
    min-width: calc(33.333% - 10px);
    justify-content: center;
    background: var(--bg-white);
    border: 1px solid var(--border-light);
    margin-bottom: 0;
    padding: 12px 8px;
    box-shadow: var(--shadow-sm);
  }
  
  .sidebar-item.active {
    background: var(--primary-gradient);
  }
  
  .sidebar-item.active::before {
    display: none;
  }
  
  .sidebar-item:hover {
    transform: translateY(-2px);
  }
  
  .content-area {
    padding: 20px;
  }
  
  .username {
    font-size: 24px;
  }
  
  .stat-number {
    font-size: 24px;
  }
}

@media (max-width: 480px) {
  .sidebar-item {
    min-width: calc(50% - 8px);
    font-size: 13px;
  }
  
  .sidebar-name {
    font-size: 13px;
  }
  
  .sidebar-icon {
    font-size: 16px;
  }
  
  .content-area {
    padding: 16px;
  }
}

/* 自定义滚动条 */
.content-area::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

.content-area::-webkit-scrollbar-track {
  background: #f1f5f9;
  border-radius: 10px;
}

.content-area::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 10px;
}

.content-area::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}

/* 平滑滚动 */
html {
  scroll-behavior: smooth;
}

/* 动画效果 */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.profile-header,
.sidebar,
.content-area {
  animation: fadeInUp 0.4s ease-out;
}

.content-area {
  animation-delay: 0.1s;
}
</style>

<!-- 补充 Element Plus 图标样式（如果项目未引入，可使用 Unicode 或自行替换） -->
<style>
/* 确保图标兼容，如果没有安装 element-plus/icons-vue，可以使用备用字符图标 */
.sidebar-icon i {
  font-style: normal;
  display: inline-block;
}

/* 备用图标样式 - 使用 Unicode 符号 */
.sidebar-item .sidebar-icon {
  font-family: system-ui, -apple-system, 'Segoe UI', sans-serif;
}

/* 如果 element 图标不可用，回退显示 Emoji 风格 */
.sidebar-icon i[class*="el-icon"]:not([class*="el-icon-"]):before {
  content: "✨";
}
</style>
<script setup lang="ts">
import { ref, watch } from 'vue';
import { useUserInfoStore } from '@/stores/userInfo'
import { onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { getAdminInfo } from '@/api/api'

const router = useRouter()
const userInfoStore = useUserInfoStore()






// const emit = defineEmits<{
//   (e: 'changeTabs', index: number): void
// }>()



const info = reactive({
  role:"",
  address: null,
  avatar:"",
  createTime:"",
  id: 2,
  nickname:"",
  username: ""
});

console.log(info)

onMounted(async () => {
  const res = await getAdminInfo();
  Object.assign(info, res.data);
});


// 添加登出函数
const handleLogout = () => {
  // 先清除 localStorage（包括 token）
  localStorage.clear()
  // 使用 Pinia store 登出
  userInfoStore.logout()
  // 跳转到登录页（相对路径）
  router.push('/')
}

</script>

<template>
  <div class="navbar">
    <div class="nav-logo">
      <span class="logo-text">秦腔宣传后台管理系统</span>

    </div>



    <div class="nav-user">
      <el-dropdown trigger="click" placement="bottom-end">
        <div class="user-dropdown-trigger">
          <el-avatar
            :src="info.avatar || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'"
            size="default" class="user-avatar" />

          <el-icon class="dropdown-icon">
            <arrow-down />
          </el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu class="custom-dropdown">
            <el-dropdown-item class="dropdown-item user-info-item">
              <div class="user-info">
                <div class="user-detail">
                  <div class="user-nickname">{{ info.nickname }}</div>
                  <div class="user-username">{{info.username }}</div>
                </div>
              </div>
            </el-dropdown-item>
            <el-dropdown-item divided class="dropdown-item logout-item" @click="handleLogout">
              <div class="menu-item-content">
                <svg t="1774517987630" class="icon" viewBox="0 0 1024 1024" version="1.1"
                  xmlns="http://www.w3.org/2000/svg" p-id="4608" width="20" height="20">
                  <path
                    d="M570.10688 47.2576H453.90336v525.52192h116.1984V47.2576z m208.86016 86.8864L697.6512 214.9376c92.87168 63.45728 162.42688 173.14304 162.42688 300.05248 0 190.47936-156.544 346.28608-348.17536 346.28608-191.42656 0-348.17536-155.80672-348.17536-346.28608 0-126.90944 69.76-236.5952 168.30976-294.27712L245.0432 134.144C129.05472 220.71296 47.73376 359.18848 47.73376 514.99008c0 254.03904 209.0752 461.75232 464.16384 461.75232 255.29856 0 464.36864-207.70816 464.36864-461.75232 0.00512-155.8016-81.31072-294.27712-197.2992-380.84608z"
                    fill="" p-id="4609"></path>
                </svg>
                <span>退出登录</span>
              </div>
            </el-dropdown-item>


          </el-dropdown-menu>
        </template>
</el-dropdown>
</div>
  </div>
</template>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
  height: 60px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  position: sticky;
  top: 0;
  z-index: 100;
}

/* Logo 区域 */
.nav-logo {
  display: flex;
  flex-direction: column;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.nav-logo:hover {
  transform: translateY(-1px);
}

.logo-text {
  font-size: 24px;
  font-weight: 800;
  background: linear-gradient(135deg, #8B5A2B 0%, #D2691E 100%);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  letter-spacing: 1px;
}

.logo-sub {
  font-size: 10px;
  color: #b87333;
  letter-spacing: 1px;
  margin-top: -2px;
}

/* 菜单区域 */
.nav-menu {
  display: flex;
  gap: 8px;
  flex: 1;
  justify-content: center;
}

.nav-item {
  position: relative;
  padding: 8px 20px;
  font-size: 15px;
  font-weight: 500;
  color: #dddddd;
  cursor: pointer;
  transition: all 0.3s ease;
  white-space: nowrap;
  border-radius: 8px;
}

.nav-item:hover {
  color: #d2691e;
  background: rgba(210, 105, 30, 0.05);
}

.nav-item.active {
  color: #d2691e;
  background: linear-gradient(135deg, rgba(210, 105, 30, 0.1), rgba(139, 90, 43, 0.05));
}

.nav-indicator {
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 3px;
  background: linear-gradient(90deg, #d2691e, #8B5A2B);
  border-radius: 3px;
  transition: width 0.3s ease;
}

.nav-item.active .nav-indicator,
.nav-item:hover .nav-indicator {
  width: 30px;
}

/* 用户区域 */
.nav-user {
  position: relative;
}

.user-dropdown-trigger {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 6px 12px;
  border-radius: 40px;
  cursor: pointer;
  transition: all 0.3s ease;
  background: transparent;
}

.user-dropdown-trigger:hover {
  background: rgba(210, 105, 30, 0.08);
  transform: translateY(-1px);
}

.user-avatar {
  transition: transform 0.2s ease;
}

.user-dropdown-trigger:hover .user-avatar {
  transform: scale(1.05);
}

.user-name {
  font-size: 14px;
  font-weight: 500;
  color: #4a5568;
  max-width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.dropdown-icon {
  font-size: 14px;
  color: #9ca3af;
  transition: transform 0.2s ease;
}

.user-dropdown-trigger:hover .dropdown-icon {
  transform: rotate(180deg);
  color: #d2691e;
}

/* 下拉菜单样式 */
:deep(.custom-dropdown) {
  border: none !important;
  border-radius: 12px !important;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12) !important;
  padding: 8px 0 !important;
  margin-top: 8px !important;
  min-width: 220px !important;
}

:deep(.dropdown-item) {
  padding: 10px 16px !important;
  transition: all 0.2s ease !important;
}

:deep(.dropdown-item:hover) {
  background: #fef5ed !important;
}

.user-info-item {
  cursor: default !important;
}

.user-info-item:hover {
  background: transparent !important;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 4px 0;
}

.user-detail {
  display: flex;
  flex-direction: column;
}

.user-nickname {
  font-size: 14px;
  font-weight: 600;
  color: #afaeae;
}

.user-username {
  font-size: 11px;
  color: #888888;
}

.menu-item-content {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 14px;
  color: #4a5568;
}

.menu-item-content .el-icon {
  font-size: 16px;
  color: #b87333;
}

.logout-item .menu-item-content {
  color: #e53e3e;
}

.logout-item .menu-item-content .el-icon {
  color: #e53e3e;
}

.login-item {
  justify-content: center;
  color: #d2691e;
  font-weight: 500;
}

.login-item .el-icon {
  font-size: 18px;
}

/* 分割线样式 */
:deep(.el-dropdown-menu__item--divided) {
  border-top-color: #f0f0f0 !important;
  margin-top: 8px !important;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .nav-menu {
    gap: 4px;
  }

  .nav-item {
    padding: 8px 12px;
    font-size: 14px;
  }
}

@media (max-width: 992px) {
  .navbar {
    padding: 0 20px;
  }

  .nav-item {
    padding: 8px 10px;
    font-size: 13px;
  }

  .user-name {
    display: none;
  }
}

@media (max-width: 768px) {
  .nav-menu {
    overflow-x: auto;
    justify-content: flex-start;
    padding: 0 10px;
    -webkit-overflow-scrolling: touch;
  }

  .nav-item {
    white-space: nowrap;
  }

  .nav-logo {
    display: none;
  }
}
</style>

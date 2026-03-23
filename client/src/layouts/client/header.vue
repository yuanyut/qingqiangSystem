<script setup lang="ts">
import { reactive, ref, watch } from 'vue';
import { useUserInfoStore } from '@/stores/userInfo'
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import {ElMessage} from 'element-plus'
const router = useRouter()
const userInfoStore = useUserInfoStore()

const props = defineProps<{
    current: number
}>()

const currentTab = ref<number>(props.current);
const menuLists = reactive([
    '首页',
    '秦腔剧目','名家介绍','推荐广场',
    '秦腔文化',
    '秦腔资讯',
    '个人中心'
])

const emit = defineEmits<{
    (e: 'changeTabs', index: number): void
}>()

watch(() => props.current, (newVal) => {
    currentTab.value = newVal
})

const handleTabClick = (index: number): void => {
    if(index == 6 && !userInfoStore.UserInfos.isLogin){
         ElMessage({
            message: '请先登录',
            type: 'warning',
        })
        return 
    }
    else{
        currentTab.value = index
        emit('changeTabs', index)
    }
    
}

onMounted(() => {
    console.log(userInfoStore.UserInfos);
})


// 添加登出函数
const handleLogout = () => {
    // 实现登出逻辑
    router.push('/login')
}

</script>

<template>
    <div class="navbar">
        <div class="nav-logo">
            <span class="logo-text">秦韵</span>
            <span class="logo-sub">Qin Opera</span>
        </div>
        
        <div class="nav-menu">
            <span 
                v-for="(item, index) in menuLists" 
                :key="index" 
                :class="{ active: currentTab === index }"
                @click="handleTabClick(index)"
                class="nav-item"
            >
                {{ item }}
                <span class="nav-indicator"></span>
            </span>
        </div>
        
        <div class="nav-user">
            <el-dropdown trigger="click" placement="bottom-end">
                <div class="user-dropdown-trigger">
                    <el-avatar 
                        :src="userInfoStore.UserInfos.avatar || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" 
                        size="default"
                        class="user-avatar"
                    />
                    <span class="user-name" v-if="userInfoStore.UserInfos.isLogin">
                        {{ userInfoStore.UserInfos.nickname || userInfoStore.UserInfos.username }}
                    </span>
                    <el-icon class="dropdown-icon">
                        <arrow-down />
                    </el-icon>
                </div>
                <template #dropdown>
                    <el-dropdown-menu class="custom-dropdown">
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin" class="dropdown-item user-info-item">
                            <div class="user-info">
                                <el-avatar :src="userInfoStore.UserInfos.avatar" size="small" />
                                <div class="user-detail">
                                    <div class="user-nickname">{{ userInfoStore.UserInfos.nickname }}</div>
                                    <div class="user-username">{{ userInfoStore.UserInfos.username }}</div>
                                </div>
                            </div>
                        </el-dropdown-item>
                        
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin" divided class="dropdown-item">
                            <div class="menu-item-content">
                                <el-icon><user /></el-icon>
                                <span>个人中心</span>
                            </div>
                        </el-dropdown-item>
                        
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin" class="dropdown-item">
                            <div class="menu-item-content">
                                <el-icon><star /></el-icon>
                                <span>我的收藏</span>
                            </div>
                        </el-dropdown-item>
                        
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin" class="dropdown-item">
                            <div class="menu-item-content">
                                <el-icon><history /></el-icon>
                                <span>浏览记录</span>
                            </div>
                        </el-dropdown-item>
                        
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin" divided class="dropdown-item logout-item" @click="handleLogout">
                            <div class="menu-item-content">
                                <el-icon><switch-button /></el-icon>
                                <span>退出登录</span>
                            </div>
                        </el-dropdown-item>
                        
                        <el-dropdown-item v-if="!userInfoStore.UserInfos.isLogin" class="dropdown-item" @click="router.push('/login')">
                            <div class="menu-item-content login-item">
                                <el-icon><user-filled /></el-icon>
                                <span>登录/注册</span>
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
    background: #2C241A;
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
    color: #2d3748;
}

.user-username {
    font-size: 11px;
    color: #9ca3af;
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

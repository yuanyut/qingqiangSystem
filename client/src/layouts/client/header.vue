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
                                <!-- <el-avatar :src="userInfoStore.UserInfos.avatar" size="small" /> -->
                                <div class="user-detail">
                                    <div class="user-nickname">{{ userInfoStore.UserInfos.nickname }}</div>
                                    <div class="user-username">{{ userInfoStore.UserInfos.username }}</div>
                                </div>
                            </div>
                        </el-dropdown-item>
                        
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin" divided class="dropdown-item">
                            <div class="menu-item-content">
                                <svg t="1774517861899" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1634" width="20" height="20"><path d="M858.496 763.584a374.016 374.016 0 0 0-80.64-119.488 375.68 375.68 0 0 0-119.488-80.64l-1.152-0.448a247.936 247.936 0 1 0-290.432 0.128l-1.152 0.448c-44.8 18.944-85.056 46.016-119.552 80.64a375.68 375.68 0 0 0-110.08 257.6 8 8 0 0 0 8 8.192h60.032a8 8 0 0 0 7.936-7.808 298.112 298.112 0 0 1 87.808-204.288A298.048 298.048 0 0 1 512 609.92c80.192 0 155.52 31.232 212.224 87.936a298.112 298.112 0 0 1 87.744 204.288 7.936 7.936 0 0 0 8.064 7.808h59.968c4.48 0 8.128-3.712 8-8.192a372.736 372.736 0 0 0-29.44-138.24l-0.064 0.064zM512 534.016a170.88 170.88 0 0 1-121.6-50.432 170.88 170.88 0 0 1-50.368-121.6c0-45.888 17.92-89.088 50.368-121.6A170.88 170.88 0 0 1 512 190.08c45.888 0 89.088 17.92 121.6 50.368a170.88 170.88 0 0 1 50.368 121.6 170.88 170.88 0 0 1-50.368 121.6 170.88 170.88 0 0 1-121.6 50.432v-0.064z" fill="#000000" p-id="1635"></path></svg>
                                <span>个人中心</span>
                            </div>
                        </el-dropdown-item>
                        
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin" class="dropdown-item">
                            <div class="menu-item-content">
                                <svg t="1774517903309" class="icon" viewBox="0 0 1059 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2628" width="20" height="20"><path d="M253.488042 1024c-16.9 0-33.2875-5.1125-47.6125-15.3625-26.625-18.425-39.425-49.6625-34.3125-81.925l40.9625-251.9c1.5375-10.2375-1.5375-20.475-8.7-27.65L28.213042 466.4375c-22.0125-22.525-29.1875-55.3-19.45-84.9875 9.725-29.7 35.325-51.2 66.05-55.8125l237.575-36.35c10.75-1.5375 19.4625-8.1875 24.0625-17.925L441.388042 48.125c13.825-29.7 42.5-48.125 75.2625-48.125s61.4375 18.4375 75.2625 48.125l104.45 223.2375c4.6125 9.725 13.825 16.375 24.0625 17.925L958.000542 325.625a82.355 82.355 0 0 1 66.05 55.8125c10.2375 29.7 2.5625 62.4625-19.45 84.9875l-175.625 180.7375c-7.1625 7.175-10.2375 17.925-8.7 27.65l40.9625 251.9c5.125 31.75-8.1875 63.4875-34.3 81.925-26.1125 18.4375-59.9 20.4875-88.0625 4.6125l-206.85-114.6875c-9.725-5.1125-20.9875-5.1125-30.7125 0l-207.3625 115.2c-12.8125 6.65-26.6375 10.2375-40.4625 10.2375zM516.650542 51.2c-12.8 0-23.55 7.1625-29.1875 18.4375L383.525542 292.875c-11.775 25.0875-35.325 43.0125-62.975 47.1l-237.575 36.35c-12.2875 2.05-21.5 9.7375-25.6 21.5-4.1 11.775-1.025 24.0625 7.675 32.775L240.688042 611.325c18.4375 18.95 26.625 45.5625 22.525 71.675L222.250542 934.9125c-2.05 12.8 3.075 24.575 13.3125 31.7375 10.2375 7.175 23.0375 7.6875 33.7875 1.5375l207.3625-115.2c25.0875-13.825 55.3-13.825 80.3875 0l207.3625 115.2c10.75 6.1375 23.55 5.625 33.8-1.5375 10.2375-7.1625 15.3625-18.95 13.3125-31.7375L770.625542 683.0125c-4.1-26.1125 4.1-52.7375 22.525-71.675l175.625-180.7375c8.7-8.7 11.2625-20.9875 7.675-32.775-4.0875-11.775-13.3125-19.9625-25.6-21.5l-237.5625-36.35c-27.65-4.0875-51.2-22.0125-62.975-47.1L545.838042 69.6375c-5.625-11.2625-16.375-18.4375-29.1875-18.4375z m0 0" p-id="2629"></path></svg>
                                <span>我的收藏</span>
                            </div>
                        </el-dropdown-item>
                        
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin" class="dropdown-item">
                            <div class="menu-item-content">
                                <svg t="1774517947496" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3622" width="20" height="20"><path d="M469.333333 554.666667l128 128 59.733334-59.733334-102.4-102.4V341.333333h-85.333334v213.333334zM85.333333 256l149.333334 149.333333 29.866666 29.866667C298.666667 332.8 396.8 256 512 256c140.8 0 256 115.2 256 256s-115.2 256-256 256c-128 0-234.666667-93.866667-251.733333-217.6l-85.333334-85.333333c-4.266667 17.066667-4.266667 29.866667-4.266666 46.933333 0 187.733333 153.6 341.333333 341.333333 341.333333s341.333333-153.6 341.333333-341.333333-153.6-341.333333-341.333333-341.333333C405.333333 170.666667 311.466667 221.866667 247.466667 298.666667l-42.666667-42.666667H85.333333z" fill="#444444" p-id="3623"></path></svg>
                                <span>浏览记录</span>
                            </div>
                        </el-dropdown-item>
                        
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin" divided class="dropdown-item logout-item" @click="handleLogout">
                            <div class="menu-item-content">
                                <svg t="1774517987630" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4608" width="20" height="20"><path d="M570.10688 47.2576H453.90336v525.52192h116.1984V47.2576z m208.86016 86.8864L697.6512 214.9376c92.87168 63.45728 162.42688 173.14304 162.42688 300.05248 0 190.47936-156.544 346.28608-348.17536 346.28608-191.42656 0-348.17536-155.80672-348.17536-346.28608 0-126.90944 69.76-236.5952 168.30976-294.27712L245.0432 134.144C129.05472 220.71296 47.73376 359.18848 47.73376 514.99008c0 254.03904 209.0752 461.75232 464.16384 461.75232 255.29856 0 464.36864-207.70816 464.36864-461.75232 0.00512-155.8016-81.31072-294.27712-197.2992-380.84608z" fill="" p-id="4609"></path></svg>
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

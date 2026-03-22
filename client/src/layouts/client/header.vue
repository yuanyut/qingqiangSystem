<script setup lang="ts">
import { reactive, ref, watch } from 'vue';
import {useUserInfoStore}  from '@/stores/userInfo'
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const userInfoStore = useUserInfoStore()
const props = defineProps<{
    current: number
}>()
// 初始化currentTab,父组件传入的 current,组件创建时初始化
const currentTab = ref<number>(props.current);
const menuLists = reactive([
    '首页',
    '秦腔剧目',
    '秦腔文化',      // 包含文章、图片、视频等文化资源（对应内容管理模块）
    '名家介绍',
    '秦腔资讯',
    '推荐广场',      // 对应个性化推荐模块（基于用户行为的定制化推荐）
    '交流社区',      // 对应互动审核模块（评论、留言管理）
    '个人中心'
])
// 声明 emits
const emit = defineEmits<{
    (e: 'changeTabs', index: number): void
}>()
//监听props.current变化，更新currentTab。不加这个，在点击的时候组件不会重新创建，因此需要监听
watch(() => props.current, (newVal) => {
    currentTab.value = newVal
})
// 点击处理函数
const handleTabClick = (index: number): void => {
    console.log('点击索引:', index)
    currentTab.value = index  // 更新当前选中状态
    emit('changeTabs', index)  // 触发事件

}
onMounted(()=>{
    console.log(userInfoStore.UserInfos);
})
</script>
<template>
    <div class="header">
        <div>
            <span v-for="(item, index) in menuLists" :key="index" :class="{ active: currentTab === index }"
                @click="handleTabClick(index)">
                {{ item }}
            </span>
        </div>
        <div>
            <el-dropdown>
                <span class="el-dropdown-link">
                    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" size="default" />
                    <el-icon class="el-icon--right">
                        <arrow-down />
                    </el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu >
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin">账号：{{userInfoStore.UserInfos.username}}</el-dropdown-item>
                        <el-dropdown-item>{{userInfoStore.UserInfos.nickname}}</el-dropdown-item>
                        <el-dropdown-item v-if="userInfoStore.UserInfos.isLogin">退出登录</el-dropdown-item>
                        
                        <el-dropdown-item v-if="!userInfoStore.UserInfos.isLogin" @click="router.push('/login')">登录/注册</el-dropdown-item>
                        <!-- <el-dropdown-item disabled>Action 4</el-dropdown-item>
                        <el-dropdown-item divided>Action 5</el-dropdown-item> -->
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>

</template>
<style scoped>
.active {
    color: rgb(26, 99, 11);
}

span {
    margin: 0 20px;
}

.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

:deep(.el-dropdown-menu) {
    border: none !important;
    outline: none !important;
    box-shadow: none !important;
    background-color: #fff !important;
}

:deep(.el-dropdown-menu__item) {
    border: none !important;
    outline: none !important;
    box-shadow: none !important;
    background-color: #fff !important;
    transition: background-color 0.2s ease !important;
}

:deep(.el-dropdown-menu__item:hover) {
    border: none !important;
    outline: none !important;
    box-shadow: none !important;
    background-color: #f5f7fa !important;
}

:deep(.el-dropdown-menu__item:focus) {
    border: none !important;
    outline: none !important;
    box-shadow: none !important;
}

:deep(.el-dropdown-menu__item:active) {
    border: none !important;
    outline: none !important;
    box-shadow: none !important;
}

:deep(.el-dropdown-menu__item:focus-visible) {
    border: none !important;
    outline: none !important;
    box-shadow: none !important;
}

/* Target the popper container */
:deep(.el-popper) {
    border: none !important;
    outline: none !important;
    box-shadow: none !important;
}

/* Target the dropdown trigger */
:deep(.el-dropdown-link) {
    border: none !important;
    outline: none !important;
    box-shadow: none !important;
}

:deep(.el-dropdown-link:hover) {
    border: none !important;
    outline: none !important;
    box-shadow: none !important;
}
</style>
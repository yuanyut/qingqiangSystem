<script setup lang="ts">
import { reactive,ref, watch } from 'vue';

const props = defineProps<{
    current: number
}>()
// 初始化currentTab,父组件传入的 current,组件创建时初始化
const currentTab =ref<number>(props.current);
const menuLists = reactive([
    '首页', '秦腔剧目', '名家介绍', '秦腔资讯', '戏曲文化', '交流区', '个人中心'
])
// 声明 emits
const emit = defineEmits<{
    (e: 'changeTabs', index: number): void
}>()
//监听props.current变化，更新currentTab。不加这个，在点击的时候组件不会重新创建，因此需要监听
watch(()=>props.current,(newVal)=>{
    currentTab.value=newVal
})
// 点击处理函数
const handleTabClick = (index: number): void => {
    console.log('点击索引:', index)
    
        currentTab.value = index  // 更新当前选中状态
        emit('changeTabs', index)  // 触发事件
    
}
</script>
<template>
    <span v-for="(item, index) in menuLists" :key="index" :class="{ active: currentTab === index } "  @click="handleTabClick(index)">
        {{ item }}
    </span>
</template>
<style scoped>
.active {
    color: rgb(26, 99, 11);
}
span{
    margin: 0 20px;
}
</style>
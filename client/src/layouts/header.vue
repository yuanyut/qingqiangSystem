<script setup lang="ts">
import { reactive } from 'vue';

const props = defineProps<{
    current: number
}>()
const menuLists = reactive([
    '首页', '秦腔剧目', '名家介绍', '秦腔资讯', '戏曲文化', '交流区', '个人中心'
])
</script>
<template>
    <span v-for="(item, index) in menuLists" :key="index" :class="{ active: props.current === index }">
        {{ item }}
    </span>
</template>
<style scoped>
.active {
    color: blanchedalmond;
}
</style>
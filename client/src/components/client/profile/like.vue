<script setup>
import { ref, computed, reactive } from 'vue'
// 收藏列表数据
const favorites = ref([
    {
        id: 1,
        type: 'drama',
        title: '三滴血',
        subtitle: '李淑芳 · 剧目',
        coverUrl: '/images/sandixue.jpg',
        collectTime: '2024-03-24',
        collectTimeText: '3天前'
    },
    {
        id: 2,
        type: 'drama',
        title: '火焰驹',
        subtitle: '李小锋 · 剧目',
        coverUrl: '/images/huoyanju.jpg',
        collectTime: '2024-03-22',
        collectTimeText: '5天前'
    },
    {
        id: 3,
        type: 'artist',
        title: '李淑芳',
        subtitle: '国家一级演员',
        coverUrl: '/images/lisf.jpg',
        collectTime: '2024-03-18',
        collectTimeText: '1周前'
    },
    {
        id: 4,
        type: 'news',
        title: '秦腔艺术节',
        subtitle: '资讯',
        coverUrl: '/images/news.jpg',
        collectTime: '2024-03-11',
        collectTimeText: '2周前'
    },
    {
        id: 5,
        type: 'video',
        title: '旦角发声技巧',
        subtitle: '教学视频',
        coverUrl: '/images/video.jpg',
        collectTime: '2024-03-11',
        collectTimeText: '2周前'
    },
    {
        id: 6,
        type: 'news',
        title: '#三滴血话题',
        subtitle: '热门讨论',
        coverUrl: '',
        collectTime: '2024-03-04',
        collectTimeText: '3周前'
    }
])
const cate = reactive([
    {
        name: "全部",
        type: "all"
    },
    {
        name: "剧目",
        type: "drama"
    },
    {
        name: "名家",
        type: "artist"
    },
    {
        name: "资讯",
        type: "news"
    },
    {
        name: "视频",
        type: "video"
    },
])
const currentCate = ref('all')
const chuliList = computed(() => {
    if (currentCate.value === 'all') {
        return favorites.value
    }
    return favorites.value.filter(item => item.type === currentCate.value)
})
const chuliCate = function (cateType) {
    console.log(cateType)
    currentCate.value = cateType
}
console.log("liebiao", chuliList.value)
const cancle = (id) => {
    favorites.value = favorites.value.filter(item => item.id !== id)
}
// 去推荐页
const goToRecommend = () => {
  // TODO: 跳转到推荐广场
}
</script>
<template>
    <div>
        <!-- 头部 -->
        <div class="section-header">
            <h3>⭐ 我的点赞</h3>
        </div>

        <!-- 筛选栏 -->
        <div class="filter-bar">
            <div v-for="(item, index) in cate" :key="index" @click="chuliCate(item.type)">{{ item.name }}</div>
        </div>

        <!-- 列表 -->
        <div class="favorites-list" v-if="chuliList.length > 0">
            <div v-for="item in chuliList" :key="item.id" class="favorite-card">
                <!-- 封面图 -->
                <div class="card-cover">
                    <img :src="item.coverUrl || '/default-cover.jpg'" :alt="item.title">
                </div>

                <!-- 信息区 -->
                <div class="card-info">
                    <h4 class="card-title">{{ item.title }}</h4>
                    <p class="card-subtitle">{{ item.subtitle }}</p>
                    <span class="collect-time">点赞于 {{ item.collectTimeText }}</span>
                </div>

                <!-- 操作区 -->
                <button class="cancel-btn" @click="cancle(item.id)">
                    取消点赞
                </button>
            </div>
        </div>
        <div class="empty-state" v-else>
            <span>📭</span>
            <p>暂无点赞内容</p>
            <button class="browse-btn" @click="goToRecommend">去逛逛</button>
        </div>
    </div>
</template>



<style scoped>
/* 样式代码略 */
</style>
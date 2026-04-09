<script setup lang="ts">
import opearHeader from '@/components/Contents/Article/tables/opearHeader.vue';
import opear from '@/components/Contents/Article/tables/opear.vue'
import tableContent from '@/components/Contents/Article/tables/tableContent.vue';
import { reactive, watch, ref } from 'vue';

// 文章接口定义
interface Article {
    id: number
    coverUrl: string
    title: string
    category: string
    categoryId: number | string
    author: string
    authorId: number | string
    publishTime: string
    status: string
    statusText: string
    viewCount: number
    likeCount: number
    commentCount: number
    summary: string
    content: string
}

// 表格数据
const tableData = reactive<Article[]>([
    {
        id: 1,
        coverUrl: 'https://picsum.photos/80/80?random=101',
        title: '秦腔艺术的传承与发展',
        category: '文化研究',
        categoryId: 1,
        author: '张文化',
        authorId: 101,
        publishTime: '2024-03-15',
        status: 'published',
        statusText: '已发布',
        viewCount: 12580,
        likeCount: 856,
        commentCount: 124,
        summary: '秦腔作为国家级非物质文化遗产，如何在当代社会传承与发展是一个重要课题...',
        content: '<p>秦腔，又称"梆子腔"，是中国最古老的戏曲剧种之一...</p>'
    },
    {
        id: 2,
        coverUrl: 'https://picsum.photos/80/80?random=102',
        title: '《三滴血》的艺术价值分析',
        category: '剧目赏析',
        categoryId: 2,
        author: '李戏剧',
        authorId: 102,
        publishTime: '2024-03-10',
        status: 'published',
        statusText: '已发布',
        viewCount: 8920,
        likeCount: 567,
        commentCount: 89,
        summary: '《三滴血》是秦腔经典剧目，本文从剧本结构、人物塑造、唱腔设计等方面进行分析...',
        content: '<p>《三滴血》是秦腔大师范紫东的代表作...</p>'
    },
    {
        id: 3,
        coverUrl: 'https://picsum.photos/80/80?random=103',
        title: '秦腔名家李淑芳的艺术人生',
        category: '人物专访',
        categoryId: 3,
        author: '王采访',
        authorId: 103,
        publishTime: '2024-03-05',
        status: 'published',
        statusText: '已发布',
        viewCount: 15600,
        likeCount: 2340,
        commentCount: 356,
        summary: '李淑芳，国家一级演员，秦腔旦角表演艺术家。本文讲述她的从艺经历和艺术心得...',
        content: '<p>李淑芳，1963年生于陕西西安...</p>'
    },
    {
        id: 4,
        coverUrl: 'https://picsum.photos/80/80?random=104',
        title: '2024秦腔艺术节盛大开幕',
        category: '新闻动态',
        categoryId: 4,
        author: '赵新闻',
        authorId: 104,
        publishTime: '2024-03-01',
        status: 'published',
        statusText: '已发布',
        viewCount: 24500,
        likeCount: 1876,
        commentCount: 234,
        summary: '2024年秦腔艺术节在西安易俗社大剧院盛大开幕...',
        content: '<p>3月1日晚，2024年秦腔艺术节在西安易俗社大剧院盛大开幕...</p>'
    },
    {
        id: 5,
        coverUrl: 'https://picsum.photos/80/80?random=105',
        title: '秦腔旦角表演艺术的特点',
        category: '戏曲知识',
        categoryId: 5,
        author: '孙教学',
        authorId: 105,
        publishTime: '2024-02-25',
        status: 'published',
        statusText: '已发布',
        viewCount: 6320,
        likeCount: 423,
        commentCount: 56,
        summary: '本文详细介绍秦腔旦角的表演特点...',
        content: '<p>秦腔旦角是秦腔行当中非常重要的一支...</p>'
    },
    {
        id: 6,
        coverUrl: 'https://picsum.photos/80/80?random=106',
        title: '浅谈秦腔音乐的伴奏艺术',
        category: '音乐研究',
        categoryId: 6,
        author: '周音乐',
        authorId: 106,
        publishTime: '2024-02-20',
        status: 'draft',
        statusText: '草稿',
        viewCount: 0,
        likeCount: 0,
        commentCount: 0,
        summary: '秦腔音乐伴奏以板胡为主...',
        content: '<p>秦腔音乐的伴奏艺术是其艺术魅力的重要组成部分...</p>'
    },
    {
        id: 7,
        coverUrl: 'https://picsum.photos/80/80?random=107',
        title: '李小锋：让秦腔走进年轻人',
        category: '人物专访',
        categoryId: 3,
        author: '王采访',
        authorId: 103,
        publishTime: '2024-02-15',
        status: 'published',
        statusText: '已发布',
        viewCount: 12800,
        likeCount: 1890,
        commentCount: 267,
        summary: '梅花奖得主李小锋谈秦腔的创新与传播...',
        content: '<p>李小锋，国家一级演员，梅花奖得主...</p>'
    },
    {
        id: 8,
        coverUrl: 'https://picsum.photos/80/80?random=108',
        title: '秦腔脸谱的艺术特征',
        category: '戏曲知识',
        categoryId: 5,
        author: '孙教学',
        authorId: 105,
        publishTime: '2024-02-10',
        status: 'offline',
        statusText: '已下架',
        viewCount: 3420,
        likeCount: 234,
        commentCount: 32,
        summary: '秦腔脸谱色彩鲜艳、图案丰富...',
        content: '<p>秦腔脸谱是中国戏曲脸谱的重要组成部分...</p>'
    }
])

// 编辑内容
const editContent = ref<Article | null>(null)

// 新增/编辑弹窗
const addEdit = ref<Article | null>(null)

// 监听编辑内容变化，处理新增
watch(editContent, (newVal) => {
    if (newVal) {
        addEdit.value = newVal
        // 如果是新增（没有id且publishTime为空）
        if (!newVal.id && !newVal.publishTime) {
            const newArticle = {
                ...newVal,
                id: Date.now(), // 临时ID
                publishTime: new Date().toLocaleString(),
                viewCount: 0,
                likeCount: 0,
                commentCount: 0
            }
            tableData.push(newArticle)
        }
    }
}, { deep: true })

// 批量删除模式
const selects = ref(false)

// 多选数据
const multipleSelection = ref<Article[]>([])

// 搜索表单数据
const formHeader = ref({
    title: '',
    category: '',
    status: ''
})

// 搜索触发标志
const search = ref(false)

// 监听搜索条件变化
watch(formHeader, (newVal) => {
    console.log('搜索条件变化:', newVal)
}, { deep: true })

// 监听搜索触发
watch(search, (newVal) => {
    if (newVal) {
        console.log('执行搜索，条件:', formHeader.value)
        // 这里可以添加搜索过滤逻辑
        search.value = false
    }
})

// 标签配置
interface LableItem {
    lable1?: string,
    lable2?: string,
    lable3?: string,
}

const labels = reactive<LableItem>({
    lable1: '标题',
    lable2: '分类',
    lable3: '状态'
})
</script>

<template>
    <div class="article-manage">
        <!-- 搜索头部 -->
        <opearHeader 
            v-model:form-header="formHeader" 
            v-model:search="search" 
            :lable="labels"
        />
        
        <!-- 操作栏 -->
        <opear 
            v-model:editContent="editContent" 
            v-model:multipleSelection="multipleSelection"
            v-model:selects="selects"
        />
        
        <!-- 表格内容 -->
        <tableContent 
            v-model:tableData="tableData" 
            v-model:selects="selects"
            v-model:multipleSelection="multipleSelection" 
            v-model:form-header="formHeader" 
            v-model:search="search"
        />
    </div>
</template>

<style scoped>
.article-manage {
    padding: 20px;
    background: #f5f7fa;
    min-height: 100vh;
}
</style>
<script setup lang="ts">
import opearHeader from '@/components/Contents/Profile/tables/opearHeader.vue';
import opear from '@/components/Contents/Profile/tables/opear.vue'
import tableContent from '@/components/Contents/Profile/tables/tableContent.vue';
import { reactive, watch, ref } from 'vue';
interface News {
    id: number
    title: string
    content: string
    category: string
    author: string
    publishTime: string
    clickCount: number
    likeCount: number
    statusText: string
}


const tableData: News[] = reactive([
    {
        id: 1,
        title: '秦腔名家李淑芳收徒仪式在西安举行',
        content: '4月1日，著名秦腔表演艺术家李淑芳收徒仪式在西安举行，来自全国各地的秦腔爱好者参加了此次活动...',
        category: '名家动态',
        author: 'admin',
        publishTime: '2024-04-01 10:30:00',
        clickCount: 5600,
        likeCount: 320,
        statusText: '已发布'
    },
    {
        id: 2,
        title: '2024秦腔艺术节将于5月在兰州举办',
        content: '据悉，2024年秦腔艺术节将于5月15日在兰州开幕，届时将有来自全国各地的秦腔剧团参演...',
        category: '活动通知',
        author: 'editor',
        publishTime: '2024-03-28 14:20:00',
        clickCount: 8900,
        likeCount: 540,
        statusText: '已发布'
    },
    {
        id: 3,
        title: '秦腔《周仁回府》入选国家级非遗代表性项目',
        content: '近日，国务院公布了第五批国家级非物质文化遗产代表性项目名录，秦腔《周仁回府》成功入选...',
        category: '非遗动态',
        author: 'admin',
        publishTime: '2024-03-20 09:15:00',
        clickCount: 12000,
        likeCount: 890,
        statusText: '已发布'
    },
    {
        id: 4,
        title: '青年秦腔演员大赛决赛圆满结束',
        content: '3月18日，第十届青年秦腔演员大赛决赛在西安圆满结束，共有来自全国各地的30名选手参加了决赛...',
        category: '赛事新闻',
        author: 'editor',
        publishTime: '2024-03-19 16:45:00',
        clickCount: 7800,
        likeCount: 450,
        statusText: '已发布'
    },
    {
        id: 5,
        title: '秦腔数字博物馆正式上线',
        content: '为了更好地传承和保护秦腔文化，秦腔数字博物馆于3月15日正式上线，市民可以通过网络了解秦腔的历史和发展...',
        category: '文化动态',
        author: 'admin',
        publishTime: '2024-03-16 11:20:00',
        clickCount: 9200,
        likeCount: 630,
        statusText: '已发布'
    },
    {
        id: 6,
        title: '著名秦腔表演艺术家刘随社新作《金沙滩》首演',
        content: '3月10日，著名秦腔表演艺术家刘随社的新作《金沙滩》在西安首演，受到了观众的热烈欢迎...',
        category: '名家动态',
        author: 'editor',
        publishTime: '2024-03-11 10:00:00',
        clickCount: 15000,
        likeCount: 1200,
        statusText: '已发布'
    }
])

const editContent = ref()
const addEdit = ref()
watch(editContent, (newVal) => {
    addEdit.value = newVal
    if (newVal.title != '' && newVal.publishTime === '') {
        newVal.publishTime = new Date().toLocaleString()
        tableData.push({
            ...newVal
        })
    }
}, { deep: true })
const selects = ref(false)
const multipleSelection = ref([])


const formHeader = ref({
    title: '',
    category: '',
    status: ''
})
const search = ref(false)
watch(formHeader, (newVal) => {
    console.log('这是新的', newVal)
}, { deep: true })
interface lableItem {
    lable1?: string,
    lable2?: string,
    lable3?: string,
    lable4?: string,
    lable5?: string,
    lable6?: string,
    lable7?: string,
}
const labels = reactive<lableItem>({
    lable5: '标题',
    lable6: '分类',
    lable7: '状态'
})

</script>
<template>
    <div>
        <opearHeader v-model:form-header="formHeader" v-model:search="search" :lable="labels"></opearHeader>
        <opear v-model:editContent="editContent" v-model:multipleSelection="multipleSelection"
            v-model:selects="selects"></opear>
        <tableContent v-model:tableData="tableData" v-model:selects="selects"
            v-model:multipleSelection="multipleSelection" v-model:form-header="formHeader" v-model:search="search">
        </tableContent>
    </div>
</template>
<style scoped></style>
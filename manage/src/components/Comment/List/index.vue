<script setup lang="ts">
import opearHeader from '@/components/Comment/List/tables/opearHeader.vue';
import opear from '@/components/Comment/List/tables/opear.vue'
import tableContent from '@/components/Comment/List/tables/tableContent.vue';
import { reactive, watch, ref } from 'vue';
interface Comment {
    id: number
    content: string
    user: string
    targetType: string
    targetId: number
    targetTitle: string
    publishTime: string
    statusText: string
    replyCount: number
    likeCount: number
}


const tableData: Comment[] = reactive([
    {
        id: 1,
        content: '这个秦腔表演太精彩了，演员的唱腔和表演都非常到位！',
        user: '张三',
        targetType: '视频',
        targetId: 1,
        targetTitle: '秦腔《周仁回府》选段',
        publishTime: '2024-04-01 10:30:00',
        statusText: '已审核',
        replyCount: 5,
        likeCount: 23
    },
    {
        id: 2,
        content: '希望能多看到这样高质量的秦腔表演，传承传统文化！',
        user: '李四',
        targetType: '视频',
        targetId: 2,
        targetTitle: '秦腔《铡美案》全本',
        publishTime: '2024-03-28 14:20:00',
        statusText: '已审核',
        replyCount: 3,
        likeCount: 18
    },
    {
        id: 3,
        content: '演员的唱功真的很棒，特别是那段高音部分，太震撼了！',
        user: '王五',
        targetType: '视频',
        targetId: 3,
        targetTitle: '秦腔《三滴血》选段',
        publishTime: '2024-03-20 09:15:00',
        statusText: '已审核',
        replyCount: 8,
        likeCount: 35
    },
    {
        id: 4,
        content: '这个版本的演绎很有新意，传统与现代的结合很成功',
        user: '赵六',
        targetType: '视频',
        targetId: 4,
        targetTitle: '秦腔《白蛇传》选段',
        publishTime: '2024-03-19 16:45:00',
        statusText: '已审核',
        replyCount: 4,
        likeCount: 27
    },
    {
        id: 5,
        content: '作为秦腔爱好者，看到这样的表演真的很开心',
        user: '孙七',
        targetType: '资讯',
        targetId: 1,
        targetTitle: '秦腔名家李淑芳收徒仪式在西安举行',
        publishTime: '2024-03-16 11:20:00',
        statusText: '已审核',
        replyCount: 2,
        likeCount: 15
    },
    {
        id: 6,
        content: '期待更多年轻演员的精彩表现',
        user: '周八',
        targetType: '资讯',
        targetId: 2,
        targetTitle: '2024秦腔艺术节将于5月在兰州举办',
        publishTime: '2024-03-11 10:00:00',
        statusText: '已审核',
        replyCount: 1,
        likeCount: 12
    }
])

const editContent = ref()
const addEdit = ref()
watch(editContent, (newVal) => {
    addEdit.value = newVal
    if (newVal.content != '' && newVal.publishTime === '') {
        newVal.publishTime = new Date().toLocaleString()
        tableData.push({
            ...newVal
        })
    }
}, { deep: true })
const selects = ref(false)
const multipleSelection = ref([])


const formHeader = ref({
    content: '',
    user: '',
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
    lable5: '评论内容',
    lable6: '用户',
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
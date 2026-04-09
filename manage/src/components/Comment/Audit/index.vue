<script setup lang="ts">
import opearHeader from '@/components/Comment/Audit/tables/opearHeader.vue';
import opear from '@/components/Comment/Audit/tables/opear.vue'
import tableContent from '@/components/Comment/Audit/tables/tableContent.vue';
import { reactive, watch, ref } from 'vue';
interface CommentAudit {
    id: number
    content: string
    user: string
    userType: string
    targetType: string
    targetTitle: string
    publishTime: string
    statusText: string
    replyCount: number
    likeCount: number
    rejectReason?: string
}


const tableData: CommentAudit[] = reactive([
    {
        id: 1,
        content: '这部秦腔剧目太经典了！',
        user: '张三',
        userType: '普通用户',
        targetType: '剧目',
        targetTitle: '《锁麟囊》',
        publishTime: '2024-04-01 12:30',
        statusText: '待审核',
        replyCount: 0,
        likeCount: 0
    },
    {
        id: 2,
        content: '演员的表演非常精彩，唱腔优美',
        user: '李四',
        userType: '普通用户',
        targetType: '视频',
        targetTitle: '秦腔《周仁回府》选段',
        publishTime: '2024-03-28 14:20',
        statusText: '待审核',
        replyCount: 0,
        likeCount: 0
    },
    {
        id: 3,
        content: '希望能多看到这样高质量的秦腔表演',
        user: '王五',
        userType: 'VIP用户',
        targetType: '资讯',
        targetTitle: '2024秦腔艺术节将于5月在兰州举办',
        publishTime: '2024-03-20 09:15',
        statusText: '待审核',
        replyCount: 0,
        likeCount: 0
    },
    {
        id: 4,
        content: '这个版本的演绎很有新意',
        user: '赵六',
        userType: '普通用户',
        targetType: '视频',
        targetTitle: '秦腔《白蛇传》选段',
        publishTime: '2024-03-19 16:45',
        statusText: '待审核',
        replyCount: 0,
        likeCount: 0
    },
    {
        id: 5,
        content: '作为秦腔爱好者，看到这样的表演真的很开心',
        user: '孙七',
        userType: '普通用户',
        targetType: '资讯',
        targetTitle: '秦腔名家李淑芳收徒仪式在西安举行',
        publishTime: '2024-03-16 11:20',
        statusText: '待审核',
        replyCount: 0,
        likeCount: 0
    }
])

const editContent = ref()
const addEdit = ref()
watch(editContent, (newVal) => {
    addEdit.value = newVal
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
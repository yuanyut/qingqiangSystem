<script setup lang="ts">
import opearHeader from '@/components/Contents/Actor/tables/opearHeader.vue';
import opear from '@/components/Contents/Actor/tables/opear.vue'
import tableContent from '@/components/Contents/Actor/tables/tableContent.vue';
import { reactive, watch, ref } from 'vue';
interface Actor {
    id: number
    name: string
    photo: string
    description: string
    juese: string
    category: string
    worksCount: number
    clickCount: number
    likeCount: number
    joinTime: string
    statusText: string
}


const tableData: Actor[] = reactive([
    {
        id: 1,
        name: '李淑芳',
        photo: 'https://example.com/qinju/actors/lishufang.jpg',
        description: '国家一级演员，秦腔旦角，师承秦腔名家肖若兰，代表作品《三娘教子》《白蛇传》等',
        juese: '旦角',
        category: '传统派',
        worksCount: 56,
        clickCount: 1250000,
        likeCount: 89000,
        joinTime: '1990-05-15',
        statusText: '活跃'
    },
    {
        id: 2,
        name: '刘随社',
        photo: 'https://example.com/qinju/actors/liusuishe.jpg',
        description: '国家一级演员，秦腔须生，嗓音洪亮，表演大气，代表作品《赵氏孤儿》《金沙滩》等',
        juese: '须生',
        category: '传统派',
        worksCount: 48,
        clickCount: 980000,
        likeCount: 72000,
        joinTime: '1988-03-20',
        statusText: '活跃'
    },
    {
        id: 3,
        name: '李小锋',
        photo: 'https://example.com/qinju/actors/lixiaofeng.jpg',
        description: '国家一级演员，秦腔小生，扮相英俊，唱腔优美，代表作品《游龟山》《周仁回府》等',
        juese: '小生',
        category: '创新派',
        worksCount: 42,
        clickCount: 760000,
        likeCount: 54000,
        joinTime: '1992-08-01',
        statusText: '活跃'
    },
    {
        id: 4,
        name: '李爱琴',
        photo: 'https://example.com/qinju/actors/liaiqin.jpg',
        description: '国家一级演员，秦腔须生，艺名"六龄童"，代表作品《周仁回府》《生死牌》等',
        juese: '须生',
        category: '传统派',
        worksCount: 67,
        clickCount: 1560000,
        likeCount: 123000,
        joinTime: '1956-01-10',
        statusText: '退休'
    },
    {
        id: 5,
        name: '马友仙',
        photo: 'https://example.com/qinju/actors/mayouxian.jpg',
        description: '国家一级演员，秦腔旦角，嗓音甜润，表演细腻，代表作品《火焰驹》《窦娥冤》等',
        juese: '旦角',
        category: '传统派',
        worksCount: 53,
        clickCount: 1120000,
        likeCount: 86000,
        joinTime: '1960-07-15',
        statusText: '退休'
    },
    {
        id: 6,
        name: '齐爱云',
        photo: 'https://example.com/qinju/actors/qiaiyun.jpg',
        description: '国家一级演员，秦腔旦角，文武兼备，代表作品《断桥》《游西湖》等',
        juese: '旦角',
        category: '创新派',
        worksCount: 38,
        clickCount: 890000,
        likeCount: 67000,
        joinTime: '1995-04-20',
        statusText: '活跃'
    },
    {
        id: 7,
        name: '丁良生',
        photo: 'https://example.com/qinju/actors/dingliangsheng.jpg',
        description: '国家一级演员，秦腔须生，唱腔苍劲有力，代表作品《杀庙》《打镇台》等',
        juese: '须生',
        category: '传统派',
        worksCount: 45,
        clickCount: 1430000,
        likeCount: 98000,
        joinTime: '1985-09-01',
        statusText: '活跃'
    },
    {
        id: 8,
        name: '胡林焕',
        photo: 'https://example.com/qinju/actors/hulinhuan.jpg',
        description: '国家一级演员，秦腔花脸，嗓音浑厚，表演粗犷，代表作品《铡美案》《黑叮本》等',
        juese: '花脸',
        category: '传统派',
        worksCount: 36,
        clickCount: 2010000,
        likeCount: 154000,
        joinTime: '1989-06-10',
        statusText: '活跃'
    },
    {
        id: 9,
        name: '张保卫',
        photo: 'https://example.com/qinju/actors/zhangbaowei.jpg',
        description: '国家一级演员，秦腔小生，代表作品《背舌》《白逼宫》等',
        juese: '小生',
        category: '传统派',
        worksCount: 32,
        clickCount: 540000,
        likeCount: 38000,
        joinTime: '1991-03-15',
        statusText: '活跃'
    },
    {
        id: 10,
        name: '任小蕾',
        photo: 'https://example.com/qinju/actors/renxiaolei.jpg',
        description: '国家一级演员，秦腔旦角，表演细腻，代表作品《拾画》《春草闯堂》等',
        juese: '旦角',
        category: '创新派',
        worksCount: 35,
        clickCount: 670000,
        likeCount: 49000,
        joinTime: '1994-08-20',
        statusText: '活跃'
    },
    {
        id: 11,
        name: '李梅',
        photo: 'https://example.com/qinju/actors/liumei.jpg',
        description: '国家一级演员，秦腔旦角，代表作品《红灯记》《迟开的玫瑰》等',
        juese: '旦角',
        category: '现代派',
        worksCount: 41,
        clickCount: 890000,
        likeCount: 67000,
        joinTime: '1987-05-01',
        statusText: '活跃'
    },
    {
        id: 12,
        name: '侯红琴',
        photo: 'https://example.com/qinju/actors/houhongqin.jpg',
        description: '国家一级演员，秦腔旦角，代表作品《五典坡》《三滴血》等',
        juese: '旦角',
        category: '传统派',
        worksCount: 39,
        clickCount: 1340000,
        likeCount: 102000,
        joinTime: '1993-02-10',
        statusText: '活跃'
    }
])

const editContent = ref()
const addEdit = ref()
watch(editContent, (newVal) => {
    addEdit.value = newVal
    if (newVal.name != '' && newVal.joinTime === '') {
        newVal.joinTime = new Date().toLocaleDateString()
        tableData.push({
            ...newVal
        })
    }
}, { deep: true })
const selects = ref(false)
const multipleSelection = ref([])


const formHeader = ref({
    name: '',
    juese: '',
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
    lable5: '姓名',
    lable6: '角色',
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
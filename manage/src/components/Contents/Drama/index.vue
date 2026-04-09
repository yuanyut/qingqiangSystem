<script setup lang="ts">
import opearHeader from '@/components/Contents/Drama/tables/opearHeader.vue';
import opear from '@/components/Contents/Drama/tables/opear.vue'
import tableContent from '@/components/Contents/Drama/tables/tableContent.vue';
import { reactive, watch, ref } from 'vue';
interface User {
    id: number | string,
    coverUrl: string,
    name: string,
    category: string,
    categoryId: number,
    clickCount: number,
    publishTime: string,
    status: string, // published, draft, offline
    statusText: string,
    director: string,
    actor: string,
    duration: string,
    description: string,
    content: string
}
const tableData: User[] = reactive([
      {
    id: 1,
    coverUrl: 'https://picsum.photos/80/80?random=1',
    name: '三滴血',
    category: '传统剧目',
    categoryId: 1,
    clickCount: 158000,
    publishTime: '2024-01-15',
    status: 'published', // published, draft, offline
    statusText: '已发布',
    director: '李淑芳',
    actor: '李淑芳、张涛',
    duration: '145分钟',
    description: '经典秦腔剧目，讲述民间冤案昭雪的故事。山西商人周仁瑞之妻生下一子，取名天佑...',
    content: '<p>《三滴血》是秦腔传统名剧，由范紫东编剧。剧情讲述了山西商人周仁瑞之妻生下一子，取名天佑，并认李三娘为干娘。周仁瑞之弟周仁祥为夺家产，诬告天佑非周家血脉...<p>'
  },
  {
    id: 2,
    coverUrl: 'https://picsum.photos/80/80?random=2',
    name: '火焰驹',
    category: '传统剧目',
    categoryId: 1,
    clickCount: 98600,
    publishTime: '2024-01-20',
    status: 'published',
    statusText: '已发布',
    director: '李小锋',
    actor: '李小锋、李梅',
    duration: '138分钟',
    description: '忠贞爱情故事，秦腔代表性剧目。北宋时，北狄反，李彦荣挂帅出征...',
    content: '<p>《火焰驹》原名《卖水记》，是秦腔经典剧目。讲述北宋时期，兵部尚书李绶之子李彦贵与黄璋之女黄桂英的爱情故事...<p>'
  },
  {
    id: 3,
    coverUrl: 'https://picsum.photos/80/80?random=3',
    name: '周仁回府',
    category: '传统剧目',
    categoryId: 1,
    clickCount: 72000,
    publishTime: '2024-02-01',
    status: 'published',
    statusText: '已发布',
    director: '任哲中',
    actor: '任哲中、郝彩凤',
    duration: '152分钟',
    description: '明代传奇故事，讲述周仁为救义兄之妻而舍妻的感人故事。',
    content: '<p>《周仁回府》又名《忠义侠》，是秦腔的经典剧目。故事发生在明代，严嵩当权，陷害杜文学...<p>'
  },
  {
    id: 4,
    coverUrl: 'https://picsum.photos/80/80?random=4',
    name: '游西湖',
    category: '传统剧目',
    categoryId: 1,
    clickCount: 65000,
    publishTime: '2024-02-10',
    status: 'published',
    statusText: '已发布',
    director: '马友仙',
    actor: '马友仙、李爱琴',
    duration: '148分钟',
    description: '李慧娘鬼魂救裴生的爱情故事，秦腔经典鬼戏。',
    content: '<p>《游西湖》是秦腔传统名剧，讲述南宋时李慧娘与裴禹的爱情故事。李慧娘因赞裴生被贾似道杀害，后化为鬼魂救出裴生...<p>'
  },
  {
    id: 5,
    coverUrl: 'https://picsum.photos/80/80?random=5',
    name: '斩单童',
    category: '传统剧目',
    categoryId: 1,
    clickCount: 58000,
    publishTime: '2024-02-15',
    status: 'published',
    statusText: '已发布',
    director: '张兰秦',
    actor: '张兰秦、刘随社',
    duration: '125分钟',
    description: '花脸经典剧目，讲述单雄信被斩前的慷慨悲歌。',
    content: '<p>《斩单童》是秦腔花脸的代表剧目，取材于《隋唐演义》。讲述瓦岗寨英雄单雄信被李世民擒获，行刑前痛骂众友，慷慨就义...<p>'
  },
  {
    id: 6,
    coverUrl: 'https://picsum.photos/80/80?random=6',
    name: '窦娥冤',
    category: '传统剧目',
    categoryId: 1,
    clickCount: 49000,
    publishTime: '2024-02-20',
    status: 'draft',
    statusText: '草稿',
    director: '苏凤丽',
    actor: '苏凤丽、张兰秦',
    duration: '140分钟',
    description: '关汉卿经典悲剧，秦腔改编版本。',
    content: '<p>《窦娥冤》改编自关汉卿同名元杂剧，讲述窦娥被冤屈而死，死后六月飞雪、血溅白练的悲剧故事...<p>'
  },
  {
    id: 7,
    coverUrl: 'https://picsum.photos/80/80?random=7',
    name: '法门寺',
    category: '传统剧目',
    categoryId: 1,
    clickCount: 43000,
    publishTime: '2024-03-01',
    status: 'published',
    statusText: '已发布',
    director: '丁良生',
    actor: '丁良生、李娟',
    duration: '135分钟',
    description: '公案戏，讲述刘媒婆、宋巧姣等人物故事。',
    content: '<p>《法门寺》是秦腔经典公案戏，故事发生在明朝，讲述刘媒婆、宋巧姣、傅朋等人的离奇案件...<p>'
  },
  {
    id: 8,
    coverUrl: 'https://picsum.photos/80/80?random=8',
    name: '秦腔艺术节开幕式',
    category: '现代演出',
    categoryId: 2,
    clickCount: 35000,
    publishTime: '2024-03-10',
    status: 'offline',
    statusText: '已下架',
    director: '省秦腔剧院',
    actor: '众名家',
    duration: '180分钟',
    description: '2024年秦腔艺术节开幕式精彩集锦。',
    content: '2024年秦腔艺术节开幕式汇集了众多秦腔名家，演出精彩纷呈...'
  }])

const editContent = ref()
const addEdit = ref()
watch(editContent, (newVal) => {
    addEdit.value = newVal
    if (newVal.name != '' && newVal.publishTime === '') {
        newVal.publishTime = new Date().toLocaleString()
        tableData.push({
            ...newVal
        })
    }
}, { deep: true })
const selects = ref(false)
const multipleSelection = ref([])


const formHeader = ref({
    name: '',
    phone: '',
    juese: '',
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
const labels=reactive<lableItem>({
    lable5:'名称',
    lable6:'分类',
    lable7:'状态'
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
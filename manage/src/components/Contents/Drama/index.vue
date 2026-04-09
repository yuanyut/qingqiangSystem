<script setup lang="ts">
import opearHeader from '@/components/Contents/Drama/tables/opearHeader.vue';
import opear from '@/components/Contents/Drama/tables/opear.vue'
import tableContent from '@/components/Contents/Drama/tables/tableContent.vue';
import { reactive, watch, ref } from 'vue';
interface User {
     id: number
  videoUrl: string
  title: string
  description: string
  actor: string
  category: string
  duration: number
  clickCount: number
  likeCount: number
  publishTime: string
  statusText: string
}


const tableData: User[] = reactive([
  {
    id: 1,
    videoUrl: 'https://example.com/qinju/sanniangjiaozhi.mp4',
    title: '三娘教子',
    description: '明代薛广赴京不归，妻张氏、妾刘氏改嫁，三娘王春娥织布教子，倚哥不争气被三娘训诫的故事',
    actor: '李淑芳',
    category: '传统剧目',
    duration: 1850,
    clickCount: 125000,
    likeCount: 8900,
    publishTime: '2024-01-15 19:30:00',
    statusText: '已发布'
  },
  {
    id: 2,
    videoUrl: 'https://example.com/qinju/zhaoshiguzhe.mp4',
    title: '赵氏孤儿',
    description: '春秋时期晋国贵族赵氏被灭门，门客程婴舍子救孤，抚养孤儿长大后复仇的悲壮故事',
    actor: '刘随社',
    category: '历史剧',
    duration: 2100,
    clickCount: 98000,
    likeCount: 7200,
    publishTime: '2024-01-20 20:00:00',
    statusText: '已发布'
  },
  {
    id: 3,
    videoUrl: 'https://example.com/qinju/youguisuan.mp4',
    title: '游龟山',
    description: '江夏县知县田云山之子田玉川与卢府千金凤英的爱情故事，包含"藏舟"等经典折子戏',
    actor: '李小锋',
    category: '爱情剧',
    duration: 1680,
    clickCount: 76000,
    likeCount: 5400,
    publishTime: '2024-02-01 19:00:00',
    statusText: '已发布'
  },
  {
    id: 4,
    videoUrl: 'https://example.com/qinju/zhourenhuixiang.mp4',
    title: '周仁回府',
    description: '严嵩权倾朝野，周仁为救义兄杜文学，被迫献出妻子，忍辱负重最终昭雪的故事',
    actor: '李爱琴',
    category: '忠义剧',
    duration: 1950,
    clickCount: 156000,
    likeCount: 12300,
    publishTime: '2024-02-10 19:30:00',
    statusText: '已发布'
  },
  {
    id: 5,
    videoUrl: 'https://example.com/qinju/huotingxiang.mp4',
    title: '火焰驹',
    description: '北宋时期，李彦荣被诬陷，其妻黄桂英骑火焰驹千里传信，最终平反昭雪的故事',
    actor: '马友仙',
    category: '神话剧',
    duration: 1720,
    clickCount: 112000,
    likeCount: 8600,
    publishTime: '2024-02-18 20:00:00',
    statusText: '已发布'
  },
  {
    id: 6,
    videoUrl: 'https://example.com/qinju/duanshu.mp4',
    title: '断桥',
    description: '白蛇传经典折子戏，白素贞在断桥与许仙重逢，小青怒斥许仙负心，感人至深',
    actor: '齐爱云',
    category: '神话剧',
    duration: 850,
    clickCount: 89000,
    likeCount: 6700,
    publishTime: '2024-02-25 19:00:00',
    statusText: '已发布'
  },
  {
    id: 7,
    videoUrl: 'https://example.com/qinju/shatan.mp4',
    title: '杀庙',
    description: '秦香莲被韩琦追杀至破庙，韩琦得知真相后不忍下手，自刎而死，秦香莲携子女逃离',
    actor: '丁良生',
    category: '悲剧',
    duration: 620,
    clickCount: 143000,
    likeCount: 9800,
    publishTime: '2024-03-01 19:30:00',
    statusText: '已发布'
  },
  {
    id: 8,
    videoUrl: 'https://example.com/qinju/qiuzhu.mp4',
    title: '铡美案',
    description: '陈世美中状元后抛妻弃子，包拯秉公执法，将其铡死，彰显正义',
    actor: '胡林焕',
    category: '公案剧',
    duration: 1450,
    clickCount: 201000,
    likeCount: 15400,
    publishTime: '2024-03-08 20:00:00',
    statusText: '已发布'
  },
  {
    id: 9,
    videoUrl: 'https://example.com/qinju/beishangfen.mp4',
    title: '背舌',
    description: '传统折子戏，讲述夫妻误会化解的幽默故事，唱腔优美，表演生动',
    actor: '张保卫',
    category: '折子戏',
    duration: 520,
    clickCount: 54000,
    likeCount: 3800,
    publishTime: '2024-03-12 19:00:00',
    statusText: '已发布'
  },
  {
    id: 10,
    videoUrl: 'https://example.com/qinju/shihua.mp4',
    title: '拾画',
    description: '书生柳梦梅拾得杜丽娘画像，相思成疾，演绎生者可以死，死者可以生的至情',
    actor: '任小蕾',
    category: '爱情剧',
    duration: 580,
    clickCount: 67000,
    likeCount: 4900,
    publishTime: '2024-03-15 19:30:00',
    statusText: '已发布'
  },
  {
    id: 11,
    videoUrl: 'https://example.com/qinju/dengtuan.mp4',
    title: '红灯记',
    description: '革命现代戏，李玉和一家三代为保护密电码与日寇斗争的英雄故事',
    actor: '李梅',
    category: '现代戏',
    duration: 1650,
    clickCount: 89000,
    likeCount: 6700,
    publishTime: '2024-03-20 19:30:00',
    statusText: '已发布'
  },
  {
    id: 12,
    videoUrl: 'https://example.com/qinju/wuyuan.mp4',
    title: '五典坡',
    description: '薛平贵与王宝钏的爱情故事，王宝钏寒窑苦守十八载，最终夫妻团圆',
    actor: '侯红琴',
    category: '传统剧目',
    duration: 1980,
    clickCount: 134000,
    likeCount: 10200,
    publishTime: '2024-03-25 20:00:00',
    statusText: '已发布'
  }
])

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
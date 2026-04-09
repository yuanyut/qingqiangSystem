<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import pie from '@/components/charts/pie.vue'
import perLine from '@/components/charts/perLine.vue'
import Radar from '@/components/charts/radar.vue'
import threeLine from '@/components/charts/threeLine.vue'
import Bar from '@/components/charts/Bar.vue'
import doubleBar from '@/components/charts/doubleBar.vue'
import doubleBars from '@/components/charts/doubleBars.vue'
import chinaMap from '@/components/charts/chinaMap.vue'
interface totalItem {
    name: string,
    data: number,
    status?: string,
    changeNum?: number,
    changeWeiDu?: string
}
interface PieItem {
    value: number
    name: string,
}
interface daramValue {
    time: string[],
    dramaCount?: number[],
    articleCount?: number[]
}
interface qualityScore {
    overview: {
        avgUserRating: number
        avgCommentQuality: number
        highQualityContentRate: number
        activeContentCount: number
        contentCount: number
    }
    radar: {
        indicator: { name: string; max: number }[]
        value: number[]
    }
}
const qualityScoreData = reactive<qualityScore>({
    overview: {
        avgUserRating: 0.72,          // Recall 映射成评分
        avgCommentQuality: 0.66,      // Precision 映射成评论质量
        highQualityContentRate: 0.54, // CTR 映射成高质量内容率
        activeContentCount: 0.78,     // Diversity 映射成活跃度
        contentCount: 0.63             // Novelty 映射成总内容新颖性
    },
    radar: {
        indicator: [
            { name: '评分', max: 1 },
            { name: '评论质量', max: 1 },
            { name: '高质量内容率', max: 1 },
            { name: '活跃度', max: 1 },
            { name: '新颖性', max: 1 }
        ],
        value: [0.72, 0.66, 0.54, 0.78, 0.63]
    }
})


const cateJumu = reactive<PieItem[]>([{
    value: 50, name: '传统剧目'
}, {
    value: 50, name: '现代剧目'
}, {
    value: 20, name: '经典折子戏'
}, {
    value: 10, name: '新编历史剧'
}])

const everyDram = reactive<daramValue>({

    time: [
        "2026-03-27", "2026-03-28", "2026-03-29", "2026-03-30", "2026-03-31",
        "2026-04-01", "2026-04-02", "2026-04-03", "2026-04-04", "2026-04-05",
        "2026-04-06", "2026-04-07", "2026-04-08", "2026-04-09"
    ],
    articleCount: [
        13, 13, 14, 15, 15, 16, 16, 17, 17, 18, 18, 19, 19, 20
    ],

})
const weekDram = reactive<daramValue>({
    time: [
        "2026-W05", "2026-W06", "2026-W07", "2026-W08",
        "2026-W09", "2026-W10", "2026-W11", "2026-W12"
    ],
    articleCount: [
        12, 13, 14, 15, 15, 16, 17, 17
    ],
})
const monthDram = reactive<daramValue>({
    time: [
        "2025-05", "2025-06", "2025-07", "2025-08", "2025-09", "2025-10",
        "2025-11", "2025-12", "2026-01", "2026-02", "2026-03", "2026-04"
    ],
    articleCount: [
        11, 11, 12, 13, 13, 14, 14, 15, 15, 16, 16, 17
    ],
})
const Dram = reactive<daramValue>({
    time: [],
    articleCount: [],
})
onMounted(() => {
    Dram.time = everyDram.time
    Dram.articleCount = everyDram.articleCount
})
const change = (daram: daramValue, index: number) => {
    Dram.time = daram.time
    Dram.articleCount = daram.articleCount
    activeDaram.value = index
    console.log(activeDaram.value)
}




const topDrama = reactive([
    { name: "三滴血", clicks: 1520 },
    { name: "白蛇传", clicks: 1380 },
    { name: "周仁回府", clicks: 1265 },
    { name: "霸王别姬", clicks: 1190 },
    { name: "红楼梦", clicks: 1105 },
    { name: "杨家将", clicks: 1050 },
    { name: "牡丹亭", clicks: 980 },
    { name: "西厢记", clicks: 910 },
    { name: "西游记", clicks: 870 },
    { name: "长生殿", clicks: 830 }
])
const crt = reactive({
    "time": ["2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24", "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29", "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"],
    "exposure": [1200, 1350, 1500, 1100, 1450, 1600, 1550, 1700, 1800, 1650, 1750, 1900, 2000, 1850],
    "click": [240, 270, 300, 220, 290, 320, 310, 340, 360, 330, 350, 380, 400, 370],

})
const sex = reactive<PieItem[]>([
    { "name": "男", "value": 5400 },
    { "name": "女", "value": 4600 },
    { "name": "未知", "value": 200 }
])
const ageData = reactive([
    { name: "18-25", clicks: 1200 },
    { name: "25-35", clicks: 3400 },
    { name: "35-50", clicks: 2100 },
    { name: "50+", clicks: 800 }
])
const mapValue = reactive([
    { name: '北京', value: 300 },
    { name: '上海', value: 500 }
])
const currentTime = ref('')
const updataTime = () => {
    const now = new Date()
    currentTime.value = now.toLocaleString()
}
const timer = ref<number>(0)
onMounted(() => {
    updataTime()
    timer.value = setInterval(updataTime, 1000)
})
onUnmounted(() => {
    if (timer.value) clearInterval(timer.value)
})
const activeDaram = ref(0)
interface nameItem {
    name1: string,
    name2?: string,
}
const name = reactive<nameItem>({
    name1: '命中率',
    name2: '',
})
</script>

<template>
    <div class="dashboard-page">


        <!-- 重新布局：左侧上方突出地图卡片 -->
        <div class="new-layout">


            <!-- 右侧辅助信息区（性别+年龄） -->
            <div class="right-side">


                <div class="chart-card">
                    <div class="chart-title">命中率趋势(单位：%)</div>
                    <div class="chart-tabs">
                        <div @click="change(everyDram, 0)" :class="{ active: activeDaram == 0 }">每日</div>
                        <div @click="change(weekDram, 1)" :class="{ active: activeDaram == 1 }">每周</div>
                        <div @click="change(monthDram, 2)" :class="{ active: activeDaram == 2 }">每月</div>
                    </div>
                    <perLine :datas="Dram" wd="100%" ht="320px" :name="name"></perLine>
                </div>
            </div>
        </div>

        <!-- 下方图表内容区 - 保持原有网格布局 -->
        <div class="chart-container">
            <!-- 左侧列 -->
            <div style="display: flex;gap:1%">

                <div class="chart-card" style="width: 33%;">
                    <div class="chart-title">推荐效果</div>
                    <Radar :datas="qualityScoreData" wd="100%" ht="320px" name="推荐效果"></Radar>
                </div>
                <div class="chart-card" style="flex: 1;">
                    <div class="chart-title">推荐系统命中率</div>
                    <doubleBars :datas="crt" wd="100%" ht="320px" name1="曝光量" name2="点击量"></doubleBars>
                </div>
            </div>





        </div>



    </div>
</template>

<style lang="css" scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

/* 整体页面背景 */
.dashboard-page {
    background: #f0f2f5;
    overflow: auto;
    min-height: 100vh;
    padding: 20px 20px 20px 20px;
}

/* 时间 */
.current-time {
    text-align: right;
    font-size: 14px;
    color: #666;
    padding: 12px 0;
    background: transparent;
}

/* 头部卡片 - 像图片中的数值标题栏 */
.header-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
    margin-bottom: 24px;
}

.stat-card {
    flex: 1;
    min-width: 160px;
    background: #ffffff;
    border-radius: 12px;
    padding: 20px 16px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
    transition: all 0.3s;
    border: 1px solid #eef2f6;
}

.stat-name {
    font-size: 14px;
    color: #8a8f99;
    margin-bottom: 8px;
}

.stat-data {
    font-size: 28px;
    font-weight: 600;
    color: #1f2f3d;
    margin-bottom: 8px;
    line-height: 1.2;
}

.stat-change {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 12px;
    flex-wrap: wrap;
}

.up {
    color: #00b42a;
    display: inline-flex;
    align-items: center;
    gap: 2px;
}

.down {
    color: #ff4d4f;
    display: inline-flex;
    align-items: center;
    gap: 2px;
}

.equal {
    color: #86909c;
}

.change-desc {
    color: #86909c;
    margin-left: 4px;
}

/* ========== 新布局：左侧突出地图 ========== */
.new-layout {
    display: flex;
    gap: 20px;
    margin-bottom: 24px;
}

.left-highlight {
    flex: 2;
    /* 地图区域占比更大，更突出 */
    min-width: 0;
}

.right-side {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 20px;
    min-width: 0;
}

/* 地图卡片突出样式 */
.map-highlight {
    height: 100%;
    background: linear-gradient(135deg, #ffffff 0%, #fafcff 100%);
    border: 1px solid #d9e8ff;
    box-shadow: 0 8px 20px rgba(22, 119, 255, 0.12);
    position: relative;
    overflow: hidden;
}



/* 原有图表容器样式 */
.chart-container {
    display: grid;

    gap: 20px;
}

.chart-column {
    flex: 1;
    min-width: 280px;
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.chart-card {
    background: #fff;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    border: 1px solid #eaecf0;
    transition: all 0.2s;
}

.chart-title {
    font-size: 16px;
    font-weight: 500;
    color: #1e2a3a;
    margin-bottom: 16px;
    padding-left: 8px;
    /* border-left: 3px solid #1677ff; */
}

.chart-tabs {
    display: flex;
    gap: 12px;
    margin-bottom: 16px;
}

.chart-tabs div {
    padding: 4px 12px;
    border-radius: 20px;
    background: #f5f7fa;
    font-size: 13px;
    cursor: pointer;
    transition: all 0.2s;
}

.chart-tabs .active {
    background: #e6f4ff;
    color: #1677ff;
}

.chart-tabs div:hover {
    background: #e6f4ff;
    color: #1677ff;
}

/* 确保所有图表组件自适应宽度 */
:deep(.chart-card > div:last-child) {
    width: 100% !important;
}

/* 响应式 */
@media (max-width: 1000px) {
    .new-layout {
        flex-direction: column;
    }

    .left-highlight,
    .right-side {
        flex: auto;
    }

    .right-side {
        flex-direction: row;
        gap: 20px;
    }

    .right-side .chart-card {
        flex: 1;
    }

    .chart-container {
        flex-direction: column;
    }

    .header-cards {
        gap: 12px;
    }

    .stat-card {
        min-width: calc(50% - 12px);
    }
}

@media (max-width: 640px) {
    .stat-card {
        min-width: 100%;
    }

    .right-side {
        flex-direction: column;
    }
}
</style>
<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import pie from '@/components/charts/pie.vue'

import Bar from '@/components/charts/Bar.vue'
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
</script>

<template>
    <div class="dashboard-page">


        <!-- 重新布局：左侧上方突出地图卡片 -->
        <div class="new-layout">
            <!-- 左侧突出区域：用户地域分布地图 -->

            <div class="chart-card map-highlight">
                <div class="chart-title">用户地域分布地图</div>
                <chinaMap :datas="mapValue" wd="100%" ht="100%"></chinaMap>
            </div>

        </div>

        <!-- 下方图表内容区 - 保持原有网格布局 -->
        <div class="chart-container">
            <!-- 左侧列 -->
            <div style="display: flex;gap:1%">
                <div class="chart-card" style="flex: 1;">
                    <div class="chart-title">用户年龄段分布</div>
                    <Bar :datas="ageData" wd="100%" ht="280px"></Bar>
                </div>
                <div class="chart-card" style="width: 33%;">
                    <div class="chart-title">用户性别占比</div>
                    <pie :datas="sex" wd="100%" ht="280px"></pie>
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
    padding: 0 20px 20px 20px;
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
    height: 60vh;
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
    width: 100%;
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
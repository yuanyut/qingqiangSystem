<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import pie from '@/components/charts/pie.vue'
import Line from '@/components/charts/barAndLine.vue'
import Radar from '@/components/charts/radar.vue'
import threeLine from '@/components/charts/threeLine.vue'
import Bar from '@/components/charts/Bar.vue'
import doubleBar from '@/components/charts/doubleBar.vue'
import chinaMap from '@/components/charts/chinaMap.vue'
import { getDramaCategory, getDramaTop10 } from '@/api/api'
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
    dramaCount: number[],
    articleCount: number[]
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

const total = reactive<totalItem[]>([
    { name: '今日访问量', data: 10 },
    { name: '剧目总数', data: 50 },
    { name: '用户总数', data: 50, status: 'up', changeNum: 5 },
    { name: 'pv(页面浏览量)', data: 100, status: 'up', changeNum: 5, changeWeiDu: '较昨天比较' },
    { name: 'uv(独立访客)', data: 100, status: 'down', changeNum: 5, changeWeiDu: '较昨天比较' },
])
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
    "time": ["2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24", "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29", "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"],
    "dramaCount": [3, 4, 5, 2, 6, 4, 3, 8, 7, 6, 9, 5, 7, 8],
    "articleCount": [1, 2, 1, 2, 1, 3, 2, 2, 1, 4, 2, 3, 2, 1]
})
const weekDram = reactive<daramValue>({
    "time": ["2024-W01", "2024-W02", "2024-W03", "2024-W04", "2024-W05", "2024-W06", "2024-W07", "2024-W08", "2024-W09", "2024-W10"],
    "dramaCount": [21, 18, 25, 28, 32, 30, 27, 35, 31, 29],
    "articleCount": [6, 5, 7, 8, 9, 7, 6, 10, 8, 7]
})
const monthDram = reactive<daramValue>({
    "time": ["2023-05", "2023-06", "2023-07", "2023-08", "2023-09", "2023-10", "2023-11", "2023-12", "2024-01", "2024-02", "2024-03", "2024-04"],
    "dramaCount": [90, 102, 96, 110, 121, 125, 130, 138, 145, 122, 150, 160],
    "articleCount": [28, 34, 29, 31, 36, 38, 42, 45, 48, 40, 52, 55]
})
const Dram = reactive<daramValue>({
    "time": ["2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24", "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29", "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"],
    "dramaCount": [3, 4, 5, 2, 6, 4, 3, 8, 7, 6, 9, 5, 7, 8],
    "articleCount": [1, 2, 1, 2, 1, 3, 2, 2, 1, 4, 2, 3, 2, 1]
})

const change = (daram: daramValue,index:number) => {
    Dram.time = daram.time
    Dram.dramaCount = daram.dramaCount
    Dram.articleCount = daram.articleCount
    activeDaram.value=index
    console.log(activeDaram.value)
}
interface trendIntergace {
    time: string[],
    visitCount: number[],
    collectCount: number[],
    shareCount: number[]
}
const everyTrend = reactive<trendIntergace>({
    "time": ["2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24", "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29", "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"],
    "visitCount": [120, 135, 150, 110, 145, 160, 155, 170, 180, 165, 175, 190, 200, 185],
    "collectCount": [30, 28, 35, 25, 32, 40, 38, 42, 45, 40, 43, 47, 50, 48],
    "shareCount": [12, 15, 10, 8, 14, 16, 15, 18, 20, 17, 19, 22, 25, 23]
})
const weekTrend = reactive<trendIntergace>({
    "time": ["2024-W01", "2024-W02", "2024-W03", "2024-W04", "2024-W05", "2024-W06", "2024-W07", "2024-W08", "2024-W09", "2024-W10"],
    "visitCount": [820, 900, 950, 870, 920, 980, 1020, 1100, 1050, 1075],
    "collectCount": [210, 220, 230, 200, 215, 240, 245, 260, 250, 255],
    "shareCount": [75, 80, 78, 70, 82, 90, 92, 100, 95, 97]
})
const monthTrend = reactive<trendIntergace>({
    "time": ["2023-05", "2023-06", "2023-07", "2023-08", "2023-09", "2023-10", "2023-11", "2023-12", "2024-01", "2024-02", "2024-03", "2024-04"],
    "visitCount": [3200, 3400, 3600, 3800, 4000, 4200, 4400, 4600, 4800, 5000, 5200, 5400],
    "collectCount": [800, 850, 880, 900, 950, 980, 1000, 1020, 1050, 1080, 1100, 1150],
    "shareCount": [250, 260, 270, 280, 290, 300, 310, 320, 330, 340, 350, 360]
})
const trend = reactive<trendIntergace>({
    "time": ["2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24", "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29", "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"],
    "visitCount": [120, 135, 150, 110, 145, 160, 155, 170, 180, 165, 175, 190, 200, 185],
    "collectCount": [30, 28, 35, 25, 32, 40, 38, 42, 45, 40, 43, 47, 50, 48],
    "shareCount": [12, 15, 10, 8, 14, 16, 15, 18, 20, 17, 19, 22, 25, 23]
})
const changeTrend = (trendValue: trendIntergace,index:number) => {
    trend.time = trendValue.time
    trend.collectCount = trendValue.collectCount
    trend.shareCount = trendValue.shareCount
    trend.visitCount = trendValue.visitCount
    activeDaram.value=index
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
    "ctr": [20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20]
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
onMounted(async () => {
    updataTime()
    timer.value = setInterval(updataTime, 1000)
    
    // 获取剧目分类饼图数据
    try {
      const categoryData = await getDramaCategory()
      console.log('剧目分类数据:', categoryData)
      if (categoryData && categoryData.data) {
        const newCateJumu = categoryData.data.map((item:any) => ({
          value: item.count,
          name: item.category
        }))
        cateJumu.length = 0
        newCateJumu.forEach((item) => cateJumu.push(item))
      }
    } catch (error) {
      console.error('获取剧目分类数据失败:', error)
    }
    
    // 获取剧目TOP10数据
    try {
      const top10Data = await getDramaTop10()
      console.log('剧目TOP10数据:', top10Data)
      if (top10Data && top10Data.data) {
        // 替换整个数组，而不是使用push
        const newTopDrama = top10Data.data.map((item) => ({
          name: item.name,
          clicks: item.viewCount
        }))
        // 清空原数组并添加新数据
        topDrama.length = 0
        newTopDrama.forEach((item) => topDrama.push(item))
      }
    } catch (error) {
      console.error('获取剧目TOP10数据失败:', error)
    }
})
onUnmounted(() => {
    if (timer.value) clearInterval(timer.value)
})
const activeDaram=ref(0)

</script>

<template>
    <div class="dashboard-page">
        <!-- 实时时间 -->

        <!-- 重新布局：左侧上方突出地图卡片 -->
        <div class="new-layout">
            

            <!-- 右侧辅助信息区（性别+年龄） -->
            <div class="right-side">
                
                <div class="chart-card">
                    <div class="chart-title">热门剧目TOP10</div>
                    <Bar :datas="topDrama" wd="100%" ht="320px"></Bar>
                </div>
                
            </div>
        </div>

        <!-- 下方图表内容区 - 保持原有网格布局 -->
        <div class="chart-container">
            <!-- 左侧列 -->
            <div style="display: flex;gap:1%">
                 <div class="chart-card" style="width: 33%;">
                    <div class="chart-title">剧目分类占比</div>
                    <pie :datas="cateJumu" wd="100%" ht="320px"></pie>
                </div>
                <div class="chart-card"  style="flex: 1;">
                    <div class="chart-title">剧目热度趋势</div>
                    <div class="chart-tabs">
                        <div @click="changeTrend(everyTrend,0)" :class="{active:activeDaram==0}">每日</div>
                        <div @click="changeTrend(weekTrend,1)" :class="{active:activeDaram==1}">每周</div>
                        <div @click="changeTrend(monthTrend,2)" :class="{active:activeDaram==2}">每月</div>
                    </div>
                    <threeLine :datas="trend" wd="100%" ht="320px" name1="访问量" name2="收藏" name3="分享"></threeLine>
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
.chart-tabs .active{
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
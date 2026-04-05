<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import pie from '@/components/charts/pie.vue'
import Line from '@/components/charts/barAndLine.vue'
import Radar from '@/components/charts/radar.vue'
import threeLine from '@/components/charts/threeLine.vue'
import Bar from '@/components/charts/Bar.vue'
import doubleBar from '@/components/charts/doubleBar.vue'
import { number } from 'echarts'
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
        indicator: { name: string; max: number }[]  // 改这里
        value: number[]
    }
}
const qualityScoreData = reactive<qualityScore>({
    overview: {
        avgUserRating: 4.3,
        avgCommentQuality: 76,
        highQualityContentRate: 0.61,
        activeContentCount: 289,
        contentCount: 356
    },
    radar: {
        indicator: [
            { name: '用户评分', max: 5 },
            { name: '点赞热度', max: 5 },
            { name: '收藏热度', max: 5 },
            { name: '评论质量', max: 5 }
        ],
        value: [4.3, 4.1, 3.6, 3.8]
    }
})

const total = reactive<totalItem[]>([

    {
        name: '今日访问量',
        data: 10
    },
    {
        name: '剧目总数',
        data: 50
    },
    {
        name: '用户总数',
        data: 50,
        status: 'up',
        changeNum: 5
    },
    {
        name: 'pv(页面浏览量)',
        data: 100,
        status: 'up',
        changeNum: 5,
        changeWeiDu: '较昨天比较'
    }, {
        name: 'pv(页面浏览量)',
        data: 100,
        status: 'up',
        changeNum: 5,
        changeWeiDu: '较上一月比较'
    }, {
        name: 'uv(独立访客)',
        data: 100,
        status: 'down',
        changeNum: 5,
        changeWeiDu: '较昨天比较'
    },
    {
        name: 'uv(独立访客)',
        data: 100,
        status: 'equal',
        changeNum: 0,
        changeWeiDu: '较上一月比较'
    }
])
const cateJumu = reactive<PieItem[]>([{
    value: 50,
    name: '传统剧目'

},
{
    value: 50,
    name: '现代剧目'

},
{
    value: 20,
    name: '经典折子戏'

},
{
    value: 10,
    name: '新编历史剧'
}
])

const everyDram = reactive<daramValue>(
    {
        "time": [
            "2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24",
            "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29",
            "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"
        ],
        "dramaCount": [3, 4, 5, 2, 6, 4, 3, 8, 7, 6, 9, 5, 7, 8],
        "articleCount": [1, 2, 1, 2, 1, 3, 2, 2, 1, 4, 2, 3, 2, 1]
    }
)
const weekDram = reactive<daramValue>(
    {
        "time": [
            "2024-W01", "2024-W02", "2024-W03", "2024-W04", "2024-W05",
            "2024-W06", "2024-W07", "2024-W08", "2024-W09", "2024-W10"
        ],
        "dramaCount": [21, 18, 25, 28, 32, 30, 27, 35, 31, 29],
        "articleCount": [6, 5, 7, 8, 9, 7, 6, 10, 8, 7]
    }
)
const monthDram = reactive<daramValue>(
    {
        "time": [
            "2023-05", "2023-06", "2023-07", "2023-08", "2023-09", "2023-10",
            "2023-11", "2023-12", "2024-01", "2024-02", "2024-03", "2024-04"
        ],
        "dramaCount": [90, 102, 96, 110, 121, 125, 130, 138, 145, 122, 150, 160],
        "articleCount": [28, 34, 29, 31, 36, 38, 42, 45, 48, 40, 52, 55]
    }
)
const Dram = reactive<daramValue>({

    "time": [
        "2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24",
        "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29",
        "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"
    ],
    "dramaCount": [3, 4, 5, 2, 6, 4, 3, 8, 7, 6, 9, 5, 7, 8],
    "articleCount": [1, 2, 1, 2, 1, 3, 2, 2, 1, 4, 2, 3, 2, 1]

})

const change = (daram: daramValue) => {
    Dram.time = daram.time
    Dram.dramaCount = daram.dramaCount
    Dram.articleCount = daram.articleCount
    console.log('这是', Dram)
}
interface trendIntergace {
    time: string[],
    visitCount: number[],
    collectCount: number[],
    shareCount: number[]
}
const everyTrend = reactive<trendIntergace>({
    "time": [
        "2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24",
        "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29",
        "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"
    ],
    "visitCount": [120, 135, 150, 110, 145, 160, 155, 170, 180, 165, 175, 190, 200, 185],
    "collectCount": [30, 28, 35, 25, 32, 40, 38, 42, 45, 40, 43, 47, 50, 48],
    "shareCount": [12, 15, 10, 8, 14, 16, 15, 18, 20, 17, 19, 22, 25, 23]
})
const weekTrend = reactive<trendIntergace>({
    "time": [
        "2024-W01", "2024-W02", "2024-W03", "2024-W04", "2024-W05",
        "2024-W06", "2024-W07", "2024-W08", "2024-W09", "2024-W10"
    ],
    "visitCount": [820, 900, 950, 870, 920, 980, 1020, 1100, 1050, 1075],
    "collectCount": [210, 220, 230, 200, 215, 240, 245, 260, 250, 255],
    "shareCount": [75, 80, 78, 70, 82, 90, 92, 100, 95, 97]
})
const monthTrend = reactive<trendIntergace>({
    "time": [
        "2023-05", "2023-06", "2023-07", "2023-08", "2023-09", "2023-10",
        "2023-11", "2023-12", "2024-01", "2024-02", "2024-03", "2024-04"
    ],
    "visitCount": [3200, 3400, 3600, 3800, 4000, 4200, 4400, 4600, 4800, 5000, 5200, 5400],
    "collectCount": [800, 850, 880, 900, 950, 980, 1000, 1020, 1050, 1080, 1100, 1150],
    "shareCount": [250, 260, 270, 280, 290, 300, 310, 320, 330, 340, 350, 360]
})
const trend = reactive<trendIntergace>({
    "time": [
        "2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24",
        "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29",
        "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"
    ],
    "visitCount": [120, 135, 150, 110, 145, 160, 155, 170, 180, 165, 175, 190, 200, 185],
    "collectCount": [30, 28, 35, 25, 32, 40, 38, 42, 45, 40, 43, 47, 50, 48],
    "shareCount": [12, 15, 10, 8, 14, 16, 15, 18, 20, 17, 19, 22, 25, 23]
})
const changeTrend = (trendValue: trendIntergace) => {
    trend.time = trendValue.time
    trend.collectCount = trendValue.collectCount
    trend.shareCount = trendValue.shareCount
    trend.visitCount = trendValue.visitCount
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
    "time": [
        "2024-03-20", "2024-03-21", "2024-03-22", "2024-03-23", "2024-03-24",
        "2024-03-25", "2024-03-26", "2024-03-27", "2024-03-28", "2024-03-29",
        "2024-03-30", "2024-03-31", "2024-04-01", "2024-04-02"
    ],
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
const currentTime=ref('')
const updataTime=()=>{
    const now =new Date()
    currentTime.value=now.toLocaleString()
    // console.log(currentTime.value)
}
const timer=ref<number>(0)
onMounted(()=>{
    updataTime()
    
    timer.value = setInterval(updataTime, 1000) // 每秒更新
})
onUnmounted(() => {
  if (timer.value) clearInterval(timer.value) // 组件销毁时清除定时器
})
</script>
<template>
    <div>
        <div>
            {{ currentTime }}
        </div>
        <div class="header">
            <div v-for="(item, index) in total" :key="index">
                <div>{{ item.name }}</div>
                <div>{{ item.data }}</div>
                <div v-if="item.status">
                    <div v-if="item.changeNum! > 0">
                        {{ item.changeNum }}
                        <div v-if="item.status === 'up'">
                            <svg t="1775129532245" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="4894" width="10" height="10">
                                <path d="M508.8 64l188.8 316.8-156.288-84.672v673.92h-64V296L320 380.8 508.8 64z"
                                    fill="#FF5050" p-id="4895"></path>
                            </svg>
                        </div>
                        <div v-if="item.status === 'equal'">
                            <span>较之前相比没变化</span>
                        </div>
                        <div v-if="item.status === 'down'">
                            <svg t="1775130098502" class="icon" viewBox="0 0 1025 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="5861" width="10" height="10">
                                <path
                                    d="M467.985218 937.525717c0.122866 0.124914 0.24778 0.248804 0.372694 0.370646 11.174684 10.873662 25.852081 16.831651 41.412066 16.831651 0.277473 0 0.554946-0.001024 0.832419-0.005119 15.837458-0.217064 30.643864-6.574368 41.702849-17.905707l334.741901-339.769178c9.525205-9.667525 9.408482-25.226487-0.259043-34.750668-9.666501-9.524181-25.227511-9.408482-34.750668 0.259043l-315.80719 320.551874L536.230246 88.475979c0-13.5716-11.001648-24.573248-24.573248-24.573248-13.5716 0-24.573248 11.001648-24.573248 24.573248l0 798.397108L166.360888 561.132161c-9.52111-9.670597-25.080071-9.791415-34.750668-0.269282-9.670597 9.52111-9.791415 25.080071-0.270306 34.750668L467.985218 937.525717z"
                                    fill="#353535" p-id="5862"></path>
                            </svg>
                        </div>
                    </div>


                </div>
            </div>
        </div>
        <div class="contents">
            <!-- 左侧 -->
            <div class="content">
                <div class="con">
                    <div>剧目分类占比</div>
                    <div>
                        <pie :datas="cateJumu" wd="500px" ht="500px"></pie>
                    </div>
                </div>
                <div class="con">
                    <div>内容发布趋势</div>
                    <div>
                        <div @click="change(everyDram)">每日</div>
                        <div @click="change(weekDram)">每周</div>
                        <div @click="change(monthDram)">每月</div>
                    </div>
                    <div>
                        <Line :datas="Dram" wd="500px" ht="500px"></Line>

                    </div>
                </div>
                <div class="con">
                    <div>内容质量评分</div>
                    <div>
                        <Radar :datas="qualityScoreData" wd="500px" ht="500px" name="内容质量评分"></Radar>
                    </div>
                </div>
            </div>
            <!-- 中间 -->
            <div class="content">
                <div class="con">
                    <div>剧目热度趋势</div>
                    <div>
                        <div @click="changeTrend(everyTrend)">每日</div>
                        <div @click="changeTrend(weekTrend)">每周</div>
                        <div @click="changeTrend(monthTrend)">每月</div>
                    </div>
                    <div>
                        <threeLine :datas="trend" wd="500px" ht="500px" name1="访问量" name2="收藏" name3="分享"></threeLine>
                    </div>
                </div>
                <div class="con">
                    <div>热门剧目TOP10</div>
                    <div>
                        <Bar :datas="topDrama" wd="500px" ht="500px"></Bar>
                    </div>
                </div>
                <div class="con">
                    <div>推荐系统命中率</div>
                    <div>
                        <doubleBar :datas="crt" wd="500px" ht="500px" name1="曝光量" name2="点击量" name3="CTR"></doubleBar>
                    </div>
                </div>
            </div>
            <!-- 右侧 -->
            <div class="content">
                <div class="con">
                    <div>用户地域分布地图</div>
                    <div>
                        <div @click="changeTrend(everyTrend)">每日</div>
                        <div @click="changeTrend(weekTrend)">每周</div>
                        <div @click="changeTrend(monthTrend)">每月</div>
                    </div>
                    <div>
                        <threeLine :datas="trend" wd="500px" ht="500px" name1="访问量" name2="收藏" name3="分享"></threeLine>
                    </div>
                </div>
                <div class="con">
                    <div>用户性别占比</div>
                    <div>
                        <pie :datas="sex" wd="500px" ht="500px"></pie>
                    </div>
                </div>
                <div class="con">
                    <div>用户年龄段分布</div>
                    <div>
                        <Bar :datas="ageData" wd="500px" ht="500px"></Bar>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<style lang="css" scoped>
.header {
    display: flex;
}

.contents {
    display: flex;
}
</style>
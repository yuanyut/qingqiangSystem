<script setup lang="ts">
import { onMounted, reactive, ref, watch } from 'vue'
import * as echarts from 'echarts'
interface LineItem {
    time:string[],
    visitCount:number[],
    collectCount:number[],
    shareCount:number[]
}
const props = defineProps<{
    datas: LineItem,
    wd: string,
    ht: string,
    name1:string,//访问
    name2:string,//收藏
    name3:string //分享
}>()


const chartRef = ref(null)
const updateChart = () => {
    const max = ref(0)
    props.datas.visitCount?.forEach((item) => {
        if (item > max.value)
            max.value = item
    })
    props.datas.collectCount?.forEach((item) => {
        if (item > max.value)
            max.value = item
    })
    props.datas.shareCount?.forEach((item) => {
        if (item > max.value)
            max.value = item
    })
    const lineChart = echarts.init(chartRef.value)

    const option = reactive({
        tooltip: {
            trigger: 'axis',
            // axisPointer: { type: 'cross' }
        },
        legend: {
            data:[props.name1,props.name2,props.name3]
        },
        xAxis: [
            {
                type: 'category',
                axisTick: {
                    alignWithLabel: true
                },
                data: props.datas.time
            }
        ],
        yAxis: [
            {
                type: 'value',
                min: 0,
                max: max,
                position: 'left',
                axisLabel: {
                    formatter: '{value}'
                }
            },
        ],
        series: [
            {
                name: props.name1,//访问
                type: 'line',
                data: props.datas.visitCount
            },
            {
                name: props.name2,
                type: 'line',
                data: props.datas.collectCount
            },
            {
                name: props.name3,
                type: 'line',
                data: props.datas.shareCount
            }
        ]
    })
    lineChart.setOption(option)
}
onMounted(() => {
    updateChart()
})
watch(
    () => props.datas,
    () => {
        updateChart()  // 数据变化时刷新图表
    },
    { deep: true }  // 必须深度监听（否则数组变化不触发）
)
</script>
<template>
    <div>
        <div ref="chartRef" :style="{ width: props.wd, height: props.ht }">
        </div>
    </div>

</template>
<style lang="css" scoped></style>
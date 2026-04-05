<script setup lang="ts">
import { onMounted, reactive, ref, watch } from 'vue'
import * as echarts from 'echarts'
interface LineItem {
    time: string[],
    dramaCount?: number[],
    articleCount?: number[]
}
const props = defineProps<{
    datas: LineItem,
    wd: string,
    ht: string
}>()


const chartRef = ref(null)
const updateChart = () => {
    const max1 = ref(0)
    props.datas.articleCount?.forEach((item) => {
        if (item > max1.value)
            max1.value = item
    })
    const max2 = ref(0)
    props.datas.dramaCount?.forEach((item) => {
        if (item > max2.value)
            max2.value = item
    })
    const pieChart = echarts.init(chartRef.value)

    const option = reactive({
        tooltip: {
            trigger: 'axis',
            axisPointer: { type: 'cross' }
        },
        legend: {},
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
                name: '新增剧目',
                min: 0,
                max: max2,
                position: 'right',
                axisLabel: {
                    formatter: '{value}'
                }
            },
            {
                type: 'value',
                name: '文章数量',
                min: 0,
                max: max1,
                position: 'left',
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name: '新增剧目',
                type: 'bar',
                yAxisIndex: 0,
                data: props.datas.dramaCount
            },
            {
                name: '文章数量',
                type: 'line',

                yAxisIndex: 1,
                data: props.datas.articleCount
            }
        ]
    })
    pieChart.setOption(option)
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
<script setup lang="ts">
import { onMounted, reactive, ref, watch } from 'vue'
import * as echarts from 'echarts'
const colors = ['#5070dd', '#b6d634', '#505372'];
interface LineItem {
    time: string[],
    exposure?: number[],
    click?: number[],
    ctr?: number[]
}
const props = defineProps<{
    datas: LineItem,
    wd: string,
    ht: string,
    name1:string,
    name2:string,
    name3:string
}>()


const chartRef = ref(null)
const updateChart = () => {
    const max1 = ref(0)
    props.datas.exposure?.forEach((item) => {
        if (item > max1.value)
            max1.value = item
    })
    const max2 = ref(0)
    props.datas.click?.forEach((item) => {
        if (item > max2.value)
            max2.value = item
    })
    const max3 = ref(0)
    props.datas.ctr?.forEach((item) => {
        if (item > max3.value)
            max3.value = item
    })
    const pieChart = echarts.init(chartRef.value!)

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
                name:props.name2,
                position: 'right',
                alignTicks: true,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: colors[0]
                    }
                },
                axisLabel: {
                    formatter: '{value}'
                }
            },
            {
                type: 'value',
                name:props.name1,
                position: 'right',
                alignTicks: true,
                offset: 80,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: colors[1]
                    }
                },
                axisLabel: {
                    formatter: '{value}'
                }
            },
            {
                type: 'value',
                name:props.name3,
                position: 'left',
                alignTicks: true,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: colors[2]
                    }
                },
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name:props.name2,
                type: 'bar',
                yAxisIndex: 0,
                data: props.datas.click
            },
            {
                name:props.name1,
                type: 'bar',
                yAxisIndex: 1,
                data: props.datas.exposure
            },
            {
                name:props.name3,
                type: 'line',

                yAxisIndex: 2,
                data: props.datas.ctr
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

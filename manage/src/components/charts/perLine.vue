<script setup lang="ts">
import { onMounted, reactive, ref, watch, computed } from 'vue'
import * as echarts from 'echarts'

interface LineItem {
    time: string[],
    dramaCount?: number[],
    articleCount?: number[]
}
interface nameItem {
    name1: string,
    name2?: string,
}
const props = defineProps<{
    datas: LineItem,
    wd: string,
    ht: string,
    name: nameItem,
}>()

const chartRef = ref(null)

// 判断显示哪些数据
const hasDramaData = computed(() => {
    return props.datas.dramaCount && props.datas.dramaCount.length > 0
})

const hasArticleData = computed(() => {
    return props.datas.articleCount && props.datas.articleCount.length > 0
})

// 判断是单数据还是双数据
const isSingleData = computed(() => {
    return (hasDramaData.value && !hasArticleData.value) || (!hasDramaData.value && hasArticleData.value)
})

const isDoubleData = computed(() => {
    return hasDramaData.value && hasArticleData.value
})

const updateChart = () => {
    if (!chartRef.value) return

    const pieChart = echarts.init(chartRef.value)

    // 计算最大值
    const max1 = ref(100)
    props.datas.articleCount?.forEach((item) => {
        if (item > max1.value) max1.value = item
    })
    const max2 = ref(100)
    props.datas.dramaCount?.forEach((item) => {
        if (item > max2.value) max2.value = item
    })

    // 构建 series
    const series: any[] = []

    if (hasDramaData.value) {
        series.push({
            name: props.name.name1,
            type: 'bar',
            yAxisIndex: isDoubleData.value ? 0 : undefined,
            data: props.datas.dramaCount,
            barWidth: '30%',
            itemStyle: {
                borderRadius: [4, 4, 0, 0],
                color: '#5470c6'
            }
        })
    }

    if (hasArticleData.value) {
        series.push({
            name: props.name.name2,
            type: 'line',
            yAxisIndex: isDoubleData.value ? 1 : undefined,
            data: props.datas.articleCount,
            smooth: true,
            lineStyle: {
                width: 2,
                color: '#fac858'
            },
            symbol: 'circle',
            symbolSize: 6,
            itemStyle: {
                color: '#fac858'
            }
        })
    }

    // 构建 yAxis（根据数据数量决定）
    let yAxis: any[] = []

    if (isDoubleData.value) {
        // 双数据：左右两个 Y 轴
        yAxis = [
            {
                type: 'value',
                name: props.name.name1,
                min: 0,
                max: max2.value > 0 ? max2.value : undefined,
                position: 'right',
                axisLabel: {
                    formatter: '{value}'
                }
            },
            {
                type: 'value',
                name: props.name.name2,
                min: 0,
                max: max1.value > 0 ? max1.value : undefined,
                position: 'left',
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ]
    } else {
        // 单数据：只有一个 Y 轴
        const maxValue = hasDramaData.value ? max2.value : max1.value
        const axisName = hasDramaData.value ? props.name.name1 : props.name.name2

        yAxis = [
            {
                type: 'value',
                name: axisName,
                min: 0,
                max: maxValue > 0 ? maxValue : undefined,
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ]
    }

    const option = reactive({
        tooltip: {
            trigger: 'axis',
            axisPointer: { type: 'shadow' },
            formatter: function (params: any) {
                let result = params[0].axisValue + '<br/>'
                params.forEach((item: any) => {
                    result += `${item.marker} ${item.seriesName}: ${item.value}%<br/>`
                })
                return result
            }
        },
        legend: {
            data: series.map(s => s.name),
            top: 0
        },
        grid: {
            left: '8%',
            right: isDoubleData.value ? '8%' : '5%',
            top: '15%',
            bottom: '8%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel: {
                    rotate: props.datas.time.length > 10 ? 30 : 0,
                    interval: 0
                },
                data: props.datas.time
            }
        ],
        yAxis: yAxis,
        series: series
    })

    pieChart.setOption(option)

    // 响应窗口大小变化
    window.addEventListener('resize', () => {
        pieChart.resize()
    })
}

onMounted(() => {
    updateChart()
})

watch(
    () => props.datas,
    () => {
        updateChart()
    },
    { deep: true }
)

// 监听宽高变化
watch(
    () => [props.wd, props.ht],
    () => {
        setTimeout(() => {
            if (chartRef.value) {
                const chart = echarts.getInstanceByDom(chartRef.value)
                chart?.resize()
            }
        }, 100)
    }
)
</script>

<template>
    <div>
        <div ref="chartRef" :style="{ width: props.wd, height: props.ht }">
        </div>
    </div>
</template>

<style lang="css" scoped></style>
<script setup lang="ts">
import {onMounted, reactive, ref, watch} from 'vue'
import * as echarts from 'echarts'
interface PieItem{
    value:number
    name:string,
    
}
const props=defineProps<{
    datas:PieItem[],
    wd:string,
    ht:string
}>()
console.log(
    '这是饼图数据',props.datas,props.ht,props.wd
)
const chartRef=ref(null)
let pieChart: echarts.ECharts | null = null

// 渲染图表
const renderChart = () => {
    if (!chartRef.value) return
    if (!pieChart) {
        pieChart = echarts.init(chartRef.value)
    }
    const option = {
        tooltip: {
            trigger: 'item'//显示数据
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                type: 'pie',
                radius: '50%',
                data: props.datas,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0,0,0,0.5)'
                    }
                }
            }

        ]
    }
    pieChart.setOption(option)
}

onMounted(() => {
    renderChart()
})

// 监听数据变化
watch(() => props.datas, () => {
    console.log('数据变化了:', props.datas)
    renderChart()
}, { deep: true })

</script>
<template>
    <div>
        <div ref="chartRef" :style="{width:props.wd,height:props.ht}">
        </div>
    </div>

</template>
<style lang="css" scoped>


</style>
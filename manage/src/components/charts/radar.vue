<script setup lang="ts">
import { onMounted, reactive, ref, watch } from 'vue'
import * as echarts from 'echarts'
interface overview {
    avgUserRating: number,
    avgCommentQuality: number,
    highQualityContentRate: number,
    activeContentCount: number,
    contentCount: number
}
interface radar{
    indicator:{
        name:string,
        max:number
    }[],value:number[]
}
interface qualityScore {
    overview: overview,
    radar: radar
}

const props = defineProps<{
    datas: qualityScore,
    wd: string,
    ht: string,
    name:string
}>()

const radarData=reactive<number[]>([])
props.datas.radar.value.forEach((val, index) => {
  radarData.push(val)
})

const chartRef = ref(null)
onMounted(() => {
    const radarChart = echarts.init(chartRef.value)
    const option = reactive({
        tooltip: {
            
            trigger: 'item'//显示数据
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        radar:{
            indicator:props.datas.radar.indicator
        },
        series: [
            {
                type: 'radar',
                data: [
                    {
                        value:radarData,
                       name:props.name
                    }
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0,0,0,0.5)'
                    }
                }
            }

        ]
    })
    radarChart.setOption(option)
    console.log(option)
})

</script>
<template>
    <div>
        <div ref="chartRef" :style="{ width: props.wd, height: props.ht }">
        </div>
    </div>

</template>
<style lang="css" scoped></style>
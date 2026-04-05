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
onMounted(()=>{
    const pieChart=echarts.init(chartRef.value)
const option=reactive({
    tooltip: {
    trigger: 'item'//显示数据
  },
    legend:{
        orient:'vertical',
        left:'left'
    },
    series:[
       {
        type:'pie',
        radius:'50%',
        data:props.datas,
        emphasis:{
            itemStyle:{
                shadowBlur:10,
                shadowOffsetX:0,
                shadowColor:'rgba(0,0,0,0.5)'
            }
        }
       }

    ]
})
    pieChart.setOption(option)
})

</script>
<template>
    <div>
        <div ref="chartRef" :style="{width:props.wd,height:props.ht}">
        </div>
    </div>

</template>
<style lang="css" scoped>


</style>
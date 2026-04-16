<script setup lang="ts">
import { onMounted, ref, watch, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'//核心可视化库
import chinaGeoJson from '@/assets/china.json'//中国地图的 GeoJSON 数据，用于渲染地图

interface MapItem {//定义每个城市的数据结构，包含城市名和对应数值
    name: string
    value: number
}

// 父组件传进的数据 Props：数据 + 宽高
const props = defineProps<{
    datas: MapItem[]
    wd: string
    ht: string
}>()
//绑定 DOM 容器
const chartRef = ref(null)
//ECharts 实例，保证只创建一次
let chartInstance: echarts.ECharts | null = null

// 中国主要省份经纬度（可扩展）,散点图需要经纬度坐标
const geoCoordMap: Record<string, [number, number]> = {
    '北京': [116.4074, 39.9042],
    '上海': [121.4737, 31.2304],
    '广州': [113.2644, 23.1291],
    '深圳': [114.0579, 22.5431],
    '杭州': [120.1551, 30.2741],
    '天津': [117.200983, 39.084158],
    '河北': [114.514901, 38.042302],
    '山西': [112.549248, 37.857014],
    '内蒙古': [111.670801, 40.818311],
    '辽宁': [123.429096, 41.796767],
    '吉林': [125.3245, 43.886841],
    '黑龙江': [126.642464, 45.756967],
    '江苏': [118.767413, 32.041544],
    '浙江': [120.153576, 30.287459],
    '安徽': [117.283042, 31.86119],
    '福建': [119.306239, 26.075302],
    '江西': [115.892151, 28.676493],
    '山东': [117.000923, 36.675807],
    '河南': [113.625368, 34.746599],
    '湖北': [114.305558, 30.592755],
    '湖南': [112.982279, 28.19409],
    '广东': [113.280637, 23.125178],
    '广西': [108.320004, 22.82402],
    '海南': [110.33119, 20.031971],
    '重庆': [106.504962, 29.533155],
    '四川': [104.066803, 30.672269],
    '贵州': [106.713478, 26.578343],
    '云南': [102.712251, 25.040609],
    '西藏': [91.132212, 29.660361],
    '陕西': [108.940175, 34.265741],
    '甘肃': [103.823557, 36.058039],
    '青海': [101.778916, 36.623178],
    '宁夏': [106.278179, 38.46637],
    '新疆': [87.617733, 43.792818],
    '台湾': [121.509062, 25.044332],
    '香港': [114.173355, 22.320048],
    '澳门': [113.54909, 22.198791]
}

// 渲染函数
const renderChart = () => {
    //只在第一次初始化时创建 ECharts 实例
    if (!chartRef.value) return

    if (!chartInstance) {
        chartInstance = echarts.init(chartRef.value)
        echarts.registerMap('china', chinaGeoJson as any)
    }

    // 散点数据
    // filter：确保城市有经纬度
    // value 展开 [经度, 纬度, 数值]，ECharts 散点需要 [x, y, value]
    // ! 告诉 TypeScript 这里不会是 undefined
    // .map=>[
    //     { name: '北京', value: [116.4074, 39.9042, 300] },
    //     { name: '上海', value: [121.4737, 31.2304, 500] },
    //     { name: '广州', value: [113.2644, 23.1291, 400] }
    // ]
    const scatterData = props.datas
        .map(item => {
            // 处理省份名称，移除"市"字
            let provinceName = item.name;
            if (provinceName.endsWith('市')) {
                provinceName = provinceName.substring(0, provinceName.length - 1);
            }
            return { ...item, provinceName };
        })
        .filter(item => geoCoordMap[item.provinceName])
        .map(item => ({
            name: item.name,
            value: [...geoCoordMap[item.provinceName]!, item.value]
        }))
    // 控制地图/散点的颜色范围
    // 根据城市数值自动映射深浅色
    // calculable: true 可以拖动滑块实时调整
    const maxValue = Math.max(...props.datas.map(d => d.value), 100)

    const option: echarts.EChartsOption = {
        tooltip: {
            trigger: 'item',
            formatter: (params: any) => {
                if (params.seriesType === 'effectScatter') {
                    return `${params.name}: ${params.value[2]}`
                }
                return params.name
            }
        },
        visualMap: {
            min: 0,
            max: maxValue,
            left: 'left',
            bottom: 'bottom',
            text: ['高', '低'],
            calculable: true,
            inRange: { color: ['#fff', '#edd'] }
        },

        //核心解决“双地图问题”的关键
        geo: {
            map: 'china',
            roam: true,            // 放大/缩小/拖拽
            label: { show: true,
                color:'#fff'
             },
            itemStyle: {
                areaColor: '#2e72bf',
                borderColor: '#333'
            },
        },
        zoom:1.8,
        series: [
            {
                name: '散点',
                type: 'effectScatter',//带波纹特效的散点
                coordinateSystem: 'geo',//绑定到上面唯一的 geo 地图
                data: scatterData,
                symbolSize: 12,//散点大小
                showEffectOn: 'render',
                rippleEffect: { brushType: 'stroke' },
                itemStyle: { color: '#fff' }//散点颜色
            }
        ]
    }

    chartInstance.setOption(option)
}

// 初始化
onMounted(() => renderChart())

// 数据变化时重新渲染
watch(() => props.datas, () => renderChart(), { deep: true })

// 组件卸载时销毁图表
onBeforeUnmount(() => {
    chartInstance?.dispose()
})
</script>

<template>
    <div ref="chartRef" :style="{ width: props.wd, height: props.ht }"></div>
</template>
<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import dialogVisible from '@/utils/dialogVisible.vue'
import edit from '@/components/Contents/Drama/tables/edit.vue'

const search = defineModel('search')
const tableData: any = defineModel('tableData')
const deleteModul = ref(false)
const editModul = ref(false)
const opearIndex = ref(0)
const editContent = ref()
const selects = defineModel('selects')
const safeForm: any = defineModel('formHeader')
const currentPage = defineModel('currentPage', { default: 1 })
const pageSize = defineModel('pageSize', { default: 10 })
const total = defineModel('total', { default: 0 })
const emit = defineEmits(['edit-click', 'delete-click', 'confirm', 'page-change', 'size-change'])

// 当前正在播放的视频（用于控制同一时间只有一个视频播放）
const currentPlayingVideo = ref<string | null>(null)

// 删除单个视频
const deleteClick = (value: any) => {
    opearIndex.value = value
    // 触发删除点击事件，传递当前行的id
    emit('delete-click', tableData.value[value].id)
}

// 确认删除（支持单个和批量）
const deleteClicks = () => {
    console.log(selects.value)
    if (selects.value === false) {
        deleteModul.value = false
    } else if (selects.value === true) {
        selects.value = false
    }
}

// 编辑视频
const editClick = (value: any) => {
    editModul.value = true
    console.log(value)
    editContent.value = value
    // 触发编辑点击事件，传递当前行数据
    emit('edit-click', value)
}

const multipleSelection: any = defineModel('multipleSelection')

const handleSelectionChange = (val: any[]) => {
    multipleSelection.value = val
    console.log('选中的视频数据:', val)
}

// 监听批量删除选择
watch(selects, (newVal) => {
    if (newVal === false) return
    if (newVal === true) {
        deleteModul.value = true
    }
})

// 监听搜索（可对接后端API）
watch(search, (newVal) => {
    if (newVal === false) return
    if (newVal === true) {
        // 调用后端返回的视频数据
    }
})

// 监听编辑内容变化，更新表格数据
watch(editContent, (newVal) => {
    console.log('666666', newVal)
    if (editModul.value === false && newVal) {
        const index = tableData.value.findIndex((item: any) => item.id === newVal.id)
        if (index !== -1) {
            console.log('更新表格第', index, '行')
            tableData.value[index] = { ...tableData.value[index], ...newVal }
        }
    }
}, { deep: true })

// 分页配置
const small = ref(false)
const background = ref(false)
const disabled = ref(false)

const handleSizeChange = (val: number) => {
    console.log(`${val} items per page`)
    pageSize.value = val
    emit('size-change', val)
}
const handleCurrentChange = (val: number) => {
    console.log(`current page: ${val}`)
    currentPage.value = val
    emit('page-change', val)
}

// 处理视频播放事件（暂停其他视频）
const handleVideoPlay = (videoId: string, event: Event) => {
    if (currentPlayingVideo.value && currentPlayingVideo.value !== videoId) {
        // 暂停其他正在播放的视频
        const otherVideo = document.getElementById(`video-${currentPlayingVideo.value}`) as HTMLVideoElement
        if (otherVideo) {
            otherVideo.pause()
        }
    }
    currentPlayingVideo.value = videoId
}

// 格式化时长（秒 -> mm:ss）
const formatDuration = (seconds: number) => {
    if (!seconds) return '00:00'
    const mins = Math.floor(seconds / 60)
    const secs = seconds % 60
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`
}

// 格式化播放量
const formatViewCount = (count: number) => {
    if (!count) return '0'
    if (count >= 10000) {
        return (count / 10000).toFixed(1) + 'w'
    }
    return count.toString()
}
</script>

<template>
    <div>
        <div>
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                <!-- 多选列 -->
                <el-table-column type="selection" width="55" />
                
                <!-- 视频播放列（核心） -->
                <el-table-column label="视频预览" width="280">
                    <template #default="scope">
                        <video 
                            :id="`video-${scope.row.id}`"
                            :src="scope.row.videoUrl" 
                            controls 
                            preload="metadata"
                            style="width: 250px; height: 140px; border-radius: 8px; background: #000;"
                            @play="handleVideoPlay(scope.row.id, $event)"
                        >
                            您的浏览器不支持 video 标签。
                        </video>
                    </template>
                </el-table-column>
                
                <!-- 视频标题 -->
                <el-table-column property="title" label="视频标题" width="180" show-overflow-tooltip />
                
                <!-- 视频描述 -->
                <el-table-column property="description" label="描述" width="160" show-overflow-tooltip />
                
                <!-- 关联内容 -->
                
                
                <!-- 上传者/作者 -->
                <el-table-column property="actor" label="演员" width="120" />
                
                <!-- 视频分类 -->
                <el-table-column property="category" label="分类" width="100" />
                
                <!-- 视频时长 -->
                
                
                <!-- 播放量 -->
                <el-table-column property="clickCount" label="点击量" width="100" />
                
                <!-- 点赞数 -->
                <el-table-column property="likeCount" label="点赞数" width="100">
                    <template #default="scope">
                        {{ formatViewCount(scope.row.likeCount) }}
                    </template>
                </el-table-column>
                
                <!-- 发布时间 -->
                <!-- <el-table-column label="发布时间" width="160">
                    <template #default="scope">{{ scope.row.publishTime }}</template>
                </el-table-column> -->
                
                <!-- 视频状态 -->
                <el-table-column property="statusText" label="状态" width="100" />
                
                <!-- 操作列 -->
                <el-table-column fixed="right" label="操作" min-width="120">
                    <template #default="scope">
                        <el-button link type="primary" size="small" @click="editClick(scope.row)">
                            编辑
                        </el-button>
                        <el-button link type="primary" size="small" @click.prevent="deleteClick(scope.$index)">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        
        <!-- 分页 -->
        <div style="margin-top: 25px;">
            <el-pagination 
                v-model:currentPage="currentPage" 
                v-model:page-size="pageSize" 
                :small="small"
                :disabled="disabled" 
                :background="background" 
                layout="prev, pager, next, jumper" 
                :total="total"
                @size-change="handleSizeChange" 
                @current-change="handleCurrentChange" 
            />
        </div>
        
        <!-- 删除确认弹窗 -->
        <div>
            <dialog-visible v-model="deleteModul" @submit-fun="deleteClicks"></dialog-visible>
        </div>
        
        <!-- 编辑弹窗 -->
        <edit v-model:dialogFormVisible="editModul" v-model:content="editContent" :title="editContent?.id ? '编辑视频' : '新增视频'" :opear="editContent?.id ? '1' : '0'" @confirm="(data) => emit('confirm', data)"></edit>
    </div>
</template>

<style lang="css" scoped>
:deep(.el-pagination) {
    display: flex;
    justify-content: center;
}

:deep(.el-table .cell) {
    white-space: nowrap;
}

/* 视频样式优化 */
video {
    object-fit: cover;
    cursor: pointer;
}

video::-webkit-media-controls {
    border-radius: 8px;
}
</style>
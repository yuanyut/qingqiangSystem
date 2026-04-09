<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import dialogVisible from '@/utils/dialogVisible.vue'
import edit from '@/components/Comment/List/tables/edit.vue'

const search = defineModel('search')
const tableData: any = defineModel('tableData')
const deleteModul = ref(false)
const editModul = ref(false)
const opearIndex = ref(0)
const editContent = ref()
const selects = defineModel('selects')
const safeForm: any = defineModel('formHeader')

// 删除单个评论
const deleteClick = (value: any) => {
    opearIndex.value = value
    deleteModul.value = true
    console.log('fu', deleteModul.value)
}

// 确认删除（支持单个和批量）
const deleteClicks = () => {
    console.log(selects.value)
    if (selects.value === false) {
        deleteModul.value = false
        tableData.value.splice(opearIndex.value, 1)
    } else if (selects.value === true) {
        multipleSelection.value.forEach((item1: any) => {
            const index = tableData.value.findIndex((item: any) => item.id === item1.id)
            console.log(`评论 ${item1.content.substring(0, 20)}... 在 tableData 的索引:`, index)
            tableData.value.splice(index, 1)
        })
        selects.value = false
    }
}

// 编辑评论
const editClick = (value: any) => {
    editModul.value = true
    console.log(value)
    editContent.value = value
}

const multipleSelection: any = defineModel('multipleSelection')

const handleSelectionChange = (val: any[]) => {
    multipleSelection.value = val
    console.log('选中的评论数据:', val)
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
        // 调用后端返回的评论数据
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
const currentPage3 = ref(1)
const pageSize3 = ref(100)
const small = ref(false)
const background = ref(false)
const disabled = ref(false)

const handleSizeChange = (val: number) => {
    console.log(`${val} items per page`)
}

const handleCurrentChange = (val: number) => {
    console.log(`current page: ${val}`)
}

// 格式化数字
const formatCount = (count: number) => {
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
                
                <!-- 评论内容 -->
                <el-table-column property="content" label="评论内容" width="300" show-overflow-tooltip />
                
                <!-- 用户 -->
                <el-table-column property="user" label="用户" width="150" />
                
                <!-- 目标类型 -->
                <el-table-column property="targetType" label="目标类型" width="100" />
                
                <!-- 目标标题 -->
                <el-table-column property="targetTitle" label="目标标题" width="200" show-overflow-tooltip />
                
                <!-- 发布时间 -->
                <el-table-column label="发布时间" width="180">
                    <template #default="scope">{{ scope.row.publishTime }}</template>
                </el-table-column>
                
                <!-- 回复数 -->
                <el-table-column property="replyCount" label="回复数" width="100">
                    <template #default="scope">
                        {{ formatCount(scope.row.replyCount) }}
                    </template>
                </el-table-column>
                
                <!-- 点赞数 -->
                <el-table-column property="likeCount" label="点赞数" width="100">
                    <template #default="scope">
                        {{ formatCount(scope.row.likeCount) }}
                    </template>
                </el-table-column>
                
                <!-- 状态 -->
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
                v-model:currentPage="currentPage3" 
                v-model:page-size="pageSize3" 
                :small="small"
                :disabled="disabled" 
                :background="background" 
                layout="prev, pager, next, jumper" 
                :total="1000"
                @size-change="handleSizeChange" 
                @current-change="handleCurrentChange" 
            />
        </div>
        
        <!-- 删除确认弹窗 -->
        <div>
            <dialog-visible v-model="deleteModul" @submit-fun="deleteClicks"></dialog-visible>
        </div>
        
        <!-- 编辑弹窗 -->
        <edit v-model:dialogFormVisible="editModul" v-model:content="editContent" title="编辑评论" opear="0"></edit>
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
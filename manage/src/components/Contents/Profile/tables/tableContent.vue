<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import dialogVisible from '@/utils/dialogVisible.vue'
import edit from '@/components/Contents/Profile/tables/edit.vue'
import type { ProfileData } from '@/api/api'
import { deleteProfile } from '@/api/api'
import { ElMessage } from 'element-plus'

const search = defineModel('search')
const tableData: any = defineModel('tableData')
const deleteModul = ref(false)
const editModul = defineModel<boolean>('editModul', { default: false })
const opearIndex = ref(0)
const editContent = defineModel<ProfileData | undefined>('editContent')
const selects = defineModel('selects')
const safeForm: any = defineModel('formHeader')

// 删除单个资讯
const deleteClick = (value: any) => {
    opearIndex.value = value
    deleteModul.value = true
    console.log('fu', deleteModul.value)
}

// 确认删除（支持单个和批量）
const deleteClicks = async () => {
    console.log(selects.value)
    try {
        if (selects.value === false) {
            const item = tableData.value[opearIndex.value]
            if (item && item.id) {
                await deleteProfile(item.id)
                tableData.value.splice(opearIndex.value, 1)
                ElMessage.success('删除成功')
            }
        } else if (selects.value === true) {
            const ids = multipleSelection.value.map((item: ProfileData) => item.id).filter((id: any) => id)
            if (ids.length > 0) {
                await deleteProfile(ids)
                multipleSelection.value.forEach((item1: ProfileData) => {
                    const index = tableData.value.findIndex((item: ProfileData) => item.id === item1.id)
                    if (index !== -1) {
                        tableData.value.splice(index, 1)
                    }
                })
                selects.value = false
                ElMessage.success('批量删除成功')
            }
        }
    } catch (error) {
        console.error('删除失败:', error)
        ElMessage.error('删除失败，请重试')
    } finally {
        deleteModul.value = false
    }
}

// 编辑资讯
const editClick = (value: ProfileData) => {
    editModul.value = true
    console.log(value)
    editContent.value = value
}

const multipleSelection: any = defineModel('multipleSelection')

const handleSelectionChange = (val: any[]) => {
    multipleSelection.value = val
    console.log('选中的资讯数据:', val)
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
        // 调用后端返回的资讯数据
    }
})

// 监听编辑内容变化，更新表格数据
watch(editContent, (newVal) => {
    console.log('666666', newVal)
    if (editModul.value === false && newVal) {
        const index = tableData.value.findIndex((item: ProfileData) => item.id === newVal.id)
        if (index !== -1) {
            console.log('更新表格第', index, '行')
            const profile = tableData.value[index];
            if (profile) {
                Object.assign(profile, {
                    title: newVal.title || '',
                    content: newVal.content || '',
                    source: newVal.source || '',
                    cover: newVal.cover || '',
                    viewCount: newVal.viewCount || 0,
                    likeCount: newVal.likeCount || 0,
                    createTime: newVal.createTime || '',
                    updateTime: newVal.updateTime || '',
                    status: newVal.status || 0,
                    category: newVal.category || ''
                });
            }
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

// 格式化播放量
const formatViewCount = (count: number) => {
    if (!count) return '0'
    if (count >= 10000) {
        return (count / 10000).toFixed(1) + 'w'
    }
    return count.toString()
}

// 格式化状态
const formatStatus = (status: number) => {
    return status === 0 ? '已上架' : '已下架'
}
</script>

<template>
    <div>
        <div>
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                <!-- 多选列 -->
                <el-table-column type="selection" width="55" />
                <!-- 封面 -->
                <el-table-column property="cover" label="封面" width="150">
                    <template #default="scope">
                        <img :src="scope.row.cover" alt="封面" style="width: 100%; height: 100%; object-fit: cover;" />
                    </template>
                </el-table-column>
                <!-- 资讯标题 -->
                <el-table-column property="title" label="资讯标题" width="200" show-overflow-tooltip />

                <!-- 资讯内容 -->
                <el-table-column property="content" label="资讯内容" width="300" show-overflow-tooltip />

                <!-- 分类 -->
                <!-- <el-table-column property="category" label="分类" width="120" /> -->

                <!-- 来源 -->
                <el-table-column property="source" label="来源" width="120" />

                <!-- 发布时间 -->
                <el-table-column label="发布时间" width="180">
                    <template #default="scope">{{ scope.row.createTime }}</template>
                </el-table-column>

                <!-- 点击量 -->
                <el-table-column property="viewCount" label="点击量" width="100">
                    <template #default="scope">
                        {{ formatViewCount(scope.row.viewCount) }}
                    </template>
                </el-table-column>

                <!-- 点赞数 -->
                <el-table-column property="likeCount" label="点赞数" width="100">
                    <template #default="scope">
                        {{ formatViewCount(scope.row.likeCount) }}
                    </template>
                </el-table-column>

                <!-- 状态 -->
                <el-table-column label="状态" width="100">
                    <template #default="scope">{{ formatStatus(scope.row.status) }}</template>
                </el-table-column>

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
        

        <!-- 删除确认弹窗 -->
        <div>
            <dialog-visible v-model="deleteModul" @submit-fun="deleteClicks"></dialog-visible>
        </div>

        <!-- 编辑弹窗 -->
        <edit v-model:dialogFormVisible="editModul" v-model:content="editContent" title="编辑资讯" opear="1"></edit>
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
</style>
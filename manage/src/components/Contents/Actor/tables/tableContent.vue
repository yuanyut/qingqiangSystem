<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import dialogVisible from '@/utils/dialogVisible.vue'
import edit from '@/components/Contents/Actor/tables/edit.vue'
import { deleteActor } from '@/api/api'
import { ElMessage } from 'element-plus'

const search = defineModel('search')
const tableData: any = defineModel('tableData')
const deleteModul = ref(false)
const editModul = defineModel<boolean>('editModul')
const opearIndex = ref(0)
const editContent = defineModel<{ id?: number; name: string; avatar: string; intro: string; roleName: string; style: string; joinDate: string; worksCount: number; viewCount: number; likeCount: number; createTime: string; updateTime: string; status: number; dramas: any[] }>('editContent')
const selects = defineModel('selects')
const status = defineModel('status')

const safeForm: any = defineModel('formHeader')
const multipleSelection: any = defineModel('multipleSelection')
// console.log(tableData.value)
// 加载状态
const loading = ref(false)

const deleteClick = (value: any) => {
    opearIndex.value = value
    deleteModul.value = true
    console.log('fu', deleteModul.value)
}

const deleteClicks = async () => {
    loading.value = true
    try {
        if (selects.value === false) {
            // 单个删除
            const item = tableData.value[opearIndex.value]
            if (item && item.id) {
                await deleteActor(item.id)
                tableData.value.splice(opearIndex.value, 1)
                ElMessage.success('删除成功')
            }
        } else if (selects.value === true) {
            // 批量删除
            const ids = multipleSelection.value.map((item: any) => item.id)
            if (ids.length > 0) {
                await deleteActor(ids)
                // 从表格中删除选中的项
                multipleSelection.value.forEach((item1: any) => {
                    const index = tableData.value.findIndex((item: any) => item.id === item1.id)
                    if (index !== -1) {
                        tableData.value.splice(index, 1)
                    }
                })
                ElMessage.success('批量删除成功')
                selects.value = false
            }
        }
    } catch (error) {
        console.error('删除失败:', error)
        ElMessage.error('删除失败，请重试')
    } finally {
        loading.value = false
        deleteModul.value = false
    }
}

const editClick = (value: any) => {
    editModul.value = true
    console.log('编辑按钮点击，数据:', value)
    // 确保字段映射正确
    editContent.value = {
        id: value.id,
        name: value.name,
        avatar: value.avatar,
        intro: value.intro,
        roleName: value.roleName,
        style: value.style,
        joinDate: value.joinDate,
        worksCount: value.worksCount || 0,
        viewCount: value.viewCount || 0,
        likeCount: value.likeCount || 0,
        createTime: value.createTime || '',
        updateTime: value.updateTime || '',
        status: value.status,
        dramas: value.dramas || []
    }
    console.log('editContent.value 设置为:', editContent.value)
}

const handleSelectionChange = (val: any[]) => {
    multipleSelection.value = val
    console.log('选中的行数据:', val)
}

watch(selects, (newVal) => {
    if (newVal === false) return
    if (newVal === true) {
        deleteModul.value = true
    }
})
watch(() => editModul.value, (newVal, oldVal) => {
    console.log('弹窗状态变化:', oldVal, '->', newVal)
    // 只在弹窗关闭时更新表格
    if (oldVal === true && newVal === false && editContent.value?.id) {
        console.log('弹窗关闭，准备更新表格数据，editContent.value:', editContent.value)
        const index = tableData.value.findIndex((item: any) => item.id === editContent.value?.id)
        if (index !== -1) {
            console.log('找到对应数据，索引:', index)
            if (editContent.value) {
                Object.assign(tableData.value[index], editContent.value);
                console.log('表格数据更新后:', tableData.value[index])
            }
        } else {
            console.log('未找到对应id的演员数据:', editContent.value?.id)
        }
    }
})

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

// 处理编辑确认事件
const handleEditConfirm = (data: any) => {
    console.log('编辑确认事件触发，数据:', data)
    if (data && data.id) {
        const index = tableData.value.findIndex((item: any) => item.id === data.id)
        if (index !== -1) {
            // 更新表格数据
            const actor = tableData.value[index];
            if (actor) {
                Object.assign(actor, {
                    name: data.name || '',
                    avatar: data.avatar || '',
                    intro: data.intro || '',
                    roleName: data.roleName || '',
                    style: data.style || '',
                    joinDate: data.joinDate || '',
                    worksCount: data.worksCount || 0,
                    viewCount: data.viewCount || 0,
                    likeCount: data.likeCount || 0,
                    createTime: data.createTime || '',
                    updateTime: data.updateTime || '',
                    status: data.status,
                    dramas: data.dramas || []
                });
                console.log('表格数据更新成功:', tableData.value[index])
            }
        }
    }
}
</script>

<template>
    <div>
        <div>
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange" v-loading="loading">
                <el-table-column type="selection" width="55" />
                
                <el-table-column label="头像" width="80">
                    <template #default="{ row }">
                        <el-image 
                            :src="row.avatar" 
                            style="width: 50px; height: 50px; border-radius: 4px;"
                            fit="cover"
                            :preview-src-list="[row.avatar]"
                        />
                    </template>
                </el-table-column>
                
                <el-table-column property="id" label="ID" width="60" />
                <el-table-column property="name" label="姓名" min-width="100" show-overflow-tooltip />
                <el-table-column property="roleName" label="角色名" width="100" />
                <el-table-column property="style" label="风格" width="100" />

                <el-table-column property="worksCount" label="作品数量" width="100" sortable />
                <el-table-column property="intro" label="介绍" width="80"  show-overflow-tooltip />
                <el-table-column property="viewCount" label="点击量" width="100" sortable />
                <el-table-column property="likeCount" label="点赞" width="80" sortable />
                <el-table-column property="joinDate" label="加入日期" width="120" sortable />
                <el-table-column property="createTime" label="创建时间" width="120" sortable />
                <el-table-column label="状态" width="100">
                    <template #default="scope">
                        {{ scope.row.status !== 1 ? '已上架': '已下架'  }}
                    </template>
                </el-table-column>
                
                <el-table-column fixed="right" label="操作" min-width="120">
                    <template #default="scope">
                        <el-button link type="primary" size="small" @click="editClick(scope.row)" :disabled="loading">
                            编辑
                        </el-button>
                        <el-button link type="primary" size="small"
                            @click.prevent="deleteClick(scope.$index)" :disabled="loading">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        
        <!-- <div style="margin-top: 25px;">
            <el-pagination v-model:currentPage="currentPage3" v-model:page-size="pageSize3" :small="small"
                :disabled="disabled" :background="background" layout="prev, pager, next, jumper" :total="1000"
                @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div> -->
        
        <div>
            <dialog-visible v-model="deleteModul" @submit-fun="deleteClicks" :loading="loading"></dialog-visible>
        </div>
        
        <edit v-model:editModul="editModul" v-model:content="editContent" title="编辑" opear="0" @confirm="handleEditConfirm"></edit>
    </div>
</template>

<style lang="css" scoped>
:deep(.el-pagination) {
    display: flex;
    justify-content: center;
}
</style>
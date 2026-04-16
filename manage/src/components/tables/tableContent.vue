<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import dialogVisible from '@/utils/dialogVisible.vue'
import edit from '@/utils/edit.vue'
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

const deleteClick = (value: any) => {
    opearIndex.value = value
    // deleteModul.value = true
    console.log('fu', deleteModul.value)
    // 触发删除点击事件，传递当前行的id
    emit('delete-click', tableData.value[value].id)
}
const deleteClicks = () => {
    console.log(selects.value)
    if (selects.value === false) {
        deleteModul.value = false
        // 这里不再直接修改tableData，而是由父组件通过API删除后重新获取数据
    }

    else if (selects.value === true) {
        // 这里不再直接修改tableData，而是由父组件通过API删除后重新获取数据
        selects.value = false
    }

}
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
    console.log('选中的行数据:', val)
}
watch(selects, (newVal) => {
    if (newVal === false)
        return
    if (newVal === true) {
        deleteModul.value = true
    }
})
watch(search, (newVal) => {
    if (newVal === false)
        return
    if (newVal === true) {
        //调用后端返回的数据
    }
})
watch(editContent,(newVal)=>{
    console.log('666666',newVal);
    if(editModul.value===false && newVal){
        const index = tableData.value.findIndex((item: any) => item.id === newVal.id)
        if (index !== -1) {
            console.log('更新表格第', index, '行')
            // 合并更新
            tableData.value[index] = { ...tableData.value[index], ...newVal }
        }
    }
},{deep:true})
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
</script>
<template>
    <div>
        <div>
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" />
                <el-table-column property="username" label="用户名" width="120" />
                <el-table-column property="nickname" label="昵称" width="120" />

                <el-table-column property="password" label="密码" width="120" />
                <el-table-column property="role" label="角色" width="240" show-overflow-tooltip />
                <el-table-column property="status" label="状态" />
                <el-table-column label="创建时间" width="160" fixed>
                    <template #default="scope">{{ scope.row.createdAt }}</template>
                </el-table-column>
                <el-table-column fixed="right" label="Operations" min-width="120">
                    <template #default="scope">
                        <el-button link type="primary" size="small" @click="editClick(scope.row)">
                            编辑
                        </el-button>
                        <el-button link type="primary" size="small"
                            @click.prevent="deleteClick(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div style="margin-top: 25px;">
            <el-pagination v-model:currentPage="currentPage" v-model:page-size="pageSize" :small="small"
                :disabled="disabled" :background="background" layout="prev, pager, next, jumper" :total="total"
                @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div>
        <div>
            <dialog-visible v-model="deleteModul" @submit-fun="deleteClicks"></dialog-visible>
        </div>
        <edit v-model:dialogFormVisible="editModul" v-model:content="editContent" :title="editContent?.id ? '编辑' : '增加'" :opear="editContent?.id ? '1' : '0'" @confirm="(data) => emit('confirm', data)"></edit>
    </div>

</template>
<style lang="css" scoped>
:deep(.el-pagination) {
    display: flex;
    justify-content: center;
}
</style>
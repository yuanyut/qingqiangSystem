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
const deleteClick = (value: any) => {

    opearIndex.value = value
    deleteModul.value = true
    console.log('fu', deleteModul.value)
}
const deleteClicks = () => {
    console.log(selects.value)
    if (selects.value === false) {
        deleteModul.value = false
        tableData.value.splice(opearIndex.value, 1)
    }

    else if (selects.value === true) {
        multipleSelection.value.forEach((item1: any) => {
            const index = tableData.value.findIndex((item: any) => item.username === item1.username)
            console.log(`用户名 ${item1.username} 在 tableData 的索引:`, index)
            tableData.value.splice(index, 1)
        })
        selects.value = false
    }

}
const editClick = (value: any) => {
    editModul.value = true
    console.log(value)
    editContent.value = value
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
</script>
<template>
    <div>
        <div>
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" />
                <el-table-column property="username" label="用户名" width="120" />
                <el-table-column property="nickname" label="昵称" width="120" />
                <el-table-column property="phone" label="电话" width="120" />
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
            <el-pagination v-model:currentPage="currentPage3" v-model:page-size="pageSize3" :small="small"
                :disabled="disabled" :background="background" layout="prev, pager, next, jumper" :total="1000"
                @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div>
        <div>
            <dialog-visible v-model="deleteModul" @submit-fun="deleteClicks"></dialog-visible>
        </div>
        <edit v-model:dialogFormVisible="editModul" v-model:content="editContent" title="编辑" opear="0"></edit>
    </div>

</template>
<style lang="css" scoped>
:deep(.el-pagination) {
    display: flex;
    justify-content: center;
}
</style>
<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import dialogVisible from '@/utils/dialogVisible.vue'
import edit from '@/utils/edit.vue'

const tableData: any = defineModel('tableData')
const deleteModul = ref(false)
const editModul = ref(false)
const opearIndex = ref(0)
const editContent = ref()
const deleteClick = (value: any) => {

    opearIndex.value = value
    deleteModul.value = true
    console.log('fu', deleteModul.value)
}
const deleteClicks = () => {
    deleteModul.value = false
    tableData.value.splice(opearIndex.value, 1)
}
const editClick = (value: any) => {
    editModul.value = true
    console.log(value)
    editContent.value = value
}

</script>
<template>
    <div>
        <div>
            <el-table :data="tableData" style="width: 100%">
                <el-table-column type="selection" width="55" />
                <el-table-column property="username" label="用户名" width="120" />
                <el-table-column property="nickname" label="昵称" width="120" />
                <el-table-column property="phone" label="电话" width="120" />
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
        <div>
            <dialog-visible v-model="deleteModul" @submit-fun="deleteClicks"></dialog-visible>
        </div>
        <edit v-model:dialogFormVisible="editModul" v-model:content="editContent" title="编辑" opear="0"></edit>
    </div>

</template>
<style lang="css" scoped></style>
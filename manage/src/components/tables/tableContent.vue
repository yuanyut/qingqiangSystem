<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import dialogVisible from '@/utils/dialogVisible.vue'
import edit from '@/utils/edit.vue'
interface User {
    id: number | string,
    username: string,
    nickname: string,
    phone: number | string,
    role: string[],
    status: string,
    createdAt: string
}
const tableData: User[] = reactive([
    { id: '1', username: 'admin', nickname: 'Admin', phone: '13800138000', role: ['超级管理员'], status: '启用', createdAt: '2023-10-01 10:00:00' },
    { id: '2', username: 'zhangsan', nickname: 'Zhang San', phone: '13912345678', role: ['内容管理员', '超级管理员'], status: '启用', createdAt: '2023-10-02 11:30:00' },
    { id: '3', username: 'lisi', nickname: 'Li Si', phone: '13798765432', role: ['互动审核员'], status: '禁用', createdAt: '2023-10-03 14:15:00' },
    { id: '4', username: 'wangwu', nickname: 'Wang Wu', phone: '13611112222', role: ['访客'], status: '启用', createdAt: '2023-10-04 09:45:00' },
    { id: '5', username: 'zhaoliu', nickname: 'Zhao Liu', phone: '13533334444', role: ['互动审核员'], status: '启用', createdAt: '2023-10-05 16:20:00' },
])
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
    tableData.splice(opearIndex.value, 1)
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
        <edit v-model:dialogFormVisible="editModul" v-model:content="editContent"></edit>
    </div>

</template>
<style lang="css" scoped></style>
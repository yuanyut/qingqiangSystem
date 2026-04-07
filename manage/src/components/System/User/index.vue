<script setup lang="ts">
import opearHeader from '@/components/tables/opearHeader.vue';
import opear from '@/components/tables/opear.vue'
import tableContent from '@/components/tables/tableContent.vue';
import { reactive, watch, ref } from 'vue';
interface User {
    id?: number | string,
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

const editContent = ref()
const addEdit = ref()
watch(editContent, (newVal) => {
    addEdit.value = newVal
    if (newVal.username != '' && newVal.createdAt === '') {
        newVal.createdAt = new Date().toLocaleString()
        tableData.push({
            ...newVal
        })
    }
}, { deep: true })


</script>
<template>
    <div>
        <opearHeader></opearHeader>
        <opear v-model:editContent="editContent"></opear>
        <tableContent v-model:tableData="tableData"></tableContent>
    </div>
</template>
<style scoped></style>
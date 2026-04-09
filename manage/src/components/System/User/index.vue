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
    password: string,
    role: string[],
    status: string,
    createdAt: string
}
const tableData: User[] = reactive([
    { id: '1', username: 'admin', nickname: 'Admin', phone: '13800138000', password: '********', role: ['超级管理员'], status: '启用', createdAt: '2023-10-01 10:00:00' },
    { id: '2', username: 'zhangsan', nickname: 'Zhang San', phone: '13912345678', password: '********', role: ['内容管理员', '超级管理员'], status: '启用', createdAt: '2023-10-02 11:30:00' },
    { id: '3', username: 'lisi', nickname: 'Li Si', phone: '13798765432', password: '********', role: ['互动审核员'], status: '禁用', createdAt: '2023-10-03 14:15:00' },
    { id: '4', username: 'wangwu', nickname: 'Wang Wu', phone: '13611112222', password: '********', role: ['访客'], status: '启用', createdAt: '2023-10-04 09:45:00' },
    { id: '5', username: 'zhaoliu', nickname: 'Zhao Liu', phone: '13533334444', password: '********', role: ['互动审核员'], status: '启用', createdAt: '2023-10-05 16:20:00' },
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
const selects = ref(false)
const multipleSelection = ref([])


const formHeader = ref({
    name: '',
    phone: '',
    juese: '',
    status: ''
})
const search = ref(false)
watch(formHeader, (newVal) => {
    console.log('这是新的', newVal)
}, { deep: true })
interface lableItem {
    lable1: string,
    lable2: string,
    lable3: string,
    lable4: string
}
const labels = reactive<lableItem>({
    lable1: '用户名',
    lable2: '手机号',
    lable3: '角色',
    lable4: '状态'
})
</script>
<template>
    <div>
        <opearHeader v-model:form-header="formHeader" v-model:search="search" :lable="labels"></opearHeader>
        <opear v-model:editContent="editContent" v-model:multipleSelection="multipleSelection"
            v-model:selects="selects"></opear>
        <tableContent v-model:tableData="tableData" v-model:selects="selects"
            v-model:multipleSelection="multipleSelection" v-model:form-header="formHeader" v-model:search="search">
        </tableContent>
    </div>
</template>
<style scoped></style>
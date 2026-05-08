<script setup lang="ts">
import opearHeader from '@/components/tables/opearHeader.vue';
import opear from '@/components/tables/opear.vue'
import tableContent from '@/components/tables/tableContent.vue';
import { reactive, watch, ref, onMounted } from 'vue';
import { getUserList, deleteUser, addUser, updateUser, getUserById } from '@/api/api';

interface User {
    id?: number | string,
    username: string,
    nickname: string,
    password: string,
    role: string,
    status: string,
    createdAt: string,
    phone:string
}

const tableData: User[] = reactive([])
const total = ref(0)
const page = ref(1)
const size = ref(10)

const editContent = ref<User>({
    id: '',
    username: '',
    nickname: '',
    password: '',
    role: '',
    status: '启用',
    createdAt: ''
})

const addEdit = ref()

const selects = ref(false)
const multipleSelection = ref([])

const formHeader = ref({
    name: '',
    juese: '',
    status: ''
})

const search = ref(false)

interface lableItem {
    lable1: string,
    lable2: string,
    lable3: string,
    lable4: string
}

const labels = reactive<lableItem>({
    lable1: '用户名',
    lable2: '昵称',
    lable3: '角色',
    lable4: '状态'
})

// 获取用户列表
const fetchUserList = async () => {
    try {
        const response = await getUserList({
            page: page.value,
            size: size.value,
            username: formHeader.value.name,
            nickname: '',
            status: formHeader.value.status === '启用' ? 1 : formHeader.value.status === '禁用' ? 0 : undefined
        });
        if (response && response.data) {
            tableData.length = 0;
            response.data.list.forEach((item: any) => {
                tableData.push({
                    id: item.id,
                    username: item.username,
                    nickname: item.nickname || '',
                    password: item.password || '',
                    role: item.role || '',
                    status: item.status === 1 ? '启用' : '禁用',
                    createdAt: item.createTime || ''
                });
            });
            total.value = response.data.total || 0;
        }
    } catch (error) {
        console.error('获取用户列表失败:', error);
    }
}

// 搜索用户
watch([formHeader, search], () => {
    if (search.value) {
        page.value = 1;
        fetchUserList();
    }
}, { deep: true })

// 处理确认事件
const handleConfirm = async (data: User) => {
    if (data.username && data.createdAt === '') {
        // 新增用户
        try {
            await addUser({
                username: data.username,
                nickname: data.nickname,
                password: data.password,
                role: data.role,
                status: data.status === '启用' ? 1 : 0
            });
            fetchUserList();
        } catch (error) {
            console.error('新增用户失败:', error);
        }
    } else if (data.id) {
        // 编辑用户
        try {
            await updateUser(data.id, {
                username: data.username,
                nickname: data.nickname,
                password: data.password,
                role: data.role,
                status: data.status === '启用' ? 1 : 0
            });
            fetchUserList();
        } catch (error) {
            console.error('编辑用户失败:', error);
        }
    }
    // 清空密码字段，确保下次点击新增按钮时密码框为空
    editContent.value.password = '';
}

// 删除用户
const handleDelete = async (id: number | string) => {
    try {
        await deleteUser(id);
        fetchUserList();
    } catch (error) {
        console.error('删除用户失败:', error);
    }
}

// 批量删除用户
const handleBatchDelete = async () => {
    if (multipleSelection.value.length === 0) return;
    try {
        const ids = multipleSelection.value.map((item: User) => item.id).filter((id): id is string | number => id !== undefined);
        await deleteUser(ids);
        fetchUserList();
    } catch (error) {
        console.error('批量删除用户失败:', error);
    }
}

// 编辑用户
const handleEdit = async (row: User) => {
    try {
        const response = await getUserById(row.id as string | number);
        if (response && response.data) {
            editContent.value = {
                id: response.data.id,
                username: response.data.username,
                nickname: response.data.nickname || '',
                password: '',
                role: response.data.role || '',
                status: response.data.status === 1 ? '启用' : '禁用',
                createdAt: response.data.createTime || ''
            };
        }
    } catch (error) {
        console.error('获取用户详情失败:', error);
    }
}

// 编辑点击事件
const editClick = (row: User) => {
    handleEdit(row);
}

// 删除点击事件
const deleteClick = (id: number | string) => {
    handleDelete(id);
}

// 批量删除点击事件
const batchDeleteClick = () => {
    handleBatchDelete();
}

// 处理页码变化
const handlePageChange = (val: number) => {
    page.value = val;
    fetchUserList();
}

// 处理每页条数变化
const handleSizeChange = (val: number) => {
    size.value = val;
    page.value = 1;
    fetchUserList();
}

// 初始化
onMounted(() => {
    fetchUserList();
})
</script>
<template>
    <div>
        <opearHeader v-model:form-header="formHeader" v-model:search="search" :lable="labels"></opearHeader>
        <opear v-model:editContent="editContent" v-model:multipleSelection="multipleSelection"
            v-model:selects="selects" @batch-delete="batchDeleteClick" @confirm="handleConfirm"></opear>
        <tableContent v-model:tableData="tableData" v-model:selects="selects"
            v-model:multipleSelection="multipleSelection" v-model:form-header="formHeader" v-model:search="search"
            v-model:currentPage="page" v-model:pageSize="size" v-model:total="total"
            @edit-click="editClick" @delete-click="deleteClick" @confirm="handleConfirm"
            @page-change="handlePageChange" @size-change="handleSizeChange"></tableContent>
    </div>
</template>
<style scoped></style>

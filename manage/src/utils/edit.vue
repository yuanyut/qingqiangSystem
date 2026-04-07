<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'

// 修正类型定义
interface FormItemUser {
    username: string
    nickname: string
    phone: string
    role: string[]
    status: string
}

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel('content', { default: () => ({}) })

const formLabelWidth = '140px'

// 初始化 form
const form = reactive<FormItemUser>({
    username: '',
    nickname: '',
    phone: '',
    role: [],
    status: ''
})

// 监听 content 变化，更新 form（用 Object.assign 保持响应式）
watch(content, (newVal) => {
    console.log('子组件收到 content:', newVal)
    if (newVal) {
        // 用 Object.assign 更新属性，而不是直接赋值
        Object.assign(form, newVal)
        console.log('form 更新后:', form)
    }
}, { deep: true, immediate: true })

// 确定按钮：将修改后的数据传回父组件
const handleConfirm = () => {
    // 把修改后的 form 数据同步回 content
    Object.assign(content.value, form)
    dialogFormVisible.value = false
}

const handleCancel = () => {
    dialogFormVisible.value = false
}
</script>

<template>
    <el-dialog v-model="dialogFormVisible" title="编辑" width="500">
        <el-form :model="form">
            <el-form-item label="用户名" :label-width="formLabelWidth">
                <el-input v-model="form.username" autocomplete="off" />
            </el-form-item>
            <el-form-item label="昵称" :label-width="formLabelWidth">
                <el-input v-model="form.nickname" autocomplete="off" />
            </el-form-item>
            <el-form-item label="电话" :label-width="formLabelWidth">
                <el-input v-model="form.phone" autocomplete="off" />
            </el-form-item>
            <el-form-item label="角色" :label-width="formLabelWidth">
                <el-select v-model="form.role" placeholder="请选择角色" multiple>
                    <el-option label="超级管理员" value="超级管理员" />
                    <el-option label="内容管理员" value="内容管理员" />
                    <el-option label="数据分析员" value="数据分析员" />
                    <el-option label="互动审核员" value="互动审核员" />
                    <el-option label="普通用户" value="普通用户" />
                    <el-option label="访客" value="访客" />
                </el-select>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth">
                <el-select v-model="form.status" placeholder="请选择状态">
                    <el-option label="启用" value="启用" />
                    <el-option label="禁用" value="禁用" />
                    <el-option label="待审核" value="待审核" />
                    <el-option label="已注销" value="已注销" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="handleCancel">取消</el-button>
                <el-button type="primary" @click="handleConfirm">确定</el-button>
            </div>
        </template>
    </el-dialog>
</template>
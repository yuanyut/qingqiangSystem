<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormInstance } from 'element-plus'

const formRef = ref<FormInstance>()
// 修正类型定义
interface FormItemUser {
    username: string
    nickname: string
    password: string
    role: string
    status: string
    createdAt?: string
}

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel('content', { default: () => ({}) })
const selects = defineModel('selects')
const formLabelWidth = '140px'

// 初始化 form
const form = reactive<FormItemUser>({
    createdAt: '',
    username: '',
    nickname: '',
    password: '',
    role: '',
    status: ''
})
const props = defineProps<{ title: string, opear: string }>()
// 监听 content 变化，更新 form（用 Object.assign 保持响应式）
watch(content, (newVal) => {
    console.log('子组件收到 content:', newVal)
    if (newVal) {
        // 用 Object.assign 更新属性，而不是直接赋值
        Object.assign(form, newVal)
        console.log('form 更新后:', form)
    }
}, { deep: true, immediate: true })

// 监听 dialogFormVisible 变化，当对话框打开时，确保密码字段被清空
watch(dialogFormVisible, (newVal) => {
    if (newVal && props.opear === '0') {
        // 新增模式，清空密码字段
        form.password = ''
    }
})

// 定义emit事件
const emit = defineEmits(['confirm'])

// 确定按钮：将修改后的数据传回父组件
const handleConfirm = () => {
    if (props.opear === '0') {
        // 新增：直接赋值
        content.value = { ...form }
    } else {
        // 编辑：修改属性
        Object.assign(content.value, form)

    }
    // 触发confirm事件，通知父组件执行相应的逻辑
    emit('confirm', { ...form })
    form.createdAt = '',
        form.username = '',
        form.nickname = '',
        form.password = '',
        form.role = '',
        form.status = ''
    dialogFormVisible.value = false
}

const handleCancel = (formEl: FormInstance | undefined) => {
    dialogFormVisible.value = false
    if (!formEl) return
    formEl.resetFields()

}
const rules = reactive({
    username: [
        { required: true, message: '输入用户名', trigger: 'blur' }
    ],
    nickname: [
        { required: true, message: '输入昵称', trigger: 'blur' }
    ],

    password: [
        { required: true, message: '输入密码', trigger: 'blur' },
        { min: 6, message: '密码长度至少6位', trigger: 'blur' }
    ],
    role: [
        { required: true, message: '请选择角色', trigger: 'change' }
    ],
    status: [
        { required: true, message: '请选择状态', trigger: 'change' }
    ]
})
</script>

<template>
    <el-dialog v-model="dialogFormVisible" :show-close="false" :title="props.title" width="500">
        <el-form :model="form" ref="formRef" :rules="rules">
            <el-form-item v-if="form.createdAt" label="创建时间" :label-width="formLabelWidth" prop="createdAt">
                <el-input v-model="form.createdAt" autocomplete="off" disabled />
            </el-form-item>
            <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
                <el-input v-model="form.username" autocomplete="off" />
            </el-form-item>
            <el-form-item label="昵称" :label-width="formLabelWidth" prop="nickname">
                <el-input v-model="form.nickname" autocomplete="off" />
            </el-form-item>

            <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
                <el-input v-model="form.password" type="password" show-password autocomplete="off"
                    placeholder="请输入密码" />
            </el-form-item>
            <el-form-item label="角色" :label-width="formLabelWidth" prop="role">
                <el-select v-model="form.role" placeholder="请选择角色" >
                    <el-option label="管理员" value="admin" />
                    <el-option label="数据分析员" value="viewer" />
                    <el-option label="内容管理员" value="editor" />
                    <!-- <el-option label="内容管理员" value="内容管理员" />
                    <el-option label="数据分析员" value="数据分析员" />
                    <el-option label="互动审核员" value="互动审核员" /> -->
                    <el-option label="普通用户" value="user" />
                </el-select>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth" prop="status">
                <el-select v-model="form.status" placeholder="请选择状态">
                    <el-option label="启用" value="启用" />
                    <el-option label="禁用" value="禁用" />
                    <!-- <el-option label="待审核" value="待审核" /> -->
                    <el-option label="已注销" value="已注销" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="handleCancel(formRef)">取消</el-button>
                <el-button type="primary" @click="handleConfirm">确定</el-button>
            </div>
        </template>
    </el-dialog>
</template>
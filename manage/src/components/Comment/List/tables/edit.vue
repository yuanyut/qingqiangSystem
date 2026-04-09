<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'

const formRef = ref<FormInstance>()

// 修正类型定义 - 适配评论数据格式
interface FormItemComment {
    id?: number
    content: string
    user: string
    targetType: string
    targetId: number
    targetTitle: string
    publishTime: string
    statusText: string
    replyCount: number
    likeCount: number
}

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel('content', { default: () => ({}) })
const selects = defineModel('selects')
const formLabelWidth = '140px'

// 初始化 form - 适配评论字段
const form = reactive<FormItemComment>({
    id: undefined,
    content: '',
    user: '',
    targetType: '',
    targetId: 0,
    targetTitle: '',
    publishTime: '',
    statusText: '',
    replyCount: 0,
    likeCount: 0
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

// 确定按钮：将修改后的数据传回父组件
const handleConfirm = async () => {
    // 验证必填字段
    if (!form.content) {
        ElMessage.error('请填写评论内容')
        return
    }
    if (!form.user) {
        ElMessage.error('请填写用户名称')
        return
    }
    if (!form.targetType) {
        ElMessage.error('请选择目标类型')
        return
    }
    if (!form.statusText) {
        ElMessage.error('请选择状态')
        return
    }

    if (props.opear === '0') {
        // 新增模式：直接赋值
        content.value = { ...form }
        // 重置表单
        resetForm()
    } else {
        // 编辑模式：修改属性
        Object.assign(content.value, form)
    }
    dialogFormVisible.value = false
}

// 重置表单
const resetForm = () => {
    form.id = undefined
    form.content = ''
    form.user = ''
    form.targetType = ''
    form.targetId = 0
    form.targetTitle = ''
    form.publishTime = ''
    form.statusText = ''
    form.replyCount = 0
    form.likeCount = 0
}

const handleCancel = (formEl: FormInstance | undefined) => {
    dialogFormVisible.value = false
    if (!formEl) return
    formEl.resetFields()
    resetForm()
}

// 规则验证
const rules = {
    content: [
        { required: true, message: '请输入评论内容', trigger: 'blur' },
        { min: 1, max: 1000, message: '长度在 1 到 1000 个字符', trigger: 'blur' }
    ],
    user: [
        { required: true, message: '请输入用户名称', trigger: 'blur' },
        { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
    ],
    targetType: [
        { required: true, message: '请选择目标类型', trigger: 'change' }
    ],
    statusText: [
        { required: true, message: '请选择状态', trigger: 'change' }
    ]
}
</script>

<template>
    <el-dialog v-model="dialogFormVisible" :show-close="false" :title="props.title" width="700">
        <el-form :model="form" ref="formRef" :rules="rules" label-position="right">

            <!-- 发布时间（仅编辑时显示） -->
            <el-form-item v-if="form.publishTime && props.opear !== '0'" label="发布时间" :label-width="formLabelWidth">
                <el-input v-model="form.publishTime" disabled />
            </el-form-item>

            <!-- 评论内容 -->
            <el-form-item label="评论内容" :label-width="formLabelWidth" prop="content">
                <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入评论内容" />
            </el-form-item>

            <!-- 用户 -->
            <el-form-item label="用户" :label-width="formLabelWidth" prop="user">
                <el-input v-model="form.user" placeholder="请输入用户名称" />
            </el-form-item>

            <!-- 目标类型 -->
            <el-form-item label="目标类型" :label-width="formLabelWidth" prop="targetType">
                <el-select v-model="form.targetType" placeholder="请选择目标类型" style="width: 100%">
                    <el-option label="视频" value="视频" />
                    <el-option label="资讯" value="资讯" />
                    <el-option label="名家" value="名家" />
                </el-select>
            </el-form-item>

            <!-- 目标ID -->
            <el-form-item label="目标ID" :label-width="formLabelWidth">
                <el-input v-model.number="form.targetId" type="number" placeholder="请输入目标ID" />
            </el-form-item>

            <!-- 目标标题 -->
            <el-form-item label="目标标题" :label-width="formLabelWidth">
                <el-input v-model="form.targetTitle" placeholder="请输入目标标题" />
            </el-form-item>

            <!-- 状态 -->
            <el-form-item label="状态" :label-width="formLabelWidth" prop="statusText">
                <el-select v-model="form.statusText" placeholder="请选择状态" style="width: 100%">
                    <el-option label="待审核" value="待审核" />
                    <el-option label="已审核" value="已审核" />
                    <el-option label="已删除" value="已删除" />
                </el-select>
            </el-form-item>

            <!-- 统计信息（仅编辑时显示） -->
            <template v-if="props.opear !== '0'">
                <el-form-item label="回复数" :label-width="formLabelWidth">
                    <el-input v-model="form.replyCount" disabled />
                </el-form-item>
                <el-form-item label="点赞数" :label-width="formLabelWidth">
                    <el-input v-model="form.likeCount" disabled />
                </el-form-item>
            </template>
        </el-form>

        <template #footer>
            <div class="dialog-footer">
                <el-button @click="handleCancel(formRef)">取消</el-button>
                <el-button type="primary" @click="handleConfirm">确定</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<style scoped>
:deep(.el-dialog__body) {
    padding-top: 20px;
    max-height: 70vh;
    overflow-y: auto;
}

:deep(.el-form-item__label) {
    font-weight: 500;
}

:deep(.el-textarea__inner) {
    font-family: inherit;
}

.status-disabled {
    color: #909399;
}

.status-pending {
    color: #e6a23c;
}

.status-deleted {
    color: #f56c6c;
}
</style>
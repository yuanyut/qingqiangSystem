<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'

const formRef = ref<FormInstance>()

// 修正类型定义 - 适配资讯数据格式
interface FormItemNews {
    id?: number
    title: string
    content: string
    category: string
    author: string
    publishTime: string
    clickCount: number
    likeCount: number
    statusText: string
}

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel('content', { default: () => ({}) })
const selects = defineModel('selects')
const formLabelWidth = '140px'

// 初始化 form - 适配资讯字段
const form = reactive<FormItemNews>({
    id: undefined,
    title: '',
    content: '',
    category: '',
    author: '',
    publishTime: '',
    clickCount: 0,
    likeCount: 0,
    statusText: ''
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
    if (!form.title) {
        ElMessage.error('请填写资讯标题')
        return
    }
    if (!form.content) {
        ElMessage.error('请填写资讯内容')
        return
    }
    if (!form.category) {
        ElMessage.error('请选择资讯分类')
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
    form.title = ''
    form.content = ''
    form.category = ''
    form.author = ''
    form.publishTime = ''
    form.clickCount = 0
    form.likeCount = 0
    form.statusText = ''
}

const handleCancel = (formEl: FormInstance | undefined) => {
    dialogFormVisible.value = false
    if (!formEl) return
    formEl.resetFields()
    resetForm()
}

// 规则验证
const rules = {
    title: [
        { required: true, message: '请输入资讯标题', trigger: 'blur' },
        { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
    ],
    content: [
        { required: true, message: '请输入资讯内容', trigger: 'blur' },
        { min: 1, max: 5000, message: '长度在 1 到 5000 个字符', trigger: 'blur' }
    ],
    category: [
        { required: true, message: '请选择资讯分类', trigger: 'change' }
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

            <!-- 资讯标题 -->
            <el-form-item label="资讯标题" :label-width="formLabelWidth" prop="title">
                <el-input v-model="form.title" placeholder="请输入资讯标题" />
            </el-form-item>

            <!-- 资讯内容 -->
            <el-form-item label="资讯内容" :label-width="formLabelWidth" prop="content">
                <el-input v-model="form.content" type="textarea" :rows="6" placeholder="请输入资讯内容" />
            </el-form-item>

            <!-- 分类 -->
            <el-form-item label="分类" :label-width="formLabelWidth" prop="category">
                <el-select v-model="form.category" placeholder="请选择分类" style="width: 100%">
                    <el-option label="名家动态" value="名家动态" />
                    <el-option label="活动通知" value="活动通知" />
                    <el-option label="非遗动态" value="非遗动态" />
                    <el-option label="赛事新闻" value="赛事新闻" />
                    <el-option label="文化动态" value="文化动态" />
                </el-select>
            </el-form-item>

            <!-- 作者 -->
            <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
                <el-input v-model="form.author" placeholder="请输入作者" />
            </el-form-item>

            <!-- 状态 -->
            <el-form-item label="状态" :label-width="formLabelWidth" prop="statusText">
                <el-select v-model="form.statusText" placeholder="请选择状态" style="width: 100%">
                    <el-option label="草稿" value="草稿" />
                    <el-option label="已发布" value="已发布" />
                    <el-option label="已下架" value="已下架" />
                </el-select>
            </el-form-item>

            <!-- 统计信息（仅编辑时显示） -->
            <template v-if="props.opear !== '0'">
                <el-form-item label="点击量" :label-width="formLabelWidth">
                    <el-input v-model="form.clickCount" disabled />
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
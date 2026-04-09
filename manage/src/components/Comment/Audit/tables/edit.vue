<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'

const formRef = ref<FormInstance>()

// 修正类型定义 - 适配评论审核数据格式
interface FormItemCommentAudit {
    id?: number
    content: string
    user: string
    userType: string
    targetType: string
    targetTitle: string
    publishTime: string
    statusText: string
    replyCount: number
    likeCount: number
    rejectReason?: string
}

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel('content', { default: () => ({}) })
const selects = defineModel('selects')
const formLabelWidth = '140px'

// 初始化 form - 适配评论审核字段
const form = reactive<FormItemCommentAudit>({
    id: undefined,
    content: '',
    user: '',
    userType: '',
    targetType: '',
    targetTitle: '',
    publishTime: '',
    statusText: '',
    replyCount: 0,
    likeCount: 0,
    rejectReason: ''
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

// 审核操作
const handlePass = () => {
    form.statusText = '已审核'
    content.value = { ...form }
    dialogFormVisible.value = false
    ElMessage.success('审核通过')
}

const handleReject = () => {
    if (!form.rejectReason) {
        ElMessage.error('请填写驳回原因')
        return
    }
    form.statusText = '已驳回'
    content.value = { ...form }
    dialogFormVisible.value = false
    ElMessage.success('审核驳回')
}

const handleDelete = () => {
    form.statusText = '已删除'
    content.value = { ...form }
    dialogFormVisible.value = false
    ElMessage.success('评论已删除')
}

const handleCancel = () => {
    dialogFormVisible.value = false
}
</script>

<template>
    <el-dialog v-model="dialogFormVisible" :show-close="false" :title="props.title" width="600">
        <div class="audit-container">
            <!-- 面包屑 -->
            <div class="breadcrumb">
                评论审核 / 审核操作
            </div>

            <!-- 评论信息 -->
            <div class="comment-info">
                <div class="info-item">
                    <span class="label">评论内容：</span>
                    <span class="value">『{{ form.content }}』</span>
                </div>
                <div class="info-item">
                    <span class="label">评论用户：</span>
                    <span class="value">{{ form.user }}（{{ form.userType }}）</span>
                </div>
                <div class="info-item">
                    <span class="label">评论时间：</span>
                    <span class="value">{{ form.publishTime }}</span>
                </div>
                <div class="info-item">
                    <span class="label">评论对象：</span>
                    <span class="value">{{ form.targetTitle }}</span>
                </div>
                <div class="info-item">
                    <span class="label">状态：</span>
                    <span class="value status-pending">{{ form.statusText }}</span>
                </div>
            </div>

            <!-- 审核操作 -->
            <div class="audit-actions">
                <div class="action-title">审核操作：</div>
                <div class="action-buttons">
                    <el-button type="success" @click="handlePass">通过</el-button>
                    <el-button type="warning" @click="dialogFormVisible = true; form.rejectReason = ''">驳回</el-button>
                    <el-button type="danger" @click="handleDelete">删除</el-button>
                </div>
            </div>

            <!-- 驳回原因（仅点击驳回时显示） -->
            <div class="reject-reason" v-if="form.rejectReason !== undefined">
                <div class="reason-title">驳回原因：</div>
                <el-select v-model="form.rejectReason" placeholder="请选择驳回原因" style="width: 100%">
                    <el-option label="违反社区规范" value="违反社区规范" />
                    <el-option label="包含敏感内容" value="包含敏感内容" />
                    <el-option label="垃圾广告" value="垃圾广告" />
                    <el-option label="其他原因" value="其他原因" />
                </el-select>
                <div class="reason-buttons">
                    <el-button @click="form.rejectReason = undefined">取消</el-button>
                    <el-button type="primary" @click="handleReject">提交</el-button>
                </div>
            </div>
        </div>

        <template #footer>
            <div class="dialog-footer">
                <el-button @click="handleCancel">关闭</el-button>
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

.audit-container {
    padding: 20px;
}

.breadcrumb {
    font-size: 14px;
    color: #606266;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #e4e7ed;
}

.comment-info {
    background: #f5f7fa;
    padding: 20px;
    border-radius: 8px;
    margin-bottom: 20px;
}

.info-item {
    margin-bottom: 12px;
    display: flex;
    align-items: flex-start;
}

.label {
    font-weight: 500;
    width: 100px;
    flex-shrink: 0;
}

.value {
    flex: 1;
    word-break: break-word;
}

.status-pending {
    color: #e6a23c;
    font-weight: 500;
}

.audit-actions {
    margin-bottom: 20px;
}

.action-title {
    font-weight: 500;
    margin-bottom: 10px;
}

.action-buttons {
    display: flex;
    gap: 10px;
}

.reject-reason {
    background: #fef0f0;
    padding: 20px;
    border-radius: 8px;
    border-left: 4px solid #f56c6c;
    margin-top: 20px;
}

.reason-title {
    font-weight: 500;
    margin-bottom: 10px;
}

.reason-buttons {
    margin-top: 15px;
    display: flex;
    gap: 10px;
    justify-content: flex-end;
}

.dialog-footer {
    text-align: center;
}
</style>
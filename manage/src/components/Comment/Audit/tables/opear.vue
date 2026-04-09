<template>
    <div class="action-bar">
        <div class="action-container">
            <div class="action-buttons">
                <el-button type="success" @click="batchPass" class="action-btn pass-btn" :icon="Check"
                    :disabled="multipleSelection?.length === 0">
                    批量通过
                </el-button>

                <el-button type="warning" @click="batchReject" class="action-btn reject-btn" :icon="InfoFilled"
                    :disabled="multipleSelection?.length === 0">
                    批量驳回
                </el-button>

                <el-button type="danger" @click="batchDelete" class="action-btn delete-btn" :icon="Delete"
                    :disabled="multipleSelection?.length === 0">
                    批量删除
                </el-button>
            </div>

            <div class="action-stats" v-if="multipleSelection?.length > 0">
                <el-tag type="info" effect="plain" size="large">
                    <el-icon>
                        <Check />
                    </el-icon>
                    已选择 {{ multipleSelection.length }} 项
                </el-tag>
            </div>
        </div>

        <edit v-model:dialogFormVisible="editModul" v-model:selects="selects" v-model:content="editContent" title="评论审核"
            opear="0" />
    </div>
</template>

<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import { Check, Delete, InfoFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import edit from '@/components/Comment/Audit/tables/edit.vue'

interface FormItemCommentAudit {
    id: number
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

const editContent = defineModel<any>('editContent')
const selects = defineModel('selects')
const multipleSelection: any = defineModel('multipleSelection')

console.log('666666', editContent.value)

const editModul = ref(false)

// 批量通过
const batchPass = () => {
    if (multipleSelection.value.length === 0) {
        ElMessage.warning('请至少选择一条评论')
        return
    }
    ElMessage.success(`已批量通过 ${multipleSelection.value.length} 条评论`)
    selects.value = false
    multipleSelection.value = []
}

// 批量驳回
const batchReject = () => {
    if (multipleSelection.value.length === 0) {
        ElMessage.warning('请至少选择一条评论')
        return
    }
    ElMessage.success(`已批量驳回 ${multipleSelection.value.length} 条评论`)
    selects.value = false
    multipleSelection.value = []
}

// 批量删除
const batchDelete = () => {
    if (multipleSelection.value.length === 0) {
        ElMessage.warning('请至少选择一条评论')
        return
    }
    ElMessage.success(`已批量删除 ${multipleSelection.value.length} 条评论`)
    selects.value = false
    multipleSelection.value = []
}

watch(editContent, (newVal) => {
    console.log('content 变化:', newVal)
}, { deep: true, immediate: true })

console.log('初始化时 content.value:', editContent.value)
</script>

<style scoped>
.action-bar {
    background: linear-gradient(135deg, #f5f7fa 0%, #ffffff 100%);
    border-radius: 16px;
    padding: 20px 24px;
    margin-bottom: 24px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
    border: 1px solid rgba(102, 126, 234, 0.1);
}

.action-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 16px;
}

.action-buttons {
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
}

.action-btn {
    border-radius: 12px;
    padding: 10px 20px;
    font-weight: 500;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    display: inline-flex;
    align-items: center;
    gap: 8px;
}

.pass-btn {
    background: linear-gradient(135deg, #67c23a 0%, #85ce61 100%);
    border: none;
    box-shadow: 0 4px 12px rgba(103, 194, 58, 0.25);
}

.pass-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(103, 194, 58, 0.35);
}

.reject-btn {
    background: linear-gradient(135deg, #e6a23c 0%, #f39c12 100%);
    border: none;
    box-shadow: 0 4px 12px rgba(230, 162, 60, 0.25);
}

.reject-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(230, 162, 60, 0.35);
}

.delete-btn {
    background: linear-gradient(135deg, #f56c6c 0%, #f093fb 100%);
    border: none;
    box-shadow: 0 4px 12px rgba(245, 108, 108, 0.25);
}

.delete-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(245, 108, 108, 0.35);
}

.action-stats {
    display: flex;
    align-items: center;
    animation: fadeIn 0.3s ease-out;
}

:deep(.el-tag) {
    border-radius: 20px;
    padding: 8px 16px;
    font-size: 14px;
    background: linear-gradient(135deg, #f0f9ff 0%, #e6f7ff 100%);
    border-color: #91d5ff;
    color: #1890ff;
}

:deep(.el-tag .el-icon) {
    margin-right: 6px;
    font-size: 16px;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: scale(0.95);
    }

    to {
        opacity: 1;
        transform: scale(1);
    }
}

/* 响应式设计 */
@media (max-width: 768px) {
    .action-bar {
        padding: 16px;
        margin-bottom: 16px;
    }

    .action-container {
        flex-direction: column;
        align-items: stretch;
    }

    .action-buttons {
        justify-content: center;
    }

    .action-btn {
        flex: 1;
        justify-content: center;
        padding: 8px 16px;
    }

    .action-stats {
        justify-content: center;
    }
}

/* 暗色模式支持 */
@media (prefers-color-scheme: dark) {
    .action-bar {
        background: linear-gradient(135deg, #1f2937 0%, #111827 100%);
        border-color: rgba(102, 126, 234, 0.2);
    }

    :deep(.el-tag) {
        background: linear-gradient(135deg, #1f3a5f 0%, #1a2c3e 100%);
        border-color: #2c4c7a;
        color: #7aa3c9;
    }
}
</style>
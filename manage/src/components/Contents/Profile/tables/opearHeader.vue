<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance } from 'element-plus'
import {
    Search,
    Document,
    Star,
    Edit,
    DataAnalysis,
    ChatDotRound,
    View,
    CircleCheck,
    CircleClose,
    Clock,
    Delete,
    Refresh,
    Phone
} from '@element-plus/icons-vue'

interface FormHeaderItem {
    title: string
    category: string
    status: string
}

const formRef = ref<FormInstance>()
const search = defineModel('search')
const safeForm = defineModel<FormHeaderItem>('formHeader')
interface lableItem {
    lable1?: string,
    lable2?: string,
    lable3?: string,
    lable4?: string,
    lable5?: string,
    lable6?: string,
    lable7?: string,
}
const props = defineProps<{
    lable: lableItem
}>()
const form = reactive<FormHeaderItem>({
    title: '',
    category: '',
    status: ''
})

const onSubmit = () => {
    safeForm.value = { ...form }
    if (safeForm.value) {
        console.log('提交的表单数据:', safeForm.value)
    }
    search.value = true
}

const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}
</script>
<template>
    <div class="search-card">
        <el-form v-if="form" :model="form" label-width="80px" ref="formRef" class="search-form">
            <!-- 资讯搜索 -->
            <div class="form-row">
                <el-form-item :label="props.lable.lable1" prop="title" v-if="props.lable.lable1">
                    <el-input v-model="form.title" placeholder="请输入资讯标题" clearable prefix-icon="Document" />
                </el-form-item>

                
                <el-form-item :label="props.lable.lable3" prop="status" v-if="props.lable.lable3">
                    <el-select v-model="form.status" placeholder="请选择状态" clearable class="full-width">
                        <el-option label="全部" value="">
                            <div class="option-item">
                                <el-icon>
                                    <CircleCheck />
                                </el-icon>
                                <span class="status-active">全部</span>
                            </div>
                        </el-option>
                        <el-option label="已上架" value="已上架">
                            <div class="option-item">
                                <el-icon>
                                    <CircleCheck />
                                </el-icon>
                                <span class="status-active">已上架</span>
                            </div>
                        </el-option>
                        <el-option label="已下架" value="已下架">
                            <div class="option-item">
                                <el-icon>
                                    <Delete />
                                </el-icon>
                                <span class="status-deleted">已下架</span>
                            </div>
                        </el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="form-actions">
                <el-button @click="resetForm(formRef)" class="reset-btn">
                    <el-icon>
                        <Refresh />
                    </el-icon>
                    重置
                </el-button>
                <el-button type="primary" @click="onSubmit" class="search-btn">
                    <el-icon>
                        <Search />
                    </el-icon>
                    查询
                </el-button>
            </div>
        </el-form>
    </div>
</template>
<style scoped>
.search-card {
    border-radius: 20px;
    padding: 24px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
    margin: 20px;
    transition: all 0.3s ease;
}

.search-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 15px 50px rgba(0, 0, 0, 0.15);
}

.card-header {
    margin-bottom: 24px;
    text-align: center;
}

.header-title {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 12px;
    font-size: 28px;
    font-weight: 700;
    color: white;
    margin-bottom: 8px;
}

.title-icon {
    font-size: 28px;
    animation: pulse 2s infinite;
}

.header-subtitle {
    color: rgba(255, 255, 255, 0.8);
    font-size: 14px;
    letter-spacing: 0.5px;
}

.search-form {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 16px;
    padding: 24px;
    backdrop-filter: blur(10px);
}

.form-row {
    display: flex;
    gap: 20px;
    margin-bottom: 20px;
    flex-wrap: wrap;
}

.form-row :deep(.el-form-item) {
    flex: 1;
    margin-bottom: 0;
}

.form-row :deep(.el-form-item__label) {
    font-weight: 500;
    color: #2c3e50;
}

.full-width {
    width: 100%;
}

:deep(.el-input__wrapper) {
    border-radius: 12px;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

:deep(.el-input__wrapper:hover) {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.15);
}

:deep(.el-input__wrapper.is-focus) {
    box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
    border-color: #667eea;
}

:deep(.el-select .el-input__wrapper) {
    border-radius: 12px;
}

.option-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.status-active {
    color: #67c23a;
    font-weight: 500;
}

.status-disabled {
    color: #f56c6c;
    font-weight: 500;
}

.status-pending {
    color: #e6a23c;
    font-weight: 500;
}

.status-deleted {
    color: #909399;
    font-weight: 500;
}

.form-actions {
    display: flex;
    justify-content: flex-end;
    gap: 16px;
    margin-top: 24px;
    padding-top: 16px;
    border-top: 1px solid #e4e7ed;
}

.reset-btn,
.search-btn {
    border-radius: 12px;
    padding: 10px 24px;
    font-weight: 500;
    transition: all 0.3s ease;
}

.reset-btn {
    background: white;
    border: 1px solid #dcdfe6;
}

.reset-btn:hover {
    background: #f5f7fa;
    transform: translateY(-1px);
}

.search-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
}

.search-btn:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

@keyframes pulse {

    0%,
    100% {
        opacity: 1;
    }

    50% {
        opacity: 0.7;
    }
}

/* 响应式设计 */
@media (max-width: 768px) {
    .search-card {
        margin: 12px;
        padding: 16px;
    }

    .form-row {
        flex-direction: column;
        gap: 16px;
    }

    .header-title {
        font-size: 24px;
    }

    .title-icon {
        font-size: 24px;
    }

    .form-actions {
        justify-content: center;
    }

    .reset-btn,
    .search-btn {
        flex: 1;
    }
}

/* 暗色模式支持 */
@media (prefers-color-scheme: dark) {
    .search-form {
        background: rgba(30, 30, 40, 0.95);
    }

    .form-row :deep(.el-form-item__label) {
        color: #e5e7eb;
    }

    .form-actions {
        border-top-color: #374151;
    }
}
</style>
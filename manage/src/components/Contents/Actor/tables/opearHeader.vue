<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance } from 'element-plus'
import {
    Search,
    Refresh,
    User,
    Delete,
    CircleCheck,
    CircleClose
} from '@element-plus/icons-vue'

interface FormHeaderItem {
    title: string
    style: string
    status: string
}

const formRef = ref<FormInstance>()
const search = defineModel('search')
const safeForm = defineModel<FormHeaderItem>('formHeader')

interface lableItem {
    lable1?: string,  // 姓名
    lable2?: string,  // 风格
    lable3?: string,  // 状态
}

const props = defineProps<{
    lable: lableItem
}>()

const form = reactive<FormHeaderItem>({
    title: '',
    style: '',
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
    form.title = ''
    form.style = ''
    form.status = ''
    safeForm.value = { title: '', style: '', status: '' }
    search.value = true
}
</script>

<template>
    <div class="search-card">
        <el-form :model="form" label-width="80px" ref="formRef" class="search-form">
            <div class="form-row">
                <!-- 姓名搜索 -->
                <el-form-item :label="props.lable.lable1 || '姓名'" prop="title">
                    <el-input 
                        v-model="form.title" 
                        :placeholder="`请输入${props.lable.lable1 || '姓名'}`" 
                        clearable 
                        :prefix-icon="User"
                    />
                </el-form-item>

                <!-- 风格筛选 -->
                <el-form-item :label="props.lable.lable2 || '风格'" prop="style" v-if="props.lable.lable2">
                    <el-select v-model="form.style" placeholder="请选择风格" clearable class="full-width">
                        <el-option label="全部风格" value="">
                            <div class="option-item">
                                <span>全部风格</span>
                            </div>
                        </el-option>
                        <el-option label="传统派" value="传统派">
                            <div class="option-item">
                                <span>传统派</span>
                            </div>
                        </el-option>
                        <el-option label="创新派" value="创新派">
                            <div class="option-item">
                                <span>创新派</span>
                            </div>
                        </el-option>
                    </el-select>
                </el-form-item>

                <!-- 状态筛选 -->
                <el-form-item :label="props.lable.lable3 || '状态'" prop="status" v-if="props.lable.lable3">
                    <el-select v-model="form.status" placeholder="请选择状态" clearable class="full-width">
                        <el-option label="全部" value="">
                            <div class="option-item">
                                <el-icon><CircleCheck /></el-icon>
                                <span>全部</span>
                            </div>
                        </el-option>
                        <el-option label="已上架" value="已上架">
                            <div class="option-item">
                                <el-icon><CircleCheck /></el-icon>
                                <span class="status-active">已上架</span>
                            </div>
                        </el-option>
                        <el-option label="已下架" value="已下架">
                            <div class="option-item">
                                <el-icon><Delete /></el-icon>
                                <span class="status-deleted">已下架</span>
                            </div>
                        </el-option>
                    </el-select>
                </el-form-item>
            </div>

            <div class="form-actions">
                <el-button @click="resetForm(formRef)" class="reset-btn">
                    <el-icon><Refresh /></el-icon>
                    重置
                </el-button>
                <el-button type="primary" @click="onSubmit" class="search-btn">
                    <el-icon><Search /></el-icon>
                    查询
                </el-button>
            </div>
        </el-form>
    </div>
</template>

<style scoped>
.search-card {
    border-radius: 20px;
    margin-bottom: 20px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
}

.search-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 15px 50px rgba(0, 0, 0, 0.15);
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
    min-width: 200px;
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

    .form-actions {
        justify-content: center;
    }

    .reset-btn,
    .search-btn {
        flex: 1;
    }
}
</style>
<template>
    <div class="action-bar">
        <div class="action-container">
            <div class="action-buttons">
                <el-button type="primary" @click="addContent" class="action-btn add-btn" :icon="Plus">
                    新增
                </el-button>

                <el-button v-if="multipleSelection?.length > 1" type="danger" @click="deleteContent"
                    class="action-btn delete-btn" :icon="Delete">
                    批量删除 ({{ multipleSelection.length }})
                </el-button>

                <el-button v-else-if="multipleSelection?.length === 1" type="warning" class="action-btn info-btn"
                    :icon="InfoFilled" disabled>
                    已选中 {{ multipleSelection.length }} 项
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

        <add v-model:dialogFormVisible="addModul" v-model:content="addContentData" title="增加名家" @confirm="handleAddConfirm" />
    </div>
</template>

<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import { Plus, Delete, InfoFilled, Check } from '@element-plus/icons-vue'
import add from '@/components/Contents/Actor/tables/add.vue'

const selects = defineModel('selects')
const multipleSelection: any = defineModel('multipleSelection')
const editContent = defineModel('editContent')

const addModul = ref(false)
const addContentData = ref<{ id?: number; name: string; avatar: string; intro: string; roleName: string; style: string; joinDate: string; status: number }>({
    name: '',
    avatar: '',
    intro: '',
    roleName: '',
    style: '',
    joinDate: '',
    status: 1
})

const addContent = () => {
    console.log('addContent 被调用')
    addContentData.value = {
        name: '',
        avatar: '',
        intro: '',
        roleName: '',
        style: '',
        joinDate: '',
        status: 1
    }
    addModul.value = true
}

const deleteContent = () => {
    selects.value = true
}

const handleAddConfirm = (data: any) => {
    // 传递新增数据给父组件
    editContent.value = data
}
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

.add-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.25);
}

.add-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(102, 126, 234, 0.35);
}

.delete-btn {
    background: linear-gradient(135deg, #f56c6c 0%, #f093fb 100%);
    border: none;
    box-shadow: 0 4px 12px rgba(245, 108, 108, 0.25);
    animation: slideInRight 0.3s ease-out;
}

.delete-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(245, 108, 108, 0.35);
}

.info-btn {
    background: linear-gradient(135deg, #e6a23c 0%, #f39c12 100%);
    border: none;
    opacity: 0.7;
    cursor: not-allowed;
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

@keyframes slideInRight {
    from {
        opacity: 0;
        transform: translateX(20px);
    }

    to {
        opacity: 1;
        transform: translateX(0);
    }
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
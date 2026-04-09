<template>
    <div class="recommend-config">


        <el-card class="config-card">
            <el-form :model="form" label-width="120px" class="config-form">
                <!-- 推荐策略 -->
                <el-form-item label="算法" prop="collaborativeFilteringAlgorithm">
                    <el-select v-model="form.collaborativeFilteringAlgorithm" placeholder="请选择算法" class="full-width">
                        <el-option label="协同过滤" value="协同过滤" />
                    </el-select>
                </el-form-item>

                <!-- 推荐数量 -->
                <el-form-item label="推荐数量" prop="recommendCount">
                    <el-input-number v-model="form.recommendCount" :min="1" :max="100" :step="1" placeholder="请输入推荐数量"
                        class="full-width" />
                </el-form-item>

                <!-- 更新频率 -->
                <el-form-item label="更新频率" prop="updateFrequency">
                    <el-select v-model="form.updateFrequency" placeholder="请选择更新频率" class="full-width">
                        <el-option label="每小时" value="hourly" />
                        <el-option label="每天" value="daily" />
                        <el-option label="每周" value="weekly" />
                        <el-option label="每月" value="monthly" />
                    </el-select>
                </el-form-item>

                <!-- 排序策略权重 -->
                <el-form-item label="排序策略权重">
                    <el-collapse v-model="activeNames">
                        <el-collapse-item title="权重配置" name="1">
                            <div class="weight-config">
                                <div>
                                    <span>内容相似度权重：{{ form.contentSimilarityWeight / 100 }}</span>
                                    <el-slider v-model="form.contentSimilarityWeight" :format-tooltip="formatTooltip" />
                                </div>
                                <div>
                                    <span>用户偏好权重：{{ form.userPreferenceWeight / 100 }}</span>
                                    <el-slider v-model="form.userPreferenceWeight" :format-tooltip="formatTooltip" />
                                </div>
                                <div>
                                    <span>流行度权重：{{ form.popularityWeight / 100 }}</span>
                                    <el-slider v-model="form.popularityWeight" :format-tooltip="formatTooltip" />
                                </div>
                                <div>
                                    <span>新鲜度权重：{{ form.freshnessWeight / 100 }}</span>
                                    <el-slider v-model="form.freshnessWeight" :format-tooltip="formatTooltip" />
                                </div>
                                <div class="weight-total">
                                    <span>总权重：{{ totalWeight.toFixed(2) }}</span>
                                    <el-alert v-if="totalWeight !== 1" :title="totalWeight !== 1 ? '权重总和必须为1' : ''"
                                        type="warning" :closable="false" show-icon />
                                </div>
                            </div>
                        </el-collapse-item>
                    </el-collapse>
                </el-form-item>

                <!-- 保存按钮 -->
                <el-form-item>
                    <el-button type="primary" @click="handleSave" :disabled="totalWeight !== 1">保存配置</el-button>
                    <el-button @click="handleReset">重置</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
const formatTooltip = (val: number) => {
    return val / 100
}
// 表单数据
const form = reactive({
    collaborativeFilteringAlgorithm: '协同过滤',
    recommendCount: 20,
    updateFrequency: 'daily',
    contentSimilarityWeight: 30,
    userPreferenceWeight: 30,
    popularityWeight: 20,
    freshnessWeight: 20
})

// 折叠面板状态
const activeNames = ref(['1'])

// 计算总权重
const totalWeight = computed(() => {
    return form.contentSimilarityWeight / 100 + form.userPreferenceWeight / 100 + form.popularityWeight / 100 + form.freshnessWeight / 100
})

// 保存配置
const handleSave = () => {
    if (totalWeight.value !== 1) {
        ElMessage.warning('权重总和必须为1')
        return
    }
    ElMessage.success('配置保存成功')
    console.log('保存的配置:', form)
}

// 重置配置
const handleReset = () => {
    form.collaborativeFilteringAlgorithm = '协同过滤算法'
    form.recommendCount = 20
    form.updateFrequency = 'daily'
    form.contentSimilarityWeight = 0.3
    form.userPreferenceWeight = 0.3
    form.popularityWeight = 0.2
    form.freshnessWeight = 0.2
    ElMessage.info('配置已重置')
}
</script>

<style scoped>
.recommend-config {
    padding: 24px;
    background: linear-gradient(135deg, #f5f7fa 0%, #ffffff 100%);
    min-height: 100vh;
}

.page-header {
    margin-bottom: 32px;
}

:deep(.el-collapse) {
    width: 100%;
}

.page-header h1 {
    font-size: 24px;
    font-weight: 600;
    margin-bottom: 16px;
    color: #333;
}

.config-card {
    border-radius: 16px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
    border: 1px solid rgba(102, 126, 234, 0.1);
}

.config-form {
    padding: 24px;
}

.full-width {
    width: 100%;
}

.weight-config {
    padding: 16px;
    background: #f9fafb;
    border-radius: 8px;
    margin-top: 8px;
}

.weight-value {
    margin-left: 16px;
    font-weight: 500;
    color: #667eea;
}

.weight-total {
    margin-top: 24px;
    padding-top: 16px;
    border-top: 1px solid #e5e7eb;
    font-weight: 500;
    color: #333;
}

:deep(.el-slider) {
    width: calc(100% - 80px);
    display: inline-block;
}

:deep(.el-alert) {
    margin-top: 12px;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .recommend-config {
        padding: 16px;
    }

    .config-form {
        padding: 16px;
    }

    :deep(.el-form-item__label) {
        width: 100px !important;
    }

    :deep(.el-slider) {
        width: calc(100% - 60px);
    }
}

/* 暗色模式支持 */
@media (prefers-color-scheme: dark) {
    .recommend-config {
        background: linear-gradient(135deg, #1f2937 0%, #111827 100%);
    }

    .page-header h1 {
        color: #f9fafb;
    }

    .config-card {
        background: #1f2937;
        border-color: rgba(102, 126, 234, 0.2);
    }

    .weight-config {
        background: #374151;
    }

    .weight-value {
        color: #818cf8;
    }

    .weight-total {
        color: #f9fafb;
        border-top-color: #374151;
    }
}
</style>
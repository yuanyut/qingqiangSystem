<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'
import { addCulture, updateCulture, uploadCulture } from '@/api/api'
import type { CultureData  } from '@/api/api'
const formRef = ref<FormInstance>()

// 文章表单数据类型
interface ArticleFormData {
    coverUrl: string
    title: string
    category: string
    publishTime: string
    statusText: string
    status: number
    viewCount: number
    likeCount: number
    commentCount: number
    summary: string
    content: string
}

const editModul = defineModel('editModul')
const content = defineModel<{ id?: number; title: string; content: string; category: string; cover: string; viewCount?: number; likeCount?: number; publishTime?: string; status?: number }>('content', { default: () => ({ id: undefined, title: '', content: '', category: '', cover: '', viewCount: 0, likeCount: 0, publishTime: '', status: 0 }) })
const formLabelWidth = '100px'

// 初始化 form
const form = reactive<ArticleFormData>({
    coverUrl: content.value.cover || '',
    title: content.value.title || '',
    category: content.value.category || '',
    publishTime: content.value.publishTime || '',   
    statusText: '',
    status: content.value.status || 0,
    viewCount: content.value.viewCount || 0,
    likeCount: content.value.likeCount || 0,
    commentCount: 0,
    summary: '',
    content: ''
})
const statusOptions = ref([
    { label: '已上架', value: 0 },
    { label: '已下架', value: 1 }
])


const props = defineProps<{ title: string, opear: string }>()

// 加载状态
const loading = ref(false)

// 隐藏的文件上传input
const fileInputRef = ref<HTMLInputElement>()

// 触发文件选择
const triggerUpload = () => {
    fileInputRef.value?.click()
}

// 处理文件上传
const handleFileChange = async (event: Event) => {
    const input = event.target as HTMLInputElement
    const file = input.files?.[0]
    
    if (!file) return
    
    // 验证文件类型
    if (!file.type.startsWith('image/')) {
        ElMessage.error('请选择图片文件')
        return
    }
    
    // 验证文件大小（限制2MB）
    if (file.size > 2 * 1024 * 1024) {
        ElMessage.error('图片大小不能超过2MB')
        return
    }
    
    // 上传到服务器
    const formData = new FormData()
    formData.append('file', file)
    
    try {
        const response = await uploadCulture(formData)
        if (response && response.data && response.data.url) {
            form.coverUrl = response.data.url
            ElMessage.success('上传成功')
        } else {
            ElMessage.error('上传失败')
        }
    } catch (error) {
        console.error('上传失败:', error)
        ElMessage.error('上传失败，请重试')
    }
    
    // 清空input，以便再次选择同一文件
    input.value = ''
}

// 监听 content 变化，更新 form
watch(content, (newVal) => {
    console.log('子组件收到 content:', newVal)
    if (newVal) {
        Object.assign(form, newVal)
        console.log('form 更新后:', form)
    }
}, { deep: true, immediate: true })

// 确定按钮：调用后端API进行编辑
const handleConfirm = async () => {
    // 验证必填字段
    if (!form.title) {
        ElMessage.error('请输入标题')
        return
    }
    if (!form.content) {
        ElMessage.error('请输入内容')
        return
    }
    if (!form.category) {
        ElMessage.error('请选择分类')
        return
    }

    loading.value = true
    try {
        // 准备数据

        const cultureData: CultureData = {
            id: (content.value as any)?.id,
            title: form.title,
            content: form.content,
            category: form.category,
            cover: form.coverUrl,
            viewCount: form.viewCount,
            likeCount: form.likeCount,
            status: form.status
        }
        console.log(cultureData)
        // 编辑操作
        await updateCulture(cultureData)
        ElMessage.success('更新成功')

        // 更新父组件数据
        Object.assign(content.value, form)
        
        editModul.value = false
    } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error('操作失败，请重试')
    } finally {
        loading.value = false
    }
}

// 取消按钮：重置表单
const handleCancel = (formEl: FormInstance | undefined) => {
    // 先重置表单数据
    Object.assign(form, {
        coverUrl: '',
        title: '',
        category: '',
        publishTime: '',
        statusText: '',
        status: 0,
        viewCount: 0,
        likeCount: 0,
        commentCount: 0,
        summary: '',
        content: ''
    })
    // 重置content数据
    content.value = {
        id: undefined,
        title: '',
        content: '',
        category: '',
        cover: '',
        status: 0
    }
    // 再关闭对话框
    editModul.value = false
    // 重置表单校验状态
    if (formEl) {
        formEl.resetFields()
    }
}
</script>

<template>
    <el-dialog v-model="editModul" :show-close="false" :title="props.title" width="600">
        <el-form :model="form" ref="formRef" :label-width="formLabelWidth">
            <!-- 封面 - 点击可更换 -->
            <el-form-item label="封面" prop="coverUrl">
                <div class="cover-upload">
                    <!-- 有封面时显示图片，可点击更换 -->
                    <div v-if="form.coverUrl" class="cover-preview" @click="triggerUpload" :disabled="loading">
                        <el-image 
                            :src="form.coverUrl" 
                            fit="cover"
                        />
                        <div class="cover-mask">
                            <span>点击更换</span>
                        </div>
                    </div>
                    <!-- 无封面时显示上传按钮 -->
                    <div v-else class="cover-placeholder" @click="triggerUpload" :disabled="loading">
                        <el-icon><Plus /></el-icon>
                        <span>点击上传封面</span>
                    </div>
                    
                    <!-- 隐藏的文件上传input -->
                    <input 
                        type="file"
                        ref="fileInputRef"
                        style="display: none"
                        accept="image/jpeg,image/png,image/gif,image/webp"
                        @change="handleFileChange"
                        :disabled="loading"
                    />
                </div>
            </el-form-item>
            
            <!-- 标题 -->
            <el-form-item label="标题" prop="title">
                <el-input v-model="form.title" autocomplete="off" placeholder="请输入文章标题" :disabled="loading" />
            </el-form-item>

            <!-- 分类 -->
            <el-form-item label="分类" prop="category">
                <el-select v-model="form.category" placeholder="请选择分类" style="width: 100%" :disabled="loading">
                    <el-option label="文化研究" value="文化研究" />
                    <el-option label="剧目赏析" value="剧目赏析" />
                    <el-option label="人物专访" value="人物专访" />
                    <el-option label="新闻动态" value="新闻动态" />
                    <el-option label="戏曲知识" value="戏曲知识" />
                    <el-option label="音乐研究" value="音乐研究" />
                    <el-option label="非遗保护" value="非遗保护" />
                </el-select>
            </el-form-item>

            
            

            <!-- 发布时间 -->
            <el-form-item v-if="form.publishTime" label="发布时间" prop="publishTime">
                <el-input v-model="form.publishTime" autocomplete="off" disabled />
            </el-form-item>


            <!-- 内容 -->
            <el-form-item label="内容" prop="content">
                <el-input 
                    v-model="form.content" 
                    type="textarea" 
                    :rows="6" 
                    placeholder="请输入文章内容"
                    :disabled="loading"
                />
            </el-form-item>

            <!-- 状态 -->
            <el-form-item label="状态" prop="status">
                <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%" :disabled="loading">
                    <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </el-form-item>
        </el-form>
        
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="handleCancel(formRef)" :disabled="loading">取消</el-button>
                <el-button type="primary" @click="handleConfirm" :loading="loading">{{ loading ? '处理中...' : '确定' }}</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<style scoped>
.cover-upload {
    display: flex;
    align-items: center;
    gap: 12px;
}

/* 封面预览样式 - 可点击 */
.cover-preview {
    position: relative;
    width: 100px;
    height: 100px;
    border-radius: 8px;
    overflow: hidden;
    cursor: pointer;
    border: 1px solid #e4e7ed;
}

.cover-preview .el-image {
    width: 100%;
    height: 100%;
}

.cover-mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.6);
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: opacity 0.3s;
    color: white;
    font-size: 12px;
}

.cover-preview:hover .cover-mask {
    opacity: 1;
}

/* 无封面时的占位符 */
.cover-placeholder {
    width: 100px;
    height: 100px;
    border: 1px dashed #d9d9d9;
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s;
    background: #fafafa;
    color: #999;
    gap: 8px;
}

.cover-placeholder:hover {
    border-color: #409eff;
    color: #409eff;
    background: #f0f9ff;
}

.cover-placeholder .el-icon {
    font-size: 24px;
}

.cover-placeholder span {
    font-size: 12px;
}

.stats-row {
    display: flex;
    gap: 24px;
    color: #909399;
    font-size: 13px;
}

:deep(.el-textarea__inner) {
    font-family: inherit;
}
</style>
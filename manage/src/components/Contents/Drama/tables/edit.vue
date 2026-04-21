<script lang="ts" setup>
import { reactive, ref, watch, onMounted } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage, ElUpload } from 'element-plus'
import { getDramaCategoryList } from '@/api/api'
import type { DramaCategory } from '@/api/api'
const formRef = ref<FormInstance>()

// 修正类型定义 - 适配视频数据格式
interface FormItemVideo {
    id?: number
    videoUrl: string
    title: string
    category: string
    clickCount: number
    likeCount: number
    publishTime: string
    statusText: string
    actor: string
    description: string
    duration: number
}

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel('content', { default: () => ({}) })
const selects = defineModel('selects')
const formLabelWidth = '140px'
const emit = defineEmits(['confirm'])

// 上传相关状态
const uploadLoading = ref(false)
const uploadProgress = ref(0)
const videoFile = ref<File | null>(null)

// 初始化 form - 适配视频字段
const form = reactive<FormItemVideo>({
    id: undefined,
    videoUrl: '',
    title: '',
    category: '',
    clickCount: 0,
    likeCount: 0,
    publishTime: '',
    statusText: '',
    actor: '',
    description: '',
    duration: 0
})

const props = defineProps<{ title: string, opear: string }>()

// 监听 content 变化，更新 form（用 Object.assign 保持响应式）
watch(content, (newVal) => {
    console.log('子组件收到 content:', newVal)
    if (newVal) {
        // 用 Object.assign 更新属性，而不是直接赋值
        Object.assign(form, newVal)
        // 确保 statusText 正确设置
        if ('status' in newVal && newVal.status !== undefined) {
            form.statusText = newVal.status === 1 ? '已发布' : '已下架'
        }
        console.log('form 更新后:', form)
    }
}, { deep: true, immediate: true })

// 格式化时长（秒 -> mm:ss）用于显示
const formatDurationForDisplay = (seconds: number) => {
    if (!seconds) return '00:00'
    const mins = Math.floor(seconds / 60)
    const secs = seconds % 60
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`
}


// 获取视频时长
const getVideoDuration = (file: File): Promise<number> => {
    return new Promise((resolve) => {
        const video = document.createElement('video')
        video.preload = 'metadata'
        video.onloadedmetadata = () => {
            window.URL.revokeObjectURL(video.src)
            resolve(Math.floor(video.duration))
        }
        video.src = URL.createObjectURL(file)
    })
}

// 上传视频到服务器（模拟）
const uploadVideoToServer = async (file: File): Promise<string> => {
    return new Promise((resolve, reject) => {
        // 模拟上传进度
        const interval = setInterval(() => {
            if (uploadProgress.value < 90) {
                uploadProgress.value += 10
            }
        }, 200)

        // 模拟上传完成
        setTimeout(() => {
            clearInterval(interval)
            uploadProgress.value = 100

            // 模拟返回的视频URL
            // 实际项目中这里应该调用真实的上传接口
            const mockVideoUrl = URL.createObjectURL(file)
            setTimeout(() => {
                resolve(mockVideoUrl)
            }, 500)
        }, 2000)
    })
}

// 视频上传前验证
const beforeVideoUpload = (file: File) => {
    const isVideo = file.type.startsWith('video/')
    if (!isVideo) {
        ElMessage.error('只能上传视频文件!')
        return false
    }

    const isLt100M = file.size / 1024 / 1024 < 100
    if (!isLt100M) {
        ElMessage.error('视频大小不能超过 100MB!')
        return false
    }

    videoFile.value = file
    return false // 阻止自动上传，手动处理
}

// 自定义上传
const handleVideoUpload = async (file: File) => {
    uploadLoading.value = true
    uploadProgress.value = 0

    try {
        // 获取视频时长
        const duration = await getVideoDuration(file)
        form.duration = duration

        // 上传视频到服务器
        const videoUrl = await uploadVideoToServer(file)
        form.videoUrl = videoUrl

        ElMessage.success('视频上传成功!')
    } catch (error) {
        console.error('上传失败:', error)
        ElMessage.error('视频上传失败，请重试!')
    } finally {
        uploadLoading.value = false
        setTimeout(() => {
            uploadProgress.value = 0
        }, 1000)
    }
}

// 处理文件选择
const handleFileChange = (file: any) => {
    if (file.raw) {
        handleVideoUpload(file.raw)
    }
}

// 移除视频
const handleRemoveVideo = () => {
    form.videoUrl = ''
    videoFile.value = null
    form.duration = 0
    ElMessage.info('已移除视频')
}

// 确定按钮：将修改后的数据传回父组件
const handleConfirm = async () => {
    // 验证必填字段
    if (!form.title) {
        ElMessage.error('请填写视频标题')
        return
    }
    if (!form.videoUrl) {
        ElMessage.error('请上传视频文件或填写视频链接')
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
    // 触发confirm事件，通知父组件执行相应的逻辑
    console.log('formEdit:', form)
    emit('confirm', { ...form })
    dialogFormVisible.value = false
}

// 重置表单
const resetForm = () => {
    form.id = undefined
    form.videoUrl = ''
    form.title = ''
    form.category = ''
    form.clickCount = 0
    form.likeCount = 0
    form.publishTime = ''
    form.statusText = ''
    form.actor = ''
    form.description = ''
    form.duration = 0
    videoFile.value = null
}

const handleCancel = () => {
    // 先重置表单数据
    resetForm()
    // 重置content数据
    content.value = {
        id: undefined,
        videoUrl: '',
        title: '',
        description: '',
        actor: '',
        category: '',
        duration: 0,
        clickCount: 0,
        likeCount: 0,
        publishTime: '',
        statusText: ''
    }
    // 再关闭对话框
    dialogFormVisible.value = false
    // 重置表单校验状态
    if (formRef.value) {
        formRef.value.resetFields()
    }
}

// 分类列表
const categoryList = ref<DramaCategory[]>([])

// 获取分类列表
const fetchCategoryList = async () => {
    try {
        const response = await getDramaCategoryList()
        if (response && response.data) {
            categoryList.value = response.data
        }
    } catch (error) {
        console.error('获取分类列表失败:', error)
    }
}

// 初始化
onMounted(() => {
    fetchCategoryList()
})

// 规则验证
const rules = {
    title: [
        { required: true, message: '请输入视频标题', trigger: 'blur' },
        { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
    ],
    videoUrl: [
        { required: true, message: '请上传视频或填写视频链接', trigger: 'change' }
    ],
    actor: [
        { required: true, message: '请输入演员名称', trigger: 'blur' }
    ],
    category: [
        { required: true, message: '请选择分类', trigger: 'change' }
    ],
    statusText: [
        { required: true, message: '请选择状态', trigger: 'change' }
    ]
}
</script>

<template>
    <el-dialog v-model="dialogFormVisible" :show-close="false" :title="props.title" width="700">
        <el-form :model="form" ref="formRef" :rules="rules" label-position="right">

            <!-- 视频上传 -->
            <el-form-item label="上传视频" :label-width="formLabelWidth">
                <div class="upload-container">
                    <el-upload class="video-uploader" :before-upload="beforeVideoUpload" :on-change="handleFileChange"
                        :show-file-list="false" accept="video/*" :auto-upload="false">
                        <el-button type="primary" :loading="uploadLoading" :icon="uploadLoading ? '' : 'Upload'">
                            {{ uploadLoading ? '上传中...' : '选择视频文件' }}
                        </el-button>
                        <template #tip>
                            <div class="el-upload__tip">
                                支持 mp4, avi, mov 等格式，大小不超过 100MB
                            </div>
                        </template>
                    </el-upload>

                    <!-- 上传进度 -->
                    <el-progress v-if="uploadLoading" :percentage="uploadProgress" :stroke-width="8"
                        style="width: 200px; margin-top: 10px;" />
                </div>
            </el-form-item>

            <!-- 视频链接 -->
            <el-form-item label="视频链接" :label-width="formLabelWidth" prop="videoUrl">
                <el-input v-model="form.videoUrl" placeholder="或直接输入视频地址（如：https://example.com/video.mp4）"
                    :disabled="uploadLoading">
                    <template #append v-if="form.videoUrl">
                        <el-button @click="handleRemoveVideo" :disabled="uploadLoading">移除</el-button>
                    </template>
                </el-input>
            </el-form-item>

            <!-- 视频预览（如果有链接） -->
            <el-form-item v-if="form.videoUrl && !uploadLoading" label="视频预览" :label-width="formLabelWidth">
                <div class="video-preview-container">
                    <video :src="form.videoUrl" controls preload="metadata"
                        style="width: 100%; max-height: 250px; border-radius: 8px; background: #000;">
                        您的浏览器不支持 video 标签。
                    </video>
                    <div v-if="form.duration" class="duration-info">
                        时长：{{ formatDurationForDisplay(form.duration) }}
                    </div>
                </div>
            </el-form-item>

            <!-- 发布时间（仅编辑时显示） -->
            <el-form-item v-if="form.publishTime && props.opear !== '0'" label="发布时间" :label-width="formLabelWidth">
                <el-input v-model="form.publishTime" disabled />
            </el-form-item>

            <!-- 视频标题 -->
            <el-form-item label="视频标题" :label-width="formLabelWidth" prop="title">
                <el-input v-model="form.title" placeholder="请输入视频标题" />
            </el-form-item>

            <!-- 演员 -->
            <el-form-item label="演员" :label-width="formLabelWidth" prop="actor">
                <el-input v-model="form.actor" placeholder="请输入主要演员" />
            </el-form-item>

            <!-- 描述 -->
            <el-form-item label="描述" :label-width="formLabelWidth" prop="description">
                <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入视频描述" />
            </el-form-item>

            <!-- 分类 -->
            <el-form-item label="分类" :label-width="formLabelWidth" prop="category">
                <el-select v-model="form.category" placeholder="请选择分类" style="width: 100%">
                    <el-option v-for="category in categoryList" :key="category.id" :label="category.name" :value="category.name" />
                </el-select>
            </el-form-item>

            <!-- 状态 -->
            <el-form-item label="状态" :label-width="formLabelWidth" prop="statusText">
                <el-select v-model="form.statusText" placeholder="请选择状态" style="width: 100%">
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
                <el-button @click="handleCancel">取消</el-button>
                <el-button type="primary" @click="handleConfirm" :loading="uploadLoading">确定</el-button>
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

.upload-container {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.video-preview-container {
    width: 100%;
}

.duration-info {
    margin-top: 8px;
    font-size: 12px;
    color: #409eff;
    text-align: center;
}

video {
    object-fit: cover;
}

:deep(.el-upload__tip) {
    margin-top: 8px;
    font-size: 12px;
    color: #909399;
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
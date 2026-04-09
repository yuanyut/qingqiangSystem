<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage, ElUpload } from 'element-plus'

const formRef = ref<FormInstance>()

// 修正类型定义 - 适配名家数据格式
interface FormItemActor {
    id?: number
    name: string
    photo: string
    description: string
    juese: string
    category: string
    worksCount: number
    clickCount: number
    likeCount: number
    joinTime: string
    statusText: string
}

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel('content', { default: () => ({}) })
const selects = defineModel('selects')
const formLabelWidth = '140px'

// 上传相关状态
const uploadLoading = ref(false)
const uploadProgress = ref(0)
const photoFile = ref<File | null>(null)

// 初始化 form - 适配名家字段
const form = reactive<FormItemActor>({
    id: undefined,
    name: '',
    photo: '',
    description: '',
    juese: '',
    category: '',
    worksCount: 0,
    clickCount: 0,
    likeCount: 0,
    joinTime: '',
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

// 上传照片到服务器（模拟）
const uploadPhotoToServer = async (file: File): Promise<string> => {
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

            // 模拟返回的照片URL
            // 实际项目中这里应该调用真实的上传接口
            const mockPhotoUrl = URL.createObjectURL(file)
            setTimeout(() => {
                resolve(mockPhotoUrl)
            }, 500)
        }, 1500)
    })
}

// 照片上传前验证
const beforePhotoUpload = (file: File) => {
    const isImage = file.type.startsWith('image/')
    if (!isImage) {
        ElMessage.error('只能上传图片文件!')
        return false
    }

    const isLt5M = file.size / 1024 / 1024 < 5
    if (!isLt5M) {
        ElMessage.error('图片大小不能超过 5MB!')
        return false
    }

    photoFile.value = file
    return false // 阻止自动上传，手动处理
}

// 自定义上传
const handlePhotoUpload = async (file: File) => {
    uploadLoading.value = true
    uploadProgress.value = 0

    try {
        // 上传照片到服务器
        const photoUrl = await uploadPhotoToServer(file)
        form.photo = photoUrl

        ElMessage.success('照片上传成功!')
    } catch (error) {
        console.error('上传失败:', error)
        ElMessage.error('照片上传失败，请重试!')
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
        handlePhotoUpload(file.raw)
    }
}

// 移除照片
const handleRemovePhoto = () => {
    form.photo = ''
    photoFile.value = null
    ElMessage.info('已移除照片')
}

// 确定按钮：将修改后的数据传回父组件
const handleConfirm = async () => {
    // 验证必填字段
    if (!form.name) {
        ElMessage.error('请填写名家姓名')
        return
    }
    if (!form.photo) {
        ElMessage.error('请上传照片或填写照片链接')
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
    form.name = ''
    form.photo = ''
    form.description = ''
    form.juese = ''
    form.category = ''
    form.worksCount = 0
    form.clickCount = 0
    form.likeCount = 0
    form.joinTime = ''
    form.statusText = ''
    photoFile.value = null
}

const handleCancel = (formEl: FormInstance | undefined) => {
    dialogFormVisible.value = false
    if (!formEl) return
    formEl.resetFields()
    resetForm()
}

// 规则验证
const rules = {
    name: [
        { required: true, message: '请输入名家姓名', trigger: 'blur' },
        { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
    ],
    photo: [
        { required: true, message: '请上传照片或填写照片链接', trigger: 'change' }
    ],
    juese: [
        { required: true, message: '请选择角色', trigger: 'change' }
    ],
    category: [
        { required: true, message: '请选择流派', trigger: 'change' }
    ],
    statusText: [
        { required: true, message: '请选择状态', trigger: 'change' }
    ]
}
</script>

<template>
    <el-dialog v-model="dialogFormVisible" :show-close="false" :title="props.title" width="700">
        <el-form :model="form" ref="formRef" :rules="rules" label-position="right">

            <!-- 照片上传 -->
            <el-form-item label="上传照片" :label-width="formLabelWidth">
                <div class="upload-container">
                    <el-upload class="photo-uploader" :before-upload="beforePhotoUpload" :on-change="handleFileChange"
                        :show-file-list="false" accept="image/*" :auto-upload="false">
                        <el-button type="primary" :loading="uploadLoading" :icon="uploadLoading ? '' : 'Upload'">
                            {{ uploadLoading ? '上传中...' : '选择照片文件' }}
                        </el-button>
                        <template #tip>
                            <div class="el-upload__tip">
                                支持 jpg, png, jpeg 等格式，大小不超过 5MB
                            </div>
                        </template>
                    </el-upload>

                    <!-- 上传进度 -->
                    <el-progress v-if="uploadLoading" :percentage="uploadProgress" :stroke-width="8"
                        style="width: 200px; margin-top: 10px;" />
                </div>
            </el-form-item>

            <!-- 照片链接 -->
            <el-form-item label="照片链接" :label-width="formLabelWidth" prop="photo">
                <el-input v-model="form.photo" placeholder="或直接输入照片地址（如：https://example.com/actor.jpg）"
                    :disabled="uploadLoading">
                    <template #append v-if="form.photo">
                        <el-button @click="handleRemovePhoto" :disabled="uploadLoading">移除</el-button>
                    </template>
                </el-input>
            </el-form-item>

            <!-- 照片预览（如果有链接） -->
            <el-form-item v-if="form.photo && !uploadLoading" label="照片预览" :label-width="formLabelWidth">
                <div class="photo-preview-container">
                    <img :src="form.photo" alt="名家照片"
                        style="width: 200px; height: 250px; object-fit: cover; border-radius: 8px; border: 1px solid #e4e7ed;">
                </div>
            </el-form-item>

            <!-- 加入时间（仅编辑时显示） -->
            <el-form-item v-if="form.joinTime && props.opear !== '0'" label="加入时间" :label-width="formLabelWidth">
                <el-input v-model="form.joinTime" disabled />
            </el-form-item>

            <!-- 名家姓名 -->
            <el-form-item label="名家姓名" :label-width="formLabelWidth" prop="name">
                <el-input v-model="form.name" placeholder="请输入名家姓名" />
            </el-form-item>

            <!-- 角色 -->
            <el-form-item label="角色" :label-width="formLabelWidth" prop="juese">
                <el-select v-model="form.juese" placeholder="请选择角色" style="width: 100%">
                    <el-option label="旦角" value="旦角" />
                    <el-option label="须生" value="须生" />
                    <el-option label="小生" value="小生" />
                    <el-option label="花脸" value="花脸" />
                    <el-option label="丑角" value="丑角" />
                </el-select>
            </el-form-item>

            <!-- 描述 -->
            <el-form-item label="个人简介" :label-width="formLabelWidth" prop="description">
                <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入名家个人简介" />
            </el-form-item>

            <!-- 流派 -->
            <el-form-item label="流派" :label-width="formLabelWidth" prop="category">
                <el-select v-model="form.category" placeholder="请选择流派" style="width: 100%">
                    <el-option label="传统派" value="传统派" />
                    <el-option label="创新派" value="创新派" />
                    <el-option label="现代派" value="现代派" />
                </el-select>
            </el-form-item>

            <!-- 状态 -->
            <el-form-item label="状态" :label-width="formLabelWidth" prop="statusText">
                <el-select v-model="form.statusText" placeholder="请选择状态" style="width: 100%">
                    <el-option label="活跃" value="活跃" />
                    <el-option label="退休" value="退休" />
                    <el-option label="离世" value="离世" />
                </el-select>
            </el-form-item>

            <!-- 统计信息（仅编辑时显示） -->
            <template v-if="props.opear !== '0'">
                <el-form-item label="作品数" :label-width="formLabelWidth">
                    <el-input v-model="form.worksCount" disabled />
                </el-form-item>
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

.photo-preview-container {
    width: 100%;
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
<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage, ElUpload } from 'element-plus'
import type { ProfileData } from '@/api/api'
import { addProfile, updateProfile } from '@/api/api'

const formRef = ref<FormInstance>()

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel<ProfileData | undefined>('content', { default: () => ({ id: undefined, title: '', content: '', source: '', cover: '', viewCount: 0, likeCount: 0, createTime: '', updateTime: '', status: 0, category: '' }) })
const selects = defineModel('selects')
const formLabelWidth = '140px'

// 初始化 form - 适配资讯字段
const form = reactive<ProfileData>({
    id: undefined,
    title: '',
    content: '',
    source: '',
    cover: '',
    viewCount: 0,
    likeCount: 0,
    createTime: '',
    updateTime: '',
    status: 0,
    category: ''
})

const currentStatus = ref<string>('0')

// 上传请求头（包含 Token）
const uploadHeaders = ref({
    'Authorization': `Bearer ${localStorage.getItem('token') || ''}`
})

// 封面上传成功
const handleCoverUploadSuccess = (response: any) => {
    if (response && response.data && response.data.url) {
        form.cover = response.data.url
        ElMessage.success('封面上传成功!')
    }
}

// 封面上传失败
const handleCoverUploadError = () => {
    ElMessage.error('封面上传失败，请重试!')
}

// 封面上传前验证
const beforeCoverUpload = (file: File) => {
    const isImage = file.type.startsWith('image/')
    if (!isImage) {
        ElMessage.error('只能上传图片文件!')
        return false
    }
    const isLt2M = file.size / 1024 / 1024 < 2
    if (!isLt2M) {
        ElMessage.error('图片大小不能超过 2MB!')
        return false
    }
    return true
}

const props = defineProps<{ title: string, opear: string }>()

// 监听 content 变化，更新 form（用 Object.assign 保持响应式）
watch(content, (newVal) => {
    console.log('子组件收到 content:', newVal)
    if (newVal) {
        // 用 Object.assign 更新属性，而不是直接赋值
        Object.assign(form, newVal)
        currentStatus.value = (newVal.status || 0).toString()
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
    // if (!form.category) {
    //     ElMessage.error('请选择资讯分类')
    //     return
    // }

    form.status = parseInt(currentStatus.value, 10)

    try {
        if (props.opear === '0') {
            // 新增模式
            await addProfile(form)
            ElMessage.success('添加成功!')
            content.value = { ...form }
            // 重置表单
            resetForm()
        } else {
            // 编辑模式
            await updateProfile(form)
            ElMessage.success('编辑成功!')
            if (content.value) {
                Object.assign(content.value, form)
            }
        }
        dialogFormVisible.value = false
        emits('confirm', { ...form })
    } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error('操作失败，请重试')
    }
}

const emits = defineEmits(['confirm'])

// 重置表单
const resetForm = () => {
    form.id = undefined
    form.title = ''
    form.content = ''
    form.source = ''
    form.cover = ''
    form.viewCount = 0
    form.likeCount = 0
    form.createTime = ''
    form.updateTime = ''
    form.status = 0
    form.category = ''
    currentStatus.value = '0'
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
    // category: [
    //     { required: true, message: '请选择资讯分类', trigger: 'change' }
    // ]
}
</script>

<template>
    <el-dialog v-model="dialogFormVisible" :show-close="false" :title="props.title" width="700">
        <el-form :model="form" ref="formRef" :rules="rules" label-position="right">

            <!-- 发布时间（仅编辑时显示） -->
            <el-form-item v-if="form.createTime && props.opear !== '0'" label="发布时间" :label-width="formLabelWidth">
                <el-input v-model="form.createTime" disabled />
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
            <!-- <el-form-item label="分类" :label-width="formLabelWidth" prop="category">
                <el-select v-model="form.category" placeholder="请选择分类" style="width: 100%">
                    <el-option label="名家动态" value="名家动态" />
                    <el-option label="活动通知" value="活动通知" />
                    <el-option label="非遗动态" value="非遗动态" />
                    <el-option label="赛事新闻" value="赛事新闻" />
                    <el-option label="文化动态" value="文化动态" />
                </el-select>
            </el-form-item> -->

            <!-- 来源 -->
            <el-form-item label="来源" :label-width="formLabelWidth">
                <el-input v-model="form.source" placeholder="请输入来源" />
            </el-form-item>

            <!-- 封面 -->
            <el-form-item label="封面" :label-width="formLabelWidth">
                <div class="upload-container">
                    <el-upload class="avatar-uploader" action="/content/culture/upload" :show-file-list="false"
                        :on-success="handleCoverUploadSuccess" :on-error="handleCoverUploadError"
                        :before-upload="beforeCoverUpload" :headers="uploadHeaders">
                        <img v-if="form.cover" :src="form.cover" class="avatar" />
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </div>
            </el-form-item>

            <!-- 状态 -->
            <el-form-item label="状态" :label-width="formLabelWidth">
                <el-select v-model="currentStatus" placeholder="请选择状态" style="width: 100%">
                    <el-option label="已上架" value="0" />
                    <el-option label="已下架" value="1" />
                </el-select>
            </el-form-item>

            <!-- 统计信息（仅编辑时显示） -->
            <template v-if="props.opear !== '0'">
                <el-form-item label="点击量" :label-width="formLabelWidth">
                    <el-input v-model="form.viewCount" disabled />
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

.upload-container {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.avatar-uploader {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 150px;
    height: 150px;
    border: 1px dashed #d9d9d9;
    border-radius: 8px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader:hover {
    border-color: #409eff;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
}

.avatar {
    width: 100%;
    height: 100%;
    display: block;
    object-fit: cover;
}

:deep(.el-upload__tip) {
    margin-top: 8px;
    font-size: 12px;
    color: #909399;
}
</style>
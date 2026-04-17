<script lang="ts" setup>
import { reactive, ref, watch, onMounted } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage, ElUpload } from 'element-plus'
import { addCulture } from '@/api/api'
const formRef = ref<FormInstance>()

// 类型定义 - 适配文化内容数据格式
interface FormItemCulture {
    id?: number
    title: string
    content: string
    category: string
    cover: string
    viewCount: number
    likeCount: number
    createTime: string
}

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel<{ id?: number; title: string; content: string; category: string; cover: string }>('content', { default: () => ({ id: undefined, title: '', content: '', category: '', cover: '' }) })
const formLabelWidth = '100px'
const emit = defineEmits(['confirm'])

// 上传相关状态
const uploadLoading = ref(false)
const uploadProgress = ref(0)

// 初始化 form - 适配文化内容字段
const form = reactive<FormItemCulture>({
    id: undefined,
    title: '',
    content: '',
    category: '',
    cover: '',
    viewCount: 0,
    likeCount: 0,
    createTime: ''
})

const props = defineProps<{ title: string }>()

// 监听 content 变化，更新 form（用 Object.assign 保持响应式）
watch(content, (newVal) => {
    console.log('子组件收到 content:', newVal)
    if (newVal) {
        // 用 Object.assign 更新属性，而不是直接赋值
        Object.assign(form, newVal)
        console.log('form 更新后:', form)
    }
}, { deep: true, immediate: true })

// 封面上传前验证
const beforeCoverUpload = (file: File) => {
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

    return true
}

// 封面上传成功处理
const handleCoverUploadSuccess = (response: any, file: any) => {
    if (response && response.data) {
        form.cover = response.data.url
        ElMessage.success('封面上传成功!')
    } else {
        ElMessage.error('封面上传失败!')
    }
}

// 封面上传失败处理
const handleCoverUploadError = () => {
    ElMessage.error('封面上传失败，请重试!')
}

// 移除封面
const handleRemoveCover = () => {
    form.cover = ''
    ElMessage.info('已移除封面')
}

// 确定按钮：将修改后的数据传回父组件
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

    try {
        // 调用后端增加API接口
        await addCulture({
            title: form.title,
            content: form.content,
            category: form.category,
            cover: form.cover
        })

        ElMessage.success('添加成功!')
        dialogFormVisible.value = false
        resetForm()
        emit('confirm', { ...form })
    } catch (error) {
        console.error('添加文化内容失败:', error)
        ElMessage.error('添加失败，请重试!')
    }
}

// 重置表单
const resetForm = () => {
    form.id = undefined
    form.title = ''
    form.content = ''
    form.category = ''
    form.cover = ''
    form.viewCount = 0
    form.likeCount = 0
    form.createTime = ''
}

const handleCancel = () => {
    // 先重置表单数据
    resetForm()
    // 重置content数据
    content.value = {
        id: undefined,
        title: '',
        content: '',
        category: '',
        cover: ''
    }
    // 再关闭对话框
    dialogFormVisible.value = false
    // 重置表单校验状态
    if (formRef.value) {
        formRef.value.resetFields()
    }
}

// 规则验证
const rules = {
    title: [
        { required: true, message: '请输入标题', trigger: 'blur' },
        { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
    ],
    content: [
        { required: true, message: '请输入内容', trigger: 'blur' }
    ],
    category: [
        { required: true, message: '请选择分类', trigger: 'change' }
    ]
}
</script>

<template>
    <el-dialog v-model="dialogFormVisible" :show-close="false" :title="props.title" width="800">
        <el-form :model="form" ref="formRef" :rules="rules" label-position="right">
            <!-- 标题 -->
            <el-form-item label="标题" :label-width="formLabelWidth" prop="title">
                <el-input v-model="form.title" placeholder="请输入标题" />
            </el-form-item>

            <!-- 封面 -->
            <el-form-item label="封面" :label-width="formLabelWidth">
                <el-upload
                    class="avatar-uploader"
                    action="/content/culture/upload"
                    :show-file-list="false"
                    :on-success="handleCoverUploadSuccess"
                    :on-error="handleCoverUploadError"
                    :before-upload="beforeCoverUpload"
                >
                    <img v-if="form.cover" :src="form.cover" class="avatar">
                    <el-button v-else type="primary">上传封面</el-button>
                </el-upload>
                <template #tip>
                    <div class="el-upload__tip">
                        支持 jpg, jpeg, png 等格式，大小不超过 5MB
                    </div>
                </template>
            </el-form-item>

            <!-- 分类 -->
            <el-form-item label="分类" :label-width="formLabelWidth" prop="category">
                <el-select v-model="form.category" placeholder="请选择分类" style="width: 100%">
                    <el-option label="秦腔历史发展" value="秦腔历史发展" />
                    <el-option label="秦腔流派与艺术风格" value="秦腔流派与艺术风格" />
                    <el-option label="行当与角色体系（生旦净丑）" value="行当与角色体系（生旦净丑）" />
                    <el-option label="秦腔经典剧目解析" value="秦腔经典剧目解析" />
                    <el-option label="秦腔音乐与唱腔艺术" value="秦腔音乐与唱腔艺术" />
                    <el-option label="舞美、服饰与化妆" value="舞美、服饰与化妆" />
                    <el-option label="秦腔名家与艺术家" value="秦腔名家与艺术家" />
                    <el-option label="剧团与传承机构" value="剧团与传承机构" />
                    <el-option label="秦腔文化活动" value="秦腔文化活动" />
                    <el-option label="秦腔数字化与多媒体资料" value="秦腔数字化与多媒体资料" />
                </el-select>
            </el-form-item>

            <!-- 内容 -->
            <el-form-item label="内容" :label-width="formLabelWidth" prop="content">
                <el-input v-model="form.content" type="textarea" :rows="10" placeholder="请输入内容" />
            </el-form-item>
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

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}

:deep(.el-upload__tip) {
    margin-top: 8px;
    font-size: 12px;
    color: #909399;
}
</style>
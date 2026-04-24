<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage, ElUpload } from 'element-plus'
import { addActor } from '@/api/api'
import type { ActorData } from '@/api/api'
const formRef = ref<FormInstance>()

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const formLabelWidth = '100px'
const emit = defineEmits(['confirm'])

// 上传相关状态
const uploadLoading = ref(false)

// 初始化 form - 适配名家字段
const form = reactive<ActorData>({
    id: undefined,
    name: '',
    avatar: '',
    intro: '',
    roleName: '',
    style: '',
    joinDate: '',
    worksCount: 0,
    viewCount: 0,
    likeCount: 0,
    createTime: '',
    updateTime: '',
    status: 1,
    dramas: []
})

const statusOptions = ref([
    { value: 1, label: '上架' },
    { value: 0, label: '下架' }
])

const currentStatus = ref(1)
const props = defineProps<{ title: string }>()

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
        form.avatar = response.data.url
        ElMessage.success('封面上传成功!')
    } else {
        ElMessage.error('封面上传失败!')
    }
}

// 封面上传失败处理
const handleCoverUploadError = () => {
    ElMessage.error('封面上传失败，请重试!')
}

// 确定按钮：将修改后的数据传回父组件
const handleConfirm = async () => {
    // 验证必填字段
    if (!form.name) {
        ElMessage.error('请输入姓名')
        return
    }
    // if (!form.avatar) {
    //     ElMessage.error('请上传封面')
    //     return
    // }
    if (!form.intro) {
        ElMessage.error('请输入介绍')
        return
    }

    try {
        form.status = currentStatus.value
        // 调用后端增加API接口
        await addActor({
            name: form.name,
            avatar: form.avatar,
            intro: form.intro,
            roleName: form.roleName,
            style: form.style,
            joinDate: form.joinDate,
            worksCount: form.worksCount,
            viewCount: form.viewCount,
            likeCount: form.likeCount,
            createTime: form.createTime,
            updateTime: form.updateTime,
            status: form.status,
            dramas: form.dramas
        })

        ElMessage.success('添加成功!')
        dialogFormVisible.value = false
        resetForm()
        emit('confirm', { ...form })
    } catch (error) {
        console.error('添加名家失败:', error)
        ElMessage.error('添加失败，请重试!')
    }
}

// 重置表单
const resetForm = () => {
    form.id = undefined
    form.name = ''
    form.avatar = ''
    form.intro = ''
    form.roleName = ''
    form.style = ''
    form.joinDate = ''
    form.worksCount = 0
    form.viewCount = 0
    form.likeCount = 0
    form.createTime = ''
    form.updateTime = ''
    form.status = 1
    form.dramas = []
    currentStatus.value = 1
}

const handleCancel = () => {
    // 先重置表单数据
    resetForm()
    // 再关闭对话框
    dialogFormVisible.value = false
    // 重置表单校验状态
    if (formRef.value) {
        formRef.value.resetFields()
    }
}

// 规则验证
const rules = {
    name: [
        { required: true, message: '请输入姓名', trigger: 'blur' },
        { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
    ],
    intro: [
        { required: true, message: '请输入介绍', trigger: 'blur' }
    ],
    style: [
        { required: true, message: '请选择风格', trigger: 'change' }
    ]
}
</script>

<template>
    <el-dialog v-model="dialogFormVisible" :show-close="false" :title="props.title" width="800">
        <el-form :model="form" ref="formRef" :rules="rules" label-position="right">
            <!-- 姓名 -->
            <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
                <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>

            <!-- 封面 -->
            <el-form-item label="封面" :label-width="formLabelWidth">
                <el-upload class="avatar-uploader" action="/actor/upload" :show-file-list="false"
                    :on-success="handleCoverUploadSuccess" :on-error="handleCoverUploadError"
                    :before-upload="beforeCoverUpload">
                    <img v-if="form.avatar" :src="form.avatar" class="avatar">
                    <el-button v-else type="primary">上传封面</el-button>
                </el-upload>
                <template #tip>
                    <div class="el-upload__tip">
                        支持 jpg, jpeg, png 等格式，大小不超过 5MB
                    </div>
                </template>
            </el-form-item>

            <!-- 角色名 -->
            <el-form-item label="角色名" :label-width="formLabelWidth" prop="roleName">
                <el-select v-model="form.roleName" placeholder="请选择角色名" style="width: 100%">
                    <el-option label="生" value="生" />
                    <el-option label="旦" value="旦" />
                    <el-option label="净" value="净" />
                    <el-option label="丑" value="丑" />
                </el-select>
            </el-form-item>

            <!-- 风格 -->
            <el-form-item label="风格" :label-width="formLabelWidth" prop="style">
                <el-select v-model="form.style" placeholder="请选择风格" style="width: 100%">
                    <el-option label="传统派" value="传统派" />
                    <el-option label="创新派" value="创新派" />
                </el-select>
            </el-form-item>

            <!-- 加入日期 -->
            <el-form-item label="加入日期" :label-width="formLabelWidth">
                <el-date-picker v-model="form.joinDate" type="date" placeholder="请选择加入日期" style="width: 100%" />
            </el-form-item>

            <!-- 状态 -->
            <el-form-item label="状态" :label-width="formLabelWidth">
                <el-select v-model="currentStatus" placeholder="请选择状态" style="width: 100%">
                    <el-option v-for="item in statusOptions" :key="item.value" :label="item.label"
                        :value="item.value" />
                </el-select>
            </el-form-item>

            <!-- 介绍 -->
            <el-form-item label="介绍" :label-width="formLabelWidth" prop="intro">
                <el-input v-model="form.intro" type="textarea" :rows="10" placeholder="请输入介绍" />
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
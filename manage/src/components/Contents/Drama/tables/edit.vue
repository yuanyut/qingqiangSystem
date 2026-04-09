<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormInstance } from 'element-plus'

const formRef = ref<FormInstance>()
// 修正类型定义
interface FormItemUser {
    coverUrl: string,
    name: string,
    category: string,
    clickCount: number,
    publishTime: string,
    statusText: string,
    actor: string,
    description: string,
    content: string
}

const dialogFormVisible = defineModel('dialogFormVisible', { default: false })
const content = defineModel('content', { default: () => ({}) })
const selects = defineModel('selects')
const formLabelWidth = '140px'

// 初始化 form
const form = reactive<FormItemUser>({
    coverUrl: '',
    name: '',
    category: '',
    clickCount: 0,
    publishTime: '',
    statusText: '',
    actor: '',
    description: '',
    content: ''
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

// 确定按钮：将修改后的数据传回父组件
const handleConfirm = () => {
    if (props.opear === '0') {
        // 新增：直接赋值
        content.value = { ...form }
    } else {
        // 编辑：修改属性
        Object.assign(content.value, form)

        form.coverUrl = '',
            form.name = '',
            form.category = '',
            form.clickCount = 0,
            form.publishTime = '',
            form.statusText = '',
            form.actor = '',
            form.description = '',
            form.content = ''

    }
    dialogFormVisible.value = false
}

const handleCancel = (formEl: FormInstance | undefined) => {
    dialogFormVisible.value = false
    if (!formEl) return
    formEl.resetFields()

}
// const rules = reactive({
//     username: [
//         { required: true, message: '输入用户名', trigger: 'blur' }
//     ],
//     nickname: [
//         { required: true, message: '输入昵称', trigger: 'blur' }
//     ],
//     phone: [
//         { required: true, message: '输入电话', trigger: 'blur' },
//         { min: 11, max: 11, message: '电话号码11位' }
//     ],
//     role: [
//         { required: true, message: '请选择角色', trigger: 'change' }
//     ],
//     status: [
//         { required: true, message: '请选择状态', trigger: 'change' }
//     ]
// })
</script>

<template>
    <el-dialog v-model="dialogFormVisible" :show-close="false" :title="props.title" width="500">
        <el-form :model="form" ref="formRef">
            <el-form-item v-if="form.publishTime" label="创建时间" :label-width="formLabelWidth" prop="publishTime">
                <el-input v-model="form.publishTime" autocomplete="off" disabled />
            </el-form-item>
            <el-form-item label="名称" :label-width="formLabelWidth" prop="name">
                <el-input v-model="form.name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="演员" :label-width="formLabelWidth" prop="actor">
                <el-input v-model="form.actor" autocomplete="off" />
            </el-form-item>
            <el-form-item label="描述" :label-width="formLabelWidth" prop="description">
                <el-input v-model="form.description" autocomplete="off" />
            </el-form-item>
            <el-form-item label="内容" :label-width="formLabelWidth" prop="content">
                <el-input v-model="form.content" autocomplete="off" />
            </el-form-item>
            <el-form-item label="分类" :label-width="formLabelWidth" prop="category">
                <el-select v-model="form.category" placeholder="请选择分类">

                    <el-option label="传统剧目" value="传统剧目">
                        <div class="option-item">
                            <span>传统剧目</span>
                        </div>
                    </el-option>
                    <el-option label="现代改编" value="现代改编">
                        <div class="option-item">

                            <span>现代改编</span>
                        </div>
                    </el-option>
                    <el-option label="实验秦腔" value="实验秦腔">
                        <div class="option-item">

                            <span>实验秦腔</span>
                        </div>
                    </el-option>
                    <el-option label="折子戏" value="折子戏">
                        <div class="option-item">

                            <span>折子戏</span>
                        </div>
                    </el-option>
                    <el-option label="名家专场" value="名家专场">
                        <div class="option-item">

                            <span>名家专场</span>
                        </div>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth" prop="status">
                <el-select v-model="form.statusText" placeholder="请选择状态">

                    <el-option label="草稿" value="草稿">
                        <div class="option-item">

                            <span class="status-disabled">草稿</span>
                        </div>
                    </el-option>
                    <el-option label="已发布" value="已发布">
                        <div class="option-item">

                            <span class="status-pending">已发布</span>
                        </div>
                    </el-option>
                    <el-option label="已下架" value="已下架">
                        <div class="option-item">

                            <span class="status-deleted">已下架</span>
                        </div>
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="handleCancel(formRef)">取消</el-button>
                <el-button type="primary" @click="handleConfirm">确定</el-button>
            </div>
        </template>
    </el-dialog>
</template>
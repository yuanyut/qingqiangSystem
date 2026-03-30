<script setup lang="ts">
import { ref, watch } from 'vue'

// 定义 props
const props = defineProps<{
    visible: boolean      // 控制对话框显示/隐藏
    title?: string        // 标题，可选
    comment: any          // 要编辑的评论数据
}>()

// 定义 emits
const emit = defineEmits<{
    (e: 'update:visible', value: boolean): void  // 支持 v-model:visible
    (e: 'confirm', comment: any): void            // 确认事件，返回编辑后的评论
}>()

// 内部编辑数据
const editComment = ref<any>(null)

// 监听 comment 变化，初始化编辑数据
watch(() => props.comment, (newComment) => {
    if (newComment) {
        editComment.value = { ...newComment }  // 深拷贝，避免直接修改原数据
    }
}, { immediate: true })

// 关闭对话框
const handleClose = () => {
    emit('update:visible', false)
}

// 确认提交
const handleConfirm = () => {
    emit('confirm', editComment.value)
    handleClose()
}
</script>

<template>
    <el-dialog 
        :title="title || '编辑评论'" 
        width="30%"
        :model-value="visible"
        @update:model-value="emit('update:visible', $event)"
    >
        <div>
           
            <!-- 这里可以添加评论编辑表单 -->
            <el-input 
                v-model="editComment.content" 
                type="textarea"
                rows="4"
                placeholder="请输入评论内容"
            />
        </div>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="handleClose">取消</el-button>
                <el-button type="primary" @click="handleConfirm">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<style lang="css" scoped></style>
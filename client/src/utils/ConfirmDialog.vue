<script setup lang="ts">
defineProps<{
    visible: boolean
    title: string
    message: string
}>()

const emit = defineEmits<{
    (e: 'update:visible', value: boolean): void
    (e: 'confirm'): void
}>()

const handleClose = () => {
    emit('update:visible', false)
}

const handleConfirm = () => {
    emit('confirm')
    handleClose()
}
</script>

<template>
    <el-dialog
        :title="title"
        width="30%"
        :model-value="visible"
        @update:model-value="emit('update:visible', $event)"
    >
        <span>{{ message }}</span>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="handleClose">取消</el-button>
                <el-button type="primary" @click="handleConfirm">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>
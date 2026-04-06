<script setup lang="ts">
import { ref, watch } from 'vue'

const dialogVisible = defineModel<boolean>({ default: false })
const emit = defineEmits(['submitFun', 'cancleFun'])
const handleClose = (done: () => void) => {
    done()
}
const submit = () => {
    dialogVisible.value = false
    emit('submitFun')
}
const cancel = () => {
    dialogVisible.value = false
}
</script>
<template>
    <div>
        <el-dialog v-model="dialogVisible" title="Tips" width="500" :before-close="handleClose">
            <span>This is a message</span>
            <template #footer>
                <div class="dialog-footer">

                    <el-button type="primary" @click="submit">
                        确定
                    </el-button>
                    <el-button @click="cancel">取消</el-button>
                </div>
            </template>
        </el-dialog>
    </div>

</template>
<style lang="css" scoped></style>
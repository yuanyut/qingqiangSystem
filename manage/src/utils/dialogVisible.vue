<script setup lang="ts">
import { ref, watch } from 'vue'

const props = defineProps<{
    status: boolean
}>()
const emit = defineEmits(['submitFun'])
watch(() => { props.status }, () => {
    dialogVisible.value = props.status
}, { deep: true })
const dialogVisible = ref(false)
dialogVisible.value = props.status
console.log('666', dialogVisible.value)
const handleClose = (done: () => void) => {
    done()
}
const submit = () => {
    dialogVisible.value = false
    emit('submitFun')
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
                    <el-button @click="dialogVisible = false">取消</el-button>
                </div>
            </template>
        </el-dialog>
    </div>

</template>
<style lang="css" scoped></style>
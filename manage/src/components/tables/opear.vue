<script setup lang="ts">
import edit from '@/utils/edit.vue'
import { reactive, ref, watch } from 'vue';
interface FormItemUser {
    username: string
    nickname: string
    phone: string
    role: string[]
    status: string
    createdAt?: string
}
// const now = new Date()
const editContent = defineModel<any>('editContent')

console.log('666666', editContent.value)
const editModul = ref(false)
const addUser = () => {
    console.log('addUser 被调用')
    editContent.value = {
        createdAt: '',
        username: '',
        nickname: '',
        phone: '',
        role: [],
        status: ''
    }
    editModul.value = true
}
watch(editContent, (newVal) => {
    console.log('content 变化:', newVal)
}, { deep: true, immediate: true })

// 再加一个
console.log('初始化时 content.value:', editContent.value)
</script>
<template>
    <div>
        <div>
            <div @click="addUser">增加用户</div>
            <div>批量删除</div>
        </div>
        <edit v-model:dialogFormVisible="editModul" v-model:content="editContent" title="增加" opear="0"></edit>
    </div>

</template>
<style lang="css" scoped></style>
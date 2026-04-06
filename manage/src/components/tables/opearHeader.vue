<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance } from 'element-plus'

const formRef = ref<FormInstance>()
const form = reactive({
    name: '',
    juese: '',
    phone: '',
    status: '',
})

const onSubmit = () => {
    console.log('submit!')
}
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}
</script>
<template>
    <div>
        <el-form :model="form" label-width="auto" ref="formRef" style="max-width: 600px">
            <el-form-item label="用户名" prop="name">
                <el-input v-model="form.name" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
                <el-input v-model="form.phone" />
            </el-form-item>
            <el-form-item label="角色" prop="juese">
                <el-select v-model="form.juese" placeholder="请选择角色">
                    <el-option label="超级管理员" value="0" />
                    <el-option label="内容管理员" value="1" />
                    <el-option label="数据分析员" value="2" />
                    <el-option label="互动审核员" value="3" />
                    <el-option label="普通用户" value="4" />
                    <el-option label="访客" value="5" />
                </el-select>
            </el-form-item>
            <el-form-item label="状态" props="status">
                <el-select v-model="form.status" placeholder="请选择状态">
                    <el-option label="启用" value="0" />
                    <el-option label="禁用" value="1" />
                    <el-option label="待审核" value="2" />
                    <el-option label="已注销" value="3" />
                </el-select>
            </el-form-item>
            <el-form-item>

                <el-button @click="resetForm(formRef)">重置</el-button>
                <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
        </el-form>
    </div>

</template>


<style lang="css" scoped></style>
<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'


const dialogFormVisible = defineModel('dialogFormVisible')
const content: any = defineModel('content')
const formLabelWidth = '140px'

const form = reactive({
    name: '',
    region: '',
    date1: '',
    date2: '',
    delivery: false,
    type: [],
    resource: '',
    desc: '',
})
watch(content, (newVal) => {
    console.log('子组件收到 content:', newVal)
}, { deep: true, immediate: true })

</script>
<template>
    <!-- <div class="flex flex-wrap gap-1">
    <el-button class="!ml-0" plain @click="dialogTableVisible = true">
      Open a Table nested Dialog
    </el-button>

    <el-button class="!ml-0" plain @click="dialogFormVisible = true">
      Open a Form nested Dialog
    </el-button>
  </div> -->
    <el-dialog v-model="dialogFormVisible" title="编辑" width="500">
        <el-form :model="content">
            <el-form-item label="用户名" :label-width="formLabelWidth">
                <el-input v-model="content.username" autocomplete="off" />
            </el-form-item>
            <el-form-item label="昵称" :label-width="formLabelWidth">
                <el-input v-model="content.nickname" autocomplete="off" />
            </el-form-item>
            <el-form-item label="电话" :label-width="formLabelWidth">
                <el-input v-model="content.phone" autocomplete="off" />
            </el-form-item>
            <el-form-item label="角色" :label-width="formLabelWidth">
                <el-select v-model="content.role" placeholder="请选择角色" multiple>
                    <el-option label="超级管理员" value="超级管理员" />
                    <el-option label="内容管理员" value="内容管理员" />
                    <el-option label="数据分析员" value="数据分析员" />
                    <el-option label="互动审核员" value="互动审核员" />
                    <el-option label="普通用户" value="普通用户" />
                    <el-option label="访客" value="访客" />
                </el-select>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth">
                <el-select v-model="content.status" placeholder="请选择状态">
                    <el-option label="启用" value="启用" />
                    <el-option label="禁用" value="禁用" />
                    <el-option label="待审核" value="待审核" />
                    <el-option label="已注销" value="已注销" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

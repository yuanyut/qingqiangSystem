<script setup lang="ts">
import { reactive, ref } from 'vue'

import type { FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  username: string
  password: string
  repassword:string
  newpassword: string
}

const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  username: 'Hello',
  password: '',
  repassword:'',
  newpassword:''
})
const checkRepassword = (rule: any, value: any, callback: any)=>{
  console.log(value);
  console.log(ruleForm.newpassword);
  if(value ===''||value.trim()===''){
    callback(new Error('请确认密码'));
    return;
  }
  else if(value!==ruleForm.newpassword){
    callback(new Error('确认密码需与新密码一致'));
    return;
  }
  else 
    callback();
}
const rules = reactive<FormRules<RuleForm>>({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
  ],
  password:[
     { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 8, max: 12, message: '长度是8-12', trigger: 'blur' }
  ],
  repassword:[
  {validator:checkRepassword, trigger: 'blur',required:true },
  ],
  newpassword:[
     { required: true, message: '请输入新密码', trigger: 'blur' },
      { min: 8, max: 12, message: '长度是8-12', trigger: 'blur' }
  ]
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
      console.log(ruleForm)
    } else {
      console.log('error submit!', fields)
    }
  })
}
const tabs=reactive(['first', 'second', 'third'])
</script>

<template>
  <template style="display:flex;">
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="ruleForm"
    :rules="rules"
    label-width="auto"
  >
    <el-form-item label="账号" prop="username">
      <el-input v-model="ruleForm.username" />
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="ruleForm.password" />
    </el-form-item>
    <el-form-item label="新密码" prop="newpassword">
      <el-input v-model="ruleForm.newpassword" />
    </el-form-item>
    <el-form-item label="再次确认密码" prop="repassword">
      <el-input v-model="ruleForm.repassword" />
    </el-form-item>
   
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">
        登录
      </el-button>
      
    </el-form-item>
  </el-form>
  
    <el-form
      ref="ruleFormRef"
      style="max-width: 600px"
      :model="ruleForm"
      :rules="rules"
      label-width="auto"
    >
      <el-form-item label="账号" prop="username">
        <el-input v-model="ruleForm.username" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="ruleForm.password" />
      </el-form-item>
      <el-form-item label="新密码" prop="newpassword">
        <el-input v-model="ruleForm.newpassword" />
      </el-form-item>
      <el-form-item label="再次确认密码" prop="repassword">
        <el-input v-model="ruleForm.repassword" />
      </el-form-item>
    
      <el-form-item>
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          注册
        </el-button>
        
      </el-form-item>
    </el-form>
  
  </template>
</template>
<style scoped>
  
</style>

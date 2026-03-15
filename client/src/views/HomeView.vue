<script setup lang="ts">
import { reactive, ref } from 'vue'
import router from '@/router/index'
import type { FormInstance, FormRules } from 'element-plus'
//如果没有，ruleForm可以随意添加字段，容易出错,纸本身不会占用空间，只是描述
interface RuleForm {
  username: string    // 用户名必须是字符串
  password?: string    // 密码必须是字符串  
  repassword?: string  // 确认密码必须是字符串
  newpassword?: string // 新密码必须是字符串
  nickname?:string
  address?:string
  avatar?:string
  sex?: number              // 添加可选属性
  age?: string      // 添加可选属性
  oldpassword?: string // 添加可选属性
}
//创建一个空的引用，稍后会指向表单组件, 告诉 TypeScript：这个引用将来会指向一个 Element Plus 的表单实例
const loginFormRef = ref<FormInstance>()
const registerFormRef =ref<FormInstance>()
const findFormRef =ref<FormInstance>()
//创建登录表单的数据对象,虽然RuleForm想了很多个，但是就用这两个
const ruleFormLogin = reactive<RuleForm>({
  username: '',
  password: ''
})
const ruleFormRegister = reactive<RuleForm>({
  username: '',
  nickname:'',
  password: '',
  sex:2,
  age:'',
  avatar:'',
  address:''
})
const ruleFormFind =reactive<RuleForm>({
  username: '',
  newpassword:'',
  repassword:'',
  oldpassword:''
})
const checkRepassword = (rule: any, value: any, callback: any)=>{
  console.log(value);
  console.log(ruleFormFind.newpassword);
  if(value ===''||value.trim()===''){
    callback(new Error('确认密码不能为空'));
    return;
  }
  else if(value!==ruleFormFind.newpassword){
    callback(new Error('确认密码需与新密码一致'));
    return;
  }
  else 
    callback();
}
//FormRules 只能传类型，不能传变量
//表单ruleFormLogin，FormRules是 Element Plus 提供的通用类型，意思是："我要为 ruleFormLogin 这个表单创建验证规则"，由于ruleFormLogin 是一个变量，不是类型，用 typeof 获取变量的类型，然后传给 FormRules
const rules1 = reactive<FormRules<typeof ruleFormLogin>>({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
  ],
  password:[
     { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 8, max: 12, message: '长度是8-12', trigger: 'blur' }
  ]
})
const rules2 = reactive<FormRules<typeof ruleFormRegister>>({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
  ],
  password:[
     { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 8, max: 12, message: '长度是8-12', trigger: 'blur' }
  ],
  nickname:[
  { required: true, message: '请输入昵称', trigger: 'blur' },
  { min: 2, max: 8, message: '长度是2-8', trigger: 'blur' }
  ],
  age:[
  { required: true, message: '请选择出生年月日', trigger: 'blur' },
  ],
  sex:[
  { required: true, message: '请选择性别', trigger: 'blur' },
  ],
  avatar:[
  { required: true, message: '请上传头像', trigger: 'blur' },
  ],
  address:[
  { required: true, message: '请选择所在地', trigger: 'blur' },
  ]
})
const rules3 = reactive<FormRules<typeof ruleFormFind>>({
  repassword:[
  {validator:checkRepassword, trigger: 'blur',required:true },
  ],
  newpassword:[
     { required: true, message: '请输入新密码', trigger: 'blur' },
      { min: 8, max: 12, message: '长度是8-12', trigger: 'blur' }
  ],
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
  ],
  oldpassword: [
    { required: true, message: '请输入旧账号', trigger: 'blur' },
  ]
 
})
//登录提交
const submitLoginForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
      console.log(ruleFormLogin)
      router.push('/user')
    } else {
      console.log('error submit!', fields)
    }
  })
}
//注册提交
const submitRegForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
      console.log(ruleFormRegister.age)
      currentTab.value='login';
      ruleFormLogin.username=ruleFormRegister.username;
      formEl.resetFields();
    } else {
      console.log('error submit!', fields)
    }
  })
  
}
//改密码
const submitFindForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
      console.log(ruleFormFind)
    } else {
      console.log('error submit!', fields)
    }
  })
}
const tabs=reactive(['login', 'register', 'findPassword'])
const currentTab =ref('login')
const exchangeTabs=(tab:any)=>{
  if(tab !== currentTab.value){
    currentTab.value=tab
  }
}
</script>

<template>
  <header style="max-width:200px">
    <h2>
      <template v-show="currentTab === 'login'">
        登陆
      </template>
      <template v-show="currentTab === 'register'">
        注册
      </template>
      <template v-show="currentTab === 'findPassword'">
        找回账号
      </template>
    </h2>
  </header>
  <el-form
    ref="loginFormRef"
    style="max-width: 600px;"
    :model="ruleFormLogin"
    :rules="rules1"
    label-width="auto"
    v-show="currentTab === 'login'"
  >
    <el-form-item label="账号" prop="username">
      <el-input v-model="ruleFormLogin.username" />
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="ruleFormLogin.password" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitLoginForm(loginFormRef)">
        登录
      </el-button>
      <el-button type="primary" @click="exchangeTabs('register')">
        注册
      </el-button>
    </el-form-item>
    <el-form-item @click="exchangeTabs('findPassword')">
        忘记密码？？？
      </el-form-item>
  </el-form>
  
    <el-form
      ref="registerFormRef"
      style="max-width: 600px;"
      :model="ruleFormRegister"
      :rules="rules2"
      label-width="auto"
       v-show="currentTab === 'register'"
    >
      <el-form-item label="账号" prop="username">
        <el-input v-model="ruleFormRegister.username" />
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="ruleFormRegister.nickname" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="ruleFormRegister.password" />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="ruleFormRegister.sex" placeholder="请选择性别">
          
          <el-option label="女" :value="0" /><el-option label="男" :value=1 /><el-option label="不愿透露" :value="2" /><!-- 使用 :value 绑定数字 -->
        </el-select>
      </el-form-item>

      <el-form-item label="出生日期" prop="age">
        <!-- <el-input v-model="ruleFormRegister.age" type="number" /> -->
        <el-date-picker
        v-model="ruleFormRegister.age"
        type="date"
        placeholder="选择出生日期"
      />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitRegForm(registerFormRef)">
          注册
        </el-button>
        <el-button type="primary" @click="exchangeTabs('login')">
          返回登陆
        </el-button>
      </el-form-item>
      
    </el-form>
    <el-form
    ref="findFormRef"
    style="max-width: 600px;"
    :model="ruleFormFind"
    :rules="rules3"
    label-width="auto"
    v-show="currentTab === 'findPassword'"
  >
    <el-form-item label="账号" prop="username">
      <el-input v-model="ruleFormFind.username" />
    </el-form-item>
    <el-form-item label="旧密码" prop="oldpassword">
      <el-input v-model="ruleFormFind.oldpassword" />
    </el-form-item>
    <el-form-item label="新密码" prop="newpassword">
      <el-input v-model="ruleFormFind.newpassword" />
    </el-form-item>
    <el-form-item label="再次确认密码" prop="repassword">
      <el-input v-model="ruleFormFind.repassword" />
    </el-form-item>
    
    <el-form-item>
      <el-button type="primary" @click="submitFindForm(findFormRef)">
          提交
        </el-button>
      <el-button type="primary" @click="exchangeTabs('login')">
        返回登录
      </el-button>
    </el-form-item>
  </el-form>
</template>
<style scoped>
  .active{
    display:block;
  }
</style>

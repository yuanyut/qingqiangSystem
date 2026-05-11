<script setup lang="ts">
import { reactive, ref } from 'vue'
import router from '@/router/index'
import { useUserInfoStore } from '@/stores/userInfo'
import { ElMessage } from 'element-plus'
import { login, register } from '@/api/user'
const userInfo=useUserInfoStore()
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
      { min: 6, max: 12, message: '长度是6-12', trigger: 'blur' }
  ]
})
const rules2 = reactive<FormRules<typeof ruleFormRegister>>({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
  ],
  password:[
     { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 12, message: '长度是6-12', trigger: 'blur' }
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
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      try {
        const res = await login({
          username: ruleFormLogin.username,
          password: ruleFormLogin.password || ''
        })
        if (res.code === 200) {
          localStorage.setItem('token', res.data)
          ElMessage.success('登录成功')
          // 验证token并获取完整用户信息
          await userInfo.validateToken()
          router.push('/drama')
        } else {
          ElMessage.error(res.message || '登录失败')
        }
      } catch (error) {
        ElMessage.error('登录失败，请检查网络连接')
        console.error('Login error:', error)
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}
//注册提交
const submitRegForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      try {
        const res = await register({
          username: ruleFormRegister.username,
          password: ruleFormRegister.password || '',
          nickname: ruleFormRegister.nickname || '',
          sex: ruleFormRegister.sex?.toString() || '2',
          birthday: ruleFormRegister.age || ''
        })
        if (res.code === 200) {
          ElMessage.success('注册成功，请登录')
          currentTab.value = 'login'
          ruleFormLogin.username = ruleFormRegister.username
          formEl.resetFields()
        } else {
          ElMessage.error(res.message || '注册失败')
        }
      } catch (error) {
        ElMessage.error('注册失败，请检查网络连接')
        console.error('Register error:', error)
      }
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
  <div class="auth-container">
    <!-- 左侧装饰区域 -->
    <div class="auth-decoration">
      <div class="decoration-content">
        <div class="logo-wrapper">
          <div class="logo-icon">🎭</div>
          <h1 class="logo-text">秦韵雅集</h1>
        </div>
        <p class="decoration-slogan">传承经典 · 品味秦腔</p>
        <div class="decoration-quote">
          <span class="quote-mark">“</span>
          <p>秦腔一声吼，千古风流尽在喉</p>
          <span class="quote-mark-right">”</span>
        </div>
        <div class="decoration-features">
          <div class="feature-item">
            <span class="feature-icon">🎵</span>
            <span>经典剧目</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">👤</span>
            <span>名家荟萃</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">📰</span>
            <span>资讯动态</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 右侧表单区域 -->
    <div class="auth-form-container">
      <div class="form-card">
        <!-- 头部标题区域 -->
        <div class="form-header">
          <div class="tab-buttons">
            <button
              class="tab-btn"
              :class="{ active: currentTab === 'login' }"
              @click="exchangeTabs('login')"
            >
              登录
            </button>
            <button
              class="tab-btn"
              :class="{ active: currentTab === 'register' }"
              @click="exchangeTabs('register')"
            >
              注册
            </button>
            <!-- <button
              class="tab-btn"
              :class="{ active: currentTab === 'findPassword' }"
              @click="exchangeTabs('findPassword')"
            >
              找回密码
            </button> -->
          </div>
          <div class="header-subtitle">
            <template v-if="currentTab === 'login'">欢迎回来，请登录您的账号</template>
            <template v-else-if="currentTab === 'register'">加入我们，开启戏曲之旅</template>
            <template v-else>重置您的密码，安全找回账号</template>
          </div>
        </div>

        <!-- 登录表单 -->
        <el-form
          ref="loginFormRef"
          class="auth-form"
          :model="ruleFormLogin"
          :rules="rules1"
          label-width="0"
          v-show="currentTab === 'login'"
        >
          <!-- 账号 -->
          <el-form-item prop="username">
            <div class="form-field">
              <div class="field-icon">
                <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                  <circle cx="12" cy="7" r="4"></circle>
                </svg>
              </div>
              <el-input
                v-model="ruleFormLogin.username"
                placeholder="请输入账号"
                class="custom-input"
              />
            </div>
          </el-form-item>

          <!-- 密码 -->
          <el-form-item prop="password">
            <div class="form-field">
              <div class="field-icon">
                <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                  <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                </svg>
              </div>
              <el-input
                v-model="ruleFormLogin.password"
                type="password"
                placeholder="请输入密码"
                class="custom-input"
                show-password
              />
            </div>
          </el-form-item>

          <div class="form-options">
            <div class="remember-me">
              <el-checkbox>记住密码</el-checkbox>
            </div>
            <div class="forgot-password" @click="exchangeTabs('findPassword')">
              忘记密码？
            </div>
          </div>

          <el-form-item class="submit-item">
            <el-button type="primary" class="submit-btn" @click="submitLoginForm(loginFormRef)">
              登录
            </el-button>
          </el-form-item>

          <div class="form-footer">
            <span>还没有账号？</span>
            <span class="footer-link" @click="exchangeTabs('register')">立即注册</span>
          </div>
        </el-form>

        <!-- 注册表单 -->
        <el-form
          ref="registerFormRef"
          class="auth-form"
          :model="ruleFormRegister"
          :rules="rules2"
          label-width="0"
          v-show="currentTab === 'register'"
        >
          <!-- 账号 -->
          <el-form-item prop="username">
            <div class="form-field">
              <div class="field-icon">
                <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                  <circle cx="12" cy="7" r="4"></circle>
                </svg>
              </div>
              <el-input v-model="ruleFormRegister.username" placeholder="请输入账号" class="custom-input" />
            </div>
          </el-form-item>

          <!-- 昵称 -->
          <el-form-item prop="nickname">
            <div class="form-field">
              <div class="field-icon">
                <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                  <circle cx="12" cy="7" r="4"></circle>
                </svg>
              </div>
              <el-input v-model="ruleFormRegister.nickname" placeholder="请输入昵称" class="custom-input" />
            </div>
          </el-form-item>

          <!-- 密码 -->
          <el-form-item prop="password">
            <div class="form-field">
              <div class="field-icon">
                <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                  <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                </svg>
              </div>
              <el-input
                v-model="ruleFormRegister.password"
                type="password"
                placeholder="请输入密码"
                class="custom-input"
                show-password
              />
            </div>
          </el-form-item>

          <div class="form-field-row">
            <!-- 性别 -->
            <el-form-item prop="sex" class="half">
              <el-select v-model="ruleFormRegister.sex" placeholder="性别" class="custom-select">
                <el-option label="女" :value="0" />
                <el-option label="男" :value="1" />
                <el-option label="不愿透露" :value="2" />
              </el-select>
            </el-form-item>
            <!-- 年龄 -->
            <el-form-item prop="age" class="half">
              <el-date-picker
                v-model="ruleFormRegister.age"
                type="date"
                placeholder="出生日期"
                class="custom-date"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </div>

          <el-form-item class="submit-item">
            <el-button type="primary" class="submit-btn" @click="submitRegForm(registerFormRef)">
              注册
            </el-button>
          </el-form-item>

          <div class="form-footer">
            <span>已有账号？</span>
            <span class="footer-link" @click="exchangeTabs('login')">返回登录</span>
          </div>
        </el-form>

        <!-- 找回密码表单 -->
        <el-form
          ref="findFormRef"
          class="auth-form"
          :model="ruleFormFind"
          :rules="rules3"
          label-width="0"
          v-show="currentTab === 'findPassword'"
        >
          <!-- 账号 -->
          <el-form-item prop="username">
            <div class="form-field">
              <div class="field-icon">
                <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                  <circle cx="12" cy="7" r="4"></circle>
                </svg>
              </div>
              <el-input v-model="ruleFormFind.username" placeholder="请输入账号" class="custom-input" />
            </div>
          </el-form-item>

          <!-- 旧密码 -->
          <el-form-item prop="oldpassword">
            <div class="form-field">
              <div class="field-icon">
                <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                  <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                </svg>
              </div>
              <el-input
                v-model="ruleFormFind.oldpassword"
                type="password"
                placeholder="请输入旧密码"
                class="custom-input"
                show-password
              />
            </div>
          </el-form-item>

          <!-- 新密码 -->
          <el-form-item prop="newpassword">
            <div class="form-field">
              <div class="field-icon">
                <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                  <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                </svg>
              </div>
              <el-input
                v-model="ruleFormFind.newpassword"
                type="password"
                placeholder="请输入新密码"
                class="custom-input"
                show-password
              />
            </div>
          </el-form-item>

          <!-- 确认密码 -->
          <el-form-item prop="repassword">
            <div class="form-field">
              <div class="field-icon">
                <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                  <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                </svg>
              </div>
              <el-input
                v-model="ruleFormFind.repassword"
                type="password"
                placeholder="请再次确认密码"
                class="custom-input"
                show-password
              />
            </div>
          </el-form-item>

          <el-form-item class="submit-item">
            <el-button type="primary" class="submit-btn" @click="submitFindForm(findFormRef)">
              确认修改
            </el-button>
          </el-form-item>

          <div class="form-footer">
            <span>想起密码了？</span>
            <span class="footer-link" @click="exchangeTabs('login')">返回登录</span>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.auth-container {
  display: flex;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

/* 左侧装饰区域 */
.auth-decoration {
  flex: 1;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.95) 0%, rgba(118, 75, 162, 0.95) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.auth-decoration::before {
  content: '';
  position: absolute;
  width: 200%;
  height: 200%;
  background: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" opacity="0.1"><path fill="white" d="M20,30 L30,20 L40,30 L30,40 Z M60,70 L70,60 L80,70 L70,80 Z M40,80 L45,75 L50,80 L45,85 Z M10,10 L15,5 L20,10 L15,15 Z"/></svg>') repeat;
  opacity: 0.1;
  animation: float 20s linear infinite;
}

@keyframes float {
  0% { transform: translate(0, 0) rotate(0deg); }
  100% { transform: translate(-10%, -10%) rotate(360deg); }
}

.decoration-content {
  text-align: center;
  color: white;
  z-index: 1;
  padding: 40px;
}

.logo-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  margin-bottom: 24px;
}

.logo-icon {
  font-size: 48px;
  filter: drop-shadow(0 4px 8px rgba(0,0,0,0.2));
}

.logo-text {
  font-size: 32px;
  font-weight: 700;
  letter-spacing: 2px;
  margin: 0;
}

.decoration-slogan {
  font-size: 18px;
  opacity: 0.9;
  margin-bottom: 48px;
}

.decoration-quote {
  background: rgba(255,255,255,0.1);
  border-radius: 24px;
  padding: 24px;
  margin: 32px 0;
  position: relative;
}

.quote-mark {
  font-size: 48px;
  opacity: 0.5;
  position: absolute;
  top: -10px;
  left: 10px;
}

.quote-mark-right {
  font-size: 48px;
  opacity: 0.5;
  position: absolute;
  bottom: -30px;
  right: 10px;
}

.decoration-quote p {
  font-size: 20px;
  font-style: italic;
  margin: 0;
  line-height: 1.5;
}

.decoration-features {
  display: flex;
  justify-content: center;
  gap: 32px;
  margin-top: 48px;
}

.feature-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  opacity: 0.8;
}

.feature-icon {
  font-size: 24px;
}

/* 右侧表单区域 */
.auth-form-container {
  width: 520px;
  background: white;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: -10px 0 30px rgba(0,0,0,0.1);
}

.form-card {
  width: 100%;
  max-width: 400px;
  padding: 48px 32px;
}

.form-header {
  text-align: center;
  margin-bottom: 40px;
}

.tab-buttons {
  display: flex;
  gap: 8px;
  background: #f5f7fa;
  padding: 4px;
  border-radius: 40px;
  margin-bottom: 20px;
}

.tab-btn {
  flex: 1;
  padding: 10px 20px;
  border: none;
  background: transparent;
  border-radius: 36px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #64748b;
}

.tab-btn.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 12px rgba(102,126,234,0.3);
}

.header-subtitle {
  font-size: 14px;
  color: #94a3b8;
}

/* 表单样式 */
.auth-form {
  margin-top: 8px;
}

.form-field {
  position: relative;
  margin-bottom: 20px;
  width: 100%;
}

.field-icon {
  position: absolute;
  left: 14px;
  top: 50%;
  transform: translateY(-50%);
  z-index: 1;
  color: #94a3b8;
}

.field-icon .icon {
  width: 18px;
  height: 18px;
}

.custom-input :deep(.el-input__wrapper) {
  padding-left: 44px;
  border-radius: 12px;
  border: 1px solid #e2e8f0;
  transition: all 0.3s ease;
  box-shadow: none;
}

.custom-input :deep(.el-input__wrapper:hover) {
  border-color: #667eea;
}

.custom-input :deep(.el-input__wrapper.is-focus) {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102,126,234,0.1);
}

.form-field-row {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}

.form-field.half {
  flex: 1;
  margin-bottom: 0;
}

.custom-select {
  width: 100%;
}

.custom-select :deep(.el-input__wrapper) {
  border-radius: 12px;
  border: 1px solid #e2e8f0;
}

.custom-date {
  width: 100%;
}

.custom-date :deep(.el-input__wrapper) {
  border-radius: 12px;
  border: 1px solid #e2e8f0;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 28px;
  font-size: 14px;
}

.forgot-password {
  color: #667eea;
  cursor: pointer;
  transition: color 0.2s;
}

.forgot-password:hover {
  color: #764ba2;
}

.submit-item {
  margin-bottom: 20px;
}

.submit-btn {
  width: 100%;
  padding: 14px;
  border-radius: 40px;
  font-size: 16px;
  font-weight: 600;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  transition: all 0.3s ease;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(102,126,234,0.4);
}

.form-footer {
  text-align: center;
  font-size: 14px;
  color: #64748b;
}

.footer-link {
  color: #667eea;
  cursor: pointer;
  font-weight: 500;
  margin-left: 8px;
  transition: color 0.2s;
}

.footer-link:hover {
  color: #764ba2;
}

/* 响应式 */
@media (max-width: 768px) {
  .auth-container {
    flex-direction: column;
  }

  .auth-decoration {
    padding: 40px 20px;
    min-height: 280px;
  }

  .auth-form-container {
    width: 100%;
  }

  .form-card {
    padding: 32px 24px;
  }

  .decoration-quote p {
    font-size: 16px;
  }

  .decoration-features {
    gap: 20px;
  }
}
</style>

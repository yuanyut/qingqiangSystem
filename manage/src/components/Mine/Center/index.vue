<template>
  <div class="account-settings">
    <!-- 头部 -->
    <div class="settings-header">
      
      <button class="save-all-btn" @click="handleSaveAll">保存全部设置</button>
    </div>

    <!-- 基本信息 -->
    <div class="settings-section">
      <div class="section-title">
        
        基本信息
      </div>
      <div class="section-content">
        <!-- 头像 -->
        <div class="setting-item avatar-item">
          <div class="setting-label">
            <span class="label-text">头像</span>
          </div>
          <div class="setting-value">
            <div class="avatar-wrapper">
              <el-upload class="avatar-uploader" :action="''"
                :show-file-list="false" :on-change="handleAvatarChange" :before-upload="beforeAvatarUpload" :auto-upload="false">
                <img v-if="imageUrl" :src="imageUrl" class="avatar-img" />
                <el-icon v-else class="avatar-uploader-icon">
                  <Plus />
                </el-icon>
              </el-upload>
            </div>
          </div>
        </div>

        <!-- 昵称 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">昵称</span>
          </div>
          <div class="setting-value">
            <span class="value-text">{{ userProfile.nickname || '未设置' }}</span>
            <button class="edit-btn" @click="openEditDialog('nickname', userProfile.nickname)">修改</button>
          </div>
        </div>

        <!-- 简介 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">简介</span>
          </div>
          <div class="setting-value">
            <span class="value-text bio-text">{{ userProfile.bio || '暂无简介' }}</span>
            <button class="edit-btn" @click="openEditDialog('bio', userProfile.bio)">修改</button>
          </div>
        </div>

        <!-- 地区 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">地区</span>
          </div>
          <div class="setting-value">
            <span class="value-text">{{ userProfile.region || '未设置' }}</span>
            <button class="edit-btn" @click="openEditDialog('region', userProfile.region)">修改</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 安全设置 -->
    <div class="settings-section">
      <div class="section-title">
       
        安全设置
      </div>
      <div class="section-content">
        <!-- 手机号 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">手机号</span>
          </div>
          <div class="setting-value">
            <span class="value-text">{{ userProfile.phone || '未绑定' }}</span>
            <button class="edit-btn" @click="openEditDialog('phone', userProfile.phone)">更换</button>
          </div>
        </div>

        <!-- 邮箱 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">邮箱</span>
          </div>
          <div class="setting-value">
            <span class="value-text">{{ userProfile.email || '未绑定' }}</span>
            <button class="edit-btn" @click="openEditDialog('email', userProfile.email)">更换</button>
          </div>
        </div>

        <!-- 密码 -->
        <div class="setting-item">
          <div class="setting-label">
            <span class="label-text">密码</span>
          </div>
          <div class="setting-value">
            <span class="value-text password-placeholder">········</span>
            <button class="edit-btn" @click="openEditDialog('password', '')">修改密码</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 编辑弹窗 -->
    <el-dialog v-model="editDialogVisible" :title="editDialogTitle" width="500px" :close-on-click-modal="false">
      <el-form :model="editFormData" :rules="editRules" ref="editFormRef" label-width="80px">
        <!-- 地区选择使用级联选择器 -->
        <el-form-item v-if="editField === 'region'" label="地区" prop="regionValue">
          <el-cascader
            v-model="editFormData.regionValue"
            :options="regionOptions"
            :props="cascaderProps"
            placeholder="请选择省/市/区"
            clearable
            style="width: 100%"
          />
        </el-form-item>
        
        <!-- 简介使用文本域 -->
        <el-form-item v-else-if="editField === 'bio'" :label="editFieldLabel" prop="value">
          <el-input 
            v-model="editFormData.value" 
            type="textarea"
            :placeholder="`请输入${editFieldLabel}`"
            :maxlength="editFieldMaxLength"
            :rows="3"
            show-word-limit
          />
        </el-form-item>
        
        <!-- 密码使用密码框 -->
        <el-form-item v-else-if="editField === 'password'" :label="editFieldLabel" prop="value">
          <el-input 
            v-model="editFormData.value" 
            type="password"
            :placeholder="`请输入${editFieldLabel}`"
            :maxlength="editFieldMaxLength"
            show-password
            show-word-limit
          />
        </el-form-item>
        
        <!-- 其他字段使用普通输入框 -->
        <el-form-item v-else :label="editFieldLabel" prop="value">
          <el-input 
            v-model="editFormData.value" 
            :placeholder="`请输入${editFieldLabel}`"
            :maxlength="editFieldMaxLength"
            show-word-limit
          />
        </el-form-item>
        
        <!-- 验证码区域（手机号/邮箱专用） -->
        <el-form-item v-if="editField === 'phone' || editField === 'email'" label="验证码" prop="code">
          <div style="display: flex; gap: 12px;">
            <el-input v-model="editFormData.code" placeholder="请输入验证码" style="flex: 1;"></el-input>
            <el-button :disabled="codeSending" @click="sendCode">
              {{ codeSending ? `${countdown}秒后重试` : '获取验证码' }}
            </el-button>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleEditConfirm">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import type { UploadProps, FormInstance, FormRules } from 'element-plus'
import { pcaTextArr } from "element-china-area-data"
import { uploadAvatar } from '@/api/api'

// 用户资料数据
const userProfile = reactive({
  nickname: '山野的风',
  bio: '喜欢旅行、摄影和美食，记录生活中的美好瞬间。',
  region: '浙江省-杭州市-西湖区',
  phone: '159****1234',
  email: 'user@example.com'
})

// 隐私设置
const privacySettings = reactive({
  allowViewFavorites: true,
  allowViewHistory: false,
  allowSearchByPhone: true
})

// 通知设置
const notificationSettings = reactive({
  system: true,
  interaction: true,
  activity: false,
  email: true
})

// 地区选择相关
const regionOptions = ref(pcaTextArr)
const cascaderProps = {
  value: 'value',
  label: 'label',
  children: 'children',
  expandTrigger: 'hover' as const
}

// 头像相关
const imageUrl = ref('https://picsum.photos/200/200?random=1')

const handleAvatarChange = async (uploadFile: any) => {
  if (uploadFile.raw) {
    const formData = new FormData()
    formData.append('file', uploadFile.raw)
    
    try {
      const response = await uploadAvatar(formData)
      if (response && response.data && response.data.url) {
        imageUrl.value = response.data.url
        ElMessage.success('头像上传成功')
      } else {
        ElMessage.error('头像上传失败')
      }
    } catch (error) {
      console.error('头像上传失败:', error)
      ElMessage.error('头像上传失败，请重试')
    }
  }
}

const handleAvatarSuccess: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!)
  ElMessage.success('头像上传成功')
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('头像图片必须是 JPG 或 PNG 格式！')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('头像图片大小不能超过 2MB！')
    return false
  }
  return true
}

// 编辑弹窗相关
const editDialogVisible = ref(false)
const editFormRef = ref<FormInstance>()
const editField = ref('')
const editFormData = reactive({
  value: '',
  code: '',
  regionValue: [] as string[]  // 存储地区选择的值
})

const codeSending = ref(false)
const countdown = ref(0)
let timer: number

// 弹窗标题和字段配置
const editDialogTitle = computed(() => {
  const titles: Record<string, string> = {
    nickname: '修改昵称',
    bio: '修改简介',
    region: '修改地区',
    phone: '更换手机号',
    email: '更换邮箱',
    password: '修改密码'
  }
  return titles[editField.value] || '编辑信息'
})

const editFieldLabel = computed(() => {
  const labels: Record<string, string> = {
    nickname: '昵称',
    bio: '简介',
    region: '地区',
    phone: '手机号',
    email: '邮箱',
    password: '新密码'
  }
  return labels[editField.value] || '内容'
})

const editFieldMaxLength = computed(() => {
  const lengths: Record<string, number> = {
    nickname: 20,
    bio: 100,
    region: 50,
    phone: 11,
    email: 50,
    password: 20
  }
  return lengths[editField.value] || 50
})

// 表单验证规则
const editRules = computed<FormRules>(() => {
  const rules: FormRules = {}
  
  if (editField.value === 'region') {
    rules.regionValue = [
      { required: true, message: '请选择地区', trigger: 'change' },
      { 
        validator: (rule, value, callback) => {
          if (!value || value.length === 0) {
            callback(new Error('请选择完整的地区信息'))
          } else {
            callback()
          }
        }, 
        trigger: 'change' 
      }
    ]
  } else {
    rules.value = [
      { required: true, message: `请输入${editFieldLabel.value}`, trigger: 'blur' }
    ]
    
    if (editField.value === 'phone') {
      rules.value.push({ 
        pattern: /^1[3-9]\d{9}$/, 
        message: '请输入正确的手机号', 
        trigger: 'blur' 
      } as any)
    }
    
    if (editField.value === 'email') {
      rules.value.push({ 
        pattern: /^[^\s@]+@([^\s@]+\.)+[^\s@]+$/, 
        message: '请输入正确的邮箱地址', 
        trigger: 'blur' 
      } as any)
    }
    
    if (editField.value === 'password') {
      rules.value.push({ 
        min: 6, 
        max: 20, 
        message: '密码长度为6-20位', 
        trigger: 'blur' 
      } as any)
    }
    
    if (editField.value === 'bio') {
      rules.value.push({ 
        max: 100, 
        message: '简介不能超过100个字符', 
        trigger: 'blur' 
      } as any)
    }
  }
  
  return rules
})

// 打开编辑弹窗
const openEditDialog = (field: string, currentValue: string) => {
  editField.value = field
  editDialogVisible.value = true
  
  // 重置表单数据
  editFormData.value = ''
  editFormData.code = ''
  
  if (field === 'region') {
    // 解析当前地区值，例如 "浙江省-杭州市-西湖区" 转换为 ["浙江省", "杭州市", "西湖区"]
    if (currentValue && currentValue !== '未设置') {
      console.log('本来的地址',currentValue)
      const regionParts = currentValue.split('-')
      editFormData.regionValue = regionParts
      console.log('现在的地址',editFormData.regionValue)
    } else {
      editFormData.regionValue = []
    }
  } else {
    editFormData.value = currentValue || ''
    editFormData.regionValue = []
  }
}

// 发送验证码
const sendCode = async () => {
  if (codeSending.value) return
  
  // 验证手机号/邮箱格式
  if (editField.value === 'phone' && !/^1[3-9]\d{9}$/.test(editFormData.value)) {
    ElMessage.warning('请先输入正确的手机号')
    return
  }
  if (editField.value === 'email' && !/^[^\s@]+@([^\s@]+\.)+[^\s@]+$/.test(editFormData.value)) {
    ElMessage.warning('请先输入正确的邮箱地址')
    return
  }

  codeSending.value = true
  countdown.value = 60
  timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer!)
      codeSending.value = false
      timer = 0
    }
  }, 1000)

  // 模拟发送验证码
  ElMessage.success(`验证码已发送至${editField.value === 'phone' ? '手机' : '邮箱'}`)
  // 实际开发中调用发送验证码接口
}

// 确认编辑
const handleEditConfirm = async () => {
  if (!editFormRef.value) return
  
  await editFormRef.value.validate(async (valid) => {
    if (!valid) return

    // 手机号/邮箱需要验证码
    if ((editField.value === 'phone' || editField.value === 'email') && !editFormData.code) {
      ElMessage.warning('请输入验证码')
      return
    }

    // 模拟保存操作
    let newValue = ''
    switch (editField.value) {
      case 'nickname':
        newValue = editFormData.value
        userProfile.nickname = newValue
        ElMessage.success('昵称修改成功')
        break
      case 'bio':
        newValue = editFormData.value
        userProfile.bio = newValue
        ElMessage.success('简介修改成功')
        break
      case 'region':
        // 将选择的地区数组转换为显示格式
        if (editFormData.regionValue && editFormData.regionValue.length >= 2) {
          newValue = editFormData.regionValue.join('-')
          userProfile.region = newValue
          ElMessage.success('地区修改成功')
        } else {
          ElMessage.warning('请选择完整的地区信息（至少选择到城市）')
          return
        }
        break
      case 'phone':
        newValue = editFormData.value
        // 实际开发中调用更换手机号接口，验证验证码
        userProfile.phone = newValue.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
        ElMessage.success('手机号更换成功')
        break
      case 'email':
        newValue = editFormData.value
        userProfile.email = newValue
        ElMessage.success('邮箱更换成功')
        break
      case 'password':
        newValue = editFormData.value
        ElMessage.success('密码修改成功，请重新登录')
        // 可跳转到登录页
        break
    }
    editDialogVisible.value = false
    editFormData.code = ''
  })
}

// 保存全部设置
const handleSaveAll = async () => {
  ElMessageBox.confirm('确认保存所有设置吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'info'
  }).then(() => {
    // 实际开发中调用保存接口
    ElMessage.success('所有设置已保存')
  }).catch(() => { })
}
</script>
<style scoped>
.account-settings {
  margin: 0 auto;
  padding: 32px 28px;
  height:calc(100vh-60px) ;
  overflow-y: auto;
  border-radius: 0;
}

/* 头部样式 */
.settings-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding-bottom: 20px;
  border-bottom: 2px solid #f0e2d0;
}

.header-title {
  font-size: 28px;
  font-weight: 600;
  margin: 0;
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  display: flex;
  align-items: center;
  gap: 12px;
}

.title-icon {
  font-size: 28px;
}

.save-all-btn {
  padding: 10px 28px;
  background: linear-gradient(135deg, #b87c4e, #9b623c);
  border: none;
  border-radius: 40px;
  color: white;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(184, 124, 78, 0.2);
}

.save-all-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(184, 124, 78, 0.3);
}

.save-all-btn:active {
  transform: translateY(0);
}

/* 设置区块 */
.settings-section {
  background: white;
  border-radius: 24px;
  margin-bottom: 24px;
  overflow: hidden;
  border: 1px solid #f0e6dc;
  transition: all 0.3s ease;
}

.settings-section:hover {
  box-shadow: 0 8px 24px -12px rgba(98, 67, 41, 0.12);
  border-color: #e6d5c4;
}

.section-title {
  padding: 20px 24px;
  border-bottom: 1px solid #f0e2d0;
  font-size: 18px;
  font-weight: 600;
  color: #3a2c21;
  display: flex;
  align-items: center;
  gap: 10px;
}

.section-icon {
  font-size: 20px;
}

.section-content {
  padding: 8px 0;
}

/* 设置项 */
.setting-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 24px;
  transition: background 0.2s ease;
  border-bottom: 1px solid #f8f2ea;
}

.setting-item:last-child {
  border-bottom: none;
}

.setting-item:hover {
  background: #fefbf7;
}

.setting-label {
  flex-shrink: 0;
  width: 140px;
}

.label-text {
  font-size: 15px;
  font-weight: 500;
  color: #5e4c38;
}

.setting-value {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 16px;
}

.value-text {
  font-size: 15px;
  color: #9b8570;
  transition: color 0.2s;
}

.bio-text {
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.password-placeholder {
  letter-spacing: 2px;
  font-size: 16px;
  font-weight: 500;
  color: #b5a084;
}

/* 头像样式 */
.avatar-item .setting-value {
  justify-content: flex-start;
}

.avatar-wrapper {
  display: flex;
  align-items: center;
  gap: 20px;
}

.avatar-img {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  object-fit: cover;
  background: #f5ede2;
  border: 3px solid #f0e2d0;
  transition: all 0.3s ease;
  cursor: pointer;
}

.avatar-img:hover {
  border-color: #b87c4e;
  transform: scale(1.02);
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #d9c8b8;
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background: #f5ede2;
  border: 3px solid #f0e2d0;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.avatar-uploader-icon:hover {
  border-color: #b87c4e;
  color: #b87c4e;
}

/* 编辑按钮 */
.edit-btn {
  padding: 6px 18px;
  background: transparent;
  border: 1px solid #e6d5c4;
  border-radius: 40px;
  color: #9b8570;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.edit-btn:hover {
  background: #f5ede2;
  border-color: #b87c4e;
  color: #b87c4e;
  transform: scale(1.02);
}

.edit-btn:active {
  transform: scale(0.98);
}

/* 开关样式 */
.switch-item {
  justify-content: space-between;
}

.switch-item .setting-label {
  width: auto;
  flex: 1;
}

.switch-item .setting-value {
  flex-shrink: 0;
  width: auto;
}

.switch {
  position: relative;
  display: inline-block;
  width: 52px;
  height: 28px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #e6d5c4;
  transition: 0.3s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 24px;
  width: 24px;
  left: 2px;
  bottom: 2px;
  background-color: white;
  transition: 0.3s;
  border-radius: 50%;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

input:checked + .slider {
  background-color: #b87c4e;
}

input:checked + .slider:before {
  transform: translateX(24px);
}

input:focus + .slider {
  box-shadow: 0 0 1px #b87c4e;
}

/* 弹窗样式优化 */
:deep(.el-dialog) {
  border-radius: 20px;
  overflow: hidden;
}

:deep(.el-dialog__header) {
  /* background: linear-gradient(135deg, #fefaf5, #fff9f2); */
  padding: 20px 24px;
  margin: 0;
  border-bottom: 1px solid #f0e2d0;
}

:deep(.el-dialog__title) {
  font-size: 18px;
  font-weight: 600;
  color: #3a2c21;
}

:deep(.el-dialog__body) {
  padding: 24px;
  background: white;
}

:deep(.el-dialog__footer) {
  padding: 16px 24px;
  /* background: #fefaf5; */
  border-top: 1px solid #f0e2d0;
}

/* 表单样式优化 */
:deep(.el-form-item__label) {
  font-weight: 500;
  color: #5e4c38;
}

:deep(.el-input__wrapper) {
  border-radius: 12px;
  transition: all 0.3s ease;
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #b87c4e inset;
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #b87c4e inset;
}

:deep(.el-textarea__inner) {
  border-radius: 12px;
  transition: all 0.3s ease;
}

:deep(.el-textarea__inner:hover) {
  border-color: #b87c4e;
}

:deep(.el-textarea__inner:focus) {
  border-color: #b87c4e;
  box-shadow: 0 0 0 2px rgba(184, 124, 78, 0.1);
}

/* 按钮样式优化 */
:deep(.el-button--primary) {
  background: linear-gradient(135deg, #b87c4e, #9b623c);
  border: none;
  border-radius: 40px;
  padding: 10px 24px;
  font-weight: 500;
}

:deep(.el-button--primary:hover) {
  background: linear-gradient(135deg, #c68b59, #ad6e44);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(184, 124, 78, 0.3);
}

:deep(.el-button--primary:active) {
  transform: translateY(0);
}

:deep(.el-button--default) {
  border-radius: 40px;
  border-color: #e6d5c4;
  color: #9b8570;
}

:deep(.el-button--default:hover) {
  border-color: #b87c4e;
  color: #b87c4e;
  background: #fefbf7;
}

/* 级联选择器样式优化 */
:deep(.el-cascader) {
  width: 100%;
}

:deep(.el-cascader__wrapper) {
  border-radius: 12px;
  transition: all 0.3s ease;
}

:deep(.el-cascader__wrapper:hover) {
  box-shadow: 0 0 0 1px #b87c4e inset;
}

:deep(.el-cascader__tags) {
  flex-wrap: wrap;
}

:deep(.el-cascader-node.in-active-path) {
  color: #b87c4e;
}

:deep(.el-cascader-node.is-active) {
  color: #b87c4e;
  font-weight: 500;
}

:deep(.el-cascader-menu) {
  border-right: 1px solid #f0e2d0;
}

:deep(.el-cascader-menu__item) {
  padding: 8px 20px;
  transition: all 0.2s ease;
}

:deep(.el-cascader-menu__item:hover) {
  background: #fefbf7;
  color: #b87c4e;
}

/* 消息提示样式 */
:deep(.el-message) {
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

:deep(.el-message--success) {
  background: linear-gradient(135deg, #f0f9f0, #e8f5e8);
  border-color: #c5e0c5;
}

:deep(.el-message--success .el-message__content) {
  color: #2c6e2c;
}

/* 加载状态 */
:deep(.el-loading-mask) {
  border-radius: 24px;
}

/* 滚动条样式 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: #f5ede2;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb {
  background: #d9c8b8;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: #b87c4e;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .account-settings {
    padding: 20px 16px;
  }

  .settings-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .header-title {
    font-size: 24px;
  }

  .title-icon {
    font-size: 24px;
  }

  .save-all-btn {
    width: 100%;
    text-align: center;
  }

  .setting-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
    padding: 16px 20px;
  }

  .setting-label {
    width: 100%;
  }

  .setting-value {
    width: 100%;
    justify-content: space-between;
  }

  .avatar-wrapper {
    width: 100%;
    justify-content: space-between;
  }

  .bio-text {
    max-width: 200px;
  }

  .section-title {
    padding: 16px 20px;
    font-size: 16px;
  }

  .section-icon {
    font-size: 18px;
  }

  :deep(.el-dialog) {
    width: 90% !important;
    margin: 0 auto !important;
  }

  :deep(.el-dialog__body) {
    padding: 20px;
  }

  :deep(.el-form-item__label) {
    width: 70px !important;
  }
}

@media (max-width: 480px) {
  .account-settings {
    padding: 16px 12px;
  }

  .settings-header {
    margin-bottom: 24px;
    padding-bottom: 16px;
  }

  .header-title {
    font-size: 22px;
  }

  .title-icon {
    font-size: 22px;
  }

  .save-all-btn {
    padding: 8px 20px;
    font-size: 13px;
  }

  .avatar-wrapper {
    flex-wrap: wrap;
    justify-content: center;
    gap: 12px;
  }

  .avatar-img,
  .avatar-uploader-icon {
    width: 56px;
    height: 56px;
  }

  .edit-btn {
    padding: 4px 14px;
    font-size: 12px;
  }

  .value-text {
    font-size: 14px;
  }

  .bio-text {
    max-width: 150px;
  }

  .setting-item {
    padding: 14px 16px;
  }

  .section-title {
    padding: 14px 16px;
    font-size: 15px;
  }

  .section-icon {
    font-size: 16px;
  }

  .switch {
    width: 48px;
    height: 26px;
  }

  .slider:before {
    height: 22px;
    width: 22px;
    left: 2px;
    bottom: 2px;
  }

  input:checked + .slider:before {
    transform: translateX(22px);
  }

  :deep(.el-dialog__header) {
    padding: 16px 20px;
  }

  :deep(.el-dialog__title) {
    font-size: 16px;
  }

  :deep(.el-dialog__body) {
    padding: 16px;
  }

  :deep(.el-dialog__footer) {
    padding: 12px 16px;
  }

  :deep(.el-button) {
    padding: 8px 16px;
    font-size: 13px;
  }

  :deep(.el-form-item) {
    margin-bottom: 18px;
  }
}

/* 暗色模式支持（可选） */
@media (prefers-color-scheme: dark) {
  .account-settings {
    background: linear-gradient(135deg, #2a241f, #1f1b17);
  }

  .settings-section {
    background: #2a241f;
    border-color: #3a2c21;
  }

  .settings-section:hover {
    border-color: #5e4c38;
  }

  .section-title {
    background: #241f1a;
    border-bottom-color: #3a2c21;
    color: #e6d5c4;
  }

  .setting-item {
    border-bottom-color: #2f2822;
  }

  .setting-item:hover {
    background: #2f2822;
  }

  .label-text {
    color: #d9c8b8;
  }

  .value-text {
    color: #b5a084;
  }

  :deep(.el-dialog) {
    background: #2a241f;
  }

  :deep(.el-dialog__header) {
    background: #241f1a;
    border-bottom-color: #3a2c21;
  }

  :deep(.el-dialog__title) {
    color: #e6d5c4;
  }

  :deep(.el-dialog__body) {
    background: #2a241f;
  }

  :deep(.el-dialog__footer) {
    background: #241f1a;
    border-top-color: #3a2c21;
  }

  :deep(.el-form-item__label) {
    color: #d9c8b8;
  }

  :deep(.el-input__wrapper) {
    background: #1f1b17;
    box-shadow: 0 0 0 1px #3a2c21;
  }

  :deep(.el-input__inner) {
    color: #e6d5c4;
  }

  :deep(.el-cascader__wrapper) {
    background: #1f1b17;
  }
}
</style>
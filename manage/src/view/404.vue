<template>
  <div class="error-page">
    <div class="error-container">
      <!-- 动画背景 -->
      <div class="error-bg">
        <div class="bg-circle bg-circle-1"></div>
        <div class="bg-circle bg-circle-2"></div>
        <div class="bg-circle bg-circle-3"></div>
      </div>

      <!-- 主要内容 -->
      <div class="error-content">
        <!-- 404 动画 -->
        <div class="error-animation">
          <div class="error-code">
            <span class="digit">4</span>
            <span class="digit zero">
              <div class="zero-animation">
                <div class="zero-ring"></div>
                <span>0</span>
              </div>
            </span>
            <span class="digit">4</span>
          </div>
        </div>

        <!-- 提示信息 -->
        <h1 class="error-title">页面迷路了</h1>
        <p class="error-description">
          抱歉，您访问的页面不存在或已被删除
        </p>

        <!-- 操作按钮 -->
        <div class="error-actions">
          <el-button type="primary" size="large" @click="goHome" class="action-btn">
            <el-icon><HomeFilled /></el-icon>
            返回首页
          </el-button>
          <el-button size="large" @click="goBack" class="action-btn">
            <el-icon><ArrowLeft /></el-icon>
            返回上一页
          </el-button>
        </div>

        <!-- 推荐链接 -->
        <div class="error-links">
          <div class="links-title">您可能想访问：</div>
          <div class="links-list">
            <div class="link-item" @click="goToPage('system/user')">
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </div>
            <div class="link-item" @click="goToPage('contents/drama')">
              <el-icon><Film /></el-icon>
              <span>剧目管理</span>
            </div>
            <div class="link-item" @click="goToPage('analysis/drama')">
              <el-icon><DataAnalysis /></el-icon>
              <span>数据分析</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { 
  HomeFilled, 
  ArrowLeft, 
  User, 
  Film, 
  DataAnalysis 
} from '@element-plus/icons-vue'

const router = useRouter()

// 返回首页
const goHome = () => {
  router.push('/manage')
}

// 返回上一页
const goBack = () => {
  router.back()
}

// 跳转到指定页面
const goToPage = (path: string) => {
  router.push(`/manage/${path}`)
}
</script>

<style scoped>
.error-page {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.error-container {
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 动画背景 */
.error-bg {
  position: absolute;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.bg-circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  animation: float 20s infinite ease-in-out;
}

.bg-circle-1 {
  width: 300px;
  height: 300px;
  top: -150px;
  right: -150px;
  animation-delay: 0s;
}

.bg-circle-2 {
  width: 500px;
  height: 500px;
  bottom: -250px;
  left: -250px;
  animation-delay: -5s;
}

.bg-circle-3 {
  width: 200px;
  height: 200px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation-delay: -10s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-20px) rotate(5deg);
  }
}

/* 主要内容 */
.error-content {
  position: relative;
  z-index: 1;
  text-align: center;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 30px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  max-width: 600px;
  width: 90%;
  animation: fadeInUp 0.6s ease-out;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 404 动画 */
.error-animation {
  margin-bottom: 20px;
}

.error-code {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 120px;
  font-weight: bold;
  color: #667eea;
}

.digit {
  display: inline-block;
  animation: bounce 1s ease-in-out;
}

.zero {
  position: relative;
  margin: 0 10px;
}

.zero-animation {
  position: relative;
  display: inline-block;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.zero-ring {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border: 4px solid #667eea;
  border-radius: 50%;
  animation: ring 2s infinite;
}

.zero-animation span {
  position: relative;
  z-index: 1;
  font-size: 120px;
  font-weight: bold;
  color: #667eea;
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
}

@keyframes ring {
  0% {
    transform: scale(0.8);
    opacity: 1;
  }
  100% {
    transform: scale(1.2);
    opacity: 0;
  }
}

/* 标题和描述 */
.error-title {
  font-size: 32px;
  color: #333;
  margin: 20px 0 10px;
  font-weight: 600;
}

.error-description {
  font-size: 16px;
  color: #666;
  margin-bottom: 30px;
  line-height: 1.5;
}

/* 操作按钮 */
.error-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
  margin-bottom: 40px;
}

.action-btn {
  padding: 12px 24px;
  font-size: 16px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

/* 推荐链接 */
.error-links {
  border-top: 1px solid #eee;
  padding-top: 30px;
}

.links-title {
  font-size: 14px;
  color: #999;
  margin-bottom: 15px;
}

.links-list {
  display: flex;
  justify-content: center;
  gap: 30px;
  flex-wrap: wrap;
}

.link-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background: #f5f7fa;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #667eea;
}

.link-item:hover {
  background: #667eea;
  color: white;
  transform: translateY(-2px);
}

.link-item .el-icon {
  font-size: 18px;
}

.link-item span {
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .error-content {
    padding: 30px 20px;
  }
  
  .error-code {
    font-size: 80px;
  }
  
  .zero-animation {
    width: 80px;
    height: 80px;
    line-height: 80px;
  }
  
  .zero-animation span {
    font-size: 80px;
  }
  
  .error-title {
    font-size: 24px;
  }
  
  .error-description {
    font-size: 14px;
  }
  
  .action-btn {
    padding: 8px 16px;
    font-size: 14px;
  }
  
  .links-list {
    gap: 15px;
  }
  
  .link-item {
    padding: 6px 12px;
  }
}
</style>
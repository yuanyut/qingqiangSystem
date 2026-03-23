<script setup lang="ts">
import { ref, reactive } from 'vue'
import { Search } from '@element-plus/icons-vue'
import shaiXuanH from '@/components/client/shaiXuanH.vue';
import actor_card from '@/components/client/actor_card.vue';
import { rongYu, years, paiXu } from '@/types/actor';
const totalcount = ref(0)
const search = () => {
    totalcount.value = 35
}
const input = ref('')
const biaoQian = reactive([
    '传统剧目',
    '现代改编',
    '旦角唱腔',
    '生角表演',
    '名家采访'
])
const handleClose = (tag: string) => {
    biaoQian.splice(biaoQian.indexOf(tag), 1)
}
const showedit = ref(false)
const change = () => { showedit.value = !showedit.value 
    console.log(showedit.value)
}
</script>
<template>
    <!-- 搜索框 -->
    <div class="search-container">
        <div class="search-wrapper">
            <el-input v-model="input" placeholder="搜索推荐内容..." class="custom-search-input">
                <template #append>
                    <el-button :icon="Search" @click="search" class="search-button" />
                </template>
            </el-input>
        </div>


    </div>
    <div style="padding:25px;position: relative;">
        <div class="hot_header">
            <span class="title">推荐偏好设置</span>
        </div>
        <div class="hot_centent " style="gap:60px;padding: 20px 20px;">

            <el-tag v-for="tag in biaoQian" :key="tag" class="mx-1" closable :disable-transitions="false"
                @close="handleClose(tag)" size="large">
                {{ tag }}
            </el-tag>
            <el-button class="button-new-tag ml-1" size="default" @click="change">
                + New Tag
            </el-button>
        </div>
        <div style="padding:0 20px;color: #a1a1a180;">
            <span> (基于你的浏览记录，我们为你推荐以下内容) </span>
        </div>
    </div>
    <!-- 编辑偏好弹窗 -->
    <div 
  :class="{ active:showedit }" 
  class="edit-dialog"
>
  <div class="dialog-mask" @click="change"></div>
  <div class="dialog-container">
    <div class="dialog-header">
      <h3 class="dialog-title">编辑内容</h3>
      <button class="dialog-close" @click="change">×</button>
    </div>
    
    <div class="dialog-body">
      <el-form label-width="100px">
        <!-- 这里放你的表单项 -->
        <el-form-item label="名称">
          <el-input placeholder="请输入" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" placeholder="请输入" />
        </el-form-item>
      </el-form>
    </div>
    
    <div class="dialog-footer">
      <el-button @click="change">取消</el-button>
      <el-button type="primary" @click="change">创建</el-button>
    </div>
  </div>
</div>
</template>

<style scoped>
.search-container {
    background: #ffffff;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

/* 搜索框样式 */
.search-wrapper {
    margin-bottom: 24px;
    display: flex;
    justify-content: center;
}

.custom-search-input {
    width: 100%;
    max-width: 400px;
}

.hot_centent {
    display: flex;
    width: 100%;
    overflow-x: auto;
    gap: 20px;
    scrollbar-width: none;
    /* Firefox */
    -ms-overflow-style: none;
    /* IE/Edge */
    /* position: relative; */
    padding-top: 25px;

}

.hot_centent::-webkit-scrollbar {
    display: none;
}

:deep(.custom-search-input .el-input__wrapper) {
    border-radius: 8px 0 0 8px;
    box-shadow: 0 0 0 1px #e4e7ed;
    transition: all 0.2s;
}

:deep(.custom-search-input .el-input__wrapper:hover) {
    box-shadow: 0 0 0 1px #c0c4cc;
}

:deep(.custom-search-input .el-input__wrapper.is-focus) {
    box-shadow: 0 0 0 1px #409eff;
}

.search-button {
    border-radius: 0 8px 8px 0;
    background-color: #f5f7fa;
    transition: all 0.2s;
}

.search-button:hover {
    background-color: #ecf5ff;
    color: #409eff;
}

.hot_header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0px 20px;
}

.title {
    font-size: 18px;
    font-weight: 700;
    color: rgb(0, 0, 0);
    letter-spacing: 0.5px;
    position: relative;
    padding-left: 12px;
}

.title::before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 3px;
    height: 20px;
    background: #ffd700;
    border-radius: 2px;
}

.change-link {
    font-size: 14px;
    color: rgba(182, 182, 182, 0.9);
    cursor: pointer;
    transition: all 0.3s ease;
    /* padding: 4px 12px; */
    border-radius: 20px;
    background: rgba(255, 255, 255, 0.1);
}

.all-link:hover {
    background: rgba(255, 255, 255, 0.2);
    transform: translateX(2px);
}
.active{
display: none;
}
/* 弹窗遮罩层 */
.edit-dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
  visibility: hidden;
  opacity: 0;
  transition: all 0.3s ease;
}

.edit-dialog.active {
  visibility: visible;
  opacity: 1;
}

/* 背景遮罩 */
.dialog-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

/* 弹窗容器 */
.dialog-container {
  position: relative;
  width: 520px;
  max-width: 90%;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 20px 35px -8px rgba(0, 0, 0, 0.2);
  animation: dialogFadeIn 0.3s ease;
}

@keyframes dialogFadeIn {
  from {
    transform: scale(0.9);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

/* 弹窗头部 */
.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-bottom: 1px solid #f0f0f0;
}

.dialog-title {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #2c241a;
}

.dialog-close {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: transparent;
  border: none;
  font-size: 24px;
  color: #8b7355;
  cursor: pointer;
  border-radius: 8px;
  transition: all 0.2s;
}

.dialog-close:hover {
  background: #f5f5f5;
  color: #d4451e;
}

/* 弹窗内容 */
.dialog-body {
  padding: 24px;
  max-height: 60vh;
  overflow-y: auto;
}

/* 弹窗底部 */
.dialog-footer {
  padding: 16px 24px;
  border-top: 1px solid #f0f0f0;
  text-align: right;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}
</style>
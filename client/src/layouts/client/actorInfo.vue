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
const input3 = ref('')

</script>
<template>
  <!-- 搜索框 -->
  <div class="search-container">
    <div class="search-wrapper">
      <el-input v-model="input3" placeholder="搜索名家..." class="custom-search-input">
        <template #append>
          <el-button :icon="Search" @click="search" class="search-button" />
        </template>
      </el-input>
    </div>

    <div class="filter-wrapper">
      <shai-xuan-h v-model:cate-list="rongYu"></shai-xuan-h>
      <shai-xuan-h v-model:cate-list="years"></shai-xuan-h>
      <shai-xuan-h v-model:cate-list="paiXu"></shai-xuan-h>
    </div>
  </div>
  <div class="playlist-container">
    <div class="playlist-header">
      <div class="header-title">
        <span class="title-text">剧目列表</span>
        <span class="title-count">共 {{ totalcount }} 部剧目</span>
      </div>
    </div>

    <div class="playlist-grid">
      <div v-for="i in 10" :key="i">
        <actor_card image="/home/banner1.png" name="李淑芬" desc="国家一级演员"  act="三滴血" watch-people="观看人数"
          love-people="喜爱人数">

        </actor_card>

      </div>


    </div>
    <div class="playlist-footer">
      <el-pagination background layout="prev, pager, next" :total="1000" />
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

/* 筛选区域 */
.filter-wrapper {
  margin-bottom: 20px;
}

.filter-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 16px;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f0;
}

.filter-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
  margin-bottom: 0;
}

.filter-label {
  min-width: 56px;
  font-size: 14px;
  font-weight: 500;
  color: #606266;
  line-height: 32px;
}

.label-text {
  position: relative;
}

.filter-options {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  align-items: center;
}

.filter-tag {
  display: inline-block;
  padding: 6px 16px;
  font-size: 13px;
  color: #606266;
  background-color: #f5f7fa;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.2s;
  line-height: 1;
  user-select: none;
}

.filter-tag:hover {
  background-color: #ecf5ff;
  color: #409eff;
}

.filter-tag.active {
  background-color: #409eff;
  color: #ffffff;
}

.sort-tag {
  /* 排序标签特殊样式，如需区分可在此添加 */
}

.playlist-container {
  /* background: #f8f9fa; */
  border-radius: 16px;
  padding: 20px;
}

/* 头部区域 */
.playlist-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 2px solid #e9ecef;
}

.header-title {
  display: flex;
  align-items: baseline;
  gap: 12px;
}

.title-text {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  position: relative;
  padding-left: 16px;
}

.title-text::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 20px;
  background: linear-gradient(135deg, #d2691e, #8B5A2B);
  border-radius: 2px;
}

.title-count {
  font-size: 13px;
  color: #94a3b8;
  background: #f1f5f9;
  padding: 2px 8px;
  border-radius: 20px;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.view-mode {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 6px 12px;
  background: #ffffff;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 13px;
  color: #64748b;
  border: 1px solid #e2e8f0;
}

.view-mode:hover {
  background: #f1f5f9;
  border-color: #cbd5e1;
}

.mode-icon {
  font-size: 14px;
}

/* 网格布局 */
.playlist-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 24px;
  margin-top: 8px;
}

.playlist-item {
  transition: all 0.3s ease;
  animation: fadeInUp 0.4s ease backwards;
}

.playlist-item:hover {
  transform: translateY(-4px);
}

.playlist-footer {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

/* 为每个卡片添加延迟动画 */
.playlist-item:nth-child(1) {
  animation-delay: 0s;
}

.playlist-item:nth-child(2) {
  animation-delay: 0.05s;
}

.playlist-item:nth-child(3) {
  animation-delay: 0.1s;
}

.playlist-item:nth-child(4) {
  animation-delay: 0.15s;
}

.playlist-item:nth-child(5) {
  animation-delay: 0.2s;
}

.playlist-item:nth-child(6) {
  animation-delay: 0.25s;
}

.playlist-item:nth-child(7) {
  animation-delay: 0.3s;
}

.playlist-item:nth-child(8) {
  animation-delay: 0.35s;
}

.playlist-item:nth-child(9) {
  animation-delay: 0.4s;
}

.playlist-item:nth-child(10) {
  animation-delay: 0.45s;
}

/* 动画 */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 空状态或加载状态（可选） */
.playlist-empty {
  text-align: center;
  padding: 60px 20px;
  color: #94a3b8;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 1400px) {
  .playlist-grid {
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
  }
}

@media (max-width: 1200px) {
  .playlist-grid {
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
  }
}

@media (max-width: 768px) {
  .playlist-container {
    padding: 16px;
  }

  .playlist-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }

  .playlist-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
    margin-bottom: 20px;
  }

  .title-text {
    font-size: 18px;
  }

  .title-count {
    font-size: 12px;
  }
}

@media (max-width: 480px) {
  .playlist-grid {
    grid-template-columns: repeat(1, 1fr);
    gap: 16px;
  }
}

/* 响应式 */
@media (max-width: 768px) {
  .search-container {
    padding: 16px;
  }

  .filter-item {
    flex-direction: column;
    margin-bottom: 12px;
    padding-bottom: 12px;
  }

  .filter-label {
    margin-bottom: 8px;
    line-height: 1.5;
  }

  .filter-tag {
    padding: 4px 12px;
    font-size: 12px;
  }

  .custom-search-input {
    max-width: 100%;
  }
}
</style>
<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import card_knowledge from '@/components/client/card_knowledge.vue'
import { getCultureList } from '@/api/knowledge'
import type { Content } from '@/api/knowledge'
import {  knowledgeSortOptions } from '@/types/knowledge'

const router = useRouter()
const route = useRoute()
const totalcount = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const originalCultureList = ref<Content[]>([])
const cultureList = ref<Content[]>([])
const loading = ref(false)

const input = ref('')
const selectedCategory = ref<string | undefined>()
const selectedSort = ref<string | undefined>()

// 筛选器选中状态
const categoryIndex = ref(0)
const sortIndex = ref(0)

// 是否处于搜索状态
const isSearching = ref(false)

const search = () => {
  isSearching.value = true
  filterCultureList()
}

const handlePageChange = (page: number) => {
  currentPage.value = page
  loadCultureList()
}

const goToDetail = (id: number) => {
  router.push(`/knowledge/${id}`)
}

// const handleCategoryChange = (index: number) => {
//   if (isSearching.value) {
//     return
//   }
//   categoryIndex.value = index
//   currentPage.value = 1
//   if (index === 0) {
//     selectedCategory.value = undefined
//   } else {
//     selectedCategory.value = knowledgeCategories[index]?.name
//   }
//   loadCultureList()
// }

const handleSortChange = (index: number) => {
  if (isSearching.value) {
    return
  }
  sortIndex.value = index
  currentPage.value = 1
  loadCultureList()
}

const filterCultureList = () => {
  if (!input.value) {
    isSearching.value = false
    cultureList.value = [...originalCultureList.value]
    totalcount.value = originalCultureList.value.length
    return
  }
  
  const keyword = input.value.toLowerCase()
  cultureList.value = originalCultureList.value.filter(item => {
    return (
      item.title.toLowerCase().includes(keyword) ||
      item.content.toLowerCase().includes(keyword)
    )
  })
  totalcount.value = cultureList.value.length
}

const loadCultureList = async () => {
  try {
    loading.value = true
    const res = await getCultureList(
      currentPage.value,
      pageSize.value
    )
    if (res && res.code === 200 && res.data) {
      originalCultureList.value = (res.data.list || []).map((item: any) => ({
        id: item.id,
        title: item.title,
        content: item.content || '',
        cover: (item.cover || '/home/banner1.png').replace(/[`]/g, ''),
        status: item.status || 1,
        viewCount: item.viewCount || 0,
        likeCount: item.likeCount || 0,
        category: item.category || '',
        createTime: item.createTime || '',
        updateTime: item.updateTime || ''
      }))
    
      // 应用分类筛选
      if (selectedCategory.value) {
        cultureList.value = originalCultureList.value.filter(item => 
          item.category === selectedCategory.value
        )
      } else {
        cultureList.value = [...originalCultureList.value]
      }
      totalcount.value = cultureList.value.length
    }
  } catch (error) {
    console.error('加载文化内容失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadCultureList()
})

// 监听路由变化，当从详情页返回时重新加载数据
watch(() => route.path, (newPath) => {
  if (newPath === '/knowledge') {
    loadCultureList()
  }
})
</script>
<template>
  <div class="culture-home">
    <!-- 搜索框 -->
    <div class="search-container">
      <div class="search-wrapper">
        <el-input
          v-model="input"
          placeholder="探索文化瑰宝，输入关键词..."
          class="custom-search-input"
          @keyup.enter="search"
        >
          <template #append>
            <el-button :icon="Search" @click="search" class="search-button">搜索</el-button>
          </template>
        </el-input>
      </div>

      <!-- <div class="filter-wrapper">
       
       <div class="filter-item" :class="{ disabled: isSearching }">
          <div class="filter-label">
            <span class="label-text">分类</span>
          </div>
          <div class="filter-options">
            <span 
              v-for="(item, index) in knowledgeCategories" 
              :key="index" 
              @click="handleCategoryChange(index)"
              :class="{ active: categoryIndex === index, disabled: isSearching }" 
              class="filter-tag"
            >
              {{ item.name }}
            </span>
          </div>
        </div>  -->
        
        
        <!-- <div class="filter-item" :class="{ disabled: isSearching }">
          <div class="filter-label">
            <span class="label-text">排序</span>
          </div>
          <div class="filter-options">
            <span 
              v-for="(item, index) in knowledgeSortOptions" 
              :key="index" 
              @click="handleSortChange(index)"
              :class="{ active: sortIndex === index, disabled: isSearching }" 
              class="filter-tag sort-tag"
            >
              {{ item.name }}
            </span>
          </div>
        </div> -->
      <!-- </div>  -->
    </div>

    <div class="playlist-container">
      <div class="playlist-header">
        <div class="header-title">
          <span class="title-text">秦腔文化知识</span>
          <span class="title-count">共 {{ totalcount }} 条内容</span>
        </div>
      </div>

      <div class="playlist-grid">
        <div v-for="item in cultureList" :key="item.id" class="playlist-item" @click="goToDetail(item.id)">
          <card_knowledge
            :image="item.cover"
            :title="item.title"
            :desc="(item.content || '').substring(0, 100) + '...'"
          >
            <template #footer>
              <div class="card-footer">
                <span>{{ item.viewCount }}阅读</span>
                <span>{{ item.likeCount }}点赞</span>
                <!-- <span>{{ item.category }}</span> -->
              </div>
            </template>
          </card_knowledge>
        </div>
        <div v-if="cultureList.length === 0 && !loading" class="empty-state">
          <span>暂无文化内容数据</span>
        </div>
      </div>

      <div class="playlist-footer">
        <el-pagination 
          background 
          layout="prev, pager, next" 
          :total="totalcount" 
          :current-page="currentPage"
          :page-size="pageSize"
          @current-change="handlePageChange"
        />
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
  margin-bottom: 24px;
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
  transition: opacity 0.2s;
}

.filter-item.disabled {
  opacity: 0.5;
  pointer-events: none;
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

.filter-tag.disabled {
  cursor: not-allowed;
  background-color: #f5f7fa;
  color: #c0c4cc;
}

.filter-tag.disabled:hover {
  background-color: #f5f7fa;
  color: #c0c4cc;
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
  grid-template-columns: repeat(3, 1fr);
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

/* 卡片底部样式 */
.card-footer {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #94a3b8;
  margin-top: 12px;
  padding-top: 12px;
  width: 100%;
  border-top: 1px solid #f0f0f0;
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

.empty-state {
  grid-column: 1 / -1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #94a3b8;
  font-size: 14px;
  background: #f8fafc;
  border-radius: 12px;
  border: 1px dashed #e2e8f0;
}

/* 响应式设计 */
@media (max-width: 1400px) {
  .playlist-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
  }
}

@media (max-width: 768px) {
  .playlist-container {
    padding: 16px;
  }

  .playlist-grid {
    grid-template-columns: repeat(1, 1fr);
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
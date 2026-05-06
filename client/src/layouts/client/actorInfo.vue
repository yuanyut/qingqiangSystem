<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import shaiXuanH from '@/components/client/shaiXuanH.vue';
import actor_card from '@/components/client/actor_card.vue';
import { rongYu, years, roleNameTypes, paiXu } from '@/types/actor';
import { getActorList } from '@/api/actor';

const router = useRouter()
const route = useRoute()
const totalcount = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const originalActorList = ref<any[]>([])
const actorList = ref<any[]>([])
const loading = ref(false)

const input3 = ref('')
const selectedHonor = ref<number | undefined>()
const selectedYear = ref<string | undefined>()
const selectedStyle = ref<string | undefined>()
const selectedSort = ref<string | undefined>()

const isLiked=ref(true)
const isFavorited=ref(true)

// 筛选器选中状态
const honorIndex = ref(0)
const yearIndex = ref(0)
const styleIndex = ref(0)
const sortIndex = ref(0)

// 是否处于搜索状态
const isSearching = ref(false)

const changeLike=(index:boolean)=>{
  isLiked.value=index
}
const changeFav=()=>{
  isFavorited.value=!isFavorited.value
}

const search=()=>{
  isSearching.value = true
  filterActorList()
}

const handlePageChange=(page:number)=>{
  currentPage.value = page
  loadActorList()
}

const goToDetail=(id:number)=>{
  router.push(`/actor/${id}`)
}

const handleHonorChange=(index: number)=>{
  if (isSearching.value) {
    return
  }
  honorIndex.value = index
  currentPage.value = 1
  switch(index) {
    case 0:
      selectedHonor.value = undefined
      break
    case 1:
      selectedHonor.value = 1
      break
    case 2:
      selectedHonor.value = 2
      break
    case 3:
      selectedHonor.value = 3
      break
    case 4:
      selectedHonor.value = 4
      break
  }
  loadActorList()
}

const handleYearChange=(index: number)=>{
  if (isSearching.value) {
    return
  }
  yearIndex.value = index
  currentPage.value = 1
  loadActorList()
}

const handleStyleChange=(index: number)=>{
  if (isSearching.value) {
    return
  }
  styleIndex.value = index
  console.log(styleIndex.value)
  currentPage.value = 1
  switch(index) {
    case 0:
      selectedStyle.value = undefined
      break
    case 1:
      selectedStyle.value = '丑'
      break
    case 2:
      selectedStyle.value = '生'
      break
    case 3:
      selectedStyle.value ='旦'
      break
    case 4:
      selectedStyle.value = '旦'
      break
  }
  loadActorList()
}

const handleSortChange=(index: number)=>{
  if (isSearching.value) {
    return
  }
  sortIndex.value = index
  currentPage.value = 1
  loadActorList()
}

const filterActorList=()=>{
  if (!input3.value) {
    isSearching.value = false
    actorList.value = [...originalActorList.value]
    totalcount.value = originalActorList.value.length
    return
  }
  
  const keyword = input3.value.toLowerCase()
  actorList.value = originalActorList.value.filter(item => {
    const nameMatch = item.name ? item.name.toLowerCase().includes(keyword) : false
    const titleMatch = item.title ? item.title.toLowerCase().includes(keyword) : false
    return nameMatch || titleMatch
  })
  totalcount.value = actorList.value.length
}

const loadActorList=async ()=>{
  try {
    loading.value = true
    console.log(selectedStyle)
    const res = await getActorList(
      currentPage.value,
      pageSize.value,
      selectedStyle.value
    )
    if (res.code === 200) {
      originalActorList.value = (res.data.list || []).map((item: any) => ({
        id: item.id,
        name: item.name,
        title: item.title || '',
        avatar: (item.avatar || '/home/banner1.png').replace(/[`\s]/g, ''),
        works: item.works || [],
        fansCount: item.fansCount || 0,
        worksCount: item.worksCount || 0,
        awardsCount: item.awardsCount || 0,
        viewCount: item.viewCount || 0,
        likeCount: item.likeCount || 0
      }))
      actorList.value = [...originalActorList.value]
      totalcount.value = res.data.total || 0
    }
  } catch (error) {
    console.error('加载演员列表失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(()=>{
  loadActorList()
})

// 监听路由变化，当从详情页返回时重新加载数据
watch(() => route.path, (newPath) => {
  if (newPath === '/actorInfo') {
    loadActorList()
  }
})
</script>
<template>
  <!-- 搜索框 -->
  <div class="search-container">
    <div class="search-wrapper">
      <el-input 
        v-model="input3" 
        placeholder="搜索名家..." 
        class="custom-search-input"
        @keyup.enter="search"
      >
        <template #append>
          <el-button :icon="Search" @click="search" class="search-button">搜索</el-button>
        </template>
      </el-input>
    </div>

    <div class="filter-wrapper">
      <!-- 荣誉筛选 -->
      <!-- <div class="filter-item" :class="{ disabled: isSearching }">
        <div class="filter-label">
          <span class="label-text">荣誉</span>
        </div>
        <div class="filter-options">
          <span 
            v-for="(item, index) in rongYu.slice(1)" 
            :key="index" 
            @click="handleHonorChange(index)"
            :class="{ active: honorIndex === index, disabled: isSearching }" 
            class="filter-tag"
          >
            {{ item.name }}
          </span>
        </div>
      </div> -->
      
      <!-- 风格筛选 -->
      <div class="filter-item" :class="{ disabled: isSearching }">
        <div class="filter-label">
          <span class="label-text">风格</span>
        </div>
        <div class="filter-options">
          <span 
            v-for="(item, index) in roleNameTypes.slice(1)" 
            :key="index" 
            @click="handleStyleChange(index)"
            :class="{ active: styleIndex === index, disabled: isSearching }" 
            class="filter-tag"
          >
            {{ item.name }}
          </span>
        </div>
      </div>
      
      <!-- 排序 -->
      <!-- <div class="filter-item" :class="{ disabled: isSearching }">
        <div class="filter-label">
          <span class="label-text">排序</span>
        </div>
        <div class="filter-options">
          <span 
            v-for="(item, index) in paiXu.slice(1)" 
            :key="index" 
            @click="handleSortChange(index)"
            :class="{ active: sortIndex === index, disabled: isSearching }" 
            class="filter-tag sort-tag"
          >
            {{ item.name }}
          </span>
        </div>
      </div> -->
    </div>
  </div>
  <div class="playlist-container">
    <div class="playlist-header">
      <div class="header-title">
        <span class="title-text">名家风采</span>
        <span class="title-count">共 {{ totalcount }} 位名家</span>
      </div>
    </div>

    <div class="playlist-grid">
      <div v-for="actor in actorList" :key="actor.id" class="playlist-item" @click="goToDetail(actor.id)">
        <actor_card 
          :image="actor.avatar || '/home/banner1.png'" 
          :name="actor.name" 
          :desc="actor.title"  
          :act="actor.works[0] || '暂无作品'" 
          :viewCount="actor.viewCount || 0"
          :likeCount="actor.likeCount || 0" 
          
          :is-liked="actor.isLiked || false" 
          :is-favorited="actor.isFavorited || false"
          @change-like="changeLike" 
          @change-favorite="changeFav"
        />
      </div>
      <div v-if="actorList.length === 0 && !loading" class="empty-state">
        <span>暂无名家数据</span>
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

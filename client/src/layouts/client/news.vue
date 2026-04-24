<script setup lang="ts">
import { ref, reactive, onMounted, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Search, Loading } from '@element-plus/icons-vue'
import CategoryNav from '@/components/client/CategoryNav.vue'
import card_knowledge from '@/components/client/card_knowledge.vue'
import card_knowledge1 from '@/components/client/card_knowledge1.vue'
import { getNewsList, getCategoryNav, getTopNews, getHotList, getRecommendList, getMediaList, categoryMap } from '@/api/news'
import type { News, Category } from '@/api/news'

const router = useRouter()
const route = useRoute()
const input = ref('')

// 状态管理
const categoryNav = ref<Category[]>([])
const topNews = ref<News | null>(null)
const newsList = ref<News[]>([])
const hotList = ref<News[]>([])
const recommendList = ref<News[]>([])
const mediaList = ref<News[]>([])

// 加载状态
const loading = reactive({
  categories: false,
  topNews: false,
  newsList: false,
  hotList: false,
  recommendList: false,
  mediaList: false
})

// 搜索逻辑
const search = () => {
  if (input.value.trim()) {
    router.push(`/news/list?keyword=${encodeURIComponent(input.value.trim())}`)
  }
}

// 分类点击事件
const handleCategoryClick = (categoryId: number) => {
  router.push(`/news/list?category=${categoryId}`)
}

// 卡片点击事件
const handleCardClick = (id: number) => {
  router.push(`/news/${id}`)
}

// 加载分类导航
const loadCategories = async () => {
  try {
    loading.categories = true
    const res = await getCategoryNav()
    if (res.code === 200) {
      // 去重处理，确保分类不重复
      const uniqueCategories: Category[] = []
      const categoryNames = new Set<string>()
      
      res.data.forEach((category: Category) => {
        // 去除分类名称两端的空格
        const normalizedName = category.name.trim()
        
        if (!categoryNames.has(normalizedName)) {
          categoryNames.add(normalizedName)
          // 使用categoryMap将英文代码映射为中文名称
          if (categoryMap[normalizedName]) {
            category.name = categoryMap[normalizedName]
          }
          uniqueCategories.push(category)
        }
      })
      
      categoryNav.value = uniqueCategories
    }
  } catch (error) {
    console.error('加载分类导航失败:', error)
  } finally {
    loading.categories = false
  }
}

// 加载头条资讯
const loadTopNews = async () => {
  try {
    loading.topNews = true
    const res = await getTopNews()
    if (res.code === 200) {
      topNews.value = res.data
    }
  } catch (error) {
    console.error('加载头条资讯失败:', error)
  } finally {
    loading.topNews = false
  }
}

// 加载最新资讯
const loadNewsList = async () => {
  try {
    loading.newsList = true
    const res = await getNewsList(1, 5)
    if (res.code === 200 && res.data) {
      newsList.value = res.data.list
    }
  } catch (error) {
    console.error('加载最新资讯失败:', error)
  } finally {
    loading.newsList = false
  }
}

// 加载热门排行
const loadHotList = async () => {
  try {
    loading.hotList = true
    const res = await getHotList(10)
    if (res.code === 200) {
      hotList.value = res.data
    }
  } catch (error) {
    console.error('加载热门排行失败:', error)
  } finally {
    loading.hotList = false
  }
}

// 加载推荐阅读
const loadRecommendList = async () => {
  try {
    loading.recommendList = true
    const res = await getRecommendList(10)
    if (res.code === 200) {
      recommendList.value = res.data
    }
  } catch (error) {
    console.error('加载推荐阅读失败:', error)
  } finally {
    loading.recommendList = false
  }
}

// 加载媒体聚焦
const loadMediaList = async () => {
  try {
    loading.mediaList = true
    const res = await getMediaList(10)
    if (res.code === 200) {
      mediaList.value = res.data
    }
  } catch (error) {
    console.error('加载媒体聚焦失败:', error)
  } finally {
    loading.mediaList = false
  }
}

// 初始化数据
onMounted(() => {
  loadCategories()
  loadTopNews()
  loadNewsList()
  loadHotList()
  loadRecommendList()
  loadMediaList()
})

// 监听路由变化，当从详情页返回时重新加载数据
watch(() => route.path, (newPath) => {
  if (newPath === '/news') {
    loadCategories()
    loadTopNews()
    loadNewsList()
    loadHotList()
    loadRecommendList()
    loadMediaList()
  }
})
</script>
<template>
  <div class="culture-home">
    

    <!-- 分类导航 -->
    <section v-if="!loading.categories && categoryNav.length > 0" class="section">
      <div class="section-header">
        
      </div>
      <div v-if="loading.categories" class="loading-state">
        <el-icon class="is-loading"><Loading /></el-icon>
        <span>加载中...</span>
      </div>
      <!-- <div v-else class="nav-grid">
        <div v-for="(item, index) in categoryNav" :key="index" >
          <CategoryNav :title="item.name" :nums="item.count" :icon="item.icon" />
        </div>
      </div> -->
    </section>

    <!-- 特色推荐 · 精选内容 -->
    <section v-if="!loading.topNews && topNews" class="section">
      <div class="section-header">
        <h2 class="section-title">头条资讯</h2>
      </div>
      <div v-if="loading.topNews" class="loading-state">
        <el-icon class="is-loading"><Loading /></el-icon>
        <span>加载中...</span>
      </div>
      <div v-else-if="topNews" class="featured-list">
        <card_knowledge 
          :title="topNews.title" 
          :desc="topNews.content || ''" 
          @click="handleCardClick(topNews.id)"
        >
          <template #footer>
            <div style="display: flex;gap:50px">
              <span>{{ topNews.createTime || '' }}</span>
              <span>{{ topNews.category ? categoryMap[topNews.category] || topNews.category : '' }}</span>
            </div>
            <div class="featured-meta">
              <span>{{ topNews.viewCount || 0 }}阅读</span>
            </div>
          </template>
        </card_knowledge>
      </div>
    </section>

    <!-- 双栏布局 (左侧文章 + 右侧卡片) -->
    <section v-if="!loading.newsList && newsList.length > 0" class="section">
      <div class="two-columns">
        <!-- 左栏：最新资讯 -->
        <div class="column">
          <div class="column-header">
            <h3>最新资讯</h3>
          </div>
          <div v-if="loading.newsList" class="loading-state">
            <el-icon class="is-loading"><Loading /></el-icon>
            <span>加载中...</span>
          </div>
          <div v-else class="card-list">
            <div v-for="(item, index) in newsList" :key="item.id">
              <card_knowledge 
                :title="item.title" 
                :desc="item.content || ''" 
                @click="handleCardClick(item.id)"
              >
                <template #footer>
                  <div class="featured-meta">
                    <span>{{ item.createTime || '' }}</span>
                    <span>{{ item.viewCount || 0 }}阅读</span>
                    <span>{{ item.source || '' }}</span>
                  </div>
                </template>
              </card_knowledge>
            </div>
          </div>
          <div v-if="!loading.newsList && newsList.length > 0" class="more-link" @click="router.push('/news/list')">查看更多 →</div>
        </div>
      </div>

      <!-- 第二行双栏 -->
      <div v-if="(!loading.hotList && hotList.length > 0) || (!loading.recommendList && recommendList.length > 0)" class="two-columns">
        <!-- 左栏：热门排行 -->
        <div v-if="!loading.hotList && hotList.length > 0" class="column">
          <div class="column-header">
            <h3>热门排行</h3>
          </div>
          <div v-if="loading.hotList" class="loading-state">
            <el-icon class="is-loading"><Loading /></el-icon>
            <span>加载中...</span>
          </div>
          <div v-else class="card-list">
            <div v-for="(item, index) in hotList" :key="item.id">
              <card_knowledge 
                :title="(index + 1) + '.' + item.title" 
                @click="handleCardClick(item.id)"
              >
                <template #footer>
                  <div class="featured-meta">
                    <span>{{ item.viewCount || 0 }}阅读</span>
                    <span>热度</span>
                  </div>
                </template>
              </card_knowledge>
            </div>
          </div>
          
        </div>

        <!-- 右栏：推荐阅读 -->
        <div v-if="!loading.recommendList && recommendList.length > 0" class="column">
          <div class="column-header">
            <h3>推荐阅读</h3>
          </div>
          <div v-if="loading.recommendList" class="loading-state">
            <el-icon class="is-loading"><Loading /></el-icon>
            <span>加载中...</span>
          </div>
          <div v-else class="card-list">
            <div v-for="(item, index) in recommendList" :key="item.id">
              <card_knowledge 
                :title="item.title" 
                @click="handleCardClick(item.id)"
              >
                <template #footer>
                  <div class="featured-meta">
                    <span>{{ item.viewCount || 0 }}阅读</span>
                  </div>
                </template>
              </card_knowledge>
            </div>
          </div>
         
        </div>
      </div>
    </section>

    <!-- 媒体聚焦 -->
    <section v-if="!loading.mediaList && mediaList.length > 0" class="section">
      <div class="section-header">
        <h2 class="section-title">媒体聚焦</h2>
        <span class="more-text" @click="router.push('/news/list')">更多 →</span>
      </div>
      <div v-if="loading.mediaList" class="loading-state">
        <el-icon class="is-loading"><Loading /></el-icon>
        <span>加载中...</span>
      </div>
      <div v-else class="gallery-grid">
        <div v-for="(item, index) in mediaList" :key="item.id">
          <card_knowledge1 
            :title="item.title" 
            @click="handleCardClick(item.id)"
          >
            <template #cardAttr>
              <div class="video-meta">
                <div>{{ item.createTime || '' }}</div>
                <div>{{ item.viewCount || 0 }}播放</div>
              </div>
            </template>
          </card_knowledge1>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
/* 整体风格变量 */
.culture-home {
  /* max-width: 1280px; */
  margin: 0 auto;
  padding: 24px 32px;
  /* background: #fefaf5; */
  font-family: 'Inter', system-ui, -apple-system, 'Segoe UI', Roboto, 'Noto Sans', sans-serif;
  color: #2c2418;
}

/* 搜索区域 */
.search-container {
  /* background: linear-gradient(135deg, #ffffff 0%, #fcf6ed 100%); */
  border-radius: 32px;
  padding: 32px 24px;
  margin-bottom: 40px;
  /* box-shadow: 0 8px 28px -6px rgba(0, 0, 0, 0.05), 0 1px 2px rgba(0, 0, 0, 0.02); */
  backdrop-filter: blur(2px);
  transition: box-shadow 0.3s ease;
}

.search-wrapper {
  display: flex;
  justify-content: center;
}

.custom-search-input {
  width: 100%;
  max-width: 560px;
  transition: all 0.3s cubic-bezier(0.2, 0, 0, 1);
}

:deep(.custom-search-input .el-input__wrapper) {
  border-radius: 60px 0 0 60px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.02);
  padding: 4px 20px;
  border: 1px solid #f0e2d0;
  background-color: #ffffff;
  transition: all 0.2s;
}

:deep(.custom-search-input .el-input__wrapper:hover) {
  border-color: #d4b48c;
  box-shadow: 0 6px 14px rgba(184, 124, 78, 0.08);
}

:deep(.custom-search-input .el-input__wrapper.is-focus) {
  border-color: #b87c4e;
  box-shadow: 0 0 0 3px rgba(184, 124, 78, 0.12);
}

.search-button {
  border-radius: 0 60px 60px 0;
  background: #f5ede2;
  border: 1px solid #f0e2d0;
  border-left: none;
  color: #b87c4e;
  transition: all 0.2s ease;
  padding: 12px 28px;
  font-weight: 500;
}

.search-button:hover {
  background: #b87c4e;
  color: white;
  border-color: #b87c4e;
  transform: scale(1.02);
}

/* 通用区块样式 */
.section {
  margin-bottom: 52px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 24px;
  border-left: 5px solid #d9b48b;
  padding-left: 18px;
}

.section-title {
  font-size: 1.65rem;
  font-weight: 600;
  margin: 0;
  background: linear-gradient(135deg, #3a2c21, #6b4c38);
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  letter-spacing: -0.01em;
}

.section-subtitle {
  font-size: 0.85rem;
  color: #a1846a;
  font-weight: 400;
  letter-spacing: 0.3px;
}

.more-text {
  font-size: 0.9rem;
  color: #b87c4e;
  cursor: pointer;
  transition: color 0.2s, transform 0.2s;
  font-weight: 500;
}

.more-text:hover {
  color: #8b5a3a;
  transform: translateX(4px);
}

/* 文化分类导航网格 */
.nav-grid {
  display: flex;
  gap: 24px;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-top: 8px;
}

/* 特色精选卡片 */
.featured-list {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.featured-card {
  background: #ffffff;
  border-radius: 28px;
  padding: 24px 28px;
  box-shadow: 0 6px 14px rgba(0, 0, 0, 0.02), 0 1px 2px rgba(0, 0, 0, 0.03);
  transition: all 0.25s ease;
  border: 1px solid #f5e8db;
}

.featured-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 20px 30px -12px rgba(98, 67, 41, 0.08);
  border-color: #eedbcb;
}

.featured-title {
  font-size: 1.35rem;
  font-weight: 600;
  margin: 0 0 10px 0;
  color: #3a2c21;
}

.featured-desc {
  font-size: 0.95rem;
  color: #5e4c38;
  line-height: 1.5;
  margin-bottom: 14px;
}

.featured-meta {
  display: flex;
  gap: 20px;
  font-size: 0.8rem;
  color: #a1846a;
  margin-bottom: 16px;
}

.featured-link {
  font-weight: 500;
  color: #b87c4e;
  cursor: pointer;
  display: inline-block;
  transition: color 0.2s;
}

.featured-link:hover {
  color: #8b5a3a;
  text-decoration: underline;
}

/* 双栏布局 */
.two-columns {
  display: flex;
  gap: 40px;
  margin-bottom: 48px;
  flex-wrap: wrap;
}

.column {
  flex: 1;
  min-width: 260px;
  background: #f7f7f7cc;
  backdrop-filter: blur(2px);
  border-radius: 28px;
  padding: 16px;
  transition: all 0.2s;
}

.column-header {
  border-bottom: 2px solid #efdfcf;
  padding-bottom: 12px;
  margin-bottom: 20px;
}

.column-header h3 {
  font-size: 1.35rem;
  font-weight: 600;
  margin: 0;
  color: #4d3826;
  letter-spacing: -0.2px;
}

.card-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.more-link {
  margin-top: 20px;
  text-align: right;
  font-weight: 500;
  color: #b87c4e;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 0.9rem;
}

.more-link:hover {
  color: #8b5a3a;
  transform: translateX(4px);
}

/* 图集与视频网格 */
.gallery-grid {
  display: flex;
  gap: 28px;
  flex-wrap: wrap;
}

.video-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.75rem;
  color: #b5a084;
  margin-top: 8px;
}

/* 加载状态 */
.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  color: #94a3b8;
  gap: 12px;
}

/* 空状态 */
.empty-state {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #94a3b8;
  font-size: 16px;
  background: #f8fafc;
  border-radius: 16px;
  margin: 20px 0;
}

/* 非遗传承列表 */
.heritage-list {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.heritage-footer {
  font-weight: 500;
  color: #b87c4e;
  transition: color 0.2s;
}

.heritage-footer:hover {
  color: #8b5a3a;
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 860px) {
  .culture-home {
    padding: 20px 20px;
  }

  .two-columns {
    gap: 28px;
  }

  .nav-grid {
    gap: 16px;
  }

  .section-title {
    font-size: 1.4rem;
  }
}

@media (max-width: 640px) {
  .search-container {
    padding: 24px 16px;
  }

  .two-columns {
    flex-direction: column;
    gap: 32px;
  }

  .column {
    width: 100%;
  }

  .featured-card {
    padding: 20px;
  }

  .gallery-grid {
    gap: 16px;
  }
}

/* 优雅滚动条 */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background: #f1e7dd;
  border-radius: 6px;
}

::-webkit-scrollbar-thumb {
  background: #cfb797;
  border-radius: 6px;
}

::-webkit-scrollbar-thumb:hover {
  background: #b48a5a;
}
</style>
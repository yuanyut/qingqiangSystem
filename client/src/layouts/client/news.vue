<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { Search, Loading } from '@element-plus/icons-vue'
import CategoryNav from '@/components/client/CategoryNav.vue'
import card_knowledge from '@/components/client/card_knowledge.vue'
import card_knowledge1 from '@/components/client/card_knowledge1.vue'
import { getNewsList } from '@/api/news'
import type { News } from '@/api/news'

const router = useRouter()
const input = ref('')

// 状态管理
const categoryNav = ref<any[]>([])
const topNews = ref<any | null>(null)
const newsList = ref<News[]>([])
const hotList = ref<any[]>([])
const recommendList = ref<any[]>([])
const mediaList = ref<any[]>([])

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
    router.push(`/culture/search?keyword=${encodeURIComponent(input.value.trim())}`)
  }
}

// 分类点击事件
const handleCategoryClick = (categoryId: number) => {
  router.push(`/culture/category/${categoryId}`)
}

// 卡片点击事件
const handleCardClick = (id: number) => {
  router.push(`/content/${id}`)
}

// 加载分类导航
const loadCategories = async () => {
  try {
    loading.categories = true
    // 模拟分类数据
    categoryNav.value = [
      { id: 1, name: '秦腔新闻', icon: '🔥', count: 123 },
      { id: 2, name: '演出资讯', icon: '🎭', count: 89 },
      { id: 3, name: '名家动态', icon: '🌟', count: 67 },
      { id: 4, name: '活动公告', icon: '📢', count: 45 }
    ]
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
    // 模拟头条资讯
    topNews.value = {
      id: 1,
      title: '2024秦腔艺术节盛大开幕',
      content: '为期一个月的秦腔艺术节在西安隆重开幕，来自全国各地的秦腔剧团将带来精彩演出...',
      createTime: '2024-04-10',
      category: '秦腔新闻',
      viewCount: 1234,
      likeCount: 567
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
    const res = await getNewsList(1, 10)
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
    // 模拟热门排行
    hotList.value = [
      { id: 1, title: '2024秦腔艺术节盛大开幕', viewCount: 1234 },
      { id: 2, title: '著名秦腔演员李梅荣获国家级奖项', viewCount: 987 },
      { id: 3, title: '秦腔经典剧目《三滴血》全国巡演', viewCount: 765 },
      { id: 4, title: '秦腔新生代演员培养计划启动', viewCount: 543 },
      { id: 5, title: '秦腔艺术博物馆开馆仪式', viewCount: 321 }
    ]
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
    // 模拟推荐阅读
    recommendList.value = [
      { id: 6, title: '秦腔的历史与发展', viewCount: 456 },
      { id: 7, title: '秦腔表演技巧解析', viewCount: 345 },
      { id: 8, title: '秦腔音乐的魅力', viewCount: 234 },
      { id: 9, title: '秦腔与其他剧种的比较', viewCount: 123 }
    ]
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
    // 模拟媒体聚焦
    mediaList.value = [
      { id: 10, title: '秦腔演员专访：李梅', createTime: '2024-04-08', viewCount: 567 },
      { id: 11, title: '秦腔经典剧目《铡美案》演出片段', createTime: '2024-04-05', viewCount: 456 },
      { id: 12, title: '秦腔艺术进校园活动', createTime: '2024-04-01', viewCount: 345 }
    ]
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
</script>
<template>
  <div class="culture-home">
    <!-- 搜索框 -->
    <div class="search-container">
      <div class="search-wrapper">
        <el-input v-model="input" placeholder="探索文化瑰宝，输入关键词..." class="custom-search-input">
          <template #append>
            <el-button :icon="Search" @click="search" class="search-button" />
          </template>
        </el-input>
      </div>
    </div>

    <!-- 文化分类导航 -->
    <section class="section">
      <div class="section-header">
        <h2 class="section-title">总览</h2>
        <span class="section-subtitle">探寻文化脉络</span>
      </div>
      <div v-if="loading.categories" class="loading-state">
        <el-icon class="is-loading"><Loading /></el-icon>
        <span>加载中...</span>
      </div>
      <div v-else class="nav-grid">
        <div v-for="(item, index) in categoryNav" :key="index" @click="handleCategoryClick(item.id)">
          <CategoryNav :title="item.name" :nums="item.count" :icon="item.icon" />
        </div>
      </div>
    </section>

    <!-- 特色推荐 · 精选内容 -->
    <section class="section">
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
              <span>{{ topNews.category || '' }}</span>
            </div>
            <div class="featured-meta">
              <span>{{ topNews.viewCount || 0 }}阅读</span>
              <span>{{ topNews.likeCount || 0 }}点赞</span>
            </div>
          </template>
        </card_knowledge>
      </div>
      <div v-else class="empty-state">
        <span>暂无头条资讯</span>
      </div>
    </section>

    <!-- 双栏布局 (左侧文章 + 右侧卡片) -->
    <section class="section">
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
                    <span>{{ item.publishTime || '' }}</span>
                    <span>{{ item.viewCount || 0 }}阅读</span>
                    <span>{{ item.source || '' }}</span>
                  </div>
                </template>
              </card_knowledge>
            </div>
          </div>
          <div v-if="!loading.newsList && newsList.length > 0" class="more-link">查看更多 →</div>
        </div>
      </div>

      <!-- 第二行双栏 -->
      <div class="two-columns">
        <!-- 左栏：热门排行 -->
        <div class="column">
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
          <div v-if="!loading.hotList && hotList.length > 0" class="more-link">查看更多 →</div>
        </div>

        <!-- 右栏：推荐阅读 -->
        <div class="column">
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
          <div v-if="!loading.recommendList && recommendList.length > 0" class="more-link">更多 →</div>
        </div>
      </div>
    </section>

    <!-- 媒体聚焦 -->
    <section class="section">
      <div class="section-header">
        <h2 class="section-title">媒体聚焦</h2>
        <span class="more-text">更多 →</span>
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
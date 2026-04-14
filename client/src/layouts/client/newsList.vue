<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { Search, Loading, ArrowLeft } from '@element-plus/icons-vue'
import card_knowledge from '@/components/client/card_knowledge.vue'
import { getNewsList, getCategoryNav, categoryMap } from '@/api/news'
import type { News, Category } from '@/api/news'

const router = useRouter()

// 状态管理
const searchKeyword = ref('')
const selectedCategory = ref<number | null>(null)
const categoryList = ref<Category[]>([])
const newsList = ref<News[]>([])
const totalCount = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

// 加载状态
const loading = reactive({
  categories: false,
  newsList: false
})

// 搜索逻辑
const search = () => {
  currentPage.value = 1
  loadNewsList()
}

// 分类筛选逻辑
const handleCategoryChange = (categoryId: number) => {
  selectedCategory.value = categoryId
  currentPage.value = 1
  loadNewsList()
}

// 卡片点击事件
const handleCardClick = (id: number) => {
  router.push(`/news/${id}`)
}

// 返回上一页
const goBack = () => {
  router.back()
}

// 加载分类列表
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
      
      categoryList.value = uniqueCategories
    }
  } catch (error) {
    console.error('加载分类列表失败:', error)
  } finally {
    loading.categories = false
  }
}

// 加载新闻列表
const loadNewsList = async () => {
  try {
    loading.newsList = true
    const res = await getNewsList(currentPage.value, pageSize.value, selectedCategory.value || undefined, searchKeyword.value || undefined)
    if (res.code === 200 && res.data) {
      newsList.value = res.data.list
      totalCount.value = res.data.total
    }
  } catch (error) {
    console.error('加载新闻列表失败:', error)
  } finally {
    loading.newsList = false
  }
}

// 分页变化处理
const handlePageChange = (page: number) => {
  currentPage.value = page
  loadNewsList()
}

// 初始化数据
onMounted(() => {
  loadCategories()
  loadNewsList()
})
</script>

<template>
  <div class="news-list-container">

    <!-- 返回按钮 -->
    <div class="back-button-section">
      <el-button type="default" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
        <span>返回</span>
      </el-button>
    </div>

    <!-- 搜索和筛选区域 -->
    <div class="search-filter-section">
      <div class="search-box">
        <el-input
          v-model="searchKeyword"
          placeholder="请输入关键词搜索"
          prefix-icon="el-icon-search"
          class="search-input"
        >
          <template #append>
            <el-button type="primary" @click="search">
              <el-icon><Search /></el-icon>
              <span>搜索</span>
            </el-button>
          </template>
        </el-input>
      </div>

      <div class="category-filter">
        <span class="filter-label">分类筛选：</span>
        <el-radio-group v-model="selectedCategory" @change="handleCategoryChange">
          <el-radio :label="null">全部</el-radio>
          <el-radio v-for="category in categoryList" :key="category.id" :label="category.id">
            {{ category.name }}
          </el-radio>
        </el-radio-group>
      </div>
    </div>

    <!-- 新闻列表 -->
    <div class="news-list-section">
      <div v-if="loading.newsList" class="loading-state">
        <el-icon class="is-loading"><Loading /></el-icon>
        <span>加载中...</span>
      </div>
      <div v-else-if="newsList.length > 0" class="news-list">
        <div v-for="item in newsList" :key="item.id" class="news-item">
          <card_knowledge
            :title="item.title"
            :desc="item.content || ''"
            @click="handleCardClick(item.id)"
          >
            <template #footer>
              <div class="news-meta">
                <span>{{ item.createTime || '' }}</span>
                <span>{{ item.viewCount || 0 }}阅读</span>
                <span>{{ item.category ? categoryMap[item.category] || item.category : '' }}</span>
              </div>
            </template>
          </card_knowledge>
        </div>
      </div>
      <div v-else class="empty-state">
        <span>暂无新闻资讯</span>
      </div>

      <!-- 分页 -->
      <div v-if="!loading.newsList && totalCount > 0" class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="totalCount"
          @current-change="handlePageChange"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.news-list-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
}

.back-button-section {
  margin-bottom: 20px;
}

.page-header {
  margin-bottom: 32px;
}

.page-title {
  font-size: 28px;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
}

.search-filter-section {
  background: #ffffff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  margin-bottom: 32px;
}

.search-box {
  margin-bottom: 20px;
}

.search-input {
  width: 100%;
  max-width: 600px;
}

.category-filter {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-wrap: wrap;
}

.filter-label {
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
}

.news-list-section {
  background: #ffffff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  gap: 16px;
  color: #94a3b8;
}

.news-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.news-item {
  border-bottom: 1px solid #e2e8f0;
  padding-bottom: 16px;
}

.news-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.news-meta {
  display: flex;
  gap: 24px;
  font-size: 14px;
  color: #64748b;
}

.empty-state {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #94a3b8;
  font-size: 16px;
  background: #f8fafc;
  border-radius: 8px;
}

.pagination {
  margin-top: 32px;
  display: flex;
  justify-content: flex-end;
}

@media (max-width: 768px) {
  .news-list-container {
    padding: 20px 16px;
  }

  .page-title {
    font-size: 24px;
  }

  .search-filter-section {
    padding: 16px;
  }

  .category-filter {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .news-list-section {
    padding: 16px;
  }

  .pagination {
    justify-content: center;
  }
}
</style>
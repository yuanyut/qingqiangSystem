<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { ArrowLeft, Loading } from '@element-plus/icons-vue'
import request from '@/api/request'

const route = useRoute()
const newsId = ref<number>(parseInt(route.params.id as string))
const news = ref<any>(null)
const loading = ref(true)

const goBack = () => {
  window.history.back()
}

const loadNewsDetail = async () => {
  try {
    loading.value = true
    news.value = null // 清空旧数据
    const res = await request({
      url: `/news/detail/${newsId.value}`,
      method: 'get'
    })
    if (res.code === 200) {
      news.value = {
        ...res.data,
        title: res.data.title || '未知标题',
        content: res.data.content || '暂无内容',
        createTime: res.data.createTime || '未知时间',
        source: res.data.source || '未知来源',
        viewCount: res.data.viewCount || 0,
        cover: res.data.cover || '/home/banner1.png'
      }
    }
  } catch (error) {
    console.error('加载新闻详情失败:', error)
  } finally {
    loading.value = false
  }
}

// 监听路由参数变化
watch(() => route.params.id, (newId) => {
  if (newId) {
    newsId.value = parseInt(newId as string)
    loadNewsDetail()
  }
})

onMounted(() => {
  loadNewsDetail()
})
</script>

<template>
  <div class="news-detail-container">
    <div v-if="loading" class="loading-state">
      <el-icon class="is-loading" :size="40">
        <Loading />
      </el-icon>
      <span>加载中...</span>
    </div>

    <div v-else-if="news" class="detail-content">
      <div class="back-button" @click="goBack">
        <el-icon :size="20">
          <ArrowLeft />
        </el-icon>
        <span>返回</span>
      </div>

      <div class="detail-header">
        <h1 class="news-title">{{ news.title }}</h1>
        <div class="news-meta">
          <span class="meta-item">{{ news.createTime }}</span>
          <span class="meta-item">{{ news.source }}</span>
          <span class="meta-item">{{ news.viewCount }} 阅读</span>
        </div>
        <div v-if="news.cover" class="news-cover">
          <el-image 
            :src="news.cover" 
            fit="cover"
            class="cover-img"
          />
        </div>
      </div>

      <div class="detail-content-section">
        <div class="news-content" v-html="news.content"></div>
      </div>
    </div>

    <div v-else class="empty-state">
      <span>新闻不存在</span>
    </div>
  </div>
</template>

<style scoped>
.news-detail-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100px 20px;
  gap: 20px;
  color: #94a3b8;
}

.detail-content {
  background: #ffffff;
  border-radius: 16px;
  padding: 40px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.back-button {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background: #f8fafc;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
  color: #64748b;
  margin-bottom: 24px;
}

.back-button:hover {
  background: #e2e8f0;
  color: #334155;
}

.detail-header {
  margin-bottom: 40px;
  padding-bottom: 40px;
  border-bottom: 1px solid #e2e8f0;
}

.news-title {
  font-size: 32px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 24px;
  line-height: 1.3;
}

.news-meta {
  display: flex;
  gap: 24px;
  margin-bottom: 24px;
  color: #64748b;
  font-size: 14px;
}

.meta-item {
  display: flex;
  align-items: center;
}

.news-cover {
  margin-bottom: 32px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.cover-img {
  width: 100%;
  height: 400px;
  object-fit: cover;
}

.detail-content-section {
  margin-top: 40px;
}

.news-content {
  font-size: 16px;
  line-height: 1.8;
  color: #334155;
}

.news-content p {
  margin-bottom: 16px;
}

.news-content img {
  max-width: 100%;
  height: auto;
  margin: 16px 0;
  border-radius: 8px;
}

.empty-state {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 100px 20px;
  color: #94a3b8;
  font-size: 16px;
  background: #f8fafc;
  border-radius: 16px;
}

@media (max-width: 768px) {
  .news-detail-container {
    padding: 20px 16px;
  }

  .detail-content {
    padding: 24px;
  }

  .news-title {
    font-size: 24px;
  }

  .news-meta {
    flex-wrap: wrap;
    gap: 12px;
  }

  .cover-img {
    height: 200px;
  }

  .news-content {
    font-size: 14px;
  }
}
</style>
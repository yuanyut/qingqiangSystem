<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { ArrowLeft, Loading } from '@element-plus/icons-vue'
import request from '@/api/request'
import { toggleLike, toggleFavorite, addBehavior, checkBehavior } from '@/api/behavior'
import { useUserInfoStore } from '@/stores/userInfo'
import { ElMessage } from 'element-plus'

const route = useRoute()
const newsId = ref<number>(parseInt(route.params.id as string))
const news = ref<any>(null)
const loading = ref(true)
const isLiked = ref(false)
const isFavorited = ref(false)
const userStore = useUserInfoStore()

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
        likeCount: res.data.likeCount || 0,
        cover: res.data.cover || '/home/banner1.png'
      }
      
      if (userStore.isLoggedIn) {
        const likeRes = await checkBehavior('news', newsId.value, 'like')
        if (likeRes.code === 200) {
          isLiked.value = likeRes.data.isLiked || false
        }
        
        const favoriteRes = await checkBehavior('news', newsId.value, 'favorite')
        if (favoriteRes.code === 200) {
          isFavorited.value = favoriteRes.data.isFavorited || false
        }
      }
    }
  } catch (error) {
    console.error('加载新闻详情失败:', error)
  } finally {
    loading.value = false
  }
}

// 处理点赞/取消点赞
const handleLike = async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    return
  }
  
  try {
    const res = await toggleLike('news', newsId.value)
    if (res.code === 200) {
      isLiked.value = res.data.isLiked || false
      news.value.likeCount = res.data.isLiked ? news.value.likeCount + 1 : news.value.likeCount - 1
      ElMessage.success(res.data.isLiked ? '点赞成功' : '取消点赞成功')
    } else {
      ElMessage.error('操作失败')
    }
  } catch (error) {
    console.error('点赞操作失败:', error)
    ElMessage.error('操作失败')
  }
}

// 处理收藏/取消收藏
const handleFavorite = async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    return
  }
  
  try {
    const res = await toggleFavorite('news', newsId.value)
    if (res.code === 200) {
      isFavorited.value = res.data.isFavorited || false
      ElMessage.success(res.data.isFavorited ? '收藏成功' : '取消收藏成功')
    } else {
      ElMessage.error('操作失败')
    }
  } catch (error) {
    console.error('收藏操作失败:', error)
    ElMessage.error('操作失败')
  }
}

// 监听路由参数变化
watch(() => route.params.id, (newId) => {
  if (newId) {
    newsId.value = parseInt(newId as string)
    isLiked.value = false
    isFavorited.value = false
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
            <span class="meta-item">{{ news.likeCount }} 点赞</span>
          </div>
          <div class="action-buttons">
            <el-button 
              :type="isLiked ? 'primary' : 'default'" 
              @click="handleLike"
              class="action-button"
            >
              {{ isLiked ? '已点赞' : '点赞' }}
            </el-button>
            <el-button 
              :type="isFavorited ? 'warning' : 'default'" 
              @click="handleFavorite"
              class="action-button"
            >
              {{ isFavorited ? '已收藏' : '收藏' }}
            </el-button>
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

.action-buttons {
  display: flex;
  gap: 12px;
  margin: 24px 0;
}

.action-button {
  flex: 1;
  max-width: 120px;
}

.action-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.action-button .el-icon {
  margin-right: 8px;
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
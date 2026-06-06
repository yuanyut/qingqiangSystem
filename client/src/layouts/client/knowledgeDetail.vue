<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { getCultureDetail } from '@/api/knowledge'
import type { Content } from '@/api/knowledge'
import { toggleLike, toggleFavorite, addBehavior, checkBehavior } from '@/api/behavior'
import { useUserInfoStore } from '@/stores/userInfo'
import { ArrowLeft } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const knowledgeItem = ref<Content | null>(null)
const loading = ref(false)
const isLiked = ref(false)
const isFavorited = ref(false)
const userStore = useUserInfoStore()

const goBack = () => {
  window.history.back()
}

const loadKnowledgeDetail = async () => {
  try {
    loading.value = true
    knowledgeItem.value = null // 清空旧数据
    const id = Number(route.params.id)

    // 浏览次数由后端拦截器自动记录，无需手动调用
    const response = await getCultureDetail(id)
    if (response && response.code === 200 && response.data) {
      knowledgeItem.value = {
        id: response.data.id,
        title: response.data.title,
        content: response.data.content || '',
        cover: (response.data.cover || '/home/banner1.png').replace(/[`]/g, ''),
        status: response.data.status || 1,
        viewCount: response.data.viewCount || 0,
        likeCount: response.data.likeCount || 0,
        category: response.data.category || '',
        createTime: response.data.createTime || '',
        updateTime: response.data.updateTime || ''
      }

      if (userStore.UserInfos.isLogin) {
        const likeRes = await checkBehavior('culture', id, 'like')
        if (likeRes.code === 200) {
          isLiked.value = likeRes.data.isLiked || false
        }

        const favoriteRes = await checkBehavior('culture', id, 'favorite')
        if (favoriteRes.code === 200) {
          isFavorited.value = favoriteRes.data.isFavorited || false
        }
      }
    }
  } catch (error) {
    console.error('加载文化知识详情失败:', error)
  } finally {
    loading.value = false
  }
}

// 处理点赞/取消点赞
const handleLike = async () => {
  if (!userStore.UserInfos.isLogin) {
    ElMessage.warning('请先登录')
    return
  }

  try {
    const res = await toggleLike('culture', knowledgeItem.value?.id || 0)
    if (res.code === 200) {
      isLiked.value = res.data.isLiked ??false
      if (knowledgeItem.value) {
        knowledgeItem.value.likeCount = res.data.isLiked ? knowledgeItem.value.likeCount + 1 : knowledgeItem.value.likeCount - 1
      }
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
  if (!userStore.UserInfos.isLogin) {
    ElMessage.warning('请先登录')
    return
  }

  try {
    const res = await toggleFavorite('culture', knowledgeItem.value?.id || 0)
    if (res.code === 200) {
      isFavorited.value = res.data.isFavorited ??false
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
    isLiked.value = false
    isFavorited.value = false
    // 只有当组件已经挂载后才重新加载，避免重复调用
    if (isMounted.value) {
      loadKnowledgeDetail()
    }
  }
})

const isMounted = ref(false)
onMounted(() => {
  loadKnowledgeDetail()
  isMounted.value = true
})
</script>
<template>
  <div class="knowledge-detail">
    <div v-if="loading" class="loading-state">
      <el-icon class="is-loading"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 12a9 9 0 1 1-6.219-8.56"/></svg></el-icon>
      <span>加载中...</span>
    </div>
    <div v-else-if="knowledgeItem" class="detail-content">
      <div class="back-button" @click="goBack">
        <el-icon :size="20">
          <ArrowLeft />
        </el-icon>
        <span>返回</span>
      </div>
      <div class="detail-header">
        <h1 class="detail-title">{{ knowledgeItem.title }}</h1>
        <div class="detail-meta">
          <span class="category">{{ knowledgeItem.category }}</span>
          <span class="view-count">{{ knowledgeItem.viewCount }} 阅读</span>
          <span class="like-count">{{ knowledgeItem.likeCount }} 点赞</span>
          <span class="create-time">{{ knowledgeItem.createTime }}</span>
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
      </div>
      <div class="detail-body">
        <!-- <div v-if="knowledgeItem.cover" class="cover-image">
          <img :src="knowledgeItem.cover" :alt="knowledgeItem.title" />
        </div> -->
        <div class="content" v-html="knowledgeItem.content"></div>
      </div>
    </div>
    <div v-else class="empty-state">
      <span>文化知识不存在</span>
    </div>
  </div>
</template>

<style scoped>
.knowledge-detail {
  max-width: 800px;
  margin: 0 auto;
  padding: 24px;
  font-family: 'Inter', system-ui, -apple-system, 'Segoe UI', Roboto, 'Noto Sans', sans-serif;
  color: #2c2418;
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100px 0;
  gap: 16px;
  color: #606266;
}

.empty-state {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 100px 0;
  color: #94a3b8;
  font-size: 16px;
}

.detail-content {
  background: #ffffff;
  border-radius: 12px;
  padding: 32px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
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
  margin-bottom: 32px;
  border-bottom: 2px solid #e9ecef;
  padding-bottom: 24px;
}

.detail-title {
  font-size: 28px;
  font-weight: 700;
  margin: 0 0 16px 0;
  color: #1e293b;
  line-height: 1.3;
}

.detail-meta {
  display: flex;
  gap: 24px;
  font-size: 14px;
  color: #94a3b8;
  flex-wrap: wrap;
}

.category {
  background: #f1f5f9;
  padding: 4px 12px;
  border-radius: 20px;
  font-weight: 500;
  color: #475569;
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

.detail-body {
  line-height: 1.8;
  color: #334155;
}

.cover-image {
  margin-bottom: 32px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.cover-image img {
  width: 100%;
  height: auto;
  display: block;
}

.content {
  font-size: 16px;
}

.content h2 {
  font-size: 22px;
  font-weight: 600;
  margin: 32px 0 16px 0;
  color: #1e293b;
}

.content h3 {
  font-size: 18px;
  font-weight: 600;
  margin: 24px 0 12px 0;
  color: #1e293b;
}

.content p {
  margin-bottom: 16px;
}

.content img {
  max-width: 100%;
  height: auto;
  margin: 24px 0;
  border-radius: 8px;
}

.content ul,
.content ol {
  margin: 16px 0;
  padding-left: 24px;
}

.content li {
  margin-bottom: 8px;
}

@media (max-width: 768px) {
  .knowledge-detail {
    padding: 16px;
  }

  .detail-content {
    padding: 24px;
  }

  .detail-title {
    font-size: 24px;
  }

  .detail-meta {
    gap: 16px;
    font-size: 12px;
  }

  .content {
    font-size: 15px;
  }

  .content h2 {
    font-size: 20px;
  }

  .content h3 {
    font-size: 16px;
  }
}
</style>

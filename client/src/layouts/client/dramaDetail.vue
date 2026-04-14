<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { getDramaDetail } from '@/api/drama'
import { ArrowLeft, Loading } from '@element-plus/icons-vue'

const route = useRoute()
const dramaId = ref<number>(parseInt(route.params.id as string))
const drama = ref<any>(null)
const loading = ref(true)

const goBack = () => {
  window.history.back()
}

const loadDramaDetail = async () => {
  try {
    loading.value = true
    drama.value = null // 清空旧数据
    const res = await getDramaDetail(dramaId.value)
    if (res.code === 200) {
      drama.value = {
        ...res.data,
        // @ts-ignore - API返回字段为name，但类型定义可能不匹配
        title: res.data.name,
        // @ts-ignore - API返回字段为intro，但类型定义可能不匹配
        description: res.data.intro,
        
        // @ts-ignore - API返回字段为cover，但类型定义可能不匹配
        coverImage: res.data.cover ? res.data.cover.replace(/[`\s]/g, '') : '/home/banner1.png',
        // @ts-ignore - API返回字段为viewCount，但类型定义可能不匹配
        watchCount: res.data.viewCount || 0,
        likeCount: 0,
        categoryName: res.data.categoryId === 1 ? '传统剧目' : '现代剧目'
      }
    }
  } catch (error) {
    console.error('加载剧目详情失败:', error)
  } finally {
    loading.value = false
  }
}

// 监听路由参数变化
watch(() => route.params.id, (newId) => {
  if (newId) {
    dramaId.value = parseInt(newId as string)
    loadDramaDetail()
  }
})

onMounted(() => {
  loadDramaDetail()
})
</script>

<template>
  <div class="drama-detail-container">
    <div v-if="loading" class="loading-state">
      <el-icon class="is-loading" :size="40">
        <Loading />
      </el-icon>
      <span>加载中...</span>
    </div>

    <div v-else-if="drama" class="detail-content">
      <div class="back-button" @click="goBack">
        <el-icon :size="20">
          <ArrowLeft />
        </el-icon>
        <span>返回</span>
      </div>

      <div class="detail-header">
        <div class="cover-image">
          <el-image 
            :src="drama.coverImage || '/home/banner1.png'" 
            fit="cover"
            class="cover-img"
          />
        </div>
        <div class="drama-info">
          <h1 class="drama-title">{{ drama.title }}</h1>
          <div class="drama-meta">
            <span class="meta-item">
              <span class="meta-label">分类：</span>
              <span class="meta-value">{{ drama.categoryName }}</span>
            </span>
            <span class="meta-item">
              <span class="meta-label">观看：</span>
              <span class="meta-value">{{ drama.watchCount }}次</span>
            </span>
            <span class="meta-item">
              <span class="meta-label">喜欢：</span>
              <span class="meta-value">{{ drama.likeCount }}人</span>
            </span>
          </div>
          <div class="drama-description">
            <h3>剧目简介</h3>
            <p>{{ drama.description }}</p>
          </div>
        </div>
      </div>

      <div class="detail-section">
        <h2 class="section-title">演员阵容</h2>
        <div v-if="drama.actors && drama.actors.length > 0" class="actors-list">
          <div v-for="actor in drama.actors" :key="actor.id" class="actor-item">
            <el-image 
              :src="actor.avatar || '/home/banner1.png'" 
              fit="cover"
              class="actor-avatar"
            />
            <div class="actor-name">{{ actor.name }}</div>
          </div>
        </div>
        <div v-else class="empty-actors">
          <span>暂无演员信息</span>
        </div>
      </div>
    </div>

    <div v-else class="empty-state">
      <span>剧目不存在</span>
    </div>
  </div>
</template>

<style scoped>
.drama-detail-container {
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
  display: flex;
  gap: 40px;
  margin-bottom: 40px;
  padding-bottom: 40px;
  border-bottom: 1px solid #e2e8f0;
}

.cover-image {
  flex-shrink: 0;
  width: 400px;
  height: 500px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.cover-img {
  width: 100%;
  height: 100%;
}

.drama-info {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.drama-title {
  font-size: 32px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 24px;
}

.drama-meta {
  display: flex;
  gap: 24px;
  margin-bottom: 24px;
  padding: 16px;
  background: #f8fafc;
  border-radius: 8px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.meta-label {
  font-size: 14px;
  color: #64748b;
}

.meta-value {
  font-size: 14px;
  font-weight: 500;
  color: #1e293b;
}

.drama-description {
  flex: 1;
}

.drama-description h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 12px;
}

.drama-description p {
  font-size: 14px;
  line-height: 1.8;
  color: #64748b;
}

.detail-section {
  margin-top: 40px;
}

.section-title {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 24px;
  padding-left: 16px;
  position: relative;
}

.section-title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 24px;
  background: linear-gradient(135deg, #d2691e, #8B5A2B);
  border-radius: 2px;
}

.actors-list {
  display: flex;
  gap: 24px;
  flex-wrap: wrap;
}

.actor-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.actor-avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.actor-name {
  font-size: 14px;
  color: #1e293b;
  font-weight: 500;
}

.empty-actors {
  padding: 40px 20px;
  text-align: center;
  color: #94a3b8;
  font-size: 14px;
  background: #f8fafc;
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
  .drama-detail-container {
    padding: 20px 16px;
  }

  .detail-content {
    padding: 24px;
  }

  .detail-header {
    flex-direction: column;
    gap: 24px;
  }

  .cover-image {
    width: 100%;
    height: 400px;
  }

  .drama-title {
    font-size: 24px;
  }

  .drama-meta {
    flex-direction: column;
    gap: 12px;
  }

  .actors-list {
    gap: 16px;
  }

  .actor-avatar {
    width: 80px;
    height: 80px;
  }
}
</style>

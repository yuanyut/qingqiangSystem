<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getActorDetail } from '@/api/actor'
import { ArrowLeft, Loading } from '@element-plus/icons-vue'

const route = useRoute()
const actorId = ref<number>(parseInt(route.params.id as string))
const actor = ref<any>(null)
const loading = ref(true)

const goBack = () => {
  window.history.back()
}

const loadActorDetail = async () => {
  try {
    loading.value = true
    const res = await getActorDetail(actorId.value)
    if (res.code === 200) {
      actor.value = {
        ...res.data,
        avatar: res.data.avatar ? res.data.avatar.replace(/[`\s]/g, '') : '/home/banner1.png',
        bio: res.data.intro || '暂无简介',
        works: res.data.dramas || [],
        roleName: res.data.roleName || '未知',
        style: res.data.style || '未知',
        likeCount: res.data.likeCount || 0,
        worksCount: res.data.worksCount || 0,
        viewCount: res.data.viewCount || 0,
        joinDate: res.data.joinDate || '未知'
      }
    }
  } catch (error) {
    console.error('加载演员详情失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadActorDetail()
})
</script>

<template>
  <div class="actor-detail-container">
    <div v-if="loading" class="loading-state">
      <el-icon class="is-loading" :size="40">
        <Loading />
      </el-icon>
      <span>加载中...</span>
    </div>

    <div v-else-if="actor" class="detail-content">
      <div class="back-button" @click="goBack">
        <el-icon :size="20">
          <ArrowLeft />
        </el-icon>
        <span>返回</span>
      </div>

      <div class="detail-header">
        <div class="avatar-image">
          <el-image 
            :src="actor.avatar || '/home/banner1.png'" 
            fit="cover"
            class="avatar-img"
          />
        </div>
        <div class="actor-info">
          <h1 class="actor-name">{{ actor.name }}</h1>
          <div class="actor-title">{{ actor.roleName }} · {{ actor.style }}</div>
          <div class="actor-stats">
            <div class="stat-item">
              <div class="stat-value">{{ actor.likeCount }}</div>
              <div class="stat-label">粉丝</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ actor.worksCount }}</div>
              <div class="stat-label">作品</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ actor.viewCount }}</div>
              <div class="stat-label">播放</div>
            </div>
          </div>
          <div class="actor-bio">
            <h3>个人简介</h3>
            <p>{{ actor.bio }}</p>
          </div>
          <div class="actor-dates">
            <span class="date-item">
              <span class="date-label">入行时间：</span>
              <span class="date-value">{{ actor.joinDate }}</span>
            </span>
          </div>
        </div>
      </div>

      <div class="detail-section">
        <h2 class="section-title">代表作品</h2>
        <div v-if="actor.works && actor.works.length > 0" class="works-list">
          <div v-for="work in actor.works" :key="work.id" class="work-item">
            <el-image 
              :src="work.cover ? work.cover.replace(/[`\s]/g, '') : '/home/banner1.png'" 
              fit="cover"
              class="work-cover"
            />
            <div class="work-info">
              <div class="work-title">{{ work.name }}</div>
              <div class="work-desc">{{ work.intro }}</div>
              <div class="work-stats">
                <span class="work-stat">{{ work.viewCount }} 播放</span>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="empty-works">
          <span>暂无作品信息</span>
        </div>
      </div>
    </div>

    <div v-else class="empty-state">
      <span>演员不存在</span>
    </div>
  </div>
</template>

<style scoped>
.actor-detail-container {
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

.avatar-image {
  flex-shrink: 0;
  width: 300px;
  height: 300px;
  border-radius: 50%;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.avatar-img {
  width: 100%;
  height: 100%;
}

.actor-info {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.actor-name {
  font-size: 32px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 12px;
}

.actor-title {
  font-size: 18px;
  color: #d2691e;
  font-weight: 500;
  margin-bottom: 24px;
}

.actor-stats {
  display: flex;
  gap: 40px;
  margin-bottom: 24px;
  padding: 20px;
  background: #f8fafc;
  border-radius: 8px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  color: #1e293b;
}

.stat-label {
  font-size: 14px;
  color: #64748b;
}

.actor-bio {
  flex: 1;
  margin-bottom: 20px;
}

.actor-bio h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 12px;
}

.actor-bio p {
  font-size: 14px;
  line-height: 1.8;
  color: #64748b;
}

.actor-dates {
  display: flex;
  gap: 32px;
  padding: 16px;
  background: #f8fafc;
  border-radius: 8px;
}

.date-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.date-label {
  font-size: 14px;
  color: #64748b;
}

.date-value {
  font-size: 14px;
  font-weight: 500;
  color: #1e293b;
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

.works-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

.work-item {
  display: flex;
  gap: 16px;
  padding: 16px;
  background: #f8fafc;
  border-radius: 12px;
  transition: all 0.2s;
}

/* .work-item:hover {
  background: #e2e8f0;
  transform: translateY(-2px);
} */

.work-cover {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  flex-shrink: 0;
}

.work-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 8px;
}

.work-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
}

.work-desc {
  font-size: 14px;
  color: #64748b;
  line-height: 1.5;
}

.work-stats {
  margin-top: 8px;
}

.work-stat {
  font-size: 12px;
  color: #94a3b8;
}

.empty-works {
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
  .actor-detail-container {
    padding: 20px 16px;
  }

  .detail-content {
    padding: 24px;
  }

  .detail-header {
    flex-direction: column;
    gap: 24px;
    align-items: center;
  }

  .avatar-image {
    width: 200px;
    height: 200px;
  }

  .actor-name {
    font-size: 24px;
    text-align: center;
  }

  .actor-title {
    text-align: center;
  }

  .actor-stats {
    width: 100%;
    justify-content: space-around;
  }

  .actor-dates {
    flex-direction: column;
    gap: 12px;
  }

  .works-list {
    grid-template-columns: 1fr;
  }

  .work-item {
    flex-direction: column;
    align-items: center;
    text-align: center;
  }

  .work-cover {
    width: 120px;
    height: 120px;
  }
}
</style>

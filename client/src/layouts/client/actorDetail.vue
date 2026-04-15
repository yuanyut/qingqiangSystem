<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { getActorDetail } from '@/api/actor'
import { toggleLike, toggleFavorite, addBehavior, checkBehavior } from '@/api/behavior'
import { useUserInfoStore } from '@/stores/userInfo'
import { ArrowLeft, Loading } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const actorId = ref<number>(parseInt(route.params.id as string))
const actor = ref<any>(null)
const loading = ref(true)
const isLiked = ref(false)
const isFavorited = ref(false)
const userStore = useUserInfoStore()

const goBack = () => {
  window.history.back()
}

const loadActorDetail = async () => {
  try {
    loading.value = true
    actor.value = null // 清空旧数据
    
    // 增加浏览次数
    await addBehavior('actor', actorId.value, 'view')
    
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
      
      if (userStore.isLoggedIn) {
        const likeRes = await checkBehavior('actor', actorId.value, 'like')
        if (likeRes.code === 200) {
          isLiked.value = likeRes.data.isLiked || false
        }
        
        const favoriteRes = await checkBehavior('actor', actorId.value, 'favorite')
        if (favoriteRes.code === 200) {
          isFavorited.value = favoriteRes.data.isFavorited || false
        }
      }
    }
  } catch (error) {
    console.error('加载演员详情失败:', error)
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
    const res = await toggleLike('actor', actorId.value)
    if (res.code === 200) {
      isLiked.value = res.data.isLiked ||false
      actor.value.likeCount = res.data.isLiked ? actor.value.likeCount + 1 : actor.value.likeCount - 1
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
    const res = await toggleFavorite('actor', actorId.value)
    if (res.code === 200) {
      isFavorited.value = res.data.isFavorited ||false
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
    actorId.value = parseInt(newId as string)
    isLiked.value = false
    isFavorited.value = false
    // 只有当组件已经挂载后才重新加载，避免重复调用
    if (isMounted.value) {
      loadActorDetail()
    }
  }
})

const isMounted = ref(false)
onMounted(() => {
  loadActorDetail()
  isMounted.value = true
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

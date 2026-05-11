<template>
    <div class="my-likes">
        <!-- 头部 -->
        <div class="section-header">
            <h3>我的点赞</h3>
            <div class="header-actions">
                <span class="likes-count">{{ chuliList.length }} 个赞</span>
                <button v-if="chuliList.length > 0" class="clear-btn" @click="clearAll">
                    <svg class="clear-icon" viewBox="0 0 24 24" fill="none">
                        <path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z" fill="currentColor"/>
                    </svg>
                    清空
                </button>
            </div>
        </div>

        <!-- 筛选栏 -->
        <div class="filter-bar">
            <div 
                v-for="(item, index) in cate" 
                :key="index" 
                class="filter-item"
                :class="{ active: currentCate === item.type }"
                @click="chuliCate(item.type)"
            >
                {{ item.name }}
            </div>
        </div>

        <!-- 列表 -->
        <div class="favorites-list" v-if="chuliList.length > 0">
            <div v-for="item in chuliList" :key="item.id" class="favorite-card" @click="goToDetail(item.targetType, item.targetId)">
                <!-- 封面图 -->
                <div class="card-cover">
                    <img :src="item.coverUrl || '/default-cover.jpg'" :alt="item.title">
                    <div class="card-type" :class="item.targetType">
                        {{ getTypeName(item.targetType) }}
                    </div>
                </div>

                <!-- 信息区 -->
                <div class="card-info">
                    <h4 class="card-title">{{ item.title }}</h4>
                    <!-- <p class="card-subtitle">{{ item.subtitle }}</p> -->
                    <div class="card-meta">
                        <span class="collect-time">
                            <svg class="time-icon" viewBox="0 0 24 24" fill="none">
                                <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 4c1.1 0 2 .9 2 2s-.9 2-2 2-2-.9-2-2 .9-2 2-2zm0 13c-2.33 0-4.31-1.46-5.11-3.5h10.22c-.8 2.04-2.78 3.5-5.11 3.5z" fill="currentColor"/>
                            </svg>
                            点赞于 {{ item.time }}
                        </span>
                    </div>
                </div>

                <!-- 操作区 -->
                <button class="cancel-btn" @click.stop="cancle(item.id)">
                    <svg class="cancel-icon" viewBox="0 0 24 24" fill="none">
                        <path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z" fill="currentColor"/>
                    </svg>
                    取消点赞
                </button>
            </div>
        </div>
        
        <div class="empty-state" v-else>
            <div class="empty-icon">📭</div>
            <p class="empty-text">暂无点赞内容</p>
            <p class="empty-hint">点赞你喜欢的剧目、名家或资讯，它们会出现在这里</p>
            <button class="browse-btn" @click="goToRecommend">去逛逛</button>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, reactive, onMounted, onActivated } from 'vue'
import { useRouter } from 'vue-router'
import { getUserLikes, deleteBehavior, clearBehavior } from '@/api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 点赞列表数据
const favorites = ref([])
const loading = ref(false)

// 加载点赞数据
const loadLikes = async () => {
  try {
    loading.value = true
    const res = await getUserLikes()
    if (res.code === 200) {
      favorites.value = res.data.list || []
    } else {
      ElMessage.error('获取点赞数据失败')
    }
  } catch (error) {
    console.error('加载点赞数据失败:', error)
    ElMessage.error('加载失败，请检查网络')
  } finally {
    loading.value = false
  }
}

// 页面加载时获取数据
onMounted(() => {
  loadLikes()
})

// 页面激活时重新加载数据（解决从其他页面点赞后返回个人中心数据不更新的问题）
onActivated(() => {
  loadLikes()
})

const cate = reactive([
    {
        name: "全部",
        type: "all"
    },
    {
        name: "剧目",
        type: "drama"
    },
    {
        name: "名家",
        type: "actor"
    },
    {
        name: "资讯",
        type: "news"
    },
    {
        name: "文化",
        type: "culture"
    },
])

const currentCate = ref('all')

const chuliList = computed(() => {
    if (currentCate.value === 'all') {
        return favorites.value
    }
    return favorites.value.filter(item => item.targetType === currentCate.value)
})

const chuliCate = function (cateType) {
    console.log(cateType)
    currentCate.value = cateType
}

console.log("liebiao", chuliList.value)

const cancle = async (id) => {
  try {
    await deleteBehavior(id)
    favorites.value = favorites.value.filter(item => item.id !== id)
    ElMessage.success('取消点赞成功')
  } catch (error) {
    console.error('取消点赞失败:', error)
    ElMessage.error('取消点赞失败，请重试')
  }
}

const clearAll = async () => {
  try {
    await clearBehavior('like')
    favorites.value = []
    ElMessage.success('清空点赞成功')
  } catch (error) {
    console.error('清空点赞失败:', error)
    ElMessage.error('清空点赞失败，请重试')
  }
}

// 获取类型名称
const getTypeName = (type) => {
    const typeMap = {
        drama: '剧目',
        actor: '名家',
        news: '资讯',
        culture: '文化'
    }
    return typeMap[type] || '其他'
}

// 去推荐页
const goToRecommend = () => {
  // TODO: 跳转到推荐广场
  console.log('去推荐页')
}

// 跳转到详情页
const goToDetail = (targetType, targetId) => {
  router.push(`/${targetType}/${targetId}`)
}
</script>

<style scoped>
.my-likes {
    
    margin: 0 auto;
    padding: 32px 24px;
    background: linear-gradient(135deg, #fefaf5 0%, #fff9f2 100%);
    min-height: 100vh;
}

/* 头部样式 */
.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 32px;
    padding-bottom: 16px;
    border-bottom: 2px solid #f0e2d0;
}

.header-actions {
    display: flex;
    align-items: center;
    gap: 16px;
}

.clear-btn {
    display: inline-flex;
    align-items: center;
    gap: 6px;
    padding: 8px 16px;
    background: transparent;
    border: 1px solid #e6d5c4;
    border-radius: 20px;
    color: #9b8570;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s ease;
}

.clear-btn:hover {
    background: #fef5e8;
    border-color: #b87c4e;
    color: #b87c4e;
}

.clear-icon {
    width: 14px;
    height: 14px;
}

.section-header h3 {
    font-size: 28px;
    font-weight: 600;
    margin: 0;
    background: linear-gradient(135deg, #3a2c21, #8b5a3a);
    background-clip: text;
    -webkit-background-clip: text;
    color: transparent;
    display: flex;
    align-items: center;
    gap: 12px;
}

.header-icon {
    font-size: 28px;
}

.likes-count {
    font-size: 14px;
    background: #f5ede2;
    padding: 4px 12px;
    border-radius: 20px;
    color: #b87c4e;
    font-weight: 500;
}

/* 筛选栏样式 */
.filter-bar {
    display: flex;
    gap: 12px;
    margin-bottom: 32px;
    flex-wrap: wrap;
    border-bottom: 1px solid #f0e2d0;
    padding-bottom: 16px;
}

.filter-item {
    padding: 8px 24px;
    background: transparent;
    border-radius: 40px;
    cursor: pointer;
    transition: all 0.3s ease;
    font-size: 15px;
    font-weight: 500;
    color: #7a6856;
    position: relative;
}

.filter-item:hover {
    color: #b87c4e;
    background: #f5ede2;
}

.filter-item.active {
    color: #b87c4e;
    background: #f5ede2;
}

.filter-item.active::after {
    content: '';
    position: absolute;
    bottom: -17px;
    left: 50%;
    transform: translateX(-50%);
    width: 30px;
    height: 3px;
    background: #b87c4e;
    border-radius: 2px;
}

/* 收藏列表 */
.favorites-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.favorite-card {
    display: flex;
    align-items: center;
    gap: 20px;
    background: white;
    border-radius: 20px;
    padding: 20px;
    transition: all 0.3s cubic-bezier(0.2, 0, 0, 1);
    border: 1px solid #f0e6dc;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.02);
    position: relative;
}

.favorite-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 12px 24px -12px rgba(98, 67, 41, 0.12);
    border-color: #e6d5c4;
}

/* 封面图 */
.card-cover {
    position: relative;
    width: 100px;
    height: 100px;
    flex-shrink: 0;
    border-radius: 16px;
    overflow: hidden;
    background: #f5ede2;
}

.card-cover img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.favorite-card:hover .card-cover img {
    transform: scale(1.05);
}

.card-type {
    position: absolute;
    top: 8px;
    left: 8px;
    padding: 4px 8px;
    border-radius: 8px;
    font-size: 10px;
    font-weight: 600;
    color: white;
    backdrop-filter: blur(4px);
}

.card-type.drama {
    background: rgba(184, 124, 78, 0.9);
}

.card-type.actor {
    background: rgba(100, 68, 42, 0.9);
}

.card-type.news {
    background: rgba(80, 120, 100, 0.9);
}

.card-type.culture {
    background: rgba(180, 100, 80, 0.9);
}

/* 信息区 */
.card-info {
    flex: 1;
    min-width: 0;
}

.card-title {
    font-size: 18px;
    font-weight: 600;
    margin: 0 0 8px 0;
    color: #3a2c21;
    transition: color 0.2s;
}

.favorite-card:hover .card-title {
    color: #b87c4e;
}

.card-subtitle {
    font-size: 14px;
    color: #9b8570;
    margin: 0 0 12px 0;
    line-height: 1.4;
}

.card-meta {
    display: flex;
    align-items: center;
}

.collect-time {
    display: inline-flex;
    align-items: center;
    gap: 6px;
    font-size: 12px;
    color: #b5a084;
    background: #faf5ed;
    padding: 4px 12px;
    border-radius: 20px;
}

.time-icon {
    width: 14px;
    height: 14px;
    color: #b87c4e;
}

/* 取消点赞按钮 */
.cancel-btn {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    padding: 10px 20px;
    background: transparent;
    border: 1px solid #e6d5c4;
    border-radius: 40px;
    color: #9b8570;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s ease;
    flex-shrink: 0;
}

.cancel-btn:hover {
    background: #fef5e8;
    border-color: #b87c4e;
    color: #b87c4e;
    transform: scale(1.02);
}

.cancel-icon {
    width: 16px;
    height: 16px;
}

/* 空状态 */
.empty-state {
    text-align: center;
    padding: 80px 20px;
    background: white;
    border-radius: 24px;
    border: 1px solid #f0e6dc;
}

.empty-icon {
    font-size: 64px;
    margin-bottom: 20px;
    opacity: 0.6;
}

.empty-text {
    font-size: 18px;
    font-weight: 500;
    color: #3a2c21;
    margin: 0 0 8px 0;
}

.empty-hint {
    font-size: 14px;
    color: #b5a084;
    margin: 0 0 24px 0;
}

.browse-btn {
    padding: 12px 32px;
    background: linear-gradient(135deg, #b87c4e, #9b623c);
    border: none;
    border-radius: 40px;
    color: white;
    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(184, 124, 78, 0.3);
}

.browse-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(184, 124, 78, 0.4);
}

/* 响应式设计 */
@media (max-width: 768px) {
    .my-likes {
        padding: 20px 16px;
    }
    
    .section-header h3 {
        font-size: 24px;
    }
    
    .header-icon {
        font-size: 24px;
    }
    
    .favorite-card {
        flex-wrap: wrap;
        padding: 16px;
    }
    
    .card-cover {
        width: 80px;
        height: 80px;
    }
    
    .card-title {
        font-size: 16px;
    }
    
    .cancel-btn {
        width: 100%;
        justify-content: center;
        margin-top: 8px;
    }
    
    .filter-item {
        padding: 6px 16px;
        font-size: 14px;
    }
}

@media (max-width: 480px) {
    .filter-bar {
        gap: 8px;
    }
    
    .filter-item {
        padding: 4px 12px;
        font-size: 13px;
    }
    
    .card-info {
        flex: 1;
    }
}
</style>
<script setup>
import { ref, computed ,reactive, onMounted, onActivated} from 'vue'
import { useRouter } from 'vue-router'
import { getUserHistory, deleteBehavior, clearBehavior } from '@/api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 浏览历史数据
const browseHistory = ref([])
const loading = ref(false)

// 加载浏览历史数据
const loadHistory = async () => {
  try {
    loading.value = true
    const res = await getUserHistory()
    if (res.code === 200) {
      browseHistory.value = res.data.list || []
    } else {
      ElMessage.error('获取浏览历史失败')
    }
  } catch (error) {
    console.error('加载浏览历史失败:', error)
    ElMessage.error('加载失败，请检查网络')
  } finally {
    loading.value = false
  }
}

// 页面加载时获取数据
onMounted(() => {
  loadHistory()
})

// 页面激活时重新加载数据（解决从其他页面浏览后返回个人中心数据不更新的问题）
onActivated(() => {
  loadHistory()
})

// 筛选选项
const filterOptions = reactive([
  { value: 'all', label: '全部' },
  { value: 'drama', label: '剧目' },
  { value: 'actor', label: '名家' },
  { value: 'culture', label: '文化' },
  { value: 'news', label: '资讯' }
])

const currentTab=ref('all')
// const filterList=reactive([])
const filterLists=(value)=>{
  // filterList.length = 0;
  
  currentTab.value=value
  console.log(currentTab.value)
}
const filterList=computed(()=>{
  
    if(currentTab.value==='all'){
      return browseHistory.value
    }
    else{
      return browseHistory.value.filter(item => item.targetType === currentTab.value)
    }
  })
console.log('这是筛选'+filterList)
const lableChange=(value)=>{
  if(value==='drama')
    return '剧目'
  if(value ==='actor')
  return '名家'
   if(value==='culture')
    return '文化'
  if(value ==='news')
  return '资讯'
}
const clearBtn=async()=>{
  try {
    await clearBehavior('view')
    browseHistory.value = []
    ElMessage.success('清空历史记录成功')
  } catch (error) {
    console.error('清空历史记录失败:', error)
    ElMessage.error('清空历史记录失败，请重试')
  }
}
const deleteBtn=async(id)=>{
  try {
    await deleteBehavior(id)
    const index = browseHistory.value.findIndex(item => item.id === id)
    if (index !== -1) {
      browseHistory.value.splice(index, 1)
    }
    ElMessage.success('删除历史记录成功')
  } catch (error) {
    console.error('删除历史记录失败:', error)
    ElMessage.error('删除历史记录失败，请重试')
  }
}

// 跳转到详情页
const goToDetail = (targetType, targetId) => {
  router.push(`/${targetType}/${targetId}`)
}
</script>
<template>
  <div class="browse-history">
    <!-- 头部 -->
    <div class="history-header">
      
      <button class="clear-btn" @click="clearBtn">清空历史</button>
    </div>
    
    <!-- 筛选栏 -->
    <div class="filter-bar">
      <button
        v-for="filter in filterOptions"
        :key="filter.value"
        class="filter-btn"
        :class="{ active: currentTab === filter.value }"
        @click="filterLists(filter.value)"
      >
        {{ filter.label }}
      </button>
    </div>
    
    <!-- 历史列表 -->
    <div class="history-list">
      <div v-for="item in filterList" :key="item.id" class="date-group">
        <div class="history-items">
          <div class="history-item" @click="goToDetail(item.targetType, item.targetId)">
            <div class="item-cover">
              <img :src="item.coverUrl" :alt="item.title">
              <div class="cover-type" :class="item.targetType">
                {{ lableChange(item.targetType)}}
              </div>
            </div>
            
            <div class="item-info">
              <div class="item-title">{{ item.title }}</div>
              <!-- <div class="item-subtitle">{{ item.subtitle }}</div> -->
              <div class="item-meta">
                <span class="item-duration">{{ item.duration }}</span>
                <span class="item-time">{{ item.time }}</span>
              </div>
            </div>
            
            <div class="item-actions">
              <button class="action-btn" @click.stop="deleteBtn(item.id)">删除</button>
            </div>
          </div>
        </div>
      </div>
      <div v-if="filterList==0">
        <el-empty description="暂无数据" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.browse-history {
  /* max-width: 1200px; */
  margin: 0 auto;
  padding: 32px 24px;
  background: linear-gradient(135deg, #fefaf5 0%, #fff9f2 100%);
  min-height: 100vh;
}

/* 头部样式 */
.history-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding-bottom: 16px;
  border-bottom: 2px solid #f0e2d0;
}

.history-header h3 {
  font-size: 28px;
  font-weight: 600;
  margin: 0;
  background: linear-gradient(135deg, #3a2c21, #8b5a3a);
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
}

.clear-btn {
  padding: 8px 20px;
  background: transparent;
  border: 1px solid #e6d5c4;
  border-radius: 40px;
  cursor: pointer;
  color: #9b8570;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
}

.clear-btn:hover {
  background: #fef5e8;
  border-color: #b87c4e;
  color: #b87c4e;
  transform: scale(1.02);
}

/* 筛选栏 */
.filter-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 32px;
  flex-wrap: wrap;
  border-bottom: 1px solid #f0e2d0;
  padding-bottom: 16px;
}

.filter-btn {
  padding: 8px 24px;
  background: transparent;
  border: none;
  border-radius: 40px;
  cursor: pointer;
  font-size: 15px;
  font-weight: 500;
  color: #7a6856;
  transition: all 0.3s ease;
  position: relative;
}

.filter-btn:hover {
  color: #b87c4e;
  background: #f5ede2;
}

.filter-btn.active {
  color: #b87c4e;
  background: #f5ede2;
}

.filter-btn.active::after {
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

/* 日期分组 */
.date-group {
  margin-bottom: 32px;
}

.date-title {
  font-size: 15px;
  font-weight: 600;
  color: #3a2c21;
  margin-bottom: 16px;
  padding-left: 12px;
  border-left: 4px solid #b87c4e;
}

/* 历史列表 */
.history-items {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.history-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px;
  background: white;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.2, 0, 0, 1);
  border: 1px solid #f0e6dc;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.02);
}

.history-item:hover {
  transform: translateX(4px);
  box-shadow: 0 8px 20px -8px rgba(98, 67, 41, 0.12);
  border-color: #e6d5c4;
}

/* 封面图 */
.item-cover {
  position: relative;
  width: 80px;
  height: 80px;
  flex-shrink: 0;
  border-radius: 16px;
  overflow: hidden;
  background: #f5ede2;
}

.item-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.history-item:hover .item-cover img {
  transform: scale(1.05);
}

.cover-type {
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

.cover-type.drama {
  background: rgba(184, 124, 78, 0.9);
}

.cover-type.interview {
  background: rgba(140, 100, 120, 0.9);
}

.cover-type.culture {
  background: rgba(180, 100, 80, 0.9);
}

.cover-type.news {
  background: rgba(80, 120, 100, 0.9);
}

/* 信息区 */
.item-info {
  flex: 1;
  min-width: 0;
}

.item-title {
  font-size: 16px;
  font-weight: 600;
  color: #3a2c21;
  margin-bottom: 6px;
  transition: color 0.2s;
}

.history-item:hover .item-title {
  color: #b87c4e;
}

.item-subtitle {
  font-size: 13px;
  color: #9b8570;
  margin-bottom: 8px;
  line-height: 1.4;
}

.item-meta {
  display: flex;
  gap: 16px;
  font-size: 12px;
}

.item-duration {
  color: #b87c4e;
}

.item-time {
  color: #b5a084;
}

/* 操作按钮 */
.item-actions {
  opacity: 0;
  transition: opacity 0.2s ease;
}

.history-item:hover .item-actions {
  opacity: 1;
}

.action-btn {
  padding: 8px 16px;
  background: transparent;
  border: 1px solid #e6d5c4;
  border-radius: 40px;
  cursor: pointer;
  font-size: 13px;
  font-weight: 500;
  color: #9b8570;
  transition: all 0.2s ease;
}

.action-btn:hover {
  background: #ff4d4f;
  border-color: #ff4d4f;
  color: white;
  transform: scale(1.02);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .browse-history {
    padding: 20px 16px;
  }
  
  .history-header h3 {
    font-size: 24px;
  }
  
  .history-item {
    padding: 12px;
  }
  
  .item-cover {
    width: 70px;
    height: 70px;
  }
  
  .item-title {
    font-size: 15px;
  }
  
  .item-subtitle {
    font-size: 12px;
  }
  
  .item-actions {
    opacity: 1;
  }
  
  .action-btn {
    padding: 6px 12px;
    font-size: 12px;
  }
  
  .filter-btn {
    padding: 6px 16px;
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .filter-bar {
    gap: 8px;
  }
  
  .filter-btn {
    padding: 4px 12px;
    font-size: 13px;
  }
  
  .item-meta {
    flex-direction: column;
    gap: 4px;
  }
}
</style>
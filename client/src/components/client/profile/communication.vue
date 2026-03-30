<template>
  <div class="my-comments">
    <!-- 头部 -->
    <div class="comments-header">
      <h3 class="header-title">
        <span class="title-icon">💬</span>
        我的评论
      </h3>
      
      <!-- 普通模式：管理按钮 -->
      <button v-if="!isManageMode" class="manage-btn" @click="enterManageMode">
        管理评论
      </button>
      
      <!-- 管理模式：操作栏 -->
      <div v-else class="manage-bar">
        <span class="selected-count">已选择 {{ selectedIds.length }} 条</span>
        <div class="manage-actions">
          <button class="action-btn select-all-btn" @click="toggleSelectAll">
            {{ isAllSelected ? '取消全选' : '全选' }}
          </button>
          <button class="action-btn delete-batch-btn" @click="batchDelete">
            批量删除
          </button>
          <button class="action-btn hide-batch-btn" @click="batchHide">
            批量隐藏
          </button>
          <button class="action-btn cancel-btn" @click="exitManageMode">
            完成
          </button>
        </div>
      </div>
    </div>

    <!-- 筛选栏 -->
    <div class="filter-bar">
      <div class="filter-tabs">
        <span 
          :class="['filter-tab', { active: filterStatus === 'all' }]"
          @click="filterStatus = 'all'"
        >全部</span>
        <span 
          :class="['filter-tab', { active: filterStatus === 'public' }]"
          @click="filterStatus = 'public'"
        >公开</span>
        <span 
          :class="['filter-tab', { active: filterStatus === 'hidden' }]"
          @click="filterStatus = 'hidden'"
        >仅自己可见</span>
      </div>
      
      <div class="sort-select">
        <select v-model="sortType">
          <option value="time">按时间排序</option>
          <option value="like">按点赞排序</option>
          <option value="reply">按回复排序</option>
        </select>
      </div>
    </div>

    <!-- 评论列表 -->
    <div class="comments-list" v-if="filteredComments.length > 0">
      <div 
        v-for="comment in filteredComments" 
        :key="comment.id" 
        class="comment-item"
        :class="{ 'hidden-comment': comment.status === 'hidden' }"
      >
        <!-- 管理模式：复选框 -->
        <div v-if="isManageMode" class="checkbox-wrapper">
          <input 
            type="checkbox" 
            :checked="selectedIds.includes(comment.id)"
            @change="toggleSelect(comment.id)"
          >
        </div>
        
        <div class="comment-content">
          <!-- 评论目标 -->
          <div class="comment-target">
            <span class="target-label">对</span>
            <span class="target-name">{{ comment.targetName }}</span>
            <span class="target-label">的评论：</span>
            <span v-if="comment.status === 'hidden'" class="hidden-badge">仅自己可见</span>
          </div>
          
          <!-- 评论正文 -->
          <p class="comment-text">{{ comment.content }}</p>
          
          <!-- 评论元信息 -->
          <div class="comment-meta">
            <span class="meta-time">发布于 {{ comment.createTime }}</span>
            <span class="meta-stats">
              <span class="stat-item">
                <span class="stat-icon">❤️</span>
                {{ formatNumber(comment.likeCount) }}
              </span>
              <span class="stat-item">
                <span class="stat-icon">💬</span>
                {{ formatNumber(comment.replyCount) }}
              </span>
            </span>
          </div>
          
          <!-- 普通模式：操作按钮 -->
          <div class="comment-actions" v-if="!isManageMode">
            <button class="action-btn edit-btn" @click="handleEdit(comment)">
              编辑
            </button>
            <button class="action-btn delete-btn" @click="handleDelete(comment)">
              删除
            </button>
            <button 
              v-if="comment.status === 'hidden'" 
              class="action-btn public-btn" 
              @click="handlePublic(comment)"
            >
              公开
            </button>
            <button 
              v-else 
              class="action-btn hide-btn" 
              @click="handleHide(comment)"
            >
              隐藏
            </button>
            <button class="action-btn view-btn" @click="handleViewOriginal(comment)">
              查看原文
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 空状态 -->
    <div class="empty-state" v-else>
      <span class="empty-icon">💬</span>
      <p class="empty-text">暂无评论，去首页发表你的看法吧～</p>
      <button class="go-home-btn" @click="goToHome">去首页</button>
    </div>

    <!-- 分页 -->
    <div class="pagination" v-if="total > pageSize">
      <button 
        class="page-btn" 
        :disabled="currentPage === 1"
        @click="changePage(currentPage - 1)"
      >
        上一页
      </button>
      <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>
      <button 
        class="page-btn" 
        :disabled="currentPage === totalPages"
        @click="changePage(currentPage + 1)"
      >
        下一页
      </button>
    </div>

    <!-- 编辑评论弹窗 -->
    <EditCommentDialog 
      v-model:visible="editDialogVisible"
      title="编辑评论"
      :comment="editingComment"
      @confirm="handleUpdateComment"
    />

    <!-- 删除确认弹窗 -->
    <ConfirmDialog
      v-model:visible="deleteDialogVisible"
      title="删除评论"
      message="确定要删除这条评论吗？删除后不可恢复。"
      @confirm="handleConfirmDelete"
    />

    <!-- 批量删除确认弹窗 -->
    <ConfirmDialog
      v-model:visible="batchDeleteDialogVisible"
      title="批量删除"
      :message="`确定要删除选中的 ${selectedIds.length} 条评论吗？删除后不可恢复。`"
      @confirm="handleConfirmBatchDelete"
    />

    <!-- 批量隐藏确认弹窗 -->
    <ConfirmDialog
      v-model:visible="batchHideDialogVisible"
      title="批量隐藏"
      :message="`确定要隐藏选中的 ${selectedIds.length} 条评论吗？隐藏后仅自己可见。`"
      @confirm="handleConfirmBatchHide"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import EditCommentDialog from '@/utils/EditCommentDialog.vue'
import ConfirmDialog from '@/utils/ConfirmDialog.vue'

const router = useRouter()

// ==================== 数据 ====================
const comments = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

// 管理模式状态
const isManageMode = ref(false)
const selectedIds = ref([])

// 筛选和排序
const filterStatus = ref('all') // all, public, hidden
const sortType = ref('time') // time, like, reply

// 弹窗状态
const editDialogVisible = ref(false)
const deleteDialogVisible = ref(false)
const batchDeleteDialogVisible = ref(false)
const batchHideDialogVisible = ref(false)
const editingComment = ref(null)
const deletingComment = ref(null)

// ==================== 计算属性 ====================
const totalPages = computed(() => Math.ceil(total.value / pageSize.value))

// 是否全选
const isAllSelected = computed(() => {
  return filteredComments.value.length > 0 && 
         selectedIds.value.length === filteredComments.value.length
})

// 筛选后的评论
const filteredComments = computed(() => {
  let result = [...comments.value]
  
  // 按状态筛选
  if (filterStatus.value === 'public') {
    result = result.filter(c => c.status !== 'hidden')
  } else if (filterStatus.value === 'hidden') {
    result = result.filter(c => c.status === 'hidden')
  }
  
  // 排序
  if (sortType.value === 'like') {
    result.sort((a, b) => b.likeCount - a.likeCount)
  } else if (sortType.value === 'reply') {
    result.sort((a, b) => b.replyCount - a.replyCount)
  } else {
    result.sort((a, b) => new Date(b.createTime) - new Date(a.createTime))
  }
  
  return result
})

// ==================== 模拟数据 ====================
const mockComments = [
  {
    id: 1,
    targetId: 101,
    targetName: '《三滴血》',
    targetType: 'drama',
    content: '李淑芳老师的唱腔太震撼了，百看不厌！',
    createTime: '2024-03-25',
    likeCount: 1289,
    replyCount: 56,
    status: 'public'
  },
  {
    id: 2,
    targetId: 102,
    targetName: '《火焰驹》',
    targetType: 'drama',
    content: '李小锋老师的表演真是入木三分！',
    createTime: '2024-03-20',
    likeCount: 856,
    replyCount: 23,
    status: 'public'
  },
  {
    id: 3,
    targetId: 201,
    targetName: '秦腔名家李淑芳专访',
    targetType: 'video',
    content: '终于看到李老师的专访了，受益匪浅！',
    createTime: '2024-03-18',
    likeCount: 432,
    replyCount: 18,
    status: 'hidden'
  }
]

// ==================== 加载数据 ====================
const loadComments = () => {
  comments.value = [...mockComments]
  total.value = mockComments.length
}

// ==================== 管理模式 ====================
const enterManageMode = () => {
  isManageMode.value = true
  selectedIds.value = []
}

const exitManageMode = () => {
  isManageMode.value = false
  selectedIds.value = []
}

const toggleSelect = (id) => {
  const index = selectedIds.value.indexOf(id)
  if (index > -1) {
    selectedIds.value.splice(index, 1)
  } else {
    selectedIds.value.push(id)
  }
}

const toggleSelectAll = () => {
  if (isAllSelected.value) {
    selectedIds.value = []
  } else {
    selectedIds.value = filteredComments.value.map(c => c.id)
  }
}

// ==================== 批量操作 ====================
const batchDelete = () => {
  if (selectedIds.value.length === 0) {
    alert('请选择要删除的评论')
    return
  }
  batchDeleteDialogVisible.value = true
}

const handleConfirmBatchDelete = () => {
  // 删除选中的评论
  comments.value = comments.value.filter(c => !selectedIds.value.includes(c.id))
  total.value = comments.value.length
  
  alert(`成功删除 ${selectedIds.value.length} 条评论`)
  selectedIds.value = []
  exitManageMode()
}

const batchHide = () => {
  if (selectedIds.value.length === 0) {
    alert('请选择要隐藏的评论')
    return
  }
  batchHideDialogVisible.value = true
}

const handleConfirmBatchHide = () => {
  // 更新本地数据
  comments.value = comments.value.map(c => {
    if (selectedIds.value.includes(c.id)) {
      return { ...c, status: 'hidden' }
    }
    return c
  })
  
  alert(`成功隐藏 ${selectedIds.value.length} 条评论`)
  selectedIds.value = []
  exitManageMode()
}

// ==================== 单条操作 ====================
const handleEdit = (comment) => {
  editingComment.value = { ...comment }
  editDialogVisible.value = true
}

const handleUpdateComment = (newContent) => {
  const index = comments.value.findIndex(c => c.id === editingComment.value.id)
  if (index !== -1) {
    comments.value[index].content = newContent
  }
  alert('评论修改成功')
  editDialogVisible.value = false
}

const handleDelete = (comment) => {
  deletingComment.value = comment
  deleteDialogVisible.value = true
}

const handleConfirmDelete = () => {
  const index = comments.value.findIndex(c => c.id === deletingComment.value.id)
  if (index !== -1) {
    comments.value.splice(index, 1)
    total.value--
  }
  alert('评论删除成功')
  deleteDialogVisible.value = false
}

const handleHide = (comment) => {
  const index = comments.value.findIndex(c => c.id === comment.id)
  if (index !== -1) {
    comments.value[index].status = 'hidden'
  }
  alert('评论已隐藏，仅自己可见')
}

const handlePublic = (comment) => {
  const index = comments.value.findIndex(c => c.id === comment.id)
  if (index !== -1) {
    comments.value[index].status = 'public'
  }
  alert('评论已公开')
}

const handleViewOriginal = (comment) => {
  switch (comment.targetType) {
    case 'drama':
      router.push(`/drama/${comment.targetId}`)
      break
    case 'video':
      router.push(`/video/${comment.targetId}`)
      break
    default:
      router.push(`/detail/${comment.targetId}`)
  }
}

const goToHome = () => {
  router.push('/')
}

// ==================== 分页 ====================
const changePage = (page) => {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
  // 分页切换时的数据加载（模拟）
  loadComments()
}

// ==================== 格式化 ====================
const formatNumber = (num) => {
  if (num >= 10000) {
    return (num / 10000).toFixed(1) + 'w'
  }
  return num.toString()
}

onMounted(() => {
  loadComments()
})
</script>

<style scoped>
.my-comments {
  background: #fff;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

/* 头部样式 */
.comments-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0e6dc;
}

.header-title {
  font-size: 20px;
  font-weight: 600;
  color: #2c241a;
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 0;
}

.title-icon {
  font-size: 24px;
}

.manage-btn {
  padding: 6px 16px;
  background: transparent;
  border: 1px solid #d4451e;
  color: #d4451e;
  border-radius: 20px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.manage-btn:hover {
  background: #d4451e;
  color: #fff;
}

/* 管理模式样式 */
.manage-bar {
  display: flex;
  align-items: center;
  gap: 16px;
}

.selected-count {
  font-size: 14px;
  color: #d4451e;
  font-weight: 500;
}

.manage-actions {
  display: flex;
  gap: 12px;
}

.select-all-btn {
  background: #f5ede3;
  color: #5a3e2b;
  border: none;
  padding: 6px 12px;
  border-radius: 16px;
  cursor: pointer;
}

.delete-batch-btn {
  background: #e74c3c;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 16px;
  cursor: pointer;
}

.delete-batch-btn:hover {
  background: #c0392b;
}

.hide-batch-btn {
  background: #f5a623;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 16px;
  cursor: pointer;
}

.hide-batch-btn:hover {
  background: #d4451e;
}

.cancel-btn {
  background: transparent;
  border: 1px solid #e6d9cc;
  color: #8b7355;
  padding: 6px 12px;
  border-radius: 16px;
  cursor: pointer;
}

/* 筛选栏 */
.filter-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0e6dc;
}

.filter-tabs {
  display: flex;
  gap: 16px;
}

.filter-tab {
  font-size: 14px;
  color: #b48c5c;
  cursor: pointer;
  padding: 4px 0;
  transition: all 0.3s ease;
}

.filter-tab:hover {
  color: #d4451e;
}

.filter-tab.active {
  color: #d4451e;
  border-bottom: 2px solid #d4451e;
}

.sort-select select {
  padding: 4px 8px;
  border: 1px solid #e6d9cc;
  border-radius: 6px;
  background: #fefaf5;
  color: #5a3e2b;
  font-size: 13px;
  cursor: pointer;
}

/* 评论列表 */
.comments-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.comment-item {
  display: flex;
  background: #fefaf5;
  border-radius: 12px;
  padding: 20px;
  transition: all 0.3s ease;
  border: 1px solid #f5ede3;
}

.comment-item:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transform: translateY(-2px);
}

/* 复选框样式 */
.checkbox-wrapper {
  margin-right: 16px;
  display: flex;
  align-items: center;
}

.checkbox-wrapper input {
  width: 18px;
  height: 18px;
  cursor: pointer;
  accent-color: #d4451e;
}

.comment-content {
  flex: 1;
}

/* 隐藏评论样式 */
.hidden-comment {
  opacity: 0.7;
  background: #f9f5f0;
}

.hidden-badge {
  display: inline-block;
  padding: 2px 8px;
  background: #e6d9cc;
  color: #8b7355;
  border-radius: 12px;
  font-size: 11px;
  margin-left: 8px;
}

/* 评论目标 */
.comment-target {
  margin-bottom: 12px;
  font-size: 14px;
  color: #8b7355;
}

.target-label {
  color: #b48c5c;
}

.target-name {
  color: #d4451e;
  font-weight: 500;
  margin: 0 4px;
}

/* 评论正文 */
.comment-text {
  font-size: 16px;
  line-height: 1.6;
  color: #2c241a;
  margin: 0 0 12px 0;
  padding: 12px 0;
  border-bottom: 1px dashed #f0e6dc;
}

/* 评论元信息 */
.comment-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  font-size: 13px;
  color: #c4a27a;
}

.meta-stats {
  display: flex;
  gap: 16px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 4px;
}

.stat-icon {
  font-size: 13px;
}

/* 操作按钮 */
.comment-actions {
  display: flex;
  gap: 16px;
}

.comment-actions .action-btn {
  padding: 4px 12px;
  font-size: 13px;
  border: none;
  background: transparent;
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.edit-btn {
  color: #f5a623;
}

.edit-btn:hover {
  background: #fff4e6;
  color: #d4451e;
}

.delete-btn {
  color: #e74c3c;
}

.delete-btn:hover {
  background: #fee9e7;
  color: #c0392b;
}

.public-btn, .hide-btn {
  color: #f5a623;
}

.public-btn:hover, .hide-btn:hover {
  background: #fff4e6;
  color: #d4451e;
}

.view-btn {
  color: #3498db;
}

.view-btn:hover {
  background: #e8f4fd;
  color: #2980b9;
}

/* 空状态 */
.empty-state {
  text-align: center;
  padding: 60px 20px;
}

.empty-icon {
  font-size: 64px;
  opacity: 0.5;
  display: block;
  margin-bottom: 16px;
}

.empty-text {
  font-size: 14px;
  color: #b48c5c;
  margin-bottom: 20px;
}

.go-home-btn {
  padding: 8px 24px;
  background: linear-gradient(135deg, #d4451e, #f5a623);
  color: white;
  border: none;
  border-radius: 24px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.go-home-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(212, 69, 30, 0.4);
}

/* 分页 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid #f0e6dc;
}

.page-btn {
  padding: 6px 16px;
  background: #fefaf5;
  border: 1px solid #e6d9cc;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #5a3e2b;
}

.page-btn:hover:not(:disabled) {
  background: #d4451e;
  color: white;
  border-color: #d4451e;
}

.page-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.page-info {
  font-size: 14px;
  color: #8b7355;
}

/* 响应式 */
@media (max-width: 768px) {
  .my-comments {
    padding: 16px;
  }
  
  .comment-item {
    padding: 16px;
  }
  
  .comment-text {
    font-size: 14px;
  }
  
  .comment-meta {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
  
  .comment-actions {
    flex-wrap: wrap;
  }
  
  .manage-actions {
    flex-wrap: wrap;
  }
  
  .filter-bar {
    flex-direction: column;
    gap: 12px;
    align-items: flex-start;
  }
  
  .manage-bar {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
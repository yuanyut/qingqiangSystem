<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'

// 模拟数据
const mockComments = [
  {
    id: 1,
    targetId: 101,
    targetName: '《三滴血》',
    targetType: 'drama',
    content: '李淑芳老师的唱腔太震撼了，百看不厌！',
    createTime: '2024-03-25',
    likeCount: 1289,
    replyCount: 56
  },
  {
    id: 2,
    targetId: 102,
    targetName: '《火焰驹》',
    targetType: 'drama',
    content: '李小锋老师的表演真是入木三分！',
    createTime: '2024-03-20',
    likeCount: 856,
    replyCount: 23
  },
  {
    id: 3,
    targetId: 201,
    targetName: '秦腔名家李淑芳专访',
    targetType: 'video',
    content: '终于看到李老师的专访了，受益匪浅！',
    createTime: '2024-03-18',
    likeCount: 432,
    replyCount: 18
  },
  {
    id: 4,
    targetId: 301,
    targetName: '秦腔艺术节盛大开幕',
    targetType: 'news',
    content: '期待已久，一定要去现场看看！',
    createTime: '2024-03-15',
    likeCount: 234,
    replyCount: 9
  }
]


</script>
<template>
  <div>
    <!-- 头部 -->
    <div class="comments-header">
      <h3 class="header-title">
        <span class="title-icon">💬</span>
        我的评论
      </h3>
      <button class="manage-btn" >
        管理评论
      </button>
    </div>

    <!-- 评论列表 -->
    <div class="comments-list" >
      <div 
        v-for="comment in mockComments" 
        :key="comment.id" 
        class="comment-item"
      >
        <div class="comment-content">
          <!-- 评论目标 -->
          <div class="comment-target">
            <span class="target-label">对</span>
            <span class="target-name">{{ comment.targetName }}</span>
            <span class="target-label">的评论：</span>
          </div>
          
          <!-- 评论正文 -->
          <p class="comment-text">{{ comment.content }}</p>
          
          <!-- 评论元信息 -->
          <div class="comment-meta">
            <span class="meta-time">发布于 {{ comment.createTime }}</span>
            <span class="meta-stats">
              <span class="stat-item">
                <span class="stat-icon">❤️</span>
                <!-- {{ formatNumber(comment.likeCount) }} -->
              </span>
              <span class="stat-item">
                <span class="stat-icon">💬</span>
                <!-- {{ formatNumber(comment.replyCount) }} -->
              </span>
            </span>
          </div>
          
          <!-- 操作按钮 -->
          <div class="comment-actions">
            <button class="action-btn edit-btn" >
              编辑
            </button>
            <button class="action-btn delete-btn" >
              删除
            </button>
            <button class="action-btn view-btn">
              查看原文
            </button>
          </div>
        </div>
      </div>
    </div>

  
  </div>
</template>



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

/* 评论列表 */
.comments-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.comment-item {
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

.action-btn {
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
  
  .action-btn {
    padding: 4px 10px;
  }
}
</style>
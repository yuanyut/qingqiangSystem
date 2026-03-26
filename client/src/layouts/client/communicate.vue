<template>
  <!-- 搜索框 -->
  <div class="search-container">
    <div class="search-wrapper">
      <el-input v-model="input" placeholder="搜索剧目、名家、资讯..." class="custom-search-input">
        <template #append>
          <el-button :icon="Search" @click="search" class="search-button" />
        </template>
      </el-input>
    </div>
  </div>

  <!-- 推荐偏好设置 -->
  <div class="preference-section">
    <div class="section-header">
      <span class="section-title">推荐偏好设置</span>
    </div>
    <div class="tags-wrapper">
      <div class="tags-container">
        <el-tag 
          v-for="tag in biaoQian" 
          :key="tag" 
          class="preference-tag" 
          closable 
          :disable-transitions="false"
          @close="handleClose(tag)" 
          size="large"
          effect="plain"
        >
          {{ tag }}
        </el-tag>
        <el-button class="add-tag-btn" size="default" @click="change">
          <span class="plus-icon">+</span> 添加偏好
        </el-button>
      </div>
    </div>
    <div class="preference-note">
      <span>基于你的浏览记录，我们为你推荐以下内容</span>
    </div>
  </div>

  

  <!-- 为你推荐 -->
  <div class="recommend-section">
    <div class="section-header-with-action">
      <h3 class="section-title-secondary">为你推荐 · 猜你喜欢</h3>
      <button class="refresh-btn">换一批</button>
    </div>
    <div class="cards-grid">
      <div v-for="i in 5" :key="i" class="card-item">
        <card_tuijian image="/home/banner1.png" name="白蛇传" desc="旦角" cate="传统剧目" act="演唱者">
          <template #cardAttr>
            <div class="card-attr">
              <span class="attr-highlight">推荐度：98%</span>
              <span class="attr-desc">基于你的浏览记录</span>
            </div>
          </template>
        </card_tuijian>
      </div>
    </div>
  </div>

  <!-- 基于相似用户推荐 -->
  <div class="recommend-section">
    <div class="section-header-with-action">
      <h3 class="section-title-secondary">基于相似用户推荐</h3>
      <button class="refresh-btn">换一批</button>
    </div>
    <div class="cards-grid">
      <div v-for="i in 5" :key="i" class="card-item">
        <card_tuijian image="/home/banner1.png" name="白蛇传" desc="旦角" cate="传统剧目" act="演唱者" watch-people="观看人数" fenshu="分数" />
      </div>
    </div>
  </div>

  <!-- 热门趋势 -->
  <div class="trend-section">
    <div class="section-header-with-action">
      <h3 class="section-title-secondary">热门趋势</h3>
      <button class="more-link">更多</button>
    </div>
    <div class="trend-list">
      <div v-for="i in 5" :key="i" class="trend-item">
        <card-tend :id="i" title="《三滴血》全新复排版" desc="李淑芳领衔主演" up=95 now-hot=50 fenshu="分数" />
      </div>
    </div>
  </div>
  
  <!-- 混合推荐 -->
  <div class="mixed-section">
    <div class="section-header-with-action">
      <h3 class="section-title-secondary">为你推荐的内容流</h3>
      <button class="refresh-btn">刷新</button>
    </div><!-- 推荐tab切换 -->
  <div class="tabs-container">
    <div 
      v-for="(item, index) in tuiTab" 
      :key="index" 
      :class="{ 'tab-item': true, 'active': index == activeTab }"
      @click="activeTab = index"
    >
      {{ item }}
    </div>
  </div>
    <div class="mixed-cards">
      <tuijian_big-card title="剧目推荐" v-if="activeTab==1 || activeTab==0" class="mixed-card" />
      <tuijian_big-card title="名家推荐" v-if="activeTab==2|| activeTab==0" class="mixed-card" />
      <tuijian_big-card title="资讯推荐" v-if="activeTab==3|| activeTab==0" class="mixed-card" />
      <tuijian_big-card title="视频推荐" v-if="activeTab==4|| activeTab==0" class="mixed-card" />
    </div>
  </div>

  <!-- 编辑偏好弹窗 -->
  <div :class="{ active: showedit }" class="edit-dialog">
    <div class="dialog-mask" @click="change"></div>
    <div class="dialog-container">
      <div class="dialog-header">
        <h3 class="dialog-title">添加兴趣标签</h3>
        <button class="dialog-close" @click="change">×</button>
      </div>
      <div class="dialog-body">
        <el-form label-width="80px" class="dialog-form">
          <el-form-item label="标签名称">
            <el-input placeholder="例如：秦腔、京剧、名家访谈" />
          </el-form-item>
          <el-form-item label="偏好描述">
            <el-input type="textarea" :rows="3" placeholder="描述你对这个标签的兴趣（可选）" />
          </el-form-item>
        </el-form>
      </div>
      <div class="dialog-footer">
        <el-button class="cancel-btn" @click="change">取消</el-button>
        <el-button type="primary" class="confirm-btn" @click="change">确认添加</el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { Search } from '@element-plus/icons-vue'
import cardTend from '@/components/client/cardTend.vue';
import Card_tuijian from '@/components/client/card_tuijian.vue';
import tuijian_bigCard from '@/components/client/tuijian_bigCard.vue';
import Tuijian_bigCard from '@/components/client/tuijian_bigCard.vue';

const totalcount = ref(0)
const search = () => {
  totalcount.value = 35
}
const input = ref('')
const biaoQian = reactive([
  '传统剧目',
  '现代改编',
  '旦角唱腔',
  '生角表演',
  '名家采访'
])
const tuiTab = reactive([
  '全部',
  '剧目推荐',
  '名家推荐',
  '资讯推荐',
  '视频推荐',
])
const handleClose = (tag: string) => {
  biaoQian.splice(biaoQian.indexOf(tag), 1)
}
const showedit = ref(false)
const change = () => {
  showedit.value = !showedit.value
  console.log(showedit.value)
}
const activeTab = ref(0)
</script>

<style scoped>
/* 全局样式重置与基调 */
* {
  box-sizing: border-box;
}

/* 搜索框区域 */
.search-container {
  background: linear-gradient(135deg, #ffffff 0%, #fef9f0 100%);
  border-radius: 24px;
  padding: 24px 20px;
  margin-bottom: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.02), 0 1px 2px rgba(0, 0, 0, 0.03);
}

.search-wrapper {
  display: flex;
  justify-content: center;
}

.custom-search-input {
  width: 100%;
  max-width: 520px;
  transition: all 0.3s ease;
}

:deep(.custom-search-input .el-input__wrapper) {
  border-radius: 48px 0 0 48px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  padding: 2px 16px;
  border: 1px solid #f0ede8;
  transition: all 0.2s;
}

:deep(.custom-search-input .el-input__wrapper:hover) {
  border-color: #d9c7a7;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

:deep(.custom-search-input .el-input__wrapper.is-focus) {
  border-color: #b87c4e;
  box-shadow: 0 0 0 3px rgba(184, 124, 78, 0.1);
}

.search-button {
  border-radius: 0 48px 48px 0;
  background-color: #f5ede2;
  border: 1px solid #f0ede8;
  border-left: none;
  color: #b87c4e;
  transition: all 0.2s;
  padding: 12px 20px;
}

.search-button:hover {
  background-color: #b87c4e;
  color: white;
  border-color: #b87c4e;
}

/* 偏好设置区域 */
.preference-section {
  background: white;
  border-radius: 24px;
  padding: 24px 0 20px 0;
  margin-bottom: 20px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.03);
}

.section-header {
  padding: 0 24px;
  margin-bottom: 12px;
}

.section-title {
  font-size: 20px;
  font-weight: 600;
  background: linear-gradient(135deg, #2c241a 0%, #5c4b3a 100%);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  letter-spacing: -0.3px;
  position: relative;
  padding-left: 16px;
}

.section-title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 24px;
  background: linear-gradient(135deg, #e6b17e 0%, #b87c4e 100%);
  border-radius: 4px;
}

.tags-wrapper {
  padding: 0 20px;
  margin-bottom: 16px;
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  align-items: center;
  padding: 8px 4px;
}

.preference-tag {
  background: #faf8f4;
  border: 1px solid #eae4da;
  color: #4a3b2c;
  font-weight: 500;
  padding: 8px 20px;
  border-radius: 40px;
  font-size: 14px;
  transition: all 0.2s;
}

.preference-tag:hover {
  background: #fff6ed;
  border-color: #d9c2a4;
  transform: translateY(-1px);
}

:deep(.preference-tag .el-tag__close) {
  color: #b87c4e;
}

:deep(.preference-tag .el-tag__close:hover) {
  background-color: #b87c4e;
  color: white;
}

.add-tag-btn {
  border: 1px dashed #d9c2a4;
  background: transparent;
  color: #b87c4e;
  border-radius: 40px;
  padding: 8px 20px;
  font-weight: 500;
  transition: all 0.2s;
}

.add-tag-btn:hover {
  background: #fff6ed;
  border-color: #b87c4e;
  transform: translateY(-1px);
}

.plus-icon {
  margin-right: 4px;
  font-weight: 600;
}

.preference-note {
  padding: 0 24px;
  margin-top: 8px;
}

.preference-note span {
  font-size: 13px;
  color: #9b8e7e;
  background: #faf8f4;
  padding: 4px 12px;
  border-radius: 20px;
  display: inline-block;
}

/* Tab切换 */
.tabs-container {
  display: flex;
  gap: 8px;
  padding: 0 20px;
  margin-bottom: 32px;
  flex-wrap: wrap;
  border-bottom: 1px solid #f0e8df;
}

.tab-item {
  padding: 12px 20px;
  font-size: 15px;
  font-weight: 500;
  color: #7f6e5c;
  cursor: pointer;
  transition: all 0.2s;
  border-bottom: 2px solid transparent;
  letter-spacing: 0.3px;
}

.tab-item:hover {
  color: #b87c4e;
}

.tab-item.active {
  color: #b87c4e;
  border-bottom-color: #b87c4e;
  font-weight: 600;
}

/* 通用推荐区块 */
.recommend-section, .trend-section, .mixed-section {
  margin-bottom: 48px;
  padding: 0 20px;
}

.section-header-with-action {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 24px;
}

.section-title-secondary {
  font-size: 18px;
  font-weight: 600;
  color: #2c241a;
  margin: 0;
  letter-spacing: -0.2px;
}

.refresh-btn, .more-link {
  background: none;
  border: none;
  color: #b87c4e;
  font-size: 14px;
  cursor: pointer;
  padding: 6px 12px;
  border-radius: 30px;
  transition: all 0.2s;
  font-weight: 500;
}

.refresh-btn:hover, .more-link:hover {
  background: #fff0e4;
  transform: translateX(2px);
}

/* 卡片网格 */
.cards-grid {
  display: flex;
  gap: 24px;
  overflow-x: auto;
  padding-bottom: 12px;
  scrollbar-width: thin;
  padding-top: 10px;
}

.cards-grid::-webkit-scrollbar {
  height: 4px;
}

.cards-grid::-webkit-scrollbar-track {
  background: #f0e8df;
  border-radius: 4px;
}

.cards-grid::-webkit-scrollbar-thumb {
  background: #d9c2a4;
  border-radius: 4px;
}

.card-item {
  flex: 0 0 calc(20% - 20px);
  min-width: 200px;
  transition: transform 0.2s;
}

.card-item:hover {
  transform: translateY(-4px);
}

.card-attr {
  display: flex;
  flex-direction: column;
  gap: 4px;
  margin-top: 8px;
  font-size: 12px;
}

.attr-highlight {
  color: #b87c4e;
  font-weight: 600;
}

.attr-desc {
  color: #9b8e7e;
}

/* 热门趋势列表 */
.trend-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  background: #fefcf9;
  border-radius: 24px;
  padding: 8px;
}

.trend-item {
  transition: all 0.2s;
}

.trend-item:hover {
  transform: translateX(4px);
}

/* 混合推荐卡片 */
.mixed-cards {
  display: flex;
  gap: 24px;
  flex-wrap: wrap;
}

.mixed-card {
  flex: 1;
  min-width: 240px;
  background: white;
  border-radius: 20px;
  transition: all 0.2s;
}

.mixed-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
}

/* 弹窗样式优化 */
.edit-dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
  visibility: hidden;
  opacity: 0;
  transition: all 0.3s cubic-bezier(0.2, 0.9, 0.4, 1.1);
}

.edit-dialog.active {
  visibility: visible;
  opacity: 1;
}

.dialog-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(4px);
}

.dialog-container {
  position: relative;
  width: 520px;
  max-width: 90%;
  background: #ffffff;
  border-radius: 32px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
  animation: dialogSlideUp 0.3s ease;
}

@keyframes dialogSlideUp {
  from {
    transform: translateY(30px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px 28px 16px 28px;
  border-bottom: 1px solid #f0e8df;
}

.dialog-title {
  margin: 0;
  font-size: 20px;
  font-weight: 600;
  color: #2c241a;
}

.dialog-close {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #faf8f4;
  border: none;
  font-size: 24px;
  color: #8b7355;
  cursor: pointer;
  border-radius: 50%;
  transition: all 0.2s;
}

.dialog-close:hover {
  background: #f0e8df;
  color: #b87c4e;
  transform: rotate(90deg);
}

.dialog-body {
  padding: 24px 28px;
}

.dialog-form :deep(.el-form-item__label) {
  color: #5c4b3a;
  font-weight: 500;
}

.dialog-form :deep(.el-input__wrapper) {
  border-radius: 16px;
  box-shadow: 0 0 0 1px #eae4da;
}

.dialog-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #d9c2a4;
}

.dialog-footer {
  padding: 16px 28px 28px 28px;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.cancel-btn, .confirm-btn {
  border-radius: 40px;
  padding: 10px 24px;
  font-weight: 500;
}

.cancel-btn {
  background: #faf8f4;
  border-color: #eae4da;
  color: #7f6e5c;
}

.cancel-btn:hover {
  background: #f0e8df;
  border-color: #d9c2a4;
  color: #5c4b3a;
}

.confirm-btn {
  background: linear-gradient(135deg, #b87c4e 0%, #9e653d 100%);
  border: none;
  color: white;
  box-shadow: 0 2px 8px rgba(184, 124, 78, 0.3);
}

.confirm-btn:hover {
  background: linear-gradient(135deg, #9e653d 0%, #8b5532 100%);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(184, 124, 78, 0.4);
}

/* 响应式适配 */
@media (max-width: 768px) {
  .cards-grid {
    gap: 16px;
  }
  
  .card-item {
    flex: 0 0 calc(50% - 12px);
  }
  
  .tabs-container {
    gap: 4px;
  }
  
  .tab-item {
    padding: 8px 12px;
    font-size: 14px;
  }
  
  .section-title-secondary {
    font-size: 16px;
  }
  
  .mixed-cards {
    flex-direction: column;
  }
  
  .preference-tag {
    font-size: 12px;
    padding: 6px 14px;
  }
}

@media (max-width: 480px) {
  .card-item {
    flex: 0 0 calc(100% - 0px);
  }
  
  .tags-container {
    gap: 12px;
  }
  
  .section-header, .preference-note, .tabs-container {
    padding-left: 16px;
    padding-right: 16px;
  }
}
</style>
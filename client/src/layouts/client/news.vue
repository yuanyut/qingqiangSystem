<script setup lang="ts">
import { ref, reactive } from 'vue'
import { Search } from '@element-plus/icons-vue'
import CategoryNav from '@/components/client/CategoryNav.vue'
import card_knowledge from '@/components/client/card_knowledge.vue'
import card_knowledge1 from '@/components/client/card_knowledge1.vue'
const search = () => {
  console.log('搜索')
}
const input = ref('')
// 资讯分类导航数据
const categoryNav = reactive([
  {
    id: 'all',
    name: '全部资讯',
    icon: '🔥',
    count: 128,
    type: 'all',
    active: true
  },
  {
    id: 'performance',
    name: '演出信息',
    icon: '🎭',
    count: 45,
    type: 'performance',
    active: false
  },
  {
    id: 'competition',
    name: '赛事活动',
    icon: '🏆',
    count: 23,
    type: 'competition',
    active: false
  },
  {
    id: 'announcement',
    name: '公告通知',
    icon: '📢',
    count: 18,
    type: 'announcement',
    active: false
  },
  {
    id: 'news',
    name: '动态新闻',
    icon: '🎬',
    count: 42,
    type: 'news',
    active: false
  },
  {
    id: 'heritage',
    name: '传承动态',
    icon: '🎓',
    count: 19,
    type: 'heritage',
    active: false
  },
  {
    id: 'academic',
    name: '学术研究',
    icon: '📖',
    count: 15,
    type: 'academic',
    active: false
  },
  {
    id: 'exchange',
    name: '交流活动',
    icon: '🎪',
    count: 12,
    type: 'exchange',
    active: false
  },
  {
    id: 'interview',
    name: '人物专访',
    icon: '🌟',
    count: 28,
    type: 'interview',
    active: false
  },
  {
    id: 'media',
    name: '媒体报道',
    icon: '📺',
    count: 16,
    type: 'media',
    active: false
  }
])
const shijian=reactive([{
  title:'1'
},
{
  title:'2'
}])
const calendarValue=ref(new Date())
console.log(calendarValue.value+"6666")
</script>
<template>
  <div class="culture-home">
    <!-- 搜索框 -->
    <div class="search-container">
      <div class="search-wrapper">
        <el-input v-model="input" placeholder="探索文化瑰宝，输入关键词..." class="custom-search-input">
          <template #append>
            <el-button :icon="Search" @click="search" class="search-button" />
          </template>
        </el-input>
      </div>
    </div>

    <!-- 文化分类导航 -->
    <section class="section">
      <div class="section-header">
        <h2 class="section-title">总览</h2>
        <span class="section-subtitle">探寻文化脉络</span>
      </div>
      <div class="nav-grid">
        <div v-for="(item, index) in categoryNav" :key="index">
          <CategoryNav :title="item.name" :nums="item.count" :icon="item.icon" />
        </div>
      </div>
    </section>

    <!-- 特色推荐 · 精选内容 -->
    <section class="section">
      <div class="section-header">
        <h2 class="section-title">头条资讯</h2>
      </div>
      <div class="featured-list">
       
        <card_knowledge title="秦腔的起源与发展" desc="来自全国12个省市的30余个剧团齐聚西安">
          <template #footer>
            <div style="display: flex;gap:50px"><span>时间</span><span>地点</span></div>
            <div class="featured-meta">
              <span>2.3w阅读</span>
              <span>点赞</span>
              <span>1人评论</span>
            </div>
          </template>
        </card_knowledge>
      </div>
    </section>

    <!-- 双栏布局 (左侧文章 + 右侧卡片) -->
    <section class="section">
      <div class="two-columns">
        <!-- 左栏：秦腔历史 -->
        <div class="column">
          <div class="column-header">
            <h3>最新资讯</h3>
          </div>
          <div class="card-list">
            <div v-for="i in 3" :key="i">
              <card_knowledge title="秦腔的起源与发展"
                desc="秦腔起源于西周，形成于秦代秦腔起源于西周，形成于秦代秦腔起源于西周，形成于秦代秦腔起源于西周，形成于秦代秦腔起源于西周，形成于秦代秦腔起源于西周，形成于秦代秦腔起源于西周，形成于秦代秦腔起源于西周，形成于秦代秦腔起源于西周，形成于秦代">
                <template #footer>
                  <div class="featured-meta">
              
              <span>时间</span><span>2.3w阅读</span>
              <span>1人评论</span>
            </div>
                </template>
              </card_knowledge>
            </div>
          </div>
          <div class="more-link">查看更多 →</div>
        </div>

        
      </div>

      <!-- 第二行双栏 -->
      <div class="two-columns">
        <!-- 左栏：艺术特色 -->
        <div class="column">
          <div class="column-header">
            <h3>热门排行</h3>
          </div>
          <div class="card-list">
            <div v-for="i in 4" :key="i">
              <card_knowledge title="1.秦腔艺术节圆满落幕">
                <template #footer><div class="featured-meta">
              
              <span>2.3w阅读</span>
              <span>热度</span>
            </div></template>
              </card_knowledge>
            </div>
          </div>
          <div class="more-link">查看更多 →</div>
        </div>

        <!-- 右栏：文化数据 -->
        <div class="column">
          <div class="column-header">
            <h3>推荐阅读</h3>
          </div>
          <div class="card-list">
            <div v-for="i in 4" :key="i">
              <card_knowledge title="秦腔名家">
                <template #footer>
                <div class="featured-meta">
              
              <span>2.3w阅读</span>
              
            </div></template>
              </card_knowledge>
            </div>
          </div>
          <div class="more-link">更多 →</div>
        </div>
      </div>
    </section>
    <!-- 活动日历 -->
    <section class="section">
      <div class="section-header">
        <h2 class="section-title">活动日历</h2>
        <span class="more-text">更多 →</span>
      </div>
      <div class="gallery-grid">
        <el-calendar v-model="calendarValue" controller-type="select" >
          <template #date-cell = "{data}">
            {{ data.day.split('-').slice(1).join('-') }}
            {{ data.isSelected ? '66666666666' : '66666666' }}
          </template>
        </el-calendar>
      </div>
    </section>

    <!-- 媒体聚焦 -->
    <section class="section">
      <div class="section-header">
        <h2 class="section-title">媒体聚焦</h2>
        <span class="more-text">更多 →</span>
      </div>
      <div class="gallery-grid">
        <div v-for="i in 3" :key="i">
          <card_knowledge1 title="央视报道:秦腔艺术节">
            <template #cardAttr>
              <div class="video-meta">
                <div>日期</div>
                <div>2.3w播放</div>
              </div>
            </template>
          </card_knowledge1>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
/* 整体风格变量 */
.culture-home {
  /* max-width: 1280px; */
  margin: 0 auto;
  padding: 24px 32px;
  /* background: #fefaf5; */
  font-family: 'Inter', system-ui, -apple-system, 'Segoe UI', Roboto, 'Noto Sans', sans-serif;
  color: #2c2418;
}

/* 搜索区域 */
.search-container {
  /* background: linear-gradient(135deg, #ffffff 0%, #fcf6ed 100%); */
  border-radius: 32px;
  padding: 32px 24px;
  margin-bottom: 40px;
  /* box-shadow: 0 8px 28px -6px rgba(0, 0, 0, 0.05), 0 1px 2px rgba(0, 0, 0, 0.02); */
  backdrop-filter: blur(2px);
  transition: box-shadow 0.3s ease;
}

.search-wrapper {
  display: flex;
  justify-content: center;
}

.custom-search-input {
  width: 100%;
  max-width: 560px;
  transition: all 0.3s cubic-bezier(0.2, 0, 0, 1);
}

:deep(.custom-search-input .el-input__wrapper) {
  border-radius: 60px 0 0 60px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.02);
  padding: 4px 20px;
  border: 1px solid #f0e2d0;
  background-color: #ffffff;
  transition: all 0.2s;
}

:deep(.custom-search-input .el-input__wrapper:hover) {
  border-color: #d4b48c;
  box-shadow: 0 6px 14px rgba(184, 124, 78, 0.08);
}

:deep(.custom-search-input .el-input__wrapper.is-focus) {
  border-color: #b87c4e;
  box-shadow: 0 0 0 3px rgba(184, 124, 78, 0.12);
}

.search-button {
  border-radius: 0 60px 60px 0;
  background: #f5ede2;
  border: 1px solid #f0e2d0;
  border-left: none;
  color: #b87c4e;
  transition: all 0.2s ease;
  padding: 12px 28px;
  font-weight: 500;
}

.search-button:hover {
  background: #b87c4e;
  color: white;
  border-color: #b87c4e;
  transform: scale(1.02);
}

/* 通用区块样式 */
.section {
  margin-bottom: 52px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 24px;
  border-left: 5px solid #d9b48b;
  padding-left: 18px;
}

.section-title {
  font-size: 1.65rem;
  font-weight: 600;
  margin: 0;
  background: linear-gradient(135deg, #3a2c21, #6b4c38);
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  letter-spacing: -0.01em;
}

.section-subtitle {
  font-size: 0.85rem;
  color: #a1846a;
  font-weight: 400;
  letter-spacing: 0.3px;
}

.more-text {
  font-size: 0.9rem;
  color: #b87c4e;
  cursor: pointer;
  transition: color 0.2s, transform 0.2s;
  font-weight: 500;
}

.more-text:hover {
  color: #8b5a3a;
  transform: translateX(4px);
}

/* 文化分类导航网格 */
.nav-grid {
  display: flex;
  gap: 24px;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-top: 8px;
}

/* 特色精选卡片 */
.featured-list {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.featured-card {
  background: #ffffff;
  border-radius: 28px;
  padding: 24px 28px;
  box-shadow: 0 6px 14px rgba(0, 0, 0, 0.02), 0 1px 2px rgba(0, 0, 0, 0.03);
  transition: all 0.25s ease;
  border: 1px solid #f5e8db;
}

.featured-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 20px 30px -12px rgba(98, 67, 41, 0.08);
  border-color: #eedbcb;
}

.featured-title {
  font-size: 1.35rem;
  font-weight: 600;
  margin: 0 0 10px 0;
  color: #3a2c21;
}

.featured-desc {
  font-size: 0.95rem;
  color: #5e4c38;
  line-height: 1.5;
  margin-bottom: 14px;
}

.featured-meta {
  display: flex;
  gap: 20px;
  font-size: 0.8rem;
  color: #a1846a;
  margin-bottom: 16px;
}

.featured-link {
  font-weight: 500;
  color: #b87c4e;
  cursor: pointer;
  display: inline-block;
  transition: color 0.2s;
}

.featured-link:hover {
  color: #8b5a3a;
  text-decoration: underline;
}

/* 双栏布局 */
.two-columns {
  display: flex;
  gap: 40px;
  margin-bottom: 48px;
  flex-wrap: wrap;
}

.column {
  flex: 1;
  min-width: 260px;
  background: #f7f7f7cc;
  backdrop-filter: blur(2px);
  border-radius: 28px;
  padding: 16px;
  transition: all 0.2s;
}

.column-header {
  border-bottom: 2px solid #efdfcf;
  padding-bottom: 12px;
  margin-bottom: 20px;
}

.column-header h3 {
  font-size: 1.35rem;
  font-weight: 600;
  margin: 0;
  color: #4d3826;
  letter-spacing: -0.2px;
}

.card-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.more-link {
  margin-top: 20px;
  text-align: right;
  font-weight: 500;
  color: #b87c4e;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 0.9rem;
}

.more-link:hover {
  color: #8b5a3a;
  transform: translateX(4px);
}

/* 图集与视频网格 */
.gallery-grid {
  display: flex;
  gap: 28px;
  flex-wrap: wrap;
}

.video-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.75rem;
  color: #b5a084;
  margin-top: 8px;
}

/* 非遗传承列表 */
.heritage-list {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.heritage-footer {
  font-weight: 500;
  color: #b87c4e;
  transition: color 0.2s;
}

.heritage-footer:hover {
  color: #8b5a3a;
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 860px) {
  .culture-home {
    padding: 20px 20px;
  }

  .two-columns {
    gap: 28px;
  }

  .nav-grid {
    gap: 16px;
  }

  .section-title {
    font-size: 1.4rem;
  }
}

@media (max-width: 640px) {
  .search-container {
    padding: 24px 16px;
  }

  .two-columns {
    flex-direction: column;
    gap: 32px;
  }

  .column {
    width: 100%;
  }

  .featured-card {
    padding: 20px;
  }

  .gallery-grid {
    gap: 16px;
  }
}

/* 优雅滚动条 */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background: #f1e7dd;
  border-radius: 6px;
}

::-webkit-scrollbar-thumb {
  background: #cfb797;
  border-radius: 6px;
}

::-webkit-scrollbar-thumb:hover {
  background: #b48a5a;
}
</style>
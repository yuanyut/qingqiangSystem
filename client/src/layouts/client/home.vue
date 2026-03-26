<script setup lang="ts">
import { reactive, ref } from 'vue'
import cardHome from '@/components/client/card_home.vue';
import newsHome from '@/components/client/news_hone.vue';
const swiperList = reactive<string[]>([
  '/home/banner1.png',
  '/home/banner2.png',
  '/home/banner3.png',
])
const youStatus = ref(false)
const isLiked=ref(true)
const isFavorited=ref(true)
const changeLike=(index:boolean)=>{
  isLiked.value=index
}
const changeFav=()=>{
  isFavorited.value=!isFavorited.value
}
</script>
<template>
  <div>
    <!-- 轮播图 -->
    <el-carousel trigger="click" height="600px">
      <el-carousel-item v-for="(item, index) in swiperList" :key="index">
        <img :src="item" :alt="`轮播图${index + 1}`" class="swiper-image">
      </el-carousel-item>
    </el-carousel>
    <!-- 热门剧目推荐 -->
    <div style="padding:25px;position: relative;">
      <div class="hot_header">
        <span class="title">热门剧目推荐</span>
        <span class="all-link">全部</span>
      </div>
      <div class="hot_centent" @mouseover="youStatus = true" @mouseleave="youStatus = false">
        <div v-for="i in 9" :key="i">
          <card-home image="/home/banner1.png" name="白蛇传" desc="这是白蛇传" cate="传统剧目" act="演唱者" watch-people="观看人数"
            love-people="喜爱人数" @change-like="changeLike" :is-liked= isLiked 
            :is-favorited= isFavorited favorite-count="16"
            @change-favorite="changeFav"/>
        </div>
        <span class="you" :class="{ active: youStatus, inactive: !youStatus }">右向箭头</span>
      </div>
    </div>
    <!-- 个性化推荐 -->
    <div style="padding:25px;position: relative;">
      <div class="hot_header">
        <span class="title">个性化推荐 · 猜你喜欢</span>
        <div style="display: flex;gap:10px;align-items: center;">
          <svg t="1774169804825" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="2668" width="15" height="15">
            <path
              d="M935.005091 459.752727a34.909091 34.909091 0 1 1 49.361454 49.361455l-78.382545 78.382545a34.816 34.816 0 0 1-49.338182 0l-78.405818-78.382545a34.909091 34.909091 0 1 1 49.361455-49.361455l14.801454 14.824728C818.525091 311.738182 678.330182 186.181818 508.928 186.181818c-130.466909 0-250.484364 76.706909-305.710545 195.397818a34.932364 34.932364 0 0 1-63.301819-29.463272C206.522182 208.896 351.418182 116.363636 508.904727 116.363636c210.152727 0 383.534545 159.953455 404.992 364.474182l21.085091-21.085091z m-73.960727 189.021091a34.932364 34.932364 0 0 1 16.965818 46.382546C811.310545 838.353455 666.461091 930.909091 508.951273 930.909091c-210.106182 0-383.534545-159.953455-404.968728-364.497455l-21.108363 21.108364a34.909091 34.909091 0 1 1-49.384727-49.361455l78.42909-78.42909a34.909091 34.909091 0 0 1 49.338182 0l78.382546 78.42909a34.909091 34.909091 0 1 1-49.338182 49.338182l-14.824727-14.801454C199.354182 735.534545 339.549091 861.090909 508.951273 861.090909c130.490182 0 250.507636-76.706909 305.710545-195.397818a34.909091 34.909091 0 0 1 46.382546-16.919273z"
              fill="#797979" p-id="2669"></path>
          </svg>
          <span class="change-link">换一批</span>
        </div>

      </div>
      <div class="hot_centent " style="gap:60px">
        <div v-for="i in 5" :key="i">
          <card-home image="/home/banner1.png" name="白蛇传" desc="这是白蛇传" cate="传统剧目" act="演唱者" watch-people="观看人数"
            love-people="喜爱人数" @change-like="changeLike" :is-liked= isLiked 
            :is-favorited= isFavorited
            @change-favorite="changeFav"/>

        </div>

      </div>
    </div>
    <!-- 双栏内容区(资讯+名家) -->
    <div style="padding:25px;position: relative;">
      <div class="hot_header">
        <span class="title">双栏内容区(资讯+名家)</span>
      </div>
      <div class="zixun_centent ">
        <!-- 秦腔资讯 -->
        <news-home name="秦腔资讯">
          <template #content>
            <div class="info-list">
              <div v-for="i in 9" :key="i" class="info-item">
                <div class="info-content">
                  <div class="info-title">这是信息 {{ i }}</div>
                  <div class="info-meta">
                    <span class="info-time">
                      <span class="time-icon">⏰</span>
                      2024-01-{{ i }}
                    </span>
                    <span class="info-count">
                      <span class="count-icon">👥</span>
                      {{ Math.floor(Math.random() * 1000) }}人
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </template>
        </news-home>
        <!-- 名家风采 -->
        <news-home name="名家风采">
          <template #content>
            <div class="artist-list">
              <div v-for="i in 5" :key="i" class="artist-card">
                <div class="artist-avatar">
                  <el-image style="width: 120px; height: 120px"
                    src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg" fit="cover"
                    class="avatar-image" />
                  <div class="avatar-overlay">
                    <span class="follow-btn">关注</span>
                  </div>
                </div>

                <div class="artist-info">
                  <div class="info-header">
                    <h3 class="artist-name">演员名字 {{ i }}</h3>
                    <span class="artist-title">国家一级演员</span>
                  </div>

                  <div class="artist-works">
                    <div class="works-label">代表作品：</div>
                    <div class="works-list">
                      <span class="work-tag">霸王别姬</span>
                      <span class="work-tag">贵妃醉酒</span>
                      <span class="work-tag">牡丹亭</span>
                    </div>
                  </div>

                  <div class="artist-stats">
                    <div class="stat-item">
                      <span class="stat-label">粉丝</span>
                      <span class="stat-value">12.3w</span>
                    </div>
                    <div class="stat-item">
                      <span class="stat-label">作品</span>
                      <span class="stat-value">28部</span>
                    </div>
                    <div class="stat-item">
                      <span class="stat-label">获奖</span>
                      <span class="stat-value">15项</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </template>
        </news-home>
      </div>
    </div>
    <!--底部 -->
    <footer class="footer">
  <div class="footer-container">
    <!-- Logo 和 Slogan -->
    <div class="footer-brand">
      <h2 class="brand-name">🎭 秦韵之声</h2>
      <p class="brand-slogan">传承秦腔艺术 · 弘扬非遗文化</p>
    </div>
    
    <!-- 链接区域 -->
    <div class="footer-links">
      <a href="/about">关于我们</a>
      <span class="link-separator">·</span>
      <a href="/contact">联系我们</a>
      <span class="link-separator">·</span>
      <a href="/privacy">隐私政策</a>
      <span class="link-separator">·</span>
      <a href="/terms">用户协议</a>
    </div>
    
    <!-- 版权和技术信息 -->
    <div class="footer-copyright">
      <p>© 2026 秦韵之声. All rights reserved.</p>
      <p class="tech-stack">基于 Spring Boot + Vue.js 构建</p>
    </div>
  </div>
</footer>
  </div>

</template>
<style scoped>
.swiper-image {
  width: 100%;
  height: 100%;
  object-fit: fill;
}

.hot_centent {
  display: flex;
  width: 100%;
  overflow-x: auto;
  gap: 20px;
  scrollbar-width: none;
  /* Firefox */
  -ms-overflow-style: none;
  /* IE/Edge */
  /* position: relative; */
  padding-top: 25px;

}

.hot_centent::-webkit-scrollbar {
  display: none;
}

.hot_header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0px 20px;
}

.title {
  font-size: 18px;
  font-weight: 700;
  color: rgb(0, 0, 0);
  letter-spacing: 0.5px;
  position: relative;
  padding-left: 12px;
}

.title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 20px;
  background: #ffd700;
  border-radius: 2px;
}

.all-link {
  font-size: 14px;
  color: rgba(182, 182, 182, 0.9);
  cursor: pointer;
  transition: all 0.3s ease;
  padding: 4px 12px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.1);
}

.change-link {
  font-size: 14px;
  color: rgba(182, 182, 182, 0.9);
  cursor: pointer;
  transition: all 0.3s ease;
  /* padding: 4px 12px; */
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.1);
}

.all-link:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: translateX(2px);
}

.you {
  position: absolute;
  top: 45%;
  right: 15px;
}

.you.active {
  display: block;
  color: black;
}

.you.inactive {
  display: none;
}

.zixun_centent {
  display: flex;
  gap: 20px;
  justify-content: space-between;
}

.info-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.info-item {
  background: linear-gradient(135deg, #fff 0%, #f8f9fa 100%);
  border-radius: 12px;
  padding: 16px;
  transition: all 0.3s ease;
  cursor: pointer;
  border: 1px solid #e9ecef;
  position: relative;
  overflow: hidden;
}

.info-item::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  width: 4px;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  transform: scaleY(0);
  transition: transform 0.3s ease;
}

.info-item:hover {
  transform: translateX(4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-color: #667eea;
}

.info-item:hover::before {
  transform: scaleY(1);
}

.info-content {
  display: flex;
  justify-content: space-between;
  /* align-items: center; */
  flex-wrap: wrap;
  gap: 12px;
  flex-direction: column;
}

.info-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  flex: 1;
  transition: color 0.3s ease;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.info-item:hover .info-title {
  color: #667eea;
}

.info-meta {
  display: flex;
  gap: 16px;
  align-items: center;
}

.info-time,
.info-count {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: #64748b;
  padding: 4px 8px;
  background: #f1f5f9;
  border-radius: 6px;
  transition: all 0.3s ease;
}

.info-item:hover .info-time,
.info-item:hover .info-count {
  background: #e2e8f0;
}

.time-icon,
.count-icon {
  font-size: 12px;
}

.artist-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.artist-card {
  display: flex;
  gap: 24px;
  background: linear-gradient(135deg, #fff 0%, #faf9ff 100%);
  border-radius: 16px;
  padding: 20px;
  transition: all 0.3s ease;
  cursor: pointer;
  border: 1px solid #f0eef7;
}

.artist-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.12);
  border-color: #e0d9ff;
}

.artist-avatar {
  position: relative;
  flex-shrink: 0;
}

.avatar-image {
  border-radius: 16px;
  transition: transform 0.3s ease;
}

.artist-card:hover .avatar-image {
  transform: scale(1.02);
}

.avatar-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.7), transparent);
  padding: 8px;
  border-radius: 0 0 16px 16px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.artist-card:hover .avatar-overlay {
  opacity: 1;
}

.follow-btn {
  display: inline-block;
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.follow-btn:hover {
  transform: scale(0.95);
}

.artist-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.info-header {
  display: flex;
  align-items: baseline;
  gap: 12px;
  flex-wrap: wrap;
}

.artist-name {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
  background: linear-gradient(135deg, #1e293b, #334155);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.artist-title {
  font-size: 13px;
  color: #667eea;
  background: #f0eefe;
  padding: 4px 12px;
  border-radius: 20px;
  font-weight: 500;
}

.artist-works {
  display: flex;
  gap: 12px;
  align-items: flex-start;
  flex-wrap: wrap;
}

.works-label {
  font-size: 13px;
  color: #64748b;
  font-weight: 500;
  white-space: nowrap;
}

.works-list {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.work-tag {
  font-size: 12px;
  color: #475569;
  background: #f1f5f9;
  padding: 4px 10px;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.work-tag:hover {
  background: #e2e8f0;
  transform: translateY(-1px);
}

.artist-stats {
  display: flex;
  gap: 24px;
  margin-top: 4px;
  padding-top: 12px;
  border-top: 1px solid #f1f5f9;
}

.stat-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-label {
  font-size: 11px;
  color: #94a3b8;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.stat-value {
  font-size: 16px;
  font-weight: 700;
  color: #1e293b;
}
.footer {
  background: #2c241a;
  color: #b8a281;
  padding: 48px 20px 32px;
  margin-top: 60px;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

.footer-brand {
  margin-bottom: 24px;
}

.brand-name {
  font-size: 24px;
  font-weight: bold;
  color: #f5a623;
  margin-bottom: 8px;
}

.brand-slogan {
  font-size: 14px;
  color: #b8a281;
}

.footer-links {
  margin-bottom: 24px;
  font-size: 14px;
}

.footer-links a {
  color: #b8a281;
  text-decoration: none;
  transition: color 0.3s;
}

.footer-links a:hover {
  color: #f5a623;
}

.link-separator {
  margin: 0 12px;
  color: #5a4a3a;
}

.footer-copyright {
  font-size: 12px;
  color: #8b7355;
  border-top: 1px solid #3a2e24;
  padding-top: 24px;
}

.tech-stack {
  margin-top: 8px;
  font-size: 11px;
  color: #6b5a48;
}
</style>
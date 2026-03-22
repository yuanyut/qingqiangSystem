<script setup lang="ts">
import { reactive } from 'vue'

const swiperList = reactive<string[]>([
   '/home/banner1.png',
   '/home/banner2.png',
   '/home/banner3.png',
])
</script>
<template>
  <div>
    <!-- 轮播图 -->
    <el-carousel trigger="click" height="600px">
       <el-carousel-item v-for="(item, index) in swiperList" :key="index">
        <img 
          :src="item" 
          :alt="`轮播图${index + 1}`"
          class="swiper-image"
        >
      </el-carousel-item>
    </el-carousel>
    <!-- 热门剧目推荐 -->
  </div>
</template>
<style scoped>
.swiper-image {
  width: 100%;
  height: 100%;
  object-fit: fill;
}
</style>
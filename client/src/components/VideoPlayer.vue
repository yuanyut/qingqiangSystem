<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  src: string
  controls?: boolean
  autoplay?: boolean
  loop?: boolean
  muted?: boolean
  width?: string
  height?: string
}>()

// 判断是否为bilibili视频
const isBilibiliVideo = computed(() => {
  if (!props.src) return false
  return props.src.includes('bilibili.com') || props.src.includes('bilibili') || props.src.includes('b站') || props.src.includes('bilibili')
})

// 解析bilibili视频ID
const bilibiliVideoId = computed(() => {
  if (!isBilibiliVideo.value) return ''
  
  const url = props.src
  // 匹配bilibili视频链接格式
  // https://www.bilibili.com/video/BV1xx411c7mZ
  // https://b23.tv/BV1xx411c7mZ
  const match = url.match(/BV[\w]+/)
  if (match) {
    return match[0]
  }
  
  // 尝试从embed链接提取
  const embedMatch = url.match(/player\.bilibili\.com\/player\.html\?.*bvid=([^&]+)/)
  if (embedMatch) {
    return embedMatch[1]
  }
  
  return ''
})

// 构建bilibili嵌入链接
const bilibiliEmbedUrl = computed(() => {
  if (!bilibiliVideoId.value) return ''
  return `//player.bilibili.com/player.html?bvid=${bilibiliVideoId.value}&page=1`
})
</script>

<template>
  <div class="video-player-container" :style="{ width: width || '100%', height: height || 'auto' }">
    <!-- Bilibili视频 -->
    <div v-if="isBilibiliVideo && bilibiliVideoId" class="bilibili-player">
      <iframe
        :src="bilibiliEmbedUrl"
        scrolling="no"
        border="0"
        frameborder="no"
        framespacing="0"
        allowfullscreen="true"
        :width="width || '100%'"
        :height="height || '400px'"
        class="bilibili-iframe"
      ></iframe>
    </div>
    
    <!-- 本地/其他视频 -->
    <video
      v-else
      :src="src"
      :controls="controls !== false"
      :autoplay="autoplay || false"
      :loop="loop || false"
      :muted="muted || false"
      class="native-video"
    >
      您的浏览器不支持 video 标签。
    </video>
  </div>
</template>

<style scoped>
.video-player-container {
  position: relative;
  background: #000;
  border-radius: 8px;
  overflow: hidden;
}

.bilibili-player {
  position: relative;
  width: 100%;
}

.bilibili-iframe {
  display: block;
}

.native-video {
  display: block;
  width: 100%;
  height: auto;
}
</style>

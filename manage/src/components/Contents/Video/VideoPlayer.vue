<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  src: string
  width?: string
  height?: string
}>()

const isBilibiliVideo = computed(() => {
  if (!props.src) return false
  return props.src.includes('bilibili.com') || props.src.includes('bilibili') || props.src.includes('b23.tv')
})

const bilibiliVideoId = computed(() => {
  if (!isBilibiliVideo.value) return ''

  const match = props.src.match(/BV[\w]+/)
  if (match) {
    return match[0]
  }

  const embedMatch = props.src.match(/player\.bilibili\.com\/player\.html\?.*bvid=([^&]+)/)
  if (embedMatch) {
    return embedMatch[1]
  }

  return ''
})

const bilibiliEmbedUrl = computed(() => {
  if (!bilibiliVideoId.value) return ''
  return `//player.bilibili.com/player.html?bvid=${bilibiliVideoId.value}&page=1`
})
</script>

<template>
  <div class="video-player-container" :style="{ width: width || '250px', height: height || '140px' }">
    <div v-if="isBilibiliVideo && bilibiliVideoId" class="bilibili-player">
      <iframe
        :src="bilibiliEmbedUrl"
        scrolling="no"
        border="0"
        frameborder="no"
        framespacing="0"
        allowfullscreen="true"
        :width="width || '250px'"
        :height="height || '140px'"
        class="bilibili-iframe"
      ></iframe>
    </div>

    <video
      v-else
      :src="src"
      controls
      preload="metadata"
      :style="{ width: width || '250px', height: height || '140px', borderRadius: '8px', background: '#000' }"
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
  height: 100%;
}

.bilibili-iframe {
  display: block;
  width: 100%;
  height: 100%;
}
</style>

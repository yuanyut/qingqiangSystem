<script setup lang="ts">
import side from '@/layouts/side.vue';
import content from '@/layouts/content.vue';
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <side></side>
      </el-aside>
      <el-container>
        <el-main>
            <content></content>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<style scoped>
</style>
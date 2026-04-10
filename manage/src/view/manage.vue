<script setup lang="ts">
import side from '@/layouts/side.vue';
import content from '@/layouts/content.vue';
import Header from '@/layouts/header.vue';
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <Header></Header>
      </el-header>
      <el-container>
        <el-aside width="200px"><side></side></el-aside>
        <el-main><content></content></el-main>
      </el-container>
    </el-container>
  </div>
</template>


<style scoped>
:deep(.el-main){
  --el-main-padding: 0px;
}
</style>
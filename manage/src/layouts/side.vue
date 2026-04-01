<script lang="ts" setup>
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
} from '@element-plus/icons-vue'
import { mockMenu } from '@/types.ts/meaus'
const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
</script>
<template>
  <div>
    <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
    >
      <!-- 遍历菜单 -->
      <template v-for="item in mockMenu" :key="item.path">

        <!-- 如果有子菜单 -->
        <el-sub-menu v-if="item.children && item.children.length > 0" :index="item.path">
          <template #title>
            <el-icon><icon-menu /></el-icon>
            <span>{{ item.name }}</span>
          </template>

          <el-menu-item
            v-for="child in item.children"
            :key="child.path"
            :index="child.path"
          >
            {{ child.name }}
          </el-menu-item>
        </el-sub-menu>

        <!-- 如果没有子菜单 -->
        <el-menu-item v-else :index="item.path">
          <el-icon><setting /></el-icon>
          <span>{{ item.name }}</span>
        </el-menu-item>

      </template>
    </el-menu>
  </div>
</template>

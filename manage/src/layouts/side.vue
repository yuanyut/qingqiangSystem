<script lang="ts" setup>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { mockMenu } from '@/types.ts/meaus'

const route = useRoute()
const router = useRouter()

// 手动控制激活的菜单项
const activeMenu = ref('')

// 更新激活菜单的函数
const updateActiveMenu = () => {
  const currentPath = route.path
  console.log('更新激活菜单，当前路径:', currentPath)
  
  if (currentPath === '/manage' || currentPath === '/manage/') {
    // 如果在根路径，激活第一个菜单
    const firstMenu = mockMenu[0]?.children?.[0]?.path
    activeMenu.value = firstMenu || ''
    console.log('根路径，激活第一个菜单:', activeMenu.value)
  } else if (currentPath.startsWith('/manage/')) {
    // 提取子路径
    const subPath = currentPath.replace('/manage/', '')
    activeMenu.value = subPath
    console.log('子路径，激活菜单:', activeMenu.value)
  } else {
    activeMenu.value = ''
    console.log('其他路径，激活空')
  }
}

// 监听路由变化 - 使用多种方式确保能捕获到浏览器前进后退
// 方式1: 使用 watch 监听 route.path
watch(() => route.path, () => {
  console.log('watch 监听到路由变化')
  updateActiveMenu()
}, { immediate: true, deep: true })

// 页面跳转
const changPage = (path: string) => {
  console.log("点击菜单:", path)
  
  // 先更新激活状态
  activeMenu.value = path
  
  // 如果当前已经在目标页面，不重复跳转
  if (route.path === `/manage/${path}`) {
    console.log('已在当前页面，只更新高亮')
    return
  }
  
  // 跳转路由
  router.push('/manage/' + path)
}

</script>

<template>
  <div>
    <el-menu 
      :default-active="activeMenu" 
      class="el-menu-vertical-demo" 
      style="height: 100vh;overflow: auto;"
  
      @select="(index:any) => console.log('菜单选中:', index)"
    >
      <!-- 遍历菜单 -->
      <template v-for="item in mockMenu" :key="item.path">
        <!-- 如果有子菜单 -->
        <el-sub-menu v-if="item.children && item.children.length > 0" :index="item.path">
          <template #title>
            <el-icon><component :is="item.icon" /></el-icon>
            <span>{{ item.name }}</span>
          </template>

          <el-menu-item 
            v-for="child in item.children" 
            :key="child.path" 
            :index="child.path"
            @click="changPage(child.path)"
          >
            {{ child.name }}
          </el-menu-item>
        </el-sub-menu>

        <!-- 如果没有子菜单 -->
        <el-menu-item v-else :index="item.path" @click="changPage(item.path)">
          <el-icon>
            <component :is="item.icon" />
          </el-icon>
          <span>{{ item.name }}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<style scoped>
/* 确保高亮样式 */
:deep(.el-menu-item.is-active) {
  background-color: #409eff !important;
  color: #fff !important;
}

:deep(.el-menu-item.is-active:hover) {
  background-color: #409eff !important;
}
</style>
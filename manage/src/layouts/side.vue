<script lang="ts" setup>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { mockMenu } from '@/types/meaus'

const route = useRoute()
const router = useRouter()

// 手动控制激活的菜单项
const activeMenu = ref('')
const changPage=(path:string) =>{
  if(route.path === `/manage/${path}`){
    return
  }
  activeMenu.value=path
  router.push('/manage/'+path)
  console.log('点击跳转activeMenu改变了',activeMenu.value)
}

watch(()=>route.path,(newRoutePath)=>{
  console.log('watch监听到路变化',newRoutePath)
  activeMenu.value=newRoutePath.replace('/manage/','')
  console.log('这是url退出的新活动侧边',activeMenu.value)
}, { immediate: true, deep: true })
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
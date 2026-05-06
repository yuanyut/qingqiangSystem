<script setup lang="ts">
import headers from '@/layouts/client/header.vue';
import {ref,watch,defineAsyncComponent} from 'vue';
import {useRouter,useRoute} from 'vue-router'
import {useUserInfoStore}  from '@/stores/userInfo'

const userInfoStore = useUserInfoStore()
//获取路由实例（用来跳转）  
const router=useRouter();
//获取当前路由信息（只读）
const route=useRoute();
const current = ref<number>(0);
console.log(route.path);
//route的改变会影响current，所以需要监听
watch(()=>route.path,(newPath)=>{
  console.log(newPath);
  //根据路由路径判断当前选中的tab
  // if(newPath.includes('home')){
  //   current.value=0;
  // }else if(newPath.includes('drama')){
  //   current.value=1;
  // }else if(newPath.includes('actorInfo')){
  //   current.value=2;
  // }else if(newPath.includes('communicate')){
  //   current.value=3;
  // }else if(newPath.includes('knowledge')){
  //   current.value=4;
  // }else if(newPath.includes('news')){
  //   current.value=5;
  // }else if(newPath.includes('profile')&&userInfoStore.isLoggedIn){
  //   current.value=6;
  // }
     if(newPath.includes('drama')){
    current.value=0;
  }else if(newPath.includes('actorInfo')){
    current.value=1;
  }
  // else if(newPath.includes('communicate')){
  //   current.value=2;
  // }
  else if(newPath.includes('knowledge')){
    current.value=2;
  }
  else if(newPath.includes('news')){
    current.value=3;
  }else if(newPath.includes('profile')&&userInfoStore.isLoggedIn){
    current.value=4;
  }
})
//监听登录状态变化，重新计算 current
watch(() => userInfoStore.isLoggedIn, (isLoggedIn) => {
  if (route.path.includes('profile')) {
    if (isLoggedIn) {
      current.value = 4;
    } else {
      // 如果未登录，跳转到首页或保持原状态
      current.value = 0;
      router.push('/drama');
    }
  }
});
// const routes=['home','drama','actorInfo','communicate','knowledge','news','profile'];
const routes=['drama','actorInfo','knowledge','news','profile']
current.value=routes.indexOf(route.path.replace('/',''));
//父组件收到通知
const changeTab =(n:number)=>{
  current.value=n;
    console.log("点击",routes[current.value]);
    
    router.push(`/${routes[current.value]}`);
    
}
</script>
<template>
    <el-container>
      <el-header>
        <headers :current=current @changeTabs="changeTab"/>
      </el-header>
      <el-main style="padding: 0;">
        <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" />
        </keep-alive>
      </router-view>
      </el-main>
    </el-container>

</template>
<style scoped>
:deep(.el-header){
  padding: 0;
}
</style>
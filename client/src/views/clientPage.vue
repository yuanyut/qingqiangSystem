<script setup lang="ts">
import headers from '@/layouts/client/header.vue';
import {ref,watch} from 'vue';
import {useRouter,useRoute} from 'vue-router'
import {useUserInfoStore}  from '@/stores/userInfo'
const userInfoStore = useUserInfoStore()
//获取路由实例（用来跳转）  
const router=useRouter();
//获取当前路由信息（只读）
const route=useRoute();
console.log(route.path);
//route的改变会影响current，所以需要监听
watch(()=>route.path,(newPath)=>{
  console.log(newPath);
  //根据路由路径判断当前选中的tab
  if(newPath.includes('home')){
    current=0;
  }else if(newPath.includes('drama')){
    current=1;
  }else if(newPath.includes('actorInfo')){
    current=2;
  }else if(newPath.includes('communicate')){
    current=3;
  }else if(newPath.includes('knowledge')){
    current=4;
  }else if(newPath.includes('news')){
    current=5;
  }else if(newPath.includes('profile')&&userInfoStore.UserInfos.isLogin){
    current=6;
  }
})
const routes=['home','drama','actorInfo','communicate','knowledge','news','profile'];
let current=routes.indexOf(route.path.replace('/',''));
//父组件收到通知
const changeTab =(n:number)=>{
    console.log("点击",n);
    current=n;
    router.push(`/${routes[current]}`);
    
}
</script>
<template>
    <el-container>
      <el-header>
        <headers :current=current @changeTabs="changeTab"/>
      </el-header>
      <el-main style="padding: 0;">
        <router-view/>
      </el-main>
    </el-container>

</template>
<style scoped>
:deep(.el-header){
  padding: 0;
}
</style>
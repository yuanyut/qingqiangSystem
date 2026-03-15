<script setup lang="ts">
import headers from '@/layouts/header.vue';
import {ref,watch} from 'vue';
import {useRouter,useRoute} from 'vue-router'
//获取路由实例（用来跳转）
const router=useRouter();
//获取当前路由信息（只读）
const route=useRoute();
//route的改变会影响current，所以需要监听
watch(()=>route.path,(newPath)=>{
  console.log(newPath);
  //根据路由路径判断当前选中的tab
  if(newPath.includes('home')){
    current.value=0;
  }else if(newPath.includes('drama')){
    current.value=1;
  }else if(newPath.includes('actorInfo')){
    current.value=2;
  }else if(newPath.includes('communicate')){
    current.value=3;
  }else if(newPath.includes('knowledge')){
    current.value=4;
  }else if(newPath.includes('news')){
    current.value=5;
  }else if(newPath.includes('profile')){
    current.value=6;
  }
})
let current=ref(0);
//父组件收到通知
const changeTab =(n:number)=>{
    console.log("点击",n);
    current.value=n;
    //路由跳转
    const routes=['home','drama','actorInfo','communicate','knowledge','news','profile'];
    router.push(`/user/${routes[current.value]}`);
}
</script>
<template>
    <el-container>
      <el-header>
        <headers :current=current @changeTabs="changeTab"/>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>

</template>
<style scoped>

</style>
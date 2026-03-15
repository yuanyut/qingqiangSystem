<script setup lang="ts">
import headers from '@/layouts/show/header.vue';
import {ref,watch} from 'vue';
import {useRouter,useRoute} from 'vue-router'
import {ElMessage} from 'element-plus'
import {useUserInfoStore}  from '@/stores/userInfo'
const userInfoStore = useUserInfoStore()
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
    // 如果用户未登录，且点击的不是登录/注册页面，跳转到登录/注册页面
    if (!userInfoStore.UserInfos.isLogin && current.value == 6) {
        // console.log("未登录");
        // router.push('/login');
        // 显示未登录提示
        ElMessage({
            message: '请先登录',
            type: 'warning',
        })
        return;
    }
    else{
      router.push(`/${routes[current.value]}`);
    }
    
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
<script setup lang="ts">
import headers from '@/layouts/header.vue';
import {ref} from 'vue';
let current=ref(0);
const changeTabs =(n:number)=>{
    console.log("点击",n);
    current.value=n;
}

</script>
<template>
    <el-container>
      <el-header>
        <headers :current=current @changeTabs="changeTabs"/>
      </el-header>
      <el-main>Main</el-main>
    </el-container>

</template>
<style scoped>

</style>
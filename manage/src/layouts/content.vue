<script setup lang="ts">
import home from '@/components/home.vue';
</script>
<template>
    <router-view>
        <KeepAlive>
            <component :is="home"></component>
        </KeepAlive>
    </router-view>
</template>
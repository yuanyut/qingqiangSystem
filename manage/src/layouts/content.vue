<script setup lang="ts">
import home from '@/components/home.vue';
</script>
<template>
    <div>
        <router-view>
            <KeepAlive>
                <component :is="home"></component>
            </KeepAlive>
        </router-view>
    </div>

</template>
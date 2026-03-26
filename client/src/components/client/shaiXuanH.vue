<script setup lang="ts">
import { ref,reactive,computed } from 'vue'
const models = defineModel<Array<{ name: string; id?: number }>>('cateList')
const current =ref(0)
const cateTitle = computed(() => models.value?.[0]?.name ?? '')
const cateList =computed(()=>{
 return   models.value?.slice(1)
})
console.log(cateList)
</script>
<template>
    
        <div class="filter-item">
            <div class="filter-label">
                <span class="label-text">{{ cateTitle}}</span>
            </div>
            <div class="filter-options">
                <span v-for="(item, index) in cateList" :key="index" @click="current = index"
                    :class="{ active: current == index }" class="filter-tag">
                    {{ item.name }}
                </span>
            </div>
        </div>
   
</template>
<style scoped>

.filter-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 16px;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f0;
}

.filter-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
  margin-bottom: 0;
}

.filter-label {
  min-width: 56px;
  font-size: 14px;
  font-weight: 500;
  color: #606266;
  line-height: 23px;
}

.label-text {
  position: relative;
}

.filter-options {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  align-items: center;
}

.filter-tag {
  display: inline-block;
  padding: 6px 16px;
  font-size: 13px;
  color: #606266;
  background-color: #f5f7fa;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.2s;
  line-height: 1;
  user-select: none;
}

.filter-tag:hover {
  background-color: #ecf5ff;
  color: #409eff;
}

.filter-tag.active {
  background-color: #409eff;
  color: #ffffff;
}

.sort-tag {
  /* 排序标签特殊样式，如需区分可在此添加 */
}
</style>
<script setup lang="ts">
    const props=defineProps<{
        name:string
    }>()
</script>
<template>
  <div class="section">
    <div class="section-header">
      <div class="header-title-wrapper">
        <span class="title-icon">🎭</span>
        <span class="header-title">{{ props.name }}</span>
      </div>
      <div class="more-wrapper">
        <span class="more-text">更多</span>
        <span class="more-icon">›</span>
      </div>
    </div>
    <div class="section-content">
      <slot name="content"></slot>
    </div>
  </div>
</template>

<style scoped>
.section {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  margin-bottom: 24px;
  width: 50%;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-title-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
}

.title-icon {
  font-size: 24px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-radius: 12px;
  padding: 6px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.2);
}

.header-title {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  position: relative;
}

.more-wrapper {
  display: flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
  padding: 8px 16px;
  background: #f8f9fa;
  border-radius: 20px;
  transition: all 0.3s ease;
}

.more-wrapper:hover {
  background: #e9ecef;
  transform: translateX(2px);
}

.more-text {
  font-size: 13px;
  color: #495057;
  font-weight: 500;
}

.more-icon {
  font-size: 18px;
  color: #868e96;
  transition: transform 0.3s ease;
}

.more-wrapper:hover .more-icon {
  transform: translateX(3px);
  color: #667eea;
}

.section-content {
  margin-top: 0;
}
</style>
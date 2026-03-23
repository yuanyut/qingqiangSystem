<script setup lang="ts">
const props = defineProps<{
  images?: string
  name?: string
  desc?: string
  cate?: string
  act?: string
  watchPeople?: string | number
  lovePeople?: string | number
}>()
</script>

<template>
  <div class="card">
    <div class="card_header">
      <image :src="props.images" size="100%" class="card_image" />
    </div>
    
    <div class="card_content">
      <div class="card_name">
        <span>{{ props.name }}</span>
      </div>
      
      <div class="card_desc">
        <span>{{ props.desc }}</span>
      </div>
      
      <div class="card_info">
        
        <div class="info_item">
          <span class="info_label">代表作品：</span>
          <span class="info_value">{{ props.act }}</span>
        </div>
      </div>
      
      <div class="card_attr">
        <div class="attr_item">
          <span class="attr_icon">👁️</span>
          <span class="attr_value">{{ props.watchPeople }}</span>
        </div>
        <div class="attr_item">
          <span class="attr_icon">❤️</span>
          <span class="attr_value">{{ props.lovePeople }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  width: 280px;
  background: linear-gradient(135deg, #fff 0%, #f8f9fa 100%);
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
}

.card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.card_header {
  position: relative;
  height: 200px;
  width: 100%;
  overflow: hidden;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.card_image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.card:hover .card_image {
  transform: scale(1.05);
}

.card_overlay {
  position: absolute;
  top: 12px;
  left: 12px;
  z-index: 1;
}

.badge {
  background: linear-gradient(135deg, #ff6b6b, #ff4757);
  color: white;
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  backdrop-filter: blur(4px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.card_content {
  padding: 16px;
  width: 100%;
}

.card_name {
  margin-bottom: 8px;
}

.card_name span {
  font-size: 18px;
  font-weight: 700;
  color: #1e293b;
  line-height: 1.3;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card_desc {
  margin-bottom: 12px;
}

.card_desc span {
  font-size: 13px;
  color: #64748b;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card_info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
  padding: 8px 0;
  border-top: 1px solid #e2e8f0;
  border-bottom: 1px solid #e2e8f0;
}

.info_item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.info_label {
  font-size: 11px;
  color: #94a3b8;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.info_value {
  font-size: 13px;
  font-weight: 600;
  color: #334155;
}

.card_attr {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
  gap: 12px;
}

.attr_item {
  display: flex;
  align-items: center;
  gap: 6px;
  background: #f1f5f9;
  padding: 6px 12px;
  border-radius: 20px;
  flex: 1;
  justify-content: center;
  transition: background 0.2s ease;
}



.attr_icon {
  font-size: 14px;
}

.attr_value {
  font-size: 13px;
  font-weight: 600;
  color: #334155;
}

.card_footer {
  display: flex;
  justify-content: end;
}

.watch_btn {
  width: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 8px;
  padding: 8px 16px;
  font-weight: 600;
  transition: all 0.2s ease;
}


/* 响应式设计 */
@media (max-width: 768px) {
  .card {
    width: 100%;
    max-width: 280px;
    margin: 0 auto;
  }
}
</style>
<script setup lang="ts">
defineProps<{
    title?: string
    nums?: string | number
    icon?: string  // 新增图标属性
}>()
</script>

<template>
    <div class="category-nav">
        <div class="category-icon" v-if="icon">{{ icon }}</div>
        <div class="category-content">
            <div class="category-title">{{ title }}</div>
            <div class="category-nums">{{ nums }}篇</div>
        </div>
    </div>
</template>

<style scoped>
.category-nav {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 20px 16px;
    background: linear-gradient(135deg, #ffffff 0%, #fefaf5 100%);
    border-radius: 20px;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    cursor: pointer;
    text-align: center;
    border: 1px solid #f0e8dc;
    min-width: 90px;
}

.category-nav:hover {
    transform: translateY(-6px);
    border-color: #d4451e30;
    box-shadow: 0 12px 24px -8px rgba(212, 69, 30, 0.15);
    background: linear-gradient(135deg, #ffffff 0%, #fff7ef 100%);
}

.category-icon {
    font-size: 32px;
    margin-bottom: 12px;
    transition: transform 0.2s ease;
}

.category-nav:hover .category-icon {
    transform: scale(1.08);
}

.category-content {
    display: flex;
    flex-direction: column;
    gap: 6px;
}

.category-title {
    font-size: 15px;
    font-weight: 600;
    color: #5a3e2b;
    letter-spacing: 1px;
    transition: color 0.2s;
}

.category-nav:hover .category-title {
    color: #d4451e;
}

.category-nums {
    font-size: 12px;
    color: #b48c5c;
    background: #fef5ea;
    padding: 3px 10px;
    border-radius: 30px;
    display: inline-block;
    font-weight: 500;
    transition: all 0.2s;
}

.category-nav:hover .category-nums {
    background: #d4451e10;
    color: #d4451e;
}

/* 响应式适配 */
@media (max-width: 768px) {
    .category-nav {
        padding: 14px 12px;
        min-width: 70px;
    }
    
    .category-icon {
        font-size: 26px;
        margin-bottom: 8px;
    }
    
    .category-title {
        font-size: 13px;
    }
    
    .category-nums {
        font-size: 11px;
        padding: 2px 8px;
    }
}

@media (max-width: 480px) {
    .category-nav {
        padding: 12px 8px;
    }
    
    .category-icon {
        font-size: 24px;
    }
    
    .category-title {
        font-size: 12px;
    }
}
</style>
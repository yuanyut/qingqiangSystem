<script setup lang="ts">
defineProps<{
    title?: string
    desc?: string
}>()

const emit = defineEmits<{
    (e: 'click'): void
}>()
</script>

<template>
    <div class="knowledge-card" @click="emit('click')">
        <div class="card-title">{{ title }}</div>
        <div class="card-desc" v-if="desc"> {{ desc }}</div>
        <div class="card-footer" v-if="$slots.footer">
            <slot name="footer"></slot>
        </div>
    </div>
</template>

<style scoped>
.knowledge-card {
    background: #ffffff;
    border-radius: 20px;
    padding: 20px 24px;
    transition: all 0.3s cubic-bezier(0.2, 0, 0, 1);
    border: 1px solid #f0e6dc;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.02);
    cursor: pointer;
}

.knowledge-card:hover {
    transform: translateY(-4px);
    border-color: #e6d5c4;
    box-shadow: 0 12px 24px -10px rgba(98, 67, 41, 0.12);
    background: #fefcf9;
}

.card-title {
    font-size: 1.1rem;
    font-weight: 600;
    color: #3a2c21;
    line-height: 1.4;
    margin-bottom: 12px;
    letter-spacing: -0.2px;
    transition: color 0.2s;
}

.knowledge-card:hover .card-title {
    color: #b87c4e;
}

.card-desc {
    font-size: 0.88rem;
    color: #7a6856;
    line-height: 1.5;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}

.card-footer {
    margin-top: 16px;
    padding-top: 12px;
    border-top: 1px solid #f3ebe2;
    font-size: 0.85rem;
    font-weight: 500;
    color: #b87c4e;
    transition: all 0.2s;
    display: inline-block;
    cursor: pointer;
}

.card-footer:hover {
    color: #8b5a3a;
    transform: translateX(4px);
}

/* 当没有描述时，标题的下边距调整 */
.knowledge-card:has(.card-desc:not([v-if])) .card-title {
    margin-bottom: 0;
}

/* 当没有 footer 时，去掉多余边框 */
.card-footer:empty {
    display: none;
}
</style>
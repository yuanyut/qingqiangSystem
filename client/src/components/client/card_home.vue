<script setup lang="ts">

</script>
<template>
    <div class="card">
        <div class="card_header">
            <image src="/home/banner1.png" size="100%" />
        </div>
        <div class="card_content">
            <div class="card_name">
                123
            </div>
            <div class="card_desc">
                123
            </div>
            <div class="card_title">
                <div>
                    分类
                </div>
                <div>
                    演唱者
                </div>
            </div>
            <div class="card_info">
                <div>观看次数</div>
                <div>喜爱人数</div>
            </div>
            <div class="card_footer">
                <div-button type="primary" size="small">观看</div-button>
            </div>
        </div>
    </div>
</template>
<style scoped>
.card {
    padding: 20px 0px 20px 20px;
}

.card_header {
    height: 200px;
    width: 200px;
}

.card_content {
    width: 200px;
}
.card_info{
    display: flex;
    justify-content: space-between;
}
</style>
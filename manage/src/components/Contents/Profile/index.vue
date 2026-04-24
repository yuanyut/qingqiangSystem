<script setup lang="ts">
import opearHeader from '@/components/Contents/Profile/tables/opearHeader.vue';
import opear from '@/components/Contents/Profile/tables/opear.vue'
import tableContent from '@/components/Contents/Profile/tables/tableContent.vue';
import { reactive, watch, ref, onMounted } from 'vue';
import { getAdminProfileList, getProfileById, addProfile, updateProfile, deleteProfile } from '@/api/api';
import type { ProfileListParams, ProfileData } from '@/api/api';
import { ElMessage } from 'element-plus';

// 表格数据
const tableData = reactive<ProfileData[]>([])

// 编辑内容
const editContent = ref<ProfileData | undefined>(undefined)

// 编辑弹窗
const editModul = ref(false)

// 新增/编辑弹窗
const addEdit = ref<ProfileData | undefined>(undefined)

// 批量删除模式
const selects = ref(false)

// 多选数据
const multipleSelection = ref<ProfileData[]>([])

// 搜索表单数据
const formHeader = ref({
    title: '',
    category: '',
    status: ''
})

// 搜索触发标志
const search = ref(false)

// 分页数据
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 加载状态
const loading = ref(false)

// 监听编辑内容变化，处理新增和编辑
watch(editContent, (newVal) => {
    console.log('index组件收到editContent变化:', newVal)
    if (newVal) {
        addEdit.value = newVal
        if (!newVal.id) {
            fetchProfileList()
        } else {
            const index = tableData.findIndex((item: ProfileData) => item.id === newVal.id)
            if (index !== -1) {
                const profile = tableData[index];
                if (profile) {
                    Object.assign(profile, {
                        title: newVal.title || '',
                        content: newVal.content || '',
                        source: newVal.source || '',
                        cover: newVal.cover || '',
                        viewCount: newVal.viewCount || 0,
                        likeCount: newVal.likeCount || 0,
                        createTime: newVal.createTime || '',
                        updateTime: newVal.updateTime || '',
                        status: newVal.status || 0,
                        category: newVal.category || ''
                    });
                }
                console.log('表格数据更新后:', tableData[index])
            }
        }
    }
}, { deep: true })

// 监听搜索条件变化
watch(formHeader, (newVal) => {
    console.log('搜索条件变化:', newVal)
}, { deep: true })

// 监听搜索触发
watch(search, (newVal) => {
    if (newVal) {
        console.log('执行搜索，条件:', formHeader.value)
        currentPage.value = 1
        fetchProfileList()
        search.value = false
    }
})

// 获取资讯列表
const fetchProfileList = async () => {
    loading.value = true
    try {
        const params: ProfileListParams = {
            page: currentPage.value,
            size: pageSize.value,
            category: formHeader.value.category || undefined,
            keyword: formHeader.value.title || undefined
        }
        
        // 处理status参数：将字符串转换为数字
        if (formHeader.value.status && formHeader.value.status !== '') {
            if (formHeader.value.status === '已上架') {
                params.status = 0
            } else if (formHeader.value.status === '已下架') {
                params.status = 1
            }
        }
        
        const response = await getAdminProfileList(params)
        if (response && response.data) {
            // 转换后端数据为前端需要的格式
            tableData.length = 0
            response.data.list.forEach((item: any) => {
                console.log(item)
                tableData.push({
                    id: item.id,
                    title: item.title || '',
                    content: item.content || '',
                    source: item.source || '',
                    cover: item.cover || '',
                    viewCount: item.viewCount || 0,
                    likeCount: item.likeCount || 0,
                    createTime: item.createTime || '',
                    updateTime: item.updateTime || '',
                    status: item.status || 0,
                    category: item.category || ''
                })
                console.log(tableData)
            })
            total.value = response.data.total || 0
        }
    } catch (error) {
        console.error('获取资讯列表失败:', error)
        ElMessage.error('获取资讯列表失败，请重试')
    } finally {
        loading.value = false
    }
}

// 处理分页变化
const handleSizeChange = (val: number) => {
    pageSize.value = val
    fetchProfileList()
}

const handleCurrentChange = (val: number) => {
    currentPage.value = val
    fetchProfileList()
}

// 标签配置
interface LableItem {
    lable1?: string,
    lable2?: string,
    lable3?: string,
}

const labels = reactive<LableItem>({
    lable1: '标题',
    lable2: '分类',
    lable3: '状态'
})

// 初始化
onMounted(() => {
    fetchProfileList()
})
</script>

<template>
    <div class="profile-manage">
        <!-- 搜索头部 -->
        <opearHeader 
            v-model:form-header="formHeader" 
            v-model:search="search" 
            :lable="labels"
        />
        
        <!-- 操作栏 -->
        <opear 
            v-model:editContent="editContent" 
            v-model:multipleSelection="multipleSelection"
            v-model:selects="selects"
            @refresh="fetchProfileList"
        />
        
        <!-- 表格内容 -->
        <div v-loading="loading" element-loading-text="加载中..." style="min-height: 400px;">
            <tableContent 
                v-model:tableData="tableData" 
                v-model:selects="selects"
                v-model:multipleSelection="multipleSelection" 
                v-model:form-header="formHeader" 
                v-model:search="search"
                v-model:editModul="editModul"
                v-model:editContent="editContent"
            />
        </div>
        
        <!-- 分页 -->
        <div style="margin-top: 20px; display: flex; justify-content: center;">
            <el-pagination
                v-model:current-page="currentPage"
                v-model:page-size="pageSize"
                :page-sizes="[10, 20, 50, 100]"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
            />
        </div>
    </div>
</template>

<style scoped>
.profile-manage {
    padding: 20px;
    background: #f5f7fa;
    min-height: 100vh;
}
</style>
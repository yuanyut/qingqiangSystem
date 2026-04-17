<script setup lang="ts">
import opearHeader from '@/components/Contents/Article/tables/opearHeader.vue';
import opear from '@/components/Contents/Article/tables/opear.vue'
import tableContent from '@/components/Contents/Article/tables/tableContent.vue';
import { reactive, watch, ref, onMounted } from 'vue';
import { getCultureList,  } from '@/api/api';
import type { CultureListParams } from '@/api/api';
import { ElMessage } from 'element-plus';

// 文章接口定义
interface Article {
    id?: number
    coverUrl: string
    title: string
    category: string
    author: string
    publishTime?: string
    statusText?: string
    viewCount?: number
    likeCount?: number
    commentCount?: number
    summary: string
    content: string
}

// 表格数据
const tableData = reactive<Article[]>([])

// 编辑内容
const editContent = ref<Article | null>(null)

// 编辑弹窗
const editModul = ref(false)

// 新增/编辑弹窗
const addEdit = ref<Article | null>(null)

// 批量删除模式
const selects = ref(false)

// 多选数据
const multipleSelection = ref<Article[]>([])

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
    if (newVal) {
        addEdit.value = newVal
        // 如果是新增（没有id），重新获取列表数据
        if (!newVal.id) {
            // 重新获取列表数据，确保与数据库一致
            fetchCultureList()
        } else {
            // 如果是编辑（有id），更新表格中对应的数据
            const index = tableData.findIndex((item: Article) => item.id === newVal.id)
            if (index !== -1) {
                // 更新表格数据，确保与编辑后的数据一致
                tableData[index] = {
                    //定位与保留：通过 tableData[index] 找到数组里的那行数据，并用
                    //  ...tableData[index]（展开运算符）把原有的其他字段（比如 id 或 createTime）
                    // 保留下来。
                    ...tableData[index],
                    //覆盖与更新：用 newVal（新值）里的内容去替换掉 title、summary 等指定字段。
                    title: newVal.title || '',
                    summary: newVal.summary || '',
                    content: newVal.content || '',
                    category: newVal.category || '',
                    coverUrl: newVal.coverUrl || '',
                    author: newVal.author || '管理员',
                    viewCount: newVal.viewCount || 0,
                    likeCount: newVal.likeCount || 0
                }
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
        fetchCultureList()
        search.value = false
    }
})

// 获取文化内容列表
const fetchCultureList = async () => {
    loading.value = true
    try {
        const params: CultureListParams = {
            page: currentPage.value,
            size: pageSize.value,
            category: formHeader.value.category || undefined,
            keyword: formHeader.value.title || undefined
        }
        const response = await getCultureList(params)
        if (response && response.data) {
            // 转换后端数据为前端需要的格式
            tableData.length = 0
            response.data.list.forEach((item: any) => {
                console.log(item)
                tableData.push({
                    id: item.id,
                    coverUrl: item.cover || '',
                    title: item.title || '',
                    category: item.category || '',
                    author: '管理员', // 后端没有作者字段，暂时固定为管理员
                    publishTime: item.createTime || '',
                    statusText: '已发布', // 后端没有状态字段，暂时固定为已发布
                    viewCount: item.viewCount || 0,
                    likeCount: item.likeCount || 0,
                    commentCount: 0, // 后端没有评论数字段，暂时固定为0
                    summary: item.content ? item.content.substring(0, 100) + '...' : '',
                    content: item.content || ''
                })
            })
            total.value = response.data.total || 0
        }
    } catch (error) {
        console.error('获取文化内容列表失败:', error)
        ElMessage.error('获取文化内容列表失败，请重试')
    } finally {
        loading.value = false
    }
}

// 处理分页变化
const handleSizeChange = (val: number) => {
    pageSize.value = val
    fetchCultureList()
}

const handleCurrentChange = (val: number) => {
    currentPage.value = val
    fetchCultureList()
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
    fetchCultureList()
})
</script>

<template>
    <div class="article-manage">
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
.article-manage {
    padding: 20px;
    background: #f5f7fa;
    min-height: 100vh;
}
</style>
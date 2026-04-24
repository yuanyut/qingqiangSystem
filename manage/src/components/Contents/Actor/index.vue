<script setup lang="ts">
import opearHeader from '@/components/Contents/Actor/tables/opearHeader.vue';
import opear from '@/components/Contents/Actor/tables/opear.vue'
import tableContent from '@/components/Contents/Actor/tables/tableContent.vue';
import { reactive, watch, ref, onMounted } from 'vue';
import { getAdminActorList } from '@/api/api';
import type { ActorListParams } from '@/api/api';
import { ElMessage } from 'element-plus';

// 演员接口定义
interface Actor {
    id?: number
    name: string
    avatar: string
    intro: string
    roleName: string
    style: string
    worksCount: number
    viewCount: number
    likeCount: number
    joinDate: string
    status: number
    createTime: string
    updateTime: string
    dramas: any[]
}

// 表格数据
const tableData = reactive<Actor[]>([])

// 编辑内容
const editContent = ref<Actor | undefined>(undefined)

// 编辑弹窗
const editModul = ref(false)

// 新增/编辑弹窗
const addEdit = ref<Actor | undefined>(undefined)

// 批量删除模式
const selects = ref(false)

// 多选数据
const multipleSelection = ref<Actor[]>([])

// 搜索表单数据
const formHeader = ref({
    title: '',
    style: '',
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
        // 如果是新增（没有id），重新获取列表数据
        if (!newVal.id) {
            // 重新获取列表数据，确保与数据库一致
            console.log('新增操作，重新获取列表数据')
            fetchActorList()
        } else {
            // 如果是编辑（有id），更新表格中对应的数据
            const index = tableData.findIndex((item: Actor) => item.id === newVal.id)
            if (index !== -1) {
                // 更新表格数据，确保与编辑后的数据一致
                console.log('编辑操作，更新表格数据，索引:', index)
                // 使用Object.assign更新原对象，保持响应式
                const actor = tableData[index];
                if (actor) {
                    Object.assign(actor, {
                        name: newVal.name || '',
                        avatar: newVal.avatar || '',
                        intro: newVal.intro || '',
                        roleName: newVal.roleName || '',
                        style: newVal.style || '',
                        joinDate: newVal.joinDate || '',
                        worksCount: newVal.worksCount || 0,
                        viewCount: newVal.viewCount || 0,
                        likeCount: newVal.likeCount || 0,
                        createTime: newVal.createTime || '',
                        updateTime: newVal.updateTime || '',
                        status: newVal.status || 1,
                        dramas: newVal.dramas || []
                    });
                }
                console.log('表格数据更新后:', tableData[index])
            } else {
                console.log('未找到对应id的演员数据:', newVal.id)
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
        fetchActorList()
        search.value = false
    }
})

// 获取演员列表
const fetchActorList = async () => {
    loading.value = true
    try {
        const params: ActorListParams = {
            page: currentPage.value,
            size: pageSize.value,
            style: formHeader.value.style || undefined,
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
        
        const response = await getAdminActorList(params)
        if (response && response.data) {
            // 转换后端数据为前端需要的格式
            tableData.length = 0
            response.data.list.forEach((item: any) => {
                console.log(item)
                tableData.push({
                    id: item.id,
                    name: item.name || '',
                    avatar: item.avatar || '',
                    intro: item.intro || '',
                    roleName: item.roleName || '',
                    style: item.style || '',
                    worksCount: item.worksCount || 0,
                    viewCount: item.viewCount || 0,
                    likeCount: item.likeCount || 0,
                    joinDate: item.joinDate || '',
                    status: item.status || 1,
                    createTime: item.createTime || '',
                    updateTime: item.updateTime || '',
                    dramas: item.dramas || []
                })
                console.log(tableData)
            })
            total.value = response.data.total || 0
        }
    } catch (error) {
        console.error('获取演员列表失败:', error)
        ElMessage.error('获取演员列表失败，请重试')
    } finally {
        loading.value = false
    }
}

// 处理分页变化
const handleSizeChange = (val: number) => {
    pageSize.value = val
    fetchActorList()
}

const handleCurrentChange = (val: number) => {
    currentPage.value = val
    fetchActorList()
}

// 标签配置
interface LableItem {
    lable1?: string,
    lable2?: string,
    lable3?: string,
}

const labels = reactive<LableItem>({
    lable1: '姓名',
    lable2: '风格',
    lable3: '状态'
})

// 初始化
onMounted(() => {
    fetchActorList()
})
</script>

<template>
    <div class="actor-manage">
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
.actor-manage {
    padding: 20px;
    background: #f5f7fa;
    min-height: 100vh;
}
</style>
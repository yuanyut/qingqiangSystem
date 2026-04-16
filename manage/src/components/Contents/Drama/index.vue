<script setup lang="ts">
import opearHeader from '@/components/Contents/Drama/tables/opearHeader.vue';
import opear from '@/components/Contents/Drama/tables/opear.vue'
import tableContent from '@/components/Contents/Drama/tables/tableContent.vue';
import { reactive, watch, ref, onMounted } from 'vue';
import { getDramaList, addDrama, updateDrama, deleteDrama, getDramaById } from '@/api/api';

interface Drama {
  id: number
  videoUrl: string
  title: string
  description: string
  actor: string
  category: string
  duration: number
  clickCount: number
  likeCount: number
  publishTime: string
  statusText: string
}

const tableData: Drama[] = reactive([])
const total = ref(0)
const page = ref(1)
const size = ref(10)

const editContent = ref<Drama>({
  id: 0,
  videoUrl: '',
  title: '',
  description: '',
  actor: '',
  category: '',
  duration: 0,
  clickCount: 0,
  likeCount: 0,
  publishTime: '',
  statusText: ''
})

const selects = ref(false)
const multipleSelection = ref<Drama[]>([])

const formHeader = ref({
  name: '',
  phone: '',
  juese: '',
  status: ''
})

const search = ref(false)

interface lableItem {
  lable1?: string,
  lable2?: string,
  lable3?: string,
  lable4?: string,
  lable5?: string,
  lable6?: string,
  lable7?: string,
}

const labels=reactive<lableItem>({
  lable5:'名称',
  lable6:'分类',
  lable7:'状态'
})

// 获取剧目列表
const fetchDramaList = async () => {
  try {
    const response = await getDramaList({
      page: page.value,
      size: size.value,
      keyword: formHeader.value.name,
      categoryId: formHeader.value.juese === '传统剧目' ? 1 : 
                 formHeader.value.juese === '历史剧' ? 2 : 
                 formHeader.value.juese === '爱情剧' ? 3 : 
                 formHeader.value.juese === '忠义剧' ? 4 : 
                 formHeader.value.juese === '神话剧' ? 5 : 
                 formHeader.value.juese === '悲剧' ? 6 : 
                 formHeader.value.juese === '公案剧' ? 7 : 
                 formHeader.value.juese === '折子戏' ? 8 : 
                 formHeader.value.juese === '现代戏' ? 9 : undefined
    });
    if (response && response.data) {
      tableData.length = 0;
      response.data.list.forEach((item: any) => {
        // 查找视频类型的content，用于视频预览
        const videoContent = item.contents && item.contents.find((c: any) => c.type === 'video');
        
        tableData.push({
          id: item.id,
          videoUrl: videoContent && videoContent.mediaUrl ? videoContent.mediaUrl.replace(/`/g, '') : (item.cover ? item.cover.replace(/`/g, '') : ''),
          title: item.name,
          description: item.intro || '',
          contents: item.contents || [], // 保存关联的content内容
          actor: item.actorDetails && item.actorDetails.length > 0 ? item.actorDetails.map(actor => actor.name).join('、') : '未知',
          category: item.categoryDetail ? item.categoryDetail.name : 
                    item.categoryId === 1 ? '传统剧目' : 
                    item.categoryId === 2 ? '历史剧' : 
                    item.categoryId === 3 ? '爱情剧' : 
                    item.categoryId === 4 ? '忠义剧' : 
                    item.categoryId === 5 ? '神话剧' : 
                    item.categoryId === 6 ? '悲剧' : 
                    item.categoryId === 7 ? '公案剧' : 
                    item.categoryId === 8 ? '折子戏' : 
                    item.categoryId === 9 ? '现代戏' : '其他',
          duration: 0, // 暂时使用默认值
          clickCount: videoContent ? (videoContent.viewCount || 0) : (item.viewCount || 0),
          likeCount: videoContent ? (videoContent.likeCount || 0) : (item.likeCount || 0),
          publishTime: item.publishDate ? new Date(item.publishDate).toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' }) : '',
          statusText: item.status === 1 ? '已发布' : '已下架'
        });
      });
      total.value = response.data.total || 0;
    }
  } catch (error) {
    console.error('获取剧目列表失败:', error);
  }
}

// 搜索剧目
watch([formHeader, search], () => {
  if (search.value) {
    page.value = 1;
    fetchDramaList();
  }
}, { deep: true })

// 处理确认事件
const handleConfirm = async (data: Drama) => {
  if (data.title && data.publishTime === '') {
    // 新增剧目
    try {
      await addDrama({
        name: data.title,
        cover: data.videoUrl,
        intro: data.description,
        categoryId: data.category === '传统剧目' ? 1 : 
                   data.category === '历史剧' ? 2 : 
                   data.category === '爱情剧' ? 3 : 
                   data.category === '忠义剧' ? 4 : 
                   data.category === '神话剧' ? 5 : 
                   data.category === '悲剧' ? 6 : 
                   data.category === '公案剧' ? 7 : 
                   data.category === '折子戏' ? 8 : 
                   data.category === '现代戏' ? 9 : 1,
        status: data.statusText === '已发布' ? 1 : 0
      });
      fetchDramaList();
    } catch (error) {
      console.error('新增剧目失败:', error);
    }
  } else if (data.id) {
    // 编辑剧目
    try {
      await updateDrama({
        id: data.id,
        name: data.title,
        cover: data.videoUrl,
        intro: data.description,
        categoryId: data.category === '传统剧目' ? 1 : 
                   data.category === '历史剧' ? 2 : 
                   data.category === '爱情剧' ? 3 : 
                   data.category === '忠义剧' ? 4 : 
                   data.category === '神话剧' ? 5 : 
                   data.category === '悲剧' ? 6 : 
                   data.category === '公案剧' ? 7 : 
                   data.category === '折子戏' ? 8 : 
                   data.category === '现代戏' ? 9 : 1,
        status: data.statusText === '已发布' ? 1 : 0
      });
      fetchDramaList();
    } catch (error) {
      console.error('编辑剧目失败:', error);
    }
  }
}

// 删除剧目
const handleDelete = async (id: number) => {
  try {
    await deleteDrama(id);
    fetchDramaList();
  } catch (error) {
    console.error('删除剧目失败:', error);
  }
}

// 批量删除剧目
const handleBatchDelete = async () => {
  if (multipleSelection.value.length === 0) return;
  try {
    const ids = multipleSelection.value.map((item: Drama) => item.id);
    await deleteDrama(ids);
    fetchDramaList();
  } catch (error) {
    console.error('批量删除剧目失败:', error);
  }
}

// 编辑剧目
const handleEdit = async (row: Drama) => {
  try {
    const response = await getDramaById(row.id);
    if (response && response.data) {
      // 查找视频类型的content，用于视频预览
      const videoContent = response.data.contents && response.data.contents.find((c: any) => c.type === 'video');
      
      editContent.value = {
        id: response.data.id,
        videoUrl: videoContent && videoContent.mediaUrl ? videoContent.mediaUrl.replace(/`/g, '') : (response.data.cover ? response.data.cover.replace(/`/g, '') : ''),
        title: response.data.name,
        description: response.data.intro || '',
        actor: response.data.actorDetails && response.data.actorDetails.length > 0 ? response.data.actorDetails.map(actor => actor.name).join('、') : '未知',
        category: response.data.categoryDetail ? response.data.categoryDetail.name : 
                  response.data.categoryId === 1 ? '传统剧目' : 
                  response.data.categoryId === 2 ? '历史剧' : 
                  response.data.categoryId === 3 ? '爱情剧' : 
                  response.data.categoryId === 4 ? '忠义剧' : 
                  response.data.categoryId === 5 ? '神话剧' : 
                  response.data.categoryId === 6 ? '悲剧' : 
                  response.data.categoryId === 7 ? '公案剧' : 
                  response.data.categoryId === 8 ? '折子戏' : 
                  response.data.categoryId === 9 ? '现代戏' : '其他',
        duration: 0, // 暂时使用默认值
        clickCount: videoContent ? (videoContent.viewCount || 0) : (response.data.viewCount || 0),
        likeCount: videoContent ? (videoContent.likeCount || 0) : (response.data.likeCount || 0),
        publishTime: response.data.publishDate ? new Date(response.data.publishDate).toLocaleString() : '',
        statusText: response.data.status === 1 ? '已发布' : '已下架'
      };
    }
  } catch (error) {
    console.error('获取剧目详情失败:', error);
  }
}

// 编辑点击事件
const editClick = (row: Drama) => {
  handleEdit(row);
}

// 删除点击事件
const deleteClick = (id: number) => {
  handleDelete(id);
}

// 批量删除点击事件
const batchDeleteClick = () => {
  handleBatchDelete();
}

// 初始化
onMounted(() => {
  fetchDramaList();
})

</script>
<template>
    <div>
        <opearHeader v-model:form-header="formHeader" v-model:search="search" :lable="labels"></opearHeader>
        <opear v-model:editContent="editContent" v-model:multipleSelection="multipleSelection"
            v-model:selects="selects" @batch-delete="batchDeleteClick" @confirm="handleConfirm"></opear>
        <tableContent v-model:tableData="tableData" v-model:selects="selects"
            v-model:multipleSelection="multipleSelection" v-model:form-header="formHeader" v-model:search="search"
            v-model:currentPage="page" v-model:pageSize="size" v-model:total="total"
            @edit-click="editClick" @delete-click="deleteClick" @confirm="handleConfirm" @page-change="fetchDramaList" @size-change="fetchDramaList">
        </tableContent>
    </div>
</template>
<style scoped></style>
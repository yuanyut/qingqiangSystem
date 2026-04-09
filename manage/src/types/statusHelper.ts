import {statusTextMap} from '@/types/article'
import type { ArticleStatus } from '@/types/article';
//获取状态文字
export const getStatusText=(statuss:ArticleStatus)=>{
    console.log(statuss)
    return statusTextMap[statuss]
}
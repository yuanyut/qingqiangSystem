import request from './request'

export interface News {
  id: number
  title: string
  content: string
  createTime: string
  viewCount: number
  source: string
  category: string
}

// 分类映射表
export const categoryMap: Record<string, string> = {
  performance: '演出信息',
  competition: '赛事活动',
  announcement: '公告通知',
  news: '动态新闻',
  heritage: '传承动态',
  academic: '学术研究',
  exchange: '交流活动',
  interview: '人物专访',
  media: '媒体报道'
}

export interface Category {
  id: number
  name: string
  icon: string
  count: number
}

export interface NewsListResponse {
  list: News[]
  total: number
}

export const getNewsList = (page: number, size: number, category?: number, keyword?: string) => {
  return request<NewsListResponse>({
    url: '/news/list',
    method: 'get',
    params: { page, size, category, keyword }
  })
}

// 获取新闻分类统计
export const getCategoryNav = () => {
  return request<Category[]>({
    url: '/news/categories',
    method: 'get'
  })
}

// 获取头条资讯
export const getTopNews = () => {
  return request<News>({
    url: '/news/top',
    method: 'get'
  })
}

// 获取热门排行
export const getHotList = (size: number) => {
  return request<News[]>({
    url: '/news/hot',
    method: 'get',
    params: {
      size
    }
  })
}

// 获取推荐阅读
export const getRecommendList = (size: number) => {
  return request<News[]>({
    url: '/news/recommend',
    method: 'get',
    params: {
      size
    }
  })
}

// 获取媒体聚焦（视频类）
export const getMediaList = (size: number) => {
  return request<News[]>({
    url: '/news/media',
    method: 'get',
    params: {
      size
    }
  })
}

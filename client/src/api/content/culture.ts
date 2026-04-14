import request from '../request'

export interface Category {
  id: number
  name: string
  icon: string
  count: number
}

export interface NewsItem {
  id: number
  title: string
  content: string
  cover: string
  status: number
  viewCount: number
  likeCount: number
  category: string
  createTime: string
  updateTime: string
}

export interface NewsListResponse {
  list: NewsItem[]
  total: number
}

// 获取文化资讯分类统计
export const getCategoryNav = () => {
  return request<Category[]>({
    url: '/content/culture/categories',
    method: 'get'
  })
}

// 获取头条资讯
export const getTopNews = () => {
  return request<NewsItem>({
    url: '/content/culture/top',
    method: 'get'
  })
}

// 获取最新资讯分页列表
export const getNewsList = (page: number, size: number) => {
  return request<NewsListResponse>({
    url: '/content/culture/list',
    method: 'get',
    params: {
      page,
      size
    }
  })
}

// 获取热门排行
export const getHotList = (size: number) => {
  return request<NewsItem[]>({
    url: '/content/culture/hot',
    method: 'get',
    params: {
      size
    }
  })
}

// 获取推荐阅读
export const getRecommendList = (size: number) => {
  return request<NewsItem[]>({
    url: '/content/culture/recommend',
    method: 'get',
    params: {
      size
    }
  })
}

// 获取媒体聚焦（视频类）
export const getMediaList = (size: number) => {
  return request<NewsItem[]>({
    url: '/content/culture/media',
    method: 'get',
    params: {
      size
    }
  })
}

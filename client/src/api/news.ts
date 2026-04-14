import request from './request'

export interface News {
  id: number
  title: string
  content: string
  publishTime: string
  viewCount: number
  source: string
}

export interface NewsListResponse {
  list: News[]
  total: number
}

export const getNewsList = (page: number, size: number) => {
  return request<NewsListResponse>({
    url: '/news/list',
    method: 'get',
    params: {
      page,
      size
    }
  })
}

import request from './request'

export interface Content {
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

export interface CultureListResponse {
  list: Content[]
  total: number
}

export const getCultureList = (page: number, size: number, category?: string) => {
  return request<CultureListResponse>({
    url: '/culture/list',
    method: 'get',
    params: {
      page,
      size,
      category
    }
  })
}

export const getCultureDetail = (id: number) => {
  return request<Content>({
    url: `/culture/detail/${id}`,
    method: 'get'
  })
}
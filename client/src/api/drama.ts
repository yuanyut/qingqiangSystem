import request from './request'

export interface Drama {
  id: number
  title: string
  description: string
  categoryId: number
  categoryName: string
  coverImage: string
  watchCount: number
  likeCount: number
  createdAt: string
}

export interface DramaListResponse {
  list: Drama[]
  total: number
}

export const getDramaList = (page: number, size: number, categoryId?: number, keyword?: string) => {
  return request<DramaListResponse>({
    url: '/drama/list',
    method: 'get',
    params: {
      page,
      size,
      categoryId,
      keyword
    }
  })
}

export const getDramaDetail = (id: number) => {
  return request<Drama>({
    url: `/drama/detail/full/${id}`,
    method: 'get'
  })
}

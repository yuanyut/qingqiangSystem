import request from './request'

export interface Actor {
  id: number
  name: string
  title: string
  avatar: string
  works: string[]
  fansCount: number
  worksCount: number
  awardsCount: number
}

export interface ActorDetail {
  id: number
  name: string
  title: string
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

export interface ActorListResponse {
  list: Actor[]
  total: number
}

export const getActorList = (page: number, size: number, style?: string) => {
  const params: any = {
    page,
    size
  }
  if (style !== undefined && style !== '') {
    params.style = style
  }
  return request<ActorListResponse>({
    url: '/actor/list',
    method: 'get',
    params
  })
}

export const getActorDetail = (id: number) => {
  return request<ActorDetail>({
    url: `/actor/detail/full/${id}`,
    method: 'get'
  })
}

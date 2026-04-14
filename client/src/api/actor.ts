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

export interface ActorListResponse {
  list: Actor[]
  total: number
}

export const getActorList = (page: number, size: number) => {
  return request<ActorListResponse>({
    url: '/actor/list',
    method: 'get',
    params: {
      page,
      size
    }
  })
}

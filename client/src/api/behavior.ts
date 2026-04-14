import request from './request'

export interface BehaviorResponse<T = any> {
  code: number
  message: string
  data: T
}

export interface LikeResponse {
  isLiked: boolean
}

export interface FavoriteResponse {
  isFavorited: boolean
}

export interface Behavior {
  id: number
  userId: number
  targetType: string
  targetId: number
  behaviorType: string
  createTime: string
}

export interface BehaviorListResponse {
  list: Behavior[]
}

// 点赞/取消点赞
export const toggleLike = (targetType: string, targetId: number) => {
  return request<LikeResponse>({
    url: '/behavior/like/toggle',
    method: 'post',
    params: { targetType, targetId }
  })
}

// 收藏/取消收藏
export const toggleFavorite = (targetType: string, targetId: number) => {
  return request<FavoriteResponse>({
    url: '/behavior/favorite/toggle',
    method: 'post',
    params: { targetType, targetId }
  })
}

// 获取用户点赞列表
export const getLikeList = (targetType?: string, page: number = 1, size: number = 10) => {
  return request<BehaviorListResponse>({
    url: '/behavior/like/list',
    method: 'get',
    params: { targetType, page, size }
  })
}

// 获取用户收藏列表
export const getFavoriteList = (targetType?: string, page: number = 1, size: number = 10) => {
  return request<BehaviorListResponse>({
    url: '/behavior/favorite/list',
    method: 'get',
    params: { targetType, page, size }
  })
}

// 获取用户浏览历史
export const getBrowseHistory = (targetType?: string, page: number = 1, size: number = 10) => {
  return request<BehaviorListResponse>({
    url: '/behavior/browse/list',
    method: 'get',
    params: { targetType, page, size }
  })
}

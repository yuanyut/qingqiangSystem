import request from './request'

export interface BehaviorResponse<T = any> {
  code: number
  message: string
  data: T
}

export interface ToggleResponse {
  isLiked?: boolean
  isFavorited?: boolean
}

export interface Behavior {
  id: number
  userId: number
  targetType: string
  targetId: number
  action: string
  status: number
  createTime: string
  updateTime: string
}

export interface BehaviorListResponse {
  list: Behavior[]
  total: number
}

// 统一行为接口：添加行为
export const addBehavior = (targetType: string, targetId: number, action: string) => {
  return request<string>({
    url: '/behavior/add',
    method: 'post',
    params: { targetType, targetId, action }
  })
}

// 统一行为接口：切换行为状态（点赞/收藏）
export const toggleBehavior = (targetType: string, targetId: number, action: string) => {
  return request<ToggleResponse>({
    url: '/behavior/toggle',
    method: 'post',
    params: { targetType, targetId, action }
  })
}

// 查询当前用户行为
export const getMyBehaviors = (targetType?: string, action?: string, page: number = 1, size: number = 10) => {
  return request<BehaviorListResponse>({
    url: '/behavior/my',
    method: 'get',
    params: { targetType, action, page, size }
  })
}

// 检查行为状态
export const checkBehavior = (targetType: string, targetId: number, action: string) => {
  return request<ToggleResponse>({
    url: '/behavior/check',
    method: 'get',
    params: { targetType, targetId, action }
  })
}

// 兼容旧接口：点赞/取消点赞
export const toggleLike = (targetType: string, targetId: number) => {
  return toggleBehavior(targetType, targetId, 'like')
}

// 兼容旧接口：收藏/取消收藏
export const toggleFavorite = (targetType: string, targetId: number) => {
  return toggleBehavior(targetType, targetId, 'favorite')
}

// 兼容旧接口：获取用户点赞列表
export const getLikeList = (targetType?: string, page: number = 1, size: number = 10) => {
  return getMyBehaviors(targetType, 'like', page, size)
}

// 兼容旧接口：获取用户收藏列表
export const getFavoriteList = (targetType?: string, page: number = 1, size: number = 10) => {
  return getMyBehaviors(targetType, 'favorite', page, size)
}

// 兼容旧接口：获取用户浏览历史
export const getBrowseHistory = (targetType?: string, page: number = 1, size: number = 10) => {
  return getMyBehaviors(targetType, 'view', page, size)
}

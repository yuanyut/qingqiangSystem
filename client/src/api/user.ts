import request from './request'

export interface LoginData {
  username: string
  password: string
}

export interface RegisterData {
  username: string
  password: string
  nickname: string
  sex: string
  birthday: string
}

export interface UserInfo {
  id: number
  username: string
  nickname: string
  avatar: string
  address: string
  createTime: string
}

export interface UserStats {
  favoriteCount: number
  viewCount: number
  likeCount: number
}

export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

export const login = (data: LoginData): Promise<ApiResponse<string>> => {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export const register = (data: RegisterData): Promise<ApiResponse<void>> => {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

export const getUserInfo = (): Promise<ApiResponse<UserInfo>> => {
  return request({
    url: '/user/me',
    method: 'get'
  })
}

export const getUserStats = (): Promise<ApiResponse<UserStats>> => {
  return request({
    url: '/user/stats',
    method: 'get'
  })
}

export const getUserFavorites = (page: number = 1, size: number = 10): Promise<ApiResponse<any>> => {
  return request({
    url: '/user/favorite',
    method: 'get',
    params: { page, size }
  })
}

export const getUserHistory = (page: number = 1, size: number = 10): Promise<ApiResponse<any>> => {
  return request({
    url: '/user/history',
    method: 'get',
    params: { page, size }
  })
}

export const getUserLikes = (page: number = 1, size: number = 10): Promise<ApiResponse<any>> => {
  return request({
    url: '/user/like',
    method: 'get',
    params: { page, size }
  })
}

export const deleteBehavior = (id: number): Promise<ApiResponse<void>> => {
  return request({
    url: `/user/behavior/${id}`,
    method: 'delete'
  })
}

export const cancelBehavior = (data: {
  targetType: string
  targetId: number
  action: string
}): Promise<ApiResponse<void>> => {
  return request({
    url: '/user/behavior/cancel',
    method: 'post',
    data
  })
}

export const clearBehavior = (action: string): Promise<ApiResponse<void>> => {
  return request({
    url: '/user/behavior/clear',
    method: 'delete',
    params: { action }
  })
}

// 获取用户详细信息
export const getUserProfile = (): Promise<ApiResponse<any>> => {
  return request({
    url: '/user/profile',
    method: 'get'
  })
}

// 更新用户资料
export const updateUserProfile = (data: {
  nickname?: string
  info?: string
  address?: string
  sex?: string
  birthday?: string
}): Promise<ApiResponse<any>> => {
  return request({
    url: '/user/updateProfile',
    method: 'post',
    data
  })
}

// 上传头像
export const uploadAvatar = (file: File): Promise<ApiResponse<string>> => {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/user/avatar/upload',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 修改密码
export const changePassword = (data: {
  oldPassword: string
  newPassword: string
}): Promise<ApiResponse<void>> => {
  return request({
    url: '/user/changePassword',
    method: 'post',
    data
  })
}

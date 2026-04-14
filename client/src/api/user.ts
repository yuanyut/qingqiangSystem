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

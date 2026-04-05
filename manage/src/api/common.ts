// 写这个 Vue 3 + TypeScript + Axios 的通用请求工具，主要目的是为了 
// 在项目中统一管理接口请求逻辑，避免每个组件都重复写 axios.get 或 axios.post。
import axios from 'axios'
import type { InternalAxiosRequestConfig } from 'axios'

// 创建实例
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
  timeout: 5000,
})

// 请求拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 如果你有 token，可以这样加：
    // const token = localStorage.getItem('token')
    // if (token) config.headers.Authorization = `Bearer ${token}`
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截器
service.interceptors.response.use(
  (response) => response.data,
  (error) => Promise.reject(error)
)

export const get = <T = any>(url: string, params?: object): Promise<T> => {
  return service.get(url, { params })
}

export const post = <T = any>(url: string, data?: object): Promise<T> => {
  return service.post(url, data)
}
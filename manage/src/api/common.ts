// common.ts
import axios from 'axios'
import type { InternalAxiosRequestConfig, AxiosResponse } from 'axios'

// 创建 Axios 实例
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
  timeout: 5000,
})

// 请求拦截器：统一加 token
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('token')

    // Axios 1.x + TS5.x 正确初始化 headers
    if (!config.headers) config.headers = axios.AxiosHeaders.from({})

    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截器：统一处理错误
service.interceptors.response.use(
  (response: AxiosResponse) => response.data, // 直接返回 data
  (error) => {
    console.error('请求失败:', error)
    return Promise.reject(error)
  }
)

// 封装 api 对象
const api = {
  get: <T = any>(url: string, params?: object): Promise<T> => {
    return service.get(url, { params })
  },
  post: <T = any>(url: string, data?: object): Promise<T> => {
    return service.post(url, data)
  },
  put: <T = any>(url: string, data?: object): Promise<T> => {
    return service.put(url, data)
  },
  delete: <T = any>(url: string, config?: object): Promise<T> => {
    return service.delete(url, config)
  },
  // 可以扩展 patch
}

export default api

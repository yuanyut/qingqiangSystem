import axios from 'axios'

export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

const service = axios.create({
  baseURL: 'http://localhost:8081',
  timeout: 5000
})

service.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    if (error.response) {
      return Promise.resolve(error.response.data)
    }
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

const request = <T = any>(config: any): Promise<ApiResponse<T>> => {
  return service(config) as Promise<ApiResponse<T>>
}

export default request

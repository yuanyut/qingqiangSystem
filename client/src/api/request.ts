import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8081',
  timeout: 5000
})

request.interceptors.request.use(
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

request.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    if (error.response) {
      // 服务器返回了错误响应，将其转换为统一格式
      return Promise.resolve(error.response.data)
    }
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

export default request

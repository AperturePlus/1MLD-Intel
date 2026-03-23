import axios from 'axios'
import router from '../router'
import { createMockAdapter, isMockEnabled } from '../mock/httpMock'

const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://115.190.101.111:10001/',
  withCredentials: true,
  timeout: 15000
})

service.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers = config.headers ?? {}
      config.headers.Authorization = `Token ${token}`
    }

    if (isMockEnabled()) {
      const adapter = createMockAdapter(config)
      if (adapter) {
        config.adapter = adapter
      }
    }

    return config
  },
  (error) => Promise.reject(error)
)

service.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('username')

      if (router.currentRoute.value.path !== '/') {
        alert('登录已过期，请重新登录。')
        router.push('/')
      }
    }

    return Promise.reject(error)
  }
)

export default service

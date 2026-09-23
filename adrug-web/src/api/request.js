import axios from 'axios'
import { useAppStore } from '@/stores/app'

const TOKEN_KEY = 'adrug_token'

// 统一 axios 实例；baseURL 可通过 VITE_API_BASE 覆盖，默认走同源（由 vite 代理）
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '',
  timeout: 15000
})

// 请求拦截：租户 ID 走 X-Tenant-Id 头；登录后附 Bearer token
service.interceptors.request.use(
  (config) => {
    const appStore = useAppStore()
    config.headers = config.headers || {}
    config.headers['X-Tenant-Id'] = appStore.tenantId ?? 0
    const token = localStorage.getItem(TOKEN_KEY)
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token
    }
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截：统一解包后端 Result { code, message, data }
service.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res && typeof res === 'object' && 'code' in res) {
      if (res.code === 0) {
        return res.data
      }
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  (error) => Promise.reject(error)
)

export default service

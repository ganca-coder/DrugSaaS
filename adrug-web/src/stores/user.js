import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi } from '@/api/auth'
import { useAppStore } from './app'

const TOKEN_KEY = 'adrug_token'
const USER_KEY = 'adrug_user'

/**
 * 登录态：token + 当前用户信息（登录返回的 LoginVO）。
 */
export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem(TOKEN_KEY) || '')
  const userInfo = ref(JSON.parse(localStorage.getItem(USER_KEY) || 'null'))

  async function login(dto) {
    const data = await loginApi(dto)
    token.value = data.token
    userInfo.value = data
    localStorage.setItem(TOKEN_KEY, data.token)
    localStorage.setItem(USER_KEY, JSON.stringify(data))
    // 登录后租户 ID 取自登录结果，并驱动 X-Tenant-Id 请求头
    useAppStore().setTenantId(data.tenantId)
    return data
  }

  function logout() {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(USER_KEY)
  }

  return { token, userInfo, login, logout }
})

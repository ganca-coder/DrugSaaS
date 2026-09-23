import { defineStore } from 'pinia'
import { ref } from 'vue'

const TENANT_KEY = 'adrug_tenant'

/**
 * 应用级状态：当前租户、侧边栏折叠等。
 * <p>
 * 租户 ID 登录后写入，本地持久化，作为 X-Tenant-Id 请求头来源。
 */
export const useAppStore = defineStore('app', () => {
  const tenantId = ref(Number(localStorage.getItem(TENANT_KEY) ?? 0))
  const collapsed = ref(false)

  function setTenantId(val) {
    tenantId.value = val ?? 0
    localStorage.setItem(TENANT_KEY, String(val ?? 0))
  }

  function toggleCollapsed() {
    collapsed.value = !collapsed.value
  }

  return { tenantId, collapsed, setTenantId, toggleCollapsed }
})

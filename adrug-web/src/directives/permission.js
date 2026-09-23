import { useUserStore } from '@/stores/user'

/**
 * 按钮级权限指令：v-permission="'system:role:create'"
 * 无权限时移除元素；超级管理员（* / *:*）不拦截。
 */
export default {
  mounted(el, binding) {
    const perm = binding.value
    if (!perm) return
    const userStore = useUserStore()
    const permissions = userStore.userInfo?.permissions || []
    if (permissions.includes('*') || permissions.includes('*:*') || permissions.includes(perm)) {
      return
    }
    el.parentNode && el.parentNode.removeChild(el)
  }
}

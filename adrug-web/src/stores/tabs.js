import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

/**
 * 页签状态：顶部多页签 + keep-alive 缓存键。
 * <p>
 * 缓存键取路由 name（即菜单 menuCode），因此各视图组件需 defineOptions({ name: '<menuCode>' })。
 */
export const useTabsStore = defineStore('tabs', () => {
  // 首页固定页签，不可关闭
  const tabs = ref([
    { path: '/dashboard', name: 'home', title: '首页', keepAlive: true, closable: false }
  ])

  /** keep-alive include：仅缓存开启 keepAlive 的页签 */
  const cachedViews = computed(() => tabs.value.filter((t) => t.keepAlive).map((t) => t.name))

  function addTab(route) {
    if (!route.name) return
    const exists = tabs.value.some((t) => t.path === route.path)
    if (exists) return
    tabs.value.push({
      path: route.path,
      name: route.name,
      title: route.meta?.title || route.name,
      keepAlive: route.meta?.keepAlive !== false,
      closable: true
    })
  }

  /**
   * 关闭页签。
   * @param {string} path 页签 path
   * @returns {string|null} 关闭后应跳转的 path（关闭当前页时使用）
   */
  function removeTab(path) {
    const idx = tabs.value.findIndex((t) => t.path === path)
    if (idx === -1 || !tabs.value[idx].closable) return null
    tabs.value.splice(idx, 1)
    const next = tabs.value[Math.min(idx, tabs.value.length - 1)]
    return next ? next.path : '/dashboard'
  }

  return { tabs, cachedViews, addTab, removeTab }
})

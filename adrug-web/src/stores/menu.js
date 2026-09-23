import { defineStore } from 'pinia'
import { ref } from 'vue'
import router from '@/router'
import { getMenuTree } from '@/api/menu'
import { fallbackMenu } from '@/mock/menu'

// 预加载 src/views 下所有页面组件，供菜单 component 字段动态映射
const viewModules = import.meta.glob('../views/**/*.vue')
const componentMap = Object.fromEntries(
  Object.entries(viewModules).map(([key, loader]) => {
    const idx = key.indexOf('/views/')
    const normalized = (idx >= 0 ? key.slice(idx + '/views/'.length) : key).replace(/\.vue$/, '')
    return [normalized, loader]
  })
)

function resolveComponent(component) {
  return component ? componentMap[component] || null : null
}

export const useMenuStore = defineStore('menu', () => {
  const menus = ref([])
  const loaded = ref(false)

  /**
   * 将菜单树拍平为可注册的路由。
   * 仅 menuType = 2（菜单）且 path 存在、且未注册过（排除静态首页）时注册。
   */
  function registerRoutes(tree) {
    for (const item of tree || []) {
      if (item.menuType === 2 && item.path && !router.hasRoute(item.menuCode)) {
        const component = resolveComponent(item.component)
        if (component) {
          router.addRoute('Layout', {
            path: item.path,
            name: item.menuCode,
            component,
            meta: { title: item.menuName, icon: item.icon, keepAlive: true }
          })
        }
      }
      if (item.children && item.children.length) {
        registerRoutes(item.children)
      }
    }
  }

  /**
   * 加载当前租户生效菜单树并注册动态路由；后端不可用时回退本地默认菜单。
   */
  async function loadMenus() {
    let tree
    try {
      tree = await getMenuTree()
    } catch (e) {
      console.warn('[menu] 菜单接口不可用，使用本地默认菜单兜底：', e.message)
      tree = fallbackMenu
    }
    menus.value = tree || []
    registerRoutes(menus.value)
    loaded.value = true
  }

  return { menus, loaded, loadMenus }
})

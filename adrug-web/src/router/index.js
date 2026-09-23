import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/index.vue'

const TOKEN_KEY = 'adrug_token'
const USER_KEY = 'adrug_user'

/**
 * 静态路由：登录 + 布局 + 首页（首页即「首页」菜单，动态路由阶段通过 router.hasRoute 去重）。
 * 其余菜单路由由 stores/menu.js 在加载菜单后动态注入（parent name = 'Layout'）。
 */
export const constantRoutes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/pos',
    name: 'Pos',
    component: () => import('@/views/pos/index.vue'),
    meta: { title: 'POS收银台' }
  },
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'home',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '首页', icon: 'HomeFilled', keepAlive: true }
      },
      {
        path: 'pms/purchase-order/detail/:id',
        name: 'pms-purchase-order-detail',
        component: () => import('@/views/pms/purchase-order/detail.vue'),
        meta: { title: '采购订单详情' }
      },
      {
        path: 'oms/pos-retail-order/detail/:id',
        name: 'oms-pos-retail-order-detail',
        component: () => import('@/views/oms/pos-retail-order/detail.vue'),
        meta: { title: 'POS零售单详情' }
      }
    ]
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/error/404.vue'),
    meta: { title: '404' }
  },
  { path: '/:pathMatch(.*)*', redirect: '/404' }
]

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
  scrollBehavior: () => ({ top: 0 })
})

// 登录守卫：未登录跳转登录页；收银员角色锁定 POS 收银台，非收银员不可进入 POS
router.beforeEach((to) => {
  const token = localStorage.getItem(TOKEN_KEY)
  if (to.path !== '/login' && !token) {
    return '/login'
  }
  if (token) {
    const userInfo = JSON.parse(localStorage.getItem(USER_KEY) || 'null')
    const isCashier = ((userInfo && userInfo.roles) || []).includes('cashier')
    if (to.path === '/login') {
      return isCashier ? '/pos' : '/'
    }
    if (isCashier && !to.path.startsWith('/pos')) {
      return '/pos'
    }
    if (!isCashier && to.path.startsWith('/pos')) {
      return '/'
    }
  }
  return true
})

export default router

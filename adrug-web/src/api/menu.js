import request from './request'

// 菜单接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递，不再走路径/入参。
export function getMenuTree() {
  return request({ url: '/app/oper/common/info/getMenuTree', method: 'get' })
}

export function pageMenu(params) {
  return request({ url: '/app/oper/common/info/getMenuPage', method: 'post', data: params })
}

export function getMenu(id) {
  return request({ url: `/app/oper/common/info/menu/${id}`, method: 'get' })
}

export function createMenu(data) {
  return request({ url: '/app/oper/common/info/menu', method: 'post', data })
}

export function updateMenu(id, data) {
  return request({ url: `/app/oper/common/info/menu/${id}`, method: 'put', data })
}

export function deleteMenu(id) {
  return request({ url: `/app/oper/common/info/menu/${id}`, method: 'delete' })
}

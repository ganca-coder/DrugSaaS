import request from './request'

// 验收单接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pageAcceptanceOrder(params) {
  return request({ url: '/app/oper/bus/pms/getAcceptanceOrderPage', method: 'post', data: params })
}

export function getAcceptanceOrder(id) {
  return request({ url: `/app/oper/bus/pms/acceptance-order/${id}`, method: 'get' })
}

export function createAcceptanceOrder(data) {
  return request({ url: '/app/oper/bus/pms/acceptance-order', method: 'post', data })
}

export function updateAcceptanceOrder(id, data) {
  return request({ url: `/app/oper/bus/pms/acceptance-order/${id}`, method: 'put', data })
}

export function deleteAcceptanceOrder(id) {
  return request({ url: `/app/oper/bus/pms/acceptance-order/${id}`, method: 'delete' })
}

export function acceptAcceptanceOrder(id) {
  return request({ url: `/app/oper/bus/pms/acceptance-order/${id}/accept`, method: 'post' })
}

export function postAcceptanceOrder(id) {
  return request({ url: `/app/oper/bus/pms/acceptance-order/${id}/post`, method: 'post' })
}

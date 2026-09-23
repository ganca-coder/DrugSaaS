import request from './request'

// 拒收单接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pageRejectionOrder(params) {
  return request({ url: '/app/oper/bus/pms/getRejectionOrderPage', method: 'post', data: params })
}

export function getRejectionOrder(id) {
  return request({ url: `/app/oper/bus/pms/rejection-order/${id}`, method: 'get' })
}

export function createRejectionOrder(data) {
  return request({ url: '/app/oper/bus/pms/rejection-order', method: 'post', data })
}

export function updateRejectionOrder(id, data) {
  return request({ url: `/app/oper/bus/pms/rejection-order/${id}`, method: 'put', data })
}

export function deleteRejectionOrder(id) {
  return request({ url: `/app/oper/bus/pms/rejection-order/${id}`, method: 'delete' })
}

export function postRejectionOrder(id) {
  return request({ url: `/app/oper/bus/pms/rejection-order/${id}/post`, method: 'post' })
}

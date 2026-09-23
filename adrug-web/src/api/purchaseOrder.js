import request from './request'

// 采购订单接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pagePurchaseOrder(params) {
  return request({ url: '/app/oper/bus/pms/getPurchaseOrderPage', method: 'post', data: params })
}

export function getPurchaseOrder(id) {
  return request({ url: `/app/oper/bus/pms/purchase-order/${id}`, method: 'get' })
}

export function createPurchaseOrder(data) {
  return request({ url: '/app/oper/bus/pms/purchase-order', method: 'post', data })
}

export function updatePurchaseOrder(id, data) {
  return request({ url: `/app/oper/bus/pms/purchase-order/${id}`, method: 'put', data })
}

export function deletePurchaseOrder(id) {
  return request({ url: `/app/oper/bus/pms/purchase-order/${id}`, method: 'delete' })
}

export function postPurchaseOrder(id) {
  return request({ url: `/app/oper/bus/pms/purchase-order/${id}/post`, method: 'post' })
}

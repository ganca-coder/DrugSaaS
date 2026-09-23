import request from './request'

// 收货单接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pageReceivingOrder(params) {
  return request({ url: '/app/oper/bus/pms/getReceivingOrderPage', method: 'post', data: params })
}

export function getReceivingOrder(id) {
  return request({ url: `/app/oper/bus/pms/receiving-order/${id}`, method: 'get' })
}

export function createReceivingOrder(data) {
  return request({ url: '/app/oper/bus/pms/receiving-order', method: 'post', data })
}

export function updateReceivingOrder(id, data) {
  return request({ url: `/app/oper/bus/pms/receiving-order/${id}`, method: 'put', data })
}

export function deleteReceivingOrder(id) {
  return request({ url: `/app/oper/bus/pms/receiving-order/${id}`, method: 'delete' })
}

export function postReceivingOrder(id) {
  return request({ url: `/app/oper/bus/pms/receiving-order/${id}/post`, method: 'post' })
}

export function loadReceivingItems(purchaseOrderId) {
  return request({ url: `/app/oper/bus/pms/receiving-order/transfer/${purchaseOrderId}`, method: 'get' })
}

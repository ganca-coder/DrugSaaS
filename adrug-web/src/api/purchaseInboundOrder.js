import request from './request'

// 采购入库单接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pagePurchaseInboundOrder(params) {
  return request({ url: '/app/oper/bus/pms/getPurchaseInboundOrderPage', method: 'post', data: params })
}

export function getPurchaseInboundOrder(id) {
  return request({ url: `/app/oper/bus/pms/purchase-inbound-order/${id}`, method: 'get' })
}

export function createPurchaseInboundOrder(data) {
  return request({ url: '/app/oper/bus/pms/purchase-inbound-order', method: 'post', data })
}

export function updatePurchaseInboundOrder(id, data) {
  return request({ url: `/app/oper/bus/pms/purchase-inbound-order/${id}`, method: 'put', data })
}

export function deletePurchaseInboundOrder(id) {
  return request({ url: `/app/oper/bus/pms/purchase-inbound-order/${id}`, method: 'delete' })
}

export function postPurchaseInboundOrder(id) {
  return request({ url: `/app/oper/bus/pms/purchase-inbound-order/${id}/post`, method: 'post' })
}

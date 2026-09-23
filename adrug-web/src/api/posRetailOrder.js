import request from './request'

// POS零售单接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pagePosRetailOrder(params) {
  return request({ url: '/app/oper/bus/oms/getPosRetailOrderPage', method: 'post', data: params })
}

export function getPosRetailOrder(id) {
  return request({ url: `/app/oper/bus/oms/pos-retail-order/${id}`, method: 'get' })
}

export function createPosRetailOrder(data) {
  return request({ url: '/app/oper/bus/oms/pos-retail-order', method: 'post', data })
}

export function updatePosRetailOrder(id, data) {
  return request({ url: `/app/oper/bus/oms/pos-retail-order/${id}`, method: 'put', data })
}

export function deletePosRetailOrder(id) {
  return request({ url: `/app/oper/bus/oms/pos-retail-order/${id}`, method: 'delete' })
}

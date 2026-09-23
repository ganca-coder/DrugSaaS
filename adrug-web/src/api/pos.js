import request from './request'

// POS收银台接口统一走应用层（adrug-app-pos）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function searchDrugPage(params) {
  return request({ url: '/app/pos/drug/search', method: 'post', data: params })
}

export function searchMemberPage(params) {
  return request({ url: '/app/pos/member/search', method: 'post', data: params })
}

export function createPosRetailOrder(data) {
  return request({ url: '/app/pos/retail-order', method: 'post', data })
}

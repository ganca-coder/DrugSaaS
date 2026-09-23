import request from './request'

// 供应商接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pageSupplier(params) {
  return request({ url: '/app/oper/bus/info/getSupplierPage', method: 'post', data: params })
}

export function getSupplier(id) {
  return request({ url: `/app/oper/bus/info/supplier/${id}`, method: 'get' })
}

export function createSupplier(data) {
  return request({ url: '/app/oper/bus/info/supplier', method: 'post', data })
}

export function updateSupplier(id, data) {
  return request({ url: `/app/oper/bus/info/supplier/${id}`, method: 'put', data })
}

export function deleteSupplier(id) {
  return request({ url: `/app/oper/bus/info/supplier/${id}`, method: 'delete' })
}

import request from './request'

// 仓库接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pageWarehouse(params) {
  return request({ url: '/app/oper/bus/info/getWarehousePage', method: 'post', data: params })
}

export function getWarehouse(id) {
  return request({ url: `/app/oper/bus/info/warehouse/${id}`, method: 'get' })
}

export function createWarehouse(data) {
  return request({ url: '/app/oper/bus/info/warehouse', method: 'post', data })
}

export function updateWarehouse(id, data) {
  return request({ url: `/app/oper/bus/info/warehouse/${id}`, method: 'put', data })
}

export function deleteWarehouse(id) {
  return request({ url: `/app/oper/bus/info/warehouse/${id}`, method: 'delete' })
}

import request from './request'

// 药品接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pageDrug(params) {
  return request({ url: '/app/oper/bus/info/getDrugPage', method: 'post', data: params })
}

export function getDrug(id) {
  return request({ url: `/app/oper/bus/info/drug/${id}`, method: 'get' })
}

export function createDrug(data) {
  return request({ url: '/app/oper/bus/info/drug', method: 'post', data })
}

export function updateDrug(id, data) {
  return request({ url: `/app/oper/bus/info/drug/${id}`, method: 'put', data })
}

export function deleteDrug(id) {
  return request({ url: `/app/oper/bus/info/drug/${id}`, method: 'delete' })
}

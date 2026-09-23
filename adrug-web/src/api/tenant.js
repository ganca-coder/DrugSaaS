import request from './request'

export function pageTenant(params) {
  return request({ url: '/app/oper/user/info/getTenantPage', method: 'post', data: params })
}

export function getTenant(id) {
  return request({ url: `/app/oper/user/info/tenant/${id}`, method: 'get' })
}

export function createTenant(data) {
  return request({ url: '/app/oper/user/info/tenant', method: 'post', data })
}

export function updateTenant(id, data) {
  return request({ url: `/app/oper/user/info/tenant/${id}`, method: 'put', data })
}

export function deleteTenant(id) {
  return request({ url: `/app/oper/user/info/tenant/${id}`, method: 'delete' })
}

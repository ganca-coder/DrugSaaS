import request from './request'

export function pageOrg(params) {
  return request({ url: '/app/oper/user/info/getOrgPage', method: 'post', data: params })
}

export function getOrg(id) {
  return request({ url: `/app/oper/user/info/org/${id}`, method: 'get' })
}

export function createOrg(data) {
  return request({ url: '/app/oper/user/info/org', method: 'post', data })
}

export function updateOrg(id, data) {
  return request({ url: `/app/oper/user/info/org/${id}`, method: 'put', data })
}

export function deleteOrg(id) {
  return request({ url: `/app/oper/user/info/org/${id}`, method: 'delete' })
}

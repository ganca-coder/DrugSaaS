import request from './request'

// 员工接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pageEmployee(params) {
  return request({ url: '/app/oper/user/info/getEmployeePage', method: 'post', data: params })
}

export function getEmployee(id) {
  return request({ url: `/app/oper/user/info/employee/${id}`, method: 'get' })
}

export function createEmployee(data) {
  return request({ url: '/app/oper/user/info/employee', method: 'post', data })
}

export function updateEmployee(id, data) {
  return request({ url: `/app/oper/user/info/employee/${id}`, method: 'put', data })
}

export function deleteEmployee(id) {
  return request({ url: `/app/oper/user/info/employee/${id}`, method: 'delete' })
}

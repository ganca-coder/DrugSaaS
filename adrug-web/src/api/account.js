import request from './request'

export function pageAccount(params) {
  return request({ url: '/app/oper/user/info/getAccountPage', method: 'post', data: params })
}

export function getAccount(id) {
  return request({ url: `/app/oper/user/info/account/${id}`, method: 'get' })
}

export function getAccountByEmployeeId(employeeId) {
  return request({ url: `/app/oper/user/info/account/employee/${employeeId}`, method: 'get' })
}

export function createAccount(data) {
  return request({ url: '/app/oper/user/info/account', method: 'post', data })
}

export function updateAccount(id, data) {
  return request({ url: `/app/oper/user/info/account/${id}`, method: 'put', data })
}

export function deleteAccount(id) {
  return request({ url: `/app/oper/user/info/account/${id}`, method: 'delete' })
}

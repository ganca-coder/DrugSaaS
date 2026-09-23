import request from './request'

// 角色 CRUD
export function pageRole(params) {
  return request({ url: '/app/oper/user/permission/getRolePage', method: 'post', data: params })
}

export function getRole(id) {
  return request({ url: `/app/oper/user/permission/role/${id}`, method: 'get' })
}

export function createRole(data) {
  return request({ url: '/app/oper/user/permission/role', method: 'post', data })
}

export function updateRole(id, data) {
  return request({ url: `/app/oper/user/permission/role/${id}`, method: 'put', data })
}

export function deleteRole(id) {
  return request({ url: `/app/oper/user/permission/role/${id}`, method: 'delete' })
}

// 角色-权限
export function saveRolePermissions(roleId, permissions) {
  return request({ url: `/app/oper/user/permission/role/${roleId}/permissions`, method: 'put', data: permissions })
}

export function getRolePermissions(roleId) {
  return request({ url: `/app/oper/user/permission/role/${roleId}/permissions`, method: 'get' })
}

// 账号-角色
export function saveAccountRoles(accountId, roleIds) {
  return request({ url: `/app/oper/user/permission/account/${accountId}/roles`, method: 'put', data: roleIds })
}

export function getAccountRoleIds(accountId) {
  return request({ url: `/app/oper/user/permission/account/${accountId}/roles`, method: 'get' })
}

// 角色-数据机构
export function saveRoleDataOrgs(roleId, orgIds) {
  return request({ url: `/app/oper/user/permission/role/${roleId}/dataOrgs`, method: 'put', data: orgIds })
}

export function getRoleDataOrgs(roleId) {
  return request({ url: `/app/oper/user/permission/role/${roleId}/dataOrgs`, method: 'get' })
}

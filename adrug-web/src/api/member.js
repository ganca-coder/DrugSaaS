import request from './request'

// 会员接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pageMember(params) {
  return request({ url: '/app/oper/user/member/getMemberPage', method: 'post', data: params })
}

export function getMember(id) {
  return request({ url: `/app/oper/user/member/${id}`, method: 'get' })
}

export function createMember(data) {
  return request({ url: '/app/oper/user/member', method: 'post', data })
}

export function updateMember(id, data) {
  return request({ url: `/app/oper/user/member/${id}`, method: 'put', data })
}

export function deleteMember(id) {
  return request({ url: `/app/oper/user/member/${id}`, method: 'delete' })
}

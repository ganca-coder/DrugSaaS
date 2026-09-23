import request from './request'

/**
 * 登录（经应用层 adrug-app-oper 编排到授权中心）。
 */
export function login(data) {
  return request({ url: '/app/oper/user/auth/login', method: 'post', data })
}

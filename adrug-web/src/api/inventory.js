import request from './request'

// 库存接口统一走应用层（adrug-app-oper）；tenantId 统一由请求头 X-Tenant-Id 传递。
export function pageInventory(params) {
  return request({ url: '/app/oper/bus/wms/getInventoryPage', method: 'post', data: params })
}

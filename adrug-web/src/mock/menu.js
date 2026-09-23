// 后端菜单接口不可用时的本地默认菜单（与 sql/menu.sql 默认种子一致）。
// 字段与后端 MenuVO 对齐：menuType 1-目录 2-菜单 3-按钮；visible 0-隐藏 1-显示；status 0-停用 1-启用。
export const fallbackMenu = [
  { id: 1, menuName: '首页', menuCode: 'home', menuType: 2, path: '/dashboard', component: 'dashboard/index', icon: 'HomeFilled', sortNo: 1, visible: 1, status: 1, children: [] },
  {
    id: 13, menuName: '组织管理', menuCode: 'user', menuType: 1, path: '/user', icon: 'User', sortNo: 5, visible: 1, status: 1, children: [
      { id: 14, menuName: '员工管理', menuCode: 'user-employee', menuType: 2, path: '/user/employee', component: 'user/employee/index', sortNo: 1, visible: 1, status: 1, children: [
        { id: 24, menuName: '员工新增', menuCode: 'user-employee-create', menuType: 3, permission: 'user:employee:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 25, menuName: '员工编辑', menuCode: 'user-employee-update', menuType: 3, permission: 'user:employee:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 26, menuName: '员工删除', menuCode: 'user-employee-delete', menuType: 3, permission: 'user:employee:delete', sortNo: 3, visible: 1, status: 1, children: [] },
        { id: 27, menuName: '分配角色', menuCode: 'user-employee-assign', menuType: 3, permission: 'user:employee:assign', sortNo: 4, visible: 1, status: 1, children: [] }
      ] },
      { id: 15, menuName: '机构管理', menuCode: 'user-org', menuType: 2, path: '/user/org', component: 'user/org/index', sortNo: 2, visible: 1, status: 1, children: [] }
    ]
  },
  {
    id: 61, menuName: '会员管理', menuCode: 'member', menuType: 1, path: '/member', icon: 'Avatar', sortNo: 6, visible: 1, status: 1, children: [
      { id: 62, menuName: '会员档案', menuCode: 'member-list', menuType: 2, path: '/member/list', component: 'member/index', sortNo: 1, visible: 1, status: 1, children: [
        { id: 63, menuName: '会员新增', menuCode: 'member-create', menuType: 3, permission: 'member:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 64, menuName: '会员编辑', menuCode: 'member-update', menuType: 3, permission: 'member:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 65, menuName: '会员删除', menuCode: 'member-delete', menuType: 3, permission: 'member:delete', sortNo: 3, visible: 1, status: 1, children: [] }
      ] }
    ]
  },
  {
    id: 2, menuName: '基础信息', menuCode: 'bus', menuType: 1, path: '/bus', icon: 'Goods', sortNo: 10, visible: 1, status: 1, children: [
      { id: 3, menuName: '药品管理', menuCode: 'bus-drug', menuType: 2, path: '/bus/drug', component: 'bus/drug/index', sortNo: 1, visible: 1, status: 1, children: [
        { id: 32, menuName: '药品新增', menuCode: 'bus-drug-create', menuType: 3, permission: 'bus:drug:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 33, menuName: '药品编辑', menuCode: 'bus-drug-update', menuType: 3, permission: 'bus:drug:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 34, menuName: '药品删除', menuCode: 'bus-drug-delete', menuType: 3, permission: 'bus:drug:delete', sortNo: 3, visible: 1, status: 1, children: [] }
      ] },
      { id: 4, menuName: '供应商管理', menuCode: 'bus-supplier', menuType: 2, path: '/bus/supplier', component: 'bus/supplier/index', sortNo: 2, visible: 1, status: 1, children: [
        { id: 35, menuName: '供应商新增', menuCode: 'bus-supplier-create', menuType: 3, permission: 'bus:supplier:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 36, menuName: '供应商编辑', menuCode: 'bus-supplier-update', menuType: 3, permission: 'bus:supplier:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 37, menuName: '供应商删除', menuCode: 'bus-supplier-delete', menuType: 3, permission: 'bus:supplier:delete', sortNo: 3, visible: 1, status: 1, children: [] }
      ] },
      { id: 5, menuName: '厂家管理', menuCode: 'bus-manufacturer', menuType: 2, path: '/bus/manufacturer', component: 'bus/manufacturer/index', sortNo: 3, visible: 1, status: 1, children: [] },
      { id: 6, menuName: '仓库管理', menuCode: 'bus-warehouse', menuType: 2, path: '/bus/warehouse', component: 'bus/warehouse/index', sortNo: 4, visible: 1, status: 1, children: [] }
    ]
  },
  {
    id: 7, menuName: '采购管理', menuCode: 'pms', menuType: 1, path: '/pms', icon: 'ShoppingCart', sortNo: 20, visible: 1, status: 1, children: [
      { id: 8, menuName: '采购订单', menuCode: 'pms-purchase-order', menuType: 2, path: '/pms/purchase-order', component: 'pms/purchase-order/index', sortNo: 1, visible: 1, status: 1, children: [
        { id: 38, menuName: '采购订单新增', menuCode: 'pms-purchase-order-create', menuType: 3, permission: 'pms:purchase-order:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 39, menuName: '采购订单编辑', menuCode: 'pms-purchase-order-update', menuType: 3, permission: 'pms:purchase-order:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 40, menuName: '采购订单删除', menuCode: 'pms-purchase-order-delete', menuType: 3, permission: 'pms:purchase-order:delete', sortNo: 3, visible: 1, status: 1, children: [] },
        { id: 41, menuName: '采购订单过账', menuCode: 'pms-purchase-order-post', menuType: 3, permission: 'pms:purchase-order:post', sortNo: 4, visible: 1, status: 1, children: [] }
      ] },
      { id: 28, menuName: '收货单', menuCode: 'pms-receiving-order', menuType: 2, path: '/pms/receiving-order', component: 'pms/receiving-order/index', sortNo: 2, visible: 1, status: 1, children: [
        { id: 42, menuName: '收货单新增', menuCode: 'pms-receiving-order-create', menuType: 3, permission: 'pms:receiving-order:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 43, menuName: '收货单编辑', menuCode: 'pms-receiving-order-update', menuType: 3, permission: 'pms:receiving-order:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 44, menuName: '收货单删除', menuCode: 'pms-receiving-order-delete', menuType: 3, permission: 'pms:receiving-order:delete', sortNo: 3, visible: 1, status: 1, children: [] },
        { id: 45, menuName: '收货单过账', menuCode: 'pms-receiving-order-post', menuType: 3, permission: 'pms:receiving-order:post', sortNo: 4, visible: 1, status: 1, children: [] }
      ] },
      { id: 29, menuName: '验收单', menuCode: 'pms-acceptance-order', menuType: 2, path: '/pms/acceptance-order', component: 'pms/acceptance-order/index', sortNo: 3, visible: 1, status: 1, children: [
        { id: 46, menuName: '验收单新增', menuCode: 'pms-acceptance-order-create', menuType: 3, permission: 'pms:acceptance-order:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 47, menuName: '验收单编辑', menuCode: 'pms-acceptance-order-update', menuType: 3, permission: 'pms:acceptance-order:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 48, menuName: '验收单删除', menuCode: 'pms-acceptance-order-delete', menuType: 3, permission: 'pms:acceptance-order:delete', sortNo: 3, visible: 1, status: 1, children: [] },
        { id: 49, menuName: '验收单验收', menuCode: 'pms-acceptance-order-accept', menuType: 3, permission: 'pms:acceptance-order:accept', sortNo: 4, visible: 1, status: 1, children: [] },
        { id: 50, menuName: '验收单过账', menuCode: 'pms-acceptance-order-post', menuType: 3, permission: 'pms:acceptance-order:post', sortNo: 5, visible: 1, status: 1, children: [] }
      ] },
      { id: 30, menuName: '采购入库单', menuCode: 'pms-purchase-inbound-order', menuType: 2, path: '/pms/purchase-inbound-order', component: 'pms/purchase-inbound-order/index', sortNo: 4, visible: 1, status: 1, children: [
        { id: 51, menuName: '采购入库单新增', menuCode: 'pms-purchase-inbound-order-create', menuType: 3, permission: 'pms:purchase-inbound-order:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 52, menuName: '采购入库单编辑', menuCode: 'pms-purchase-inbound-order-update', menuType: 3, permission: 'pms:purchase-inbound-order:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 53, menuName: '采购入库单删除', menuCode: 'pms-purchase-inbound-order-delete', menuType: 3, permission: 'pms:purchase-inbound-order:delete', sortNo: 3, visible: 1, status: 1, children: [] },
        { id: 54, menuName: '采购入库单过账', menuCode: 'pms-purchase-inbound-order-post', menuType: 3, permission: 'pms:purchase-inbound-order:post', sortNo: 4, visible: 1, status: 1, children: [] }
      ] },
      { id: 31, menuName: '拒收单', menuCode: 'pms-rejection-order', menuType: 2, path: '/pms/rejection-order', component: 'pms/rejection-order/index', sortNo: 5, visible: 1, status: 1, children: [
        { id: 55, menuName: '拒收单新增', menuCode: 'pms-rejection-order-create', menuType: 3, permission: 'pms:rejection-order:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 56, menuName: '拒收单编辑', menuCode: 'pms-rejection-order-update', menuType: 3, permission: 'pms:rejection-order:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 57, menuName: '拒收单删除', menuCode: 'pms-rejection-order-delete', menuType: 3, permission: 'pms:rejection-order:delete', sortNo: 3, visible: 1, status: 1, children: [] },
        { id: 58, menuName: '拒收单过账', menuCode: 'pms-rejection-order-post', menuType: 3, permission: 'pms:rejection-order:post', sortNo: 4, visible: 1, status: 1, children: [] }
      ] }
    ]
  },
  {
    id: 9, menuName: '订单管理', menuCode: 'oms', menuType: 1, path: '/oms', icon: 'List', sortNo: 30, visible: 1, status: 1, children: [
      { id: 10, menuName: '药店订单', menuCode: 'oms-shop-order', menuType: 2, path: '/oms/shop-order', component: 'oms/shop-order/index', sortNo: 1, visible: 1, status: 1, children: [] },
      { id: 66, menuName: 'POS零售单', menuCode: 'oms-pos-retail-order', menuType: 2, path: '/oms/pos-retail-order', component: 'oms/pos-retail-order/index', sortNo: 2, visible: 1, status: 1, children: [
        { id: 67, menuName: 'POS零售单新增', menuCode: 'oms-pos-retail-order-create', menuType: 3, permission: 'oms:pos-retail-order:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 68, menuName: 'POS零售单编辑', menuCode: 'oms-pos-retail-order-update', menuType: 3, permission: 'oms:pos-retail-order:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 69, menuName: 'POS零售单删除', menuCode: 'oms-pos-retail-order-delete', menuType: 3, permission: 'oms:pos-retail-order:delete', sortNo: 3, visible: 1, status: 1, children: [] }
      ] }
    ]
  },
  {
    id: 59, menuName: '库存管理', menuCode: 'inventory', menuType: 1, path: '/inventory', icon: 'Box', sortNo: 40, visible: 1, status: 1, children: [
      { id: 60, menuName: '库存查询', menuCode: 'inventory-query', menuType: 2, path: '/inventory/query', component: 'inventory/query/index', sortNo: 1, visible: 1, status: 1, children: [] }
    ]
  },
  {
    id: 11, menuName: '系统管理', menuCode: 'system', menuType: 1, path: '/system', icon: 'Setting', sortNo: 90, visible: 1, status: 1, children: [
      { id: 12, menuName: '菜单管理', menuCode: 'system-menu', menuType: 2, path: '/system/menu', component: 'system/menu/index', sortNo: 1, visible: 1, status: 1, children: [] },
      { id: 17, menuName: '租户管理', menuCode: 'system-tenant', menuType: 2, path: '/system/tenant', component: 'system/tenant/index', sortNo: 2, visible: 1, status: 1, children: [] },
      { id: 18, menuName: '角色管理', menuCode: 'system-role', menuType: 2, path: '/system/role', component: 'system/role/index', sortNo: 3, visible: 1, status: 1, children: [
        { id: 19, menuName: '角色新增', menuCode: 'system-role-create', menuType: 3, permission: 'system:role:create', sortNo: 1, visible: 1, status: 1, children: [] },
        { id: 20, menuName: '角色编辑', menuCode: 'system-role-update', menuType: 3, permission: 'system:role:update', sortNo: 2, visible: 1, status: 1, children: [] },
        { id: 21, menuName: '角色删除', menuCode: 'system-role-delete', menuType: 3, permission: 'system:role:delete', sortNo: 3, visible: 1, status: 1, children: [] },
        { id: 22, menuName: '角色授权', menuCode: 'system-role-permission', menuType: 3, permission: 'system:role:permission', sortNo: 4, visible: 1, status: 1, children: [] },
        { id: 23, menuName: '分配角色', menuCode: 'system-role-assign', menuType: 3, permission: 'system:role:assign', sortNo: 5, visible: 1, status: 1, children: [] }
      ] }
    ]
  }
]

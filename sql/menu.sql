-- 菜单表
-- 对应实体：Menu（继承 com.adrug.erp.common.entity.BaseEntity）
-- 所属库：adrug_common（单库，不分租户库）
-- 租户隔离：tenant_id = 0 为默认菜单（所有租户共享）；tenant_id > 0 为租户自定义菜单，
--          按 menu_code 覆盖默认菜单同名项，租户新增项合并进来。
USE `adrug_common`;

CREATE TABLE `menu` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（0-默认菜单，>0-租户自定义菜单）',
    `org_id`         BIGINT       NOT NULL DEFAULT 0      COMMENT '机构ID（菜单为租户级，固定0）',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `menu_name`      VARCHAR(64)  NOT NULL                COMMENT '菜单名称',
    `menu_code`      VARCHAR(64)  NOT NULL                COMMENT '菜单编码（租户覆盖默认菜单的匹配键）',
    `parent_id`      BIGINT       NOT NULL DEFAULT 0      COMMENT '父菜单ID（0-顶级）',
    `menu_type`      TINYINT      NOT NULL DEFAULT 2      COMMENT '菜单类型：1-目录，2-菜单，3-按钮（MenuTypeEnum）',
    `path`           VARCHAR(128) NULL                    COMMENT '路由地址',
    `component`      VARCHAR(128) NULL                    COMMENT '前端组件路径（相对 src/views，不含 .vue 后缀）',
    `icon`           VARCHAR(64)  NULL                    COMMENT '图标（Element Plus 图标名）',
    `sort_no`        INT          NOT NULL DEFAULT 0      COMMENT '排序号',
    `visible`        TINYINT      NOT NULL DEFAULT 1      COMMENT '是否显示：0-隐藏，1-显示（MenuVisibleEnum）',
    `status`         TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：0-停用，1-启用（MenuStatusEnum）',
    `permission`     VARCHAR(128) NULL                    COMMENT '权限标识',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_menu_code` (`tenant_id`, `menu_code`),
    KEY `idx_tenant_parent` (`tenant_id`, `parent_id`)
) ENGINE = InnoDB  COMMENT = '菜单表（默认菜单 + 租户自定义菜单）';

-- ===== 默认菜单种子数据（tenant_id = 0，所有租户共享） =====
INSERT INTO `menu`
    (`id`, `tenant_id`, `menu_name`, `menu_code`, `parent_id`, `menu_type`, `path`, `component`, `icon`, `sort_no`, `visible`, `status`, `permission`, `remark`)
VALUES
    (1,  0, '首页',     'home',              0,  2, '/dashboard',          'dashboard/index',             'HomeFilled',  1,  1, 1, NULL, '首页'),
    (2,  0, '基础信息', 'bus',               0,  1, '/bus',                NULL,                          'Goods',       10, 1, 1, NULL, '基础信息'),
    (3,  0, '药品管理', 'bus-drug',          2,  2, '/bus/drug',           'bus/drug/index',              NULL,          1,  1, 1, 'bus:drug:list',       '药品信息'),
    (4,  0, '供应商管理', 'bus-supplier',    2,  2, '/bus/supplier',       'bus/supplier/index',          NULL,          2,  1, 1, 'bus:supplier:list',   '供应商信息'),
    (5,  0, '厂家管理', 'bus-manufacturer',  2,  2, '/bus/manufacturer',   'bus/manufacturer/index',      NULL,          3,  1, 1, 'bus:manufacturer:list','厂家信息'),
    (6,  0, '仓库管理', 'bus-warehouse',     2,  2, '/bus/warehouse',      'bus/warehouse/index',         NULL,          4,  1, 1, 'bus:warehouse:list',  '仓库信息'),
    (7,  0, '采购管理', 'pms',               0,  1, '/pms',                NULL,                          'ShoppingCart', 20, 1, 1, NULL, '采购管理'),
    (8,  0, '采购订单', 'pms-purchase-order', 7,  2, '/pms/purchase-order', 'pms/purchase-order/index',   NULL,          1,  1, 1, 'pms:purchase-order:list', '采购订单'),
    (9,  0, '订单管理', 'oms',               0,  1, '/oms',                NULL,                          'List',        30, 1, 1, NULL, '订单管理'),
    (10, 0, '零售销售单', 'oms-shop-order',    9,  2, '/oms/shop-order',     'oms/shop-order/index',        NULL,          1,  1, 1, 'oms:shop-order:list',  '药店订单'),
    (11, 0, '系统管理', 'system',            0,  1, '/system',             NULL,                          'Setting',     90, 1, 1, NULL, '系统管理'),
    (12, 0, '菜单管理', 'system-menu',       11, 2, '/system/menu',        'system/menu/index',           NULL,          1,  1, 1, 'system:menu:list',     '菜单管理'),
    (13, 0, '组织管理', 'user',              0,  1, '/user',               NULL,                          'User',        5,  1, 1, NULL,                  '组织管理'),
    (14, 0, '员工管理', 'user-employee',     13, 2, '/user/employee',      'user/employee/index',         NULL,          1,  1, 1, 'user:employee:list',  '员工信息'),
    (15, 0, '机构管理', 'user-org',         13, 2, '/user/org',           'user/org/index',              NULL,          2,  1, 1, 'user:org:list',       '机构信息'),
    (17, 0, '租户管理', 'system-tenant',    11, 2, '/system/tenant',      'system/tenant/index',         NULL,          2,  1, 1, 'system:tenant:list',  '租户信息'),
    (18, 0, '角色管理', 'system-role',      11, 2, '/system/role',        'system/role/index',           NULL,          3,  1, 1, 'system:role:list',    '角色信息'),
    (19, 0, '角色新增', 'system-role-create',     18, 3, NULL,             NULL,                          NULL,          1,  1, 1, 'system:role:create',     '角色新增按钮'),
    (20, 0, '角色编辑', 'system-role-update',     18, 3, NULL,             NULL,                          NULL,          2,  1, 1, 'system:role:update',     '角色编辑按钮'),
    (21, 0, '角色删除', 'system-role-delete',     18, 3, NULL,             NULL,                          NULL,          3,  1, 1, 'system:role:delete',     '角色删除按钮'),
    (22, 0, '角色授权', 'system-role-permission', 18, 3, NULL,             NULL,                          NULL,          4,  1, 1, 'system:role:permission', '角色授权按钮'),
    (23, 0, '分配角色', 'system-role-assign',     18, 3, NULL,             NULL,                          NULL,          5,  1, 1, 'system:role:assign',     '分配角色按钮'),
    (24, 0, '员工新增', 'user-employee-create',   14, 3, NULL,             NULL,                          NULL,          1,  1, 1, 'user:employee:create',   '员工新增按钮'),
    (25, 0, '员工编辑', 'user-employee-update',   14, 3, NULL,             NULL,                          NULL,          2,  1, 1, 'user:employee:update',   '员工编辑按钮'),
    (26, 0, '员工删除', 'user-employee-delete',   14, 3, NULL,             NULL,                          NULL,          3,  1, 1, 'user:employee:delete',   '员工删除按钮'),
    (27, 0, '分配角色', 'user-employee-assign',   14, 3, NULL,             NULL,                          NULL,          4,  1, 1, 'user:employee:assign',   '分配角色按钮'),
    (28, 0, '收货单',     'pms-receiving-order',        7,  2, '/pms/receiving-order',       'pms/receiving-order/index',       NULL,          2,  1, 1, 'pms:receiving-order:list',       '收货单'),
    (29, 0, '验收单',     'pms-acceptance-order',       7,  2, '/pms/acceptance-order',      'pms/acceptance-order/index',      NULL,          3,  1, 1, 'pms:acceptance-order:list',      '验收单'),
    (30, 0, '采购入库单', 'pms-purchase-inbound-order', 7,  2, '/pms/purchase-inbound-order', 'pms/purchase-inbound-order/index', NULL,          4,  1, 1, 'pms:purchase-inbound-order:list', '采购入库单'),
    (31, 0, '拒收单',     'pms-rejection-order',        7,  2, '/pms/rejection-order',       'pms/rejection-order/index',       NULL,          5,  1, 1, 'pms:rejection-order:list',       '拒收单'),
    -- 药品按钮
    (32, 0, '药品新增', 'bus-drug-create', 3, 3, NULL, NULL, NULL, 1, 1, 1, 'bus:drug:create', '药品新增按钮'),
    (33, 0, '药品编辑', 'bus-drug-update', 3, 3, NULL, NULL, NULL, 2, 1, 1, 'bus:drug:update', '药品编辑按钮'),
    (34, 0, '药品删除', 'bus-drug-delete', 3, 3, NULL, NULL, NULL, 3, 1, 1, 'bus:drug:delete', '药品删除按钮'),
    -- 供应商按钮
    (35, 0, '供应商新增', 'bus-supplier-create', 4, 3, NULL, NULL, NULL, 1, 1, 1, 'bus:supplier:create', '供应商新增按钮'),
    (36, 0, '供应商编辑', 'bus-supplier-update', 4, 3, NULL, NULL, NULL, 2, 1, 1, 'bus:supplier:update', '供应商编辑按钮'),
    (37, 0, '供应商删除', 'bus-supplier-delete', 4, 3, NULL, NULL, NULL, 3, 1, 1, 'bus:supplier:delete', '供应商删除按钮'),
    -- 采购订单按钮
    (38, 0, '采购订单新增', 'pms-purchase-order-create', 8, 3, NULL, NULL, NULL, 1, 1, 1, 'pms:purchase-order:create', '采购订单新增按钮'),
    (39, 0, '采购订单编辑', 'pms-purchase-order-update', 8, 3, NULL, NULL, NULL, 2, 1, 1, 'pms:purchase-order:update', '采购订单编辑按钮'),
    (40, 0, '采购订单删除', 'pms-purchase-order-delete', 8, 3, NULL, NULL, NULL, 3, 1, 1, 'pms:purchase-order:delete', '采购订单删除按钮'),
    (41, 0, '采购订单过账', 'pms-purchase-order-post',   8, 3, NULL, NULL, NULL, 4, 1, 1, 'pms:purchase-order:post',   '采购订单过账按钮'),
    -- 收货单按钮
    (42, 0, '收货单新增', 'pms-receiving-order-create', 28, 3, NULL, NULL, NULL, 1, 1, 1, 'pms:receiving-order:create', '收货单新增按钮'),
    (43, 0, '收货单编辑', 'pms-receiving-order-update', 28, 3, NULL, NULL, NULL, 2, 1, 1, 'pms:receiving-order:update', '收货单编辑按钮'),
    (44, 0, '收货单删除', 'pms-receiving-order-delete', 28, 3, NULL, NULL, NULL, 3, 1, 1, 'pms:receiving-order:delete', '收货单删除按钮'),
    (45, 0, '收货单过账', 'pms-receiving-order-post',   28, 3, NULL, NULL, NULL, 4, 1, 1, 'pms:receiving-order:post',   '收货单过账按钮'),
    -- 验收单按钮
    (46, 0, '验收单新增', 'pms-acceptance-order-create', 29, 3, NULL, NULL, NULL, 1, 1, 1, 'pms:acceptance-order:create', '验收单新增按钮'),
    (47, 0, '验收单编辑', 'pms-acceptance-order-update', 29, 3, NULL, NULL, NULL, 2, 1, 1, 'pms:acceptance-order:update', '验收单编辑按钮'),
    (48, 0, '验收单删除', 'pms-acceptance-order-delete', 29, 3, NULL, NULL, NULL, 3, 1, 1, 'pms:acceptance-order:delete', '验收单删除按钮'),
    (49, 0, '验收单验收', 'pms-acceptance-order-accept', 29, 3, NULL, NULL, NULL, 4, 1, 1, 'pms:acceptance-order:accept', '验收单验收按钮'),
    (50, 0, '验收单过账', 'pms-acceptance-order-post',   29, 3, NULL, NULL, NULL, 5, 1, 1, 'pms:acceptance-order:post',   '验收单过账按钮'),
    -- 采购入库单按钮
    (51, 0, '采购入库单新增', 'pms-purchase-inbound-order-create', 30, 3, NULL, NULL, NULL, 1, 1, 1, 'pms:purchase-inbound-order:create', '采购入库单新增按钮'),
    (52, 0, '采购入库单编辑', 'pms-purchase-inbound-order-update', 30, 3, NULL, NULL, NULL, 2, 1, 1, 'pms:purchase-inbound-order:update', '采购入库单编辑按钮'),
    (53, 0, '采购入库单删除', 'pms-purchase-inbound-order-delete', 30, 3, NULL, NULL, NULL, 3, 1, 1, 'pms:purchase-inbound-order:delete', '采购入库单删除按钮'),
    (54, 0, '采购入库单过账', 'pms-purchase-inbound-order-post',   30, 3, NULL, NULL, NULL, 4, 1, 1, 'pms:purchase-inbound-order:post',   '采购入库单过账按钮'),
    -- 拒收单按钮
    (55, 0, '拒收单新增', 'pms-rejection-order-create', 31, 3, NULL, NULL, NULL, 1, 1, 1, 'pms:rejection-order:create', '拒收单新增按钮'),
    (56, 0, '拒收单编辑', 'pms-rejection-order-update', 31, 3, NULL, NULL, NULL, 2, 1, 1, 'pms:rejection-order:update', '拒收单编辑按钮'),
    (57, 0, '拒收单删除', 'pms-rejection-order-delete', 31, 3, NULL, NULL, NULL, 3, 1, 1, 'pms:rejection-order:delete', '拒收单删除按钮'),
    (58, 0, '拒收单过账', 'pms-rejection-order-post',   31, 3, NULL, NULL, NULL, 4, 1, 1, 'pms:rejection-order:post',   '拒收单过账按钮'),
    (59, 0, '库存管理', 'inventory',        0,  1, '/inventory',         NULL,                          'Box',         40, 1, 1, NULL, '库存管理'),
    (60, 0, '库存查询', 'inventory-query',  59, 2, '/inventory/query',   'inventory/query/index',        NULL,          1,  1, 1, 'wms:inventory:list', '库存查询'),
    -- 会员管理
    (61, 0, '会员管理', 'member',                  0,  1, '/member',              NULL,                          'Avatar',      6,  1, 1, NULL,                        '会员管理'),
    (62, 0, '会员档案', 'member-list',             61, 2, '/member/list',         'member/index',                NULL,          1,  1, 1, 'member:list',               '会员档案'),
    (63, 0, '会员新增', 'member-create',           62, 3, NULL,                   NULL,                          NULL,          1,  1, 1, 'member:create',             '会员新增按钮'),
    (64, 0, '会员编辑', 'member-update',           62, 3, NULL,                   NULL,                          NULL,          2,  1, 1, 'member:update',             '会员编辑按钮'),
    (65, 0, '会员删除', 'member-delete',           62, 3, NULL,                   NULL,                          NULL,          3,  1, 1, 'member:delete',             '会员删除按钮'),
    -- POS零售单
    (66, 0, 'POS零售单',       'oms-pos-retail-order',        9,  2, '/oms/pos-retail-order', 'oms/pos-retail-order/index', NULL,          2,  1, 1, 'oms:pos-retail-order:list',   'POS零售单'),
    (67, 0, 'POS零售单新增',   'oms-pos-retail-order-create', 66, 3, NULL,                   NULL,                          NULL,          1,  1, 1, 'oms:pos-retail-order:create', 'POS零售单新增按钮'),
    (68, 0, 'POS零售单编辑',   'oms-pos-retail-order-update', 66, 3, NULL,                   NULL,                          NULL,          2,  1, 1, 'oms:pos-retail-order:update', 'POS零售单编辑按钮'),
    (69, 0, 'POS零售单删除',   'oms-pos-retail-order-delete', 66, 3, NULL,                   NULL,                          NULL,          3,  1, 1, 'oms:pos-retail-order:delete', 'POS零售单删除按钮');

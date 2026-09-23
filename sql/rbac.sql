-- RBAC：角色 / 角色-权限 / 账号-角色
-- 所属库：adrug_common（单库）
USE `adrug_common`;

-- 角色表
CREATE TABLE `role` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（数据隔离）',
    `org_id`         BIGINT       NOT NULL DEFAULT 0      COMMENT '机构ID（角色为租户级，固定0）',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `role_code`      VARCHAR(64)  NOT NULL                COMMENT '角色编码',
    `role_name`      VARCHAR(64)  NOT NULL                COMMENT '角色名称',
    `status`         TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：0-停用，1-启用（RoleStatusEnum）',
    `data_scope`     TINYINT      NOT NULL DEFAULT 4      COMMENT '数据范围：1-全部，2-多机构及下级，3-本机构及下级，4-本机构，5-仅本人（DataScopeEnum）',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_role_code` (`tenant_id`, `role_code`),
    KEY `idx_tenant` (`tenant_id`)
) ENGINE = InnoDB  COMMENT = '角色表';

-- 角色-数据机构关联表（数据范围为「多机构及下级」时使用）
CREATE TABLE `role_data_org` (
    `id`      BIGINT NOT NULL COMMENT '主键（雪花ID）',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `org_id`  BIGINT NOT NULL COMMENT '机构ID',
    PRIMARY KEY (`id`),
    KEY `idx_role` (`role_id`)
) ENGINE = InnoDB  COMMENT = '角色-数据机构关联表';

-- 角色-权限关联表（permission 对应 menu.permission 标识）
CREATE TABLE `role_permission` (
    `id`         BIGINT       NOT NULL COMMENT '主键（雪花ID）',
    `role_id`    BIGINT       NOT NULL COMMENT '角色ID',
    `permission` VARCHAR(128) NOT NULL COMMENT '权限标识',
    PRIMARY KEY (`id`),
    KEY `idx_role` (`role_id`)
) ENGINE = InnoDB  COMMENT = '角色-权限关联表';

-- 账号-角色关联表
CREATE TABLE `account_role` (
    `id`         BIGINT NOT NULL COMMENT '主键（雪花ID）',
    `account_id` BIGINT NOT NULL COMMENT '账号ID',
    `role_id`    BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    KEY `idx_account` (`account_id`),
    KEY `idx_role` (`role_id`)
) ENGINE = InnoDB  COMMENT = '账号-角色关联表';

-- 种子：管理员角色（拥有全部权限），并绑定到 admin 账号（id=1，见 seed.sql）
INSERT INTO `role` (`id`, `tenant_id`, `role_code`, `role_name`, `status`, `data_scope`, `remark`)
VALUES (1, 1, 'admin', '管理员', 1, 1, '超级管理员（拥有全部权限）');

INSERT INTO `role_permission` (`id`, `role_id`, `permission`) VALUES (1, 1, '*');

INSERT INTO `account_role` (`id`, `account_id`, `role_id`) VALUES (1, 1, 1);

-- ===== 默认角色种子（常用角色，供分配） =====
INSERT INTO `role` (`id`, `tenant_id`, `role_code`, `role_name`, `status`, `data_scope`, `remark`) VALUES
    (2, 1, 'ops-manager',   '运营主管', 1, 1, '运营主管（全部数据）'),
    (3, 1, 'store-manager', '店长',     1, 3, '店长（本机构及下级）'),
    (4, 1, 'employee',         '店员',     1, 4, '店员（本机构）'),
    (5, 1, 'purchaser',     '采购员',   1, 4, '采购员（本机构）'),
    (6, 1, 'cashier',       '收银员',   1, 4, '收银员（本机构，登录直达 POS 收银台）');

INSERT INTO `role_permission` (`id`, `role_id`, `permission`) VALUES
    -- 运营主管：全部功能
    (2, 2, 'bus:drug:list'), (3, 2, 'bus:supplier:list'), (4, 2, 'bus:manufacturer:list'),
    (5, 2, 'bus:warehouse:list'), (6, 2, 'pms:purchase-order:list'), (7, 2, 'oms:shop-order:list'),
    (8, 2, 'user:employee:list'), (9, 2, 'user:org:list'), (10, 2, 'user:account:list'),
    (11, 2, 'system:menu:list'), (12, 2, 'system:tenant:list'), (13, 2, 'system:role:list'),
    (14, 2, 'system:role:create'), (15, 2, 'system:role:update'), (16, 2, 'system:role:delete'),
    (17, 2, 'system:role:permission'), (18, 2, 'system:role:assign'),
    -- 店长：业务 + 人员
    (19, 3, 'bus:drug:list'), (20, 3, 'bus:supplier:list'), (21, 3, 'bus:manufacturer:list'),
    (22, 3, 'bus:warehouse:list'), (23, 3, 'pms:purchase-order:list'), (24, 3, 'oms:shop-order:list'),
    (25, 3, 'user:employee:list'), (26, 3, 'user:account:list'),
    -- 店员：药品 + 订单
    (27, 4, 'bus:drug:list'), (28, 4, 'oms:shop-order:list'),
    -- 采购员：采购相关
    (29, 5, 'bus:drug:list'), (30, 5, 'bus:supplier:list'), (31, 5, 'bus:manufacturer:list'),
    (32, 5, 'pms:purchase-order:list'),
    -- 采购相关新增：收货单 / 验收单 / 采购入库单
    (33, 2, 'pms:receiving-order:list'), (34, 2, 'pms:acceptance-order:list'), (35, 2, 'pms:purchase-inbound-order:list'),
    (36, 3, 'pms:receiving-order:list'), (37, 3, 'pms:acceptance-order:list'), (38, 3, 'pms:purchase-inbound-order:list'),
    (39, 5, 'pms:receiving-order:list'), (40, 5, 'pms:acceptance-order:list'), (41, 5, 'pms:purchase-inbound-order:list'),
    (42, 2, 'pms:rejection-order:list'), (43, 3, 'pms:rejection-order:list'), (44, 5, 'pms:rejection-order:list'),
    -- 药品/供应商按钮：运营主管 + 采购员
    (45, 2, 'bus:drug:create'), (46, 2, 'bus:drug:update'), (47, 2, 'bus:drug:delete'),
    (48, 2, 'bus:supplier:create'), (49, 2, 'bus:supplier:update'), (50, 2, 'bus:supplier:delete'),
    (51, 5, 'bus:drug:create'), (52, 5, 'bus:drug:update'), (53, 5, 'bus:drug:delete'),
    (54, 5, 'bus:supplier:create'), (55, 5, 'bus:supplier:update'), (56, 5, 'bus:supplier:delete'),
    -- 采购单据按钮：运营主管
    (57, 2, 'pms:purchase-order:create'), (58, 2, 'pms:purchase-order:update'), (59, 2, 'pms:purchase-order:delete'), (60, 2, 'pms:purchase-order:post'),
    (61, 2, 'pms:receiving-order:create'), (62, 2, 'pms:receiving-order:update'), (63, 2, 'pms:receiving-order:delete'), (64, 2, 'pms:receiving-order:post'),
    (65, 2, 'pms:acceptance-order:create'), (66, 2, 'pms:acceptance-order:update'), (67, 2, 'pms:acceptance-order:delete'), (68, 2, 'pms:acceptance-order:accept'), (69, 2, 'pms:acceptance-order:post'),
    (70, 2, 'pms:purchase-inbound-order:create'), (71, 2, 'pms:purchase-inbound-order:update'), (72, 2, 'pms:purchase-inbound-order:delete'), (73, 2, 'pms:purchase-inbound-order:post'),
    (74, 2, 'pms:rejection-order:create'), (75, 2, 'pms:rejection-order:update'), (76, 2, 'pms:rejection-order:delete'), (77, 2, 'pms:rejection-order:post'),
    -- 采购单据按钮：采购员
    (78, 5, 'pms:purchase-order:create'), (79, 5, 'pms:purchase-order:update'), (80, 5, 'pms:purchase-order:delete'), (81, 5, 'pms:purchase-order:post'),
    (82, 5, 'pms:receiving-order:create'), (83, 5, 'pms:receiving-order:update'), (84, 5, 'pms:receiving-order:delete'), (85, 5, 'pms:receiving-order:post'),
    (86, 5, 'pms:acceptance-order:create'), (87, 5, 'pms:acceptance-order:update'), (88, 5, 'pms:acceptance-order:delete'), (89, 5, 'pms:acceptance-order:accept'), (90, 5, 'pms:acceptance-order:post'),
    (91, 5, 'pms:purchase-inbound-order:create'), (92, 5, 'pms:purchase-inbound-order:update'), (93, 5, 'pms:purchase-inbound-order:delete'), (94, 5, 'pms:purchase-inbound-order:post'),
    (95, 5, 'pms:rejection-order:create'), (96, 5, 'pms:rejection-order:update'), (97, 5, 'pms:rejection-order:delete'), (98, 5, 'pms:rejection-order:post'),
    (99, 2, 'wms:inventory:list'), (100, 3, 'wms:inventory:list'), (101, 4, 'wms:inventory:list'), (102, 5, 'wms:inventory:list'),
    -- 收银员：POS 收银台
    (103, 6, 'bus:drug:list'), (104, 6, 'member:list'),
    (105, 6, 'oms:pos-retail-order:list'), (106, 6, 'oms:pos-retail-order:create'),
    (107, 6, 'wms:inventory:list');

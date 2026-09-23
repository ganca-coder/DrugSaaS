-- 通用库：账号信息、租户信息、机构信息
-- 当前单库，不分租户库；后续新增 adrug-bus-info 库时再做库级分租户
CREATE DATABASE IF NOT EXISTS `adrug_common`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE `adrug_common`;

-- ===== 租户表 =====
CREATE TABLE `tenant` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_code`    VARCHAR(64)  NOT NULL                COMMENT '租户编码（唯一）',
    `tenant_name`    VARCHAR(128) NOT NULL                COMMENT '租户名称',
    `status`         TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1-启用，0-停用',
    `contact_name`   VARCHAR(64)  NULL                    COMMENT '联系人',
    `contact_phone`  VARCHAR(20)  NULL                    COMMENT '联系电话',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_code` (`tenant_code`)
) ENGINE = InnoDB  COMMENT = '租户表';

-- ===== 机构表（药店/门店，支持树形） =====
CREATE TABLE `org` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（真实租户 ID，数据隔离）',
    `parent_id`      BIGINT       NOT NULL DEFAULT 0      COMMENT '上级机构ID（0-顶级）',
    `org_code`       VARCHAR(64)  NOT NULL                COMMENT '机构编码',
    `org_name`       VARCHAR(128) NOT NULL                COMMENT '机构名称',
    `org_type`       TINYINT      NOT NULL DEFAULT 1      COMMENT '机构类型：1-总部，2-药店，3-门店',
    `status`         TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1-启用，0-停用',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_parent` (`tenant_id`, `parent_id`),
    KEY `idx_org_code` (`org_code`)
) ENGINE = InnoDB COMMENT = '机构表';

-- ===== 账号表 =====
CREATE TABLE `account` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（真实租户 ID，数据隔离）',
    `org_id`         BIGINT       NOT NULL                COMMENT '机构ID（药店/门店）',
    `username`       VARCHAR(64)  NOT NULL                COMMENT '登录账号',
    `password`       VARCHAR(128) NOT NULL                COMMENT '密码（加密存储）',
    `account_type`   TINYINT      NOT NULL DEFAULT 1      COMMENT '账号类型：1-管理员，2-员工',
    `employee_id`    BIGINT       NULL                    COMMENT '关联员工ID',
    `status`         TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1-启用，0-停用',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_username` (`tenant_id`, `username`),
    KEY `idx_tenant_org` (`tenant_id`, `org_id`)
) ENGINE = InnoDB COMMENT = '账号表';

CREATE TABLE `employee` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（真实租户 ID，数据隔离）',
    `org_id`         BIGINT       NOT NULL                COMMENT '机构ID（药店/门店）',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `emp_no`         VARCHAR(32)  NULL                    COMMENT '工号',
    `name`           VARCHAR(64)  NOT NULL                COMMENT '姓名',
    `gender`         TINYINT      NULL                    COMMENT '性别：0-未知，1-男，2-女（GenderEnum）',
    `birth_date`     DATE         NULL                    COMMENT '出生日期',
    `id_card_no`     VARCHAR(18)  NULL                    COMMENT '身份证号',
    `phone`          VARCHAR(20)  NULL                    COMMENT '手机号',
    `email`          VARCHAR(100) NULL                    COMMENT '邮箱',
    `position_id`    BIGINT       NULL                    COMMENT '岗位ID（关联岗位信息）',
    `entry_date`     DATE         NULL                    COMMENT '入职日期',
    `resign_date`    DATE         NULL                    COMMENT '离职日期',
    `status`         TINYINT      NOT NULL DEFAULT 1      COMMENT '在职状态：1-在职，2-离职（EmployeeStatusEnum）',
    `pharmacist`     TINYINT      NOT NULL DEFAULT 0      COMMENT '是否药师：0-否，1-是（PharmacistFlagEnum）',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_org` (`tenant_id`, `org_id`),
    KEY `idx_emp_no` (`emp_no`),
    KEY `idx_position_id` (`position_id`),
    KEY `idx_name` (`name`)
) ENGINE = InnoDB COMMENT = '员工信息表';


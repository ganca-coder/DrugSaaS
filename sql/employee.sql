-- 员工信息表
-- 对应实体：Employee（继承 com.adrug.erp.common.entity.BaseEntity）
-- 所属库：adrug_common（单库，不分租户库；tenant_id 存真实租户 ID）
USE `adrug_common`;

CREATE TABLE `employee` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（真实租户 ID，数据隔离）',
    `org_id`         BIGINT       NOT NULL                COMMENT '机构ID（药店/门店）',
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
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_org` (`tenant_id`, `org_id`),
    KEY `idx_emp_no` (`emp_no`),
    KEY `idx_position_id` (`position_id`),
    KEY `idx_name` (`name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工信息表';

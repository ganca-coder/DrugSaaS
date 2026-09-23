-- 会员信息表
-- 对应实体：Member（继承 com.adrug.erp.common.entity.BaseEntity）
-- 所属库：adrug_common（单库，不分租户库；tenant_id 存真实租户 ID）
USE `adrug_common`;

CREATE TABLE `member` (
    `id`             BIGINT        NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT        NOT NULL                COMMENT '租户ID（真实租户 ID，数据隔离）',
    `org_id`         BIGINT        NOT NULL                COMMENT '机构ID（药店/门店）',
    `member_no`      VARCHAR(64)   NULL                    COMMENT '会员卡号',
    `name`           VARCHAR(64)   NULL                    COMMENT '姓名',
    `phone`          VARCHAR(20)   NULL                    COMMENT '手机号',
    `gender`         TINYINT       NULL                    COMMENT '性别：0-未知，1-男，2-女（GenderEnum）',
    `id_card_no`     VARCHAR(18)   NULL                    COMMENT '身份证号',
    `birth_date`     DATE          NULL                    COMMENT '出生日期',
    `level`          VARCHAR(32)   NULL                    COMMENT '会员等级',
    `discount_rate`  DECIMAL(10,4) NULL                    COMMENT '折扣率（0.95 表示 95折）',
    `points`         DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '积分',
    `balance`        DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '储值余额',
    `status`         TINYINT       NOT NULL DEFAULT 1      COMMENT '状态：1-正常，0-停用（MemberStatusEnum）',
    `register_time`  DATETIME      NULL                    COMMENT '注册时间',
    `remark`         VARCHAR(500)  NULL                    COMMENT '备注',
    `create_time`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT        NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT        NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT       NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT           NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_org` (`tenant_id`, `org_id`),
    KEY `idx_member_no` (`member_no`),
    KEY `idx_phone` (`phone`),
    KEY `idx_name` (`name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员信息表';

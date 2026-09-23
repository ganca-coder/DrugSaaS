-- 业务库：adrug-bus-oms（订单管理：POS零售单）
-- 库级分租户：tenant_id % 2 → adrug_bus_oms_0 / adrug_bus_oms_1
-- 表字段保存真实租户 ID（tenant_id 作分片键）
CREATE DATABASE IF NOT EXISTS `adrug_bus_oms_0`
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS `adrug_bus_oms_1`
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- =====================================================================
-- adrug_bus_oms_0
-- =====================================================================
USE `adrug_bus_oms_0`;

-- POS零售单主表（业务实体，含定制化字段 cust1~cust6）
CREATE TABLE `pos_retail_order` (
    `id`                  BIGINT        NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`           BIGINT        NOT NULL                COMMENT '租户ID（真实值，分片键）',
    `org_id`              BIGINT        NOT NULL                COMMENT '机构ID（药店/门店）',
    `order_no`            VARCHAR(64)   NOT NULL                COMMENT '单据编号',
    `bill_type`           VARCHAR(32)   NULL                    COMMENT '单据类型',
    `bill_date`           DATE          NULL                    COMMENT '单据日期',
    `sale_type`           VARCHAR(32)   NULL                    COMMENT '销售类型',
    `pos_serial_no`       VARCHAR(64)   NULL                    COMMENT 'POS流水号',
    `warehouse_id`        BIGINT        NULL                    COMMENT '仓库ID（关联仓库信息）',
    `warehouse_name`      VARCHAR(128)  NULL                    COMMENT '仓库名称',
    `business_time`       DATETIME      NULL                    COMMENT '营业时间',
    `pos_no`              VARCHAR(64)   NULL                    COMMENT 'POS编号',
    `shift`               VARCHAR(32)   NULL                    COMMENT '班次',
    `points_printed`      TINYINT       NOT NULL DEFAULT 0      COMMENT '会员积分是否已打印：0-否，1-是',
    `cashier`             VARCHAR(64)   NULL                    COMMENT '收银员',
    `salesman`            VARCHAR(64)   NULL                    COMMENT '营业员',
    `total_qty`           DECIMAL(18,4) NOT NULL DEFAULT 0      COMMENT '合计数量',
    `discounted_amount`   DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '折后金额',
    `discount_amount`     DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '优惠金额',
    `whole_discount_rate` DECIMAL(10,4) NULL                    COMMENT '整单折扣率',
    `change_amount`       DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '找零金额',
    `wallet_amount`       DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '零钱包金额',
    `round_amount`        DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '抹零金额',
    `manual_round_amount` DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '手工抹零金额',
    `member_id`           BIGINT        NULL                    COMMENT '会员ID（关联会员信息）',
    `member_name`         VARCHAR(64)   NULL                    COMMENT '会员姓名',
    `member_id_card_no`   VARCHAR(18)   NULL                    COMMENT '会员身份证号',
    `member_phone`        VARCHAR(20)   NULL                    COMMENT '会员手机号',
    `pos_source_order_no` VARCHAR(64)   NULL                    COMMENT 'POS来源单号',
    `summary`             VARCHAR(255)  NULL                    COMMENT '摘要',
    `business_platform`   VARCHAR(32)   NULL                    COMMENT '业务平台',
    `retail_price_type`   VARCHAR(32)   NULL                    COMMENT '零售价类型',
    `remark`              VARCHAR(500)  NULL                    COMMENT '备注',
    `create_time`         DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id`      BIGINT        NULL                    COMMENT '创建人（用户ID）',
    `update_user_id`      BIGINT        NULL                    COMMENT '更新人（用户ID）',
    `deleted`             TINYINT       NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`             INT           NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `cust1`               VARCHAR(255)  NULL                    COMMENT '定制化字段1',
    `cust2`               VARCHAR(255)  NULL                    COMMENT '定制化字段2',
    `cust3`               VARCHAR(255)  NULL                    COMMENT '定制化字段3',
    `cust4`               VARCHAR(255)  NULL                    COMMENT '定制化字段4',
    `cust5`               VARCHAR(255)  NULL                    COMMENT '定制化字段5',
    `cust6`               VARCHAR(255)  NULL                    COMMENT '定制化字段6',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_order_no` (`tenant_id`, `order_no`),
    KEY `idx_tenant_warehouse` (`tenant_id`, `warehouse_id`),
    KEY `idx_tenant_member` (`tenant_id`, `member_id`),
    KEY `idx_tenant_bill_date` (`tenant_id`, `bill_date`)
) ENGINE = InnoDB COMMENT = 'POS零售单主表';

-- POS零售单明细表
CREATE TABLE `pos_retail_order_item` (
    `id`               BIGINT        NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`        BIGINT        NOT NULL                COMMENT '租户ID（真实值，分片键）',
    `org_id`           BIGINT        NOT NULL                COMMENT '机构ID（药店/门店）',
    `order_id`         BIGINT        NOT NULL                COMMENT 'POS零售单ID（关联 pos_retail_order.id）',
    `seq_no`           INT           NULL                    COMMENT '序号',
    `drug_id`          BIGINT        NULL                    COMMENT '商品ID（关联商品信息）',
    `drug_code`        VARCHAR(64)   NULL                    COMMENT '商品编码',
    `generic_name`     VARCHAR(128)  NULL                    COMMENT '通用名称',
    `drug_name`        VARCHAR(128)  NULL                    COMMENT '商品名称',
    `unit`             VARCHAR(16)   NULL                    COMMENT '单位',
    `spec`             VARCHAR(64)   NULL                    COMMENT '规格',
    `origin`           VARCHAR(128)  NULL                    COMMENT '产地',
    `batch_no`         VARCHAR(64)   NULL                    COMMENT '批号',
    `production_date`  DATE          NULL                    COMMENT '生产日期',
    `expiry_date`      DATE          NULL                    COMMENT '有效期',
    `qty`              DECIMAL(18,4) NOT NULL DEFAULT 0      COMMENT '数量',
    `price`            DECIMAL(18,4) NOT NULL DEFAULT 0      COMMENT '单价',
    `amount`           DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '金额',
    `discount_rate`    DECIMAL(10,4) NULL                    COMMENT '折扣率',
    `discount_amount`  DECIMAL(18,2) NOT NULL DEFAULT 0      COMMENT '优惠金额',
    `discounted_price` DECIMAL(18,4) NULL                    COMMENT '折后单价',
    `receivable_amount` DECIMAL(18,2) NOT NULL DEFAULT 0     COMMENT '应收金额',
    `remark`           VARCHAR(500)  NULL                    COMMENT '备注',
    `create_time`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id`   BIGINT        NULL                    COMMENT '创建人（用户ID）',
    `update_user_id`   BIGINT        NULL                    COMMENT '更新人（用户ID）',
    `deleted`          TINYINT       NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`          INT           NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `cust1`            VARCHAR(255)  NULL                    COMMENT '定制化字段1',
    `cust2`            VARCHAR(255)  NULL                    COMMENT '定制化字段2',
    `cust3`            VARCHAR(255)  NULL                    COMMENT '定制化字段3',
    `cust4`            VARCHAR(255)  NULL                    COMMENT '定制化字段4',
    `cust5`            VARCHAR(255)  NULL                    COMMENT '定制化字段5',
    `cust6`            VARCHAR(255)  NULL                    COMMENT '定制化字段6',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_tenant_drug` (`tenant_id`, `drug_id`)
) ENGINE = InnoDB COMMENT = 'POS零售单明细表';

-- =====================================================================
-- adrug_bus_oms_1（结构与 _0 完全一致）
-- =====================================================================
USE `adrug_bus_oms_1`;

CREATE TABLE `pos_retail_order` LIKE `adrug_bus_oms_0`.`pos_retail_order`;
CREATE TABLE `pos_retail_order_item` LIKE `adrug_bus_oms_0`.`pos_retail_order_item`;

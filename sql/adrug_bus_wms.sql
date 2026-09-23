-- 业务库：adrug-bus-wms（仓库管理：库存、库位、盘点、移库、出库等）
-- 库级分租户：tenant_id % 2 → adrug_bus_wms_0 / adrug_bus_wms_1
-- 表字段保存真实租户 ID（tenant_id 作分片键）
CREATE DATABASE IF NOT EXISTS `adrug_bus_wms_0`
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS `adrug_bus_wms_1`
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- =====================================================================
-- adrug_bus_wms_0
-- =====================================================================
USE `adrug_bus_wms_0`;

-- 库存表（业务实体，含定制化字段 cust1~cust6）
CREATE TABLE `inventory` (
    `id`              BIGINT        NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`       BIGINT        NOT NULL                COMMENT '租户ID（真实值，分片键）',
    `org_id`          BIGINT        NOT NULL                COMMENT '机构ID（药店/门店）',
    `drug_id`         BIGINT        NOT NULL                COMMENT '商品ID（关联商品信息）',
    `drug_code`       VARCHAR(64)   NULL                    COMMENT '药品编码',
    `drug_name`       VARCHAR(128)  NULL                    COMMENT '药品名称',
    `spec`            VARCHAR(64)   NULL                    COMMENT '规格',
    `unit`            VARCHAR(16)   NULL                    COMMENT '单位',
    `warehouse_id`    BIGINT        NOT NULL                COMMENT '仓库ID（关联仓库信息）',
    `warehouse_name`  VARCHAR(128)  NULL                    COMMENT '仓库名称',
    `batch_no`        VARCHAR(64)   NOT NULL DEFAULT ''     COMMENT '批号',
    `production_date` DATE          NULL                    COMMENT '生产日期',
    `expiry_date`     DATE          NULL                    COMMENT '有效期',
    `quantity`        DECIMAL(18,4) NOT NULL DEFAULT 0      COMMENT '库存数量',
    `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id`  BIGINT        NULL                    COMMENT '创建人（用户ID）',
    `update_user_id`  BIGINT        NULL                    COMMENT '更新人（用户ID）',
    `deleted`         TINYINT       NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`         INT           NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `cust1`           VARCHAR(255)  NULL                    COMMENT '定制化字段1',
    `cust2`           VARCHAR(255)  NULL                    COMMENT '定制化字段2',
    `cust3`           VARCHAR(255)  NULL                    COMMENT '定制化字段3',
    `cust4`           VARCHAR(255)  NULL                    COMMENT '定制化字段4',
    `cust5`           VARCHAR(255)  NULL                    COMMENT '定制化字段5',
    `cust6`           VARCHAR(255)  NULL                    COMMENT '定制化字段6',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_drug_warehouse_batch` (`tenant_id`, `drug_id`, `warehouse_id`, `batch_no`),
    KEY `idx_tenant_drug` (`tenant_id`, `drug_id`),
    KEY `idx_tenant_warehouse` (`tenant_id`, `warehouse_id`)
) ENGINE = InnoDB COMMENT = '库存表';

-- =====================================================================
-- adrug_bus_wms_1（结构与 _0 完全一致）
-- =====================================================================
USE `adrug_bus_wms_1`;

CREATE TABLE `inventory` LIKE `adrug_bus_wms_0`.`inventory`;

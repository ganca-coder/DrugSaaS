-- 业务库：adrug-bus-info（基础信息，如药品信息）
-- 库级分租户：tenant_id % 2 → adrug_bus_info_0 / adrug_bus_info_1
-- 表字段保存真实租户 ID（tenant_id 作分片键）
CREATE DATABASE IF NOT EXISTS `adrug_bus_info_0`
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS `adrug_bus_info_1`
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- =====================================================================
-- adrug_bus_info_0
-- =====================================================================
USE `adrug_bus_info_0`;

-- 药品信息表（业务实体，含定制化字段 cust1~cust6）
CREATE TABLE `drug` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（真实值，分片键）',
    `org_id`         BIGINT       NOT NULL                COMMENT '机构ID（药店/门店）',
    `drug_code`      VARCHAR(64)  NOT NULL                COMMENT '药品编码',
    `drug_name`      VARCHAR(128) NOT NULL                COMMENT '药品名称',
    `generic_name`   VARCHAR(128) NULL                    COMMENT '通用名',
    `spec`           VARCHAR(64)  NULL                    COMMENT '规格',
    `dosage_form`    VARCHAR(32)  NULL                    COMMENT '剂型',
    `unit`           VARCHAR(16)  NULL                    COMMENT '单位',
    `manufacturer`   VARCHAR(128) NULL                    COMMENT '生产厂家',
    `approval_no`    VARCHAR(64)  NULL                    COMMENT '批准文号',
    `main_barcode`            VARCHAR(64)   NULL           COMMENT '主条形码',
    `category`                TINYINT       NULL           COMMENT '商品分类：1-普通药品，2-中药饮片，3-精制饮片，4-医疗器械，5-保健食品，6-赠品，7-其他（DrugCategoryEnum）',
    `package_spec`            VARCHAR(64)   NULL           COMMENT '包装规格',
    `origin`                  VARCHAR(128)  NULL           COMMENT '产地',
    `marketing_holder`        VARCHAR(128)  NULL           COMMENT '上市持有人',
    `marketing_holder_address` VARCHAR(255) NULL           COMMENT '上市持有人地址',
    `approval_expiry_date`    DATE          NULL           COMMENT '批文有效期',
    `drug_supervision_code`   VARCHAR(64)   NULL           COMMENT '药监统一编码',
    `barcode1`                VARCHAR(64)   NULL           COMMENT '条码一',
    `standard_code`           VARCHAR(64)   NULL           COMMENT '本位码',
    `storage_condition`       VARCHAR(255)  NULL           COMMENT '储存条件',
    `business_scope`          VARCHAR(255)  NULL           COMMENT '经营范围',
    `split_spec`              VARCHAR(64)   NULL           COMMENT '拆零规格',
    `split_unit`              VARCHAR(16)   NULL           COMMENT '拆零单位',
    `purchase_price`          DECIMAL(18,4) NULL           COMMENT '进价',
    `retail_price`            DECIMAL(18,4) NULL           COMMENT '零售价',
    `member_price`            DECIMAL(18,4) NULL           COMMENT '会员价',
    `split_price`             DECIMAL(18,4) NULL           COMMENT '拆零价',
    `suggested_retail_price`  DECIMAL(18,4) NULL           COMMENT '建议零售价',
    `national_medical_insurance_code` VARCHAR(64) NULL     COMMENT '国家医保编码',
    `mnemonic_code`           VARCHAR(64)   NULL           COMMENT '助记码',
    `drug_type`      TINYINT      NOT NULL DEFAULT 1      COMMENT '药品类型：1-处方药，2-非处方药（DrugTypeEnum）',
    `status`         TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1-启用，0-停用（DrugStatusEnum）',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `cust1`          VARCHAR(255) NULL                    COMMENT '定制化字段1',
    `cust2`          VARCHAR(255) NULL                    COMMENT '定制化字段2',
    `cust3`          VARCHAR(255) NULL                    COMMENT '定制化字段3',
    `cust4`          VARCHAR(255) NULL                    COMMENT '定制化字段4',
    `cust5`          VARCHAR(255) NULL                    COMMENT '定制化字段5',
    `cust6`          VARCHAR(255) NULL                    COMMENT '定制化字段6',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_drug_code` (`tenant_id`, `drug_code`),
    KEY `idx_tenant_org` (`tenant_id`, `org_id`),
    KEY `idx_drug_name` (`drug_name`)
) ENGINE = InnoDB  COMMENT = '药品信息表';

-- 供应商信息表
CREATE TABLE `supplier` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（真实值，分片键）',
    `org_id`         BIGINT       NOT NULL                COMMENT '机构ID（药店/门店）',
    `supplier_code`  VARCHAR(64)  NOT NULL                COMMENT '供应商编码',
    `supplier_name`  VARCHAR(128) NOT NULL                COMMENT '供应商名称',
    `status`         TINYINT      NOT NULL DEFAULT 1      COMMENT '供应商状态：1-启用，0-停用（SupplierStatusEnum）',
    `enterprise_type` VARCHAR(32) NULL                    COMMENT '企业类型',
    `supplier_category` VARCHAR(32) NULL                  COMMENT '供应商分类',
    `tax_no`         VARCHAR(64)  NULL                    COMMENT '税号',
    `settlement_method` VARCHAR(32) NULL                  COMMENT '结算方式',
    `legal_representative` VARCHAR(64) NULL               COMMENT '法人代表',
    `phone`          VARCHAR(32)  NULL                    COMMENT '电话',
    `enterprise_leader` VARCHAR(64) NULL                  COMMENT '企业负责人',
    `quality_leader` VARCHAR(64)  NULL                    COMMENT '质量负责人',
    `contact`        VARCHAR(64)  NULL                    COMMENT '联系人',
    `contact_phone`  VARCHAR(32)  NULL                    COMMENT '联系人手机',
    `contact_id_card` VARCHAR(32) NULL                    COMMENT '联系人身份证',
    `company_address` VARCHAR(255) NULL                   COMMENT '公司地址',
    `warehouse_address` VARCHAR(255) NULL                 COMMENT '仓库地址',
    `region`         VARCHAR(64)  NULL                    COMMENT '区域',
    `province`       VARCHAR(64)  NULL                    COMMENT '省份',
    `city`           VARCHAR(64)  NULL                    COMMENT '城市',
    `district`       VARCHAR(64)  NULL                    COMMENT '区/县',
    `fax`            VARCHAR(32)  NULL                    COMMENT '传真',
    `email`          VARCHAR(64)  NULL                    COMMENT 'EMAIL',
    `postcode`       VARCHAR(16)  NULL                    COMMENT '邮政编码',
    `expected_delivery_days` INT  NULL                    COMMENT '预计送货天数',
    `input_tax_rate` DECIMAL(5,2) NULL                    COMMENT '进项税率(%)',
    `invoice_type`   VARCHAR(32)  NULL                    COMMENT '发票类型',
    `drug_supervision_unit` VARCHAR(128) NULL             COMMENT '药监往来单位',
    `entrusted_org`  VARCHAR(128) NULL                    COMMENT '受托机构',
    `mnemonic_code`  VARCHAR(64)  NULL                    COMMENT '助记码',
    `planned_variety` VARCHAR(255) NULL                   COMMENT '拟供应品种',
    `last_year_report` VARCHAR(255) NULL                  COMMENT '上一年度报告',
    `seal_style`     VARCHAR(255) NULL                    COMMENT '印章样式',
    `quality_system_survey` VARCHAR(255) NULL             COMMENT '质量体系调查表',
    `with_goods_bill_style` VARCHAR(255) NULL             COMMENT '随货同行单样式',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `cust1`          VARCHAR(255) NULL                    COMMENT '定制化字段1',
    `cust2`          VARCHAR(255) NULL                    COMMENT '定制化字段2',
    `cust3`          VARCHAR(255) NULL                    COMMENT '定制化字段3',
    `cust4`          VARCHAR(255) NULL                    COMMENT '定制化字段4',
    `cust5`          VARCHAR(255) NULL                    COMMENT '定制化字段5',
    `cust6`          VARCHAR(255) NULL                    COMMENT '定制化字段6',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_supplier_code` (`tenant_id`, `supplier_code`),
    KEY `idx_tenant_org` (`tenant_id`, `org_id`),
    KEY `idx_supplier_name` (`supplier_name`)
) ENGINE = InnoDB COMMENT = '供应商信息表';

-- 供应商证件信息表（供应商从表，一对多）
CREATE TABLE `supplier_certificate` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（真实值，分片键）',
    `org_id`         BIGINT       NOT NULL                COMMENT '机构ID（药店/门店）',
    `supplier_id`    BIGINT       NOT NULL                COMMENT '供应商ID（关联 supplier.id）',
    `seq_no`         INT          NULL                    COMMENT '序号',
    `cert_type`      VARCHAR(64)  NULL                    COMMENT '证件类型',
    `cert_no`        VARCHAR(64)  NULL                    COMMENT '证件号码',
    `cert_start_date` DATE        NULL                    COMMENT '证件开始日期',
    `cert_expiry_date` DATE      NULL                    COMMENT '证件有效期',
    `business_scope` VARCHAR(500) NULL                    COMMENT '经营范围',
    `issuing_authority` VARCHAR(128) NULL                 COMMENT '发证机关',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `cust1`          VARCHAR(255) NULL                    COMMENT '定制化字段1',
    `cust2`          VARCHAR(255) NULL                    COMMENT '定制化字段2',
    `cust3`          VARCHAR(255) NULL                    COMMENT '定制化字段3',
    `cust4`          VARCHAR(255) NULL                    COMMENT '定制化字段4',
    `cust5`          VARCHAR(255) NULL                    COMMENT '定制化字段5',
    `cust6`          VARCHAR(255) NULL                    COMMENT '定制化字段6',
    PRIMARY KEY (`id`),
    KEY `idx_supplier_id` (`supplier_id`),
    KEY `idx_tenant` (`tenant_id`)
) ENGINE = InnoDB COMMENT = '供应商证件信息表';

-- 仓库信息表（含仓库类型：合格仓/不合格仓/待验区/退货区）
CREATE TABLE `warehouse` (
    `id`             BIGINT       NOT NULL                COMMENT '主键（雪花ID）',
    `tenant_id`      BIGINT       NOT NULL                COMMENT '租户ID（真实值，分片键）',
    `org_id`         BIGINT       NOT NULL                COMMENT '机构ID（药店/门店）',
    `warehouse_code` VARCHAR(64)  NOT NULL                COMMENT '仓库编码',
    `warehouse_name` VARCHAR(128) NOT NULL                COMMENT '仓库名称',
    `warehouse_type` TINYINT      NOT NULL DEFAULT 1      COMMENT '仓库类型：1-合格仓，2-不合格仓，3-待验区，4-退货区（WarehouseTypeEnum）',
    `address`        VARCHAR(255) NULL                    COMMENT '仓库地址',
    `manager`        VARCHAR(64)  NULL                    COMMENT '负责人',
    `phone`          VARCHAR(32)  NULL                    COMMENT '联系电话',
    `status`         TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1-启用，0-停用（WarehouseStatusEnum）',
    `remark`         VARCHAR(500) NULL                    COMMENT '备注',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT       NULL                    COMMENT '创建人（用户ID）',
    `update_user_id` BIGINT       NULL                    COMMENT '更新人（用户ID）',
    `deleted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除标记：0-未删除，1-已删除',
    `version`        INT          NOT NULL DEFAULT 0      COMMENT '乐观锁版本号',
    `cust1`          VARCHAR(255) NULL                    COMMENT '定制化字段1',
    `cust2`          VARCHAR(255) NULL                    COMMENT '定制化字段2',
    `cust3`          VARCHAR(255) NULL                    COMMENT '定制化字段3',
    `cust4`          VARCHAR(255) NULL                    COMMENT '定制化字段4',
    `cust5`          VARCHAR(255) NULL                    COMMENT '定制化字段5',
    `cust6`          VARCHAR(255) NULL                    COMMENT '定制化字段6',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_warehouse_code` (`tenant_id`, `warehouse_code`),
    KEY `idx_tenant_org` (`tenant_id`, `org_id`),
    KEY `idx_warehouse_name` (`warehouse_name`)
) ENGINE = InnoDB COMMENT = '仓库信息表';

-- =====================================================================
-- adrug_bus_info_1（结构与 _0 完全一致）
-- =====================================================================
USE `adrug_bus_info_1`;

CREATE TABLE `drug` LIKE `adrug_bus_info_0`.`drug`;
CREATE TABLE `supplier` LIKE `adrug_bus_info_0`.`supplier`;
CREATE TABLE `supplier_certificate` LIKE `adrug_bus_info_0`.`supplier_certificate`;
CREATE TABLE `warehouse` LIKE `adrug_bus_info_0`.`warehouse`;

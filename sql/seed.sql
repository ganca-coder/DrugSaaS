-- 引导种子数据：默认租户 + 机构 + 管理员账号
-- 账号 admin / 密码 123456（BCrypt 存储）
USE `adrug_common`;

-- 默认租户（id 即后续其它表的 tenant_id）
INSERT INTO `tenant`
    (`id`, `tenant_code`, `tenant_name`, `status`, `contact_name`, `contact_phone`, `remark`)
VALUES
    (1, 'demo', '演示租户', 1, '管理员', '13800000000', '默认演示租户');

-- 默认机构（总部）
INSERT INTO `org`
    (`id`, `tenant_id`, `parent_id`, `org_code`, `org_name`, `org_type`, `status`, `remark`)
VALUES
    (1, 1, 0, 'HQ', '演示总部', 1, 1, '默认总部');

-- 默认管理员账号（admin / 123456）
INSERT INTO `account`
    (`id`, `tenant_id`, `org_id`, `username`, `password`, `account_type`, `employee_id`, `status`, `remark`)
VALUES
    (1, 1, 1, 'admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 1, NULL, 1, '默认管理员');

# DrugERP 项目说明

## 项目概述

医药 ERP SaaS 项目，当前聚焦**单一/连锁药店**业务，并预留 B2B、B2C、O2O 及医保（医保）业务扩展。

- 数据隔离模型：**租户 → 机构 → 员工**
- 前端：Vue（`adrug-web`）
- 后端：Spring Cloud 微服务，多模块 Maven 工程
- 当前处于**骨架搭建阶段**：模块目录与 POM 已生成，业务代码尚未实现。

## 技术栈与版本

| 组件 | 版本 |
|---|---|
| JDK | 21 |
| Spring Cloud | 2025.1.2 |
| Spring Boot | 4.1.1 |
| Nacos | 3.2.4 |
| RabbitMQ（服务端） | 4.3.1 |
| XXL-Job | 3.4.2 |
| Redis（服务端） | 8.2.8 |
| EhCache | 3.12.0 |
| MySQL | 8.4（驱动 `com.mysql:mysql-connector-j` 版本 8.4.0） |
| MyBatis-Plus | `com.baomidou:mybatis-plus-spring-boot3-starter` 3.5.9 |

> 版本统一在 `adrug-parent/pom.xml` 的 `<properties>` + `<dependencyManagement>` 中声明。子模块引用依赖时不写版本号。

## 坐标约定

- `groupId`：`com.adrug.erp`
- `version`：`1.0.0-SNAPSHOT`

## 模块结构

```
pom.xml                      # 根聚合工程 (adrug)，parent 指向 adrug-parent
adrug-parent/             # 通用父模块，统一版本/依赖管理
adrug-common/             # 通用模块（pom 聚合）
  adrug-common-model      #   通用实体类
  adrug-common-core       #   通用逻辑（如通用切面）
  adrug-common-mq         #   通用 MQ
  adrug-common-redis      #   通用 Redis
  adrug-common-db         #   通用数据源（含 MyBatis 依赖）
  adrug-common-cache      #   通用缓存
  adrug-common-oss        #   通用 OSS
  adrug-common-all        #   聚合
adrug-app/                # 应用层，编排业务流程，不处理具体业务逻辑
  adrug-app-oper/         #   运营管理
  adrug-app-shop/         #   药店收银台
  adrug-app-term/         #   终端接入（手机应用、PDA 扫描）
  adrug-app-mq/           #   通用 MQ 消费（如更新 OMS 订单状态）
  adrug-app-job/          #   定时任务
  adrug-app-out/          #   外部多渠道
    adrug-app-out-meituan/  # 美团渠道（拉订单、MQ 更新订单状态）
adrug-svc/                # 服务层，不处理跨业务流程，只处理服务内部逻辑
  adrug-svc-user/         #   用户服务
    adrug-svc-user-info/  #     账号信息、租户信息、机构信息
      adrug-svc-user-info-interface/   # Openfeign 接口，与 Controller 接口联动
      adrug-svc-user-info-model/       # 对外实体类
      adrug-svc-user-info-repository/  # 数据源
      adrug-svc-user-info-provider/    # Controller 及依赖倒置实现类
  adrug-svc-common/       #   通用服务（菜单等，非业务）
    adrug-svc-common-interface/   # Openfeign 接口，与 Controller 接口联动
    adrug-svc-common-model/       # 对外实体类
    adrug-svc-common-repository/  # 数据源
    adrug-svc-common-provider/    # Controller 及依赖倒置实现类
  adrug-svc-bus/          #   业务服务
    adrug-svc-bus-common/ #     业务通用逻辑（如字典）
    adrug-svc-bus-info/   #     基础信息（药品、供应商、厂家、单位、仓库等）
    adrug-svc-bus-pms/    #     采购管理
    adrug-svc-bus-oms/    #     订单管理
      adrug-svc-bus-oms-shop/ # 药店订单管理
    adrug-svc-bus-wms/    #     仓库管理
    adrug-svc-bus-tms/    #     运输管理
  adrug-svc-gsp/          #   医药行业 GSP
    adrug-svc-gsp-minsurance/ # 医保服务
    adrug-svc-gsp-buslog/    # 业务日志服务
  adrug-svc-ext/          #   扩展服务
    adrug-svc-ext-iexport/ #  导入导出（批量导入、批量导出）
    adrug-svc-ext-ai/      #  AI Agent
adrug-boot/               # 组合启动方式
  adrug-boot-all/         #   全部服务组合启动，即单一服务
adrug-gateway/            # 网关（含主类 + spring-cloud-starter-gateway）
adrug-web/                # 前端（Vue）
adrug-android/            # Android 客户端
adrug-ios/                # iOS 客户端
```

## 代码实现规范（详见 Skill）

> 后端代码实现规范（分层与依赖、基础实体字段、接口路径、Mapper XML 预留、枚举定义、各层代码模板等）已沉淀到 Skill：**`springboot-code-standard`**（`.claude/skills/springboot-code-standard/SKILL.md`）。
>
> 生成/修改「Controller → Service → Repository → Mapper → 数据库」完整链路时，会自动加载该规范。**`adrug-app` 模块除外**（客户端接入服务，不按此规范分层）。

## 应用层（adrug-app）分层约定

> `adrug-app` 模块不按上面 Skill 的四层（interface/model/repository/provider）分层，而是按「编排」定位，采用 `Controller → Service → Rpc → Feign` 调用链。以 `adrug-app-oper-service` 为例：

**包结构**：`com.adrug.erp.app.{client}.{领域}.{controller|service|rpc}[.impl]`

- `领域` 对应 svc 服务名去掉 `adrug-` 前缀，如 `user`（对应 `adrug-svc-user-info`）、`bus`（对应 `adrug-svc-bus-info`）。

**命名约定**：

| 层 | 命名 | 示例 |
|---|---|---|
| Controller | `{领域}InfoController`（领域 + Info + Controller） | `UserInfoController`、`BusInfoController` |
| Service | `{实体}Service` | `EmployeeService`、`DrugService` |
| Rpc | `{实体}Rpc` | `EmployeeRpc`、`DrugRpc` |

**实现方式**：

- Controller：`@RestController` + `@RequestMapping("app/oper/{领域}/info")`，`@Autowired` 注入 Service；方法名 `{实体}Page`（如 `drugPage`/`employeePage`），转发 `service.page(query)`。
- Service：接口 + `@Service` 实现，`@Autowired` 注入 Rpc，纯转发编排、不写业务逻辑。
- Rpc：接口 + `@Service` 实现，`@Autowired` 注入 Feign（svc 层 interface 模块），调用后 `.getData()` 解包 `Result`。

**调用链**：`Controller → Service → Rpc → Feign`（Feign 定义在 svc 层 `-interface` 模块，与 svc 层 Controller 路径一致）。

## 需求文档

需求文档来源为语雀知识库（私有，需登录，`WebFetch` 无法抓取），用户导出为 Markdown 放在 `docs/` 目录。

- 已读：`docs/基础信息.md` —— 基础信息模块规格（对应 `adrug-bus-info`），关键规则见下。

## 建表 SQL 约定

- 建表 SQL 统一放在项目根目录下的 **`sql/`** 文件夹（与 `docs/` 平行）。
- 每张表一个 `.sql` 文件，命名与表名一致（如 `employee.sql`）。
- 已生成：`sql/employee.sql`（员工信息表）。

## 关键业务规则（摘自 docs/基础信息.md）

- **只读 + 维护单模式**：商品信息、供应商信息界面只允许查询，变更须通过「维护单」流程（勾选 → 加载到维护单 → 变更资料）。
- **被引用数据禁止删除**（红字强调）：库区、商品分类、员工、岗位、经营范围、仓库、机构等，凡被其它模块引用均不允许删除 → 需软删除 + 引用检查。
- **主从表**：厂家信息 = 厂家主表 + 多个证件从表（一对多）。
- **树形数据**：商品分类、经营范围、组织机构支持「新增本级/新增下级」（`parent_id` 树形结构）。
- **通用档案/可配置字段**：证件信息为通用档案，被供应商/门店/企业复用，属性字段可配置。

## 基础信息模块归属边界（已确认）

- **`adrug-svc-user-info`（用户服务）**：员工信息(1.4)、岗位信息(1.4.3)、药店信息/组织机构(1.8) —— 归属租户/机构/账号体系。
- **`adrug-svc-bus-info`（业务基础信息）**：商品管理(1.1)、供应商信息(1.2)、厂家信息(1.3)、质量管理(1.5)、商品货位配置(1.6)、仓库信息(1.7) —— 纯业务基础数据。

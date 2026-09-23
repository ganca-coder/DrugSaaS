---
name: springboot-code-standard
description: DrugERP 后端代码实现规范。当生成或修改「从 Controller 到数据库」的完整接口链路（Controller、Service、Repository、Mapper、Convert、Enum、Entity、DTO、Query、VO）时，必须无条件遵循本规范。adrug-app 模块除外。涉及分层依赖、基础实体字段、接口路径、Mapper XML 预留、枚举定义、传输层 code 约定等要求时必须加载并应用本技能。
---

# DrugERP 后端代码实现规范

## 适用范围（重要）

本规范强制适用于「从 Controller 到数据库」的完整接口链路：

```
Controller -> Service -> Repository -> Mapper -> 数据库
```

只要接口调用链贯穿这四层，就必须完整遵循本规范。

### 必须应用本规范的场景

- 新增一个从 Controller 到数据库的接口（CRUD、分页、批量等）。
- 新增/修改涉及 Entity、DTO、Query、VO、Enum、Convert、Mapper、Mapper XML、Repository、Service、Controller 中任意一层的代码，且整条链路落到数据库。
- 用户提供 DDL、字段列表、实体描述，要求落地完整对外接口。
- 用户要求「加一个接口」「加一个查询」「加一个分页」「加一个状态」等。

### 不适用本规范的场景（例外）

- **`adrug-app` 模块**：客户端接入服务（oper/shop/mobile/ai），不按本规范分层实现。
- 纯工具类、纯配置类、纯静态方法等不涉及完整链路的代码。
- 用户明确说「本次不按规范」「只改这一层」的任务。

> 判断原则：**链路是否完整贯穿 Controller -> Service -> Repository -> Mapper -> 数据库。** 是 → 强制套用本规范；否，或属于 `adrug-app` → 不套用。

## 技术栈与坐标

| 项 | 值 |
|---|---|
| JDK | 21 |
| Spring Cloud / Boot | 2025.1.2 / 4.1.1 |
| ORM | MyBatis-Plus `mybatis-plus-spring-boot3-starter` 3.5.9 |
| groupId | `com.adrug.erp` |
| version | `1.0.0-SNAPSHOT` |
| 通用基类 | `com.adrug.erp.common.entity.BaseEntity` |
| 业务基类 | `com.adrug.erp.common.entity.BusBaseEntity`（继承 BaseEntity，含 cust1~6） |
| 枚举接口 | `com.adrug.erp.common.enums.BaseEnum` |
| 分页 | `com.adrug.erp.common.dto.PageQuery` / `PageResult` |
| 统一返回 | `com.adrug.erp.common.result.Result` / `ResultCode` |
| 业务异常 | `com.adrug.erp.common.core.exception.BusinessException` |

## 分层与依赖约定（依赖倒置）

需要拆分的业务服务（如 `adrug-user-info`、`adrug-user-auth`、`adrug-bus-pms`）按四层拆子模块：

| 子模块 | 打包 | 依赖方向 |
|---|---|---|
| `-interface` | jar | 只依赖 `-model` + `adrug-common-core`（Openfeign 接口，与 Controller 联动） |
| `-model` | jar | 只依赖 `adrug-common-model`（对外实体/DTO/VO/Query/枚举） |
| `-repository` | jar | 依赖 `-model` + `adrug-common-db`（数据源及 Mapper） |
| `-service` | jar | 依赖 `-interface` + `-model` + `-repository`（Controller + Service 实现） |

依赖方向单向：`service -> repository -> model`；`service -> interface -> model`。

## 包结构约定

以服务 `adrug-user-info`（服务根包 `com.adrug.erp.user.info`）、实体 `Employee` 为例：

| 文件 | 包/路径 |
|---|---|
| Entity | `Employee` |
| Enum | `com.adrug.erp.user.info.enums.XxxEnum` |
| DTO | `com.adrug.erp.user.info.model.dto.EmployeeSaveDTO` |
| Query | `com.adrug.erp.user.info.model.query.EmployeeQuery` |
| VO | `com.adrug.erp.user.info.model.vo.EmployeeVO` |
| Mapper | `EmployeeMapper` |
| Mapper XML | `src/main/resources/mapper/EmployeeMapper.xml`（repository 模块） |
| Repository | `EmployeeRepository` |
| RepositoryImpl | `EmployeeRepositoryImpl` |
| Service | `EmployeeService` |
| ServiceImpl | `EmployeeServiceImpl` |
| Convert | `EmployeeConvert` |
| Controller | `EmployeeController`（service 模块） |
| Feign | `EmployeeFeign`（interface 模块） |

## 命名约定

以业务对象 `Employee` 为例：

| 类型 | 命名 |
|---|---|
| 实体 | `Employee`（`@TableName` 对应表） |
| DTO | `EmployeeSaveDTO` |
| Query | `EmployeeQuery` |
| VO | `EmployeeVO` |
| Enum | `GenderEnum` / `EmployeeStatusEnum` 等（按业务语义） |
| Convert | `EmployeeConvert` |
| Mapper | `EmployeeMapper` |
| Repository | `EmployeeRepository` |
| RepositoryImpl | `EmployeeRepositoryImpl` |
| Service | `EmployeeService` |
| ServiceImpl | `EmployeeServiceImpl` |
| Controller | `EmployeeController` |
| Feign | `EmployeeFeign` |

字段转驼峰：`emp_no -> empNo`、`id_card_no -> idCardNo`、`create_time -> createTime`。

## 基础实体字段约定

所有**租户相关表**统一含以下通用字段（由 `BaseEntity` 承载）：

- `id`（雪花主键 `@TableId(type = ASSIGN_ID)`）、`tenantId`、`orgId`
- 审计字段：`createTime`、`updateTime`（`@TableField(fill = ...)` 自动填充）、`createUserId`、`updateUserId`
- `deleted`（`@TableLogic` 逻辑删除）、`version`（`@Version` 乐观锁）

实体基类分两种：

| 基类 | 适用域 | 字段 |
|---|---|---|
| `BaseEntity` | 通用库（如 `adrug_common`：账号/租户/机构/员工） | 上述通用字段，**不含定制化字段** |
| `BusBaseEntity extends BaseEntity` | 业务库（如 `adrug-bus-info`） | 通用字段 + **定制化字段 `cust1` ~ `cust6`**（`String`） |

> 业务库实体继承 `BusBaseEntity`，建表时须包含 `cust1`~`cust6`（建议 `VARCHAR(255)`），
> 定制化字段须贯通 Entity（继承自基类） / DTO / VO / Query / Mapper XML（resultMap + Base_Column_List）。
> 通用库实体继承 `BaseEntity`，**不含** `cust` 字段。

## 接口路径规范

Controller 的 `@RequestMapping` 与 Feign 的 `@FeignClient(path=...)` 统一遵循：

```
/{服务模块路径}/{资源名}
```

- **服务模块路径**：服务模块名去掉 `adrug-` 前缀，中划线 `-` 替换为 `/`。
- **资源名**：实体类名（去掉 `Entity` 后缀）转小写，多词用中划线分隔。

示例：

| 服务模块 | 资源 | 路径 |
|---|---|---|
| `adrug-user-info` | `Employee` | `/user/info/employee` |
| `adrug-user-auth` | `Role` | `/user/auth/role` |
| `adrug-bus-pms` | `PurchaseOrder` | `/bus/pms/purchase-order` |

> Controller 与对应 Feign 的路径必须一致；Feign 的 `name` 仍用服务模块名（如 `adrug-user-info`）。

## 枚举规范

所有业务枚举统一遵循：

1. **公共接口**：实现 `BaseEnum<T>`，约定 `getCode()` / `getName()` / `getDesc()`。
   - `code`：存储/传输值
   - `name`：预留名称（默认取枚举常量名）
   - `desc`：展示文案
2. **字段顺序**：常量与字段统一按 `code, name, desc` 顺序（`desc` 放最后）。
3. **落库**：实体字段直接存 `code`（Integer），枚举类不参与 MyBatis 映射（无需 `@EnumValue`）。
4. **传输**：枚举 JSON 序列化/反序列化用 code —— `@JsonValue` 标注在 `getCode()`，`@JsonCreator` 提供 `of(code)`。
5. **反查**：反查逻辑收敛在 `BaseEnum` 接口静态方法 `BaseEnum.ofCode(Class, code)` / `BaseEnum.ofName(Class, name)`；枚举类 `of(code)` / `ofName(name)` 各一行委托。值为 null 或无匹配返回 null。
6. **VO 展示文案**：VO 为枚举字段额外生成 `getXxxDesc()` 方法。
7. 枚举类加 `@Getter @AllArgsConstructor`，`code`/`name`/`desc` 为 `private final` 字段。

## 枚举 code 约定（传输层与实体）

- 传输对象（DTO / Query / VO）与实体（Entity）的枚举字段一律用 `Integer code`，不用枚举类型。
- 枚举类仅用于展示文案（VO 的 `getXxxDesc()`）与 code 语义说明，不参与 MyBatis 映射。
- 类型转换无需枚举↔code 切换，Convert 层直接属性拷贝。
- DTO / Query / VO / Entity 中的枚举 code 字段注释须标注对应枚举类：`/** xxx，对应枚举类 {@link XxxEnum} */`。

## Lombok

- 实体用 `@Getter @Setter`（继承基类，避免字段遮蔽）。
- DTO / VO / Query 用 `@Data`。
- 不手写 getter/setter。

## Mapper XML 预留约定

1. Mapper 接口继承 `BaseMapper` 承担简单 CRUD / 分页 / 逻辑删除，无需 XML。
2. 每个 Mapper 预留 XML 文件 `src/main/resources/mapper/<Xxx>Mapper.xml`，用于复杂 SQL。
3. XML 需包含 `resultMap id="BaseResultMap"`（映射全部字段含基类字段）与 `<sql id="Base_Column_List">`。
4. 实体字段存 code（Integer），resultMap 中枚举字段按普通列映射，无需指定枚举 typeHandler。
5. 软删除字段 `deleted` 由 `@TableLogic` 自动处理，XML 不写删除条件。
6. `application.yml` 声明 `mybatis-plus.mapper-locations: classpath*:/mapper/**/*.xml`。
7. **物理删除预留**：在 Mapper 接口声明 `int deletePhysically(@Param("id") Long id)` + XML 写 `<delete id="deletePhysically">DELETE FROM ... WHERE id = #{id}</delete>`（绕过 @TableLogic）。
8. 新增自定义方法：Mapper 接口声明方法 + XML 补对应 SQL 节点，namespace 与 Mapper 全限定名一致。

## 修改已有接口时的强制动作

1. 识别本次改动涉及的层。
2. 检查被改文件是否符合本规范（字段注释、枚举化、@TableLogic、时间范围、业务实体 cust 字段、@author/@date、XML 的 resultMap/Base_Column_List）。
3. 不符合的，一并修正。
4. 回复中列出「本次按规范额外修正项」。

> 例外：`adrug-app` 模块不套用本规范。

---

# 代码模板

以下模板基于项目实际规范（以 `Employee` 为示例）。实际生成时替换业务字段，但**结构、注解、命名、包路径规则不变**。

## 枚举类模板

```java
package com.adrug.erp.user.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements BaseEnum<Integer> {

    /** 未知 */
    UNKNOWN(0, "UNKNOWN", "未知"),

    /** 男 */
    MALE(1, "MALE", "男"),

    /** 女 */
    FEMALE(2, "FEMALE", "女");

    /** 状态码 */
    private final Integer code;

    /** 预留名称字段 */
    private final String name;

    /** 状态描述 */
    private final String desc;

    /**
     * 序列化为 code。
     */
    @JsonValue
    public Integer getCode() {
        return code;
    }

    /**
     * 按 code 反序列化。
     */
    @JsonCreator
    public static GenderEnum of(Integer code) {
        return BaseEnum.ofCode(GenderEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static GenderEnum ofName(String name) {
        return BaseEnum.ofName(GenderEnum.class, name);
    }
}
```

## 实体模板

```java
package com.adrug.erp.user.info.entity;

import com.adrug.erp.common.entity.BaseEntity;
import EmployeeStatusEnum;
import GenderEnum;
import PharmacistFlagEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 员工信息实体（对应基础信息——员工管理——员工信息）。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("employee")
public class Employee extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 工号 */
    private String empNo;

    /** 姓名 */
    private String name;

    /** 性别（{@link GenderEnum} code） */
    private Integer gender;

    /** 出生日期 */
    private LocalDate birthDate;

    /** 身份证号 */
    private String idCardNo;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 岗位 ID（关联岗位信息） */
    private Long positionId;

    /** 入职日期 */
    private LocalDate entryDate;

    /** 离职日期 */
    private LocalDate resignDate;

    /** 在职状态（{@link EmployeeStatusEnum} code） */
    private Integer status;

    /** 是否药师（{@link PharmacistFlagEnum} code） */
    private Integer pharmacist;

    /** 备注 */
    private String remark;
}
```

## DTO 模板

```java
package com.adrug.erp.user.info.model.dto;

import EmployeeStatusEnum;
import GenderEnum;
import PharmacistFlagEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 员工信息新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段由后端自动填充，不对外暴露。
 * 枚举字段（性别/在职状态/是否药师）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class EmployeeSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 工号 */
    private String empNo;

    /** 姓名 */
    private String name;

    /** 性别，对应枚举类 {@link GenderEnum} */
    private Integer gender;

    /** 出生日期 */
    private LocalDate birthDate;

    /** 身份证号 */
    private String idCardNo;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 岗位 ID（关联岗位信息） */
    private Long positionId;

    /** 入职日期 */
    private LocalDate entryDate;

    /** 离职日期 */
    private LocalDate resignDate;

    /** 在职状态，对应枚举类 {@link EmployeeStatusEnum} */
    private Integer status;

    /** 是否药师，对应枚举类 {@link PharmacistFlagEnum} */
    private Integer pharmacist;

    /** 备注 */
    private String remark;
}
```

## Query 模板

```java
package com.adrug.erp.user.info.model.query;

import com.adrug.erp.common.dto.PageQuery;
import EmployeeStatusEnum;
import GenderEnum;
import PharmacistFlagEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工信息查询条件。
 * <p>
 * 覆盖 {@code Employee} 的全部业务与审计字段，并针对日期/时间字段提供范围查询。
 * 枚举字段（性别/在职状态/是否药师）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class EmployeeQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：姓名 / 工号 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    private Long orgId;

    /** 工号（精确匹配） */
    private String empNo;

    /** 姓名（精确匹配） */
    private String name;

    /** 性别，对应枚举类 {@link GenderEnum} */
    private Integer gender;

    /** 出生日期（精确匹配） */
    private LocalDate birthDate;

    /** 出生日期范围-起 */
    private LocalDate birthDateStart;

    /** 出生日期范围-止 */
    private LocalDate birthDateEnd;

    /** 身份证号（精确匹配） */
    private String idCardNo;

    /** 手机号（精确匹配） */
    private String phone;

    /** 邮箱（精确匹配） */
    private String email;

    /** 岗位 ID */
    private Long positionId;

    /** 入职日期（精确匹配） */
    private LocalDate entryDate;

    /** 入职日期范围-起 */
    private LocalDate entryDateStart;

    /** 入职日期范围-止 */
    private LocalDate entryDateEnd;

    /** 离职日期（精确匹配） */
    private LocalDate resignDate;

    /** 离职日期范围-起 */
    private LocalDate resignDateStart;

    /** 离职日期范围-止 */
    private LocalDate resignDateEnd;

    /** 在职状态，对应枚举类 {@link EmployeeStatusEnum} */
    private Integer status;

    /** 是否药师，对应枚举类 {@link PharmacistFlagEnum} */
    private Integer pharmacist;

    /** 备注（精确匹配） */
    private String remark;

    /** 创建人（用户 ID） */
    private Long createUserId;

    /** 更新人（用户 ID） */
    private Long updateUserId;

    /** 创建时间范围-起 */
    private LocalDateTime createTimeStart;

    /** 创建时间范围-止 */
    private LocalDateTime createTimeEnd;

    /** 更新时间范围-起 */
    private LocalDateTime updateTimeStart;

    /** 更新时间范围-止 */
    private LocalDateTime updateTimeEnd;
}
```

## VO 模板

```java
package com.adrug.erp.user.info.model.vo;

import com.adrug.erp.common.enums.BaseEnum;
import EmployeeStatusEnum;
import GenderEnum;
import PharmacistFlagEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工信息视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 * <p>
 * 枚举字段（性别/在职状态/是否药师）以 code 值表示，desc 字段通过 {@code getXxxDesc()} 提供。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class EmployeeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String empNo;

    private String name;

    /** 性别，对应枚举类 {@link GenderEnum} */
    private Integer gender;

    private LocalDate birthDate;

    private String idCardNo;

    private String phone;

    private String email;

    private Long positionId;

    private LocalDate entryDate;

    private LocalDate resignDate;

    /** 在职状态，对应枚举类 {@link EmployeeStatusEnum} */
    private Integer status;

    /** 是否药师，对应枚举类 {@link PharmacistFlagEnum} */
    private Integer pharmacist;

    private String remark;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 性别描述
     */
    public String getGenderDesc() {
        GenderEnum genderEnum = BaseEnum.ofCode(GenderEnum.class, gender);
        return genderEnum == null ? null : genderEnum.getDesc();
    }

    /**
     * 在职状态描述
     */
    public String getStatusDesc() {
        EmployeeStatusEnum statusEnum = BaseEnum.ofCode(EmployeeStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }

    /**
     * 是否药师描述
     */
    public String getPharmacistDesc() {
        PharmacistFlagEnum pharmacistEnum = BaseEnum.ofCode(PharmacistFlagEnum.class, pharmacist);
        return pharmacistEnum == null ? null : pharmacistEnum.getDesc();
    }
}
```

## Convert 模板

```java
package com.adrug.erp.user.info.convert;

import Employee;
import com.adrug.erp.user.info.model.dto.EmployeeSaveDTO;
import com.adrug.erp.user.info.model.vo.EmployeeVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工信息实体 <-> 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）与实体中的枚举字段统一用 code（Integer），直接属性拷贝即可。
 */
public final class EmployeeConvert {

    private EmployeeConvert() {
    }

    /**
     * 实体转视图对象。
     */
    public static EmployeeVO toVO(Employee employee) {
        EmployeeVO vo = new EmployeeVO();
        BeanUtils.copyProperties(employee, vo);
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<EmployeeVO> toVOList(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体。
     */
    public static Employee toEntity(EmployeeSaveDTO dto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        return employee;
    }
}
```

## Mapper 接口模板

```java
package com.adrug.erp.user.info.mapper;

import Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 员工信息 Mapper。
 * <p>
 * 继承 MyBatis-Plus 的 {@link BaseMapper} 即可获得通用 CRUD、分页、逻辑删除能力。
 * 复杂 SQL（多表关联、动态条件等）预留 XML 方式实现，见
 * {@code resources/mapper/EmployeeMapper.xml}；未来在此接口声明自定义方法，
 * 并在 XML 中补充对应 SQL 节点。
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 物理删除员工（预留）。
     * <p>
     * 通过手写 DELETE 语句绕过 {@code @TableLogic} 逻辑删除，真正从数据库移除记录。
     * 仅用于特殊清理场景，业务常规删除请继续使用 {@link BaseMapper#deleteById}（逻辑删除）。
     *
     * @param id 员工主键
     * @return 影响行数
     */
    int deletePhysically(@Param("id") Long id);
}
```

## Mapper XML 模板

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
  员工信息 Mapper XML（预留）。
  当前简单 CRUD 由 MyBatis-Plus 的 BaseMapper 提供，无需 XML；
  本文件用于预留复杂 SQL（多表关联、自定义统计、动态条件查询等）的扩展点。

  注意事项：
  1. 枚举字段（gender/status/pharmacist）落库为 code，按普通列映射，无需指定枚举 typeHandler。
  2. 软删除字段 deleted 由 @TableLogic 自动处理，XML 中不写删除条件。
-->
<mapper namespace="EmployeeMapper">

    <resultMap id="BaseResultMap" type="Employee">
        <id column="id" property="id"/>
        <result column="tenant_id" property="tenantId"/>
        <result column="org_id" property="orgId"/>
        <result column="create_time" property="createTime"/>
        <result column="update_time" property="updateTime"/>
        <result column="create_user_id" property="createUserId"/>
        <result column="update_user_id" property="updateUserId"/>
        <result column="deleted" property="deleted"/>
        <result column="version" property="version"/>

        <result column="emp_no" property="empNo"/>
        <result column="name" property="name"/>
        <result column="gender" property="gender"/>
        <result column="birth_date" property="birthDate"/>
        <result column="id_card_no" property="idCardNo"/>
        <result column="phone" property="phone"/>
        <result column="email" property="email"/>
        <result column="position_id" property="positionId"/>
        <result column="entry_date" property="entryDate"/>
        <result column="resign_date" property="resignDate"/>
        <result column="status" property="status"/>
        <result column="pharmacist" property="pharmacist"/>
        <result column="remark" property="remark"/>
    </resultMap>

    <sql id="Base_Column_List">
        id, tenant_id, org_id, create_time, update_time, create_user_id, update_user_id,
        deleted, version,
        emp_no, name, gender, birth_date, id_card_no, phone, email,
        position_id, entry_date, resign_date, status, pharmacist, remark
    </sql>

    <delete id="deletePhysically">
        DELETE FROM employee WHERE id = #{id}
    </delete>
</mapper>
```

## Repository 接口模板

```java
package com.adrug.erp.user.info.repository;

import com.adrug.erp.common.dto.PageResult;
import Employee;
import com.adrug.erp.user.info.model.query.EmployeeQuery;
import EmployeeRepositoryImpl;

import java.util.Collection;
import java.util.List;

/**
 * 员工信息数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link EmployeeRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface EmployeeRepository {

    /**
     * 分页查询员工。
     */
    PageResult<Employee> selectPage(EmployeeQuery query);

    /**
     * 按 ID 查询员工。
     */
    Employee selectById(Long id);

    /**
     * 按 ID 集合批量查询员工。
     */
    List<Employee> selectByIds(Collection<Long> ids);

    /**
     * 新增员工（雪花主键回填到 {@code employee.id}）。
     */
    void insert(Employee employee);

    /**
     * 变更员工。
     */
    void updateById(Employee employee);

    /**
     * 删除员工（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除员工（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
```

## RepositoryImpl 模板

```java
package com.adrug.erp.user.info.repository.impl;

import com.adrug.erp.common.dto.PageResult;
import Employee;
import EmployeeMapper;
import com.adrug.erp.user.info.model.query.EmployeeQuery;
import EmployeeRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 员工信息数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EmployeeMapper employeeMapper;

    public EmployeeRepositoryImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public PageResult<Employee> selectPage(EmployeeQuery query) {
        Page<Employee> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        // 关键字：姓名 或 工号 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Employee::getName, query.getKeyword())
                        .or()
                        .like(Employee::getEmpNo, query.getKeyword()));

        // 精确匹配字段
        wrapper.eq(query.getId() != null, Employee::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Employee::getTenantId, query.getTenantId());
        wrapper.eq(query.getOrgId() != null, Employee::getOrgId, query.getOrgId());
        wrapper.eq(StringUtils.hasText(query.getEmpNo()), Employee::getEmpNo, query.getEmpNo());
        wrapper.eq(StringUtils.hasText(query.getName()), Employee::getName, query.getName());
        wrapper.eq(query.getGender() != null, Employee::getGender, query.getGender());
        wrapper.eq(query.getBirthDate() != null, Employee::getBirthDate, query.getBirthDate());
        wrapper.eq(StringUtils.hasText(query.getIdCardNo()), Employee::getIdCardNo, query.getIdCardNo());
        wrapper.eq(StringUtils.hasText(query.getPhone()), Employee::getPhone, query.getPhone());
        wrapper.eq(StringUtils.hasText(query.getEmail()), Employee::getEmail, query.getEmail());
        wrapper.eq(query.getPositionId() != null, Employee::getPositionId, query.getPositionId());
        wrapper.eq(query.getEntryDate() != null, Employee::getEntryDate, query.getEntryDate());
        wrapper.eq(query.getResignDate() != null, Employee::getResignDate, query.getResignDate());
        wrapper.eq(query.getStatus() != null, Employee::getStatus, query.getStatus());
        wrapper.eq(query.getPharmacist() != null, Employee::getPharmacist, query.getPharmacist());
        wrapper.eq(StringUtils.hasText(query.getRemark()), Employee::getRemark, query.getRemark());
        wrapper.eq(query.getCreateUserId() != null, Employee::getCreateUserId, query.getCreateUserId());
        wrapper.eq(query.getUpdateUserId() != null, Employee::getUpdateUserId, query.getUpdateUserId());
        // 日期/时间范围查询
        wrapper.ge(query.getBirthDateStart() != null, Employee::getBirthDate, query.getBirthDateStart());
        wrapper.le(query.getBirthDateEnd() != null, Employee::getBirthDate, query.getBirthDateEnd());
        wrapper.ge(query.getEntryDateStart() != null, Employee::getEntryDate, query.getEntryDateStart());
        wrapper.le(query.getEntryDateEnd() != null, Employee::getEntryDate, query.getEntryDateEnd());
        wrapper.ge(query.getResignDateStart() != null, Employee::getResignDate, query.getResignDateStart());
        wrapper.le(query.getResignDateEnd() != null, Employee::getResignDate, query.getResignDateEnd());
        wrapper.ge(query.getCreateTimeStart() != null, Employee::getCreateTime, query.getCreateTimeStart());
        wrapper.le(query.getCreateTimeEnd() != null, Employee::getCreateTime, query.getCreateTimeEnd());
        wrapper.ge(query.getUpdateTimeStart() != null, Employee::getUpdateTime, query.getUpdateTimeStart());
        wrapper.le(query.getUpdateTimeEnd() != null, Employee::getUpdateTime, query.getUpdateTimeEnd());

        wrapper.orderByDesc(Employee::getCreateTime);

        Page<Employee> result = employeeMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Employee selectById(Long id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public List<Employee> selectByIds(Collection<Long> ids) {
        return employeeMapper.selectByIds(ids);
    }

    @Override
    public void insert(Employee employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public void updateById(Employee employee) {
        employeeMapper.updateById(employee);
    }

    @Override
    public void deleteById(Long id) {
        employeeMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return employeeMapper.deletePhysically(id);
    }
}
```

## Service 接口模板

```java
package com.adrug.erp.user.info.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.user.info.model.dto.EmployeeSaveDTO;
import com.adrug.erp.user.info.model.query.EmployeeQuery;
import com.adrug.erp.user.info.model.vo.EmployeeVO;

import java.util.Collection;
import java.util.List;

/**
 * 员工信息服务接口（依赖倒置契约）。
 * <p>
 * Controller 依赖本抽象接口，具体实现位于 service 模块 {@code EmployeeServiceImpl}。
 */
public interface EmployeeService {

    /**
     * 分页查询员工。
     */
    PageResult<EmployeeVO> page(EmployeeQuery query);

    /**
     * 按 ID 查询员工。
     */
    EmployeeVO getById(Long id);

    /**
     * 按 ID 集合批量查询员工。
     */
    List<EmployeeVO> getByIds(Collection<Long> ids);

    /**
     * 新增员工。
     *
     * @return 新员工主键
     */
    Long create(EmployeeSaveDTO dto);

    /**
     * 变更员工。
     */
    void update(Long id, EmployeeSaveDTO dto);

    /**
     * 删除员工（逻辑删除）。
     */
    void delete(Long id);

    /**
     * 物理删除员工（预留）。
     */
    void deletePhysically(Long id);
}
```

## ServiceImpl 模板

```java
package com.adrug.erp.user.info.service.impl;

import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.result.ResultCode;
import com.adrug.erp.common.dto.PageResult;
import EmployeeConvert;
import Employee;
import com.adrug.erp.user.info.model.dto.EmployeeSaveDTO;
import com.adrug.erp.user.info.model.query.EmployeeQuery;
import com.adrug.erp.user.info.model.vo.EmployeeVO;
import EmployeeRepository;
import EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 员工信息服务实现。
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public PageResult<EmployeeVO> page(EmployeeQuery query) {
        PageResult<Employee> pageResult = employeeRepository.selectPage(query);
        List<EmployeeVO> records = EmployeeConvert.toVOList(pageResult.getRecords());
        return PageResult.of(records, pageResult.getTotal(), pageResult.getPageNum(), pageResult.getPageSize());
    }

    @Override
    public EmployeeVO getById(Long id) {
        Employee employee = employeeRepository.selectById(id);
        if (employee == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return EmployeeConvert.toVO(employee);
    }

    @Override
    public List<EmployeeVO> getByIds(Collection<Long> ids) {
        List<Employee> employees = employeeRepository.selectByIds(ids);
        return EmployeeConvert.toVOList(employees);
    }

    @Override
    public Long create(EmployeeSaveDTO dto) {
        Employee employee = EmployeeConvert.toEntity(dto);
        employee.setId(null);
        employeeRepository.insert(employee);
        return employee.getId();
    }

    @Override
    public void update(Long id, EmployeeSaveDTO dto) {
        Employee existing = employeeRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        Employee employee = EmployeeConvert.toEntity(dto);
        employee.setId(id);
        employeeRepository.updateById(employee);
    }

    @Override
    public void delete(Long id) {
        Employee existing = employeeRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // @TableLogic 逻辑删除
        employeeRepository.deleteById(id);
    }

    @Override
    public void deletePhysically(Long id) {
        Employee existing = employeeRepository.selectById(id);
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        employeeRepository.deletePhysically(id);
    }
}
```

## Controller 模板

```java
package com.adrug.erp.user.info.controller;

import com.adrug.erp.common.result.Result;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.user.info.model.dto.EmployeeSaveDTO;
import com.adrug.erp.user.info.model.query.EmployeeQuery;
import com.adrug.erp.user.info.model.vo.EmployeeVO;
import EmployeeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 员工信息 Controller。
 * <p>
 * URL 路径与 {@code EmployeeFeign}（OpenFeign）保持一致。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@RestController
@RequestMapping("/user/info/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/page")
    public Result<PageResult<EmployeeVO>> page(@RequestBody EmployeeQuery query) {
        return Result.success(employeeService.page(query));
    }

    @GetMapping("/{id}")
    public Result<EmployeeVO> getById(@PathVariable("id") Long id) {
        return Result.success(employeeService.getById(id));
    }

    @PostMapping("/listByIds")
    public Result<List<EmployeeVO>> listByIds(@RequestBody List<Long> ids) {
        return Result.success(employeeService.getByIds(ids));
    }

    @PostMapping
    public Result<Long> create(@RequestBody EmployeeSaveDTO dto) {
        return Result.success(employeeService.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody EmployeeSaveDTO dto) {
        employeeService.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return Result.success();
    }
}
```

## Feign 模板

```java
package com.adrug.erp.user.info;

import com.adrug.erp.common.result.Result;
import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.user.info.model.dto.EmployeeSaveDTO;
import com.adrug.erp.user.info.model.query.EmployeeQuery;
import com.adrug.erp.user.info.model.vo.EmployeeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 员工信息 OpenFeign 客户端，与 Controller 接口联动。
 * <p>
 * 供其它服务 / 客户端（adrug-app-*）跨服务调用；URL 路径须与
 * service 模块的 {@code EmployeeController} 保持一致。
 */
@FeignClient(name = "adrug-user-info", path = "/user/info/employee", contextId = "employeeClient")
public interface EmployeeFeign {

    @PostMapping("/page")
    Result<PageResult<EmployeeVO>> page(@RequestBody EmployeeQuery query);

    @GetMapping("/{id}")
    Result<EmployeeVO> getById(@PathVariable("id") Long id);

    @PostMapping("/listByIds")
    Result<List<EmployeeVO>> listByIds(@RequestBody List<Long> ids);

    @PostMapping
    Result<Long> create(@RequestBody EmployeeSaveDTO dto);

    @PutMapping("/{id}")
    Result<Void> update(@PathVariable("id") Long id, @RequestBody EmployeeSaveDTO dto);

    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
}
```

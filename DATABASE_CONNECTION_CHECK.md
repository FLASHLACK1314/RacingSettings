# 数据库连接检查功能说明

## 功能概述

本项目已添加数据库连接优先检查功能，在应用启动时会首先验证数据库连接是否可用。如果数据库连接失败，应用将拒绝启动并提供详细的错误信息。

## 执行顺序

应用启动时的初始化顺序：

```
@Order(0) - DatabaseConnectionChecker  (数据库连接检查)
    ↓
@Order(1) - DatabaseInitializer        (创建数据库表结构)
    ↓
@Order(2) - Initializer                (初始化基础数据和管理员用户)
```

## 检查内容

### 成功连接时的输出

```
开始检查数据库连接...
✓ 数据库连接成功！
数据库信息: jdbc:postgresql://localhost:5432/racingSettings
数据库类型: PostgreSQL
数据库版本: 14.x
```

### 连接失败时的输出

```
================================================================================
数据库连接错误详情:
错误代码: 0
SQL状态: 08001
错误信息: Connection refused: connect
================================================================================
请检查以下配置:
1. 数据库服务是否已启动
2. application-dev.yaml 中的数据库连接配置是否正确
3. 数据库用户名和密码是否正确
4. 数据库名称是否存在
5. 数据库端口是否正确 (默认PostgreSQL: 5432)
================================================================================
```

应用将抛出运行时异常并终止启动。

## 测试方法

### 测试场景1: 数据库未启动

1. 停止PostgreSQL服务
2. 运行应用：`./mvnw spring-boot:run`
3. 预期结果：应用启动失败，显示详细错误信息

### 测试场景2: 数据库配置错误

1. 修改 `application-dev.yaml` 中的数据库配置（如错误的端口或用户名）
2. 运行应用：`./mvnw spring-boot:run`
3. 预期结果：应用启动失败，显示配置错误提示

### 测试场景3: 数据库正常连接

1. 确保PostgreSQL服务正在运行
2. 确保 `application-dev.yaml` 配置正确
3. 运行应用：`./mvnw spring-boot:run`
4. 预期结果：应用正常启动，显示数据库连接成功信息

## 配置示例

确保 `application-dev.yaml` 配置正确：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/racingSettings
    username: racingSettings
    password: 123456
    driver-class-name: org.postgresql.Driver
```

## 常见错误及解决方案

### 错误1: Connection refused

**原因**: 数据库服务未启动或端口错误

**解决方案**: 
- 启动PostgreSQL服务
- 检查端口配置是否为5432

### 错误2: database "racingSettings" does not exist

**原因**: 数据库不存在

**解决方案**: 
```sql
CREATE DATABASE "racingSettings";
```

### 错误3: password authentication failed

**原因**: 用户名或密码错误

**解决方案**: 
- 检查 application-dev.yaml 中的用户名和密码
- 确保数据库用户已创建并授予权限

### 错误4: role "racingSettings" does not exist

**原因**: 数据库用户不存在

**解决方案**: 
```sql
CREATE USER "racingSettings" WITH PASSWORD '123456';
GRANT ALL PRIVILEGES ON DATABASE "racingSettings" TO "racingSettings";
```

## 实现细节

数据库连接检查器位于：
```
src/main/java/com/flashlack/homeofesportsracingsimulatorsettings/config/Initializer/DatabaseConnectionChecker.java
```

该组件使用 `@Order(0)` 注解确保在所有其他初始化组件之前执行，保证数据库连接可用后才进行后续初始化操作。

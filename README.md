# Home of eSports Racing Simulator Settings

一个专为赛车模拟器电竞设置管理而设计的后端服务系统。

## 项目概述

本项目是一个基于Spring Boot的后端服务，专门用于管理赛车模拟器的各种设置配置，包括车辆设置、比赛设置、教学设置等功能。

## 技术栈

- **后端框架**: Spring Boot
- **数据库**: SQL数据库
- **构建工具**: Maven
- **认证方式**: JWT Token
- **缓存**: Redis
- **数据库操作**: MyBatis Plus

## 项目结构

```
src/
├── main/
│   ├── java/com/flashlack/homeofesportsracingsimulatorsettings/
│   │   ├── config/          # 配置类
│   │   ├── controller/      # 控制器层（v1, v2, v3版本）
│   │   ├── dao/            # 数据访问对象
│   │   ├── logic/          # 业务逻辑层
│   │   ├── mapper/         # MyBatis映射器
│   │   ├── model/          # 数据模型（DTO, Entity, VO）
│   │   ├── service/        # 服务层
│   │   └── util/           # 工具类
│   └── resources/
│       ├── application-dev.yaml
│       └── templates/      # SQL模板文件
└── test/                   # 测试代码
```

## 核心功能模块

### 1. 设置管理 (Settings)
- **车辆设置** (SettingsCar): 管理赛车的各项参数配置
- **游戏设置** (SettingsGame): 管理游戏相关的基础配置
- **比赛设置** (SettingsMatch): 管理比赛规则和参数
- **调校设置** (SettingsSetups): 管理车辆调校方案
- **教学设置** (SettingsTeaching): 管理教学内容和配置
- **赛道设置** (SettingsTrack): 管理赛道信息和配置

### 2. 用户管理 (User)
- 用户注册/登录
- 角色权限管理
- JWT身份认证

### 3. 邮件服务 (Mail)
- 邮件验证码发送
- 邮件模板管理

### 4. 推荐系统 (RecommendSetups)
- 智能推荐调校方案
- 个性化设置建议

### 5. 比赛管理 (Match)
- 比赛创建和管理
- 比赛数据统计

### 6. 教学系统 (Teaching)
- 教学内容管理
- 学习进度跟踪

## API版本控制

项目支持多版本API：
- `v1/`: 基础版本API
- `v2/`: 增强版本API  
- `v3/`: 最新版本API

## 数据库设计

项目包含以下主要数据表：
- `email_code`: 邮箱验证码
- `settings_car`: 车辆设置
- `settings_game`: 游戏设置
- `settings_match`: 比赛设置
- `settings_role`: 角色权限
- `settings_setups`: 调校方案
- `settings_teaching`: 教学设置
- `settings_track`: 赛道设置
- `settings_user`: 用户信息
- `system_constants`: 系统常量

## 安装与运行

### 环境要求
- JDK 8+
- Maven 3.6+
- MySQL/其他SQL数据库
- Redis

### 启动步骤

1. 克隆项目
```bash
git clone [repository-url]
cd RacingSettings
```

2. 配置数据库
```bash
# 创建数据库并执行src/main/resources/templates/下的SQL文件
```

3. 修改配置文件
```yaml
# 编辑src/main/resources/application-dev.yaml
# 配置数据库连接、Redis连接等信息
```

4. 启动项目
```bash
mvn spring-boot:run
```

## 主要特性

- **多版本API支持**: 向前兼容的API版本管理
- **JWT认证**: 安全的用户身份验证机制
- **Redis缓存**: 高性能数据缓存
- **邮件服务**: 完整的邮件发送功能
- **异常处理**: 统一的异常处理机制
- **MyBatis Plus**: 简化的数据库操作
- **分页支持**: 自定义分页功能

## 贡献

欢迎提交Issue和Pull Request来帮助改进项目。

## 许可证
MIT License

这份文档涵盖了项目的主要功能模块、技术栈、项目结构和使用说明，适合作为GitHub项目的README文档。

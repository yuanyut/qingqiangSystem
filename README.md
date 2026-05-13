# 秦腔宣传系统

一个用于秦腔文化宣传的现代化管理系统，包含用户前台展示和管理后台两大模块。

## 项目简介

本系统分为三个部分：

| 模块 | 端口 | 说明 |
|------|------|------|
| **client** | 5173 | 用户前台，展示秦腔资讯、剧目、名家等内容 |
| **manage** | 5174 | 管理后台，用于内容管理、用户管理、数据分析等 |
| **server** | 8081 | 后端 API 服务，提供数据接口支持 |

## 技术栈

### 前端
- **Vue 3** + TypeScript + Composition API
- **Vue Router** 5.x（路由管理）
- **Pinia**（状态管理）
- **Element Plus**（UI 组件库）
- **ECharts**（数据可视化，仅 manage）
- **Axios**（HTTP 请求）

### 后端
- **Spring Boot** 3.2.5
- **Spring Security**（安全认证）
- **MyBatis**（数据库持久化）
- **MySQL**（数据库）
- **JWT**（身份认证）

## 软件架构

```
┌─────────────────────────────────────────────────────────────┐
│                         前端                                │
│  ┌─────────────────┐              ┌─────────────────┐     │
│  │     client      │              │     manage       │     │
│  │   用户前台      │              │   管理后台       │     │
│  │   :5173         │              │   :5174         │     │
│  └────────┬────────┘              └────────┬────────┘     │
│           │                                │              │
└───────────┼────────────────────────────────┼──────────────┘
            │                                │
            └──────────────┬─────────────────┘
                           │
                    ┌───────▼───────┐
                    │    server     │
                    │   后端 API    │
                    │   :8081      │
                    └───────┬───────┘
                            │
                    ┌───────▼───────┐
                    │    MySQL      │
                    │   数据库      │
                    └───────────────┘
```

## 功能模块

### 用户前台（client）

| 模块 | 功能 |
|------|------|
| 首页 |  banner 展示、热门推荐 |
| 剧目 | 秦腔剧目列表、详情浏览 |
| 名家 | 秦腔名家介绍、艺术家详情 |
| 资讯 | 最新资讯、新闻动态 |
| 知识 | 秦腔文化知识科普 |
| 个人中心 | 用户登录、收藏、历史记录、评论管理 |

### 管理后台（manage）

| 模块 | 功能 |
|------|------|
| 数据大屏 | 系统数据可视化展示 |
| 统计分析 | 剧目热度分析、用户画像 |
| 系统管理 | 用户管理 |
| 内容管理 | 剧目、名家、资讯、文化等内容的增删改查 |
| 评论审核 | 用户评论管理与审核 |

### 后端接口（server）

提供完整的 RESTful API，包括：

- 用户认证：`/user/login`, `/user/register`, `/admin/login`
- 内容接口：`/drama/list`, `/actor/list`, `/news/list`, `/culture/list`
- 管理接口：`/admin/*`（需管理员权限）
- 推荐接口：`/recommend/home`

## 快速开始

### 环境要求

- **Node.js** >= 20.19.0
- **Java** 17+
- **Maven** 3.6+
- **MySQL** 8.0+

### 1. 克隆项目

```bash
git clone <项目地址>
cd qin-opera-promotion-system
```

### 2. 配置数据库

创建 MySQL 数据库并导入 SQL 文件，然后修改 `server/serve/src/main/resources/application.yml` 中的数据库配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/你的数据库名
    username: 你的用户名
    password: 你的密码
```

### 3. 启动后端服务

```bash
cd server/serve
mvn spring-boot:run
```

后端服务将运行在 `http://localhost:8081`

### 4. 启动管理后台

```bash
cd manage
npm install
npm run dev
```

管理后台将运行在 `http://localhost:5174`

### 5. 启动用户前台

```bash
cd client
npm install
npm run dev
```

用户前台将运行在 `http://localhost:5173`

## 用户角色权限

系统支持四种用户角色：

| 角色 | 说明 | 可访问模块 |
|------|------|----------|
| admin | 管理员 | 全部功能 |
| editor | 编辑 | 内容管理 |
| viewer | 访客 | 部分内容查看 |
| user | 普通用户 | 前台功能 |

## API 文档

### 公共接口（无需登录）

```
GET  /drama/list          剧目列表
GET  /drama/detail/{id}   剧目详情
GET  /actor/list          名家列表
GET  /actor/detail/{id}   名家详情
GET  /news/list           资讯列表
GET  /news/detail/{id}    资讯详情
GET  /culture/list        文化列表
GET  /recommend/home      首页推荐
```

### 需要登录的接口

```
POST /user/login          用户登录
POST /user/register       用户注册
POST /user/collect         添加收藏
GET  /user/collect/list   收藏列表
POST /comment/add         添加评论
```

### 管理接口（需 admin/editor 角色）

```
POST /admin/drama/add     添加剧目
PUT  /admin/drama/edit    编辑剧目
DELETE /admin/drama/{id}  删除剧目
... 其他管理接口
```

## 项目结构

```
qin-opera-promotion-system/
├── client/                    # 用户前台
│   ├── src/
│   │   ├── api/              # API 接口定义
│   │   ├── components/       # 公共组件
│   │   ├── layouts/         # 页面布局
│   │   ├── router/          # 路由配置
│   │   ├── stores/          # Pinia 状态管理
│   │   ├── types/           # TypeScript 类型定义
│   │   ├── views/           # 页面视图
│   │   └── App.vue
│   └── vite.config.ts
│
├── manage/                    # 管理后台
│   ├── src/
│   │   ├── api/             # API 接口定义
│   │   ├── components/       # 公共组件
│   │   │   ├── Analysis/    # 数据分析组件
│   │   │   ├── Contents/    # 内容管理组件
│   │   │   └── charts/      # ECharts 图表组件
│   │   ├── layouts/         # 页面布局
│   │   ├── router/          # 路由配置
│   │   ├── stores/          # Pinia 状态管理
│   │   ├── types/           # TypeScript 类型定义
│   │   ├── view/           # 页面视图
│   │   └── App.vue
│   └── vite.config.ts
│
└── server/                    # 后端服务
    └── serve/
        └── src/main/java/com/qqsystem/serve/
            ├── controller/   # 控制器
            ├── service/      # 业务逻辑
            ├── mapper/       # 数据访问
            ├── entity/       # 实体类
            ├── dto/          # 数据传输对象
            └── config/       # 配置类
```

## 开发指南

### 添加新的 API 接口

1. **后端**：在对应的 Controller 中添加接口方法
2. **前端**：在 `api/` 目录下添加接口调用方法

### 添加新的页面

1. **创建视图组件**：在 `views/` 目录下创建 `.vue` 文件
2. **配置路由**：在 `router/index.ts` 中添加路由规则

## License

MIT License

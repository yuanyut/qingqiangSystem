# 生产环境部署说明

## 环境变量配置

在生产环境中，可以通过环境变量或启动参数配置以下内容：

### 必需配置

| 环境变量 | 说明 | 默认值 |
|---------|------|--------|
| `DB_URL` | 数据库连接URL | `jdbc:mysql://localhost:3306/qinqiang` |
| `DB_USERNAME` | 数据库用户名 | `root` |
| `DB_PASSWORD` | 数据库密码 | `your_password` |
| `UPLOAD_PATH` | 文件上传路径 | `./upload` |

### 可选配置

| 环境变量 | 说明 | 默认值 |
|---------|------|--------|
| `APP_DOMAIN` | 应用域名（用于返回完整URL） | `http://localhost:8081` |

## 启动命令

### 方式一：使用环境变量

```bash
# Linux/Mac
export DB_URL="jdbc:mysql://your-db-host:3306/qinqiang"
export DB_USERNAME="your_username"
export DB_PASSWORD="your_password"
export UPLOAD_PATH="/var/www/upload"
export APP_DOMAIN="https://your-domain.com"

java -jar serve.jar --spring.profiles.active=prod
```

```powershell
# Windows PowerShell
$env:DB_URL="jdbc:mysql://your-db-host:3306/qinqiang"
$env:DB_USERNAME="your_username"
$env:DB_PASSWORD="your_password"
$env:UPLOAD_PATH="C:\upload"
$env:APP_DOMAIN="https://your-domain.com"

java -jar serve.jar --spring.profiles.active=prod
```

### 方式二：使用启动参数

```bash
java -jar serve.jar \
  --spring.profiles.active=prod \
  --DB_URL="jdbc:mysql://your-db-host:3306/qinqiang" \
  --DB_USERNAME="your_username" \
  --DB_PASSWORD="your_password" \
  --UPLOAD_PATH="/var/www/upload" \
  --APP_DOMAIN="https://your-domain.com"
```

### 方式三：使用配置文件

修改 `application-prod.yml` 文件中的配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://your-db-host:3306/qinqiang
    username: your_username
    password: your_password

app:
  domain: https://your-domain.com
  upload:
    path: /var/www/upload
```

## 文件上传目录权限

确保上传目录有正确的读写权限：

```bash
# Linux/Mac
mkdir -p /var/www/upload/avatar
mkdir -p /var/www/upload/actor
mkdir -p /var/www/upload/drama
mkdir -p /var/www/upload/culture
chmod -R 755 /var/www/upload
```

```powershell
# Windows
mkdir C:\upload\avatar
mkdir C:\upload\actor
mkdir C:\upload\drama
mkdir C:\upload\culture
```

## 前端配置

### Client 端

修改 `client/vite.config.ts` 中的代理配置：

```typescript
server: {
  proxy: {
    '/upload': {
      target: 'https://your-domain.com',
      changeOrigin: true,
    },
    // ... 其他代理配置
  },
}
```

### Manage 端

修改 `manage/vite.config.ts` 中的代理配置：

```typescript
server: {
  proxy: {
    '/upload': {
      target: 'https://your-domain.com',
      changeOrigin: true,
    },
    // ... 其他代理配置
  },
}
```

## Nginx 配置示例

如果使用 Nginx 作为反向代理，配置如下：

```nginx
server {
    listen 80;
    server_name your-domain.com;

    # 后端 API 代理
    location /api/ {
        proxy_pass http://localhost:8081/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    # 文件上传资源代理
    location /upload/ {
        proxy_pass http://localhost:8081/upload/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    # 前端静态文件
    location / {
        root /var/www/client/dist;
        try_files $uri $uri/ /index.html;
    }

    # 管理后台
    location /manage/ {
        root /var/www/manage/dist;
        try_files $uri $uri/ /manage/index.html;
    }
}
```

## Docker 部署

### Dockerfile 示例

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY serve.jar app.jar

# 创建上传目录
RUN mkdir -p /app/upload/avatar \
    && mkdir -p /app/upload/actor \
    && mkdir -p /app/upload/drama \
    && mkdir -p /app/upload/culture

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
```

### docker-compose.yml 示例

```yaml
version: '3.8'

services:
  serve:
    build: .
    ports:
      - "8081:8081"
    environment:
      - DB_URL=jdbc:mysql://mysql:3306/qinqiang
      - DB_USERNAME=root
      - DB_PASSWORD=your_password
      - UPLOAD_PATH=/app/upload
      - APP_DOMAIN=https://your-domain.com
    volumes:
      - ./upload:/app/upload
    depends_on:
      - mysql

  mysql:
    image: mysql:8.0
    environment:
      - MYSQL_ROOT_PASSWORD=your_password
      - MYSQL_DATABASE=qinqiang
    volumes:
      - mysql-data:/var/lib/mysql

volumes:
  mysql-data:
```

## 常见问题

### 1. 文件上传后无法访问

- 检查上传目录权限是否正确
- 检查 `UPLOAD_PATH` 环境变量是否配置正确
- 检查 Nginx/代理配置是否正确

### 2. 图片显示 404

- 检查静态资源映射配置
- 检查文件是否真的存在于上传目录
- 检查 URL 路径是否正确（应该是 `/upload/xxx/xxx.jpg`）

### 3. 跨域问题

- 检查 `WebMvcConfig` 中的 CORS 配置
- 检查前端代理配置是否正确
- 生产环境建议使用 Nginx 反向代理，避免跨域
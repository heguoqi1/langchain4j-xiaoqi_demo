# 智能小奇前端项目

基于 Vue 3 + Vite 的智能小奇前端应用，采用前后端分离架构。

## 技术栈

- Vue 3
- Vite
- Axios
- 原生 Fetch API (用于流式响应)

## 项目结构

```
ai_xiaoqi_html/
├── src/
│   ├── App.vue          # 主应用组件
│   └── main.js          # 入口文件
├── index.html           # HTML 模板
├── package.json         # 项目依赖配置
├── vite.config.js       # Vite 配置（包含代理设置）
└── .gitignore          # Git 忽略文件
```

## 功能特性

- 实时流式对话
- 美观的聊天界面
- 自动滚动到最新消息
- 打字状态提示
- 响应式设计

## 启动步骤

### 1. 安装依赖

```bash
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

前端服务将在 `http://localhost:3000` 启动

### 3. 启动后端服务

确保后端服务运行在 `http://localhost:8080`

## API 接口

### 对话接口

- **URL**: `/xiaoqi/chat`
- **方法**: POST
- **Content-Type**: application/json
- **请求体**:
```json
{
  "memoryId": 1234567890,
  "userMassage": "你好"
}
```
- **响应**: text/stream;charset=utf-8 (流式响应)

## 跨域配置

前端通过 Vite 代理配置实现跨域：

```javascript
server: {
  port: 3000,
  proxy: {
    '/xiaoqi': {
      target: 'http://localhost:8080',
      changeOrigin: true,
      secure: false
    }
  }
}
```

后端已配置 CORS 支持，允许所有来源的跨域请求。

## 构建生产版本

```bash
npm run build
```

构建产物将输出到 `dist` 目录。

## 预览生产版本

```bash
npm run preview
```

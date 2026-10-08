# ai_qi（智能小奇）

基于 **LangChain4j + Spring Boot 3** 的「智能小奇」医疗预约助手，集成大模型对话、流式输出、向量检索（RAG）、MongoDB 记忆持久化与 MySQL 预约管理，前端配套 Vue 3 项目，支持对话式完成科室预约。

#### 项目简介

「智能小奇」是一个面向医院挂号场景的智能客服：

- 用户以自然语言与 AI 助手对话，提出挂号 / 取消 / 查询号源等需求。
- 大模型理解意图后，自动调用后端工具（`AppointmentTools`）完成真实的数据库操作。
- 对话支持流式响应，会话记忆通过 MongoDB 持久化，支持多轮上下文。
- 内置 RAG（向量检索）能力，用于从知识库中检索医生信息。

#### 技术架构

后端：

| 模块 | 技术 |
|------|------|
| Web | Spring Boot 3.2.6、Spring WebFlux（流式输出） |
| AI 框架 | LangChain4j 1.0.0-beta3（open-ai / community-dashscope） |
| 大模型 | 阿里云百炼（qwen-max、qwen-plus 流式、text-embedding-v3）<br>兼容 DeepSeek（OpenAI 协议，配置已预留） |
| 向量库 | Pinecone（langchain4j-pinecone） |
| 持久层 | MyBatis-Plus 3.5.11 + MySQL |
| 会话记忆 | Spring Data MongoDB |
| 接口文档 | Knife4j 4.3.0（Swagger3） |

前端：`ai_xiaoqi_html/` —— Vue 3 + Vite + Axios，详细说明见该目录下 `README.md`。

后端目录结构：

```
src/main/java/com/hgq/javaailangchain4j/
├── JavaAiLangchain4jApplication.java   # 启动类
├── assistant/XiaoqiAgent.java          # LangChain4j Agent（工具调用 + RAG + 流式）
├── config/
│   ├── XiaoqiAgentConfig.java          # Agent / 模型 / 记忆 / RAG 配置
│   └── CorsConfig.java                 # 跨域
├── controller/XiaoqiController.java    # /xiaoqi/chat 流式接口
├── service / mapper / entity           # 预约业务（MyBatis-Plus）
├── store/MongoChatMemoryStore.java     # MongoDB 会话记忆
└── tools/AppointmentTools.java        # 大模型可调用的工具方法
```

#### 环境要求

- JDK 17+
- Maven 3.8+
- Node.js 18+（运行前端）
- MySQL 8.x（库名 `ai_xiaoqi`）
- MongoDB 4.x+（默认 `mongodb://localhost:27017/chat_memory_db`）
- 阿里云百炼 `DASH_SCOPE_API_KEY`（必填）；如使用 DeepSeek 则填 `DEEP_SEEK_API_KEY`

#### 安装与启动

1. **启动 MySQL、MongoDB**，导入业务表结构（参见 `Appointment` 实体与 `mapper/AppointmentMapper.xml`）。
2. **配置环境变量**：
   ```bash
   # Windows PowerShell
   $env:DASH_SCOPE_API_KEY="sk-..."
   # 或系统环境变量
   ```
3. **启动后端**：
   ```bash
   mvn spring-boot:run
   ```
   服务监听 `http://localhost:8080`，Knife4j 文档：`/doc.html`。
4. **启动前端**：
   ```bash
   cd ai_xiaoqi_html
   npm install
   npm run dev
   ```
   访问 `http://localhost:3000`，前端已配置代理转发 `/xiaoqi` 到 `8080`。

#### 接口说明

对话接口（流式输出，SSE-like）：

- `POST /xiaoqi/chat`
- Content-Type：`application/json`
- 请求体：
  ```json
  { "memoryId": 1234567890, "userMassage": "帮我挂一个明天下午的心内科号" }
  ```
- 响应：`text/stream;charset=utf-8`

`memoryId` 为会话隔离键，相同 memoryId 的多轮对话共享上下文，记忆落库 MongoDB。

#### 功能特性

- 多模型支持：阿里云百炼（默认）+ DeepSeek（OpenAI 协议，配置已注释预留）
- 流式响应（WebFlux + Reactor）
- 工具调用（Function Calling）：预约挂号 / 取消预约 / 查询号源
- RAG：基于 Pinecone 的医生知识检索
- 会话记忆：MongoDB 持久化，支持多 memoryId 隔离
- Knife4j 接口文档

#### 参与贡献

1. Fork 本仓库
2. 新建 `Feat_xxx` 分支
3. 提交代码
4. 新建 Pull Request

#### 许可证

仅供学习交流使用。
# AI角色扮演 说明文档
## 1️⃣ 网页面向的用户 & 设想的用户故事

**目标用户：**

- 喜欢在学习、心理陪伴、职业指导等场景中与虚拟角色进行互动的普通用户
- 需要练习沟通、表达、辩论技巧的学生或职场人士
- 需要定制特定角色人格与语气风格来进行问答、陪伴或训练的企业客户

**痛点：**

- 现有 AI 聊天大多是“同质化”对话，缺少人物设定、技能、语气等个性化特征
- 用户需要自己反复提示，才能引导出所需角色语气，体验不连贯
- 部分用户更希望“听到”AI声音，而不仅仅是文字

**用户故事：**

- 小李是一名心理学专业学生，需要一位“苏格拉底式”提问的 AI 辅助练习反问引导技巧
- 小张是一名英语学习者，想要一个“英音口音”的虚拟老师进行语音对话
- 企业 HR 想快速搭建一个“面试官 AI”用来训练候选人

## 2️⃣ 网页需要的功能 & 优先级

| **功能**         | **说明**                               | **优先级**    |
| ---------------- | -------------------------------------- | ------------- |
| 用户注册 / 登录  | 支持用户名、邮箱注册，JWT 登录         | ★★★★☆（核心） |
| 角色设定输入     | 用户可自定义“角色名”、“技能/风格”      | ★★★★☆         |
| 文本聊天         | 用户输入文字，AI 按角色回复            | ★★★★☆         |
| 语音输入 (ASR)   | 用户可以用语音说出内容，后台识别成文字 | ★★★☆☆         |
| 语音输出 (TTS)   | AI 回复可合成为语音播放                | ★★★☆☆         |
| 会话记录保存     | 支持多轮对话、历史查看                 | ★★★☆☆         |
| 用户资料管理     | 修改头像、昵称等                       | ★★☆☆☆         |
| 后台管理（可选） | 角色库、日志、统计                     | ★☆☆☆☆         |

## 3️⃣ 采纳公司的 LLM 模型能力 & 选择的理由

对比后选择使用 **阿里云 DashScope / 通义千问（Qwen）** 作为后端大模型：

**理由：**

- 支持中文场景良好，角色扮演效果突出
- 有稳定的企业 API 服务和计费方式
- 支持多模态（文本、语音）能力，可与 ASR / TTS 结合
- 响应延迟、吞吐量在国内访问更优

## 4️⃣ 期望 AI 角色除了语音聊天外的技能

- **多轮上下文记忆**：能记住对话上下文，实现连续多轮的角色扮演
- **多角色切换**：用户可一键切换不同预设角色人格
- **知识增强**：能调用外部知识库、文件、API，提供更准确答案
- **情绪 / 语气控制**：根据用户需求调整语气（温柔、严肃、幽默等）
- **多语言能力**：可用中英双语或其他语言交流
- **任务执行**：不仅聊天，还能辅助写作、规划、批改、问答



---



# AI 角色扮演 项目文档
这是一个基于 Spring Boot + MyBatis + MySQL 后端、Vue3 + Element Plus 前端的 AI 角色扮演 Demo。

目前支持：

- 用户注册 / 登录（JWT）
- 前端输入角色、技能、文字与 AI 对话
- 后端返回 AI 回复

---

## 📂 仓库地址

[https://github.com/BillyNanLu/AICharacterRole-playing](https://github.com/BillyNanLu/AICharacterRole-playing)

## 🛠 技术栈

| **模块** | **技术**                                                    |
| -------- | ----------------------------------------------------------- |
| 后端     | Spring Boot 3.x、MyBatis、MySQL、JWT、Redis（可选）、Lombok |
| 前端     | Vue3、Vite、Element Plus、Axios                             |
| 开发工具 | IDEA / VSCode                                               |

## 🗂 后端技术栈

```bash
src/main/java/com/nan19/roleplaying
│
├── controller
│   ├── UserController.java     # 用户注册/登录接口
│   └── ChatController.java     # AI文本聊天接口
│
├── service
│   ├── UserService.java
│   └── impl/UserServiceImpl.java
│
├── mapper
│   └── UserMapper.java
│
├── pojo
│   ├── User.java
│   └── Conversation.java  # 会话表（预留）
│
└── util
    └── JwtUtil.java        # JWT生成工具
```

配置文件 application.yml

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/roleplaying?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
    username: root
    password: ****
mybatis:
  configuration:
    map-underscore-to-camel-case: true
```

主要接口：

- POST /api/users/register

  请求：{username, password, email}

  响应：用户信息

- POST /api/users/login

  请求：{username, password}

  响应：{token, user}

- POST /api/chat/text

  请求：{role, skill, userText}

  响应：{assistantText}

## 🗂 前端结构

```bash
src/
├── api/
│   └── user.js        # registerUser / loginUser 封装
│
├── views/
│   ├── Login.vue
│   ├── Register.vue
│   └── Home.vue       # 角色扮演页面
│
├── router/
│   └── index.js       # 路由配置
│
└── App.vue
```

Element Plus 引入(main.js)

```bash
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

createApp(App).use(router).use(ElementPlus).mount('#app')
```

## 🚀 启动方式

###  1️⃣ 前端

```bash
cd frontend
npm install
npm run dev
```

### **2️⃣ **后端

```bash
# IDEA里运行 RoleplayingApplication
mvn spring-boot:run
```

前端默认 http://localhost:5173

后端默认 http://localhost:8080



---



## **👤 作者信息**

- 作者：Billy Nan Lu
- 本项目所有功能均由作者独立完成
- 仓库地址：https://github.com/BillyNanLu/AICharacterRole-playing


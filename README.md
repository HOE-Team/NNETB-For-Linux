<div align="center" class="logo-container">
   <img height="50" src="images/hoe-team.png" alt="HOE-Team徽标">
   <img height="50" src="images/PLAN_NO.png" alt="PLAN-NO徽标">
</div>

<div align="center" class="NPL text">
   <p>此项目属于 HOE Team PLAN NO 计划 <a href="https://github.com/HOE-Team/.github/blob/main/profile/docs/INTRODUCE_PLAN_NO.md">了解更多[↗]</a></p>
</div>

<h1 align="center">NNETB's Not Everything Toolbox For Linux</h1>
<h2 align="center">非万！ - Linux fork</h2>

<div align="center">
    <img width="150" src="images/logo.png" alt="项目Logo">
</div>

<h4 align="center">一款为Linux平台设计的简约高效、技术激进的实验性工具箱</h4>

<div align="center">

[![Stars](https://img.shields.io/github/stars/HOE-Team/NNETB-For-Linux?style=for-the-badge&logo=data:image/svg%2bxml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEiIHdpZHRoPSIxNiIgaGVpZ2h0PSIxNiI+PHBhdGggZD0iTTggLjI1YS43NS43NSAwIDAgMSAuNjczLjQxOGwxLjg4yAzLjgxNSA0LjIxLjYxMmEuNzUuNzUgMCAwIDEgLjQxNiAxLjI3OWwtMy4wNDYgMi45Ny43MTkgNC4xOTJhLjc1MS43NTEgMCAwIDEtMS4wODguNzkxTDggMTIuMzQ3bC0zLjc2NiAxLjk4YS43NS43NSAwIDAgMS0xLjA4OC0uNzlsLjcyLTQuMTk0TC44MTggNi4zNzRhLjc1Ljc1IDAgMCAxIC40MTYtMS4yOGw0LjIxLS42MTFMNy4zMjcuNjY4QS43NS43NSAwIDAgMSA4IC4yNVoiIGZpbGw9IiNlYWM1NGYiLz48L3N2Zz4=&logoSize=auto&label=Stars&labelColor=444444&color=eac54f)](https://github.com/HOE-Team/NNETB-For-Linux)
[![LICENSE](https://img.shields.io/github/license/HOE-Team/NNETB-For-Linux?style=for-the-badge)](https://github.com/HOE-Team/NNETB-For-Linux/blob/main/LICENSE)
[![GitHub Release](https://img.shields.io/github/v/release/HOE-Team/NNETB-For-Linux?label=Release&logo=github&style=for-the-badge)](https://github.com/HOE-Team/NNETB-For-Linux/releases)

</div>

---

## 📋 项目状态

> [!IMPORTANT]
> 这是一个**实验性项目**，采用激进的技术栈（Kotlin + CMP），现在已经进入**终结性**维护，将**不再**受理Pull Request和Feature Issue。

## 📑 目录

- [📋 项目状态](#-项目状态)
- [✨ 特性](#-特性)
- [🖥️ 系统要求](#️-系统要求)
- [🚀 安装和运行](#-安装和运行)
- [📦 JAR分发说明](#-jar分发说明)
- [🤝 如何贡献](#-如何贡献)
- [📁 项目结构](#-项目结构)
- [🔗 技术栈](#-技术栈)
- [📜 版权与许可证](#-版权与许可证)

## ✨ 特性

* **完全Linux适配**：专为Linux平台设计，支持主流Linux发行版
* **包管理器集成**：自动检测系统包管理器（APT、DNF、PACMAN、ZYPPER、EMERGE、NIX）
* **现代UI**：采用 Material Design 3 设计风格
* **技术激进**：使用 Kotlin + Compose Multiplatform 跨平台框架
* **代码透明**：完全开源，可供任何人审计
* **智能环境检测**：非Linux环境显示全局警告

## 🖥️ 系统要求

* **操作系统**：Linux（推荐Arch、Debian/Ubuntu、Fedora、openSUSE等主流发行版，Linux Kernel 4.x+）
> [!WARNING]
> 本程序专为Linux平台设计，在非Linux系统上运行时功能受限（会显示警告）。Windows平台用户请访问 [NNETB's Not Everything Toolbox[↗]](https://github.com/HOE-Team/NNETB) 仓库。

* **Java环境**：Java 21 或更高版本
* **网络**：需要稳定网络连接（用于包管理器操作）
* **权限**：部分功能（如包管理器安装）需要sudo权限

## 🚀 安装和运行

### 方法1：直接运行JAR文件
1. 确保已安装Java 21或更高版本
2. 从本项目的 [Releases](https://github.com/HOE-Team/NNETB-For-Linux/releases) 页面下载 `NNETB-all.jar`
3. 运行应用程序：
   ```bash
   java -jar NNETB-all.jar
   ```

### 方法2：从源代码构建
```bash
# 克隆仓库
git clone https://github.com/HOE-Team/NNETB-For-Linux.git
cd NNETB-For-Linux

# 构建可执行JAR
./gradlew fatJar

# 运行应用程序
java -jar build/libs/NNETB-all.jar
```

## 📦 JAR分发说明

本项目使用JAR分发，具有以下优势：
- **跨平台**：可在任何支持Java的Linux系统上运行
- **便携性**：单个JAR文件包含所有依赖
- **易于分发**：无需复杂的安装过程
- **包含Linux原生库**：JAR中已包含必要的Linux Skiko库（libskiko-linux-x64.so）

## 🤝 如何贡献

## 🤝 如何贡献
> [!IMPORTANT]
> ### 本项目不再受理新的Pull Request，如果发现了严重Bug，请创建一个Bug [Issue](https://github.com/HOE-Team/NNETB/issues).

## 📁 项目结构

```
NNETB-For-Linux/
├── src/main/kotlin/          # Kotlin源代码
│   ├── components/           # 可复用组件
│   ├── screens/             # 主要界面
│   ├── utils/               # 工具类（包管理器、系统信息等）
│   ├── theme/               # 主题配置
│   └── config/              # 配置类
├── src/main/resources/      # 资源文件
├── build.gradle.kts         # Gradle构建配置
├── run.sh                   # Linux启动脚本
└── README.md               # 项目说明
```

## 🔗 技术栈

| 组件 | 用途 | 开源协议 |
|------|------|------|
| [Kotlin](https://kotlinlang.org/) | 主要编程语言 | Apache 2.0 |
| [Compose Multiplatform](https://kotlinlang.org/compose-multiplatform/) | 跨平台声明式 UI 框架 | Apache 2.0 |
| [OSHI](https://github.com/oshi/oshi) | 操作系统和硬件信息获取 | MIT |

## 📬 联系方式

你可以通过我们的电子邮箱 hoe_software_team@outlook.com 发送邮件联系我们，我们稍后也会开启GitHub Discussion供大家讨论。

如果你觉得本实验对你的项目开发有启发，欢迎给个 Star 支持！

## 📜 版权与许可证

版权所有 © 2026 HOE Team。保留部分权利。

本项目（NNETB-For-Linux）基于 **[GNU GPL v3 许可证](LICENSE)** 开源，是独立于 [NNETB](https://github.com/HOE-Team/NNETB) 原项目（[MIT 许可证](LICENSE-MIT)）的 Linux 平台实验性分支。

> [!NOTE]
> 这份许可证(GNU GPL v3)意味着：
> 
> 你可以自由使用、修改、复制、分发这个项目代码，无论是在个人项目还是商业项目中。 
> 
> 如果你修改并重新发布这个代码，你必须以 GPL v3 许可证公开你的修改后的源代码（即“传染性”开源）。 
> 
> 你可以用它来开发商业软件并销售，但你必须同时以 GPL v3 许可证提供你修改后的完整源代码。 
> 
> 作者不提供任何保证，如果使用该软件导致任何问题，你需要自己承担风险。 

> [!WARNING]
> ### 著作权声明
> NNETB 的徽标为 HOE Team 所有，受法律保护。未经明确书面授权，不得用于商业用途或进行修改后使用。在项目生命周期终结(具体以理事会文件为准)后，本项目 Logo 将使用 CC0-1.0 开放到公共领域。
>
> Linux® 是 Linus Torvalds 在美国和其他国家的注册商标。

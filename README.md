# NeedsOfNature 1.20.1 移植版

数据驱动的 Minecraft Fabric 玩法模组（成人向 / NSFW），依赖动画框架
**Animation Director** 播放多演员动画。

本仓库是 `NeedsOfNature 1.3.1` 的社区非官方 **Minecraft 1.20.1 逆向移植与中文化**，
与前置框架 [Animation Director](../AnimationDirector) 分离为两个独立仓库。

> **内容提示**：本项目及默认内容涉及成人向玩法。仅应在符合法律、平台规则、
> 服务器规则且所有参与者明确同意的环境中使用。

## 模组关系

| 模组 | ID | 角色 |
|------|-----|------|
| Animation Director | `animationframework` | **前置框架**（必须安装） |
| NeedsOfNature（本仓库） | `needsofnature` | **主模组 / 玩法** |

未安装 `animationframework` 时，本模组会拒绝加载。

## 功能概览

- 多演员动画玩法、能量与阶段系统
- 液体 / 瓶装 / 怀孕 / 孵化等玩法系统
- 饰品槽（Trinkets）与饰品效果
- 污渍、破损皮肤动态贴图
- 默认内容包（资源包 + 数据包）加载
- 简体中文界面与配置提示

## 运行环境

### 原生 Fabric

| 项目 | 版本 |
|------|------|
| Minecraft | 1.20.1 |
| Java | 17 |
| Fabric Loader | 0.16.14+ |
| Fabric API | 0.92.9+1.20.1 |
| GeckoLib Fabric | 4.8.4 |
| Trinkets | 3.7.2（可选；默认包 5 件饰品需要） |
| Animation Director | 本系列 1.3.1-port.1 |

### Forge + Sinytra Connector（已测组合）

- Minecraft 1.20.1 / Forge 47.4.21
- Sinytra Connector 1.0.0-beta.49+1.20.1
- Forgified Fabric API 0.92.6+1.11.14+1.20.1
- GeckoLib **Forge** 4.8.4（不要换成 Fabric 版）
- Trinkets 3.7.2（由 Connector 加载）

## 构建

### 1. 先构建前置模组

```powershell
# 在 AnimationDirector 仓库
./gradlew.bat clean build --no-daemon --console=plain
```

### 2. 将前置 JAR 放入本仓库 `libs/`

```text
NeedsOfNature/libs/animationdirector-1.20.1-1.3.1-port.1.jar
```

详见 [libs/README.md](libs/README.md)。

### 3. 构建本模组

```powershell
./gradlew.bat clean build --no-daemon --console=plain
```

产物：

```text
build/libs/needsofnature-1.20.1-1.3.1-port.1.jar
```

## 本地运行与默认内容包

```powershell
./gradlew.bat :runClient
```

将默认内容包：

```text
dev-assets/needsofnature/needs_of_nature_default_packv1.3.0.zip
```

复制到运行目录：

```text
run/needsofnature/
```

默认包不会放在普通 `resourcepacks` 目录；NeedsOfNature 会把它同时作为
客户端资源包和服务端数据包加载。

`dev-assets/config-examples/` 保存了实机测试通过的配置样例。复制前请先备份
自己的 `config/animationframework.json` 与 `config/needsofnature.json`。

## 目录结构

```text
.
├─ src/main/java/com/nonid/     玩法源码（客户端 / 网络 / 实体 / Mixin …）
├─ src/main/resources/          fabric.mod.json、Mixin 配置、assets/data
├─ libs/                        放置 Animation Director 前置 JAR
├─ docs/                        玩法与配置中文文档
├─ dev-assets/                  默认内容包与配置样例
├─ NOTICE                       版权与来源声明
├─ build.gradle
├─ gradle.properties
└─ settings.gradle
```

## 文档

- [快速玩法说明](docs/GAMEPLAY_ZH.md)
- [完整玩法、配置与资源包指南](docs/完整玩法配置与资源包指南_ZH.md)

## 许可与来源

原作者 **L1Z0**。详见 [NOTICE](NOTICE)。

本仓库为非官方移植，不代表原作者。使用与再分发前请遵守原发布页许可、
平台规则及当地法律。

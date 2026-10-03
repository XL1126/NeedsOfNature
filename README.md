# Animation Director（Animation Framework）1.20.1 移植版

Minecraft Fabric 动画框架模组，支持对一个或多个实体/玩家播放 GeckoLib 动画。

本仓库是 `Animation Director 1.3.1` 的社区非官方 **Minecraft 1.20.1 逆向移植**，
独立于玩法模组 [NeedsOfNature](../NeedsOfNature) 发布与构建。

> 内容提示：该框架可被成人向玩法模组使用。请仅在合法、合规、所有参与者明确同意的环境中使用。

## 功能概览

- 数据驱动的 AFW（Animation Framework）动画定义加载
- 多演员（multi-actor）骨骼动画编排与同步
- 玩家/实体模型替换渲染（GeckoLib 4）
- 摄像机约束、输入锁定、动画阶段控制
- 半透明贴图层、发光层、逐骨骼手持道具
- C2S/S2C 动画网络协议
- 配置界面与诊断工具

## 运行环境

| 项目 | 版本 |
|------|------|
| Minecraft | 1.20.1 |
| Java | 17 |
| Fabric Loader | 0.16.14+ |
| Fabric API | 0.92.9+1.20.1 |
| GeckoLib | 4.8.4（Fabric） |

也可在 Forge 47.4.21 + Sinytra Connector 环境加载（需使用 Forge 版 GeckoLib）。

## 构建

```powershell
./gradlew.bat clean build --no-daemon --console=plain
```

产物：

```text
build/libs/animationdirector-1.20.1-1.3.1-port.1.jar
```

## 安装

将上述 JAR 与依赖模组（Fabric API、GeckoLib）一并放入 `mods/`。

若需要完整玩法，请另外安装主模组 **NeedsOfNature** 及其默认内容包。

## 作为开发依赖（给其他模组）

其他模组若需在编译期调用本框架 API：

1. 构建本仓库得到 JAR；
2. 将 JAR 放入目标工程的 `libs/`；
3. 在目标工程 `build.gradle` 中声明，例如：

```gradle
dependencies {
    modImplementation fileTree(dir: 'libs', include: '*animationdirector*.jar')
}
```

运行时仍需把本模组 JAR 装进游戏 `mods/`。

## 目录结构

```text
.
├─ src/main/java/com/afwid/     框架源码（API / 客户端 / 网络 / Mixin）
├─ src/main/resources/          fabric.mod.json、Mixin 配置、assets
├─ NOTICE                       版权与来源声明
├─ build.gradle
├─ gradle.properties
└─ settings.gradle
```

## 与 NeedsOfNature 的关系

| 模组 | 角色 |
|------|------|
| Animation Director（本仓库） | 前置框架 |
| NeedsOfNature | 玩法主模组（硬依赖本框架） |

两者为独立仓库、独立 JAR。NeedsOfNature 通过模组 ID `animationframework` 依赖本仓库产物。

## 许可与来源

原作者 **L1Z0**。详见 [NOTICE](NOTICE)。

本仓库为非官方移植，不代表原作者。使用与再分发前请遵守原发布页许可、平台规则及当地法律。

# NeedsOfNature

Minecraft 1.20.1 社区逆向移植工程仓库，包含两个独立 Fabric 模组，按分支存放。

## 分支说明

| 分支 | 内容 | 角色 |
|------|------|------|
| `main` | 本介绍文件 | 仓库说明 |
| `AnimationDirector` | Animation Director / Animation Framework 项目 | 前置框架模组 |
| `NeedsOfNature` | NeedsOfNature 项目 | 玩法主模组 |

## 模组关系

- **Animation Director**（模组 ID：`animationframework`）为前置框架。
- **NeedsOfNature**（模组 ID：`needsofnature`）为玩法主模组，硬依赖上述框架。

两个模组各自独立构建、独立分发；完整玩法需同时安装两者。

## 运行环境（摘要）

- Minecraft 1.20.1 / Java 17
- Fabric Loader 0.16.14+，或 Forge 47.4.21 + Sinytra Connector
- Fabric API、GeckoLib 4.8.4；饰品功能需 Trinkets 3.7.2

## 如何获取源码

```bash
git clone -b AnimationDirector https://github.com/XL1126/NeedsOfNature.git AnimationDirector
git clone -b NeedsOfNature https://github.com/XL1126/NeedsOfNature.git NeedsOfNature
```

各分支根目录即对应模组的完整工程，详见分支内 `README.md`。

## 内容提示

本项目及默认内容涉及成人向玩法。仅应在符合法律、平台规则、服务器规则且所有参与者明确同意的环境中使用。

## 许可与来源

原模组作者 **L1Z0**。本仓库为社区维护的非官方移植与中文化工程，不代表原作者。  
使用与再分发前请遵守原发布页许可、平台规则及当地法律。详见各模组分支中的 `NOTICE`。

原始发布页：[Minecraft Needs of Nature](https://www.loverslab.com/files/file/49713-minecraft-needs-of-nature-nsfw-mod-data-driven-sex-animation-mod-for-minecraft-fabric-12111/)

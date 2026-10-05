# NeedsOfNature

Minecraft 1.20.1 社区逆向移植工程仓库，包含两个独立 Fabric 模组，按分支存放。

## 作者

- **原作者**：L1Z0（原版 NeedsOfNature 1.3.1 / Animation Director 1.3.1）
- **1.20.1 移植与中文化**：XL1126（小狸）
- 原始发布页：[Minecraft Needs of Nature](https://www.loverslab.com/files/file/49713-minecraft-needs-of-nature-nsfw-mod-data-driven-sex-animation-mod-for-minecraft-fabric-12111/)

本仓库为非官方移植，不代表原作者。使用与再分发请遵守原发布页许可、平台规则及当地法律。

## 分支说明

| 分支 | 内容 | 发布 |
|------|------|------|
| `main` | 本说明与总览 Release | [v1.3.1-port.1](https://github.com/XL1126/NeedsOfNature/releases/tag/v1.3.1-port.1) |
| `AnimationDirector` | 前置框架工程 | [AnimationDirector-v1.3.1-port.1](https://github.com/XL1126/NeedsOfNature/releases/tag/AnimationDirector-v1.3.1-port.1) |
| `NeedsOfNature` | 玩法主模组工程 | [NeedsOfNature-v1.3.1-port.1](https://github.com/XL1126/NeedsOfNature/releases/tag/NeedsOfNature-v1.3.1-port.1) |

## 模组关系

- **Animation Director**（`animationframework`）— 前置动画框架
- **NeedsOfNature**（`needsofnature`）— 玩法主模组，硬依赖上述框架

## 附属 / 依赖模组

| 模组 | 版本 | 必需性 | 用途 |
|------|------|--------|------|
| Fabric API | 0.92.9+1.20.1 | 必需 | 基础 API |
| GeckoLib (Fabric) | 4.8.4 | 必需 | 模型动画 |
| Animation Director | 1.20.1-port.1 | 必需 | 前置框架 |
| Trinkets | 3.7.2 | 可选 | 饰品槽 |
| Wildfire Female Gender Mod | 1.20-3.0.1 | 可选 | 女性胸部模型（NoN 仅同步参数） |
| Mod Menu | 7.2.2 | 可选 | 模组配置界面 |

Forge 侧可用 Sinytra Connector + Forge 版 GeckoLib。

## 相对原仓库 / 原版的主要修改

1. **构建**：拆为两个独立 Gradle 工程；Trinkets/ModMenu 走 Modrinth Maven（原源在本环境不可达）
2. **ModMenu**：恢复 `modmenu` 入口，配置界面可从模组菜单打开
3. **UI（1.20.1）**：所有设置界面补 `renderBackground`（1.20.1 的 `Screen.render` 不自动画背景）；`SettingsList` 滚动条/行高/裁剪修正
4. **性别**：进服选择窗口仅首次弹出；界面文案直白化
5. **语言**：按键分类 `key.categories.animationframework`；药水/箭矢按状态命名（性欲高涨 / 性欲消退 / 易受孕等）；修正「非性别」「香草覆盖」等误译
6. **药水**：注册名 `needsofnature.*`，与语言键对齐
7. **饰品**：标签路径 `tags/item` → `tags/items`（1.20.1）
8. **破损皮肤**：头部（y<16）保留；纤细→`alex_f.png`、粗手→`kai_m.png`；多级读取原皮肤；命令 `/needsofnature skin ripped head on|off`、`/needsofnature skin variant ...`
9. **调试手杖**：补 `models/item/debug_staff.json`
10. **摄像机**：降低动画镜头缩放平滑系数与碰撞阈值，减少抖动

## 内容提示

本项目及默认内容涉及成人向玩法。仅应在符合法律、平台规则、服务器规则且所有参与者明确同意的环境中使用。

## 构建

```bash
# Animation Director
cd AnimationDirector && ./gradlew clean build

# NeedsOfNature（先将 AnimationDirector 的 JAR 放入 libs/）
cd NeedsOfNature && ./gradlew clean build
```

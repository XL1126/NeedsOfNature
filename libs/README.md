# libs/ — 前置模组 JAR 目录

本目录用于放置 **Animation Director（animationframework）** 的构建产物，
供本仓库在编译期解析 `com.afwid.*` API。

## 放置方法

1. 克隆并构建前置仓库：

   ```powershell
   git clone <AnimationDirector 仓库地址>
   cd AnimationDirector
   ./gradlew.bat clean build --no-daemon --console=plain
   ```

2. 将产物复制到本目录：

   ```text
   build/libs/animationdirector-1.20.1-1.3.1-port.1.jar
   → NeedsOfNature/libs/
   ```

3. 再构建本仓库。

`build.gradle` 通过 `fileTree(dir: 'libs', include: '*animationdirector*.jar')` 引用，
文件名包含 `animationdirector` 即可。

## 运行时

编译通过 ≠ 游戏可运行。进入游戏时，`mods/` 中仍需安装：

- Animation Director JAR（即本文件）
- Fabric API
- GeckoLib
-（可选）Trinkets

## 注意

- `libs/` 下的 `*.jar` 已被 `.gitignore` 忽略，请勿提交二进制。
- 缺少前置 JAR 时编译失败是预期行为。

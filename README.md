# Air2 Android

> Air2 Android 客户端 —— Kotlin + Jetpack Compose + 自研后端

## 技术栈

| 层 | 选型 |
|---|---|
| UI | Kotlin + Jetpack Compose + Material3 |
| 业务 | Kotlin |
| 自研后端 | Kotlin + JNI（C++） |
| Java 层 | 自写（参照 iOS 的 JavaApp） |

## 与 iOS 的关系

**功能基准**：[Air2](https://github.com/Air-Devs/Air2)（iOS, Objective-C）

**不共用代码。** iOS 的 `Natives/` 是 Objective-C + iOS 专属 API，
Android 上以它为设计参照，用 Kotlin 重新实现。

## 模块对应

| iOS `Natives/` | Android |
|---|---|
| `Context/a2_launch.m` | `launch/LaunchCore.kt` |
| `Context/a2_argbuilder.m` | `launch/ArgumentBuilder.kt` |
| `Context/a2_jli_launch.m` | `launch/JliLauncher.kt` |
| `Context/a2_vmloader.m` | `launch/VMLoader.kt` |
| `Context/a2_resourcepaths.m` | `launch/ResourcePaths.kt` |
| `Renderer/a2_gl_bridge.m` | `jni/gl_bridge.cpp` |
| `Renderer/a2_eagl_egl.m` | **不需要**（Android 有原生 EGL） |
| `OwnGlfw/a2_own_glfw_bridge.m` | `jni/glfw_bridge.cpp` |
| `Input/*` | `ui/controls/`（Compose） |

## 规范

设计规范见 [Air2-Spec](https://github.com/Air-Devs/Air2-Spec)

## 状态

🚧 项目初始化中

# Air2 Android 路线图

## 阶段 1：项目骨架（1 周）

- [ ] Gradle 工程配置（AGP / Kotlin / Compose）
- [ ] 应用入口 + 基础导航
- [ ] MD3 主题层（对齐 Air2-Spec）

## 阶段 2：自研后端 - 启动核心（1 个月）

对照 iOS `Natives/Context/`：

- [ ] `ResourcePaths.kt` —— 路径解析
- [ ] `ArgumentBuilder.kt` —— JVM 参数组装
- [ ] `VMLLoader.kt` —— libjvm 加载 + 信号复位
- [ ] `JliLauncher.kt` —— JNI 调 JLI_Launch
- [ ] `LaunchCore.kt` —— 启动主流程

## 阶段 3：渲染桥（1 个月）

- [ ] `gl_bridge.cpp` —— GL 桥（C++ JNI）
- [ ] Android 原生 EGL 接入（替代 iOS 的 `a2_eagl_egl.m`）
- [ ] 渲染器选择 + 回退链

## 阶段 4：GLFW 桥（0.5 个月）

- [ ] `glfw_bridge.cpp` —— 窗口 / 输入事件
- [ ] Surface 绑定

## 阶段 5：控件层（1 个月）

- [ ] 虚拟摇杆 / 按钮（Compose）
- [ ] 控件编辑器
- [ ] 悬浮球
- [ ] 输入法桥

## 阶段 6：业务功能（2 个月）

- [ ] 账号（微软 / 离线 / 第三方）
- [ ] 版本管理
- [ ] 下载
- [ ] 安装（含 Forge）
- [ ] 设置

## 总计

约 5-6 个月

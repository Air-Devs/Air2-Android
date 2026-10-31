package dev.airdevs.air2.data.version

import kotlinx.serialization.Serializable

/**
 * 版本类型
 */
@Serializable
enum class A2VersionType {
    Release,
    Snapshot,
    OldBeta,
    OldAlpha,
    Unknown,
}

/**
 * 模组加载器
 */
data class A2LoaderInfo(
    val kind: String,     // Fabric / Forge / NeoForge / Quilt / OptiFine
    val version: String,
)

/**
 * 已安装的版本
 *
 * 对应 iOS 的 A2Version（从 versions/<name>/<name>.json 解析而来）
 */
data class A2Version(
    /** 版本目录名，如 "1.21.1" 或 "1.21.1-fabric" */
    val name: String,

    /** 实际 MC 版本号（从 json 的 inheritsFrom 或 id 解析） */
    val minecraftVersion: String,

    val type: A2VersionType = A2VersionType.Unknown,

    /** 加载器信息（原版为 null） */
    val loader: A2LoaderInfo? = null,

    /** 是否可启动：json + jar 都存在 */
    val isValid: Boolean = false,

    /** 不可用原因 */
    val invalidReason: String = "",

    /** 版本目录绝对路径 */
    val path: String = "",
) {
    /** 显示用类型 */
    val typeLabel: String
        get() = when (type) {
            A2VersionType.Release -> "正式版"
            A2VersionType.Snapshot -> "快照"
            A2VersionType.OldBeta -> "旧测试版"
            A2VersionType.OldAlpha -> "旧远古版"
            A2VersionType.Unknown -> "未知"
        }

    /** 副标题：原版 / Fabric 0.16.5 … */
    val subtitle: String
        get() = loader?.let { "${it.kind} ${it.version}" } ?: "原版"
}

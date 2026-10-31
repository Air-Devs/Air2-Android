package dev.airdevs.air2.data.account

import kotlinx.serialization.Serializable

/**
 * 账号类型
 *
 * 对齐 iOS 的 A2Account：
 *   Microsoft  微软正版（OAuth 设备码）
 *   Offline    离线（只存用户名）
 *   ThirdParty 第三方（authlib-injector）
 */
@Serializable
enum class A2AccountType {
    Microsoft,
    Offline,
    ThirdParty,
}

/**
 * 账号
 *
 * 存储：{appData}/accounts/<uuid>.json
 * 当前账号的 uuid 存在 SharedPreferences
 */
@Serializable
data class A2Account(
    /** 唯一标识：微软账号是 UUID，离线账号是 "offline-<name>" */
    val id: String,

    val type: A2AccountType,

    /** 显示名 */
    val username: String,

    /** 微软账号的 accessToken（第三方/离线为空） */
    val accessToken: String = "",

    /** 刷新令牌 */
    val refreshToken: String = "",

    /** 过期时间（Unix 毫秒），0 = 不过期 */
    val expiresAt: Long = 0L,

    /** 第三方服务器地址（authlib-injector） */
    val authlibServer: String = "",

    /** 皮肤模型：classic / slim */
    val skinModel: String = "classic",

    /** 创建时间 */
    val createdAt: Long = System.currentTimeMillis(),

    /** 头像 URL（可空，未取到时界面显示首字母） */
    val avatarUrl: String = "",
) {
    /** 显示副标题 */
    val subtitle: String
        get() = when (type) {
            A2AccountType.Microsoft -> "微软正版"
            A2AccountType.Offline -> "离线账号"
            A2AccountType.ThirdParty -> authlibServer.ifEmpty { "第三方" }
        }

    /** 是否过期 */
    val isExpired: Boolean
        get() = expiresAt > 0 && System.currentTimeMillis() > expiresAt
}

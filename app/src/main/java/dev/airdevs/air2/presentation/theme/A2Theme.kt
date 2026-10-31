package dev.airdevs.air2.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Air2 MD3 主题
 *
 * 把 [A2ColorScheme] 桥接到 Compose 的 [MaterialTheme]，
 * 同时通过 [LocalA2Colors] 暴露我们自己的语义字段
 * （surfaceContainer 五档在 Material3 里没有直接暴露）。
 */

val LocalA2Colors = staticCompositionLocalOf<A2ColorScheme> {
    error("A2ColorScheme 未提供：请在 A2Theme 内使用")
}

/** 主题枚举 —— 对应 iOS 的五套 + 动态取色 */
enum class A2ThemeVariant {
    Embermire,      // 烈焰红棕（默认）
    Glacier,        // 冰川蓝
    VerdantDawn,    // 青野绿
    VelvetRose,     // 绛紫玫瑰
    UrbanAsh,       // 都市灰
    Dynamic,        // 从壁纸提取（Android 12+）
}

/** 按变体 + 亮暗取色板（目前只实现了 Embermire） */
private fun colorSchemeFor(variant: A2ThemeVariant, dark: Boolean): A2ColorScheme {
    return when (variant) {
        // TODO: 其余四套待从 iOS 提取
        A2ThemeVariant.Glacier,
        A2ThemeVariant.VerdantDawn,
        A2ThemeVariant.VelvetRose,
        A2ThemeVariant.UrbanAsh,
        A2ThemeVariant.Dynamic,
        A2ThemeVariant.Embermire -> if (dark) EmbermireDark else EmbermireLight
    }
}

/** A2ColorScheme → Material3 ColorScheme */
private fun A2ColorScheme.toMaterial3() = if (isDark) {
    darkColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer,
        background = surface,
        onBackground = onSurface,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        surfaceTint = surfaceTint,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        inversePrimary = inversePrimary,
        outline = outline,
        outlineVariant = outlineVariant,
        scrim = scrim,
    )
} else {
    lightColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer,
        background = surface,
        onBackground = onSurface,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        surfaceTint = surfaceTint,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        inversePrimary = inversePrimary,
        outline = outline,
        outlineVariant = outlineVariant,
        scrim = scrim,
    )
}

@Composable
fun A2Theme(
    variant: A2ThemeVariant = A2ThemeVariant.Embermire,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = colorSchemeFor(variant, darkTheme)
    CompositionLocalProvider(LocalA2Colors provides colors) {
        MaterialTheme(
            colorScheme = colors.toMaterial3(),
            typography = A2Typography,
            content = content,
        )
    }
}

/** 便捷访问器 */
object A2Colors {
    val current: A2ColorScheme
        @Composable get() = LocalA2Colors.current
}

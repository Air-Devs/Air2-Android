package dev.airdevs.air2.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * Air2 MD3 色板
 *
 * 与 iOS 的 A2ColorScheme 一一对应（30 个语义字段）。
 * 规范见 Air2-Spec / design/MD3-SPEC.md
 */
data class A2ColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,

    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,

    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,

    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,

    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val surfaceTint: Color,

    val outline: Color,
    val outlineVariant: Color,

    val inverseSurface: Color,
    val inverseOnSurface: Color,
    val inversePrimary: Color,

    val scrim: Color,
    val shadow: Color,

    val isDark: Boolean,
)

// ─────────────────────────────────────────────────────────────
// Embermire —— 烈焰红棕（默认）
// ─────────────────────────────────────────────────────────────

val EmbermireLight = A2ColorScheme(
    primary = Color(0xFF8B4513),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDBC8),
    onPrimaryContainer = Color(0xFF2F1500),

    secondary = Color(0xFF77574A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDBC8),
    onSecondaryContainer = Color(0xFF2C160C),

    tertiary = Color(0xFF6B5D2E),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFF3E1A6),
    onTertiaryContainer = Color(0xFF221B00),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    surface = Color(0xFFFFF8F6),
    onSurface = Color(0xFF221A16),
    surfaceVariant = Color(0xFFF4DED4),
    onSurfaceVariant = Color(0xFF53433C),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFFF1EA),
    surfaceContainer = Color(0xFFFCEAE1),
    surfaceContainerHigh = Color(0xFFF6E4DB),
    surfaceContainerHighest = Color(0xFFF1DFD6),
    surfaceTint = Color(0xFF8B4513),

    outline = Color(0xFF85736C),
    outlineVariant = Color(0xFFD8C2B8),

    inverseSurface = Color(0xFF382E2A),
    inverseOnSurface = Color(0xFFFFEDE6),
    inversePrimary = Color(0xFFFFB68A),

    scrim = Color(0xFF000000),
    shadow = Color(0xFF000000),

    isDark = false,
)

val EmbermireDark = A2ColorScheme(
    primary = Color(0xFFFFB68A),
    onPrimary = Color(0xFF4F2600),
    primaryContainer = Color(0xFF71370A),
    onPrimaryContainer = Color(0xFFFFDBC8),

    secondary = Color(0xFFE7BEAC),
    onSecondary = Color(0xFF442A20),
    secondaryContainer = Color(0xFF5D4034),
    onSecondaryContainer = Color(0xFFFFDBC8),

    tertiary = Color(0xFFD6C58C),
    onTertiary = Color(0xFF393005),
    tertiaryContainer = Color(0xFF514619),
    onTertiaryContainer = Color(0xFFF3E1A6),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    surface = Color(0xFF1A120E),
    onSurface = Color(0xFFF1DFD6),
    surfaceVariant = Color(0xFF53433C),
    onSurfaceVariant = Color(0xFFD8C2B8),
    surfaceContainerLowest = Color(0xFF140C08),
    surfaceContainerLow = Color(0xFF221A16),
    surfaceContainer = Color(0xFF271E1A),
    surfaceContainerHigh = Color(0xFF322823),
    surfaceContainerHighest = Color(0xFF3D332E),
    surfaceTint = Color(0xFFFFB68A),

    outline = Color(0xFFA08C84),
    outlineVariant = Color(0xFF53433C),

    inverseSurface = Color(0xFFF1DFD6),
    inverseOnSurface = Color(0xFF382E2A),
    inversePrimary = Color(0xFF8B4513),

    scrim = Color(0xFF000000),
    shadow = Color(0xFF000000),

    isDark = true,
)

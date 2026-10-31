package dev.airdevs.air2.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.airdevs.air2.data.account.A2Account
import dev.airdevs.air2.data.version.A2Version
import dev.airdevs.air2.presentation.theme.A2Colors
import dev.airdevs.air2.presentation.theme.A2Radius
import dev.airdevs.air2.presentation.theme.A2Size
import dev.airdevs.air2.presentation.theme.A2Spacing
import dev.airdevs.air2.presentation.viewmodel.Air2HomeState

/**
 * Air2 主页
 *
 * 对齐 iOS 的 A2LauncherViewController：
 *   顶栏（Air2 + 账号/下载/设置）
 *   版本卡（账号 + 版本 + 启动按钮）
 *
 * 数据来自 [Air2HomeState]（真实账号与版本），不再硬编码。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Air2HomeScreen(
    state: Air2HomeState,
    onLaunch: () -> Unit = {},
    onOpenAccount: () -> Unit = {},
    onOpenDownloads: () -> Unit = {},
    onOpenSettings: () -> Unit = {},
    onCreateOfflineAccount: () -> Unit = {},
) {
    val colors = A2Colors.current
    val cfg = LocalConfiguration.current
    val isLandscape = cfg.screenWidthDp > cfg.screenHeightDp

    Scaffold(
        containerColor = colors.surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Air2",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    IconButton(onClick = onOpenAccount) {
                        Icon(Icons.Outlined.AccountCircle, contentDescription = "账号")
                    }
                    IconButton(onClick = onOpenDownloads) {
                        Icon(Icons.Outlined.Download, contentDescription = "下载")
                    }
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Filled.Settings, contentDescription = "设置")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.surface,
                    titleContentColor = colors.onSurface,
                    actionIconContentColor = colors.onSurfaceVariant,
                ),
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            when {
                state.loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = colors.primary,
                    )
                }

                // 没有任何账号：引导创建
                state.account == null -> {
                    EmptyPrompt(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(A2Spacing.XL),
                        title = "还没有账号",
                        message = "创建一个离线账号即可开始。\n（微软正版登录后续版本提供）",
                        actionLabel = "创建离线账号",
                        onAction = onCreateOfflineAccount,
                    )
                }

                // 有账号没版本
                state.version == null -> {
                    EmptyPrompt(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(A2Spacing.XL),
                        title = "还没有安装版本",
                        message = "游戏目录：${state.account.username}\n请先到「下载」页安装一个 Minecraft 版本。",
                        actionLabel = "去下载",
                        onAction = onOpenDownloads,
                    )
                }

                else -> {
                    VersionCard(
                        modifier = Modifier
                            .align(if (isLandscape) Alignment.CenterEnd else Alignment.Center)
                            .padding(A2Spacing.XL)
                            .then(
                                if (isLandscape) Modifier.width(360.dp)
                                else Modifier.fillMaxWidth()
                            ),
                        account = state.account,
                        version = state.version,
                        versionCount = state.versionCount,
                        onLaunch = onLaunch,
                        onOpenSettings = onOpenSettings,
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyPrompt(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    actionLabel: String,
    onAction: () -> Unit,
) {
    val colors = A2Colors.current
    Surface(
        modifier = modifier,
        color = colors.surfaceContainerHigh,
        shape = RoundedCornerShape(A2Radius.XL),
    ) {
        Column(
            modifier = Modifier.padding(A2Spacing.XL),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(A2Spacing.M),
        ) {
            Icon(
                Icons.Outlined.Add,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = colors.primary,
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colors.onSurface,
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Button(
                onClick = onAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(A2Size.ButtonHeight),
                shape = RoundedCornerShape(A2Radius.L),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary,
                ),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(actionLabel, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun VersionCard(
    modifier: Modifier = Modifier,
    account: A2Account,
    version: A2Version,
    versionCount: Int,
    onLaunch: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val colors = A2Colors.current

    Surface(
        modifier = modifier,
        color = colors.surfaceContainerHigh,
        shape = RoundedCornerShape(A2Radius.XL),
    ) {
        Column(
            modifier = Modifier.padding(A2Spacing.XL),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(A2Spacing.M),
        ) {
            Icon(
                Icons.Outlined.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = colors.inversePrimary,
            )
            Text(
                text = account.username,
                style = MaterialTheme.typography.titleMedium,
                color = colors.onSurface,
            )
            Text(
                text = account.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = colors.onSurfaceVariant,
            )

            Spacer(Modifier.height(A2Spacing.S))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = version.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = colors.onSurface,
                    )
                    Text(
                        text = buildString {
                            append(version.subtitle)
                            append(" · ")
                            append(if (version.isValid) "可用" else version.invalidReason)
                            if (versionCount > 1) append(" · 共 $versionCount 个")
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = if (version.isValid) colors.onSurfaceVariant else colors.error,
                    )
                }
                IconButton(onClick = onOpenSettings) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "版本设置",
                        tint = colors.primary,
                    )
                }
            }

            Button(
                onClick = onLaunch,
                enabled = version.isValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(A2Size.ButtonHeight),
                shape = RoundedCornerShape(A2Radius.L),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary,
                ),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = if (version.isValid) "启动游戏" else "版本不完整",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

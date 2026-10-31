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
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.airdevs.air2.presentation.theme.A2Colors
import dev.airdevs.air2.presentation.theme.A2Radius
import dev.airdevs.air2.presentation.theme.A2Size
import dev.airdevs.air2.presentation.theme.A2Spacing
import dev.airdevs.air2.presentation.theme.A2Theme

/**
 * Air2 主页
 *
 * 对齐 iOS 的 A2LauncherViewController：
 *   顶栏（Air2 + 账号/下载/设置）
 *   版本卡（账号 + 版本 + 启动按钮）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Air2HomeScreen(
    versionName: String = "1.21.1",
    versionType: String = "原版 · 隔离 · 关闭",
    accountName: String = "yitenchen1234",
    accountType: String = "第三方 (littleskin.cn)",
    onLaunch: () -> Unit = {},
    onOpenAccount: () -> Unit = {},
    onOpenDownloads: () -> Unit = {},
    onOpenSettings: () -> Unit = {},
) {
    val colors = A2Colors.current

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
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(A2Spacing.XL)
                    .width(320.dp),
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
                        text = accountName,
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.onSurface,
                    )
                    Text(
                        text = accountType,
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
                                text = versionName,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = colors.onSurface,
                            )
                            Text(
                                text = versionType,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.onSurfaceVariant,
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = "版本设置",
                                tint = colors.primary,
                            )
                        }
                    }

                    Button(
                        onClick = onLaunch,
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
                            text = "启动游戏",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 400)
@Composable
private fun Air2HomePreview() {
    A2Theme {
        Air2HomeScreen()
    }
}

package dev.airdevs.air2

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import dev.airdevs.air2.presentation.screens.home.Air2HomeScreen
import dev.airdevs.air2.presentation.theme.A2Theme
import dev.airdevs.air2.presentation.viewmodel.Air2HomeViewModel

class MainActivity : ComponentActivity() {

    private val vm: Air2HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 强制横屏（游戏机式布局）
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        enableEdgeToEdge()

        setContent {
            A2Theme {
                val state by vm.state.collectAsState()
                var showOfflineDialog by remember { mutableStateOf(false) }

                // 启动时刷新一次（账号 / 版本扫描）
                LaunchedEffect(Unit) { vm.refresh() }

                Air2HomeScreen(
                    state = state,
                    onLaunch = { vm.launch() },
                    onOpenAccount = { showOfflineDialog = true },
                    onOpenDownloads = { /* TODO: 下载页 */ },
                    onOpenSettings = { /* TODO: 设置页 */ },
                    onCreateOfflineAccount = { showOfflineDialog = true },
                )

                // 错误提示
                state.error?.let { err ->
                    AlertDialog(
                        onDismissRequest = { vm.clearError() },
                        title = { Text("出问题了") },
                        text = { Text(err) },
                        confirmButton = {
                            TextButton(onClick = { vm.clearError() }) { Text("知道了") }
                        },
                    )
                }

                // 创建离线账号
                if (showOfflineDialog) {
                    var name by remember { mutableStateOf(TextFieldValue("Player")) }
                    AlertDialog(
                        onDismissRequest = { showOfflineDialog = false },
                        title = { Text("创建离线账号") },
                        text = {
                            androidx.compose.material3.OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("用户名") },
                                singleLine = true,
                            )
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                val n = name.text.trim()
                                if (n.isNotEmpty()) {
                                    vm.createOfflineAccount(n)
                                    showOfflineDialog = false
                                }
                            }) { Text("创建") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showOfflineDialog = false }) { Text("取消") }
                        },
                    )
                }
            }
        }
    }
}

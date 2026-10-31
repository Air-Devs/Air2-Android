package dev.airdevs.air2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.airdevs.air2.presentation.screens.home.Air2HomeScreen
import dev.airdevs.air2.presentation.theme.A2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A2Theme {
                Air2App()
            }
        }
    }
}

@Composable
fun Air2App() {
    Air2HomeScreen(
        versionName = "1.21.1",
        versionType = "原版 · 隔离 · 关闭",
        onLaunch = { /* TODO: 接自研后端 */ },
        onOpenAccount = { /* TODO */ },
        onOpenDownloads = { /* TODO */ },
        onOpenSettings = { /* TODO */ },
    )
}

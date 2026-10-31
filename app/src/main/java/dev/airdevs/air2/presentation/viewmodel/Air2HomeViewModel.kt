package dev.airdevs.air2.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.airdevs.air2.data.account.A2Account
import dev.airdevs.air2.data.account.A2AccountRepository
import dev.airdevs.air2.data.account.A2AccountType
import dev.airdevs.air2.data.version.A2Version
import dev.airdevs.air2.data.version.A2VersionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 主页状态
 */
data class Air2HomeState(
    val loading: Boolean = true,
    val account: A2Account? = null,
    val version: A2Version? = null,
    val versionCount: Int = 0,
    val error: String? = null,
) {
    val isEmpty: Boolean get() = !loading && version == null
}

/**
 * 主页 ViewModel
 *
 * 持有账号与版本的真实数据（来自 A2AccountRepository / A2VersionRepository），
 * 不再硬编码。
 */
class Air2HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val accountRepo = A2AccountRepository(app)
    private val versionRepo = A2VersionRepository(app)

    private val _state = MutableStateFlow(Air2HomeState())
    val state: StateFlow<Air2HomeState> = _state.asStateFlow()

    /** 界面操作回调 */
    var onLaunchRequest: ((A2Version, A2Account) -> Unit)? = null
    var onNeedAccount: (() -> Unit)? = null
    var onNeedVersion: (() -> Unit)? = null

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)

            accountRepo.load()
            versionRepo.scan()

            _state.value = Air2HomeState(
                loading = false,
                account = accountRepo.current.value,
                version = versionRepo.current.value,
                versionCount = versionRepo.versions.value.size,
            )
        }
    }

    /** 点击「启动游戏」 */
    fun launch() {
        val v = versionRepo.current.value
        val a = accountRepo.current.value

        when {
            a == null -> onNeedAccount?.invoke()
            v == null -> onNeedVersion?.invoke()
            !v.isValid -> _state.value = _state.value.copy(
                error = "版本不完整：${v.invalidReason}。请重新安装。"
            )
            else -> onLaunchRequest?.invoke(v, a)
        }
    }

    /** 快速创建离线账号（调试/首次使用） */
    fun createOfflineAccount(name: String) {
        viewModelScope.launch {
            accountRepo.createOffline(name)
            refresh()
        }
    }

    /** 清错误 */
    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}

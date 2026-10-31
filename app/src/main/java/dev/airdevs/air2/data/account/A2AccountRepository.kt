package dev.airdevs.air2.data.account

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * 账号仓库
 *
 * 存储位置：{appFilesDir}/accounts/<id>.json
 * 当前账号 id：SharedPreferences("air2")["currentAccountId"]
 *
 * 对齐 iOS 的 A2AccountManager。
 */
class A2AccountRepository(private val context: Context) {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    private val accountsDir: File
        get() = File(context.filesDir, "accounts").apply { if (!exists()) mkdirs() }

    private val prefs
        get() = context.getSharedPreferences("air2", Context.MODE_PRIVATE)

    private val _accounts = MutableStateFlow<List<A2Account>>(emptyList())
    val accounts: StateFlow<List<A2Account>> = _accounts.asStateFlow()

    private val _current = MutableStateFlow<A2Account?>(null)
    val current: StateFlow<A2Account?> = _current.asStateFlow()

    /** 启动时加载 */
    suspend fun load() = withContext(Dispatchers.IO) {
        val list = accountsDir.listFiles()
            ?.filter { it.extension == "json" }
            ?.mapNotNull { file ->
                runCatching { json.decodeFromString<A2Account>(file.readText()) }.getOrNull()
            }
            ?.sortedByDescending { it.createdAt }
            ?: emptyList()

        _accounts.value = list

        val currentId = prefs.getString("currentAccountId", null)
        _current.value = list.firstOrNull { it.id == currentId } ?: list.firstOrNull()
    }

    /** 保存（新增或覆盖） */
    suspend fun save(account: A2Account) = withContext(Dispatchers.IO) {
        File(accountsDir, "${account.id}.json").writeText(json.encodeToString<A2Account>(account))
        _accounts.value = (_accounts.value.filter { it.id != account.id } + account)
            .sortedByDescending { it.createdAt }
        if (_current.value?.id == account.id) _current.value = account
    }

    /** 删除 */
    suspend fun delete(id: String) = withContext(Dispatchers.IO) {
        File(accountsDir, "$id.json").delete()
        _accounts.value = _accounts.value.filter { it.id != id }
        if (_current.value?.id == id) {
            _current.value = _accounts.value.firstOrNull()
            prefs.edit().putString("currentAccountId", _current.value?.id).apply()
        }
    }

    /** 切换当前账号 */
    fun select(id: String) {
        val a = _accounts.value.firstOrNull { it.id == id } ?: return
        _current.value = a
        prefs.edit().putString("currentAccountId", id).apply()
    }

    /** 创建离线账号 */
    suspend fun createOffline(name: String): A2Account {
        val account = A2Account(
            id = "offline-$name",
            type = A2AccountType.Offline,
            username = name,
        )
        save(account)
        select(account.id)
        return account
    }
}

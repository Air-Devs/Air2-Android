package dev.airdevs.air2.data.version

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File

/**
 * 版本仓库
 *
 * 扫描 {gameHome}/versions/<name>/<name>.json，解析成 A2Version。
 * 对齐 iOS 的 A2VersionManager 扫描逻辑。
 *
 * gameHome 默认：{appFilesDir}/minecraft
 */
class A2VersionRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    /** 游戏根目录 */
    val gameHome: File
        get() = File(context.filesDir, "minecraft").apply { if (!exists()) mkdirs() }

    private val versionsDir: File
        get() = File(gameHome, "versions").apply { if (!exists()) mkdirs() }

    private val prefs
        get() = context.getSharedPreferences("air2", Context.MODE_PRIVATE)

    private val _versions = MutableStateFlow<List<A2Version>>(emptyList())
    val versions: StateFlow<List<A2Version>> = _versions.asStateFlow()

    private val _current = MutableStateFlow<A2Version?>(null)
    val current: StateFlow<A2Version?> = _current.asStateFlow()

    /** 扫描全部版本 */
    suspend fun scan() = withContext(Dispatchers.IO) {
        val list = versionsDir.listFiles()
            ?.filter { it.isDirectory }
            ?.mapNotNull { dir -> parseVersion(dir) }
            ?.sortedByDescending { it.name }
            ?: emptyList()

        _versions.value = list

        val currentName = prefs.getString("currentVersionName", null)
        _current.value = list.firstOrNull { it.name == currentName }
            ?: list.firstOrNull { it.isValid }
            ?: list.firstOrNull()
    }

    /** 解析单个版本目录 */
    private fun parseVersion(dir: File): A2Version? {
        val name = dir.name
        val jsonFile = File(dir, "$name.json")
        if (!jsonFile.exists()) return null

        val obj: JsonObject = runCatching {
            json.parseToJsonElement(jsonFile.readText()).jsonObject
        }.getOrNull() ?: return A2Version(
            name = name,
            minecraftVersion = name,
            isValid = false,
            invalidReason = "版本 json 解析失败",
            path = dir.absolutePath,
        )

        // MC 版本：优先 inheritsFrom，其次 id
        val inheritsFrom = obj["inheritsFrom"]?.jsonPrimitive?.contentOrNull
        val id = obj["id"]?.jsonPrimitive?.contentOrNull
        val mcVersion = inheritsFrom ?: id ?: name

        // 类型
        val type = when (obj["type"]?.jsonPrimitive?.contentOrNull) {
            "release" -> A2VersionType.Release
            "snapshot" -> A2VersionType.Snapshot
            "old_beta" -> A2VersionType.OldBeta
            "old_alpha" -> A2VersionType.OldAlpha
            else -> A2VersionType.Unknown
        }

        // 加载器
        val loader = detectLoader(obj, name)

        // 有效性：json + jar
        val jar = File(dir, "$name.jar")
        val hasJar = jar.exists() && jar.length() > 0

        // 如果是加载器版，jar 可能在父版本目录里
        val parentJar = inheritsFrom?.let { File(versionsDir, "$it/$it.jar") }
        val effectiveHasJar = hasJar || (parentJar?.exists() == true && parentJar.length() > 0)

        return when {
            !effectiveHasJar -> A2Version(
                name = name,
                minecraftVersion = mcVersion,
                type = type,
                loader = loader,
                isValid = false,
                invalidReason = "缺少客户端 jar",
                path = dir.absolutePath,
            )
            else -> A2Version(
                name = name,
                minecraftVersion = mcVersion,
                type = type,
                loader = loader,
                isValid = true,
                path = dir.absolutePath,
            )
        }
    }

    /** 从 json 里识别加载器 */
    private fun detectLoader(obj: JsonObject, name: String): A2LoaderInfo? {
        // id 里带 -fabric / -forge / -neoforge / -quilt / -optifine
        val lower = name.lowercase()
        val suffix = listOf(
            "fabric" to "Fabric",
            "forge" to "Forge",
            "neoforge" to "NeoForge",
            "quilt" to "Quilt",
            "optifine" to "OptiFine",
        ).firstOrNull { lower.contains(it.first) }?.second ?: return null

        // 版本号：从 libraries 里找加载器主 jar
        var loaderVersion = ""
        runCatching {
            val libs = obj["libraries"]?.jsonArray ?: return@runCatching
            for (lib in libs) {
                val libName = lib.jsonObject["name"]?.jsonPrimitive?.contentOrNull ?: continue
                val parts = libName.split(":")
                if (parts.size >= 3 && parts[1].contains(suffix.lowercase())) {
                    loaderVersion = parts[2]
                    break
                }
            }
        }

        return A2LoaderInfo(kind = suffix, version = loaderVersion)
    }

    /** 切换当前版本 */
    fun select(name: String) {
        val v = _versions.value.firstOrNull { it.name == name } ?: return
        _current.value = v
        prefs.edit().putString("currentVersionName", name).apply()
    }
}

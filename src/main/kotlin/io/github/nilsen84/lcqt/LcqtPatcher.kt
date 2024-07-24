package io.github.nilsen84.lcqt

import io.github.nilsen84.lcqt.patches.*
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import java.lang.instrument.Instrumentation

object LcqtPatcher {
    val JSON = Json { ignoreUnknownKeys = true; prettyPrint = true }

    @get:JvmName("configDir")
    val configDir = getConfigDir().also {
        if (!it.exists()) it.mkdirs()
    }

    private fun getConfigDir(): File {
        val os = System.getProperty("os.name").lowercase()
        val home = System.getProperty("user.home")

        val configDir: File = when {
            os.contains("windows") -> System.getenv("APPDATA")?.let(::File) ?: File(home, "AppData\\Roaming")
            os.contains("mac") -> File(home, "Library/Application Support")
            else -> System.getenv("XDG_CONFIG_HOME")?.let(::File) ?: File(home, ".config")
        }

        return File(configDir, "lcqt2")
    }
}
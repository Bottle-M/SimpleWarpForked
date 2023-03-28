package me.marylieh.simplewarp.utils

import me.marylieh.simplewarp.SimpleWarp
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException

/**
 * 这个类针对配置进行操作
 */
object Config {

    private lateinit var file: File
    private lateinit var config: YamlConfiguration
    fun loadConfig() {
        val dir = SimpleWarp.plugin.dataFolder // 插件目录

        if (!dir.exists()) {
            dir.mkdirs()
        }

        file = File(dir, "configs.yml")

        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        config = YamlConfiguration.loadConfiguration(file)
        initConfig()
    }

    // 判断玩家是否能作为op忽略一切权限限制
    fun opOverride(player: Player): Boolean {
        return getConfig().getBoolean("op-full-access") && player.isOp
    }

    // 检查一个玩家是否有某种权限
    fun checkPermission(player: Player, node: String): Boolean {
        return opOverride(player) || player.hasPermission(node)
    }

    fun getConfig(): YamlConfiguration {
        return config
    }

    private fun save() {
        try {
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun reload(): Boolean {
        try {
            config.load(file)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InvalidConfigurationException) {
            e.printStackTrace()
        }
        return false
    }

    private fun initConfig() {
        // OP有最高权限
        if (config.get("op-full-access") == null) {
            config.set("op-full-access", true) // 默认OP有全部权限
        }
        // 传送延迟时间(in seconds)
        if (config.get("delay-before-tp") == null) {
            config.set("delay-before-tp", 0) // 默认立即传送
        }
        // 即将传送的时候是否不允许玩家移动
        if (config.get("no-move-allowed-before-tp") == null) {
            config.set("no-move-allowed-before-tp", false) // 默认传送前可以移动
        }
        if (config.get("position-system") == null) {
            config.set("position-system", false)
        }
        /*  作为fork版本，这里就不整什么自动更新了，花里胡哨
        if (config.get("auto-update") == null) {
            config.set("auto-update", true)
        }
        */
        if (config.get("player-warps-only") == null) {
            config.set("player-warps-only", false)
        }
        save()
    }
}
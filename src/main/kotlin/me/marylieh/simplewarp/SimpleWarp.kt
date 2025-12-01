package me.marylieh.simplewarp

import me.marylieh.simplewarp.commands.*
import me.marylieh.simplewarp.commands.position.PositionCommandExecutor
import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import org.bukkit.plugin.java.JavaPlugin

class SimpleWarp : JavaPlugin() {

    val prefix = "§6[SimpleWarp]"
    val version = "4.0"

    // 伴生对象
    companion object {
        lateinit var plugin: SimpleWarp
            private set // 私有化setter
    }

    override fun onLoad() {
        plugin = this // 暴露插件Plugin对象
        Config.loadConfig() // 初始化配置对象
        Data.loadData() // 初始化地标数据对象
    }

    override fun onEnable() {
        registerCommands()
        // if (Config.getConfig().getBoolean("auto-update")) {val updater = Updater(this, 395393, this.file, Updater.UpdateType.DEFAULT, true)}
    }


    private fun registerCommands() {
        val setWarpCommand = getCommand("setwarp") ?: error("Couldn't get setwarp command! This should not happen!")
        val delWarpCommand = getCommand("delwarp") ?: error("Couldn't get delwarp command! This should not happen!")
        val warpCommand = getCommand("warp") ?: error("Couldn't get warp command! This should not happen!")
        val warpsCommand = getCommand("warps") ?: error("Couldn't get warps command! This should not happen!")
        val simpleWarpCommand =
            getCommand("simplewarp") ?: error("Couldn't get simplewarp command! This should not happen!")
        val positionCommand = getCommand("position") ?: error("Couldn't get position command! This should not happen!")
        val setHomeCommand = getCommand("sethome") ?: error("Couldn't get sethome command! This should not happen!")
        val delHomeCommand = getCommand("delhome") ?: error("Couldn't get delhome command! This should not happen!")
        val homeCommand = getCommand("home") ?: error("Couldn't get home command! This should not happen!")
        val homesCommand = getCommand("homes") ?: error("Couldn't get homes command! This should not happen!")
        setWarpCommand.setExecutor(SetWarpCommandExecutor())
        delWarpCommand.setExecutor(DelWarpCommandExecutor())
        warpCommand.setExecutor(WarpCommandExecutor())
        warpsCommand.setExecutor(WarpsCommandExecutor())
        setHomeCommand.setExecutor(SetHomeCommandExecutor())
        delHomeCommand.setExecutor(DelHomeCommandExecutor())
        homeCommand.setExecutor(HomeCommandExecutor())
        homesCommand.setExecutor(HomesCommandExecutor())
        simpleWarpCommand.setExecutor(SimpleWarpCommandExecutor())
        positionCommand.setExecutor(PositionCommandExecutor())
        simpleWarpCommand.tabCompleter = SimpleWarpTabCompleter()
        warpCommand.tabCompleter = WarpTabCompleter()
        delWarpCommand.tabCompleter = WarpTabCompleter()
        homeCommand.tabCompleter = HomeTabCompleter()
        delHomeCommand.tabCompleter = HomeTabCompleter()
    }


}
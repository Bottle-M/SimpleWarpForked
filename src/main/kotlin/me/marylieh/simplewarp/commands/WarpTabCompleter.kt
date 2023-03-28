package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.command.TabCompleter

class WarpTabCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val list = ArrayList<String>()
        if (sender !is Player) return list
        val player: Player = sender
        if (Config.checkPermission(player, "simplewarp.warps")) {
            // 如果OP有最高权限，就可以列出所有的地标
            if (!Config.opOverride(player) && Config.getConfig().getBoolean("player-warps-only")) {
                // 模糊匹配
                val filtered = Data.playerWarpSet(player.uniqueId.toString())
                    ?.filter { value -> value.lowercase().startsWith(args[0].lowercase()) }
                filtered?.forEach { list.add(it) }
                return list
            }
            val filtered = Data.allWarpsSet()?.filter { value -> value.lowercase().startsWith(args[0].lowercase()) }
            filtered?.forEach { list.add(it) }
        }
        return list
    }
}

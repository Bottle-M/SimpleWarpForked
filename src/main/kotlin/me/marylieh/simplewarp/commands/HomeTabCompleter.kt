package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.command.TabCompleter

class HomeTabCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val list = ArrayList<String>()
        if (sender !is Player) return list
        val player: Player = sender
        val uuid = player.uniqueId.toString()
        if (Config.checkPermission(player, "simplewarp.homes")) {
            val filtered =
                Data.allPlayerHomesSet(uuid)?.filter { value -> value.lowercase().startsWith(args[0].lowercase()) }
            filtered?.forEach { list.add(it) }
        }
        return list
    }
}

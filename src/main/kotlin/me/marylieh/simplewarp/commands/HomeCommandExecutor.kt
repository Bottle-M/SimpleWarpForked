package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import me.marylieh.simplewarp.utils.Messages
import me.marylieh.simplewarp.utils.TeleportDelayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HomeCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender
        if (Config.checkPermission(player, "simplewarp.home")) {
            if (args.size == 1) {
                var homeId = ""
                val uuid = player.uniqueId.toString()
                if (Config.checkPermission(player, "simplewarp.homes")) {
                    // 模糊匹配家
                    val filtered =
                        Data.allPlayerHomesSet(uuid)
                            ?.filter { value -> value.lowercase().startsWith(args[0].lowercase()) }
                    if (filtered?.size == 1) {
                        homeId = filtered[0]
                    }
                }
                // 没有模糊匹配到，就采用用户输入
                if (homeId == "") {
                    homeId = args[0]
                }
                // 家不存在
                if (!Data.playerHomeExists(homeId, uuid)) {
                    player.sendMessage(Messages.homeNotExist)
                    return true
                }
                // 创建传送任务
                TeleportDelayer.tp(player, Data.getHome(homeId, uuid), homeId)

            } else {
                player.sendMessage(Messages.usage("§7/home <homeName>"))
            }
        } else {
            player.sendMessage(Messages.noPermission)
        }
        return true
    }
}
package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import me.marylieh.simplewarp.utils.Messages
import me.marylieh.simplewarp.utils.TeleportDelayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarpCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender
        val isOp = Config.opOverride(player)
        if (isOp || player.hasPermission("simplewarp.warp")) {
            if (args.size == 1) {
                var warpId = ""
                if (player.hasPermission("simplewarp.warps")) {
                    // 模糊匹配地标，用户可以只输入地标名的开头几个字符
                    val filtered =
                        Data.allWarpsSet()?.filter { value -> value.lowercase().startsWith(args[0].lowercase()) }
                    if (filtered?.size == 1) {
                        warpId = filtered[0]
                    }
                }
                // 没有模糊匹配到，就采用用户输入
                if (warpId == "") {
                    warpId = args[0]
                }
                // 地标不存在
                if (!Data.warpExists(warpId)) {
                    player.sendMessage(Messages.warpNotExist)
                    return true
                }

                if (Config.getConfig().getBoolean("player-warps-only")) {
                    // 检查玩家是否拥有地标
                    if (!isOp && !Data.warpOwnedBy(player.uniqueId.toString(), warpId)) {
                        player.sendMessage(Messages.noPermission)
                        return true
                    }
                }
                // 创建传送任务
                TeleportDelayer.tp(player, Data.getWarp(warpId), warpId)

            } else {
                player.sendMessage(Messages.usage("§7/warp <warpName>"))
            }
        } else {
            player.sendMessage(Messages.noPermission)
        }
        return true
    }
}
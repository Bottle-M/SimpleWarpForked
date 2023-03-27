package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import me.marylieh.simplewarp.utils.Messages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarpsCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender
        val allWarps = Data.allWarpsSet()
        if (player.hasPermission("simplewarp.warps")) {
            // 如果没有开启【玩家创建的地标只能由玩家使用】的选项，就列出所有地标
            if (!Config.getConfig().getBoolean("player-warps-only")) {
                player.sendMessage(Messages.listWarps(allWarps))
                return true
            }
            // 否则列出这个玩家创建的所有地标
            val playerWarps = Data.playerWarpSet(player.uniqueId.toString())
            player.sendMessage(Messages.listPlayerWarps(playerWarps))
        } else {
            player.sendMessage(Messages.noPermission)
        }
        return true
    }
}
package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import me.marylieh.simplewarp.utils.Messages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DelHomeCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender
        if (Config.checkPermission(player, "simplewarp.delhome")) {
            if (args.size == 1) {
                val homeId = args[0]
                val uuid = player.uniqueId.toString()
                // 地标存在才能删掉
                if (Data.playerHomeExists(homeId, uuid)) {
                    Data.rmHome(homeId, uuid)
                    player.sendMessage(Messages.homeDeleted(homeId))
                } else {
                    player.sendMessage(Messages.homeNotExist)
                }
            } else {
                player.sendMessage(Messages.usage("§7/delhome <homeName>"))
            }
        } else {
            player.sendMessage(Messages.noPermission)
        }
        return true
    }
}
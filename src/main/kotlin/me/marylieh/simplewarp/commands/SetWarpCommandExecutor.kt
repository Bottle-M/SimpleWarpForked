package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import me.marylieh.simplewarp.utils.Messages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetWarpCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender

        if (Config.opOverride(player) || player.hasPermission("simplewarp.setwarp")) {
            if (args.size == 1) {
                val warpId = args[0]
                Data.setWarp(warpId, player.location, player.uniqueId.toString())
                player.sendMessage(Messages.warpSet(warpId))
            } else {
                player.sendMessage(Messages.usage("§7/setwarp <warpName>"))
            }
        } else {
            player.sendMessage(Messages.noPermission)
        }

        return true
    }
}
package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import me.marylieh.simplewarp.utils.Messages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HomesCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender
        val uuid = player.uniqueId.toString()
        if (Config.checkPermission(player, "simplewarp.homes")) {
            val allPlayerHomes = Data.allPlayerHomesSet(uuid)
            player.sendMessage(Messages.listPlayerHomes(allPlayerHomes))
        } else {
            player.sendMessage(Messages.noPermission)
        }
        return true
    }
}
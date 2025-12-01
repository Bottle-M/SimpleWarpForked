package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import me.marylieh.simplewarp.utils.Messages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetHomeCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
            return true
        }
        val player: Player = sender
        // 最多允许的家数量
        val maxHomesAllowed = Config.getConfig().getInt("max-homes-per-player")

        if (Config.checkPermission(player, "simplewarp.sethome")) {
            if (args.size == 1) {
                val homeId = args[0]
                val uuid = player.uniqueId.toString()
                val numPlayerHomes = Data.playerHomeCount(uuid)
                if(numPlayerHomes >= maxHomesAllowed) {
                    // 家数量超出限制
                    player.sendMessage(Messages.tooManyHomes(maxHomesAllowed,numPlayerHomes))
                    return true
                }
                if (!Data.playerHomeExists(homeId, uuid)) {
                    Data.setHome(homeId, player.location, uuid)
                    player.sendMessage(Messages.homeSet(homeId))
                } else {
                    player.sendMessage(Messages.homeAlreadyExists)
                }
            } else {
                player.sendMessage(Messages.usage("§7/sethome <homeName>"))
            }
        } else {
            player.sendMessage(Messages.noPermission)
        }

        return true
    }
}
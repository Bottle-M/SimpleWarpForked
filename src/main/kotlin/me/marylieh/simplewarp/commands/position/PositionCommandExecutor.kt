package me.marylieh.simplewarp.commands.position

import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import me.marylieh.simplewarp.utils.Messages
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PositionCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.notAPlayer)
        }
        val player: Player = sender as Player
        if (!Config.checkPermission(player, "simplewarp.position")) {
            player.sendMessage(Messages.noPermission)
            return true
        }
        if (!Config.getConfig().getBoolean("position-system")) {
            player.sendMessage(Messages.featureNotAvailable)
            return true
        }
        val allPositions = Data.allPositionsSet()
        if (args.size == 1 || args.size == 2) {
            when (args[0]) {
                "list" -> {
                    if (!Config.checkPermission(player, "simplewarp.position.list")) {
                        player.sendMessage(Messages.noPermission)
                        return true
                    }
                    player.sendMessage(Messages.listPositions(allPositions))
                }

                "del" -> {
                    if (!Config.checkPermission(player, "simplewarp.position.del")) {
                        player.sendMessage(Messages.noPermission)
                        return true
                    }
                    // 如果地标存在就可以删除
                    if (Data.positionExists(args[1])) {
                        Data.rmPosition(args[1])
                        player.sendMessage(Messages.positionDeleted(args[1]))
                    } else {
                        player.sendMessage(Messages.posNotExist)
                        return true
                    }
                }

                else -> {
                    val posId = args[0]
                    if (Data.positionExists(posId)) {
                        // 如果位置存在
                        if (!Config.checkPermission(player, "simplewarp.position.view")) {
                            player.sendMessage(Messages.noPermission)
                            return true
                        }
                        player.sendMessage(Messages.custom(Data.getPosition(posId)))
                        return true
                    }
                    // 如果位置不存在，就开始创建
                    if (!Config.checkPermission(player, "simplewarp.position.create")) {
                        player.sendMessage(Messages.noPermission)
                        return true
                    }

                    Data.setPosition(posId, player.location)
                    Bukkit.broadcast(
                        Component.text(
                            Messages.custom(
                                "New position from [${player.name}] - ${
                                    Data.getPosition(
                                        posId
                                    )
                                }"
                            )
                        )
                    )
                }
            }
        } else {
            player.sendMessage(Messages.usage("§c/position <list | positionId | del>"))
        }
        return true
    }
}
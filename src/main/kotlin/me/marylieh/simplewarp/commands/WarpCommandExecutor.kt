package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.SimpleWarp
import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.TeleportDelayer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarpCommandExecutor : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${SimpleWarp.instance.prefix} §4Just a Player can execute this command!")
            return true
        }
        val player: Player = sender

        if (player.hasPermission("simplewarp.warp")) {
            if (args.size == 1) {

                var id = ""
                if (player.hasPermission("simplewarp.warps")) {
                    val filtered = Config.getConfig().getConfigurationSection(".Warps")?.getKeys(false)
                        ?.filter { value -> value.lowercase().startsWith(args[0].lowercase()) }
                    if (filtered?.size == 1) {
                        id = filtered[0]
                    }
                }
                if (id == "") {
                    id = args[0]
                }
                if (Config.getConfig().getString(".Warps.$id") == null) {
                    player.sendMessage("${SimpleWarp.instance.prefix} §cThis warp doesn't exists!")
                    return true
                }

                if (Config.getConfig().get("player-warps-only") == null) {
                    Config.getConfig().set("player-warps-only", false)
                    println("Old Version of Config detected! Setting player-warps-only to false!")
                    Config.save()
                }

                if (Config.getConfig().getBoolean("player-warps-only")) {
                    println(Config.getConfig().getBoolean("player-warps-only"))
                    if (Config.getConfig().getString(".Warps.${id}.Owner") != player.uniqueId.toString()) {
                        player.sendMessage("${SimpleWarp.instance.prefix} §cYou don't have the permission to do that!")
                        return true
                    }
                }

                val world = Bukkit.getWorld(Config.getConfig().getString(".Warps.${id}.World")!!)

                val x = Config.getConfig().getInt("Warps.${id}.X").toDouble()
                val y = Config.getConfig().getInt("Warps.${id}.Y").toDouble()
                val z = Config.getConfig().getInt("Warps.${id}.Z").toDouble()

                val yaw = Config.getConfig().getInt("Warps.${id}.Yaw").toFloat()
                val pitch = Config.getConfig().getInt("Warps.${id}.Pitch").toFloat()
                TeleportDelayer.tp(player, Location(world, x, y, z, yaw, pitch), id)

            } else {
                player.sendMessage("${SimpleWarp.instance.prefix} §cPlease use: §7/warp <warpname>")
            }
        } else {
            player.sendMessage("${SimpleWarp.instance.prefix} §cYou don't have the permission to do that!")
        }
        return true
    }
}
package me.marylieh.simplewarp.commands

import me.marylieh.simplewarp.SimpleWarp
import me.marylieh.simplewarp.utils.Config
import me.marylieh.simplewarp.utils.Data
import me.marylieh.simplewarp.utils.Messages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * 这个模块用作接管/simplewarp相关指令的执行
 */
class SimpleWarpCommandExecutor : CommandExecutor {
    private val promptMsg = Messages.usage("§c/simplewarp <version | reload>")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // 接受一个参数
        if (args.size == 1) {
            when (args[0]) {
                "version" -> {
                    // 版本信息
                    sender.sendMessage(Messages.custom("§aVersion: ${SimpleWarp.plugin.version}"))
                    sender.sendMessage(Messages.custom("§3Forked version by§e SomeBottle"))
                    sender.sendMessage(Messages.custom("§3Original developer -§e marie(marylieh)"))
                    sender.sendMessage(Messages.custom("§9Repo: https://github.com/Bottle-M/SimpleWarpForked"))
                    sender.sendMessage(Messages.custom("(∠・ω< )"))
                }

                "reload" -> {
                    // 重载配置
                    if ((sender !is Player) || Config.checkPermission(sender, "simplewarp.reload")) {
                        if (Config.reload() && Data.reload()) {
                            sender.sendMessage(Messages.reloadSuccess)
                        } else {
                            sender.sendMessage(Messages.reloadFailure) // 重载失败
                        }
                    } else {
                        sender.sendMessage(Messages.noPermission)
                    }
                }

                else -> {
                    sender.sendMessage(promptMsg)
                }
            }
        } else {
            sender.sendMessage(promptMsg)
        }
        return true
    }
}
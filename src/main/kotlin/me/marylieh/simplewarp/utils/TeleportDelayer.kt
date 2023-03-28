package me.marylieh.simplewarp.utils

import me.marylieh.simplewarp.SimpleWarp
import org.bukkit.entity.Player
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable
import kotlin.collections.HashMap
import kotlin.math.abs

object TeleportDelayer {
    // 用哈希表储存<玩家ID,传送任务>
    private val tasks = HashMap<String, TeleportTask>()

    // 这里用玩家的UUID作为键
    fun tp(player: Player, location: Location, warpId: String) {
        // 传送前等待时间(In seconds)
        var timeDelay = Config.getConfig().getLong("delay-before-tp")
        if (timeDelay < 0) // 防止出现负数的等待时间(Seconds)
            timeDelay = 0
        // 等待时是否允许玩家移动
        var noMoveAllowed = Config.getConfig().getBoolean("no-move-allowed-before-tp")
        // 如果玩家有相应权限，就可以绕过传送延迟和移动检查
        if (Config.checkPermission(player, "simplewarp.warp.nodelay"))
            timeDelay = 0
        if (Config.checkPermission(player, "simplewarp.warp.allowmove"))
            noMoveAllowed = false
        // 创建传送任务（计时器）对象
        // 这里timeDelay*2是因为tpTask每半秒被执行一次
        val tpTask = TeleportTask(player, location, timeDelay * 2, noMoveAllowed, warpId)
        val taskKey = player.uniqueId.toString()
        if (tasks.containsKey(taskKey) && tasks[taskKey]?.isWorking() == true) {
            // 如果当前用户下还有正在运行的计时器，就取消掉
            tasks[taskKey]?.stopWorking()
        }
        tasks[taskKey] = tpTask // 将传送任务存入哈希表
        // 如果有延迟时间，就提醒玩家
        if (timeDelay > 0) {
            player.sendMessage(Messages.teleportDelay(timeDelay, noMoveAllowed))
        }
        // 添加定时器任务
        tpTask.runTaskTimer(SimpleWarp.plugin, 0, 10) // 先立即执行一次，然后每间隔半秒(10ticks)执行一次，直至任务结束
    }
}

/**
 * 表示一个传送玩家到location位置的任务
 */
class TeleportTask(
    private val player: Player,
    private val location: Location,
    private var timeLeft: Long,
    private val noMoveAllowed: Boolean,
    private val warpId: String
) : BukkitRunnable() {
    private var initialPos: Location = player.location // 玩家的最初位置
    private var status: Boolean = true

    // 计时器是否正在运行
    fun isWorking(): Boolean {
        return status
    }

    // 取消传送任务
    fun stopWorking() {
        this.cancel() // 取消定时器
        status = false
    }

    override fun run() {
        // 如果在等待过程中不允许玩家移动，就需要检查玩家是否移动
        if (noMoveAllowed) {
            val currPos: Location = player.location // 获得玩家当前的坐标
            val xDiff = abs(currPos.x - initialPos.x) // 获得x,y,z上玩家移动的距离
            val yDiff = abs(currPos.y - initialPos.y)
            val zDiff = abs(currPos.z - initialPos.z)
            // 超过大半格就算移动
            if (xDiff > 0.6 || yDiff > 0.6 || zDiff > 0.6) {
                stopWorking() // 取消定时器，传送取消
                player.sendMessage(Messages.tpCancelledByMovement)
                return
            }
        }
        if (timeLeft <= 0) {
            player.sendMessage(Messages.teleportedTo(warpId))
            player.teleport(location) // 倒计时结束立即传送
            stopWorking() // 玩家已传送，计时器任务停止
            return
        }
        timeLeft--
    }
}
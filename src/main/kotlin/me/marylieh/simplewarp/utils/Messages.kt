package me.marylieh.simplewarp.utils

import me.marylieh.simplewarp.SimpleWarp

/**
 * 本模块集中处理发送给玩家或者控制台的信息
 */
object Messages {
    // 提示信息中包含的前缀内容，往往是插件名
    private val PREFIX = SimpleWarp.plugin.prefix

    // 执行指令的不是玩家
    val notAPlayer: String = "$PREFIX §4This command can only be executed by a player!"

    // 指令执行者没有权限
    val noPermission: String = "$PREFIX §cYou don't have the permission to do that! (╬▔皿▔)"

    // 配置成功重载
    val reloadSuccess: String = "$PREFIX Successfully reloaded the configs"

    // 配置重载失败
    val reloadFailure: String = "$PREFIX §bFailed to reload configs!"

    // 地标不存在
    val warpNotExist: String = "$PREFIX §cThis warp doesn't exists! (￣^￣)ゞ"

    // 地标已存在
    val warpAlreadyExists: String = "$PREFIX §cWarp of the name already exists! (￣^￣)ゞ"

    // 位置信息不存在
    val posNotExist: String = "$PREFIX §cThis position doesn't exists. (；￣Д￣)"

    // 家已存在
    val homeAlreadyExists: String = "$PREFIX §cHome of the name already exists! (￣^￣)ゞ"

    // 家不存在
    val homeNotExist: String = "$PREFIX §cThis home doesn't exists. (∠・ω< )⌒☆"

    // 由于玩家移动，传送取消
    val tpCancelledByMovement: String = "$PREFIX §cTeleportation was cancelled due to movement. Zako zako~ (σ´∀｀)σ"

    // 功能未启动
    val featureNotAvailable: String = "$PREFIX §cThis feature has been disabled by the Administrator! (¬‿¬ )"

    // 列出所有地标
    fun listWarps(warps: Collection<String>?): String {
        if (warps.isNullOrEmpty()) {
            return "$PREFIX §7No warps available. (；´д｀)ゞ"
        }
        return "$PREFIX All available warps: §e${warps.joinToString(", ")}"
    }

    // 列出玩家拥有的地标
    fun listPlayerWarps(warps: Collection<String>?): String {
        if (warps.isNullOrEmpty()) {
            return "$PREFIX §7You don't own any warps. (≖‿≖ )"
        }
        return "$PREFIX Warps you owned: §e${warps.joinToString(", ")}"
    }

    // 列出所有位置信息
    fun listPositions(positions: Collection<String>?): String {
        if (positions.isNullOrEmpty()) {
            return "$PREFIX §7No positions available. (；´д｀)ゞ"
        }
        return "$PREFIX §7Available §9positions: §b${positions.joinToString(", ")}"
    }

    // 列出玩家的家
    fun listPlayerHomes(homes: Collection<String>?): String {
        if (homes.isNullOrEmpty()) {
            return "$PREFIX §7You don't have any homes. (∠・ω< )⌒★"
        }
        return "$PREFIX §7Your §9homes: §b${homes.joinToString(", ")}"
    }

    // 传送延迟时发给玩家的消息
    fun teleportDelay(timeDelay: Long, noMoveAllowed: Boolean): String {
        var msg = "$PREFIX §a(∩^o^)⊃━☆ Teleportation will start in §6$timeDelay§a second(s). "
        if (noMoveAllowed)
            msg += "Please §cDO NOT MOVE§a while waiting. (乂'ω')"
        return msg
    }

    // 传送到地标时发送给玩家的消息
    fun teleportedTo(warpId: String): String {
        return "$PREFIX §aYou have been teleported to §6$warpId§a! °˖✧◝(⁰▿⁰)◜✧˖°"
    }

    // 位置信息被删除
    fun positionDeleted(posId: String): String {
        return "$PREFIX The position §a${posId} §6has been successfully §cdeleted!"
    }

    // 地标被删除
    fun warpDeleted(warpId: String): String {
        return "$PREFIX §aWarp §6$warpId§a was successfully deleted! (￣^￣)ゞ"
    }

    fun warpSet(warpId: String): String {
        return "$PREFIX §aWarp §6${warpId}§a was successfully set! (￣^￣)ゞ"
    }

    fun homeDeleted(homeId: String): String {
        return "$PREFIX §aHome §6$homeId§a was successfully deleted! (￣^￣)ゞ"
    }

    fun homeSet(homeId: String): String {
        return "$PREFIX §aHome §6$homeId§a was successfully set! (￣^￣)ゞ"
    }

    fun tooManyHomes(maxHomes: Int, currentHomes: Int): String {
        return "$PREFIX §cYou have reached the maximum number (§6$currentHomes§c/§6$maxHomes§c) of homes! (∠・▽< )⌒☆"
    }

    fun usage(info: String): String {
        return "$PREFIX §cUsage: $info"
    }

    // 自定义消息
    fun custom(info: String): String {
        return "$PREFIX $info"
    }
}
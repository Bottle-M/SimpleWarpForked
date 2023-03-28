package me.marylieh.simplewarp.utils

import me.marylieh.simplewarp.SimpleWarp
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException

/**
 * 这个类用于对Warp和Position数据进行操作
 */
object Data {
    private lateinit var warpFile: File
    private lateinit var positionFile: File
    private lateinit var warpData: YamlConfiguration
    private lateinit var positionData: YamlConfiguration
    fun loadData() {
        val dir = SimpleWarp.plugin.dataFolder // 插件目录

        if (!dir.exists()) {
            dir.mkdirs()
        }

        warpFile = File(dir, "warps.data") // 地标数据路径
        positionFile = File(dir, "positions.data") // 位置数据路径
        if (!warpFile.exists()) {
            try {
                warpFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (!positionFile.exists()) {
            try {
                positionFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        warpData = YamlConfiguration.loadConfiguration(warpFile)
        positionData = YamlConfiguration.loadConfiguration(positionFile)
    }

    // 写入文件
    private fun save() {
        try {
            warpData.save(warpFile)
            positionData.save(positionFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // 重载数据
    fun reload(): Boolean {
        try {
            warpData.load(warpFile)
            positionData.load(positionFile)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InvalidConfigurationException) {
            e.printStackTrace()
        }
        return false
    }

    // 设置warp
    fun setWarp(warpId: String, location: Location, uuid: String) {
        val world: String = location.world.name
        val x = location.x
        val y = location.y
        val z = location.z
        val yaw = location.yaw
        val pitch = location.pitch
        warpData.set(".warps.${warpId}.world", world)
        warpData.set(".warps.${warpId}.x", x)
        warpData.set(".warps.${warpId}.y", y)
        warpData.set(".warps.${warpId}.z", z)
        warpData.set(".warps.${warpId}.yaw", yaw)
        warpData.set(".warps.${warpId}.pitch", pitch)
        warpData.set(".warps.${warpId}.owner", uuid)
        save()
    }

    // 获得warp
    fun getWarp(warpId: String): Location {
        val world = Bukkit.getWorld(warpData.getString(".warps.${warpId}.world")!!)
        val x = warpData.getLong("warps.${warpId}.x").toDouble()
        val y = warpData.getLong("warps.${warpId}.y").toDouble()
        val z = warpData.getLong("warps.${warpId}.z").toDouble()
        val yaw = warpData.getLong("warps.${warpId}.yaw").toFloat()
        val pitch = warpData.getLong("warps.${warpId}.pitch").toFloat()
        return Location(world, x, y, z, yaw, pitch)
    }

    // 移除warp
    fun rmWarp(warpId: String) {
        warpData.set(".warps.$warpId", null)
        save()
    }

    // 按玩家UUID筛选出玩家创建的所有地标
    fun playerWarpSet(uuid: String): Collection<String>? {
        return allWarpsSet()?.filter { warpData.getString(".warps.${it}.owner") == uuid }
    }

    // 返回装有所有地标的集合
    fun allWarpsSet(): Set<String>? {
        return warpData.getConfigurationSection(".warps")?.getKeys(false)
    }

    // 判断某个地标是否为某个玩家所拥有
    fun warpOwnedBy(player: Player, warpId: String): Boolean {
        // op有最高权限的的话就可以访问所有坐标
        return Config.opOverride(player) || warpData.getString(".warps.${warpId}.owner") == player.uniqueId.toString()
    }

    // 地标是否存在
    fun warpExists(warpId: String): Boolean {
        return warpData.getString(".warps.$warpId") != null
    }

    // 获得所有位置的集合
    fun allPositionsSet(): Set<String>? {
        return positionData.getConfigurationSection(".positions")?.getKeys(false)
    }

    // 获得位置
    fun getPosition(posId: String): String {
        val world = positionData.getString(".positions.${posId}.world")
        val x = positionData.getLong(".positions.${posId}.x")
        val y = positionData.getLong(".positions.${posId}.y")
        val z = positionData.getLong(".positions.${posId}.z")
        return "§9$posId §8[§6$x§8, §6$y§8, §6$z§8, World: §6$world§8]"
    }

    // 记录位置
    fun setPosition(posId: String, location: Location) {
        val world = location.world.name
        val x = location.blockX
        val y = location.blockY
        val z = location.blockZ
        positionData.set(".positions.${posId}.world", world)
        positionData.set(".positions.${posId}.x", x)
        positionData.set(".positions.${posId}.y", y)
        positionData.set(".positions.${posId}.z", z)
        save()
    }

    // 位置是否存在
    fun positionExists(posId: String): Boolean {
        return positionData.getString(".positions.$posId") != null
    }

    // 移除位置
    fun rmPosition(posId: String) {
        positionData.set(".positions.$posId", null)
        save()
    }
}
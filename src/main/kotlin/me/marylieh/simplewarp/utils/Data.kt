package me.marylieh.simplewarp.utils

import me.marylieh.simplewarp.SimpleWarp
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
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
        val x = warpData.getInt("warps.${warpId}.x").toDouble()
        val y = warpData.getInt("warps.${warpId}.y").toDouble()
        val z = warpData.getInt("warps.${warpId}.z").toDouble()
        val yaw = warpData.getInt("warps.${warpId}.yaw").toFloat()
        val pitch = warpData.getInt("warps.${warpId}.pitch").toFloat()
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
    fun warpOwnedBy(uuid: String, warpId: String): Boolean {
        return warpData.getString(".warps.${warpId}.owner") == uuid
    }

    // 地标是否存在
    fun warpExists(warpId: String): Boolean {
        return warpData.getString(".warps.$warpId") != null
    }

    // 写入文件
    fun save() {
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
}
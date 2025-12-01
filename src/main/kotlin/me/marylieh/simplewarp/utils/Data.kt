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
    private lateinit var homeSaveDir: File

    // 存储用户 ID 对应的家数据文件
    private lateinit var uuid2HomeData: HashMap<String, YamlConfiguration>
    fun loadData() {
        val dir = SimpleWarp.plugin.dataFolder // 插件目录

        if (!dir.exists()) {
            dir.mkdirs()
        }

        warpFile = File(dir, "warps.data") // 地标数据路径
        positionFile = File(dir, "positions.data") // 位置数据路径
        homeSaveDir = File(dir, "homes") // 存储家数据的目录路径
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
        if (!homeSaveDir.exists()) {
            homeSaveDir.mkdirs()
        }
        warpData = YamlConfiguration.loadConfiguration(warpFile)
        positionData = YamlConfiguration.loadConfiguration(positionFile)
        uuid2HomeData = HashMap()
    }

    // 把 warp 和 position 数据写入文件
    private fun saveWarpsAndPositions() {
        try {
            warpData.save(warpFile)
            positionData.save(positionFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // 读取家数据
    private fun loadHomeData(uuid: String): YamlConfiguration {
        // 如果缓存中已有该玩家的家数据，直接返回
        if (uuid2HomeData.containsKey(uuid)) {
            return uuid2HomeData[uuid]!!
        }
        val homeFile = File(homeSaveDir, "${uuid}.home")
        val homeData = YamlConfiguration()
        if (homeFile.exists()) {
            try {
                homeData.load(homeFile)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InvalidConfigurationException) {
                e.printStackTrace()
            }
        }
        uuid2HomeData[uuid] = homeData
        return homeData
    }

    // 保存家数据
    private fun saveHomeData(uuid: String) {
        val homeData = uuid2HomeData[uuid] ?: return
        val homeFile = File(homeSaveDir, "${uuid}.home")
        try {
            homeData.save(homeFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // 重载数据
    fun reload(): Boolean {
        try {
            warpData.load(warpFile)
            positionData.load(positionFile)
            // 重置家数据缓存
            uuid2HomeData.clear()
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
        saveWarpsAndPositions()
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
        saveWarpsAndPositions()
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
        saveWarpsAndPositions()
    }

    // 位置是否存在
    fun positionExists(posId: String): Boolean {
        return positionData.getString(".positions.$posId") != null
    }

    // 移除位置
    fun rmPosition(posId: String) {
        positionData.set(".positions.$posId", null)
        saveWarpsAndPositions()
    }

    // 玩家设置 Home
    fun setHome(homeId: String, location: Location, uuid: String) {
        val world: String = location.world.name
        val x = location.x
        val y = location.y
        val z = location.z
        val yaw = location.yaw
        val pitch = location.pitch
        // 取出这个用户的家数据
        val homeData = loadHomeData(uuid)
        homeData.set(".homes.${homeId}.world", world)
        homeData.set(".homes.${homeId}.x", x)
        homeData.set(".homes.${homeId}.y", y)
        homeData.set(".homes.${homeId}.z", z)
        homeData.set(".homes.${homeId}.yaw", yaw)
        homeData.set(".homes.${homeId}.pitch", pitch)
        // 保存这个用户的家数据
        saveHomeData(uuid)
    }

    // 获得 Home
    fun getHome(homeId: String, uuid: String): Location {
        val homeData = loadHomeData(uuid)
        val world = Bukkit.getWorld(homeData.getString(".homes.${homeId}.world")!!)
        val x = homeData.getLong("homes.${homeId}.x").toDouble()
        val y = homeData.getLong("homes.${homeId}.y").toDouble()
        val z = homeData.getLong("homes.${homeId}.z").toDouble()
        val yaw = homeData.getLong("homes.${homeId}.yaw").toFloat()
        val pitch = homeData.getLong("homes.${homeId}.pitch").toFloat()
        return Location(world, x, y, z, yaw, pitch)
    }

    // 移除 Home
    fun rmHome(homeId: String, uuid: String) {
        val homeData = loadHomeData(uuid)
        homeData.set(".homes.$homeId", null)
        saveHomeData(uuid)
    }

    // 获得玩家所有 Home 的集合
    fun allPlayerHomesSet(uuid: String): Collection<String>? {
        val homeData = loadHomeData(uuid)
        return homeData.getConfigurationSection(".homes")?.getKeys(false)
    }

    // 玩家的家数量
    fun playerHomeCount(uuid: String): Int {
        return allPlayerHomesSet(uuid)?.size ?: 0
    }

    // 玩家的家是否存在
    fun playerHomeExists(homeId: String, uuid: String): Boolean {
        val homeData = loadHomeData(uuid)
        return homeData.getString(".homes.$homeId") != null
    }
}
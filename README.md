# SimpleWarp-Forked

This is a forked version of [SimpleWarp](https://github.com/marylieh/SimpleWarpV3) by [@SomeBottle](https://github.com/SomeBottle)  

* Original Author: [@marylieh](https://github.com/marylieh)

SimpleWarp allows you to **create**, **delete** and **use**
warp points. These points are some sort of landmarks for the players to teleport to.

The plugin also has a position saving feature. Positions are like warp points except that it's only used for **showing a
coordinate**.

* Position feature is disabled by default, it can be enabled in config file `configs.yml` .

## Commands

* `/warp <warpname>` - *Teleport you to a warppoint.*
* `/setwarp <warpname>` - *Create a warppoint.*
* `/delwarp <warpname>` - *Delete a warppoint.*
* `/warps` - *List all warppoints*
* `/simplewarp <version | reload>`
    * `version` - *Displays current plugin version*
    * `reload` - *Reload all configs and warp/position data*
* `/position <positionName | del | list>`
    * `positionName` - *Shows the coordinates of the position. If the position doesn't exist, the position will be set
      to your current location*
    * `del` - *Remove a position*
    * `list` - *Lists all available positions*

## Permissions

* `simplewarp.warp` - Allows you to use the **/warp** command.
* `simplewarp.warp.nodelay` - Allows you to **ignore** the delay before teleportation
* `simplewarp.warp.allowmove` - Allows you to **move** while waiting to teleport
* `simplewarp.delwarp` - Allows you to use the **/delwarp** command.
* `simplewarp.setwarp` - Allows you to use the **/setwarp** command.
* `simplewarp.warps` - Allows you to use the **/warps** command.
* `simplewarp.position` - Allows you to use the **/position** command.
* `simplewarp.position.view` - Allows you to use the **/position \<name>** command.
* `simplewarp.position.create` - Allows you to use the **/position \<name>**
* `simplewarp.position.del` - Allows you to use the **/position del \<name>**
* `simplewarp.position.list` - Allows you to use the **/position list**

*To give the permissions to players you need a Permission System like [LuckPerms](https://luckperms.net/) or [GroupManager](https://github.com/ElgarL/GroupManager).*


# SimpleWarp-Forked

This is a forked version of [SimpleWarp](https://github.com/marylieh/SimpleWarpV3)
by [@SomeBottle](https://github.com/SomeBottle)

* Original Author: [@marylieh](https://github.com/marylieh)

SimpleWarp allows you to **create**, **delete** and **use**
*warp* and *home* points. Warp points are some sort of landmarks for the players to teleport to, while home points are personal warp points that only the player who created them can use.  

The plugin also has a position saving feature. Positions are like warp points except that it's only used for **showing a
coordinate**.

* Position feature is disabled by default, it can be enabled in config file `configs.yml` .

## Configs

`configs.yml`

```yaml
op-full-access: false
delay-before-tp: 0
no-move-allowed-before-tp: false
position-system: false
player-warps-only: false
max-homes-per-player: 0
```

- `op-full-access` - If this option is set to `true`, server operator will have full access to all available features of
  SimpleWarp.
    * **Notice**: Permission plugins such as **GroupManager** may have an identical option, in this
      case, `op-full-access` option might have no effect because it's overrided by the permission plugin.

- `delay-before-tp` - The delay time **in seconds** before the teleportation start, with this option set with a value
  greater than 0, player will have to wait for the specific time before being teleported.
    * **Notice**: This can be bypassed if one has the permission `simplewarp.warp.nodelay`

- `no-move-allowed-before-tp` - This option is valid when `delay-before-tp > 0`. If it's set to `true`, any movement
  from the player being teleported during the delay period will terminate the teleportation.
    * **Notice**: Can be bypassed with the permission `simplewarp.warp.allowmove`

- `position-system` - Used to enable the `/position` feature.

- `player-warps-only` - With this option enabled, players can only teleport to the warp points of their own.  

- `max-homes-per-player` - The maximum number of homes a player can set.  

## Commands

* `/warp <warpname>` - *Teleport you to a warp point.*
* `/setwarp <warpname>` - *Create a warp point.*
* `/delwarp <warpname>` - *Delete a warp point.*
* `/warps` - *List all warp points that you have access to.*
* `/home <homename>` - *Teleport you to your home point.*
* `/sethome <homename>` - *Create a home point.*
* `/delhome <homename>` - *Delete a home point.*
* `/homes` - *List all your home points*
* `/simplewarp <version | reload>`
    * `version` - *Displays current plugin version*
    * `reload` - *Reload all configs and warp/position data*
* `/position <positionName | del | list>`
    * `positionName` - *Shows the coordinates of the position. If the position doesn't exist, the position will be set
      to your current location*
    * `del` - *Remove a position*
    * `list` - *Lists all available positions*

## Permissions

* `simplewarp.warp` - Allows the player to use the **/warp** command.
* `simplewarp.warp.nodelay` - Allows the player to **ignore** the delay before teleportation
* `simplewarp.warp.allowmove` - Allows the player to **move** while waiting to teleport
* `simplewarp.delwarp` - Allows the player to use the **/delwarp** command.
* `simplewarp.setwarp` - Allows the player to use the **/setwarp** command.
* `simplewarp.warps` - Allows the player to use the **/warps** command.
* `simplewarp.home` - Allows the player to use the **/home** command.
* `simplewarp.sethome` - Allows the player to use the **/sethome** command.
* `simplewarp.delhome` - Allows the player to use the **/delhome** command.
* `simplewarp.homes` - Allows the player to use the **/homes** command.
* `simplewarp.position` - Allows the player to use the **/position** command.
* `simplewarp.position.view` - Allows the player to use the **/position \<name>** command.
* `simplewarp.position.create` - Allows the player to use the **/position \<name>**
* `simplewarp.position.del` - Allows the player to use the **/position del \<name>**
* `simplewarp.position.list` - Allows the player to use the **/position list**
* `simplewarp.reload` - Allows the player to reload all configs and warp / home / position data

*To give the permissions to players you need a Permission System like [LuckPerms](https://luckperms.net/)
or [GroupManager](https://github.com/ElgarL/GroupManager).*


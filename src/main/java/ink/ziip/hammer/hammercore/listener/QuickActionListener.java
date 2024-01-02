package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import ink.ziip.hammer.hammercore.api.util.Utils;
import ink.ziip.hammer.hammercore.manager.ConfigManager;
import org.bukkit.*;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.regex.Pattern;

import static ink.ziip.hammer.hammercore.manager.ConfigManager.UTIL_DISABLE_ELYTRA;

public class QuickActionListener extends BaseListener {

    @EventHandler
    public void onPlayerSwapItemToOpenMenu(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            event.setCancelled(true);
            if (player.hasPermission("group.player")) {
                player.performCommand("dm open main_menu");
            } else {
                player.performCommand("tutorial 5");
            }
        }
    }

    @EventHandler
    public void onPlayerDropItemToToggleBend(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            event.setCancelled(true);
            player.performCommand("bend toggle");
        }
    }

    @EventHandler
    public void onPlayerInteractWithMenu(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        if (event.getItem() == null) {
            return;
        }
        if (event.getItem().getType() != Material.CLOCK) {
            return;
        }
        Player player = event.getPlayer();

        if (player.hasPermission("group.player")) {
            player.performCommand("dm open main_menu");
        } else {
            player.performCommand("tutorial 5");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (ConfigManager.UTIL_DISABLE_JOIN_MESSAGE)
            event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (ConfigManager.UTIL_DISABLE_QUIT_MESSAGE)
            event.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (ConfigManager.UTIL_AUTO_RESPAWN_ENABLED) {
            Player player = event.getEntity();
            new BukkitRunnable() {
                public void run() {
                    player.spigot().respawn();
                    player.teleport(ConfigManager.UTIL_AUTO_RESPAWN_LOCATION);
                }
            }.runTaskLater(HammerCore.getInstance(), 1L);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractItemFrameToTeleport(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame itemFrame) {
            Player player = event.getPlayer();
            if (player.isSneaking()) {
                ItemMeta itemMeta = itemFrame.getItem().getItemMeta();
                if (itemMeta != null) {
                    if (itemMeta.hasLore()) {
                        for (String lore : itemMeta.getLore()) {
                            if (Utils.isTeleportationItem(lore)) {
                                lore = ChatColor.stripColor(lore);
                                String warp = lore.replaceAll("\\[[0-9]{3}-", "");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "huskhomes:warp " + warp.replaceAll("]", "") + " " + player.getName());
                                event.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBreakBlockToVoid(BlockBreakEvent event) {
        if (!UTIL_DISABLE_ELYTRA) {
            return;
        }

        Player player = event.getPlayer();
        Location location = player.getLocation().add(0, -3, 0);
        World world = location.getWorld();
        if (world != null) {
            if (player.getLocation().getY() >= event.getBlock().getLocation().getY()) {
                if (world.getBlockAt(location).getType() == Material.AIR) {
                    player.sendMessage(Utils.translateColorCodes("&7[&c&l!&7] &6你脚下的方块下面是空气！"));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDropItemsInSpawn(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        HammerUser hammerUser = HammerUser.getUser(player);

        BoundingBox boundingBox = new BoundingBox(56, 22, 120, 103, 82, 217);
        if (boundingBox.contains(location.getX(), location.getY(), location.getZ()) && location.getWorld() != null && location.getWorld().getName().equals("world")) {
            if (System.currentTimeMillis() - hammerUser.getDropItemsTime() > 120000) {
                hammerUser.setDropItemsTime(System.currentTimeMillis());
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(Utils.translateColorCodes("&d[拉菲] &7嗷呜～ " + player.getName() + " 在主城乱丢垃圾，拉菲将在 1分钟 后把垃圾吃掉！"));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (event.getItemDrop().isValid())
                                event.getItemDrop().remove();
                        }
                    }.runTaskLater(HammerCore.getInstance(), 1200);
                }
            }
        }
    }
}

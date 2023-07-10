package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import ink.ziip.hammer.hammercore.api.util.Utils;
import ink.ziip.hammer.hammercore.manager.ConfigManager;
import ink.ziip.hammer.hammercore.manager.MessageManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.regex.Pattern;

public class ProtectionListener extends BaseListener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onVillagerCareerChange(VillagerCareerChangeEvent event) {
        if (!ConfigManager.WORLD_PROTECTION_NO_CARTOGRAPHER)
            return;
        if (event.getProfession().equals(Villager.Profession.CARTOGRAPHER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCreeperExplode(EntityExplodeEvent event) {
        World world = event.getLocation().getWorld();
        if (world == null)
            return;
        if (ConfigManager.WORLD_PROTECTION_ANTI_EXPLOSION_WORLD_LIST.contains(world.getName())) {
            if (event.getEntity() instanceof Creeper) {
                event.blockList().clear();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        World world = event.getBlock().getWorld();
        if (!(event.getEntity() instanceof Enderman))
            return;
        if (ConfigManager.WORLD_PROTECTION_ANTI_GRIEFING_WORLD_LIST.contains(world.getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityBreakFarmLand(EntityInteractEvent event) {
        if (!ConfigManager.WORLD_PROTECTION_PREVENT_FARM_LAND_DESTROY)
            return;
        if ((event.getEntity() instanceof Player))
            return;

        Block block = event.getBlock();
        if (block.getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!ConfigManager.WORLD_PROTECTION_PREVENT_FARM_LAND_DESTROY)
            return;
        if (event.getAction() == Action.PHYSICAL) {
            Block block = event.getClickedBlock();
            if (block == null)
                return;
            if (block.getType() == Material.FARMLAND) {
                if (event.getPlayer().hasPermission("hammercore.utils.protectfarm")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String pattern = "[ꀀ-\uA48F]";

        if (Pattern.matches(pattern, event.getMessage())) {
            event.setCancelled(true);
            player.sendMessage(Utils.translateColorCodes(MessageManager.NO_PERMISSION));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.hasPermission("hammercore.utils.keepinventory")
                || (player.getLocation().getWorld() != null && player.getLocation().getWorld().getName().equals("nether") && player.getLocation().getY() >= 127)) {
            event.getDrops().clear();
            event.setDroppedExp(0);
            event.setKeepInventory(true);
            event.setKeepLevel(true);
        }
    }
}

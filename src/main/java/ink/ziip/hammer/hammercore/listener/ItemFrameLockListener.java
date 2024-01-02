package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import ink.ziip.hammer.hammercore.api.object.lock.ItemFrameLock;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import ink.ziip.hammer.hammercore.api.util.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFrameLockListener extends BaseListener {

    @EventHandler
    public void onPlayerInteractItemFrameLock(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof ItemFrame itemFrame) {
            if (player.isSneaking()) {
                ItemMeta itemMeta = itemFrame.getItem().getItemMeta();
                if (itemMeta != null) {
                    if (itemMeta.hasLore()) {
                        for (String lore : itemMeta.getLore()) {
                            if (Utils.isTeleportationItem(lore)) {
                                return;
                            }
                        }
                    }
                }
            }

            HammerUser hammerUser = HammerUser.getUser(player);
            ItemFrameLock itemFrameLock = ItemFrameLock.getItemFrameLock(itemFrame);
            if (player.isSneaking() && itemFrame.getItem().getType() != Material.AIR) {
                if (!itemFrameLock.hasOwner()) {
                    itemFrameLock.setOwner(player);
                    hammerUser.sendActionBar("&c你成为了这个展示框的所有者", false);
                    event.setCancelled(true);
                    return;
                } else {
                    if (itemFrameLock.isOwner(player)) {
                        itemFrameLock.removeOwner();
                        hammerUser.sendActionBar("&c解除了展示框的锁定", false);
                    } else {
                        hammerUser.sendActionBar("&7这个展示框的所有者为：" + itemFrameLock.getOwnerName(), false);
                    }
                }
                event.setCancelled(true);
                return;
            }
            if (!itemFrameLock.isOwner(player)) {
                hammerUser.sendActionBar("&7这个展示框的所有者为：" + itemFrameLock.getOwnerName(), false);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerBreakItemFrame(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ItemFrame itemFrame) {
            ItemFrameLock itemFrameLock = ItemFrameLock.getItemFrameLock(itemFrame);

            if (itemFrameLock.hasOwner()) {
                if (event.getDamager() instanceof Player player) {
                    HammerUser hammerUser = HammerUser.getUser(player);
                    if (itemFrameLock.isOwner(player)) {
                        return;
                    }
                    hammerUser.sendActionBar("&7这个展示框的所有者为：" + itemFrameLock.getOwnerName(), false);
                    event.setCancelled(true);
                    return;
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerBreakItemFrame(EntityDamageByBlockEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ItemFrame itemFrame) {
            ItemFrameLock itemFrameLock = ItemFrameLock.getItemFrameLock(itemFrame);

            if (itemFrameLock.hasOwner()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // TODO
    }
}

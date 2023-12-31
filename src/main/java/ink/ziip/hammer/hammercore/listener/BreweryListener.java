package ink.ziip.hammer.hammercore.listener;

import com.dre.brewery.api.BreweryApi;
import com.dre.brewery.api.events.brew.BrewDrinkEvent;
import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import ink.ziip.hammer.hammercore.manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

public class BreweryListener extends BaseListener {

    @EventHandler
    public void onPlayerBrew(BrewDrinkEvent event) {
        Player player = event.getPlayer();
        if (player == null)
            return;
        HammerUser hammerUser = HammerUser.getUser(player);
        hammerUser.sendMessage(MessageManager.DRINKING_ALCOHOL_IS_BAD_FOR_YOUR_HEALTH);

        if (event.getBrew().getCurrentRecipe().getName(event.getBrew().getQuality()).equals("优质孟婆汤")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "bend remove " + player.getName());
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 元素祭坛 " + player.getName());
            Bukkit.broadcastMessage(hammerUser.setPlaceholders(MessageManager.MENG_PO_SOUP_TIPS));
            return;
        }

        if (event.getBrew().getCurrentRecipe().getName(event.getBrew().getQuality()).equals("红酒")) {
            if (event.getItemMeta().hasLore()) {
                for (String lore : event.getItemMeta().getLore()) {
                    String regex = ".*XL木桶熟成.*";
                    if (Pattern.matches(regex, ChatColor.stripColor(lore))) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "si give 23中秋巡礼-风滚草镇 1 " + player.getName());
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void PlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.CAULDRON
                    || event.getClickedBlock().getType() == Material.WATER_CAULDRON)) {
                if (event.getItem() != null && event.getItem().getType() == Material.POTION) {
                    if (BreweryApi.isBrew(event.getItem())) {
                        event.setCancelled(true);
                        event.getItem().setType(Material.GLASS_BOTTLE);
                        ItemStack itemStack = new ItemStack(Material.GLASS_BOTTLE);
                        event.getItem().setItemMeta(itemStack.getItemMeta());
                    }
                }
            }
        }
    }
}

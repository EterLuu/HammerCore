package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener extends BaseListener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HammerUser.getUser(player);

        // Remove invalid potion effects
        player.getActivePotionEffects().forEach(
                potionEffect -> {
                    if (potionEffect.getDuration() > 6000) {
                        player.removePotionEffect(potionEffect.getType());
                    }
                });
    }

    // Remove user cache when leaving to avoid issue
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        HammerUser.removeHammerUser(player);
    }
}

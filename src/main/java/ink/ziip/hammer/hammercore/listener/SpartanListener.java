package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class SpartanListener extends BaseListener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerViolation(PlayerViolationEvent event) {
        HammerUser hammerUser = HammerUser.getUser(event.getPlayer());
        if (hammerUser.isBedrockPlayer()) {
            event.setCancelled(true);
        }
    }
}

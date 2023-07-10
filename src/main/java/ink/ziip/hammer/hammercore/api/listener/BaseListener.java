package ink.ziip.hammer.hammercore.api.listener;

import ink.ziip.hammer.hammercore.HammerCore;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class BaseListener implements Listener {

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, HammerCore.getInstance());
    }

    public void unRegister() {
        HandlerList.unregisterAll(this);
    }
}

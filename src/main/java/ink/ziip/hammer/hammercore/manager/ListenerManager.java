package ink.ziip.hammer.hammercore.manager;

import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import ink.ziip.hammer.hammercore.api.manager.BaseManager;
import ink.ziip.hammer.hammercore.listener.*;
import ink.ziip.hammer.hammercore.listener.SnowballListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class ListenerManager extends BaseManager {

    private final BaseListener bossBarTaskListener;
    private final BaseListener mineCartAcceleratorListener;
    private final BaseListener protectionListener;
    private final BaseListener breweryListener;
    private final BaseListener peelingRecoveryListener;
    private final BaseListener quickActionListener;
    private final BaseListener joinQuitListener;
    private final BaseListener snowBallListener;

    public ListenerManager() {
        bossBarTaskListener = new BossBarTaskListener();
        mineCartAcceleratorListener = new MineCartListener();
        protectionListener = new ProtectionListener();
        breweryListener = new BreweryListener();
        peelingRecoveryListener = new PeelingRecoveryListener();
        quickActionListener = new QuickActionListener();
        joinQuitListener = new JoinQuitListener();
        snowBallListener = new SnowballListener();
    }

    @Override
    public void load() {

        joinQuitListener.register();
        bossBarTaskListener.register();
        mineCartAcceleratorListener.register();
        protectionListener.register();

        if (Bukkit.getPluginManager().getPlugin("Brewery") != null
                && Bukkit.getPluginManager().getPlugin("ProjectKorra") != null) {
            breweryListener.register();

            if (ConfigManager.PROJECTKORRA_ALL_EARTH_BINDER) {
                ProjectKorraListener projectKorraListener = new ProjectKorraListener();
                projectKorraListener.register();
            }
        }

        if (Bukkit.getPluginManager().getPlugin("Spartan") != null) {
            SpartanListener spartanListener = new SpartanListener();
            spartanListener.register();
        }

        if (ConfigManager.UTIL_PEELING_RECOVERY) {
            peelingRecoveryListener.register();
        }

        quickActionListener.register();
        snowBallListener.register();

    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(HammerCore.getInstance());
    }

    @Override
    public void reload() {

    }
}

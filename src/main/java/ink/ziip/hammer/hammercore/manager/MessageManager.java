package ink.ziip.hammer.hammercore.manager;

import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.manager.BaseManager;
import ink.ziip.hammer.hammercore.api.util.Utils;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager extends BaseManager {

    private FileConfiguration config;

    public static String NO_PERMISSION;
    public static String DRINKING_ALCOHOL_IS_BAD_FOR_YOUR_HEALTH;
    public static String MENG_PO_SOUP_TIPS;
    public static String WHAT_ARE_YOU_DOING;
    public static String NO_ENTITY_WAS_TARGETED;

    public MessageManager() {
        config = HammerCore.getInstance().getConfig();
    }

    @Override
    public void load() {
        loadConfig();
    }

    @Override
    public void unload() {
    }

    @Override
    public void reload() {
        HammerCore.getInstance().reloadConfig();
        load();
    }

    private void loadConfig() {
        config = HammerCore.getInstance().getConfig();
        loadMessages();
    }

    private void loadMessages() {
        NO_PERMISSION = Utils.translateColorCodes(config.getString("message.no-permission"));
        DRINKING_ALCOHOL_IS_BAD_FOR_YOUR_HEALTH = Utils.translateColorCodes(config.getString("message.drinking-alcohol-is-bad-for-your-health"));
        MENG_PO_SOUP_TIPS = Utils.translateColorCodes(config.getString("message.meng-po-soup-tips"));
        WHAT_ARE_YOU_DOING = Utils.translateColorCodes(config.getString("message.what-are-you-doing"));
        NO_ENTITY_WAS_TARGETED = Utils.translateColorCodes(config.getString("message.no-entity-was-targeted"));
    }
}

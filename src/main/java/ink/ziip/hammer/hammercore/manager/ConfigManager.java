package ink.ziip.hammer.hammercore.manager;

import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.manager.BaseManager;
import ink.ziip.hammer.hammercore.api.util.Utils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

public class ConfigManager extends BaseManager {

    private final FileConfiguration config;

    public static Boolean INFO_BAR_ENABLED;
    public static String INFO_BAR_CONTENT_JAVA_DEFAULT;
    public static Map<String, String> INFO_BAR_CONTENT_JAVA_WORLD_LIST;
    public static String INFO_BAR_CONTENT_BEDROCK_DEFAULT;
    public static Map<String, String> INFO_BAR_CONTENT_BEDROCK_WORLD_LIST;
    public static String INFO_BAR_WORLD_NAME_JAVA_DEFAULT;
    public static Map<String, String> INFO_BAR_WORLD_NAME_JAVA_WORLD_LIST;
    public static String INFO_BAR_WORLD_NAME_BEDROCK_DEFAULT;
    public static Map<String, String> INFO_BAR_WORLD_NAME_BEDROCK_WORLD_LIST;
    public static String INFO_BAR_INFO_SEASON_DEFAULT;
    public static Map<String, String> INFO_BAR_INFO_SEASON_LIST;
    public static String INFO_BAR_INFO_WEATHER_DEFAULT;
    public static Map<String, String> INFO_BAR_INFO_WEATHER_LIST;
    public static List<String> WORLD_PROTECTION_ANTI_EXPLOSION_WORLD_LIST;
    public static List<String> WORLD_PROTECTION_ANTI_GRIEFING_WORLD_LIST;
    public static Boolean WORLD_PROTECTION_NO_CARTOGRAPHER;
    public static Boolean WORLD_PROTECTION_PREVENT_FARM_LAND_DESTROY;
    public static Boolean FLOODGATE_HIDE_PARTICLES;
    public static Boolean UTIL_ARCHITECTURE_LORE_HIDDEN;
    public static List<String> UTIL_ARCHITECTURE_LORE_HIDDEN_CONTENT;
    public static Boolean UTIL_PEELING_RECOVERY;
    public static Boolean UTIL_DISABLE_JOIN_MESSAGE;
    public static Boolean UTIL_DISABLE_QUIT_MESSAGE;

    public static Boolean PROJECTKORRA_ALL_EARTH_BINDER;

    public ConfigManager() {
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
        loadInfoBarConfig();
    }

    private void loadInfoBarConfig() {
        INFO_BAR_ENABLED = config.getBoolean("info-bar.enabled", true);

        INFO_BAR_CONTENT_JAVA_DEFAULT = config.getString("info-bar.content.java.default", "#BFCFD4&rꐐ");
        INFO_BAR_CONTENT_JAVA_WORLD_LIST = Utils.splitList(config.getStringList("info-bar.content.java.world"));

        INFO_BAR_CONTENT_BEDROCK_DEFAULT = config.getString("info-bar.content.bedrock.default", "");
        INFO_BAR_CONTENT_BEDROCK_WORLD_LIST = Utils.splitList(config.getStringList("info-bar.content.bedrock.world"));

        INFO_BAR_WORLD_NAME_JAVA_DEFAULT = config.getString("info-bar.world-name.java.default", "");
        INFO_BAR_WORLD_NAME_JAVA_WORLD_LIST = Utils.splitList(config.getStringList("info-bar.world-name.java.world"));

        INFO_BAR_WORLD_NAME_BEDROCK_DEFAULT = config.getString("info-bar.world-name.bedrock.default", "");
        INFO_BAR_WORLD_NAME_BEDROCK_WORLD_LIST = Utils.splitList(config.getStringList("info-bar.world-name.bedrock.world"));

        INFO_BAR_INFO_SEASON_DEFAULT = config.getString("info-bar.info.season.default", "");
        INFO_BAR_INFO_SEASON_LIST = Utils.splitList(config.getStringList("info-bar.info.season.season"));

        INFO_BAR_INFO_WEATHER_DEFAULT = config.getString("info-bar.info.weather.default", "");
        INFO_BAR_INFO_WEATHER_LIST = Utils.splitList(config.getStringList("info-bar.info.weather.weather"));

        WORLD_PROTECTION_ANTI_EXPLOSION_WORLD_LIST = config.getStringList("world-protection.anti-explosion");
        WORLD_PROTECTION_ANTI_GRIEFING_WORLD_LIST = config.getStringList("world-protection.anti-griefing");
        WORLD_PROTECTION_NO_CARTOGRAPHER = config.getBoolean("world-protection.no-cartographer", true);
        WORLD_PROTECTION_PREVENT_FARM_LAND_DESTROY = config.getBoolean("world-protection.prevent-farm-land-destroy", true);

        FLOODGATE_HIDE_PARTICLES = config.getBoolean("floodgate.hide-particles", true);

        UTIL_ARCHITECTURE_LORE_HIDDEN = config.getBoolean("util.architecture-lore.hidden", true);
        UTIL_ARCHITECTURE_LORE_HIDDEN_CONTENT = config.getStringList("util.architecture-lore.content");
        UTIL_PEELING_RECOVERY = config.getBoolean("util.peeling-recovery", true);
        UTIL_DISABLE_JOIN_MESSAGE = config.getBoolean("util.disable-join-message", true);
        UTIL_DISABLE_QUIT_MESSAGE = config.getBoolean("util.disable-quit-message", true);

        PROJECTKORRA_ALL_EARTH_BINDER = config.getBoolean("projectkorra.all-earth-binder", false);
    }
}

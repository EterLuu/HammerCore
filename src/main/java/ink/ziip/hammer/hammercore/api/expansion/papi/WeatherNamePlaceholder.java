package ink.ziip.hammer.hammercore.api.expansion.papi;

import ink.ziip.hammer.hammercore.manager.ConfigManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class WeatherNamePlaceholder extends BasePlaceholder {

    @Override
    public @NotNull String getIdentifier() {
        return "hammercoreweather";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        if (params.equalsIgnoreCase("bedrock")) {
            return PlaceholderAPI.setPlaceholders(offlinePlayer, "%seasons_weather%");
        }

        if (params.equalsIgnoreCase("java")) {
            String season = PlaceholderAPI.setPlaceholders(offlinePlayer, "%seasons_weather%");
            season = ChatColor.stripColor(season);

            if (ConfigManager.INFO_BAR_INFO_WEATHER_LIST.containsKey(season)) {
                return ConfigManager.INFO_BAR_INFO_WEATHER_LIST.get(season);
            }

            return ConfigManager.INFO_BAR_INFO_WEATHER_DEFAULT;
        }

        // Placeholder is unknown by the Expansion
        return null;
    }
}

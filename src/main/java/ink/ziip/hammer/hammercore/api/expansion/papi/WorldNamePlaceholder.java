package ink.ziip.hammer.hammercore.api.expansion.papi;

import ink.ziip.hammer.hammercore.manager.ConfigManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldNamePlaceholder extends BasePlaceholder {

    @Override
    public @NotNull String getIdentifier() {
        return "hammercoreworld";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        if (params.equalsIgnoreCase("bedrock")) {
            Player player = offlinePlayer.getPlayer();
            if (player == null)
                return null;
            World world = player.getWorld();

            if (ConfigManager.INFO_BAR_WORLD_NAME_BEDROCK_WORLD_LIST.containsKey(world.getName()))
                return ConfigManager.INFO_BAR_WORLD_NAME_BEDROCK_WORLD_LIST.get(world.getName());

            return ConfigManager.INFO_BAR_WORLD_NAME_BEDROCK_DEFAULT;
        }

        if (params.equalsIgnoreCase("java")) {
            Player player = offlinePlayer.getPlayer();
            if (player == null)
                return null;
            World world = player.getWorld();

            if (ConfigManager.INFO_BAR_WORLD_NAME_JAVA_WORLD_LIST.containsKey(world.getName()))
                return ConfigManager.INFO_BAR_WORLD_NAME_JAVA_WORLD_LIST.get(world.getName());

            return ConfigManager.INFO_BAR_WORLD_NAME_JAVA_DEFAULT;
        }

        // Placeholder is unknown by the Expansion
        return null;
    }
}

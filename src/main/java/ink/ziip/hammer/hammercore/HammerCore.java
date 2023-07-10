package ink.ziip.hammer.hammercore;

import ink.ziip.hammer.hammercore.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HammerCore extends JavaPlugin {

    private static HammerCore instance;
    private ConfigManager configManager;
    private MessageManager messageManager;
    private CommandManager commandManager;
    private PacketListenerManager packetListenerManager;
    private PlaceholderExpansionManager placeholderExpansionManager;
    private TaskManager taskManager;
    private ListenerManager listenerManager;

    public HammerCore() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().warning("Could not find PlaceholderAPI!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        // Configuration loading
        configManager = new ConfigManager();
        configManager.load();

        // Messages loading
        messageManager = new MessageManager();
        messageManager.load();

        // Placeholders loading
        placeholderExpansionManager = new PlaceholderExpansionManager();
        placeholderExpansionManager.load();

        // Commands loading
        commandManager = new CommandManager();
        commandManager.load();

        // Packet listeners loading
        packetListenerManager = new PacketListenerManager();
        packetListenerManager.load();

        // Tasks loading
        taskManager = new TaskManager();
        taskManager.load();

        // Listeners loading
        listenerManager = new ListenerManager();
        listenerManager.load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        configManager.unload();
        messageManager.unload();
        commandManager.unload();
        packetListenerManager.unload();
        placeholderExpansionManager.unload();
        taskManager.unload();
        listenerManager.unload();
    }

    public static HammerCore getInstance() {
        return instance;
    }
}

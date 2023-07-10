package ink.ziip.hammer.hammercore.manager;

import ink.ziip.hammer.hammercore.api.manager.BaseManager;
import ink.ziip.hammer.hammercore.command.MainCommand;
import ink.ziip.hammer.hammercore.command.sub.AdminCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

public class CommandManager extends BaseManager {

    private MainCommand mainCommand;
    private final PluginCommand pluginCommand;

    public CommandManager() {
        this.mainCommand = new MainCommand();
        this.pluginCommand = Bukkit.getPluginCommand("hammercore");
    }

    @Override
    public void load() {
        this.mainCommand = new MainCommand();

        // Sub commands adding
        this.mainCommand.addSubCommand(new AdminCommand());

        if (this.pluginCommand != null) {
            this.pluginCommand.setExecutor(this.mainCommand);
            this.pluginCommand.setTabCompleter(this.mainCommand);
        }
    }

    @Override
    public void unload() {

    }

    @Override
    public void reload() {

    }
}

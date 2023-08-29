package ink.ziip.hammer.hammercore.manager;

import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.manager.BaseManager;
import ink.ziip.hammer.hammercore.command.MainCommand;
import ink.ziip.hammer.hammercore.command.MenuCommand;
import ink.ziip.hammer.hammercore.command.sub.AdminCommand;
import ink.ziip.hammer.hammercore.command.sub.RideCommand;
import ink.ziip.hammer.hammercore.command.sub.SayCommand;
import ink.ziip.hammer.hammercore.command.sub.ToggleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class CommandManager extends BaseManager {

    private MainCommand mainCommand;
    private final MenuCommand menuCommand;
    private final PluginCommand corePluginCommand;
    private final PluginCommand menuPluginCommand;

    public CommandManager() {
        this.mainCommand = new MainCommand();
        this.menuCommand = new MenuCommand();

        this.corePluginCommand = Bukkit.getPluginCommand("hammercore");
        this.menuPluginCommand = Bukkit.getPluginCommand("menu");
    }

    @Override
    public void load() {
        this.mainCommand = new MainCommand();

        // Sub commands adding
        this.mainCommand.addSubCommand(new AdminCommand());
        this.mainCommand.addSubCommand(new RideCommand());
        this.mainCommand.addSubCommand(new SayCommand());
        this.mainCommand.addSubCommand(new ToggleCommand());

        if (this.corePluginCommand != null) {
            this.corePluginCommand.setExecutor(this.mainCommand);
            this.corePluginCommand.setTabCompleter(this.mainCommand);
        }

        menuPluginCommand.setExecutor(this.menuCommand);
        menuPluginCommand.setTabCompleter(this.menuCommand);
    }

    @Override
    public void unload() {
        Field commandMapField;
        try {
            commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());

            corePluginCommand.unregister(commandMap);
            menuPluginCommand.unregister(commandMap);
        } catch (Exception e) {
            HammerCore.getInstance().getLogger().log(Level.WARNING, "Unregister commands failed.");
        }
    }

    @Override
    public void reload() {

    }
}

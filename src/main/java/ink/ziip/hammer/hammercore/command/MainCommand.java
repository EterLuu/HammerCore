package ink.ziip.hammer.hammercore.command;

import ink.ziip.hammer.hammercore.api.command.BaseSubCommand;
import ink.ziip.hammer.hammercore.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final Map<String, BaseSubCommand> subCommandMap;

    public MainCommand() {
        this.subCommandMap = new ConcurrentHashMap<>();
    }

    public void addSubCommand(BaseSubCommand subCommand) {
        subCommandMap.put(subCommand.getCommandName(), subCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            return true;
        }
        if (!sender.hasPermission("hammercore." + args[0])) {
            sender.sendMessage(MessageManager.NO_PERMISSION);
            return true;
        }

        BaseSubCommand subCommand = subCommandMap.get(args[0]);
        if (subCommand != null) {
            return subCommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> returnList = new java.util.ArrayList<>(subCommandMap.keySet().stream().toList());
            returnList.removeIf(s -> !s.startsWith(args[0]) || !sender.hasPermission("hammercore." + args[0]));
            return returnList;
        }

        BaseSubCommand subCommand = subCommandMap.get(args[0]);
        if (subCommand != null) {
            return subCommand.onTabComplete(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        }

        return Collections.singletonList("");
    }
}

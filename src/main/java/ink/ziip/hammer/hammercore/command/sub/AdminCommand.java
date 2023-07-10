package ink.ziip.hammer.hammercore.command.sub;

import ink.ziip.hammer.hammercore.api.command.BaseSubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AdminCommand extends BaseSubCommand {

    private final String[] commandList = new String[]{
            "talk"
    };

    public AdminCommand() {
        super("admin");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            return true;
        }
        if (args[0].equals("talk")) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player != null) {
                player.chat(args[2]);
                return true;
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> returnList = Arrays.asList(commandList);
            returnList.removeIf(s -> !s.startsWith(args[0]));
            return returnList;
        }
        if (args[0].equals("talk")) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }

        return Collections.singletonList("");
    }
}

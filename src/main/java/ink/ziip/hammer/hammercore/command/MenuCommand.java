package ink.ziip.hammer.hammercore.command;

import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MenuCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        HammerUser hammerUser = HammerUser.getUser(sender);
        Player player = hammerUser.getPlayer();

        if (player != null) {
            if (player.hasPermission("group.player")) {
                player.performCommand("dm open main_menu");
            } else {
                player.performCommand("tutorial 6");
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}

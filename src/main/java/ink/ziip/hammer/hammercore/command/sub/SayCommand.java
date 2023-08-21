package ink.ziip.hammer.hammercore.command.sub;

import ink.ziip.hammer.hammercore.api.command.BaseSubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SayCommand extends BaseSubCommand {

    public SayCommand() {
        super("say");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            Player player = Bukkit.getPlayer(sender.getName());
            if (player != null) {
                player.chat(args[0]);
                return true;
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.singletonList("");
    }
}

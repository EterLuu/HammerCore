package ink.ziip.hammer.hammercore.command.sub;

import ink.ziip.hammer.hammercore.api.command.BaseSubCommand;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ToggleCommand extends BaseSubCommand {

    private final String[] commandList = new String[]{
            "minecart",
    };

    public ToggleCommand() {
        super("toggle");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            return true;
        }
        if (args[0].equals("minecart")) {
            HammerUser hammerUser = HammerUser.getUser(sender);
            hammerUser.setRemoveMineCartOnLeaving(!hammerUser.isRemoveMineCartOnLeaving());
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

        return Collections.singletonList("");
    }
}

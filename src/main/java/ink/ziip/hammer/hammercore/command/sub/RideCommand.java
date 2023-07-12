package ink.ziip.hammer.hammercore.command.sub;

import ink.ziip.hammer.hammercore.api.command.BaseSubCommand;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RideCommand extends BaseSubCommand {

    public RideCommand() {
        super("ride");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        HammerUser hammerUser = HammerUser.getUser(sender);
        if (hammerUser == null)
            return true;

        Entity entity = hammerUser.getTargetEntity();
        if (entity == null)
            return true;

        entity.addPassenger(hammerUser.getPlayer());
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}

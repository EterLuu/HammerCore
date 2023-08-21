package ink.ziip.hammer.hammercore.command.sub;

import ink.ziip.hammer.hammercore.api.command.BaseSubCommand;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import ink.ziip.hammer.hammercore.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RideCommand extends BaseSubCommand {

    private final String[] commandList = new String[]{
            "clean"
    };

    public RideCommand() {
        super("ride");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        HammerUser hammerUser = HammerUser.getUser(sender);
        Player player = hammerUser.getPlayer();

        if (args.length < 1) {
            if (!(sender instanceof Player)) {
                hammerUser.sendMessage(MessageManager.WHAT_ARE_YOU_DOING);
                return true;
            }

            Entity entity = hammerUser.getTargetEntity();
            if (entity == null) {
                hammerUser.sendMessage(MessageManager.NO_ENTITY_WAS_TARGETED);
                return true;
            }

            if (player != null) {
                if (entity instanceof Projectile)
                    return true;
                entity.addPassenger(player);
            }

            return true;
        }

        if (args[0].equals("clean")) {
            if (player != null) {
                player.getPassengers().forEach(player::removePassenger);
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

        return Collections.singletonList("");
    }
}

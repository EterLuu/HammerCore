package ink.ziip.hammer.hammercore.command.sub;

import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.command.BaseSubCommand;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import ink.ziip.hammer.hammercore.api.util.Utils;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.coreprotect.listener.player.PlayerDropItemListener;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AdminCommand extends BaseSubCommand {

    private static final CoreProtectAPI coreProtectAPI = CoreProtect.getInstance().getAPI();

    private final String[] commandList = new String[]{
            "talk",
            "give-random-effect",
            "give-levitation-effect",
            "remove-main-hand",
            "sonic-boom",
    };

    public AdminCommand() {
        super("admin");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            return true;
        }
        if (args[0].equals("talk") && args.length == 3) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player != null) {
                player.chat(args[2]);
                return true;
            }
        }

        if (args[0].equals("give-random-effect") && args.length == 2) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player != null) {
                HammerUser hammerUser = HammerUser.getUser(player);
                Player targetPlayer = hammerUser.getTargetPlayer();
                if (targetPlayer != null) {
                    if (hammerUser.canUseMagicStick()) {
                        List<Object> list = Utils.getRandomPotionEffect();
                        targetPlayer.addPotionEffect(new PotionEffect((PotionEffectType) list.get(0), 100, 1));
                        targetPlayer.sendMessage(Utils.translateColorCodes("&b[!] &3你被 " + hammerUser.getPlayer().getName() + " 施加了诡异的" + (String) list.get(1)));
                        hammerUser.sendMessage("&b[!] &3你对 " + targetPlayer.getName() + " 施加了诡异的" + (String) list.get(1));
                        hammerUser.useMagicStick();
                    } else {
                        hammerUser.sendMessage("&b[!] &3你还没有足够的魔力。");
                    }
                }
                return true;
            }
        }

        if (args[0].equals("remove-main-hand") && args.length == 2) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player != null) {
                HammerUser hammerUser = HammerUser.getUser(player);
                Player targetPlayer = hammerUser.getTargetPlayer();
                if (targetPlayer != null) {
                    ItemStack itemStack = targetPlayer.getInventory().getItemInMainHand();
                    ItemStack newItemStack = itemStack.clone();
                    if (itemStack.getType() != Material.AIR) {
                        itemStack.setAmount(0);
                        Random random = new Random();
                        if (targetPlayer.getLocation().getWorld() != null) {
                            Location dropLocation = targetPlayer.getLocation().add(random.nextInt(1, 3), 1, random.nextInt(1, 3));
                            targetPlayer.getLocation().getWorld().dropItem(dropLocation, newItemStack);
                            if (coreProtectAPI != null) {
                                PlayerDropItemListener.playerDropItem(dropLocation, targetPlayer.getName(), newItemStack);
                            }
                        }
                        player.chat("Expelliarmus!");
                    }
                }
                return true;
            }
        }

        if (args[0].equals("sonic-boom") && args.length == 2) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player != null) {
                Snowball snowball = player.launchProjectile(Snowball.class);
                snowball.setMetadata("hammercore", new FixedMetadataValue(HammerCore.getInstance(), "sonic-boom"));
                snowball.setVelocity(player.getLocation().getDirection().normalize().multiply(2));
                new BukkitRunnable() {
                    int num = 4;

                    @Override
                    public void run() {
                        if (!snowball.isValid()) {
                            cancel();
                        }
                        Location location = snowball.getLocation();
                        if (location.getWorld() != null) {
                            location.getWorld().spawnParticle(Particle.SONIC_BOOM, location, 3);
                            location.getWorld().playSound(location, Sound.ENTITY_WARDEN_SONIC_BOOM, 1, 1);
                        }
                        if (num == 0) {
                            snowball.remove();
                            cancel();
                        }
                        num--;
                    }
                }.runTaskTimer(HammerCore.getInstance(), 0L, 5L);
                return true;
            }
        }

        if (args[0].equals("give-levitation-effect") && args.length == 2) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player != null) {
                HammerUser hammerUser = HammerUser.getUser(player);
                Player targetPlayer = hammerUser.getTargetPlayer();
                if (targetPlayer != null) {
                    targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 1));
                    player.chat("Wingardium Leviosa!");
                }
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
        if (args[0].equals("talk") && args.length == 2) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }

        return Collections.singletonList("");
    }
}

package ink.ziip.hammer.hammercore.listener;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ProjectKorraListener extends BaseListener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        BendingPlayer bendingPlayer = BendingPlayer.getBendingPlayer(player);

        if (bendingPlayer != null) {
            if (!bendingPlayer.getElements().contains(Element.EARTH))
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bend add earth " + playerName);
                    }
                }.runTaskLater(HammerCore.getInstance(), 20L);
        }
    }
}

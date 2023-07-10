package ink.ziip.hammer.hammercore.listener;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class ProjectKorraListener extends BaseListener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bendingPlayer = BendingPlayer.getBendingPlayer(player);

        if (bendingPlayer == null)
            return;

        if (bendingPlayer.getElements().contains(Element.EARTH))
            return;

        bendingPlayer.setElement(Element.EARTH);
    }
}

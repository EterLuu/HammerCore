package ink.ziip.hammer.hammercore.listener.packetlistener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.listener.packetlistener.BasePacketListener;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import org.bukkit.entity.Player;

public class ActionBarListener extends BasePacketListener {

    private final PacketAdapter actionBarPacketAdapter;

    public ActionBarListener() {
        super();
        actionBarPacketAdapter = new PacketAdapter(HammerCore.getInstance(), ListenerPriority.LOWEST, PacketType.Play.Server.SET_ACTION_BAR_TEXT) {
            public void onPacketSending(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null)
                    return;
                if (packetEvent.getPacket().getMeta("signed").isPresent()) {
                    return;
                }
                HammerUser.getUser(player).setActionBarSendingTime(System.currentTimeMillis());
            }
        };
    }

    @Override
    public void register() {
        protocolManager.addPacketListener(actionBarPacketAdapter);
    }

    @Override
    public void unRegister() {
        protocolManager.removePacketListener(actionBarPacketAdapter);
    }
}
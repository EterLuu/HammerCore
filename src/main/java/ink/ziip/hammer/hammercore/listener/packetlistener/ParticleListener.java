package ink.ziip.hammer.hammercore.listener.packetlistener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.listener.packetlistener.BasePacketListener;
import ink.ziip.hammer.hammercore.api.object.user.HammerUser;

public class ParticleListener extends BasePacketListener {

    private final PacketAdapter packetAdapter;

    public ParticleListener() {
        super();
        packetAdapter = new PacketAdapter(HammerCore.getInstance(), ListenerPriority.HIGH, PacketType.Play.Server.WORLD_PARTICLES) {
            public void onPacketSending(PacketEvent packetEvent) {
                if (HammerUser.getUser(packetEvent.getPlayer()).isBedrockPlayer()) {
                    packetEvent.setCancelled(true);
                }
            }
        };
    }

    @Override
    public void register() {
        protocolManager.addPacketListener(packetAdapter);
    }

    @Override
    public void unRegister() {
        protocolManager.removePacketListener(packetAdapter);
    }
}

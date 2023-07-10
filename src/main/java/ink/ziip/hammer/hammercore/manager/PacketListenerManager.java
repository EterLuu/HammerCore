package ink.ziip.hammer.hammercore.manager;

import ink.ziip.hammer.hammercore.api.listener.packetlistener.BasePacketListener;
import ink.ziip.hammer.hammercore.api.manager.BaseManager;
import ink.ziip.hammer.hammercore.listener.packetlistener.ActionBarListener;
import ink.ziip.hammer.hammercore.listener.packetlistener.ArchitectureItemListener;
import ink.ziip.hammer.hammercore.listener.packetlistener.ParticleListener;

public class PacketListenerManager extends BaseManager {

    private final BasePacketListener actionBarListener;
    private final BasePacketListener particleListener;
    private final BasePacketListener architectureItemListener;

    public PacketListenerManager() {
        actionBarListener = new ActionBarListener();
        particleListener = new ParticleListener();
        architectureItemListener = new ArchitectureItemListener();
    }

    @Override
    public void load() {
        actionBarListener.register();

        if (ConfigManager.FLOODGATE_HIDE_PARTICLES)
            particleListener.register();

        if (ConfigManager.UTIL_ARCHITECTURE_LORE_HIDDEN)
            architectureItemListener.register();
    }

    @Override
    public void unload() {
        actionBarListener.unRegister();
        particleListener.unRegister();
        architectureItemListener.unRegister();
    }

    @Override
    public void reload() {

    }
}

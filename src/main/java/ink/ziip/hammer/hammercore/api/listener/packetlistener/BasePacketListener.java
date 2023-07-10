package ink.ziip.hammer.hammercore.api.listener.packetlistener;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public abstract class BasePacketListener {

    protected final ProtocolManager protocolManager;

    protected BasePacketListener() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public abstract void register();

    public abstract void unRegister();
}

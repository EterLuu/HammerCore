package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.util.BoundingBox;

public class PortalListener extends BaseListener {

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (event.getReason() == PortalCreateEvent.CreateReason.FIRE ||
                event.getReason() == PortalCreateEvent.CreateReason.NETHER_PAIR) {
            BlockState blockState = event.getBlocks().get(0);
            BoundingBox boundingBox = new BoundingBox(-1000, -64, -1000, 1000, 320, 1000);
            if (blockState.getWorld().getName().equals("world_nether") && !boundingBox.contains(blockState.getX(), blockState.getY(), blockState.getZ())) {
                event.setCancelled(true);
            }
        }
    }
}

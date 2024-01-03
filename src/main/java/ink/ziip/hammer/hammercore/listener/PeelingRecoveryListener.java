package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import ink.ziip.hammer.hammercore.api.object.location.WoodLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PeelingRecoveryListener extends BaseListener {

    @EventHandler
    public void onWoodStripped(EntityChangeBlockEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Block block = event.getBlock();
        String name = block.getType().name();
        Location location = block.getLocation();

        if (name.endsWith("_WOOD") || name.endsWith("_LOG") || name.equals("BAMBOO_BLOCK")) {
            WoodLocation.addWood(WoodLocation.builder()
                    .x(location.getX())
                    .y(location.getY())
                    .z(location.getZ())
                    .world(location.getWorld())
                    .strippedTime(System.currentTimeMillis())
                    .blockData(block.getBlockData().clone())
                    .build());
        }
    }

    @EventHandler
    public void onStrippedBroken(BlockBreakEvent event) {

        Block block = event.getBlock();
        String name = block.getType().name();
        Location location = block.getLocation();

        if (name.startsWith("STRIPPED_")) {
            WoodLocation.removeWood(WoodLocation.builder()
                    .x(location.getX())
                    .y(location.getY())
                    .z(location.getZ())
                    .world(location.getWorld())
                    .build());
        }
    }

    @EventHandler
    public void playerInteractStrippedWood(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (block == null)
                return;

            String name = block.getType().name();
            Location location = block.getLocation();

            if (!event.hasItem() && (name.endsWith("_WOOD") || name.endsWith("_LOG") || name.equals("BAMBOO_BLOCK"))) {

                WoodLocation woodLocation = WoodLocation.builder()
                        .x(location.getX())
                        .y(location.getY())
                        .z(location.getZ())
                        .world(location.getWorld())
                        .strippedTime(System.currentTimeMillis())
                        .build();

                if (WoodLocation.containsWood(woodLocation)) {
                    block.setBlockData(WoodLocation.removeWood(woodLocation).getBlockData());
                }
            }
        }
    }
}

package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Rail;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityDismountEvent;

public class MineCartAcceleratorListener extends BaseListener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onVehicleLeave(EntityDismountEvent event) {
        Entity dismounted = event.getDismounted();
        Entity entity = event.getEntity();
        if (entity instanceof Player && dismounted instanceof Minecart minecart && minecart.getPassengers().size() == 1) {
            minecart.remove();
            entity.teleport(entity.getLocation().add(0, 1, 0));
            entity.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.MINECART));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onVehicleLeave(VehicleEntityCollisionEvent event) {
        if (event.getEntity() instanceof Minecart minecart) {
            for (Entity entity : minecart.getPassengers()) {
                if (entity instanceof Player) {
                    event.setCollisionCancelled(true);
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart cart && !event.getVehicle().isEmpty()) {

            for (Entity entity : event.getVehicle().getPassengers()) {
                if (entity instanceof Player) {
                    Location frontLocation1 = event.getFrom().add(cart.getFacing().getDirection().multiply(1));
                    Location frontLocation2 = event.getFrom().add(cart.getFacing().getDirection().multiply(3));
                    Location frontLocation3 = event.getFrom().add(cart.getFacing().getDirection().multiply(5));
                    if (frontLocation1.getBlock().getBlockData() instanceof Rail &&
                            frontLocation2.getBlock().getBlockData() instanceof Rail &&
                            frontLocation3.getBlock().getBlockData() instanceof Rail &&
                            cart.getLocation().getBlock().getType() != Material.RAIL &&
                            event.getFrom().getBlock().getType() != Material.RAIL &&
                            event.getTo().getBlock().getType() != Material.RAIL
                    ) {
                        cart.setMaxSpeed(1.6);
                    } else {
                        if (cart.getVelocity().length() > 2) {
                            cart.setVelocity(cart.getVelocity().multiply(2 / cart.getVelocity().length()));
                        }
                        cart.setMaxSpeed(0.4);
                    }
                    return;
                }
            }
        }
    }
}

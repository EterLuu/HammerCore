package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class MineCartAcceleratorListener extends BaseListener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onVehicleMove(VehicleMoveEvent event) {

        if (event.getVehicle() instanceof Minecart cart && !event.getVehicle().isEmpty()) {

            Location location = cart.getLocation();
            int cartX, cartY, cartZ;

            cartX = location.getBlockX();
            cartY = location.getBlockY();
            cartZ = location.getBlockZ();

            for (int x = cartX - 1; x < cartX + 1; x++) {
                for (int y = cartY - 1; y < cartY + 1; y++) {
                    for (int z = cartZ - 1; z < cartZ + 1; z++) {
                        Material material = cart.getWorld().getBlockAt(x, y, z).getBlockData().getMaterial();
                        if (material == Material.IRON_BLOCK) {
                            cart.setMaxSpeed(1.6);
                            return;
                        }

                        if (material == Material.GOLD_BLOCK) {
                            cart.setMaxSpeed(0.4);
                            return;
                        }

                        Block block = cart.getWorld().getBlockAt(x, y, z);
                        double speed;

                        if (material == Material.OAK_SIGN || material == Material.OAK_WALL_SIGN) {
                            Sign sign = (Sign) block.getState();
                            if (sign.getSide(Side.FRONT).getLines()[0].equalsIgnoreCase("[msp]")) {
                                try {
                                    speed = Double.parseDouble(sign.getSide(Side.FRONT).getLines()[1]);
                                } catch (Exception e) {
                                    sign.getSide(Side.FRONT).setLine(2, "错误");
                                    sign.getSide(Side.FRONT).setLine(3, "无效的数字");
                                    sign.update();
                                    return;
                                }
                                if (0 < speed && speed <= 50) {
                                    cart.setMaxSpeed(0.4D * speed);
                                    return;
                                }
                            }
                            if (sign.getSide(Side.BACK).getLines()[0].equalsIgnoreCase("[msp]")) {
                                try {
                                    speed = Double.parseDouble(sign.getSide(Side.BACK).getLines()[1]);
                                } catch (Exception e) {
                                    sign.getSide(Side.BACK).setLine(2, "错误");
                                    sign.getSide(Side.BACK).setLine(3, "无效的数字");
                                    sign.update();
                                    return;
                                }
                                if (0 < speed && speed <= 50) {
                                    cart.setMaxSpeed(0.4D * speed);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

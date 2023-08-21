package ink.ziip.hammer.hammercore.listener.packetlistener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SnowballListener extends BaseListener {

    @EventHandler
    public void onAttackedBySnowball(ProjectileHitEvent event) {
        if (event.getEntity().hasMetadata("hammercore")) {
            Entity entity = event.getHitEntity();
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    entity.setVelocity(event.getEntity().getLocation().getDirection().multiply(2));
                    ((org.bukkit.entity.LivingEntity) entity).damage(3);
                }
            }
        }
    }
}

package ink.ziip.hammer.hammercore.api.object.location;

import lombok.Builder;
import lombok.Data;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Builder
public class WoodLocation {

    private static final Map<WoodLocation, WoodLocation> woodLocationMap = new ConcurrentHashMap<>();

    private World world;
    private double x;
    private double y;
    private double z;
    private BlockData blockData;

    private Long strippedTime;

    public static void addWood(WoodLocation woodLocation) {
        woodLocation.setStrippedTime(System.currentTimeMillis());
        woodLocationMap.put(woodLocation, woodLocation);
    }

    public static WoodLocation removeWood(WoodLocation woodLocation) {
        WoodLocation newWoodLocation = woodLocationMap.get(woodLocation);
        woodLocationMap.remove(woodLocation);
        return newWoodLocation;
    }

    public static boolean containsWood(WoodLocation woodLocation) {
        return woodLocationMap.containsKey(woodLocation);
    }

    public static void removeOutdatedWood() {
        for (Map.Entry<WoodLocation, WoodLocation> entry : woodLocationMap.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue().strippedTime > 300000) {
                woodLocationMap.remove(entry.getKey());
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof WoodLocation other)) return false;

        if (!this.world.equals(other.world)) return false;
        if (Double.compare(this.x, other.x) != 0) return false;
        if (Double.compare(this.y, other.y) != 0) return false;
        return Double.compare(this.z, other.z) == 0;
    }

    // Bukkit's implementation
    @Override
    public int hashCode() {
        int hash = 3;

        World world = (this.world == null) ? null : this.world;
        hash = 19 * hash + (world != null ? world.hashCode() : 0);
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
}

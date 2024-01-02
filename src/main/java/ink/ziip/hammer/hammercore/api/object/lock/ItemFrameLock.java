package ink.ziip.hammer.hammercore.api.object.lock;

import ink.ziip.hammer.hammercore.HammerCore;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class ItemFrameLock {

    private static final NamespacedKey HEADER_KEY = new NamespacedKey(HammerCore.getInstance(), "header");
    private static final NamespacedKey OWNER_UUID = new NamespacedKey(HammerCore.getInstance(), "ownerUUID");
    private static final NamespacedKey OWNER_USERNAME = new NamespacedKey(HammerCore.getInstance(), "ownerUsername");

    private ItemFrame itemFrame;

    private ItemFrameLock(ItemFrame itemFrame) {
        this.itemFrame = itemFrame;

        PersistentDataContainer dataContainer = itemFrame.getPersistentDataContainer();
        if (!dataContainer.has(HEADER_KEY, PersistentDataType.STRING))
            dataContainer.set(HEADER_KEY, PersistentDataType.LONG, System.currentTimeMillis());
    }

    public static ItemFrameLock getItemFrameLock(ItemFrame itemFrame) {
        return new ItemFrameLock(itemFrame);
    }

    public UUID getOwnerUUID() {
        PersistentDataContainer dataContainer = itemFrame.getPersistentDataContainer();

        long[] uuid = dataContainer.get(OWNER_UUID, PersistentDataType.LONG_ARRAY);
        if (uuid == null || uuid.length != 2) {
            return null;
        }
        return new UUID(uuid[0], uuid[1]);
    }

    public String getOwnerName() {
        PersistentDataContainer dataContainer = itemFrame.getPersistentDataContainer();

        return dataContainer.get(OWNER_USERNAME, PersistentDataType.STRING);
    }

    public boolean hasOwner() {
        PersistentDataContainer dataContainer = itemFrame.getPersistentDataContainer();
        String username = dataContainer.get(OWNER_USERNAME, PersistentDataType.STRING);

        return username != null;
    }

    public boolean isOwner(Player player) {
        if (player.isOp())
            return true;

        if (hasOwner())
            return player.getUniqueId().equals(getOwnerUUID());
        return true;
    }

    public void setOwner(Player player) {
        PersistentDataContainer dataContainer = itemFrame.getPersistentDataContainer();
        dataContainer.set(OWNER_USERNAME, PersistentDataType.STRING, player.getName());
        UUID uuid = player.getUniqueId();
        dataContainer.set(OWNER_UUID, PersistentDataType.LONG_ARRAY, new long[]{uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()});
        dataContainer.set(HEADER_KEY, PersistentDataType.LONG, System.currentTimeMillis());
    }

    public void removeOwner() {
        PersistentDataContainer dataContainer = itemFrame.getPersistentDataContainer();
        dataContainer.remove(OWNER_UUID);
        dataContainer.remove(OWNER_USERNAME);
        dataContainer.set(HEADER_KEY, PersistentDataType.LONG, System.currentTimeMillis());
    }
}

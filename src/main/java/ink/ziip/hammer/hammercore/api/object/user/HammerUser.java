package ink.ziip.hammer.hammercore.api.object.user;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import ink.ziip.hammer.hammercore.api.util.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.geysermc.floodgate.api.FloodgateApi;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class HammerUser {

    private static final Map<UUID, HammerUser> users = new ConcurrentHashMap<>();

    @Nullable
    private final Player player;
    private final OfflinePlayer offlinePlayer;
    private final UUID playerUUID;
    private final CommandSender commandSender;

    private final boolean bedrockPlayer;

    private Long actionBarSendingTime = System.currentTimeMillis();
    private Long messageSendingTime = System.currentTimeMillis();

    private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private FloodgateApi floodgateApi = FloodgateApi.getInstance();
    private int magicStickUsingTimes = 0;
    private Long magicStickPendingTime = 0L;

    @Getter
    @Setter
    private boolean removeMineCartOnLeaving = true;

    @Getter
    @Setter
    private Long dropItemsTime = 0L;

    public boolean canUseMagicStick() {
        return System.currentTimeMillis() - magicStickPendingTime > 120000;
    }

    public void useMagicStick() {
        magicStickUsingTimes++;

        if (magicStickUsingTimes == 10) {
            magicStickPendingTime = System.currentTimeMillis();
            magicStickUsingTimes = 0;
        }
    }

    private HammerUser(@NonNull Player player) {
        this.player = player;
        this.offlinePlayer = player;
        this.playerUUID = player.getUniqueId();
        this.commandSender = player;
        this.bedrockPlayer = floodgateApi.isFloodgateId(this.playerUUID);
        users.put(this.playerUUID, this);
    }

    private HammerUser(@NonNull OfflinePlayer offlinePlayer) {
        this.player = offlinePlayer.isOnline() ? offlinePlayer.getPlayer() : null;
        this.offlinePlayer = offlinePlayer;
        this.playerUUID = offlinePlayer.getUniqueId();
        this.commandSender = offlinePlayer.isOnline() ? offlinePlayer.getPlayer() : null;
        this.bedrockPlayer = floodgateApi.isFloodgateId(this.playerUUID);
        users.put(this.playerUUID, this);
    }

    private HammerUser(@NonNull UUID uuid) {
        this.playerUUID = uuid;
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        this.player = offlinePlayer.isOnline() ? offlinePlayer.getPlayer() : null;
        this.commandSender = offlinePlayer.isOnline() ? offlinePlayer.getPlayer() : null;
        this.bedrockPlayer = floodgateApi.isFloodgateId(this.playerUUID);
        users.put(this.playerUUID, this);
    }

    private HammerUser(@NonNull CommandSender commandSender) {
        this.commandSender = commandSender;
        this.player = Bukkit.getPlayer(commandSender.getName());
        this.playerUUID = player != null ? player.getUniqueId() : null;
        this.offlinePlayer = player != null ? Bukkit.getOfflinePlayer(playerUUID) : null;
        this.bedrockPlayer = player != null && floodgateApi.isFloodgateId(this.playerUUID);
        users.put(this.playerUUID, this);
    }

    public static HammerUser getUser(@NonNull Player player) {
        if (users.containsKey(player.getUniqueId())) {
            return users.get(player.getUniqueId());
        }
        return new HammerUser(player);
    }

    public static HammerUser getUser(@NonNull OfflinePlayer offlinePlayer) {
        if (users.containsKey(offlinePlayer.getUniqueId())) {
            return users.get(offlinePlayer.getUniqueId());
        }
        return new HammerUser(offlinePlayer);
    }

    public static HammerUser getUser(@NonNull UUID uuid) {
        if (users.containsKey(uuid)) {
            return users.get(uuid);
        }
        return new HammerUser(uuid);
    }

    public static HammerUser getUser(@NonNull CommandSender commandSender) {
        if (commandSender instanceof Player)
            return getUser((Player) commandSender);

        return new HammerUser(commandSender);
    }

    public static void removeHammerUser(@NonNull Player player) {
        users.remove(player.getUniqueId());
    }

    public static void removeHammerUser(@NonNull OfflinePlayer offlinePlayer) {
        users.remove(offlinePlayer.getUniqueId());
    }

    public static void removeHammerUser(@NonNull UUID uuid) {
        users.remove(uuid);
    }

    public static void cleanHammerUsers() {
        users.clear();
    }

    public boolean isBedrockPlayer() {
        return bedrockPlayer;
    }

    public boolean hasPermission(String name) {
        if (player == null)
            return commandSender.hasPermission(name);

        return player.hasPermission(name);
    }

    public void sendActionBar(String content, boolean filtered) {
        WrappedChatComponent wrappedChatComponent = WrappedChatComponent.fromLegacyText(setPlaceholders(content));
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SYSTEM_CHAT);
        StructureModifier<Integer> integers = packetContainer.getIntegers();
        if (integers.size() == 1) {
            integers.write(0, (int) EnumWrappers.ChatType.GAME_INFO.getId());
        } else {
            packetContainer.getBooleans().write(0, true);
        }
        packetContainer.getStrings().write(0, wrappedChatComponent.getJson());
        if (filtered) {
            packetContainer.setMeta("signed", true);
        }
        protocolManager.sendServerPacket(player, packetContainer);
    }

    public void sendMessage(String content) {
        if (player == null) {
            commandSender.sendMessage(Utils.translateColorCodes(setPlaceholders(content)));
            return;
        }

        player.sendMessage(Utils.translateColorCodes(setPlaceholders(content)));
    }

    public String setPlaceholders(String content) {
        // Using offlinePlayer to avoid issues
        return Utils.translateColorCodes(PlaceholderAPI.setPlaceholders(offlinePlayer, content));
    }

    public Player getTargetPlayer() {
        if (player == null)
            return null;

        return getTarget(player, player.getWorld().getPlayers());
    }

    public Entity getTargetEntity() {
        if (player == null)
            return null;

        return getTarget(player, player.getNearbyEntities(50, 50, 50));
    }

    // Source: https://bukkit.org/threads/get-entity-player-is-looking.300661/#post-2727103
    private <T extends Entity> T getTarget(final Entity entity, final Iterable<T> entities) {
        if (entity == null)
            return null;

        T target = null;
        final Location entityLocation = entity.getLocation();
        final Vector entityVector = entityLocation.toVector();
        final double threshold = 1;

        for (final T other : entities) {
            Vector otherEntityVector = other.getLocation().toVector();

            final Vector n = otherEntityVector.subtract(entityVector);

            boolean targeted = entityLocation.getDirection().normalize().crossProduct(n).lengthSquared() < threshold && n.normalize().dot(entityLocation.getDirection().normalize()) >= 0;
            if (targeted) {

                boolean closed = target == null || target.getLocation().distanceSquared(entityLocation) > other.getLocation().distanceSquared(entityLocation);
                if (closed)
                    target = other;
            }
        }

        return target;
    }
}

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
import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class HammerUser {

    private static final Map<UUID, HammerUser> users = new ConcurrentHashMap<>();

    private final Player player;
    private final OfflinePlayer offlinePlayer;
    private final UUID playerUUID;
    private final boolean bedrockPlayer;

    private Long actionBarSendingTime = System.currentTimeMillis();
    private Long messageSendingTime = System.currentTimeMillis();

    private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private FloodgateApi floodgateApi = FloodgateApi.getInstance();

    private HammerUser(@NonNull Player player) {
        this.player = player;
        this.offlinePlayer = player;
        this.playerUUID = player.getUniqueId();
        this.bedrockPlayer = floodgateApi.isFloodgateId(this.playerUUID);
        users.put(this.playerUUID, this);
    }

    private HammerUser(@NonNull OfflinePlayer offlinePlayer) {
        this.player = offlinePlayer.getPlayer();
        this.offlinePlayer = offlinePlayer;
        this.playerUUID = offlinePlayer.getUniqueId();
        this.bedrockPlayer = floodgateApi.isFloodgateId(this.playerUUID);
        users.put(this.playerUUID, this);
    }

    private HammerUser(@NonNull UUID uuid) {
        this.player = Bukkit.getPlayer(uuid);
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        this.playerUUID = uuid;
        this.bedrockPlayer = floodgateApi.isFloodgateId(this.playerUUID);
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

    public boolean isBedrockPlayer() {
        return bedrockPlayer;
    }

    public boolean hasPermission(String name) {
        return player.hasPermission(name);
    }

    public void sendActionBar(String content, boolean filtered) {
        WrappedChatComponent wrappedChatComponent = WrappedChatComponent.fromLegacyText(PlaceholderAPI.setPlaceholders(player, content));
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
        content = PlaceholderAPI.setPlaceholders(player, content);
        player.sendMessage(Utils.translateColorCodes(content));
    }

    public String setPlaceholders(String content) {
        return Utils.translateColorCodes(PlaceholderAPI.setPlaceholders(player, content));
    }
}

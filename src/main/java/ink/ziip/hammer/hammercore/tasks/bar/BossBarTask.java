package ink.ziip.hammer.hammercore.tasks.bar;

import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import ink.ziip.hammer.hammercore.api.task.BaseTask;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static ink.ziip.hammer.hammercore.manager.ConfigManager.*;

public class BossBarTask extends BaseTask {

    private static BossBarTask instance;

    private static final Map<UUID, BossBar> infobarMap = new ConcurrentHashMap<>();

    private BossBarTask() {
        super(40);
    }

    public static BossBarTask getInstance() {
        if (instance == null) {
            instance = new BossBarTask();
        }
        return instance;
    }

    @Override
    public void start() {
        super.start();
        for (Player player : Bukkit.getOnlinePlayers()) {
            addToAll(player);
        }
    }

    @Override
    public void stop() {
        if (started) {
            new HashSet<>(infobarMap.keySet()).forEach(playerUUID -> {
                infobarMap.get(playerUUID).removeAll();
            });
            infobarMap.clear();
            cancel();
        }
    }

    @Override
    public void run() {
        if (started) {
            for (Map.Entry<UUID, BossBar> entry : infobarMap.entrySet()) {
                Player player = Bukkit.getPlayer(entry.getKey());
                if (player != null)
                    updateBossBar(entry.getValue(), player);
            }
        }
    }

    private static BossBar createBossBar() {
        return Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
    }

    public void updateBossBar(BossBar bossbar, Player player) {
        HammerUser hammerUser = HammerUser.getUser(player);
        if (hammerUser.isBedrockPlayer()) {
            String infoContent = INFO_BAR_CONTENT_BEDROCK_WORLD_LIST.get(player.getWorld().getName());
            if (infoContent != null) {
                bossbar.setTitle(hammerUser.setPlaceholders(infoContent));
                return;
            }
            bossbar.setTitle(hammerUser.setPlaceholders(INFO_BAR_CONTENT_BEDROCK_DEFAULT));
            return;
        }
        String infoContent = INFO_BAR_CONTENT_JAVA_WORLD_LIST.get(player.getWorld().getName());
        if (infoContent != null) {
            bossbar.setTitle(hammerUser.setPlaceholders(infoContent));
            return;
        }
        bossbar.setTitle(hammerUser.setPlaceholders(INFO_BAR_CONTENT_JAVA_DEFAULT));
    }

    public static void addToAll(Player player) {
        UUID playerUUID = player.getUniqueId();
        infobarMap.remove(playerUUID);
        BossBar bossBar = createBossBar();
        bossBar.addPlayer(player);
        infobarMap.put(playerUUID, bossBar);
    }

    public static void removeFromAll(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (infobarMap.containsKey(playerUUID)) {
            infobarMap.get(playerUUID).removePlayer(player);
            infobarMap.remove(playerUUID);
        }
    }
}

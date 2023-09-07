package ink.ziip.hammer.hammercore.tasks;

import ink.ziip.hammer.hammercore.HammerCore;
import ink.ziip.hammer.hammercore.api.task.BaseTask;
import ink.ziip.hammer.hammercore.api.util.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalTime;

public class MaintenanceTask extends BaseTask {

    private static MaintenanceTask instance;
    @Getter
    private static boolean whitelist = false;

    private MaintenanceTask() {
        super(60);
    }

    public static MaintenanceTask getInstance() {
        if (instance == null) {
            instance = new MaintenanceTask();
        }
        return instance;
    }

    @Override
    public void run() {
        if (started) {
            if (LocalTime.now().toSecondOfDay() >= LocalTime.parse("01:00").toSecondOfDay() &&
                    LocalTime.now().toSecondOfDay() <= LocalTime.parse("06:00").toSecondOfDay()
            ) {
                if (!whitelist) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.kickPlayer(Utils.translateColorCodes("&7[&c&l!&7] &4服务器维护。（01:00~06:00）"));
                    }
                    whitelist = true;
                }
            } else {
                if (whitelist) {
                    whitelist = false;
                }
            }
        }
    }

    @Override
    public void start() {
        this.runTaskTimer(HammerCore.getInstance(), 1, period);
        started = true;
    }

    @Override
    public void stop() {
        cancel();
    }
}
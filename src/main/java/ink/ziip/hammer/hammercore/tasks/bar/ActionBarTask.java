package ink.ziip.hammer.hammercore.tasks.bar;

import ink.ziip.hammer.hammercore.api.object.user.HammerUser;
import ink.ziip.hammer.hammercore.api.task.BaseTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionBarTask extends BaseTask {

    private static ActionBarTask instance;

    private ActionBarTask() {
        super(40);
    }

    public static ActionBarTask getInstance() {
        if (instance == null) {
            instance = new ActionBarTask();
        }
        return instance;
    }

    @Override
    public void run() {
        if (started) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.isInWater()) {
                    HammerUser hammerUser = HammerUser.getUser(player);
                    if (!hammerUser.isBedrockPlayer()) {
                        if (System.currentTimeMillis() - hammerUser.getActionBarSendingTime() > 1000) {
                            hammerUser.sendActionBar("\uE120\uE11E\uE116&f%pkaddons_actionbar% \uE121%rs_temperaturecolor%[ %rs_temperature% ]", true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void stop() {
        cancel();
    }
}
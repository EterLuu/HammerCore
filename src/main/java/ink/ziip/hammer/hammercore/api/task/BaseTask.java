package ink.ziip.hammer.hammercore.api.task;

import ink.ziip.hammer.hammercore.HammerCore;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class BaseTask extends BukkitRunnable {

    protected boolean started;
    protected int period;

    public BaseTask(int period) {
        this.started = false;
        this.period = period;
    }

    public void start() {
        this.runTaskTimerAsynchronously(HammerCore.getInstance(), 1, period);
        started = true;
    }

    public abstract void stop();
}

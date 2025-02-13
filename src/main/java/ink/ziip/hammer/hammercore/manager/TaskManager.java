package ink.ziip.hammer.hammercore.manager;

import ink.ziip.hammer.hammercore.api.manager.BaseManager;
import ink.ziip.hammer.hammercore.api.task.BaseTask;
import ink.ziip.hammer.hammercore.tasks.MaintenanceTask;
import ink.ziip.hammer.hammercore.tasks.bar.ActionBarTask;
import ink.ziip.hammer.hammercore.tasks.bar.BossBarTask;
import ink.ziip.hammer.hammercore.tasks.DataCleanerTask;

public class TaskManager extends BaseManager {

    private final BaseTask actionBarTask;
    private final BaseTask bossBarTask;
    private final BaseTask dataCleanerTask;
//    private final BaseTask maintenanceTask;

    public TaskManager() {
        actionBarTask = ActionBarTask.getInstance();
        bossBarTask = BossBarTask.getInstance();
        dataCleanerTask = DataCleanerTask.getInstance();
//        maintenanceTask = MaintenanceTask.getInstance();
    }

    @Override
    public void load() {
        actionBarTask.start();
        bossBarTask.start();
        dataCleanerTask.start();
//        maintenanceTask.start();
    }

    @Override
    public void unload() {
        actionBarTask.stop();
        bossBarTask.stop();
        dataCleanerTask.stop();
//        maintenanceTask.stop();
    }

    @Override
    public void reload() {

    }
}

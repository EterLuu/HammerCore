package ink.ziip.hammer.hammercore.tasks;

import ink.ziip.hammer.hammercore.api.object.location.WoodLocation;
import ink.ziip.hammer.hammercore.api.task.BaseTask;

public class DataCleanerTask extends BaseTask {

    private static DataCleanerTask instance;

    private DataCleanerTask() {
        super(12000);
    }

    public static DataCleanerTask getInstance() {
        if (instance == null) {
            instance = new DataCleanerTask();
        }
        return instance;
    }

    @Override
    public void run() {
        if (started) {
            WoodLocation.removeOutdatedWoods();
        }
    }

    @Override
    public void stop() {
        cancel();
    }
}
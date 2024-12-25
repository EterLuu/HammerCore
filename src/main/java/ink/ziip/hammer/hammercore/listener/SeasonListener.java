package ink.ziip.hammer.hammercore.listener;

import ink.ziip.hammer.hammercore.api.listener.BaseListener;
import me.casperge.realisticseasons.api.SeasonChangeEvent;
import me.casperge.realisticseasons.season.Season;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import uk.co.harieo.seasons.plugin.Seasons;

public class SeasonListener extends BaseListener {
    @EventHandler
    public void onSeasonChange(SeasonChangeEvent event) {
        Season newSeason = event.getNewSeason();
        if (newSeason == Season.SPRING) {
            Seasons.getInstance().getWorldCycle(Bukkit.getWorld("world")).setSeason(uk.co.harieo.seasons.plugin.models.Season.SPRING);
        }
        if (newSeason == Season.SUMMER) {
            Seasons.getInstance().getWorldCycle(Bukkit.getWorld("world")).setSeason(uk.co.harieo.seasons.plugin.models.Season.SUMMER);
        }
        if (newSeason == Season.FALL) {
            Seasons.getInstance().getWorldCycle(Bukkit.getWorld("world")).setSeason(uk.co.harieo.seasons.plugin.models.Season.AUTUMN);
        }
        if (newSeason == Season.WINTER) {
            Seasons.getInstance().getWorldCycle(Bukkit.getWorld("world")).setSeason(uk.co.harieo.seasons.plugin.models.Season.WINTER);
        }
    }
}

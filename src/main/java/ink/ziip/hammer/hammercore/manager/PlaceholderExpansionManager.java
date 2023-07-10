package ink.ziip.hammer.hammercore.manager;

import ink.ziip.hammer.hammercore.api.expansion.papi.BasePlaceholder;
import ink.ziip.hammer.hammercore.api.expansion.papi.SeasonNamePlaceholder;
import ink.ziip.hammer.hammercore.api.expansion.papi.WeatherNamePlaceholder;
import ink.ziip.hammer.hammercore.api.expansion.papi.WorldNamePlaceholder;
import ink.ziip.hammer.hammercore.api.manager.BaseManager;

public class PlaceholderExpansionManager extends BaseManager {

    private final BasePlaceholder seasonNamePlaceholder;
    private final BasePlaceholder weatherNamePlaceholder;
    private final BasePlaceholder worldNamePlaceholder;

    public PlaceholderExpansionManager() {
        seasonNamePlaceholder = new SeasonNamePlaceholder();
        weatherNamePlaceholder = new WeatherNamePlaceholder();
        worldNamePlaceholder = new WorldNamePlaceholder();
    }

    @Override
    public void load() {
        seasonNamePlaceholder.register();
        weatherNamePlaceholder.register();
        worldNamePlaceholder.register();
    }

    @Override
    public void unload() {
        seasonNamePlaceholder.unregister();
        weatherNamePlaceholder.unregister();
        worldNamePlaceholder.unregister();
    }

    @Override
    public void reload() {

    }
}

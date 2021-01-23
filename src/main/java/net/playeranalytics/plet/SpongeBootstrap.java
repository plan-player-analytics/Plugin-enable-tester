package net.playeranalytics.plet;

import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.api.TimeAmount;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "plet",
        name = "Plugin enable tester",
        version = "1.0",
        description = "Plugin enable tester",
        authors = {"Rsl1122"}
)
public class SpongeBootstrap extends SpongePlugin implements PlatformChecks {

    @com.google.inject.Inject
    private Logger slf4jLogger;

    @com.google.inject.Inject
    @ConfigDir(sharedRoot = false)
    private File dataFolder;

    @com.google.inject.Inject
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        runnableFactory.create(null, new Plet(this))
                .runTaskLater(TimeAmount.toTicks(10, TimeUnit.SECONDS));
    }

    @Override
    public Logger getLogger() {
        return slf4jLogger;
    }

    @Override
    public File getDataFolder() {
        return dataFolder;
    }

    @Override
    public boolean isPluginEnabled(String pluginName) {
        return pluginManager.isLoaded(pluginName);
    }

    @Override
    public String getVersion() {
        return getClass().getAnnotation(Plugin.class).version();
    }
}

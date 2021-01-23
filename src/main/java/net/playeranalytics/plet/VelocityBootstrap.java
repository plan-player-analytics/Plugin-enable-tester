package net.playeranalytics.plet;

import com.djrapitops.plugin.VelocityPlugin;
import com.djrapitops.plugin.api.TimeAmount;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "plet",
        name = "Plugin enable tester",
        version = "1.0",
        description = "Plugin enable tester",
        authors = {"Rsl1122"}
)
public class VelocityBootstrap extends VelocityPlugin implements PlatformChecks {

    @com.google.inject.Inject
    public VelocityBootstrap(ProxyServer proxy, Logger slf4jLogger, @DataDirectory Path dataFolderPath) {
        super(proxy, slf4jLogger, dataFolderPath);
    }

    @Override
    public void onEnable() {
        runnableFactory.create(null, new Plet(this))
                .runTaskLater(TimeAmount.toTicks(10, TimeUnit.SECONDS));
    }

    @Override
    public String getVersion() {
        return getClass().getAnnotation(Plugin.class).version();
    }

    @Override
    public boolean isPluginEnabled(String pluginName) {
        return getProxy().getPluginManager().isLoaded(pluginName);
    }
}

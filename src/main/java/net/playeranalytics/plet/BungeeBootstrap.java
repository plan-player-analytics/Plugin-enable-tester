package net.playeranalytics.plet;

import com.djrapitops.plugin.BungeePlugin;
import com.djrapitops.plugin.api.TimeAmount;

import java.util.concurrent.TimeUnit;

public class BungeeBootstrap extends BungeePlugin implements PlatformChecks {

    @Override
    public void onEnable() {
        runnableFactory.create(null, new Plet(this))
                .runTaskLater(TimeAmount.toTicks(10, TimeUnit.SECONDS));
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public boolean isPluginEnabled(String pluginName) {
        return getProxy().getPluginManager().getPlugin(pluginName) != null;
    }
}

package net.playeranalytics.plet;

import com.djrapitops.plugin.IPlugin;

public interface PlatformChecks extends IPlugin {

    boolean isPluginEnabled(String pluginName);

}

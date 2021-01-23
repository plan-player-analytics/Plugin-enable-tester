package net.playeranalytics.plet;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.task.AbsRunnable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Plet extends AbsRunnable {

    private final PlatformChecks platformChecks;

    private String pluginName;
    private List<Pattern> badFilePatterns;

    public Plet(PlatformChecks platformChecks) {
        this.platformChecks = platformChecks;
        try {
            pluginName = System.getenv("PLUGIN_TO_TEST");
            badFilePatterns = new ArrayList<>();
            String[] patternRegexes = System.getenv("BAD_FILE_PATTERN").split(",");
            for (String regex : patternRegexes) {
                badFilePatterns.add(Pattern.compile(regex));
            }
            if (pluginName == null || badFilePatterns.isEmpty()) throw new NullPointerException();
        } catch (NullPointerException e) {
            platformChecks.getPluginLogger().error("SET UP ENV PARAMETERS 'PLUGIN_TO_TEST' AND 'BAD_FILE_PATTERN'");
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            if (!platformChecks.isPluginEnabled(pluginName)) fail("Plugin was not enabled.");
            File serverFolder = platformChecks.getDataFolder().getParentFile().getParentFile();
            scan(serverFolder.listFiles());
        } catch (IllegalStateException fail) {
            platformChecks.getPluginLogger().error("FAIL: " + fail.getMessage());
            System.exit(1);
        }
        platformChecks.getPluginLogger().error("SUCCESS!");
        System.exit(0);
    }

    private void scan(File[] toScan) {
        if (toScan == null) return;
        for (File file : toScan) {
            if (file.isDirectory()) {
                scan(file.listFiles());
            } else {
                for (Pattern badFilePattern : badFilePatterns) {
                    String fileName = file.getName();
                    if (badFilePattern.matcher(fileName).matches()) {
                        printBadFile(file);
                        fail("File " + fileName + " matched bad pattern " + badFilePattern.pattern());
                    }
                }
            }
        }
    }

    private void printBadFile(File file) {
        PluginLogger logger = platformChecks.getPluginLogger();
        logger.warn("Contents of " + file.getName() + ':');
        try (Stream<String> lines = Files.lines(file.toPath())) {
            lines.forEach(logger::warn);
        } catch (IOException e) {
            logger.log(L.WARN, "Could not read bad file", e);
        }
    }

    private void fail(String reason) {
        throw new IllegalStateException(reason);
    }
}

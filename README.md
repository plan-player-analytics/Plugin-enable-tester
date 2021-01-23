# Plugin Enable Tester

This is a very rudamentary plugin for multiple server platforms that checks if a specific plugin has enabled. Currently
supports Spigot, Sponge, Bungeecord & Velocity.

The purpose is to load it on a CI service with the server platform and see if the build passes enable test.

In addition there is a possiblity to scan for a file with specific regex pattern as the name and fail if it is present.
This is to for error logs.

## Environment variables

The following env variables are needed for the test to work

```
PLUGIN_TO_TEST = name of the plugin to test
BAD_FILE_PATTERN = Regex patterns separated by commas
```

## Usage

- Set the needed environmental variables in the CI config
- Download a build from Github or build the project using `mvn package`.
- Place Plet.jar in the platform's plugin folder
- Enable the platform in your test script
- The test is run 10 seconds after platform has enabled and System.exit(0) or System.exit(1) is called based on the
  results.
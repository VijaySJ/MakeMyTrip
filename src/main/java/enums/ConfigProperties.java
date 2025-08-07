package enums;

/**
 * Enum to hold all keys present in the config.properties file.
 * Helps ensure type safety and avoids hardcoding strings across the framework.
 */
public enum ConfigProperties {

    URL,            // Base URL of the application under test
    BROWSER         // Browser to run tests on (CHROME, EDGE, etc.)

}

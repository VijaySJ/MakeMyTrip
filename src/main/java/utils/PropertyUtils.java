package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import constants.FrameworkConstants;

/**
 * Utility class to read configuration from config.properties.
 * Loads properties once for fast access throughout the framework.
 */
public final class PropertyUtils {

    // Prevents instantiation
    private PropertyUtils() {}

    // Used to load properties from file
    private static final Properties property = new Properties();

    // Stores all config properties for O(1) lookup
    private static final Map<String, String> CONFIGMAP = new HashMap<>();

    // Static initializer loads the config file once when class loads
    static {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.getConfigFilePath())) {
            property.load(fis); // Load from file into Properties
            // Move each property to a HashMap for quick repeated access
            property.entrySet().forEach(entry ->
                CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()))
            );
        } catch (IOException e) {
            // Fail fast if config file is missing or unreadable
            throw new RuntimeException("Failed to load properties file.", e);
        }
    }

    /**
     * Gets the value for a property key.
     * Throws if key is missing/null/empty for reliable test behavior.
     */
    public static String get(String key) {
        if (Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key)) || CONFIGMAP.get(key).isEmpty()) {
            throw new RuntimeException("Property key '" + key + "' not found in config.properties");
        }
        return CONFIGMAP.get(key);
    }
}

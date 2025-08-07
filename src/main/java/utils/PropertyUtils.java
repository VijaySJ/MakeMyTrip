package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import constants.FrameworkConstants;

/**
 * Utility class to read configuration properties from the config.properties file.
 * Loads properties once into a Map for fast access across the framework.
 */
public final class PropertyUtils {

    // Private constructor to prevent instantiation
    private PropertyUtils() {}

    // Java Properties object to load key-value pairs
    private static final Properties property = new Properties();

    // Config map to store properties for quick access
    private static final Map<String, String> CONFIGMAP = new HashMap<>();

    // Static block to load the config file when the class is loaded
    static {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.getConfigFilePath())) {
            // Load properties from file
            property.load(fis);

            // Transfer properties from java.util.Properties to HashMap for faster lookup
            property.entrySet().forEach(entry ->
                CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()))
            );

        } catch (IOException e) {
            // If file is not found or cannot be read, throw a RuntimeException to fail fast
            throw new RuntimeException("Failed to load properties file.", e);
        }
    }

    /**
     * Retrieves the value of a given property key from the config map.
     *
     * @param key the property key to lookup
     * @return the value associated with the key
     * @throws RuntimeException if the key is null, missing, or empty
     */
    public static String get(String key) {
        // Validate the key and value existence before returning
        if (Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key)) || CONFIGMAP.get(key).isEmpty()) {
            throw new RuntimeException("Property key '" + key + "' not found in config.properties");
        }
        return CONFIGMAP.get(key);
    }
}

package constants;

/**
 * Central class to store all framework-wide constant values.
 * Avoids hardcoding values in test or utility classes.
 */
public final class FrameworkConstants {

    // Private constructor to prevent instantiation
    private FrameworkConstants() {}

    // Path to the main resource folder
    private static final String RESOURCE_PATH = System.getProperty("user.dir") + "/src/test/resources";

    // Path to the config.properties file
    private static final String CONFIGFILE_PATH = RESOURCE_PATH + "/config/config.properties";

    // Path to Excel file containing test data
    private static final String EXCEL_PATH = RESOURCE_PATH + "/testdata/BusBookingData.xlsx";

    // Path where the Extent report will be generated
    private static final String EXTENT_REPORT_PATH = RESOURCE_PATH + "/report.html";

    // Default explicit wait time (in seconds) used across the framework
    private static final int EXPLICIT_WAIT = 20;

    /**
     * Returns the path to the configuration file.
     * Used wherever we load config.properties.
     */
    public static String getConfigFilePath() {
        return CONFIGFILE_PATH;
    }

    /**
     * Returns the explicit wait time in seconds.
     * This is used by WebDriverWait (centralized wait).
     */
    public static int getExplicitWait() {
        return EXPLICIT_WAIT;
    }

    /**
     * Returns the full path to the Excel file.
     * This is the file we use for data-driven testing.
     */
    public static String getExcelFilePath() {
        return EXCEL_PATH;
    }

    /**
     * Returns the path where the Extent report will be saved.
     * Used during report generation.
     */
    public static String getExtentReportPath() {
        return EXTENT_REPORT_PATH;
    }
}

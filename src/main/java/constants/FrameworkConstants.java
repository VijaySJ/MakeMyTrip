package constants;

/**
 * Holds constant values used across the whole test framework.
 */
public final class FrameworkConstants {

    // Prevents anyone from creating an object of this class
    private FrameworkConstants() {}

    /** Path to the resources folder */
    private static final String RESOURCE_PATH = System.getProperty("user.dir") + "/src/test/resources";

    /** Path to the config.properties file */
    private static final String CONFIGFILE_PATH = RESOURCE_PATH + "/config/config.properties";

    /** Path to the test data Excel file */
    private static final String EXCEL_PATH = RESOURCE_PATH + "/testdata/BusBookingData.xlsx";

    /** Where the Extent test report will be saved */
    private static final String EXTENT_REPORT_PATH = RESOURCE_PATH + "/report.html";
    
 // Folder for screenshots inside the project directory
    private static final String SCREENSHOT_PATH = RESOURCE_PATH + "/screenshots/";

    /** Default wait time (in seconds) for WebDriver explicit waits */
    private static final int EXPLICIT_WAIT = 20;

    // Returns path to the configuration file
    public static String getConfigFilePath() {
        return CONFIGFILE_PATH;
    }

    // Returns the default explicit wait time for WebDriverWait
    public static int getExplicitWait() {
        return EXPLICIT_WAIT;
    }

    // Returns path to Excel data file
    public static String getExcelFilePath() {
        return EXCEL_PATH;
    }

    // Returns path to test report file
    public static String getExtentReportPath() {
        return EXTENT_REPORT_PATH;
    }
    
    // Returns path to screenshot file
    public static String getScreenshotPath() {
        return SCREENSHOT_PATH;
    }
}

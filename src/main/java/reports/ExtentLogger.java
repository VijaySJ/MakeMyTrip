package reports;

import com.aventstack.extentreports.ExtentTest;

/**
 * Utility class for logging test steps, statuses, and screenshots to ExtentReports.
 * 
 * <p>This class uses a ThreadLocal<ExtentTest> variable to store one ExtentTest instance 
 * per test thread, ensuring that logs from parallel tests don't get mixed up.
 *
 * <p>It provides convenience static methods to log different statuses (PASS, FAIL, INFO, SKIP, WARN)
 * and attach screenshots to the report.
 */
public final class ExtentLogger {

    /** 
     * Private constructor to prevent instantiation. 
     * This class should only be used in a static way.
     */
    private ExtentLogger() {}

    /** ThreadLocal variable to store ExtentTest instance for the current thread */
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    /**
     * Sets the ExtentTest instance for the current thread.
     * This is typically called in the TestNG listener when a test starts.
     *
     * @param test The ExtentTest object to associate with the current thread
     */
    public static void setTest(ExtentTest test) {
        TEST.set(test);
    }

    /**
     * Retrieves the ExtentTest instance for the current thread.
     *
     * @return The thread's ExtentTest object, or null if not set
     */
    public static ExtentTest getTest() {
        return TEST.get();
    }

    /**
     * Removes the ExtentTest instance from the ThreadLocal storage.
     * This should be called when a test finishes to prevent memory leaks.
     */
    public static void remove() {
        TEST.remove();
    }

    /**
     * Logs a PASS status to the report.
     * @param message The message describing the passed step
     */
    public static void pass(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.pass(message);
        } else {
            System.err.println("ExtentTest is null in pass(): " + message);
        }
    }

    /**
     * Logs a FAIL status to the report.
     * @param message The message describing the failed step
     */
    public static void fail(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.fail(message);
        } else {
            System.err.println("ExtentTest is null in fail(): " + message);
        }
    }

    /**
     * Logs an INFO message to the report (for step descriptions or checkpoints).
     * @param message The informational message
     */
    public static void info(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.info(message);
        } else {
            System.err.println("ExtentTest is null in info(): " + message);
        }
    }

    /**
     * Logs a SKIP status to the report.
     * @param message The reason or description for skipping
     */
    public static void skip(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.skip(message);
        } else {
            System.err.println("ExtentTest is null in skip(): " + message);
        }
    }

    /**
     * Logs a WARN status (warning) to the report.
     * @param message The warning message
     */
    public static void warn(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.warning(message);
        } else {
            System.err.println("ExtentTest is null in warn(): " + message);
        }
    }

    /**
     * Adds a screenshot to the report.
     *
     * @param path The file path to the screenshot
     */
    public static void addScreenshot(String path) {
        try {
            ExtentTest test = getTest();
            if (test != null && path != null) {
                test.addScreenCaptureFromPath(path); // Attach screenshot to extent report
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package reports;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentLogger {

    // Private constructor to prevent instantiation of utility class
    private ExtentLogger() {
    }

    // ThreadLocal to ensure thread safety in parallel test execution
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    /**
     * Sets the ExtentTest instance for the current thread.
     * This must be called before logging anything, typically in the listener.
     */
    public static void setTest(ExtentTest test) {
        TEST.set(test);
    }

    /**
     * Retrieves the ExtentTest instance associated with the current thread.
     */
    public static ExtentTest getTest() {
        return TEST.get();
    }

    /**
     * Removes the ExtentTest instance from the ThreadLocal storage.
     * This is important to avoid memory leaks after test execution ends.
     */
    public static void remove() {
        TEST.remove();
    }

    /**
     * Logs a PASS status with the provided message.
     * If the test instance is null (e.g., not set), logs to System.err as fallback.
     */
    public static void pass(String message) {
        if (getTest() != null) {
            getTest().pass(message);
        } else {
            System.err.println("ExtentTest is null in pass(): " + message);
        }
    }

    /**
     * Logs a FAIL status with the provided message.
     * If the test instance is null, logs to System.err.
     */
    public static void fail(String message) {
        if (getTest() != null) {
            getTest().fail(message);
        } else {
            System.err.println("ExtentTest is null in fail(): " + message);
        }
    }

    /**
     * Logs an INFO status with the provided message.
     * Use this to log informative steps in your test.
     */
    public static void info(String message) {
        if (getTest() != null) {
            getTest().info(message);
        } else {
            System.err.println("ExtentTest is null in info(): " + message);
        }
    }

    /**
     * Logs a SKIP status with the provided message.
     * Useful for skipped tests or steps.
     */
    public static void skip(String message) {
        if (getTest() != null) {
            getTest().skip(message);
        } else {
            System.err.println("ExtentTest is null in skip(): " + message);
        }
    }
}

package reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import constants.FrameworkConstants;

/**
 * Utility class to manage ExtentReports setup and flushing.
 * Handles singleton creation, configuration, and auto-opening.
 */
public final class ExtentReportManager {

    // Singleton instance to ensure one report per run
    private static ExtentReports extent;

    // Prevent object creation
    private ExtentReportManager() {}

    /**
     * Initializes the ExtentReports instance if not already done.
     * Sets config/theme and attaches system info.
     */
    public static ExtentReports initReport() {
        if (extent == null) {
            // Create Spark reporter and set output path
            ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportPath());

            // Optional: Report appearance/theme settings
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Test Results");

            // Attach reporter to ExtentReports object
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Add environment/tester info
            extent.setSystemInfo("Tester", "Vijay");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    /**
     * Flushes report to disk and tries to auto-open in browser.
     */
    public static void flushReport() throws IOException {
        if (extent != null) {
            extent.flush(); // Write test results to file
            // Try opening report automatically after run
            Desktop.getDesktop().browse(new File(FrameworkConstants.getExtentReportPath()).toURI());
        }
    }

    /**
     * Returns current ExtentReports instance, or null if not yet initialized.
     */
    public static ExtentReports getExtent() {
        return extent;
    }
}

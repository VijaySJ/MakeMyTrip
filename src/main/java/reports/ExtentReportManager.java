package reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import constants.FrameworkConstants;

public final class ExtentReportManager {

    // Singleton ExtentReports instance
    private static ExtentReports extent;

    // Private constructor to prevent instantiation
    private ExtentReportManager() {}

    /**
     * Initializes the ExtentReports instance if not already created.
     * Sets up the SparkReporter, applies configurations, and attaches to ExtentReports.
     *
     * @return the initialized ExtentReports instance
     */
    public static ExtentReports initReport() {
        if (extent == null) {
            // Create Spark reporter with file path
            ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportPath());
            
            // Report appearance configurations
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Test Results");

            // Create and attach reporter to ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Add system/environment info
            extent.setSystemInfo("Tester", "Vijay");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    /**
     * Flushes and writes the report to disk.
     * Also attempts to automatically open the report in the default browser.
     *
     * @throws IOException if opening the report file fails
     */
    public static void flushReport() throws IOException {
        if (extent != null) {
            extent.flush(); // Write report
            // Auto-open report in default browser
            Desktop.getDesktop().browse(new File(FrameworkConstants.getExtentReportPath()).toURI());
        }
    }

    /**
     * Returns the current ExtentReports instance.
     *
     * @return ExtentReports instance or null if not initialized
     */
    public static ExtentReports getExtent() { 
        return extent;
    }
}

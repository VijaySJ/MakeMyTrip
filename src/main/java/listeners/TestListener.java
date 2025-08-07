package listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import reports.ExtentLogger;
import reports.ExtentReportManager;

/**
 * TestNG Listener implementation to hook into test execution lifecycle.
 * This listener is responsible for initializing and managing Extent Reports.
 */
public class TestListener implements ITestListener {

    private static ExtentReports extent;

    /**
     * Called once before any test starts in the suite.
     * We initialize the ExtentReports instance here.
     */
    @Override
    public void onStart(ITestContext context) {
        extent = ExtentReportManager.initReport(); // ✅ Report setup
    }

    /**
     * Called when each @Test method starts.
     * Creates a test node in the Extent report for the method.
     */
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        ExtentLogger.setTest(test); // ✅ Sets current test to thread-safe logger
    }

    /**
     * Called when a test passes.
     * Logs a PASS status in the Extent report.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentLogger.pass("✅ Test Passed");
        ExtentLogger.remove(); // ✅ Clear thread-safe test instance
    }

    /**
     * Called when a test fails.
     * Logs the error/exception to the Extent report.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentLogger.fail("❌ Test Failed: " + result.getThrowable());
        ExtentLogger.remove();
    }

    /**
     * Called when a test is skipped.
     * Logs the skip reason (if any) to the Extent report.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentLogger.skip("⚠️ Test Skipped: " + result.getThrowable());
        ExtentLogger.remove();
    }

    /**
     * Called once after all tests are executed in the suite.
     * Finalizes the report by flushing all logs to the file.
     */
    @Override
    public void onFinish(ITestContext context) {
        try {
            ExtentReportManager.flushReport(); // ✅ Final report write
        } catch (IOException e) {
            e.printStackTrace(); // ⚠️ Consider logging this to a file in real framework
        }
    }
}

package listeners;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import reports.ExtentLogger;
import reports.ExtentReportManager;
import utils.ScreenshotUtils;

public class TestListener implements ITestListener {

    private static ExtentReports extent;

    @Override
    public void onStart(ITestContext context) {
        extent = ExtentReportManager.initReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Default test name is the method name
        String testName = result.getMethod().getMethodName();

        // If DataProvider passed a Map, try to add ScenarioName
        if (result.getParameters().length > 0 && result.getParameters()[0] instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, String> data = (Map<String, String>) result.getParameters()[0];
            String scenario = data.get("ScenarioName");
            if (scenario != null && !scenario.isEmpty()) {
                testName += " - " + scenario;
            }
        }

        ExtentTest test = extent.createTest(testName);
        ExtentLogger.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentLogger.pass("✅ Test Passed");
        ExtentLogger.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentLogger.fail("❌ " + result.getThrowable());

        // Capture and attach screenshot
        String screenshotPath = ScreenshotUtils.captureScreenshot(result.getMethod().getMethodName());
        ExtentLogger.addScreenshot(screenshotPath);

        ExtentLogger.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentLogger.skip("⚠️ " + result.getThrowable());
        ExtentLogger.remove();
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            ExtentReportManager.flushReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import constants.FrameworkConstants;
import driver.DriverManager;

/**
 * Utility class to capture and save screenshots using Selenium WebDriver.
 */
public final class ScreenshotUtils {

    // Prevent instantiation
    private ScreenshotUtils() {}

    /**
     * Captures a screenshot and saves it in the screenshots folder.
     * Filename: testName_timestamp.png
     *
     * @param testName The name of the test (used in the filename)
     * @return Absolute path to the saved screenshot, or null if failed
     */
    public static String captureScreenshot(String testName) {
        String screenshotDir = FrameworkConstants.getScreenshotPath();
        String fileName = testName + "_" + System.currentTimeMillis() + ".png";
        File dest = new File(screenshotDir + fileName);

        try {
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File src = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, dest);
            System.out.println("✅ Screenshot saved: " + dest.getAbsolutePath());
            return dest.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("❌ Failed to save screenshot for test: " + testName);
            e.printStackTrace();
            return null;
        }
    }
}

package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import driver.DriverManager;

/**
 * Utility class to capture and save screenshots.
 * Uses the WebDriver instance from DriverManager to take a screenshot.
 */
public final class ScreenshotUtils {

    // Private constructor to prevent instantiation
    private ScreenshotUtils() {}

    /**
     * Captures a screenshot and saves it to the /screenshots directory.
     *
     * @param testName The name of the test method to use for the screenshot filename.
     */
    public static void captureScreenshot(String testName) {
        // Capture screenshot using WebDriver's TakesScreenshot interface
        File src = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

        // Define screenshot destination path
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        File dest = new File(screenshotDir + testName + ".png");

        try {
            // Create screenshots directory if it doesn't exist
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Copy screenshot to destination
            FileUtils.copyFile(src, dest);
            System.out.println("✅ Screenshot saved: " + dest.getAbsolutePath());

        } catch (IOException e) {
            // Handle file operation exceptions gracefully
            System.err.println("❌ Failed to save screenshot for test: " + testName);
            e.printStackTrace();
        } catch (Exception e) {
            // Catch unexpected runtime issues
            System.err.println("❌ Unexpected error while capturing screenshot for: " + testName);
            e.printStackTrace();
        }
    }
}

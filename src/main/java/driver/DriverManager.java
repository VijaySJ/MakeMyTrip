package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import enums.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Manages WebDriver lifecycle using ThreadLocal (safe for parallel tests).
 */
public final class DriverManager {

    // Holds one WebDriver instance per thread (parallel-safe)
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Prevent object creation
    private DriverManager() {}

    /**
     * Starts WebDriver for the given browser type.
     */
    public static void initDriver(BrowserType browser) {
        if (driver.get() == null) { // Only create if not already started
            switch (browser) {
                case CHROME:
                    WebDriverManager.chromedriver().setup(); // Auto-setup ChromeDriver
                    driver.set(new ChromeDriver(new ChromeOptions())); // Launch Chrome
                    break;

                case EDGE:
                    WebDriverManager.edgedriver().setup(); // Auto-setup EdgeDriver
                    driver.set(new EdgeDriver(new EdgeOptions())); // Launch Edge
                    break;

                default:
                    throw new IllegalStateException("❌ Unsupported browser: " + browser);
            }

            getDriver().manage().window().maximize(); // Fullscreen for consistency
            getDriver().manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10)); // Basic implicit wait
        }
    }

    /**
     * Gets WebDriver for current thread.
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new IllegalStateException("❌ WebDriver not initialized. Call initDriver() first.");
        }
        return driver.get();
    }

    /**
     * Closes browser and clears ThreadLocal instance.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();   // Close browser
            driver.remove();       // Remove driver from ThreadLocal
        }
    }
}

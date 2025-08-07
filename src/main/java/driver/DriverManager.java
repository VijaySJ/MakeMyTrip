package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import enums.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * DriverManager handles the WebDriver lifecycle using ThreadLocal for parallel test execution support.
 * Ensures one driver instance per thread and browser type support (Chrome, Edge).
 */
public final class DriverManager {

    // ThreadLocal ensures thread-safe WebDriver instances during parallel execution
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Private constructor to prevent object creation
    private DriverManager() {
    }

    /**
     * Initializes the WebDriver based on the specified browser type.
     * Supports Chrome and Edge (can be extended to others).
     *
     * @param browser BrowserType enum value (CHROME or EDGE)
     */
    public static void initDriver(BrowserType browser) {
        if (driver.get() == null) {
            switch (browser) {
                case CHROME:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    driver.set(new ChromeDriver(chromeOptions));
                    break;

                case EDGE:
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    driver.set(new EdgeDriver(edgeOptions));
                    break;

                default:
                    throw new IllegalStateException("❌ Unexpected browser type: " + browser);
            }

            // Maximize browser window to ensure consistency in UI layout
            getDriver().manage().window().maximize();
            
         // ✅ Add Implicit Wait
            getDriver().manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        }
    }

    /**
     * Returns the WebDriver instance associated with the current thread.
     * Throws an exception if the driver is not initialized.
     *
     * @return WebDriver instance for the current thread
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new IllegalStateException("❌ WebDriver not initialized. Call initDriver() first.");
        }
        return driver.get();
    }

    /**
     * Quits the browser and cleans up the ThreadLocal driver instance.
     * Always call this at the end of test execution to avoid memory leaks.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Remove reference to ensure clean memory
        }
    }
}

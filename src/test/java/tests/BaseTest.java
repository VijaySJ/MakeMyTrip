package tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import driver.DriverManager;
import enums.BrowserType;
import listeners.TestListener;
import utils.PropertyUtils;

/**
 * BaseTest serves as the superclass for all test classes.
 * It initializes and quits the WebDriver at the suite level.
 */
@Listeners(TestListener.class)
public class BaseTest {

    // Protected constructor to prevent direct object creation
    protected BaseTest() {}

    /**
     * Initializes the browser driver and opens the application URL.
     * Executed once before the entire test suite.
     */
    @BeforeSuite
    public void setUp() {
        try {
            BrowserType browser = BrowserType.valueOf(PropertyUtils.get("browser").toUpperCase());
            DriverManager.initDriver(browser);
            String appUrl = PropertyUtils.get("url");
            DriverManager.getDriver().get(appUrl);
            System.out.println("✅ Browser launched and navigated to: " + appUrl);
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize browser or navigate to URL.");
            e.printStackTrace();
            throw new RuntimeException("Suite setup failed. Stopping execution.");
        }
    }

    /**
     * Quits the browser driver after the entire test suite has completed.
     */
    @AfterSuite
    public void tearDown() {
        try {
            DriverManager.quitDriver();
            System.out.println("✅ Browser closed successfully.");
        } catch (Exception e) {
            System.err.println("❌ Error while closing the browser.");
            e.printStackTrace();
        }
    }
}

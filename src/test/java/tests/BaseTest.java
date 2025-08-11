package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import driver.DriverManager;
import enums.BrowserType;
import listeners.TestListener;
import utils.PropertyUtils;

@Listeners(TestListener.class)
public class BaseTest {

    protected BaseTest() {}

    @BeforeMethod
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
            throw new RuntimeException("Test setup failed. Stopping execution.");
        }
    }

    @AfterMethod
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

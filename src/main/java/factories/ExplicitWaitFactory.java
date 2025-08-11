package factories;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import constants.FrameworkConstants;
import driver.DriverManager;
import enums.WaitStrategy;

/**
 * Central place for handling different explicit wait strategies before returning a WebElement.
 */
public final class ExplicitWaitFactory {

    // Prevent creating objects of this utility class
    private ExplicitWaitFactory() {}

    /**
     * Waits for the element using the given wait strategy and returns it.
     *
     * @param waitStrategy strategy like CLICKABLE / VISIBLE / PRESENCE / NONE
     * @param locator element locator
     * @return WebElement after applying the wait
     */
    public static WebElement performExplicitWait(WaitStrategy waitStrategy, By locator) {
        // Get current driver
        WebDriver driver = DriverManager.getDriver();

        // Create wait object with default timeout from constants
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.getExplicitWait()));

        // Pick wait type based on enum value
        switch (waitStrategy) {
            case CLICKABLE:
                return wait.until(ExpectedConditions.elementToBeClickable(locator)); // visible & enabled

            case VISIBLE:
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); // only visible

            case PRESENCE:
                return wait.until(ExpectedConditions.presenceOfElementLocated(locator)); // in DOM, not always visible

            case NONE:
                return driver.findElement(locator); // no wait at all

            default:
                throw new IllegalStateException("❌ Unknown WaitStrategy: " + waitStrategy);
        }
    }
}

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
 * This factory class centralizes all explicit wait logic
 * based on the defined WaitStrategy enum.
 * 
 * It helps keep the test code clean and consistent by applying
 * the right wait condition before interacting with web elements.
 */
public final class ExplicitWaitFactory {

    // Private constructor to prevent instantiation
    private ExplicitWaitFactory() {}

    /**
     * Applies the appropriate WebDriver explicit wait strategy
     * before returning the WebElement for interaction.
     *
     * @param waitStrategy The strategy to apply (CLICKABLE, VISIBLE, etc.)
     * @param locator The locator of the target element
     * @return The WebElement after the wait condition is satisfied
     */
    public static WebElement performExplicitWait(WaitStrategy waitStrategy, By locator) {
        WebDriver driver = DriverManager.getDriver();

        // Create WebDriverWait with configurable wait time
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.getExplicitWait()));

        switch (waitStrategy) {
            case CLICKABLE:
                // Waits until the element is visible and enabled
                return wait.until(ExpectedConditions.elementToBeClickable(locator));

            case VISIBLE:
                // Waits until the element is visible in the DOM
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            case PRESENCE:
                // Waits until the element is present in the DOM (not necessarily visible)
                return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            case NONE:
                // No wait applied; element is fetched directly
                return driver.findElement(locator);

            default:
                throw new IllegalStateException("❌ Unexpected WaitStrategy: " + waitStrategy);
        }
    }
}

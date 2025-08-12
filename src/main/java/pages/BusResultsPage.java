package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import driver.DriverManager;
import enums.SeatType;
import enums.WaitStrategy;
import factories.ExplicitWaitFactory;

/**
 * Page Object for the Bus Results page.
 * Handles seat selection, boarding/dropping point selection, and clicking Continue.
 */
public class BusResultsPage extends BasePage {

    // ===== Locators =====
    private static final By BUS_TITLE_HEADING = By.xpath("//h1[@data-testid='listing-title']");
    private static final By BUSES_COUNT = By.xpath("//p[contains(text(),'buses found')]");
    private static final By SEATER_AVAILABLE = By.xpath("//div[contains(@class,'Tooltip_tooltipWrapper')]/following::img[contains(@src, 'Seater_Available.png')]");
    private static final By SLEEPER_AVAILABLE = By.xpath("//div[contains(@class,'Tooltip_tooltipWrapper')]/following::img[contains(@src, 'Sleeper_Available.png')]");
    private static final By CONTINUE_BUTTON = By.xpath("//button[contains(text(),'Continue')]");

    /** Gets and returns bus route heading text from results page */
    public String getBusRouteTitle() {
        return getText(BUS_TITLE_HEADING, WaitStrategy.VISIBLE);
    }
    
    /** Gets and returns bus count text from results page */
    public String getBusesCountText() {
    	return getText(BUSES_COUNT, WaitStrategy.VISIBLE);
    }

    /** Clicks "Select Seats" button for the bus at the given result index */
    public BusResultsPage clickSelectSeatsButtonByIndex(int index) {
        String xpath = String.format("(//div[@data-testid='bus-card']//button[contains(text(),'Select Seats')])[%d]", index);
        click(By.xpath(xpath), WaitStrategy.CLICKABLE);
        return this;
    }

    /** Selects given number of available seats based on seat type (SEATER / SLEEPER) */
    public BusResultsPage selectAvailableSeats(SeatType seatType, int count) {
        // Choose locator based on seat type
        List<WebElement> seats = (seatType == SeatType.SEATER)
                ? getElements(SEATER_AVAILABLE)
                : getElements(SLEEPER_AVAILABLE);

        // Select up to 'count' number of seats, but not more than available
        int available = Math.min(count, seats.size());
        for (int i = 0; i < available; i++) {
            seats.get(i).click();
        }
        return this;
    }

    /** Chooses a boarding point from the list using its index position */
    public BusResultsPage selectBoardingPointByIndex(int index) {
        String xpath = String.format(
            "//div[contains(@class,'PickUpDropSelection_pickDropContainer__VSr2j') and .//div[text()='Boarding Points']]//div[contains(@class,'PickUpDropSelection_pickDropItem__YQFG2')][%d]",
            index);
        WebElement option = ExplicitWaitFactory.performExplicitWait(WaitStrategy.CLICKABLE, By.xpath(xpath));
        option.click();
        return this;
    }

    /** Chooses a dropping point from the list using its index position */
    public BusResultsPage selectDroppingPointByIndex(int index) {
        String xpath = String.format(
            "//div[contains(@class,'PickUpDropSelection_pickDropContainer__VSr2j') and .//div[text()='Drop Points']]//div[contains(@class,'PickUpDropSelection_pickDropItem__YQFG2')][%d]",
            index);
        click(By.xpath(xpath), WaitStrategy.CLICKABLE);
        return this;
    }

    /**
     * Checks if 'Continue' button is enabled:
     * - Scrolls it into view
     * - Checks class and disabled attributes
     */
    public boolean waitUntilContinueButtonIsEnabled() {
        try {
            WebElement button = ExplicitWaitFactory.performExplicitWait(WaitStrategy.PRESENCE, CONTINUE_BUTTON);

            // Scroll so the button is visible
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", button);

            // Check if button is NOT marked disabled and is enabled
            String classAttr = button.getAttribute("class");
            String disabledAttr = button.getAttribute("disabled");

            return !((classAttr != null && classAttr.contains("Button_disabled__")) || (disabledAttr != null))
                    && button.isEnabled();
        } catch (Exception e) {
            return false; // If not found or fails, treat it as disabled
        }
    }

    /**
     * Clicks the Continue button if enabled.
     * Uses JS click as fallback if intercepted.
     */
    public void clickContinueButton() {
        try {
            WebElement button = ExplicitWaitFactory.performExplicitWait(WaitStrategy.CLICKABLE, CONTINUE_BUTTON);

            // Scroll to bring button to center view
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", button);

            Thread.sleep(1000); // wait for UI enablement animations

            String classAttr = button.getAttribute("class");
            String disabledAttr = button.getAttribute("disabled");

            // Validate enabled state before clicking
            if (!((classAttr != null && classAttr.contains("Button_disabled__")) || disabledAttr != null)
                    && button.isEnabled()) {
                try {
                    button.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    // JS click fallback if overlay intercepts
                    ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", button);
                }
            } else {
                throw new RuntimeException("Continue button is not enabled, cannot click.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Continue button: " + e.getMessage(), e);
        }
    }
}

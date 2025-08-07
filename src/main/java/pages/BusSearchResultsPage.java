package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import driver.DriverManager;
import enums.SeatType;
import enums.WaitStrategy;
import factories.ExplicitWaitFactory;

public class BusSearchResultsPage extends BasePage {

    // 🔍 Locators for bus search result page
    private static final By BUSTITLEHEADING = By.xpath("//h1[@data-testid='listing-title']");
    private static final By SEATER_AVAILABLE = By.xpath("//div[contains(@class,'Tooltip_tooltipWrapper')]/following::img[contains(@src, 'Seater_Available.png')]");
    private static final By SLEEPER_AVAILABLE = By.xpath("//div[contains(@class,'Tooltip_tooltipWrapper')]/following::img[contains(@src, 'Sleeper_Available.png')]");
    private static final By CONTINUE_BUTTON = By.xpath("//button[contains(text(),'Continue')]");

    /**
     * Fetches the route heading (like “Bangalore → Hyderabad”) from the results page.
     */
    public String getBusRouteTitle() {
        return getText(BUSTITLEHEADING, WaitStrategy.VISIBLE);
    }

    /**
     * Selects seats based on the operator’s name.
     */
    public BusSearchResultsPage clickSelectSeatsByOperator(String operatorName) {
        String dynamicXpath = String.format(
            "//div[@data-testid='bus-card' and .//div[contains(text(),'%s')]]//button[contains(text(),'Select Seats')]",
            operatorName);
        click(By.xpath(dynamicXpath), WaitStrategy.CLICKABLE);
        return this;
    }

    /**
     * Selects seats based on the departure time (like “09:00 PM”).
     */
    public BusSearchResultsPage clickSelectSeatsByTime(String departureTime) {
        String dynamicXpath = String.format(
            "//div[@data-testid='bus-card' and .//div[contains(text(),'%s')]]//button[contains(text(),'Select Seats')]",
            departureTime);
        click(By.xpath(dynamicXpath), WaitStrategy.CLICKABLE);
        return this;
    }

    /**
     * Selects seats based on the index (1-based) of the bus card on the page.
     */
    public BusSearchResultsPage clickSelectSeatsButtonByIndex(int index) {
        String dynamicXpath = String.format("(//div[@data-testid='bus-card']//button[contains(text(),'Select Seats')])[%d]", index);
        click(By.xpath(dynamicXpath), WaitStrategy.CLICKABLE);
        return this;
    }

    /**
     * Selects boarding point by index (1-based). Prints the selected index to console.
     */
    public void selectBoardingPointByIndex(int index) {
        String xpath = String.format(
            "//div[contains(@class,'PickUpDropSelection_pickDropContainer__VSr2j') and .//div[text()='Boarding Points']]" +
            "//div[contains(@class,'PickUpDropSelection_pickDropItem__YQFG2')][%d]", index);
        By locator = By.xpath(xpath);

        WebElement option = ExplicitWaitFactory.performExplicitWait(WaitStrategy.CLICKABLE, locator);
        option.click();
        System.out.println("✅ Selected boarding point at index: " + index);
    }

    /**
     * Selects dropping point by index (1-based). Returns current page for chaining.
     */
    public BusSearchResultsPage selectDroppingPointByIndex(int index) {
        String xpath = String.format(
            "//div[contains(@class,'PickUpDropSelection_pickDropContainer__VSr2j') and .//div[text()='Drop Points']]" +
            "//div[contains(@class,'PickUpDropSelection_pickDropItem__YQFG2')][%d]", index);
        By locator = By.xpath(xpath);

        click(locator, WaitStrategy.CLICKABLE);
        System.out.println("✅ Selected dropping point at index: " + index);
        return this;
    }

    /**
     * Selects available seats of the given seat type (SEATER or SLEEPER).
     * Attempts to click only the requested number of seats.
     * Logs warnings if not enough seats are found.
     */
    public BusSearchResultsPage selectAvailableSeats(SeatType seatType, int count) {
        List<WebElement> seats;
        switch (seatType) {
            case SEATER:
                seats = getElements(SEATER_AVAILABLE);
                break;
            case SLEEPER:
                seats = getElements(SLEEPER_AVAILABLE);
                break;
            default:
                throw new IllegalArgumentException("Unsupported seat type: " + seatType);
        }

        int available = Math.min(count, seats.size());

        for (int i = 0; i < available; i++) {
            seats.get(i).click();
            System.out.println("✅ Selected " + seatType + " seat #" + (i + 1));
        }

        if (available < count) {
            System.out.println("⚠️ Only " + available + " " + seatType + " seats were available.");
        }

        return this;
    }

    /**
     * Waits until the Continue button is enabled by checking class and disabled attributes.
     */
    public boolean waitUntilContinueButtonIsEnabled() {
        try {
            WebElement button = ExplicitWaitFactory.performExplicitWait(WaitStrategy.PRESENCE, CONTINUE_BUTTON);

            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", button);

            String classAttr = button.getAttribute("class");
            String disabledAttr = button.getAttribute("disabled");

            boolean isClassDisabled = classAttr != null && classAttr.contains("Button_disabled__");
            boolean isDisabledAttrPresent = disabledAttr != null;

            boolean enabled = !isClassDisabled && !isDisabledAttrPresent && button.isEnabled();

            System.out.println("Continue button enabled? " + enabled);
            return enabled;
        } catch (Exception e) {
            System.out.println("Error checking Continue button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the Continue button if enabled. Uses JS click as a fallback.
     * Throws runtime exception if the button is still disabled.
     */
    public void clickContinueButton() {
        try {
            WebElement button = ExplicitWaitFactory.performExplicitWait(WaitStrategy.CLICKABLE, CONTINUE_BUTTON);

            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
            Thread.sleep(1000); // Give time for animations to finish

            String classAttr = button.getAttribute("class");
            String disabledAttr = button.getAttribute("disabled");

            boolean isClassDisabled = classAttr != null && classAttr.contains("Button_disabled__");
            boolean isDisabledAttrPresent = disabledAttr != null;

            if (!isClassDisabled && !isDisabledAttrPresent && button.isEnabled()) {
                try {
                    button.click();
                    System.out.println("✅ Continue button clicked.");
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    System.out.println("⚠️ Normal click intercepted. Trying JavaScript click...");
                    ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", button);
                    System.out.println("✅ Continue button clicked via JavaScript.");
                }
            } else {
                throw new RuntimeException("❌ Continue button is not enabled, cannot click.");
            }

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to click Continue button: " + e.getMessage(), e);
        }
    }
}

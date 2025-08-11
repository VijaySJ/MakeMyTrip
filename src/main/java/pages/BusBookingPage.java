package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import driver.DriverManager;
import enums.WaitStrategy;
import factories.ExplicitWaitFactory;

public final class BusBookingPage extends BasePage {

    // ===== Locators =====
    private static final By BUSES_TAB = By.cssSelector("span.chBuses");
    private static final By FROM_CITY_FIELD = By.id("fromCity");
    private static final By FROM_INPUT = By.xpath("//input[@placeholder='From']");
    private static final By TO_INPUT = By.xpath("//input[@placeholder='To']");
    private static final By SEARCH_BUTTON = By.xpath("//button[text()='Search']");
    private static final By LOGIN_POPUP_CLOSE = By.cssSelector("span.commonModal__close");
    private static final By SECONDARY_POPUP_CLOSE = By.xpath("//span[@data-cy='travel-card-close']");
    private static final String EXACT_CITY_XPATH ="//span[@class='sr_city blackText' and text()='%s']";

    public BusBookingPage() {}

    /**
     * Closes any initial popups like login prompt or travel card popup.
     * Added check so we don't throw unnecessary exceptions.
     */
    public BusBookingPage closeInitialPopups() {
        // Try closing login popup if present
        if (getElements(LOGIN_POPUP_CLOSE).size() > 0) {
            click(LOGIN_POPUP_CLOSE, WaitStrategy.CLICKABLE);
            System.out.println("✅ Login popup closed");
        } else {
            System.out.println("ℹ️ Login popup not present");
        }

        // Try closing secondary popup if present
        if (getElements(SECONDARY_POPUP_CLOSE).size() > 0) {
            click(SECONDARY_POPUP_CLOSE, WaitStrategy.CLICKABLE);
            System.out.println("✅ Secondary popup closed");
        } else {
            System.out.println("ℹ️ Secondary popup not present");
        }
        return this;
    }

    /**
     * Clicks on the Buses tab in the homepage.
     * Falls back to JavaScript click if intercepted.
     */
    public BusBookingPage clickBusesTab() {
        try {
            click(BUSES_TAB, WaitStrategy.CLICKABLE);
        } catch (ElementClickInterceptedException e) {
            WebElement el = DriverManager.getDriver().findElement(BUSES_TAB);
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", el);
        }
        return this;
    }

    /** Selects the 'From' city using auto-suggest dropdown */
    public BusBookingPage enterFromCity(String fullCityText) {
        click(FROM_CITY_FIELD, WaitStrategy.CLICKABLE);
        sendKeys(FROM_INPUT, fullCityText.split(",")[0].trim(), WaitStrategy.PRESENCE);
        clickExactCitySuggestion(fullCityText);
        return this;
    }

    /** Selects the 'To' city using auto-suggest dropdown */
    public BusBookingPage enterToCity(String fullCityText) {
        sendKeys(TO_INPUT, fullCityText.split(",")[0].trim(), WaitStrategy.PRESENCE);
        clickExactCitySuggestion(fullCityText);
        return this;
    }

    /**
     * Selects travel date in calendar using aria-label.
     * If normal click fails, scrolls into view and uses JS click.
     */
    public BusBookingPage selectTravelDateByAriaLabel(String travelDate) {
        By dateLocator = By.xpath(
                "//div[contains(@class,'DayPicker-Day') " +
                "and contains(@aria-label,'" + travelDate + "') " +
                "and @aria-disabled='false']"
        );

        WebElement dateElement =
                ExplicitWaitFactory.performExplicitWait(WaitStrategy.CLICKABLE, dateLocator);

        // Scroll to center view (avoid overlay intercept)
        ((JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", dateElement);

        try {
            dateElement.click();
        } catch (ElementClickInterceptedException e) {
            // JS fallback click
            ((JavascriptExecutor) DriverManager.getDriver())
                    .executeScript("arguments[0].click();", dateElement);
        }
        return this;
    }

    /**
     * Clicks Search button and waits for results page's heading.
     */
    public BusResultsPage clickSearch() {
        click(SEARCH_BUTTON, WaitStrategy.CLICKABLE);
        By resultHeading = By.xpath("//h1[@data-testid='listing-title']");
        ExplicitWaitFactory.performExplicitWait(WaitStrategy.VISIBLE, resultHeading);
        return new BusResultsPage();
    }

    /** Clicks on the exact matching city in auto-suggestions */
    private void clickExactCitySuggestion(String fullCityText) {
        By exactCity = By.xpath(String.format(EXACT_CITY_XPATH, fullCityText));
        try {
            click(exactCity, WaitStrategy.CLICKABLE);
        } catch (ElementClickInterceptedException e) {
            WebElement el = DriverManager.getDriver().findElement(exactCity);
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", el);
        }
    }
}

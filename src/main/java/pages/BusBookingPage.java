package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import driver.DriverManager;
import enums.WaitStrategy;
import factories.ExplicitWaitFactory;

public final class BusBookingPage extends BasePage {

    // 🔒 Locators used in the page
    private static final By BUSES_TAB = By.cssSelector("span.chBuses");
    private static final By FROM_CITY_FIELD = By.id("fromCity");
    private static final By FROM_INPUT = By.xpath("//input[@placeholder='From']");
    private static final By TO_INPUT = By.xpath("//input[@placeholder='To']");
    private static final By SEARCH_BUTTON = By.xpath("//button[text()='Search']");
    private static final By LOGIN_POPUP_CLOSE = By.cssSelector("span.commonModal__close");
    private static final By SECONDARY_POPUP_CLOSE = By.xpath("//span[@data-cy='travel-card-close']");

    private static final String EXACT_CITY_XPATH = "//span[@class='sr_city blackText' and text()='%s']";

    // ⛔ Private constructor to prevent instantiation
    public BusBookingPage() {}

    /**
     * Closes the login and travel card popups if they are visible.
     * This ensures the UI is interactable before test proceeds.
     */
    public BusBookingPage closeInitialPopups() {
        try {
            click(LOGIN_POPUP_CLOSE, WaitStrategy.CLICKABLE);
        } catch (Exception e) {
            System.out.println("Login popup not present.");
        }
        try {
            click(SECONDARY_POPUP_CLOSE, WaitStrategy.CLICKABLE);
        } catch (Exception e) {
            System.out.println("Secondary popup not present.");
        }
        return this;
    }

    /**
     * Clicks on the "Buses" tab on the homepage.
     * Falls back to JavaScript click if regular click is intercepted.
     */
    public BusBookingPage clickBusesTab() {
        try {
            click(BUSES_TAB, WaitStrategy.CLICKABLE);
        } catch (ElementClickInterceptedException e) {
            WebElement element = DriverManager.getDriver().findElement(BUSES_TAB);
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", element);
        }
        return this;
    }

    /**
     * Enters the "From" city and selects the exact city from the auto-suggest dropdown.
     * @param fullCityText - e.g., "Bangalore, Karnataka, India"
     */
    public BusBookingPage enterFromCity(String fullCityText) {
        click(FROM_CITY_FIELD, WaitStrategy.CLICKABLE);
        String cityOnly = fullCityText.split(",")[0].trim();
        sendKeys(FROM_INPUT, cityOnly, WaitStrategy.PRESENCE);
        clickExactCitySuggestion(fullCityText);
        return this;
    }

    /**
     * Enters the "To" city and selects the exact match from the suggestions.
     * @param fullCityText - e.g., "Hyderabad, Telangana, India"
     */
    public BusBookingPage enterToCity(String fullCityText) {
        String cityOnly = fullCityText.split(",")[0].trim();
        sendKeys(TO_INPUT, cityOnly, WaitStrategy.PRESENCE);
        clickExactCitySuggestion(fullCityText);
        return this;
    }

    /**
     * Selects the exact matching city suggestion from the auto-suggest dropdown.
     * Uses JS click as fallback if intercepted.
     */
    private void clickExactCitySuggestion(String fullCityText) {
        By exactCity = By.xpath(String.format(EXACT_CITY_XPATH, fullCityText));
        try {
            click(exactCity, WaitStrategy.CLICKABLE);
        } catch (ElementClickInterceptedException e) {
            WebElement element = DriverManager.getDriver().findElement(exactCity);
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", element);
        }
    }

    /**
     * Selects travel date from the calendar using the aria-label attribute value.
     * Format should match the site, e.g., "Wednesday, August 7, 2025"
     */
    public BusBookingPage selectTravelDateByAriaLabel(String travelDate) {
        By dateLocator = By.xpath("//div[contains(@class, 'DayPicker-Day') and contains(@aria-label, '" + travelDate
                + "') and @aria-disabled='false']");
        click(dateLocator, WaitStrategy.CLICKABLE);
        return this;
    }

    /**
     * Clicks the Search button to initiate the bus search.
     * Waits for results page heading to confirm navigation.
     * @return BusSearchResultsPage instance.
     */
    public BusSearchResultsPage clickSearch() {
        click(SEARCH_BUTTON, WaitStrategy.CLICKABLE);

        // Confirm navigation to results page by waiting for heading
        By resultHeading = By.xpath("//h1[@data-testid='listing-title']");
        ExplicitWaitFactory.performExplicitWait(WaitStrategy.VISIBLE, resultHeading);

        return new BusSearchResultsPage();
    }
}

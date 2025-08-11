package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import driver.DriverManager;
import enums.WaitStrategy;
import factories.ExplicitWaitFactory;

/**
 * BasePage contains common Selenium actions reused across all page classes
 * to follow DRY principle in the Page Object Model.
 */
public class BasePage {

    /** Clicks an element after applying the given wait strategy */
    protected void click(By locator, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
        element.click();
    }

    /** Sends text to an input after clearing it first */
    protected void sendKeys(By locator, String value, WaitStrategy waitStrategy) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Input value cannot be null or empty"); // Validation
        }
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
        element.clear();
        element.sendKeys(value);
    }

    /** Sends text without clearing the field (for auto-suggest fields) */
    protected void sendKeysWithoutClear(By locator, String value, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(waitStrategy, locator).sendKeys(value);
    }

    /** Presses ENTER key on the given element */
    protected void pressEnter(By locator, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(waitStrategy, locator).sendKeys(Keys.ENTER);
    }

    /** Returns current page title */
    protected String getPageTitle() {
        return DriverManager.getDriver().getTitle();
    }

    /** Returns true if element is displayed */
    protected boolean isDisplayed(By locator, WaitStrategy waitStrategy) {
        return ExplicitWaitFactory.performExplicitWait(waitStrategy, locator).isDisplayed();
    }

    /** Gets visible text of an element */
    protected String getText(By locator, WaitStrategy waitStrategy) {
        return ExplicitWaitFactory.performExplicitWait(waitStrategy, locator).getText();
    }

    /** Hovers mouse over an element */
    protected void hoverOverElement(By locator, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
        new Actions(DriverManager.getDriver()).moveToElement(element).perform();
    }

    /** Select dropdown option by visible text */
    protected void selectDropdownByText(By locator, WaitStrategy waitStrategy, String text) {
        new Select(ExplicitWaitFactory.performExplicitWait(waitStrategy, locator)).selectByVisibleText(text);
    }

    /** Select dropdown option by value attribute */
    protected void selectDropdownByValue(By locator, WaitStrategy waitStrategy, String value) {
        new Select(ExplicitWaitFactory.performExplicitWait(waitStrategy, locator)).selectByValue(value);
    }

    /** Select dropdown option by index position */
    protected void selectDropdownByIndex(By locator, WaitStrategy waitStrategy, int index) {
        new Select(ExplicitWaitFactory.performExplicitWait(waitStrategy, locator)).selectByIndex(index);
    }

    /** Scrolls to an element (brings into view) */
    protected void scrollToElement(By locator, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
        new Actions(DriverManager.getDriver()).moveToElement(element).perform();
    }

    /** Click using Actions class (useful for tricky JS elements) */
    protected void actionClick(By locator, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
        new Actions(DriverManager.getDriver()).moveToElement(element).click().perform();
    }

    /** Returns element without applying any wait */
    protected WebElement getElement(By locator) {
        return DriverManager.getDriver().findElement(locator);
    }

    /** Returns list of elements without explicit wait */
    protected List<WebElement> getElements(By locator) {
        return DriverManager.getDriver().findElements(locator);
    }

    /**
     * Picks a date from a calendar UI by matching month/year
     * - Keeps clicking 'next' until desired month/year is visible
     */
    protected void selectDateInCalendar(String expectedMonthYear, String expectedDay,
                                        By allMonthHeadersLocator, By nextArrowLocator,
                                        String dayXpathTemplate) {

        boolean dateSelected = false;

        // Loop until the target date is selected
        while (!dateSelected) {
            List<WebElement> monthHeaders = DriverManager.getDriver().findElements(allMonthHeadersLocator);

            for (WebElement monthHeader : monthHeaders) {
                if (monthHeader.getText().equalsIgnoreCase(expectedMonthYear)) {
                    // Build dynamic day XPath and click
                    String finalDayXpath = String.format(dayXpathTemplate, expectedDay);
                    click(By.xpath(finalDayXpath), WaitStrategy.CLICKABLE);
                    dateSelected = true;
                    break;
                }
            }

            // If month not found, click next arrow
            if (!dateSelected) {
                click(nextArrowLocator, WaitStrategy.CLICKABLE);
            }
        }
    }
}

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
 * BasePage class contains reusable Selenium actions used across all pages.
 * Follows Page Object Model best practices with centralized utility methods.
 */
public class BasePage {

	/**
	 * Clicks on a web element after applying specified wait strategy.
	 */
	protected void click(By locator, WaitStrategy waitStrategy) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		element.click();
	}

	/**
	 * Sends text input after clearing the field.
	 * Useful for standard form fields.
	 */
	protected void sendKeys(By locator, String value, WaitStrategy waitStrategy) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException("Input value cannot be null or empty");
		}
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		element.clear();
		element.sendKeys(value);
	}

	/**
	 * Sends text input without clearing the field.
	 * Suitable for auto-suggest and city selection fields.
	 */
	protected void sendKeysWithoutClear(By locator, String value, WaitStrategy waitStrategy) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		element.sendKeys(value);
	}

	/**
	 * Presses Enter key on a specific element.
	 * Useful in search or auto-complete fields.
	 */
	protected void pressEnter(By locator, WaitStrategy waitStrategy) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		element.sendKeys(Keys.ENTER);
	}

	/**
	 * Returns the title of the current page.
	 */
	protected String getPageTitle() {
		return DriverManager.getDriver().getTitle();
	}

	/**
	 * Checks whether a given element is visible on the page.
	 */
	protected boolean isDisplayed(By locator, WaitStrategy waitStrategy) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		return element.isDisplayed();
	}

	/**
	 * Fetches text from a given element.
	 */
	protected String getText(By locator, WaitStrategy waitStrategy) {
		return ExplicitWaitFactory.performExplicitWait(waitStrategy, locator).getText();
	}

	/**
	 * Hovers mouse over the given element using Actions class.
	 */
	protected void hoverOverElement(By locator, WaitStrategy waitStrategy) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		Actions actions = new Actions(DriverManager.getDriver());
		actions.moveToElement(element).perform();
	}

	/**
	 * Selects a dropdown value using visible text.
	 */
	protected void selectDropdownByText(By locator, WaitStrategy waitStrategy, String text) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		new Select(element).selectByVisibleText(text);
	}

	/**
	 * Selects a dropdown value using its value attribute.
	 */
	protected void selectDropdownByValue(By locator, WaitStrategy waitStrategy, String value) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		new Select(element).selectByValue(value);
	}

	/**
	 * Selects a dropdown option by its index position.
	 */
	protected void selectDropdownByIndex(By locator, WaitStrategy waitStrategy, int index) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		new Select(element).selectByIndex(index);
	}

	/**
	 * Scrolls to the element to bring it into view.
	 */
	protected void scrollToElement(By locator, WaitStrategy waitStrategy) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		Actions actions = new Actions(DriverManager.getDriver());
		actions.moveToElement(element).perform();
	}

	/**
	 * Performs click using Actions class, useful for dynamic or JS-heavy elements.
	 */
	protected void actionClick(By locator, WaitStrategy waitStrategy) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
		Actions actions = new Actions(DriverManager.getDriver());
		actions.moveToElement(element).click().perform();
	}

	/**
	 * Returns a single WebElement directly without applying wait.
	 */
	protected WebElement getElement(By locator) {
		return DriverManager.getDriver().findElement(locator);
	}

	/**
	 * Returns a list of WebElements for a given locator.
	 */
	protected List<WebElement> getElements(By locator) {
		return DriverManager.getDriver().findElements(locator);
	}

	/**
	 * Selects a date from a calendar UI widget by matching the displayed month/year.
	 * Continues clicking next arrow until expected month/year is found.
	 */
	protected void selectDateInCalendar(String expectedMonthYear, String expectedDay, By allMonthHeadersLocator,
			By nextArrowLocator, String dayXpathTemplate) {
		boolean dateSelected = false;

		while (!dateSelected) {
			List<WebElement> monthHeaders = DriverManager.getDriver().findElements(allMonthHeadersLocator);

			for (WebElement monthHeader : monthHeaders) {
				if (monthHeader.getText().equalsIgnoreCase(expectedMonthYear)) {
					String finalDayXpath = String.format(dayXpathTemplate, expectedDay);
					By dayLocator = By.xpath(finalDayXpath);
					click(dayLocator, WaitStrategy.CLICKABLE);
					dateSelected = true;
					break;
				}
			}

			if (!dateSelected) {
				click(nextArrowLocator, WaitStrategy.CLICKABLE);
			}
		}
	}
}

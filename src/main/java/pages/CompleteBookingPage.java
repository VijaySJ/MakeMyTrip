package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import enums.WaitStrategy;

public final class CompleteBookingPage extends BasePage {
	
	// Locators for the complete booking screen
	private static final By COMPLETE_BOOKING_HEADER = By.xpath("//h1[text()='Complete your booking']");
	private static final By TRAVEL_DETAILS_FIRST_NAME = By.xpath("//label[text()='Name']/following::input[@id='fname']");
	private static final By TRAVEL_DETAILS_AGE = By.xpath("//label[text()='Age']/following::input[@id='age']");
	private static final By GENDER_MALE = By.xpath("//div[@class='maleTab ']");
	private static final By EMAIL_ID = By.xpath("//input[@id='contactEmail']");
	private static final By MOBILE_NUMBER = By.xpath("//input[@id='mobileNumber']");
	private static final By STATE_CONFIRMATION_CHECKBOX = By.xpath("//input[@id='cb_gst_info']/following-sibling::b");
	private static final By SELECT_STATE = By.xpath("//input[@value='Select State']");
	private static final By STATE_LIST = By.xpath("//ul[@class='dropdownListWpr']/li");
	private static final By CONTINUE_BTN = By.xpath("//span[.='Continue']");
	private static final By PLEASE_WAIT_POPUP = By.xpath("//div[contains(text(),'Please wait')]");

	// This method confirms that we're on the "Complete your booking" page by checking the header
	public String verifyCompleteBookingHeader() {
	    return getText(COMPLETE_BOOKING_HEADER, WaitStrategy.VISIBLE);
	}
	
	// Fills in the first name field under travel details section
	public void fillTravelDetailsFirstNameField(String fname) {
		sendKeys(TRAVEL_DETAILS_FIRST_NAME, fname, WaitStrategy.PRESENCE);
	}

	// Enters the passenger's age in the age field
	public void fillTravelDetailsAgeField(String age) {
		sendKeys(TRAVEL_DETAILS_AGE, age, WaitStrategy.PRESENCE);
	}

	// Clicks on the "Male" gender option
	public void clickGender() {
		click(GENDER_MALE, WaitStrategy.VISIBLE);
	}

	// Fills in the passenger's email address
	public void fillEmailId(String emailId) {
		sendKeys(EMAIL_ID, emailId, WaitStrategy.PRESENCE);
	}

	// Fills in the passenger's mobile number
	public void fillMobileNumber(String mobileNumber) {
		sendKeys(MOBILE_NUMBER, mobileNumber, WaitStrategy.PRESENCE);
	}

	// Clicks the checkbox to confirm the selected state for GST details
	public void checkStateConfirmationCheckbox() {
		click(STATE_CONFIRMATION_CHECKBOX, WaitStrategy.VISIBLE);
	}

	// Selects a state from the dropdown based on the value passed (e.g., "Tamil Nadu")
	public void selectState(String stateName) {
	    // Step 1: Open the dropdown
	    click(SELECT_STATE, WaitStrategy.CLICKABLE);

	    // Step 2: Get the list of all state options
	    List<WebElement> options = getElements(STATE_LIST);

	    // Step 3: Loop through and click the correct state
	    boolean found = false;
	    for (WebElement option : options) {
	        if (option.getText().trim().equalsIgnoreCase(stateName)) {
	            option.click();
	            found = true;
	            System.out.println("✅ Selected state: " + stateName);
	            break;
	        }
	    }

	    // Step 4: If state not found, throw an error
	    if (!found) {
	        throw new RuntimeException("❌ State not found in dropdown: " + stateName);
	    }
	}

	// Clicks the "Continue" button at the bottom of the form
	public void clickContinueBtn() {
		click(CONTINUE_BTN, WaitStrategy.VISIBLE);
	}
	
	public boolean verifyPleaseWaitPopup() {
		return isDisplayed(PLEASE_WAIT_POPUP, WaitStrategy.VISIBLE);
		
	}
}

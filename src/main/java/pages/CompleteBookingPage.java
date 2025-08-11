package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import enums.WaitStrategy;

/**
 * Page Object for the 'Complete your booking' page.
 * Handles filling traveller info, selecting state, and proceeding with booking.
 */
public final class CompleteBookingPage extends BasePage {

    // ===== Locators =====
    private static final By COMPLETE_BOOKING_HEADER = By.xpath("//h1[text()='Complete your booking']");
    private static final By TRAVEL_DETAILS_NAME = By.xpath("//label[text()='Name']/following::input[@id='fname']");
    private static final By TRAVEL_DETAILS_AGE = By.xpath("//label[text()='Age']/following::input[@id='age']");
    private static final By GENDER_MALE = By.xpath("//div[@class='maleTab ']");
    private static final By GENDER_FEMALE = By.xpath("//div[@class='femaleTab ']");
    private static final By EMAIL_ID = By.xpath("//input[@id='contactEmail']");
    private static final By MOBILE_NUMBER = By.xpath("//input[@id='mobileNumber']");
    private static final By STATE_CONFIRMATION_CHECKBOX = By.xpath("//input[@id='cb_gst_info']/following-sibling::b");
    private static final By SELECT_STATE = By.xpath("//input[@value='Select State']");
    private static final By STATE_LIST = By.xpath("//ul[@class='dropdownListWpr']/li");
    private static final By DONT_NEED_RADIO = By.xpath("//span[@class='appendLeft10']");
    private static final By CONTINUE_BTN = By.xpath("//span[.='Continue']");
    private static final By PLEASE_WAIT_POPUP = By.xpath("//div[contains(text(),'Please wait')]");

    /** Gets the header text to confirm user is on Complete Booking page */
    public String verifyCompleteBookingHeader() {
        return getText(COMPLETE_BOOKING_HEADER, WaitStrategy.VISIBLE);
    }

    /** Fills traveller's name */
    public CompleteBookingPage fillTravelDetailsNameField(String fname) {
        sendKeys(TRAVEL_DETAILS_NAME, fname, WaitStrategy.PRESENCE);
        return this;
    }

    /** Fills traveller's age */
    public CompleteBookingPage fillTravelDetailsAgeField(String age) {
        sendKeys(TRAVEL_DETAILS_AGE, age, WaitStrategy.PRESENCE);
        return this;
    }
    /**
     * Selects the desired gender tab ("Male" or "Female").
     * @param gender Should be "Male" or "Female" (case insensitive).
     * @return the current CompleteBookingPage instance for method chaining.
     */
    public CompleteBookingPage selectGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            click(GENDER_MALE, WaitStrategy.VISIBLE);
        } else if ("female".equalsIgnoreCase(gender)) {
            click(GENDER_FEMALE, WaitStrategy.VISIBLE);
        } else {
            throw new IllegalArgumentException("Gender must be 'Male' or 'Female'");
        }
        return this;
    }

    /** Enters contact email address */
    public CompleteBookingPage fillEmailId(String emailId) {
        sendKeys(EMAIL_ID, emailId, WaitStrategy.PRESENCE);
        return this;
    }

    /** Enters contact mobile number */
    public CompleteBookingPage fillMobileNumber(String mobileNumber) {
        sendKeys(MOBILE_NUMBER, mobileNumber, WaitStrategy.PRESENCE);
        return this;
    }

    /** Checks the GST/state confirmation checkbox */
    public CompleteBookingPage checkStateConfirmationCheckbox() {
        click(STATE_CONFIRMATION_CHECKBOX, WaitStrategy.VISIBLE);
        return this;
    }

    /**
     * Selects a state from the dropdown.
     * Throws an exception if the given state is not found.
     */
    public CompleteBookingPage selectState(String stateName) {
        click(SELECT_STATE, WaitStrategy.CLICKABLE); // open dropdown
        List<WebElement> options = getElements(STATE_LIST); // get all state options
        boolean found = false;
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(stateName)) {
                option.click();
                found = true;
                break;
            }
        }
        if (!found) throw new RuntimeException("State not found in dropdown: " + stateName);
        return this;
    }

    /** Clicks the Continue button to proceed to payment */
    public CompleteBookingPage clickContinueBtn() {
        try {
            if (isDisplayed(DONT_NEED_RADIO, WaitStrategy.VISIBLE)) {
                click(DONT_NEED_RADIO, WaitStrategy.CLICKABLE);
            }
        } catch (TimeoutException e) {
            // DONT_NEED_RADIO not present, proceed without clicking
        }
        click(CONTINUE_BTN, WaitStrategy.CLICKABLE);
        return this;
    }


    /** Checks if 'Please wait' popup is displayed (after continue click) */
    public boolean verifyPleaseWaitPopup() {
        return isDisplayed(PLEASE_WAIT_POPUP, WaitStrategy.VISIBLE);
    }
}

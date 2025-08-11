package pages;

import org.openqa.selenium.By;
import enums.WaitStrategy;

/**
 * Page Object for the Payment page.
 * Used for verifying payment-related headers and amounts.
 */
public final class PaymentPage extends BasePage {

    // ===== Locators =====
    private static final By SCAN_TO_PAY = By.xpath("//div[@class='displayContents']/child::div[.='Scan to Pay']");
    private static final By PAYMENT_OPTIONS = By.xpath("//h2[text()='Payment Options']");
    private static final By TOTAL_DUE = By.xpath("//p[contains(normalize-space(),'Total Due')]");
    private static final By TOTAL_DUE_AMOUNT = By.xpath("//p[contains(normalize-space(),'Total Due')]/span//span[contains(text(),'₹')]");

    /** Returns the 'Scan to Pay' header text */
    public String getScanToPayHeaderText() {
        return getText(SCAN_TO_PAY, WaitStrategy.VISIBLE);
    }

    /** Returns the 'Payment Options' header text */
    public String getPaymentOptionsHeaderText() {
        return getText(PAYMENT_OPTIONS, WaitStrategy.VISIBLE);
    }

    /**
     * Gets the 'Total Due' label (without the amount).
     * Splits the text by '₹' and trims extra spaces.
     */
    public String getTotalDueLabelText() {
        String fullText = getText(TOTAL_DUE, WaitStrategy.VISIBLE);
        return fullText.split("₹")[0].trim(); // first part before ₹
    }

    /** Returns the amount value from 'Total Due' */
    public String getTotalDueAmountText() {
        return getText(TOTAL_DUE_AMOUNT, WaitStrategy.VISIBLE);
    }
}

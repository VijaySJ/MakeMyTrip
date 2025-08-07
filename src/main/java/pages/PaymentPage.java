package pages;

import org.openqa.selenium.By;

import enums.WaitStrategy;

public final class PaymentPage extends BasePage{
	
	private static final By SCAN_TO_PAY = By.xpath("//div[@class='displayContents']/child::div[.='Scan to Pay']");
	private static final By PAYMENT_OPTIONS = By.xpath("//h2[text()='Payment Options']");
	private static final By TOTAL_DUE = By.xpath("//p[text()='Total Due']");
	private static final By TOTAL_DUE_AMOUNT = By.xpath("//p[contains(text(),'Total Due')]/span//span[contains(text(),'₹')]");
	
	
	public String verifyScanToHeader() {
		return getText(SCAN_TO_PAY, WaitStrategy.VISIBLE);
	}
	
	public String verifyPaymentOptions() {
		return getText(PAYMENT_OPTIONS, WaitStrategy.VISIBLE);
	}
	
	public String vertifyTotalDue() {
		return getText(TOTAL_DUE, WaitStrategy.VISIBLE);
	}
	
	public String verifyTotalDueAmount() {
		return getText(TOTAL_DUE_AMOUNT, WaitStrategy.VISIBLE);
	}
}

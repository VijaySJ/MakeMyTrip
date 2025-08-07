package tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import dataProviders.ExcelDataProvider;
import enums.SeatType;
import pages.BusBookingPage;
import pages.BusSearchResultsPage;
import pages.CompleteBookingPage;
import pages.PaymentPage;

public class BusBookingTest extends BaseTest {

	private BusSearchResultsPage resultsPage;
	private CompleteBookingPage completeBooking;
	private PaymentPage payment;
	private String fromCity, toCity, travelDate;

	@Test(priority = 1, dataProvider = "getBusBookingData", dataProviderClass = ExcelDataProvider.class)
	public void setupBusBooking(Map<String, String> testData) {
		fromCity = testData.get("fromCity");
		toCity = testData.get("toCity");
		travelDate = testData.get("travelDate");

		resultsPage = new BusBookingPage().closeInitialPopups().clickBusesTab().enterFromCity(fromCity)
				.enterToCity(toCity).selectTravelDateByAriaLabel(travelDate).clickSearch();
	}

	@Test(dependsOnMethods = "setupBusBooking")
	public void verifyBusTitleHeading() {
		String expectedTitle = fromCity + " to " + toCity + " Bus";
		String actualTitle = resultsPage.getBusRouteTitle();
		Assert.assertEquals(actualTitle, expectedTitle, "❌ Bus route title mismatch!");
	}

	@Test(dependsOnMethods = "verifyBusTitleHeading")
	public void clickSelectSeatsButton() {
		resultsPage.clickSelectSeatsButtonByIndex(1);
	}

	@Test(dependsOnMethods = "clickSelectSeatsButton")
	public void selectAvailableSeat() throws InterruptedException {
		//Thread.sleep(5000);
		resultsPage.selectAvailableSeats(SeatType.SEATER, 1);
	}

	@Test(dependsOnMethods = "selectAvailableSeat")
	public void selectBoardingPoint() throws InterruptedException {
		//Thread.sleep(5000);
		resultsPage.selectBoardingPointByIndex(1);
	}

	@Test(dependsOnMethods = "selectBoardingPoint")
	public void selectDroppingPoint() throws InterruptedException {
		//Thread.sleep(5000);
		resultsPage.selectDroppingPointByIndex(1);
	}

	@Test(dependsOnMethods = "selectDroppingPoint")
	public void clickContinueButton() {
		try {
			Thread.sleep(5000); // Let the page update button status
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean isEnabled = resultsPage.waitUntilContinueButtonIsEnabled();
		resultsPage.clickContinueButton();
		Assert.assertTrue(isEnabled, "❌ Continue button is NOT enabled after selecting boarding and dropping points!");
	}

	@Test(dependsOnMethods = "clickContinueButton")
	public void fillCompleteBookingDetails() {
		completeBooking = new CompleteBookingPage();
		completeBooking.verifyCompleteBookingHeader();
		completeBooking.fillTravelDetailsFirstNameField("Vijay S");
		completeBooking.fillTravelDetailsAgeField("27");
		completeBooking.clickGender();
		completeBooking.fillEmailId("vijaysjofficial@gmail.com");
		completeBooking.fillMobileNumber("9994050623");
		completeBooking.checkStateConfirmationCheckbox();
		//completeBooking.selectState("Tamil Nadu");
		completeBooking.clickContinueBtn();
		completeBooking.verifyPleaseWaitPopup();
	}
	
	@Test(dependsOnMethods = "fillCompleteBookingDetails")
	public void verifyPaymentDetails() {
		payment = new PaymentPage();
		payment.verifyScanToHeader();
		payment.verifyPaymentOptions();
		payment.vertifyTotalDue();
		payment.verifyTotalDueAmount().contains("₹");
	}

}

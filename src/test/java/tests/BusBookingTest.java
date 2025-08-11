package tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import dataProviders.ExcelDataProvider;
import enums.SeatType;
import pages.BusBookingPage;
import pages.BusResultsPage;
import pages.CompleteBookingPage;
import pages.PaymentPage;
import reports.ExtentLogger;  // ✅ Import the logger

public final class BusBookingTest extends BaseTest {

    @Test(dataProvider = "BusBooking", dataProviderClass = ExcelDataProvider.class)
    public void busTicketBookingFlow(Map<String, String> data) {

        ExtentLogger.info("<=== Starting scenario: " + data.get("Scenario") + " ===>");

        // ===== Step 1: Bus search =====
        ExtentLogger.info("Searching bus from " + data.get("fromCity") + " to " + data.get("toCity"));
        BusResultsPage resultsPage = new BusBookingPage()
            .closeInitialPopups()
            .clickBusesTab()
            .enterFromCity(data.get("fromCity"))
            .enterToCity(data.get("toCity"))
            .selectTravelDateByAriaLabel(data.get("travelDate"))
            .clickSearch();
        ExtentLogger.pass("Bus search completed");

        // ===== Step 2: Select bus, seats & boarding/dropping points =====
        String expectedRoute = data.get("fromCity") + " to " + data.get("toCity") + " Bus";
        String busesCount = resultsPage.getBusesCountText();
        ExtentLogger.info("Buses Count: "+busesCount);
        Assert.assertEquals(resultsPage.getBusRouteTitle(), expectedRoute, "Route title mismatch");
        ExtentLogger.info("Verified search results route title: "+resultsPage.getBusRouteTitle());

        resultsPage
            .clickSelectSeatsButtonByIndex(1)
            .selectAvailableSeats(SeatType.SEATER, 1)
            .selectBoardingPointByIndex(1)
            .selectDroppingPointByIndex(1);

        Assert.assertTrue(resultsPage.waitUntilContinueButtonIsEnabled(), "Continue button not enabled");
        resultsPage.clickContinueButton();
        ExtentLogger.pass("Seat selection and boarding/dropping points completed");

        // ===== Step 3: Passenger details =====
        CompleteBookingPage completeBooking = new CompleteBookingPage();
        Assert.assertEquals(completeBooking.verifyCompleteBookingHeader(), "Complete your booking");
        ExtentLogger.info("On Complete Booking Page Header is Visible");

        completeBooking
            .fillTravelDetailsNameField(data.get("Name"))
            .fillTravelDetailsAgeField(data.get("Age"))
            .selectGender(data.get("Gender"))
            .fillEmailId(data.get("Email"))
            .fillMobileNumber(data.get("MobileNumber"))
            .checkStateConfirmationCheckbox()
            .clickContinueBtn();

        Assert.assertTrue(completeBooking.verifyPleaseWaitPopup(), "Please wait popup not displayed");
        ExtentLogger.pass("Passenger details filled and continue pressed");

        // ===== Step 4: Payment page =====
        PaymentPage payment = new PaymentPage();
        Assert.assertEquals(payment.getScanToPayHeaderText(), "Scan to Pay");
        Assert.assertEquals(payment.getPaymentOptionsHeaderText(), "Payment Options");
        Assert.assertEquals(payment.getTotalDueLabelText(), "Total Due");
        ExtentLogger.info("Total Due Amount: "+ payment.getTotalDueAmountText());
        Assert.assertTrue(payment.getTotalDueAmountText().contains("₹"), "Total Due amount missing currency symbol");
        ExtentLogger.pass("Payment page verification completed successfully");

        ExtentLogger.info("<=== Scenario End: " + data.get("Scenario") + " ===>");
    }
}

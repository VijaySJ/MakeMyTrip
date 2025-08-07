package dataProviders;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import utils.ExcelUtils;

/**
 * This class provides test data to TestNG test methods using @DataProvider.
 * It fetches data from an Excel sheet via the ExcelUtils utility.
 */
public final class ExcelDataProvider {

    // Prevent instantiation
    private ExcelDataProvider() {}

    /**
     * DataProvider method for supplying test data related to bus booking.
     * Sheet name = "BusData" in the Excel file.
     *
     * @return Object[] - Array of test data rows (as Map<String, String>)
     */
    @DataProvider(name = "getBusBookingData")
    public static Object[] provideLoginData() {
        try {
            // Reading test data from the Excel sheet named "BusData"
            return ExcelUtils.getData("BusData");
        } catch (IOException e) {
            // Log the error and return an empty array to prevent test failure at startup
            System.err.println("❌ Failed to read data from Excel sheet 'BusData': " + e.getMessage());
            e.printStackTrace();
            return new Object[0];
        }
    }
}

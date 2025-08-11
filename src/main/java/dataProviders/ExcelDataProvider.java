package dataProviders;

import org.testng.annotations.DataProvider;
import utils.ExcelUtils;

/**
 * Provides test data to TestNG tests by reading from Excel sheets.
 */
public class ExcelDataProvider {

    /**
     * DataProvider that returns merged data from two Excel sheets.
     *
     * @return Object[] where each element is a single test data set (merged from both sheets)
     * @throws Exception if Excel reading fails
     */
    @DataProvider(name = "BusBooking", parallel = false)
    public Object[] getFullFlowData() throws Exception {
        // Reads 'BusData' and 'BookingData' sheets and merges each row into one data set
        // Example: Row 1 from BusData + Row 1 from BookingData = single Map<String, String>
        return ExcelUtils.getData("BusBookingData");
    }
}

package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import constants.FrameworkConstants;

/**
 * Utility class for reading Excel test data.
 * Uses Apache POI to fetch data into Object[] (for TestNG DataProviders).
 */
public class ExcelUtils {

    // Private constructor to prevent object creation
    private ExcelUtils() {}

    /**
     * Reads a given sheet's data and returns it as an Object[].
     * Each element is a Map<ColumnHeader, CellValue>.
     */
    public static Object[] getData(String sheetName) throws IOException {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.getExcelFilePath());
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("❌ Sheet not found: " + sheetName);

            int rowCount = sheet.getLastRowNum(); // total data rows (excluding header)
            int colCount = sheet.getRow(0).getLastCellNum(); // total columns in header

            DataFormatter formatter = new DataFormatter(); // formats numeric/date into String
            Object[] data = new Object[rowCount]; // Data array for return

            // Loop through data rows (start from 1, row 0 is header)
            for (int i = 1; i <= rowCount; i++) {
                Map<String, String> map = new HashMap<>();
                Row row = sheet.getRow(i);

                // Loop through every column for this row
                for (int j = 0; j < colCount; j++) {
                    String key = sheet.getRow(0).getCell(j).getStringCellValue().trim(); // header as key
                    String value = "";
                    if (row != null && row.getCell(j) != null) {
                        value = formatter.formatCellValue(row.getCell(j)); // safe text value
                    }
                    map.put(key, value);
                }
                data[i - 1] = map; // store row map into Object[]
            }
            return data;
        }
    }

    /**
     * Reads and merges two sheets row-by-row into a single Object[].
     * Both sheets must have the same number of rows.
     */
    @SuppressWarnings("unchecked")
    public static Object[] getMergedData(String sheet1, String sheet2) throws IOException {
        Object[] data1 = getData(sheet1);
        Object[] data2 = getData(sheet2);

        // Check row count match
        if (data1.length != data2.length) {
            throw new RuntimeException("❌ Row count mismatch between " + sheet1 + " and " + sheet2);
        }

        Object[] merged = new Object[data1.length];
        for (int i = 0; i < data1.length; i++) {
            Map<String, String> combined = new HashMap<>();
            combined.putAll((Map<String, String>) data1[i]); // add first sheet data
            combined.putAll((Map<String, String>) data2[i]); // add second sheet data
            merged[i] = combined;
        }
        return merged;
    }
}

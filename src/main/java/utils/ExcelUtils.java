package utils;

import constants.FrameworkConstants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {

    // Private constructor to prevent instantiation of utility class
    private ExcelUtils() {}

    /**
     * Reads data from the specified sheet in the Excel file.
     * Returns each row (excluding the header) as a Map<String, String>
     * where key = column header and value = cell value.
     *
     * @param sheetName the name of the sheet to read
     * @return an Object[] where each element is a Map<String, String> representing a row
     * @throws IOException if file reading fails
     */
    public static Object[] getData(String sheetName) throws IOException {
        // Load the Excel file
        FileInputStream fis = new FileInputStream(FrameworkConstants.getExcelFilePath());
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        // Get total number of data rows and columns
        int rowCount = sheet.getLastRowNum(); // ignores header row
        int colCount = sheet.getRow(0).getLastCellNum(); // includes all columns in header

        // Prepare an array of Objects (Maps) to store each data row
        Object[] data = new Object[rowCount];

        // Start from row 1 (skip row 0 — it's the header)
        for (int i = 1; i <= rowCount; i++) {
            Map<String, String> map = new HashMap<>();
            Row row = sheet.getRow(i);

            // For each column, map the header (row 0) to the value in this row
            for (int j = 0; j < colCount; j++) {
                String key = sheet.getRow(0).getCell(j).getStringCellValue(); // header
                Cell cell = row.getCell(j);
                String value = "";

                // Extract and convert cell value to String based on type
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            value = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            value = String.valueOf((int) cell.getNumericCellValue()); // cast to int (assumes no decimals/dates)
                            break;
                        case BOOLEAN:
                            value = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case BLANK:
                            value = "";
                            break;
                        default:
                            value = "";
                    }
                }

                // Store in map: header -> cell value
                map.put(key, value);
            }

            // Add row map to data array
            data[i - 1] = map;
        }

        // Clean up
        workbook.close();
        fis.close();

        return data;
    }
}
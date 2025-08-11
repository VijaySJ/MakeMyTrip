
==================================================================
MAKE MY TRIP – BUS BOOKING AUTOMATION PROJECT (Java + Selenium)
==================================================================

📌 OBJECTIVE:
Automate the **bus ticket booking flow** on https://www.makemytrip.com using **Selenium WebDriver**, following the **Page Object Model (POM)** design pattern with **TestNG**, **Extent Reports**, and **Data-Driven Testing**.

📂 PROJECT LOCATION:
C:\YourName\MakeMyTripBusBookingFramework\

==================================================================
✅ TOOLS & TECHNOLOGIES USED:
==================================================================
- Programming Language: Java
- Automation Tool: Selenium WebDriver
- Testing Framework: TestNG
- Design Pattern: Page Object Model (POM)
- Reporting: ExtentReports
- Data-Driven Testing: Apache POI with Excel
- Browser: Chrome (via WebDriverManager)
- Exception Handling: Try-catch, TestNG listeners
- Wait Mechanisms: Implicit and Explicit Waits
- IDE: Eclipse IDE (2023 or later)

==================================================================
📦 PROJECT STRUCTURE (src/main/java):
==================================================================

1. **constants/**
   - `FrameworkConstants.java`: Paths, timeouts, and report config

2. **dataProviders/**
   - `ExcelDataProvider.java`: Supplies test data using `@DataProvider`

3. **driver/**
   - `DriverManager.java`: Initializes and manages WebDriver instance

4. **enums/**
   - `BrowserType.java`: Enum for supported browsers (e.g., CHROME)
   - `ConfigProperties.java`: Enum keys for config.properties
   - `SeatType.java`: Enum for seat selection
   - `WaitStrategy.java`: Enum for wait strategy (CLICKABLE, VISIBLE, etc.)

5. **factories/**
   - `ExplicitWaitFactory.java`: Applies waits based on WaitStrategy

6. **listeners/**
   - `TestListener.java`: Implements ITestListener for Extent Reporting

7. **pages/**
   - `BasePage.java`: Common methods for all page classes
   - `BusBookingPage.java`: Handles origin/destination/date selection
   - `BusResultsPage.java`: Selects bus, time, seat
   - `CompleteBookingPage.java`: Enters passenger details
   - `PaymentPage.java`: Navigates to the payment page

8. **reports/**
   - `ExtentLogger.java`: Logs test steps to Extent Report
   - `ExtentReportManager.java`: Manages report lifecycle

9. **utils/**
   - `PropertyUtils.java`: Loads config from config.properties
   - `ExcelUtils.java`: Reads data from Excel
   - `ScreenshotUtils.java`: Captures screenshots on failure

==================================================================
📂 TEST STRUCTURE (src/test/java):
==================================================================

1. **tests/**
   - `BaseTest.java`: Initializes and quits the browser
   - `BusBookingTest.java`: Actual test steps using `@Test` and `@DataProvider`

==================================================================
📂 RESOURCES (src/test/resources):
==================================================================

1. **config/**
   - `config.properties`: Stores URL, browser type

2. **testdata/**
   - `BusBookingData.xlsx`: Excel sheet containing test input data

==================================================================
📃 OTHER FILES:
==================================================================
- **testng.xml**: Executes the suite and passes parameters (browser, etc.)
- **report.html**: Generated Extent Report after execution
- **notes.txt**: (Optional) Explanation/justification for logic

==================================================================
💡 TESTING FEATURES:
==================================================================
✔️ Bus booking flow automated
✔️ POM structure for scalable maintenance  
✔️ Data-driven testing from Excel  
✔️ Parameterization via TestNG XML  
✔️ ChromeDriver managed via WebDriverManager  
✔️ Assertions for validation  
✔️ Exception handling with logging  
✔️ Screenshots captured on failure  
✔️ Extent Reports integrated  

==================================================================
🚀 HOW TO RUN:
==================================================================

1. Open Eclipse IDE  
2. Import the project from:
   `C:\YourName\MakeMyTripBusBookingFramework\`

3. Right-click on the project > Run As > TestNG Suite  
   or  
   Run `testng.xml` directly from Eclipse

4. The Extent Report will be generated at:
   `/test-output/report.html`

==================================================================
📦 PACKAGING:
==================================================================

✅ All referenced JARs are located in `/lib` (if not using Maven)  
✅ ChromeDriver is managed using WebDriverManager (No manual .exe needed)  
✅ Screenshots and reports are stored automatically  
✅ Add this `README.txt` to your zip  
✅ Zip the full project folder:
   `MakeMyTripBusBookingFramework.zip`

==================================================================
Demo Run Link: https://jam.dev/c/f02eb3e5-1053-43f7-973e-de2138d7610e

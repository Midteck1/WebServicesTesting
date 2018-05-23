package API.testing.WebAppITesting;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestCaseBase {
	ExtentHtmlReporter htmlRep;
	ExtentReports extent;
	ExtentTest logger;
	
	
	@BeforeTest
	public void initialize() {
		System.out.println("Executing init....");
		System.out.println("Executing BeforeTest");
		System.out.println(System.getProperty("user.dir"));

		htmlRep = new ExtentHtmlReporter(System.getProperty("user.dir") + "//test-results//HS_API_TestReport.html");
		extent = new ExtentReports();

		extent.attachReporter(htmlRep);
		extent.setSystemInfo("Environment", "HubSpot API Automation Testing");
		extent.setSystemInfo("User Name", "Kishore");

		// Report Configuration
		htmlRep.config().setDocumentTitle("HubSpot API Testing with RestAssured");
		htmlRep.config().setReportName("Automation Test Report");
		htmlRep.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlRep.config().setTheme(Theme.STANDARD);
	}
	
	
	
	@AfterTest
	public void cleanup() {
		System.out.println("Executing cleanup....");
		extent.flush();
	}
	
	
}

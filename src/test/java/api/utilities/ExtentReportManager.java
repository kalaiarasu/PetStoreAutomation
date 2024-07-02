package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportManager implements ITestListener {
    private static ExtentReports extent;
    private static ExtentTest test;
    String reportname;
    @Override
    public void onStart(ITestContext context) {
    	String timestamp = new SimpleDateFormat("yyyy.MM,dd.HH.mm.ss").format(new Date());
    	reportname = "Test-Report -" +timestamp+".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(".\\reports\\"+reportname);
        sparkReporter.config().setDocumentTitle("API Test Report");
        sparkReporter.config().setReportName("API Testing");
        sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Tester");
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    // These methods can be overridden if needed
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) { }
}
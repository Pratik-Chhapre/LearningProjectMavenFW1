package com.genericUtils;

import com.pages.login.LoginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.genericUtils.BasePage.infoPrint;
import static com.genericUtils.AutoConstants.*;
public class BaseTest {
    public static ExtentReports extent;
    public static Properties prop;
    public static ExtentTest logger;
    public static WebDriver driver;
    public  static WebDriverWait wait;
    public static Actions builder;
    public static JavascriptExecutor js;
    public static  LoginPage loginPage;
    public static String executionReport;
    public static FileInputStream fileInput;
    public static String method="";
    public static List<String> fileCount = new ArrayList<>();
    public static int tnCount=0;
    public static int delay=2000;
    @BeforeTest
    public void preconditions() throws IOException {
        appOpen();
        executionReport="LearningFrameWork Report".concat("_"+BasePage.currentDateAndTimeWithSpecificFormat())+".html";
        extent = new ExtentReports(System.getProperty("user.dir") + "/Execution Status/Extent Reports/"+BasePage.currentDate()+"/"+executionReport, true);
        extent.addSystemInfo("Executed By ", prop.getProperty("userName"));
        extent.addSystemInfo("Environment", prop.getProperty("url"));
        extent.loadConfig(new File(System.getProperty("user.dir") + "/Execution Status/Extent Reports/"+BasePage.currentDate()+"/"+executionReport));
        driver.get(prop.getProperty("url"));
        loginPage = new LoginPage(driver);
    }
    @BeforeMethod(alwaysRun = true)
    public void verifyAppStatus(){
        try {
            if(driver.getCurrentUrl().equalsIgnoreCase("https://www.saucedemo.com/")){

            }
        } catch (Exception e){
            appOpen();
        }

    }
    public void appOpen(){
        System.setProperty(CHROME_DRIVER_KEY, CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory",System.getProperty("user.dir") +  File.separator +"src"+ File.separator +"test"+ File.separator +"resources"+ File.separator +"BrowserDownloadedFiles");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        //options.addArguments("--headless");
        options.addArguments("--window-size=1400,800");
        options.setExperimentalOption("prefs", prefs);
        // Instantiate the chrome driver

        try{ DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            options.merge(capabilities);
            capabilities.setPlatform(Platform.ANY);
            driver = new RemoteWebDriver(new URL(prop.getProperty("hubURL") ),capabilities);
            System.out.println("running in Grid");
        }
        catch (Exception e){
            options.setHeadless(false);
            System.out.println("Trying to run in browser");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().pageLoadTimeout( Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        wait=new WebDriverWait(driver,(Duration.ofSeconds(EXPLICIT_WAIT)));
        builder=new Actions(driver);
        js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='100%'");

    }
    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(ITestResult res) throws IOException {
        if (res.getStatus() == ITestResult.SUCCESS) {
            infoPrint( res.getTestClass().getName() + " is Passed ",1);
        } else if (res.getStatus() == ITestResult.FAILURE) {
            String name = res.getName();
            File Path = BasePage.captureScreenshot(driver, name + " " + BasePage.currentDateAndTime());
            String screenshotPath = Path.getPath();
//            screenshotPath = screenshotPath.replace(System.getProperty("user.dir")+"/Execution Status/ScreenShots/"+BasePage.currentDate()+"/", "");
            logger.log(LogStatus.FAIL, method);
            logger.log(LogStatus.FAIL, "" + res.getThrowable());
            String image = logger.addScreenCapture(screenshotPath);
            logger.log(LogStatus.FAIL, image);
            logger.log(LogStatus.FAIL, res.getTestClass().getName() + " is Failed ");
        } else if (res.getStatus() == ITestResult.SKIP) {
            logger.log(LogStatus.SKIP, res.getTestClass().getName() + " is Skipped");
        }
        extent.endTest(logger);
    }
    @BeforeSuite
    public void toTrashOldReport() throws IOException {
        prop=new Properties();
        String filepath1=System.getProperty("user.dir")+"/src/test/resources/config/project.properties";
        fileInput = new FileInputStream(filepath1);
        prop.load(fileInput);
        try {
            File fileToBeDeleted = new File((System.getProperty("user.dir") + "/Execution Status/CurrentExecutionReport"));
            FileUtils.cleanDirectory(fileToBeDeleted);
            File downloadFileToBeDeleted = new File((System.getProperty("user.dir") + "/src/test/resources/BrowserDownloadedFiles"));
            FileUtils.cleanDirectory(downloadFileToBeDeleted);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    @AfterSuite
    public  void fileTransferToDirectories() throws IOException {
        try {
            File executionResult = new File((System.getProperty("user.dir") + "/Execution Status/CurrentExecutionReport"));
            if (!executionResult.exists()) {
                if (executionResult.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        FileTransferToDirectories.selectDirectoriesPath();
        String path = System.getProperty("user.dir") + "/src/test/resources/config/project.properties";
        FileOutputStream fis = new FileOutputStream(path);
        prop.setProperty("oldReport", executionReport);
        prop.store(fis, "Test-Data");
        fis.close();

    }
    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
    @AfterTest
    public void postConditions() throws InterruptedException {
        extent.flush();
        extent.close();
        //appClose();
    }
    public void appClose() throws InterruptedException {
        /*Logout method should call*/
        Thread.sleep(10000);
        //loginPage.logOutSteps();
        driver.quit();
    }

}


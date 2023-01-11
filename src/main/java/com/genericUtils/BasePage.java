package com.genericUtils;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static com.genericUtils.AutoConstants.*;
import static org.testng.Assert.assertEquals;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * The BasePage class for some extension classes. It contains common utility methods
 * to be used by the sub-classes.
 *
 * @author Nandini
 */
public class BasePage extends BaseTest {
    static int j=0;
    /* captureScreenshot() return the screenshot of current page*/
    public static File captureScreenshot(WebDriver driver, String name) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) (driver);
        File src = ts.getScreenshotAs(OutputType.FILE);
        String screenShot = name + currentTime() + 100000000 + new Random().nextInt(900000000) + ".png";
        String path = System.getProperty("user.dir") + "/Execution Status/ScreenShots/"+BasePage.currentDate()+"/"+ screenShot;
        informationPrint("ScreenShot Name:" + screenShot);
        File dest = new File(path);
        FileUtils.copyFile(src, dest);
        return dest;
    }
    /* takeScreenShortAndFail() return the screenshot of Element Page and Fail Scenario*/
    public static void takeScreenShortAndFail( WebElement element) throws IOException {
        File Path = BasePage.captureScreenshot(driver,  "Element " + BasePage.currentDateAndTime());
        String screenshotPath = Path.getPath();
        logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
        logger.log(LogStatus.FAIL, element.toString()+" is not Expected ");
    }
    /* takeScreenShortAndPrintInfo() return the screenshot of Element Page and print info Scenario*/
    public static void takeScreenShortAndPrintInfo( WebElement element) throws IOException {
        File Path = BasePage.captureScreenshot(driver,  "Element " + BasePage.currentDateAndTime());
        String screenshotPath = Path.getPath();
        warnInfoPrint( logger.addScreenCapture(screenshotPath));
        warnInfoPrint( element.toString()+" is not accessible ");
    }
    /* informationPrint() used for information print*/
    public static void informationPrint(String info) {
        logger.log(LogStatus.INFO, info);
    }

    /* currentTime() will return only current time*/
    public static String currentTime() {
        DateFormat date = new SimpleDateFormat("MM_dd");
        Date currentDate = new Date();
        Date time = Calendar.getInstance().getTime();
        return date.format(currentDate).replace("_", "").concat(String.valueOf(time.getHours())).concat(String.valueOf(time.getMinutes()));
    }
    /* logTime() returns specific format for log*/
    public static String logTime() {
        DateFormat date = new SimpleDateFormat("MM:dd:yyyy");
        Date currentDate = new Date();
        return date.format(currentDate).concat(" "+String.valueOf(currentDate.getHours()).concat(":"+String.valueOf(currentDate.getMinutes()).concat(":"+String.valueOf(currentDate.getSeconds()))));
    }
    /* currentDateAndTimeWithSpecificFormat() returns specific format */
    public static String currentDateAndTimeWithSpecificFormat() {
        DateFormat date = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        Date currentDate = new Date();
        return date.format(currentDate).replaceAll(":",".");
    }
    /* currentDateAndTimeWithSpecificFormat() returns current date and Time */

    public static String currentDateAndTime() {
        DateFormat date = new SimpleDateFormat("MM_dd_yyyy");
        Date currentDate = new Date();
        return date.format(currentDate).concat(String.valueOf(date.getCalendar().getInstance().getTime())).replace(" ", "").replace(":", "");
    }
    /* currentDateAndTimeWithSpecificFormat() returns current date */
    public static String currentDate() {
        DateFormat date = new SimpleDateFormat("MM_dd_yyyy");
        Date currentDate = new Date();
        return date.format(currentDate);
    }
    public static String previousDate() {
        DateFormat date = new SimpleDateFormat("MM_dd_yyyy");
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -1);
        Date modifiedDate = cal.getTime();
        return date.format(modifiedDate);
    }
    /* getTitle() returns current page title */
    public static void warnInfoPrint(String value)  {
        logger.log(LogStatus.WARNING,value);
        try {
            File Path  = BasePage.captureScreenshot(driver, "Element " + BasePage.currentDateAndTime());
            String screenshotPath = Path.getPath();
            informationPrint(logger.addScreenCapture(screenshotPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(logTime()+" : "+value);
    }
    public static void infoPrint(String message, int i){
        String temp;
        temp=method;
        method=message;
        if(i!=0){
            logger.log(LogStatus.PASS,temp);
        }
        System.out.println(logTime()+" : "+temp);
    }
    public static void implicitWait(int i){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(i));

    }
    /**
     * pageRefresh() refresh the page
     */
    public static void pageRefresh() {
        try {
            driver.navigate().refresh();
            waitForPageLoad();
        }
        catch (Exception exception){}
    }
    /**
     * navigateToBackPage() navigate to  the back page
     */
    public static void navigateToBackPage() {
        driver.navigate().back();
        waitForPageLoad();
    }
    public static void waitForPageLoad() {
//        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver driver) {
//                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//            }
//        };
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_WAIT);
//            wait.until(expectation);
//        } catch (Throwable error) {
//            error.printStackTrace();
//        }
        wait = new WebDriverWait(driver, EXPLICIT_WAIT);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return js.executeScript("return document.readyState").equals("complete");
            }
        });
        new Actions(driver).pause(delay).build().perform();
    }
    public static void zoomInPageAndTakeScreenShort(WebElement element) throws IOException {
        takeScreenShortAndPrintInfo(element);
    }
    public static void elementClick(WebElement element) throws IOException {
        if(getElement(element)){
            scrollTillElement(element);
            builder.moveToElement(element).click(element).build().perform();
        }
        else {
            zoomInPageAndTakeScreenShort(element);
        }
        waitForPageLoad();
    }
    public static boolean  getElement(WebElement element){
        boolean result;
        try {
            scrollTillElement(element);
            wait.until(ExpectedConditions.visibilityOf(element));
            result = true;
        } catch (Exception exception) {
            informationPrint(String.valueOf(exception));
            result = false;
        }
        return result;
    }
    /**
     * scrollTillElement() receives
     * @param element and value, The element is WebElement type and value is String type.
     *This will check the element is available in the UI or not, If present scroll till the Element and then enter the value into text area
     */
    public static void scrollTillElement(WebElement element){
        waitForPageLoad();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    /**
     * givenList_shouldReturnARandomElement() receives
     * @param nameEndsWith  is String type and  it returns random new Name .
     *This  will receive name which needs to be enter the UI and concat with some random number and returns
     */
    public static String randomName(String nameEndsWith){
        Random rand = new Random();
        return nameEndsWith.concat(String.valueOf(rand.nextInt(1000)));
    }
    /**
     * randomAccountNumberGeneration() returns random new 10 digit account number .
     */
    public static String randomAccountNumberGeneration(){
        Random random = new Random();
        return String.format("%011d", random.nextInt(1000000000));
    }
}
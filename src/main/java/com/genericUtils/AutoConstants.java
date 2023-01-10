package com.genericUtils;
/**
 * The AutoConstants contains common Static values
 * to be used by the sub-classes.
 *
 * @author Nandini
 */
public class AutoConstants {
    public static final long PAGE_LOAD_TIMEOUT = 300;
    public static final long  IMPLICIT_WAIT = 40;
    public static final long  EXPLICIT_WAIT = 40;
    public static final String CHROME_DRIVER_PATH=System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe";
    public static final String CHROME_DRIVER_KEY="webdriver.chrome.driver";
    public static final  String DATE_FORMAT="MM/dd/yyyy";

}

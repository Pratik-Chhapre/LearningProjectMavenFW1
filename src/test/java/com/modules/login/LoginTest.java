package com.modules.login;

import com.genericUtils.BasePage;
import com.genericUtils.BaseTest;
import com.pages.login.LoginPage;
import org.testng.annotations.Test;
import java.io.IOException;

import static com.genericUtils.BasePage.infoPrint;

public class LoginTest extends BaseTest {
    @Test(priority = 1)
    public void testLogin() throws IOException {
        logger = extent.startTest("Test Login");
        //infoPrint( res.getTestClass().getName() + " is Passed ",1);
        infoPrint("Launching the Browser worked fine",1);
        LoginPage loginPage1 = new LoginPage(driver);
        infoPrint("-------------------------------",1);

    }
}

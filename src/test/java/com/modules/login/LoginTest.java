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
        infoPrint("Launching the Browser worked fine",0);
        LoginPage loginPage1 = new LoginPage(driver);
        loginPage1.enterUsername();
        infoPrint("Entered the username",1);
        loginPage1.enterPassword();
        infoPrint("Entered the password",1);
        loginPage1.clickLoginButton();
        infoPrint("Click on Login button ",1);
        infoPrint("-------------------------------",1);

    }
}

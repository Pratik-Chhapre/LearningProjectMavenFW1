package com.modules.login;

import com.genericUtils.BaseTest;
import com.pages.login.LoginPage;
import com.pages.login.orderSelectionPage;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.genericUtils.BasePage.infoPrint;

public class orderSelectionTest extends BaseTest {
    @Test(priority = 1)
    public void testLogin() throws IOException {
        logger = extent.startTest("Order Selection task");
        infoPrint("Launching the Browser worked fine",0);
        LoginPage loginPage1 = new LoginPage(driver);
        orderSelectionPage order = new orderSelectionPage(driver);
        order.selectTshirt();
        infoPrint("Selected an item into the cart",1);
        order.clickOnCart();
        infoPrint("Click on Cart",1);
        order.clickOnCheckout();
        infoPrint("Click on Checkout button",1);
        order.enterDetails();
        infoPrint("Enter First Name, Last Name and Zip Code",1);
        order.captureTotalAmt();
        infoPrint("Order is Placed",1);



        infoPrint("-------------------------------",1);

    }
}

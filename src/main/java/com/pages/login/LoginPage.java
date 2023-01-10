package com.pages.login;

import com.genericUtils.BasePage;
//import com.pages.orders.dashboard.PortInPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static com.genericUtils.BasePage.*;
import static com.genericUtils.BaseTest.builder;
import static com.genericUtils.BaseTest.js;
/**
 * The LoginPage has LoginPage  webelement's ,methode to perform the actions, methode to collect the  results and methode to Verify the results
 * @author Nandini
 */
public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

    }

}
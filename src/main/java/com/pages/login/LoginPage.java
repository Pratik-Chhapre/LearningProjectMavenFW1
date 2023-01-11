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

public class LoginPage {

    @FindBy(xpath = "//input[@id='user-name']")
    private WebElement username;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement password;
    @FindBy(xpath = "//input[@id='login-button']")
    private WebElement loginButton;
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(){
        username.sendKeys(prop.getProperty("urnm"));
    }
    public void enterPassword(){
        password.sendKeys(prop.getProperty("pass"));
    }
    public void clickLoginButton(){
        loginButton.click();
    }
}
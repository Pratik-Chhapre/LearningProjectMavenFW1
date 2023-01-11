package com.pages.login;

import com.genericUtils.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class orderSelectionPage extends BasePage {

    @FindBy(id = "add-to-cart-sauce-labs-bolt-t-shirt")
    private WebElement selectOrder;
    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    private WebElement clickOnCart;
    @FindBy(id = "checkout")
    private WebElement clickCheckout;
    @FindBy(id = "first-name")
    private WebElement enterFirstName;
    @FindBy(id = "last-name")
    private WebElement enterLastName;
    @FindBy(id = "postal-code")
    private WebElement enterZipCode;

    @FindBy(id = "continue")
    private WebElement clickOnContinue;
    @FindBy(xpath = "//div[@class='summary_total_label']")
    private WebElement captureTotalAmount;

    @FindBy(id = "finish")
    private WebElement clickFinish;

    public orderSelectionPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void selectTshirt() {
        selectOrder.click();
    }

    public void clickOnCart() {
        clickOnCart.click();
    }

    public void clickOnCheckout() {
        clickCheckout.click();
    }
    public void enterDetails(){
        enterFirstName.sendKeys(BasePage.randomName("FirstName"));
        enterLastName.sendKeys(BasePage.randomName("LastName"));
        enterZipCode.sendKeys(BasePage.randomAccountNumberGeneration());
        clickOnContinue.click();
    }
    public void captureTotalAmt(){
        String str = captureTotalAmount.getText();
        infoPrint("Total Amount is "+str,1);
        clickFinish.click();
    }

}

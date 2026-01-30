package org.example.web.modules.authorize;

import org.example.web.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class cardDetailsInput extends paymentsHelper {
    public void cardDetailsInput(String cardNumber){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("cc_exp_month")));
        driver.findElement(By.name("cc_number")).sendKeys(cardNumber);
        WebElement monthDropDown = driver.findElement(By.name("cc_exp_month"));
        Select expiryMonth = new Select(monthDropDown);
        expiryMonth.selectByValue(readProperty("expiryMonth"));
        WebElement yearDropDown = driver.findElement(By.name("cc_exp_year"));
        Select expiryYear = new Select(yearDropDown);
        expiryYear.selectByValue(readProperty("expiryYear"));
        driver.findElement(By.name("cc_cvv")).sendKeys(readProperty("cvv"));
    }
}

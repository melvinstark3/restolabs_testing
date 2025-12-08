package org.example.modules.checkoutfi;

import org.example.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class cardDetailsInput extends paymentsHelper {
    public void cardDetailsInput(String cardNumber) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cardNumber")));;
        WebElement cardNumberField = driver.findElement(By.id("cardNumber"));
        js.executeScript("arguments[0].value='"+cardNumber+"';", cardNumberField);
        WebElement cardExpiry = driver.findElement(By.id("expiry"));
        js.executeScript("arguments[0].value='"+readProperty("expiry")+"';", cardExpiry);
        driver.findElement(By.id("cvv")).sendKeys(readProperty("cvv"));
    }
}
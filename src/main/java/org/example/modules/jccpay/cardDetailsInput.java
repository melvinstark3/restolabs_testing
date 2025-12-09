package org.example.modules.jccpay;

import org.example.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class cardDetailsInput extends paymentsHelper {
    public void cardDetailsInput(String cardNumber){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("pan")));
        driver.findElement(By.name("pan")).sendKeys(cardNumber);
        driver.findElement(By.name("cardholderName")).sendKeys(readProperty("cardHolderName"));
        driver.findElement(By.name("expiry")).sendKeys(readProperty("expiry"));
        driver.findElement(By.name("newCardCvc")).sendKeys(readProperty("cvv"));
    }
}

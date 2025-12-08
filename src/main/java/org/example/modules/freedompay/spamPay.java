package org.example.modules.freedompay;

import org.example.commonUtils.payment.paymentsHelper;
import org.example.core.browserSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class spamPay extends org.example.commonUtils.payment.spamPay {
    public void spamPay() throws InterruptedException {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Warning: Laravel Page is Not Displayed!");
        }
        paymentsHelper gateway = getModule.currentModuleClass("cardDetailsInput",paymentsHelper.class);
        gateway.cardDetailsInput(readProperty("guestNewCardNumber"));
        Thread.sleep(2000);
        driver.findElement(By.id("btnPay")).click();
        wait = new WebDriverWait(driver, 20);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-button"))).click();
            System.out.println("TC 32: FAIL: WARNING! Multiple Pay Clicks Intercepted. Check Case Manually.");
        } catch (TimeoutException | ElementNotInteractableException e) {
            // If button is NOT clickable due to loader overlay: PASS
            System.out.println("TC 32: PASS: Multiple Pay Clicks are not Allowed");
        }
    }
}

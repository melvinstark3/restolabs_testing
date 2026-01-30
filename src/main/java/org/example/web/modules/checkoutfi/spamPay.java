package org.example.web.modules.checkoutfi;

import org.example.web.commonUtils.payment.newCardPayment;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class spamPay extends org.example.web.commonUtils.payment.spamPay{
    public void spamPay() throws InterruptedException {
        newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.web.commonUtils.payment.newCardPayment.class);
        newCardPayment.newCardPayment(readProperty("guestNewCardNumber"),false);
        wait = new WebDriverWait(driver, 20);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
            System.out.println("TC 32: FAIL: WARNING! Multiple Pay Clicks Intercepted. Check Case Manually.");
        } catch (TimeoutException | ElementNotInteractableException e) {
            // If button is NOT clickable due to loader overlay: PASS
            System.out.println("TC 32: PASS: Multiple Pay Clicks are not Allowed");
        }
    }
}

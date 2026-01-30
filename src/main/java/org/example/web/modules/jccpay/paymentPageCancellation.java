package org.example.web.modules.jccpay;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class paymentPageCancellation extends org.example.web.commonUtils.payment.paymentPageCancellation{

    public void paymentPageCancellation() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label=\"Payment amount\"]")));
        driver.navigate().back();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-header"))).isDisplayed();
            System.out.println("TC 3: PASS: After Payment Cancellation User is redirected to Menu");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC 3: FAIL: After Payment Cancellation User is not redirected to Menu");
        }
        System.out.println("Cancellation of Payment takes User to the URL: " + driver.getCurrentUrl());
    }

}

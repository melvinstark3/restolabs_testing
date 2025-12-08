package org.example.modules.worldpayexp;

import org.example.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class gatewayPageCancellation extends paymentsHelper {

    public void gatewayPageCancellation() {
        System.out.println("Attempting Cancellation on WorldPay Gateway Page");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_mainPage_btn_Cancel")));
        driver.findElement(By.id("ctl00_mainPage_btn_Cancel")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-header"))).isDisplayed();
            System.out.println("User is redirected to Menu");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("User is not redirected to Menu");
        }
        System.out.println("Cancellation of Payment takes User to the URL: " + driver.getCurrentUrl());
    }
}

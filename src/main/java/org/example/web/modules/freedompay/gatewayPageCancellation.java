package org.example.web.modules.freedompay;

import org.example.web.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class gatewayPageCancellation extends paymentsHelper {

    public void gatewayPageCancellation() {
        System.out.println("Attempting Cancellation on FreedomPay Gateway Page");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hpc--cardonfile-frame")));
            driver.findElement(By.id("hpc--cardonfile-frame")).click();
            WebElement gatewayIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("hpc--cardonfile-frame")
            ));
            driver.switchTo().frame(gatewayIframe);
        } catch (NoSuchElementException | TimeoutException e){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hpc--card-frame")));
            driver.findElement(By.id("hpc--card-frame")).click();
            WebElement gatewayIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("hpc--card-frame")
            ));
            driver.switchTo().frame(gatewayIframe);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SecurityCode")));
        driver.switchTo().defaultContent();
        driver.navigate().back();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-header"))).isDisplayed();
            System.out.println("User is redirected to Menu");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("User is not redirected to Menu");
        }
        System.out.println("Cancellation of Payment takes User to the URL: " + driver.getCurrentUrl());
    }
}

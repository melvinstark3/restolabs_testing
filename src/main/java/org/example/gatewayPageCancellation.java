package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class gatewayPageCancellation extends browserSetup{

    public gatewayPageCancellation() throws InterruptedException {
        System.out.println("Attempting Cancellation on FreedomPay Gateway Page");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hpc--card-frame")));
        driver.findElement(By.id("hpc--card-frame")).click();
        WebElement gatewayIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("hpc--card-frame")
        ));
        driver.switchTo().frame(gatewayIframe);
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

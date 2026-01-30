package org.example.web.modules.checkoutfi;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class paymentPageCancellation extends org.example.web.commonUtils.payment.paymentPageCancellation{

    public void paymentPageCancellation() throws InterruptedException {
        wait = new WebDriverWait(driver, 15);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try{
            System.out.println("Page with Pay Now & Later Options is Displayed");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class=\"css-12ujjj0\"]"))).click();
        } catch (NoSuchElementException | TimeoutException e) {
            js.executeScript("window.scrollBy(0,2000)", "");
            System.out.println("Page with Direct Payment Options is Displayed");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Visa\"]"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cancel-link"))).click();
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-header"))).isDisplayed();
            System.out.println("TC 3: PASS: After Payment Cancellation User is redirected to Menu");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC 3: FAIL: After Payment Cancellation User is not redirected to Menu");
        }
        System.out.println("Cancellation of Payment takes User to the URL: " + driver.getCurrentUrl());
        wait = new WebDriverWait(driver, 30);
    }

}

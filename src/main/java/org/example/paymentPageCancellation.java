package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class paymentPageCancellation extends browserSetup{

    public paymentPageCancellation() throws InterruptedException {
        wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean paynowButton = false;
        try{
            paynowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-tab=\"tab-now\"]"))).isDisplayed();
            System.out.println("Page with Pay Now & Later Options is Displayed");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Page with Direct Payment Options is Displayed");
        }
        js.executeScript("window.scrollBy(0,2000)", "");
        if(paynowButton){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class=\"css-12ujjj0\"]"))).click();
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class=\"css-1yedui6\"]"))).click();
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

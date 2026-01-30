package org.example.web.commonUtils.payment;

import org.example.core.browserSetup;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;

public class paymentPageCancellation extends browserSetup{

    public void paymentPageCancellation() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            System.out.println("Trying to Delete any Pre-Saved Cards if Found!");
            deleteCard deleteCardAction = getModule.currentModuleClass("deleteCard", deleteCard.class);
            deleteCardAction.deleteCard();
            Thread.sleep(5000);
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("No Pre-Saved Cards were found! Proceeding Ahead!");
        }
        
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("back-button")));
            driver.findElement(By.id("back-button")).click();
        } catch (ElementClickInterceptedException e){
            js.executeScript("arguments[0].click();", driver.findElement(By.id("back-button")));
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-header"))).isDisplayed();
            System.out.println("TC 3: PASS: After Payment Cancellation User is redirected to Menu");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC 3: FAIL: After Payment Cancellation User is not redirected to Menu");
        }
        System.out.println("Cancellation of Payment takes User to the URL: " + driver.getCurrentUrl());
    }

}

package org.example.modules.jccpay;

import org.example.commonUtils.payment.newCardPayment;
import org.example.commonUtils.payment.savedCardPayment;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class checkSavedOrNew extends org.example.commonUtils.payment.checkSavedOrNew{

    public void checkSavedOrNew(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label=\"Payment amount\"]")));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,2000)", "");;
            System.out.println("Checking Saved Cards");
            WebElement savedCard = driver.findElement(By.xpath("//button[@aria-label=\"Select saved card\"]"));
            if (!savedCard.isDisplayed()) {
                System.out.println("Saved Card Found! Proceeding with Saved Card Payment");
                savedCardPayment savedCardPayment = getModule.currentModuleClass("savedCardPayment",org.example.commonUtils.payment.savedCardPayment.class);
                savedCardPayment.savedCardPayment();
            } else {
                newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.commonUtils.payment.newCardPayment.class);
                newCardPayment.newCardPayment(cardNumber,loggedIn);
                System.out.println("NO Saved Card was Found! Proceeding with New Card Payment");
            }
            //Wait for Order ID element
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));

        } catch (NoSuchElementException | TimeoutException e) {
            try {
                wait = new WebDriverWait(driver, 2);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Select new card\"]")));
                driver.findElement(By.xpath("//button[@aria-label=\"Select new card\"]")).click();
            } catch (NoSuchElementException | TimeoutException t) {
                System.out.println("Trying First Fresh Card Payment");
            }
            wait = new WebDriverWait(driver, 30);
            newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.commonUtils.payment.newCardPayment.class);
            newCardPayment.newCardPayment(cardNumber,loggedIn);
        }
    }
}

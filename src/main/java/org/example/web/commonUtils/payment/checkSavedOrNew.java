package org.example.web.commonUtils.payment;

import org.example.core.browserSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class checkSavedOrNew extends browserSetup{
    public void checkSavedOrNew(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-button")));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,2000)", "");;
            System.out.println("Checking Saved Cards");
            List<WebElement> elements = driver.findElements(By.id("new-card"));
            if (!elements.isEmpty()) {
                savedCardPayment savedCardPayment = getModule.currentModuleClass("savedCardPayment",org.example.web.commonUtils.payment.savedCardPayment.class);
                savedCardPayment.savedCardPayment();
            } else {
                newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.web.commonUtils.payment.newCardPayment.class);
                newCardPayment.newCardPayment(cardNumber,loggedIn);
            }
            //Wait for Order ID element
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));

        } catch (NoSuchElementException | TimeoutException e) {
            try {
                wait = new WebDriverWait(driver, 2);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[normalize-space()='Add New Card']")));
                driver.findElement(By.xpath("//label[normalize-space()='Add New Card']")).click();
            } catch (NoSuchElementException | TimeoutException t) {
                System.out.println("Trying First Fresh Card Payment");
            }
            wait = new WebDriverWait(driver, 30);
            newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.web.commonUtils.payment.newCardPayment.class);
            newCardPayment.newCardPayment(cardNumber,loggedIn);
        }
    }
}

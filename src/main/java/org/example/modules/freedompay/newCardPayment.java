package org.example.modules.freedompay;

import org.example.commonUtils.payment.defaultSaveCardCheckbox;
import org.example.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class newCardPayment extends org.example.commonUtils.payment.newCardPayment {

    public void newCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
            if (loggedIn) {
                new defaultSaveCardCheckbox();
            }
            driver.navigate().refresh();
            System.out.println("Laravel Page Refreshed!");
            driver.findElement(By.id("submit-button")).click();
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Warning: Laravel Page is Not Displayed!");
        }
        paymentsHelper gateway = getModule.currentModuleClass("cardDetailsInput",paymentsHelper.class);
        gateway.cardDetailsInput(cardNumber);
        driver.findElement(By.id("btnPay")).click();
        System.out.println("Proceeding Payment with New Card");
        wait = new WebDriverWait(driver, 30);
    }
}

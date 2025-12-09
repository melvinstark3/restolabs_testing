package org.example.modules.worldpayexp;

import org.example.commonUtils.payment.defaultSaveCardCheckbox;
import org.example.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class newCardPayment extends org.example.commonUtils.payment.newCardPayment{

    public void newCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException{
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
            if (loggedIn) {
                defaultSaveCardCheckbox defaultSaveCardCheckbox = getModule.currentModuleClass("defaultSaveCardCheckbox",org.example.commonUtils.payment.defaultSaveCardCheckbox.class);
                defaultSaveCardCheckbox.defaultSaveCardCheckbox();
            }
            driver.navigate().refresh();
            driver.findElement(By.id("submit-button")).click();
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Warning: Laravel Page is Not Displayed!");
        }
        paymentsHelper gateway = getModule.currentModuleClass("cardDetailsInput",paymentsHelper.class);
        gateway.cardDetailsInput(cardNumber);
        driver.findElement(By.id("btn_Submit")).click();
        System.out.println("Proceeding Payment with New Card");
        wait = new WebDriverWait(driver, 30);
    }
}

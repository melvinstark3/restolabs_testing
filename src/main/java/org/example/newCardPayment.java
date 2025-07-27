package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class newCardPayment extends browserSetup{

    public newCardPayment(String cardNumber){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
            new defaultSaveCardCheckbox();
            driver.navigate().refresh();
            driver.findElement(By.id("submit-button")).click();
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Warning: Laravel Page is Not Displayed!");
        }
        new cardDetailsInput(cardNumber);
        driver.findElement(By.id("btn_Submit")).click();
        System.out.println("Proceeding Payment with New Card");
        wait = new WebDriverWait(driver, 30);
    }
}

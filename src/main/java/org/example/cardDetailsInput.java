package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class cardDetailsInput extends browserSetup{
    public cardDetailsInput(String cardNumber){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cardNumber")));;
        WebElement cardNumberField = driver.findElement(By.id("cardNumber"));
        js.executeScript("arguments[0].value='"+cardNumber+"';", cardNumberField);
        WebElement cardExpiry = driver.findElement(By.id("expiry"));
        js.executeScript("arguments[0].value='"+readProperty("expiry")+"';", cardExpiry);
        driver.findElement(By.id("cvv")).sendKeys(readProperty("cvv"));
    }

}

package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class cardDetailsInput extends browserSetup{
    public cardDetailsInput(String cardNumber){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CARD_NUMBER_ID")));
        driver.findElement(By.id("CARD_NUMBER_ID")).click();
        WebElement cardNumberIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CARD_NUMBER_ID")));
        driver.switchTo().frame(cardNumberIframe);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cardNumber"))).sendKeys(cardNumber);
        driver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CARD_DATE_ID")));
        driver.findElement(By.id("CARD_DATE_ID")).click();
        WebElement cardDateIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CARD_DATE_ID")));
        driver.switchTo().frame(cardDateIframe);
        driver.findElement(By.id("date")).sendKeys(readProperty("expiry"));
        driver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CARD_CVV_ID")));
        driver.findElement(By.id("CARD_CVV_ID")).click();
        WebElement cardCvvIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CARD_CVV_ID")));
        driver.switchTo().frame(cardCvvIframe);
        driver.findElement(By.name("cvc")).sendKeys(readProperty("cvv"));
        driver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CARD_POSTAL_CODE_ID")));
        driver.findElement(By.id("CARD_POSTAL_CODE_ID")).click();
        WebElement cardPostalIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CARD_POSTAL_CODE_ID")));
        driver.switchTo().frame(cardPostalIframe);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("postal"))).sendKeys(readProperty("postalCode"));
        driver.switchTo().defaultContent();
    }

}

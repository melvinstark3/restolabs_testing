package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class cardDetailsInput extends browserSetup{
    public cardDetailsInput(String cardNumber) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hpc--card-frame")));
        driver.findElement(By.id("hpc--card-frame")).click();
        WebElement gatewayIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("hpc--card-frame")
        ));
        driver.switchTo().frame(gatewayIframe);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SecurityCode")));
        driver.switchTo().defaultContent();
        System.out.println("Entering Card Details on Gateway Page");
        driver.findElement(By.id("inputNameOnCard")).sendKeys("Test User Name");
        driver.switchTo().frame(gatewayIframe);
        driver.findElement(By.id("CardNumber")).sendKeys(cardNumber);
        driver.findElement(By.id("ExpirationDate")).sendKeys(readProperty("expiry"));
        driver.findElement(By.id("SecurityCode")).click();
        driver.findElement(By.id("SecurityCode")).sendKeys(readProperty("cvv"));
        driver.findElement(By.id("SecurityCode")).click();
        actions.sendKeys(Keys.TAB).perform();
        driver.switchTo().defaultContent();
        actions.sendKeys(Keys.SPACE).perform();
        Thread.sleep(2000);
    }
}

package org.example.web.modules.square;

import org.example.web.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class cardDetailsInput extends paymentsHelper {
    public void cardDetailsInput(String cardNumber){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@class=\"sq-card-component\"]"))).click();
        WebElement squareIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//iframe[@class=\"sq-card-component\"]")
        ));
        driver.switchTo().frame(squareIframe);
        driver.findElement(By.id("cardNumber")).sendKeys(cardNumber);
        driver.findElement(By.id("expirationDate")).sendKeys(readProperty("expiry"));
        driver.findElement(By.id("cvv")).sendKeys(readProperty("cvv"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postalCode"))).sendKeys(readProperty("postalCode"));
        driver.switchTo().defaultContent();
    }

}

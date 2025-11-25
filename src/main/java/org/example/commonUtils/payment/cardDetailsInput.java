package org.example.commonUtils.payment;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class cardDetailsInput extends browserSetup {

    public static void authEnterCardDetails(String cardNumber){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("cc_exp_month")));
        driver.findElement(By.name("cc_number")).sendKeys(cardNumber);
        WebElement monthDropDown = driver.findElement(By.name("cc_exp_month"));
        Select expiryMonth = new Select(monthDropDown);
        expiryMonth.selectByValue(readProperty("expiryMonth"));
        WebElement yearDropDown = driver.findElement(By.name("cc_exp_year"));
        Select expiryYear = new Select(yearDropDown);
        expiryYear.selectByValue(readProperty("expiryYear"));
        driver.findElement(By.name("cc_cvv")).sendKeys(readProperty("cvv"));
    }

    public static void stripeEnterCardDetails(String cardNumber){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@title='Secure card payment input frame']")));
        driver.findElement(By.xpath("//iframe[@title='Secure card payment input frame']")).click();
        WebElement stripeIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//iframe[contains(@name, '__privateStripeFrame')]")
        ));
        driver.switchTo().frame(stripeIframe);
        driver.findElement(By.xpath("//div[@class=\"CardNumberField CardNumberField--ltr\"]")).sendKeys(cardNumber);
        driver.findElement(By.name("cardnumber")).sendKeys(cardNumber);
        driver.findElement(By.name("exp-date")).sendKeys(readProperty("expiry"));
        driver.findElement(By.name("cvc")).sendKeys(readProperty("cvv"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("postal"))).sendKeys(readProperty("postalCode"));
        driver.switchTo().defaultContent();
    }
}

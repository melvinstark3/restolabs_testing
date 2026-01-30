package org.example.web.modules.freedompay;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class savedCardPayment extends org.example.web.commonUtils.payment.savedCardPayment{
    public void savedCardPayment() throws InterruptedException {
        String SavedCardPaymentOrderID = driver.findElement(By.xpath("//h4[@class=\"payment__for__id\"]")).getText();
        System.out.println("Attempting Payment for Order ID " + SavedCardPaymentOrderID);
        String maskedCardNumber = driver.findElement(By.xpath("//p[@class=\"card__number\"]")).getText();
        String cardEndingNumber = maskedCardNumber.substring(maskedCardNumber.length() - 4);
        System.out.println("Saved Card Details: Card Number Ends with : " + cardEndingNumber);
        String cardSubDetails = driver.findElement(By.xpath("//p[@class=\"expiry__date\"]")).getText();
        System.out.println("Extracted date/Card Type : " + cardSubDetails);

        //As a saved card is already selected by Default, we are just directly clicking Pay button
        driver.findElement(By.id("submit-button")).click();
        System.out.println("Attempting Payment with Saved Card");

        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hpc--cardonfile-frame")));
        System.out.println("Gateway Page for Card Payment is Displayed!");
        driver.findElement(By.id("hpc--cardonfile-frame")).click();
        WebElement cvvIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("hpc--cardonfile-frame")
        ));
        driver.switchTo().frame(cvvIframe);
        driver.findElement(By.id("SecurityCode")).click();
        driver.findElement(By.id("SecurityCode")).sendKeys(readProperty("cvv"));
        driver.findElement(By.id("SecurityCode")).click();
        actions.sendKeys(Keys.TAB).perform();
        driver.switchTo().defaultContent();
        actions.sendKeys(Keys.SPACE).perform();
        Thread.sleep(2000);
        driver.findElement(By.id("btnPay")).click();
    }
}

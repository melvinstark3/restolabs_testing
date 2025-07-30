package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class spamPay extends browserSetup{
    public spamPay(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label=\"Payment amount\"]")));
        new cardDetailsInput(readProperty("guestNewCardNumber"));
        driver.findElement(By.xpath("//div[@class=\"Button_content__e67jX\"]"));
        wait = new WebDriverWait(driver, 20);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"Button_content__e67jX\"]"))).click();
            System.out.println("TC 32: FAIL: WARNING! Multiple Pay Clicks Intercepted. Check Case Manually.");
        } catch (TimeoutException | ElementNotInteractableException e) {
            // If button is NOT clickable due to loader overlay: PASS
            System.out.println("TC 32: PASS: Multiple Pay Clicks are not Allowed");
        }
    }
}

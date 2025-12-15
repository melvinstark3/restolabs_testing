package org.example.modules.basic;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class pickupOrder extends browserSetup {
    public pickupOrder() throws InterruptedException {
        new guestOrder("Pick Up");
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("Case 9: PASS: Pick Up Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Case 9: FAIL: Pick Up Order Failed");
        }
    }
}

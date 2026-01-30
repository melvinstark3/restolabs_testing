package org.example.web.modules.restoclient;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class dineInOrder extends browserSetup {
    public dineInOrder() throws InterruptedException {
        String orderMode = "Dine In";
        String dineInOrderTime = "later";
        new loginOrder(orderMode, readProperty("dineInPickupLocation"),dineInOrderTime);
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("Case 7: PASS: Logged In Dine In Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Case 7: FAIL: Logged In Dine In Order Failed");
        }
    }
}

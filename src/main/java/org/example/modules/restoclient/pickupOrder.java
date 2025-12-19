package org.example.modules.restoclient;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class pickupOrder extends browserSetup {
    public pickupOrder() throws InterruptedException {
        String orderMode = "Pick Up";
        String pickupOrderTime = "asap";
        new guestOrder(orderMode, readProperty("guestPickupLocation"), pickupOrderTime);
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("PASS: Guest Pick Up Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("FAIL: Guest Pick Up Order Failed");
        }
        new loginOrder(orderMode, readProperty("loginPickupLocation"), pickupOrderTime);
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("PASS: Logged In Pick Up Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("FAIL: Logged In Pick Up Order Failed");
        }
    }
}

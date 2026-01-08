package org.example.modules.restoclient;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class homeDeliveryOrder extends browserSetup {
    public homeDeliveryOrder() throws InterruptedException {
        String orderMode = "Home Delivery";
        String homeDeliveryOrderTime = "later";
        new guestOrder(orderMode, readProperty("guestDeliveryLocation"), homeDeliveryOrderTime);
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("PASS: Guest Home Delivery Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("FAIL: Guest Home Delivery Order Failed");
        }
        new loginOrder(orderMode, readProperty("loginDeliveryLocation"),homeDeliveryOrderTime);
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("PASS: Login Home Delivery Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("FAIL: Login Home Delivery Order Failed");
        }
    }
}

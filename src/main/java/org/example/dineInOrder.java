package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class dineInOrder extends browserSetup{
    public dineInOrder() throws InterruptedException {
        new loginOrder("Dine In");
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("Case 7: PASS: Dine In Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Case 7: FAIL: Dine In Order Failed");
        }
    }
}

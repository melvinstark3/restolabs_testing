package org.example.web.modules.basic;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class comboOrder extends browserSetup {
    public comboOrder() throws InterruptedException {
        new loginOrder("comboOrder");
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("Case 28: PASS: Combo Item Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Case 28: FAIL: Combo Item Order Failed");
        }
    }
}

package org.example.web.commonUtils.payment;

import org.example.core.browserSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class gatewayNavigation extends browserSetup{

    public void gatewayNavigation(boolean loggedIn) throws InterruptedException {
        paymentNavigation paymentNavigation = getModule.currentModuleClass("paymentNavigation", org.example.web.commonUtils.payment.paymentNavigation.class);
        paymentNavigation.paymentNavigation(loggedIn);
        try {
            List<WebElement> elements = driver.findElements(By.id("new-card"));
            if (!elements.isEmpty()) {
                driver.findElement(By.xpath("//label[@class=\"saved__payment__card add_new_card_btn\"]")).click();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            driver.findElement(By.id("submit-button")).click();
        } finally {
            driver.findElement(By.id("submit-button")).click();
        }
        System.out.println("Checking Hypertext Protocol for Gateway Page");
        checkHttps checkHttps = getModule.currentModuleClass("checkHttps", org.example.web.commonUtils.payment.checkHttps.class);
        checkHttps.checkHttps();
    }

}

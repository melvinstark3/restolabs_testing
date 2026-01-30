package org.example.web.modules.freedompay;

import org.example.web.commonUtils.payment.paymentNavigation;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class gatewayNavigation extends org.example.web.commonUtils.payment.gatewayNavigation{

    public void gatewayNavigation(boolean loggedIn) throws InterruptedException {
        paymentNavigation paymentNavigation = getModule.currentModuleClass("paymentNavigation", org.example.web.commonUtils.payment.paymentNavigation.class);
        paymentNavigation.paymentNavigation(loggedIn);
        try {
            wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-card")));
            List<WebElement> elements = driver.findElements(By.id("new-card"));
            if (!elements.isEmpty()) {
                System.out.println("Saved Cards Found!");
                driver.findElement(By.xpath("//label[@class=\"saved__payment__card add_new_card_btn\"]")).click();
            }
            else {
                System.out.println("No Saved Cards Found! Proceed Payment with New Card");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Proceeding Payment with New Card");
        }
    }

}
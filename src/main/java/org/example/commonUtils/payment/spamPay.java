package org.example.commonUtils.payment;

import org.example.core.browserSetup;

import org.example.core.moduleSelector;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class spamPay extends browserSetup{
    public spamPay() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        moduleSelector getModule = new moduleSelector();
        paymentsHelper gateway = getModule.currentModuleClass("cardDetailsInput");
        gateway.cardDetailsInput(readProperty("guestNewCardNumber"));
        driver.findElement(By.id("submit-button")).click();
        wait = new WebDriverWait(driver, 20);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-button"))).click();
            System.out.println("TC 32: FAIL: WARNING! Multiple Pay Clicks Intercepted. Check Case Manually.");
        } catch (TimeoutException | ElementNotInteractableException e) {
            // If button is NOT clickable due to loader overlay: PASS
            System.out.println("TC 32: PASS: Multiple Pay Clicks are not Allowed");
        }
    }
}

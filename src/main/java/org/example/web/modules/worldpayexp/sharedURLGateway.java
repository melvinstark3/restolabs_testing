package org.example.web.modules.worldpayexp;

import org.example.web.commonUtils.payment.newCardPayment;
import org.example.web.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class sharedURLGateway extends paymentsHelper {
    public void sharedURLGateway(boolean loggedIn) throws InterruptedException{
        wait = new WebDriverWait(driver, 30);
        System.out.println("Checking URL Sharing for Gateway Page");
        String gatewayURL=driver.getCurrentUrl();
        System.out.println("Checking URL Sharing with URL: " + gatewayURL);
        invokeBrowser();
        driver.get(gatewayURL);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,2000)", "");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_mainPage_txt_CardNumber")));
            newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.web.commonUtils.payment.newCardPayment.class);
            newCardPayment.newCardPayment(readProperty("guestNewCardNumber"),loggedIn);
            String postOrderURL=driver.getCurrentUrl().toLowerCase();
            System.out.println("Order Completion Redirects the User to URL: " + postOrderURL);
            if (postOrderURL.contains("success")) {
                System.out.println("TC 57: FAIL: User is redirected to Payment Success URL");
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
                    System.out.println("Shared Gateway URL redirected the User to Order Status Page");
                }
                catch (NoSuchElementException | TimeoutException e){
                    System.out.println("Shared Gateway URL redirected User to :" + driver.getCurrentUrl());
                }
            } else {
                System.out.println("TC 57: PASS: User is not redirected to payment Success URL");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC 57: PASS: User is not able to Pay with Shared Gateway URL");
        }
    }
}

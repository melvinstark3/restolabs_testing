package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class sharedURLPayment extends browserSetup{
    public sharedURLPayment() throws InterruptedException{
        String paymentURL=driver.getCurrentUrl();
        invokeBrowser();
        driver.get(paymentURL);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt=\"Paytrail logo\"]"))).isDisplayed();
            new newCardPayment("card",readProperty("guestNewCardNumber"));
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
                System.out.println("TC 57: FAIL: Payment was Successful in New Session");
            }
            catch (NoSuchElementException | TimeoutException e){
                System.out.println("TC 57: PASS: Payment was Unsuccessful in New Session");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC 57: PASS: User is not able to Pay with Shared Gateway URL");
        }
    }
}

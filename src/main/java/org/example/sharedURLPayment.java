package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class sharedURLPayment extends browserSetup{
    public sharedURLPayment() throws InterruptedException{
        System.out.println("Checking URL Sharing for Payment Page");
        String paymentURL=driver.getCurrentUrl();
        System.out.println("Checking URL Sharing with URL: " + paymentURL);
        invokeBrowser();
        driver.get(paymentURL);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-button"))).isDisplayed();
            if (Objects.equals(readProperty("tokenized"), "yes")){
                new checkSavedOrNew(readProperty("guestNewCardNumber"));
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
                    System.out.println("TC 57: FAIL: Shared Laravel Payment was Successful in New Session");
                }
                catch (NoSuchElementException | TimeoutException e){
                    System.out.println("TC 57: PASS: Shared Laravel Payment was Unsuccessful in New Session");
                }
            }
            else{
                System.out.println("TC 57: WARNING! Gateway Page Visible even with Tokenized Gateway. PLEASE CHECK MANUALLY!!");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC 57: PASS: User is not able to Pay with Shared Laravel Payment URL");
        }
    }
}

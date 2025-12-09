package org.example.modules.jccpay;

import org.example.commonUtils.payment.checkSavedOrNew;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class sharedURLPayment extends org.example.commonUtils.payment.sharedURLPayment{
    public void sharedURLPayment() {
        String paymentURL=driver.getCurrentUrl();
        invokeBrowser();
        driver.get(paymentURL);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label=\"Payment amount\"]")));
            if (Objects.equals(readProperty("tokenized"), "no")){
                checkSavedOrNew checkSavedOrNew = getModule.currentModuleClass("checkSavedOrNew",org.example.commonUtils.payment.checkSavedOrNew.class);
                checkSavedOrNew.checkSavedOrNew(readProperty("guestNewCardNumber"),false);
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
                    System.out.println("TC 57: FAIL: Payment was Successful in New Session");
                }
                catch (NoSuchElementException | TimeoutException e){
                    System.out.println("TC 57: PASS: Payment was Unsuccessful in New Session");
                }
            }
            else{
                System.out.println("TC 57: WARNING! Gateway Page Visible even with Tokenized Gateway. PLEASE CHECK MANUALLY!!");
            }
        } catch (NoSuchElementException | TimeoutException | InterruptedException e) {
            System.out.println("TC 57: PASS: User is not able to Pay with Shared Gateway URL");
        }
    }
}

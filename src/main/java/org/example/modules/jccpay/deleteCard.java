package org.example.modules.jccpay;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class deleteCard extends org.example.commonUtils.payment.deleteCard{
    public void deleteCard() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class=\"RawCard_pan__zd4+z\"])[1]")));
        String maskedCardNumber = driver.findElement(By.xpath("(//div[@class=\"RawCard_pan__zd4+z\"])[1]")).getText();
        String cardEndingNumber = maskedCardNumber.substring(maskedCardNumber.length() - 4);
        System.out.print("Attempting to Delete Card Ending with : " + cardEndingNumber);
        String tempExpiry = driver.findElement(By.xpath("(//div[@class=\"RawCard_expires__kHqmN\"])[1]")).getText();
        System.out.println(" with Expiry :" + tempExpiry);

        //Delete Card Action
        try {
            driver.findElement(By.xpath("//button[@aria-label=\"Edit\"]")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label=\"Remove\"]"))).click();
        } catch (ElementClickInterceptedException e){
            js.executeScript("arguments[0].click()", wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label=\"Remove\"]"))));
            driver.findElement(By.xpath("(//div[@class=\"Button_content__e67jX\"])[3]")).click();
            System.out.println("Accepting Card Deletion Confirmation!");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored){}
        }
        try {
            String recheckedCardNumber = driver.findElement(By.xpath("(//div[@class=\"RawCard_pan__zd4+z\"])[1]")).getText();
            String recheckedExpiry = driver.findElement(By.xpath("(//div[@class=\"RawCard_expires__kHqmN\"])[1]")).getText();
            if (Objects.equals(recheckedCardNumber, maskedCardNumber) && Objects.equals(recheckedExpiry, maskedCardNumber)) {
                System.out.println("ERROR! Matching Card Details were found after Delete Attempt");
            } else {
                System.out.println("TC_37: Pass: Saved Card was Deleted");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("WARNING! No Card was Displayed Please Check the Payment Flow");
        }
    }
}

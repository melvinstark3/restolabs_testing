package org.example.web.modules.jccpay;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class savedCardPayment extends org.example.web.commonUtils.payment.savedCardPayment{

    public void savedCardPayment() throws InterruptedException {
        String SavedCardPaymentOrderID = driver.findElement(By.xpath("//div[@class=\"DesktopLeft_orderDescription__iOkAH\"]")).getText();
        System.out.println("Attempting " + SavedCardPaymentOrderID);
        String maskedCardNumber = driver.findElement(By.xpath("(//div[@class=\"RawCard_pan__zd4+z\"])[1]")).getText();
        String cardEndingNumber = maskedCardNumber.substring(maskedCardNumber.length() - 4);
        System.out.println("Saved Card Details: Card Number Ends with : " + cardEndingNumber);
        String cardSubDetails = driver.findElement(By.xpath("(//div[@class=\"RawCard_expires__kHqmN\"])[1]")).getText();
        System.out.println("Extracted date/Card Type : " + cardSubDetails);
        //As a saved card is already selected by Default, we are just directly clicking Pay button
        driver.findElement(By.name("bindingCvc")).sendKeys(readProperty("cvv"));
        driver.findElement(By.xpath("//div[@class=\"Button_content__e67jX\"]")).click();
        System.out.println("Attempting Payment with Saved Card");
        if(cardEndingNumber.equals("5599")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"btn-half btn-success\"]"))).click();
            System.out.println("Proceeding Payment with Second Card with ACS Challenge");
        }
    }
}

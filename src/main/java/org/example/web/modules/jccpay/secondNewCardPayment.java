package org.example.web.modules.jccpay;

import org.example.web.commonUtils.payment.deleteCard;
import org.example.web.commonUtils.payment.newCardPayment;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class secondNewCardPayment extends org.example.web.commonUtils.payment.secondNewCardPayment{

    public void secondNewCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"DesktopLeft_orderDescription__iOkAH\"]")));
        String SecondCardPaymentOrderID = driver.findElement(By.xpath("//div[@class=\"DesktopLeft_orderDescription__iOkAH\"]")).getText();
        if (loggedIn) {
            deleteCard deleteCardAction = getModule.currentModuleClass("deleteCard", deleteCard.class);
            deleteCardAction.deleteCard();
            Thread.sleep(5000);
        }
        System.out.println("Attempting Payment for Order ID " + SecondCardPaymentOrderID);
        newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.web.commonUtils.payment.newCardPayment.class);
        newCardPayment.newCardPayment(cardNumber,loggedIn);
        if(cardNumber.equals("5555555555555599")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"btn-half btn-success\"]"))).click();
            System.out.println("Proceeding Payment with Second Card with ACS Challenge");
        }
    }

}

package org.example.commonUtils.payment;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class secondNewCardPayment extends browserSetup{

    public void secondNewCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        System.out.print("For 2nd Card Payment: ");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        String SecondCardPaymentOrderID = driver.findElement(By.xpath("//h4[@class=\"payment__for__id\"]")).getText();
        if (loggedIn) {
            deleteCard deleteCardAction = getModule.currentModuleClass("deleteCard", deleteCard.class);
            deleteCardAction.deleteCard();
            Thread.sleep(5000);
        }
        System.out.println("Attempting Payment for Order ID " + SecondCardPaymentOrderID);
        newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.commonUtils.payment.newCardPayment.class);
        newCardPayment.newCardPayment(cardNumber,loggedIn);
        System.out.println("Proceeding Payment with Second New Card");
    }

}

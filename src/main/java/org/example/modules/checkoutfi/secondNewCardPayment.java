package org.example.modules.checkoutfi;

import org.example.commonUtils.payment.newCardPayment;
import org.openqa.selenium.support.ui.WebDriverWait;

public class secondNewCardPayment extends org.example.commonUtils.payment.secondNewCardPayment{
    public void secondNewCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException{
        wait = new WebDriverWait(driver, 30);
        System.out.print("Checking CheckoutFi Wallet Payment");
        newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.commonUtils.payment.newCardPayment.class);
        newCardPayment.newCardPayment(cardNumber,loggedIn);
    }
}

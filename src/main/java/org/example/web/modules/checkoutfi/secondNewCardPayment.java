package org.example.web.modules.checkoutfi;

import org.example.web.commonUtils.payment.newCardPayment;
import org.openqa.selenium.support.ui.WebDriverWait;

public class secondNewCardPayment extends org.example.web.commonUtils.payment.secondNewCardPayment{
    public void secondNewCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException{
        wait = new WebDriverWait(driver, 30);
        System.out.print("Checking CheckoutFi Wallet Payment");
        newCardPayment newCardPayment = getModule.currentModuleClass("newCardPayment",org.example.web.commonUtils.payment.newCardPayment.class);
        newCardPayment.newCardPayment(cardNumber,loggedIn);
    }
}

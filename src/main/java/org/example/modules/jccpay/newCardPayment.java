package org.example.modules.jccpay;

import org.example.commonUtils.payment.defaultSaveCardCheckbox;
import org.example.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class newCardPayment extends org.example.commonUtils.payment.newCardPayment{

    public void newCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label=\"Payment amount\"]")));
        if (loggedIn) {
            defaultSaveCardCheckbox defaultSaveCardCheckbox = getModule.currentModuleClass("defaultSaveCardCheckbox",org.example.commonUtils.payment.defaultSaveCardCheckbox.class);
            defaultSaveCardCheckbox.defaultSaveCardCheckbox();
        }
        paymentsHelper gateway = getModule.currentModuleClass("cardDetailsInput",paymentsHelper.class);
        gateway.cardDetailsInput(cardNumber);
        driver.findElement(By.xpath("//div[@class=\"Button_content__e67jX\"]")).click();
        System.out.println("Proceeding Payment with New Card");
    }
}

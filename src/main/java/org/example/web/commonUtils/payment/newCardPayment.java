package org.example.web.commonUtils.payment;
import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class newCardPayment extends browserSetup{
    public void newCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        if (loggedIn) {
            defaultSaveCardCheckbox defaultSaveCardCheckbox = getModule.currentModuleClass("defaultSaveCardCheckbox",org.example.web.commonUtils.payment.defaultSaveCardCheckbox.class);
            defaultSaveCardCheckbox.defaultSaveCardCheckbox();
        }
        paymentsHelper gateway = getModule.currentModuleClass("cardDetailsInput",paymentsHelper.class);
        gateway.cardDetailsInput(cardNumber);
        driver.findElement(By.id("submit-button")).click();
        System.out.println("Proceeding Payment with New Card");
    }
}

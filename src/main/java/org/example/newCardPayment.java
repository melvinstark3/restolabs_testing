package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class newCardPayment extends browserSetup{

    public newCardPayment(String cardNumber){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        new cardDetailsInput(cardNumber);
        driver.findElement(By.id("submit-button")).click();
        System.out.println("CASE 4: Proceeding Payment with New Card");
    }
}

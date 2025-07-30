package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class secondNewCardPayment extends browserSetup{

    public secondNewCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"DesktopLeft_orderDescription__iOkAH\"]")));
        String SecondCardPaymentOrderID = driver.findElement(By.xpath("//div[@class=\"DesktopLeft_orderDescription__iOkAH\"]")).getText();
        if (loggedIn) {
            if(driver.findElement(By.xpath("//button[@aria-label=\"Select saved card\"]")).isDisplayed()){
                new deleteCard();
            }
        }
        System.out.println("Attempting Payment for Order ID " + SecondCardPaymentOrderID);
        new newCardPayment(cardNumber);
        if(cardNumber.equals("5555555555555599")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"btn-half btn-success\"]"))).click();
            System.out.println("Proceeding Payment with Second Card with ACS Challenge");
        }
    }

}

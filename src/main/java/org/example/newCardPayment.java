package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class newCardPayment extends browserSetup{

    public newCardPayment(String paymentMode, String cardNumber){
        wait = new WebDriverWait(driver, 5);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean paynowButton = false;
        try{
            paynowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-tab=\"tab-now\"]"))).isDisplayed();
            System.out.println("Page with Pay Now & Later Options is Displayed");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Page with Direct Payment Options is Displayed");
        }
        if(paynowButton){
            driver.findElement(By.xpath("//button[@data-tab=\"tab-now\"]")).click();
            if (paymentMode.equals("wallet")){
                driver.findElement(By.id("bank-container")).click();
                driver.findElement(By.xpath("//button[@aria-label=\"OP\"]")).click();
                System.out.println("Proceeding New Payment with Wallet");
            } else if (paymentMode.equals("card")){
                js.executeScript("window.scrollBy(0,2000)", "");
                driver.findElement(By.id("creditcard-container")).click();
                driver.findElement(By.xpath("(//img[@alt=\"Visa\"])[2]")).click();
            }
        }
        else {
            if (paymentMode.equals("wallet")){
                driver.findElement(By.xpath("//span[@class=\"toggle-indicator css-t18241\"]")).click();
                driver.findElement(By.xpath("//button[@aria-label=\"OP\"]")).click();
                System.out.println("Proceeding New Payment with Wallet");
            }
            else if (paymentMode.equals("card")){
                driver.findElement(By.xpath("//button[@aria-label=\"Visa\"]")).click();
            }
        }
        if (paymentMode.equals("card")){
            new cardDetailsInput(cardNumber);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            System.out.println("Proceeding Payment with New Card");
        }
        wait = new WebDriverWait(driver, 30);
    }
}

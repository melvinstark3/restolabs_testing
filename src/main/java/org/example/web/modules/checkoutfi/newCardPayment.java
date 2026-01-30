package org.example.web.modules.checkoutfi;

import org.example.web.commonUtils.payment.paymentsHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class newCardPayment extends org.example.web.commonUtils.payment.newCardPayment{

    public void newCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait = new WebDriverWait(driver, 5);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean paynowButton = false;
        String paymentMode = readProperty("checkoutFiPaymentMode");
        try{
            paynowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-tab=\"tab-now\"]"))).isDisplayed();
            System.out.println("Page with Pay Now & Later Options is Displayed");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Page with Direct Payment Options is Displayed");
        }
        if(paynowButton){
            driver.findElement(By.xpath("//button[@data-tab=\"tab-now\"]")).click();
            if (paymentMode.equalsIgnoreCase("wallet")){
                driver.findElement(By.id("bank-container")).click();
                driver.findElement(By.xpath("//button[@aria-label=\"OP\"]")).click();
                System.out.println("Proceeding New Payment with Wallet");
            } else if (paymentMode.equalsIgnoreCase("card")){
                js.executeScript("window.scrollBy(0,2000)", "");
                driver.findElement(By.id("creditcard-container")).click();
                driver.findElement(By.xpath("(//img[@alt=\"Visa\"])[2]")).click();
            }
        }
        else {
            if (paymentMode.equalsIgnoreCase("wallet")){
                driver.findElement(By.xpath("//span[@class=\"toggle-indicator css-t18241\"]")).click();
                driver.findElement(By.xpath("//button[@aria-label=\"OP\"]")).click();
                System.out.println("Proceeding New Payment with Wallet");
            }
            else if (paymentMode.equalsIgnoreCase("card")){
                driver.findElement(By.xpath("//button[@aria-label=\"Visa\"]")).click();
            }
        }
        if (paymentMode.equalsIgnoreCase("card")){
            paymentsHelper gateway = getModule.currentModuleClass("cardDetailsInput",paymentsHelper.class);
            gateway.cardDetailsInput(cardNumber);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            System.out.println("Proceeding Payment with New Card");
        }
        wait = new WebDriverWait(driver, 30);
    }
}

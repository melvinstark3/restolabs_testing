package org.example.modules.worldpayexp;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class matchAmount extends org.example.commonUtils.payment.matchAmount {
    public void matchAmount(String checkoutOrderTotal) {
        wait = new WebDriverWait(driver, 30);
        String gatewayOrderTotal;
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"total__amount\"]")));
            gatewayOrderTotal = driver.findElement(By.xpath("//h4[@class=\"total__amount\"]")).getText();
            System.out.println("Checking Order Amount Match with Laravel Page");
        } catch (NoSuchElementException | TimeoutException e){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_mainPage_lbl_TotalValue")));
            gatewayOrderTotal = driver.findElement(By.id("ctl00_mainPage_lbl_TotalValue")).getText();
            System.out.println("Checking Order Amount Match with Gateway Page");
        }
        if(Objects.equals(checkoutOrderTotal, gatewayOrderTotal)){
            System.out.println("TC 56, 30: PASS: Checkout Order Total and Currency Matches with Gateway");
        }
        else {
            System.out.println("TC 56, 30: FAIL: Order Totals/Currency Doesn't Match.");
        }
    }
}
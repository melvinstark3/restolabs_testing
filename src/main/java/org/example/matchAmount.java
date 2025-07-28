package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class matchAmount extends browserSetup {
    public matchAmount(String checkoutOrderTotal) {
        wait = new WebDriverWait(driver, 5);
        boolean paynowButton = false;
        String gatewayOrderTotal;
        try{
            paynowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-tab=\"tab-now\"]"))).isDisplayed();
            System.out.println("Page with Pay Now & Later Options is Displayed");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Page with Direct Payment Options is Displayed");
        }
        if (paynowButton) {
            gatewayOrderTotal = driver.findElement(By.xpath("//h2[@class=\"css-1cqpfm1\"]")).getText();
        } else {
            gatewayOrderTotal = driver.findElement(By.xpath("//h1[@class=\"css-3kuns2\"]")).getText();
        }
        if(Objects.equals(checkoutOrderTotal, gatewayOrderTotal)){
            System.out.println("TC 56, 30: PASS: Checkout Order Total and Currency Matches with Gateway");
        }
        else {
            System.out.println("TC 56, 30: FAIL: Order Totals/Currency Doesn't Match.");
        }
        wait = new WebDriverWait(driver, 30);
    }
}
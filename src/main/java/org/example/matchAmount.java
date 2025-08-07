package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class matchAmount extends browserSetup {
    public matchAmount(String checkoutOrderTotal, boolean laravelPage) {
        wait = new WebDriverWait(driver, 60);
        String gatewayOrderTotal;
        String gatewayOrderAmount = "";
        String gatewayOrderCurrency = "";
        String checkoutOrderAmount = checkoutOrderTotal.substring(1);
        String checkoutOrderCurrency = checkoutOrderAmount.substring(0, 1);
        Map<String, String> currencySymbolToCode = new HashMap<>();
        currencySymbolToCode.put("$", "USD");
        currencySymbolToCode.put("€", "EUR");
        currencySymbolToCode.put("£", "GBP");
        currencySymbolToCode.put("₹", "INR");
        String ConvertedCurrencyCode = currencySymbolToCode.getOrDefault(checkoutOrderCurrency, "UNKNOWN");

        if (laravelPage){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"total__amount\"]")));
            gatewayOrderTotal = driver.findElement(By.xpath("//h4[@class=\"total__amount\"]")).getText();
            System.out.println("Checking Order Amount Match with Laravel Page");
            if(Objects.equals(checkoutOrderTotal, gatewayOrderTotal)){
                System.out.println("TC 56, 30: PASS: Checkout Order Total Matches with Gateway");
            } else {
                System.out.println("TC 56, 30: FAIL: Order Totals Doesn't Match.");
            }

        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hpc--card-frame")));
            driver.findElement(By.id("hpc--card-frame")).click();
            WebElement gatewayIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("hpc--card-frame")
            ));
            driver.switchTo().frame(gatewayIframe);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SecurityCode")));
            driver.switchTo().defaultContent();
            gatewayOrderAmount = driver.findElement(By.id("totalAmount")).getText();
            gatewayOrderCurrency = driver.findElement(By.xpath("//span[@class=\"currencyCode\"]")).getText();
            System.out.println("Currency Displayed at Checkout Page is " + checkoutOrderCurrency);
            System.out.println("Currency Displayed at Gateway Page is " + gatewayOrderCurrency);
            System.out.println("Checking Order Amount Match with Gateway Page");
            if(Objects.equals(checkoutOrderAmount, gatewayOrderAmount)){
                System.out.println("TC 56: PASS: Checkout Order Total Matches with Gateway");
            } else {
                System.out.println("TC 56: FAIL: Order Totals/Currency Doesn't Match.");
            }

            if (Objects.equals(ConvertedCurrencyCode, gatewayOrderCurrency)){
                System.out.println("TC 30: PASS: Currency Displayed on Checkout matches with Currency on Gateway Page");
            } else {
                System.out.println("TC 30: FAIL: Currency Displayed on Checkout doesn't match with Currency on Gateway Page");
            }
        }
        wait = new WebDriverWait(driver, 60);
    }
}
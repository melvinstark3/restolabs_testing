package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class applyComoRewards extends browserSetup {
    public applyComoRewards() throws InterruptedException {
        String itemNote = driver.findElement(By.xpath("//span[@class=\"item-comment-value\"]")).getText();
        System.out.println("Item Note at Checkout is " + itemNote);
        if (itemNote.contains(readProperty("comoDealName"))){
            System.out.println("CASE 17: PASS: Como Deal Applied");
        }
        else {
            System.out.println("CASE 17: FAIL: Como Deal not Applied");
        }
        driver.findElement(By.xpath("//button[normalize-space()='Show Rewards']")).click();
        System.out.println("Opening Como Rewards Container.");
        int comoGiftButtonIndex = 1;
        String comoGiftButtonsXpath = "//button[@class=\"bg-app-gray-200 text-app-gray-500 capitalize text-base font-medium text-center w-full p-2 ng-star-inserted\"]";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(comoGiftButtonsXpath)));
        List<WebElement> comoGiftButtons = driver.findElements(By.xpath(comoGiftButtonsXpath));
        for (WebElement e: comoGiftButtons){
            if(e.getText().equalsIgnoreCase("Select")) {
                driver.findElement(By.xpath("(//button[@class=\"bg-app-gray-200 text-app-gray-500 capitalize text-base font-medium text-center w-full p-2 ng-star-inserted\"])["+comoGiftButtonIndex+"]")).click();
                driver.findElement(By.xpath("//button[@class=\"text-white font-medium rounded-xl text-sm px-4 py-2 w-full md:w-[25%] h-[50px] capitalize\"]")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@class=\"text-white font-medium rounded-xl text-sm px-4 py-2 w-full md:w-[25%] h-[50px] capitalize\"]")));
                System.out.println("Selecting Valid Como Gift!");
                Thread.sleep(3000);
                break;
            }
            comoGiftButtonIndex++;
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class=\"ml-3 inline-block text-sm md:text-base font-semibold text-app-gray-500\"]")));
        String orderTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]"))).getText();
        Double checkoutOrderAmount = Double.parseDouble(orderTotal.substring(1));
        String checkoutOrderCurrency = orderTotal.substring(0, 1);
        Double taxAmount = 0.0;
        try{
            String taxDisplayed = driver.findElement(By.xpath("//h6[@data-testid=\"Tax\"]")).getText();
            taxAmount = Double.parseDouble(taxDisplayed.substring(2));
            System.out.println("Tax Amount Applied at Checkout is " + taxDisplayed);
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("No Tax Applied in Line Items!");
        }
        Map<String, String> currencySymbolToCode = new HashMap<>();
        currencySymbolToCode.put("$", "USD");
        currencySymbolToCode.put("€", "EUR");
        currencySymbolToCode.put("£", "GBP");
        currencySymbolToCode.put("₹", "INR");
        String ConvertedCurrencyCode = currencySymbolToCode.getOrDefault(checkoutOrderCurrency, "UNKNOWN");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        String paymentMethodAfterGifts = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class=\"ml-3 inline-block text-sm md:text-base font-semibold text-app-gray-500\"]"))).getText();
        if(paymentMethodAfterGifts.equalsIgnoreCase("No Payment Required")){
            System.out.println("Case 19: PASS: No Payment Required was displayed for Complete Points/Credits payment");
        }
        else {
            System.out.println("Como Gifts didn't bring the Order Total to Zero");
            driver.findElement(By.xpath("//button[normalize-space()='Use Points']")).click();
            WebElement comoPointsField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("simple-coupon")));
            // We are not Entering Custom Input in Como Points Field for now as Max Applicable Points are supposed to be pre-filled
            driver.findElement(By.xpath("//button[@class=\"text-white absolute top-0 right-0 font-medium rounded-xl text-sm px-4 py-2 w-[25%] h-[50px] capitalize\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            Thread.sleep(5000);
            String paymentMethodAfterPoints = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class=\"ml-3 inline-block text-sm md:text-base font-semibold text-app-gray-500\"]"))).getText();
            if(paymentMethodAfterPoints.equalsIgnoreCase("No Payment Required")){
                System.out.println("Case 19: PASS: No Payment Required was displayed for Complete Gift or Points/Credits payment");
            } else {
                System.out.println("Case 19: FAIL: No Payment Required was NOT displayed for Complete Gift or Points/Credits payment");
            }
        }
    }
}

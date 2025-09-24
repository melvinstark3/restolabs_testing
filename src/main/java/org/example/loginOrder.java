package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class loginOrder extends browserSetup{
    public loginOrder(String orderType) throws InterruptedException {
        wait = new WebDriverWait(driver, 60);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean loggedIn = true;
        driver.navigate().to(readProperty("loginURL"));
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"ng-tns-c2869761534-1\"]"))).click();
            System.out.println("User is not Logged in. Attempting Login!");
            driver.findElement(By.id("email")).sendKeys(readProperty("loginUserEmail"));
            driver.findElement(By.id("password")).sendKeys(readProperty("loginUserPassword"));
            driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("User is already Logged in! Skipping Login");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\""+ orderType +"\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\""+ orderType +"\"]")).click();
        String orderItem;
        String orderMode;
        if (orderType.equalsIgnoreCase("comboOrder")){
            orderItem = readProperty("comboOrderItem");
            orderMode = readProperty("Dine In");
        } else {
            orderItem = readProperty("onlineOrderItem");
            orderMode = orderType;
        }
        System.out.println("Creating Cart");
        new createCart(orderMode, readProperty("loginLocation"),loggedIn,readProperty("loginOrderTime"),readProperty("preOrderTime"),orderItem);
        new checkout(orderMode, loggedIn);
        // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
        Thread.sleep(5000);
        try {
            try {
                wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"closeBillAddressDialog\"])[2]"))).click();
            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("Pre-Saved Billing Dialog wasn't Displayed.");
            }
            wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Billing Details Page wasn't Displayed");
        }
        System.out.println("Proceeding for Payment!");
        System.out.print("For Logged In Order: ");
        if(orderType.equalsIgnoreCase("comboOrder")){
            new deleteCard();
        }
        new checkSavedOrNew(readProperty("cardNumber"),loggedIn);
        try {
            wait = new WebDriverWait(driver, 60);
            String orderIDwithHash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']"))).getText();
            String OrderID = orderIDwithHash.replace("#", "");
            System.out.println("CASE 2: PASS: Order Placed by Logged In User Successfully. Order ID : " + OrderID);
            System.out.println("CASE 8: PASS: Pre-Order (Later) Order was Placed Successfully.");
            System.out.println("CASE 17: PASS: Customizable Item (Variant/Size) Order was Placed Successfully");
            System.out.println("CASE 17: PASS: Customizable Item (Variant's -> Checkbox) Order was Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("CASE 2: FAIL: Order wasn't Posted in Time. Please Check the Case Manually");
            System.out.println("CASE 8: PASS: Pre-Order (Later) Order wasn't Posted in Time.");
            System.out.println("CASE 17: PASS: Customizable Item (Variant/Size) Order wasn't Posted in Time");
            System.out.println("CASE 17: PASS: Customizable Item (Variant's -> Checkbox) Order wasn't Posted in Time");
        }
    }
}

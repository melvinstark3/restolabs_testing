package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class loginOrder extends browserSetup{
    public loginOrder(String orderMode) throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean loggedIn = true;
        driver.navigate().to(readProperty("loginURL"));
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("complete_space")));
            System.out.println("User is Already Logged In!");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("User is not Logged in. Attempting Login!");
            driver.findElement(By.id("guest_user")).click();
            driver.findElement(By.xpath("//button[normalize-space()='Get Code by SMS']")).click();
            driver.findElement(By.id("phone")).sendKeys(readProperty("comoLoginNumber"));
            driver.findElement(By.xpath("//span[@class='ng-tns-c1779389301-0']")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otp1"))).sendKeys(new getComoOTP().getOTP());
            driver.findElement(By.xpath("//span[@class='ng-tns-c1779389301-0']")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"inline-flex flex-shrink-0 justify-center items-center h-6 w-6 rounded-md text-gray-500 hover:text-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-400 focus:ring-offset-2 focus:ring-offset-white transition-all text-sm dark:focus:ring-gray-700 dark:focus:ring-offset-gray-800\"]"))).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\""+ orderMode +"\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\""+ orderMode +"\"]")).click();
        System.out.println("Creating Cart");
        new createCart(orderMode, readProperty("loginLocation"),loggedIn,readProperty("loginOrderTime"),readProperty("preOrderTime"));
        new checkout(orderMode, loggedIn);
        // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
        Thread.sleep(5000);
        try {
            wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"closeBillAddressDialog\"])[2]"))).click();
        }catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Pre-Saved Billing Dialog wasn't Displayed.");
        }
        wait = new WebDriverWait(driver, 30);
        if (orderMode.equalsIgnoreCase("Dine In")){
            System.out.println("Payment is Handled by Como Rewards Logic!");
        }
        else {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
            System.out.println("Proceeding for Payment!");
            System.out.print("For Logged In Order: ");
            new checkSavedOrNew(readProperty("cardNumber"),loggedIn);
        }
        try {
            wait = new WebDriverWait(driver, 60);
            String orderIDwithHash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']"))).getText();
            String OrderID = orderIDwithHash.replace("#", "");
            System.out.println("Case 5: PASS: Order Placed by Logged In User Successfully. Order ID : " + OrderID);
            if(orderMode.equalsIgnoreCase("Home Delivery")){
                System.out.println("Case 13: PASS: Check for the Online Payment order.");
                System.out.println("Case 18: PASS: Partial Points/Credits payment Order was placed Successfully");
            }
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Case 5: FAIL: Order wasn't Posted in Time. Please Check the Case Manually");
            if(orderMode.equalsIgnoreCase("Home Delivery")){
                System.out.println("Case 13: FAIL: Online Payment order wasn't placed Successfully");
                System.out.println("Case 18: FAIL: Partial Points/Credits payment Order wasn't placed Successfully");
            }
        }
    }
}

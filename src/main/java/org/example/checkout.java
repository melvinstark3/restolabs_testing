package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

public class checkout extends browserSetup{
    public checkout(String orderMode, boolean loggedIn) throws InterruptedException {
        wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"first_name\"]")));
            System.out.println("Customer Details Prompt was displayed! Filling Customer Details");
            driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys(readProperty("GuestFirstName"));
            driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys(readProperty("GuestLastName"));
            driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).clear();
            driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys(readProperty("GuestPhoneNumber"));
            try {
                WebElement customerEmailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
                customerEmailField.clear();
                customerEmailField.sendKeys(readProperty("GuestEmail"));
            } catch (NoSuchElementException | TimeoutException e){
                System.out.println("Skipping Email Field as it wasn't Displayed!");
            }
            driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
            // Stale Element Exception if Trying Implicit Wait
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\"//button[@class=\\\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\\\"]\"")));
        }catch (NoSuchElementException | TimeoutException e){
            System.out.println("Customer Details Prompt wasn't Displayed! Proceeding to Checkout!");
        }
        wait = new WebDriverWait(driver, 30);
        if(loggedIn){
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]"))).click();
            } catch (TimeoutException ignored){}
            if (orderMode.equalsIgnoreCase("Home Delivery")){
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]"))).click();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            js.executeScript("window.scrollBy(0,2000)", "");
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]"))).click();
                System.out.println("Saving Details in Save Address Popup!");
            } catch (NoSuchElementException | TimeoutException e){
                System.out.println("Skipping Save Address Popup as it wasn't displayed!");
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys(readProperty("orderComment"));
            System.out.println("CASE 33: Passing Order Comment at Checkout");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
            Thread.sleep(5000);
            try{
                wait = new WebDriverWait(driver, 10);
                if (driver.findElement(By.id("policy")).isSelected()) {
                    System.out.println("Privacy Policy and Terms & Conditions are Already Accepted");
                } else {
                    driver.findElement(By.id("policy")).click();
                    System.out.println("Privacy Policy and Terms & Conditions Accepted");
                }
            }
            catch (NoSuchElementException | TimeoutException e) {
                System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
            }
            wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")).click();
            if(orderMode.equalsIgnoreCase("Home Delivery")){
                new applyCoupon();
                try{
                    WebElement deliveryFeeLineItem = driver.findElement(By.xpath("//h6[@data-testid=\"Delivery Fee\"]"));
                    System.out.println("CASE 35: PASS: Delivery fee is displayed in Line Items with Value " + deliveryFeeLineItem.getText());
                } catch (NoSuchElementException | TimeoutException e){
                    System.out.println("CASE 35: FAIL: Delivery fee not Found in Line Items! Please Verify Manually");
                }
            } else {
                new applyRestoLoyalty();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
            if(orderMode.equalsIgnoreCase("Home Delivery")){
                try {
                    wait = new WebDriverWait(driver, 5);
                    WebElement farAddressValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@class=\"warning-info font-bold text-lg text-app-gray-500 text-center ng-star-inserted\"]")));
                    System.out.println("Validation Displayed : \"" + farAddressValidation.getText() + "\"");
                    driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
                    System.out.println("\nContinuing Order with Validation Prompt!");
                } catch (NoSuchElementException | TimeoutException e){
                    System.out.println("No Validation Prompt was Displayed!");
                }
                // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
                Thread.sleep(5000);
                try{
                    wait = new WebDriverWait(driver, 30);
                    String validation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-pc-section=\"summary\"]"))).getText();
                    System.out.println("ERROR: Order Stopped with Validation : " + validation);
                } catch (Exception e){
                    System.out.println("Proceeding with Order!");
                }
            }
        }
        //Guest Order
        else {
            try{
                wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"first_name\"]")));
                System.out.println("Customer Details Prompt was displayed! Filling Customer Details");
                driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys(readProperty("GuestFirstName"));
                driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys(readProperty("GuestLastName"));
                driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).clear();
                driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys(readProperty("GuestPhoneNumber"));
                try {
                    WebElement customerEmailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
                    customerEmailField.clear();
                    customerEmailField.sendKeys(readProperty("GuestEmail"));
                } catch (NoSuchElementException | TimeoutException e){
                    System.out.println("Skipping Email Field as it wasn't Displayed!");
                }
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]"))).click();
                // Stale Element Exception if Trying Implicit Wait
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")));
            }catch (NoSuchElementException | TimeoutException e){
                System.out.println("Customer Details Prompt wasn't Displayed! Proceeding to Checkout!");
            }
            wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            js.executeScript("window.scrollBy(0,2000)", "");
            System.out.println("CASE 1: For Guest Order: ");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            Thread.sleep(5000);
            wait = new WebDriverWait(driver, 10);
            // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
            try{
                if (driver.findElement(By.id("policy")).isSelected()) {
                    System.out.println("Privacy Policy and Terms & Conditions are Already Accepted");
                } else {
                    driver.findElement(By.id("policy")).click();
                    System.out.println("Privacy Policy and Terms & Conditions Accepted");
                }
            }
            catch (NoSuchElementException | TimeoutException e) {
                System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
            }
            wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")));
            driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        }
    }
}

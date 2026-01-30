package org.example.web.modules.restoclient;

import org.example.web.commonUtils.payment.checkSavedOrNew;
import org.example.core.browserSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class loginOrder extends browserSetup {
    public loginOrder(String orderMode, String orderLocation, String orderTime) throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean loggedIn = true;
        driver.navigate().to("https://" + readProperty("shortcode") + "." + System.getProperty("server"));
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("//button[contains(@class, 'user_login_btn rounded-md px-1')]")));
            System.out.println("User is Already Logged In!");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("User is not Logged in. Attempting Login!");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Login\"]//span"))).click();
            driver.findElement(By.id("email")).sendKeys(readProperty("loginUserEmail"));
            driver.findElement(By.id("password")).sendKeys(readProperty("loginUserPassword"));
            driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\""+ orderMode +"\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\""+ orderMode +"\"]")).click();
        System.out.println("Creating Cart");
        new createCart(orderMode, orderLocation,orderTime);
        new checkout(orderMode, loggedIn);
        // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
        Thread.sleep(5000);
        try{
            wait = new WebDriverWait(driver, 30);
            //Add Billing Details Title of Container
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("(//div[@class=\"text-xl font-bold mb-4\"])[2]"),"Add Billing Address"));
            System.out.println("Filling Necessary Billing Details");
            try{
                try {
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillFirst name\"])[2]")).clear();
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillFirst name\"])[2]")).sendKeys(readProperty("BillingFirstName"));
                } catch (ElementNotInteractableException e){
                    driver.findElement(By.xpath("//input[@data-testid=\"BillFirst name\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"BillFirst name\"]")).sendKeys(readProperty("BillingFirstName"));
                }
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Billing First Name Field as it's not Displayed");
            }
            try{
                try {
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillLast name\"])[2]")).clear();
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillLast name\"])[2]")).sendKeys(readProperty("BillingLastName"));
                } catch (ElementNotInteractableException e){
                    driver.findElement(By.xpath("//input[@data-testid=\"BillLast name\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"BillLast name\"]")).sendKeys(readProperty("BillingLastName"));
                }
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Last Name Field as it's not Displayed");
            }
            try{
                try {
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillCompany\"])[2]")).clear();
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillCompany\"])[2]")).sendKeys(readProperty("BillingCompany"));
                } catch (ElementNotInteractableException e){
                    driver.findElement(By.xpath("//input[@data-testid=\"BillCompany\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"BillCompany\"]")).sendKeys(readProperty("BillingCompany"));
                }
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Billing Company Field as it's not Displayed");
            }
            try{
                try {
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillComplete address\"])[2]")).clear();
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillComplete address\"])[2]")).sendKeys(readProperty("BillingCompleteAddress"));
                } catch (ElementNotInteractableException e){
                    driver.findElement(By.xpath("//input[@data-testid=\"BillComplete address\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"BillComplete address\"]")).sendKeys(readProperty("BillingCompleteAddress"));
                }
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Logged In Billing Complete Address Field as it's not Displayed");
            }
            try{
                try {
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillStreet address 2\"])[2]")).clear();
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillStreet address 2\"])[2]")).sendKeys(readProperty("BillingStreetAddress2"));
                } catch (ElementNotInteractableException e){
                    driver.findElement(By.xpath("//input[@data-testid=\"BillStreet address 2\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"BillStreet address 2\"]")).sendKeys(readProperty("BillingStreetAddress2"));
                }
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Billing Street Address 2 Field as it's not Displayed");
            }
            try{
                try {
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillCity\"])[2]")).clear();
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillCity\"])[2]")).sendKeys(readProperty("BillingCity"));
                } catch (ElementNotInteractableException e){
                    driver.findElement(By.xpath("//input[@data-testid=\"BillCity\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"BillCity\"]")).sendKeys(readProperty("BillingCity"));
                }
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Billing City Field as it's not Displayed");
            }
            try{
                try {
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillPostal code\"])[2]")).clear();
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillPostal code\"])[2]")).sendKeys(readProperty("BillingPostalCode"));
                } catch (ElementNotInteractableException e){
                    driver.findElement(By.xpath("//input[@data-testid=\"BillPostal code\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"BillPostal code\"]")).sendKeys(readProperty("BillingPostalCode"));
                }
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Billing Postal Code Field as it's not Displayed");
            }
            try{
                try {
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillPhone number\"])[2]")).clear();
                    driver.findElement(By.xpath("(//input[@data-testid=\"BillPhone number\"])[2]")).sendKeys(readProperty("BillingPhoneNumber"));
                } catch (ElementNotInteractableException e){
                    driver.findElement(By.xpath("//input[@data-testid=\"BillPhone number\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"BillPhone number\"]")).sendKeys(readProperty("BillingPhoneNumber"));
                }
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Billing Phone Number Field as it's not Displayed");
            }
            //Submit Button is Displayed only when all Required Fields are filled
            driver.findElement(By.xpath("//button[@data-testid=\"SubmitBillingDetails\"]")).click();
            // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
            Thread.sleep(5000);
        } catch (NoSuchElementException | TimeoutException e){
            try {
                wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"closeBillAddressDialog\"])[2]"))).click();
            }catch (NoSuchElementException | TimeoutException f) {
                System.out.println("Pre-Saved Billing Dialog wasn't Displayed.");
            }
        }
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
        } catch (TimeoutException e){
            System.out.println("Billing Page Continue Button wasn't Displayed!");
        }
        System.out.println("Proceeding for Payment!");
        System.out.print("For Logged In Order: ");
        checkSavedOrNew checkSavedOrNew = getModule.currentModuleClass("checkSavedOrNew",org.example.web.commonUtils.payment.checkSavedOrNew.class);
        checkSavedOrNew.checkSavedOrNew(readProperty("cardNumber"),loggedIn);
        try {
            wait = new WebDriverWait(driver, 60);
            String orderIDwithHash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']"))).getText();
            String OrderID = orderIDwithHash.replace("#", "");
            System.out.println("Case 5: PASS: Order Placed by Logged In User Successfully. Order ID : " + OrderID);
            if(orderMode.equalsIgnoreCase("Home Delivery")){
                System.out.println("Case 13: PASS: Check for the Online Payment order.");
            }
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Case 5: FAIL: Order wasn't Posted in Time. Please Check the Case Manually");
            if(orderMode.equalsIgnoreCase("Home Delivery")){
                System.out.println("Case 13: FAIL: Online Payment order wasn't placed Successfully");
            }
        }
    }
}

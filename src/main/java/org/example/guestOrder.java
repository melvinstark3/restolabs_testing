package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class guestOrder extends browserSetup{
    public guestOrder(String orderMode) throws InterruptedException {
        boolean loggedIn = false;
        driver.navigate().to(readProperty("GuestURL"));
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\""+ orderMode +"\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\""+ orderMode +"\"]")).click();
        new createCart(orderMode, readProperty("GuestLocation"),loggedIn,"later",readProperty("preOrderTime"));
        new checkout(orderMode, loggedIn);
        try{
            wait = new WebDriverWait(driver, 30);
            //Add Billing Details Title of Container
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class=\"text-xl font-bold mb-4\"])[2]")));
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
                System.out.println("Skipping Billing Complete Address Field as it's not Displayed");
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
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
        new checkSavedOrNew(readProperty("guestNewCardNumber"),loggedIn);
        try {
            wait = new WebDriverWait(driver, 60);
            String orderIDwithHash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']"))).getText();
            String OrderID = orderIDwithHash.replace("#", "");
            System.out.println("Case 3: PASS: Place a guest order. Order ID : " + OrderID);
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Case 3: FAIL: Guest Order Failed. Please Check the Case Manually");
        }
    }
}

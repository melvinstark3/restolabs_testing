package org.example.modules.restoclient;

import org.example.core.browserSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class checkout extends browserSetup {
    public checkout(String orderMode, boolean loggedIn) throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if(loggedIn){
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]"))).click();
            } catch (TimeoutException ignored){}
            if (orderMode.equalsIgnoreCase("Home Delivery")){
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]"))).click();
                } catch (TimeoutException ignored){}
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            try{
                driver.findElement(By.xpath("//span[@class=\"cursor-pointer text-red-600 ng-star-inserted\"]")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]")));
                System.out.println("Filling Necessary Checkout Details");
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"company\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"company\"]")).sendKeys(readProperty("checkoutCompany"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Company Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"street1\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"street1\"]")).sendKeys(readProperty("checkoutStreet1"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Street 1 Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"street2\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"street2\"]")).sendKeys(readProperty("checkoutStreet2"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Street 2 Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"city\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"city\"]")).sendKeys(readProperty("checkoutCity"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping City Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"postal_code\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"postal_code\"]")).sendKeys(readProperty("checkoutPostalCode"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Postal Code Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.name("email")).clear();
                    driver.findElement(By.name("email")).sendKeys(readProperty("GuestEmail"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Email Field as it's not Displayed");
                }
                driver.findElement(By.xpath("//button[@data-testid=\"continueAddAddress\"]")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            } catch (NoSuchElementException | TimeoutException e){
                System.out.println("Additional Checkout Details are not required!");
            }
            js.executeScript("window.scrollBy(0,2000)", "");
            wait = new WebDriverWait(driver, 120);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
            driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).clear();
            driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys("Test Order Don't Prepare");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
            Thread.sleep(2000);
            try{
                wait = new WebDriverWait(driver, 3);
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
            if (orderMode.equalsIgnoreCase("Home Delivery")){
                try{
                    String deliveryFee = driver.findElement(By.xpath("//h6[@data-testid=\"Delivery Fee\"]")).getText();
                    System.out.println("CASE 11: PASS: Delivery Fee Displayed at Checkout is " + deliveryFee);
                }catch (NoSuchElementException | TimeoutException e){
                    System.out.println("CASE 11: FAIL: No Delivery Fee Found!");
                }
            }
            wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label=\"Select Custom Tip\"]"))).click();
            driver.findElement(By.name("customAmount1")).sendKeys(readProperty("CustomTipAmount"));
            driver.findElement(By.xpath("//button[@data-testid=\"tip1CustomSubmit\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
            if(Objects.equals(readProperty("useCoupon"),"yes")){
                try{
                    boolean couponPreApplied = driver.findElement(By.xpath("//h6[contains(@data-testid, 'Coupon')]")).isDisplayed();
                    if (couponPreApplied){
                        System.out.println(driver.findElement(By.xpath("//h6[contains(@data-testid, 'Coupon')]")).getAttribute("data-testid") + " is Already Applied.");
                        driver.findElement(By.xpath("//button[@class=\"coupon__remove text-xs font-semibold text-red-600 bg-red-100 py-0.5 px-2 hover:bg-app-gray-100 transition-all duration-300 rounded-full\"]")).click();
                        System.out.println("Removing Pre-Applied Coupon!");
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
                        try{
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid, 'Coupon')]")));
                            System.out.println("ERROR! Unable to Remove Pre-Applied Coupon");
                        } catch (NoSuchElementException | TimeoutException e){
                            System.out.println("Pre-Applied Coupon was Removed! Proceeding!");
                        }
                    }
                } catch (NoSuchElementException ignored){}

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
                String couponsXpath = "//span[@class=\"flex-shrink-0 py-2 px-4 text-sm text-black font-semibold rounded-xl truncate max-w-[150px]\"]";
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(couponsXpath)));
                List<WebElement> couponsDisplayed = driver.findElements(By.xpath(couponsXpath));
                for(WebElement e : couponsDisplayed){
                    if(e.getText().equalsIgnoreCase(readProperty("couponCodeToApply"))){
                        System.out.println("Applying Coupon " + e.getText());
                        e.click();
                        break;
                    }
                }
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"coupon__remove text-xs font-semibold text-red-600 bg-red-100 py-0.5 px-2 hover:bg-app-gray-100 transition-all duration-300 rounded-full\"]")));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid, 'Coupon')]")));
                    System.out.println("PASS: Coupon has been Applied for the Order");
                } catch (NoSuchElementException | TimeoutException e){
                    System.out.println("FAIL: Coupon application was Unsuccessful");
                }
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
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
        //Guest Order
        else {
            try {
                System.out.println("Entering Customer Details");
                //Xpath for Modal Sheet Popup
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"modal-content max-h-full overflow-hidden flex flex-col bg-white shadow-sm rounded-t-3xl rounded-b-none md:rounded-xl\"]")));
                try {
                    driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys(readProperty("GuestFirstName"));
                } catch (ElementNotInteractableException e){
                    System.out.println("Skipping First Name Field as it was not Displayed!");
                }
                try {
                    driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys(readProperty("GuestLastName"));
                } catch (ElementNotInteractableException e){
                    System.out.println("Skipping Last Name Field as it was not Displayed!");
                }
                try {
                    driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys(readProperty("GuestPhoneNumber"));
                } catch (ElementNotInteractableException e){
                    System.out.println("Skipping Phone Number Field as it was not Displayed!");
                }
                try {
                    driver.findElement(By.name("email")).clear();
                    driver.findElement(By.name("email")).sendKeys(readProperty("GuestEmail"));
                } catch (ElementNotInteractableException e){
                    System.out.println("Skipping Email Field as it was not Displayed!");
                }
                driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
            }
            catch (NoSuchElementException | TimeoutException e){
                System.out.println("Customer Details are Pre-filled. Continuing with Guest Order!");
            }
            try{
                driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")));
            } catch (NoSuchElementException | TimeoutException ignored){}
            if (orderMode.equalsIgnoreCase("Home Delivery")){
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]"))).click();
                } catch (TimeoutException ignored){}
            }
            // Stale Element Exception if Trying Implicit Wait
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\"//button[@class=\\\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\\\"]\"")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            try{
                driver.findElement(By.xpath("//span[@class=\"cursor-pointer text-red-600 ng-star-inserted\"]")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]")));
                System.out.println("Filling Necessary Checkout Details");
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"company\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"company\"]")).sendKeys(readProperty("checkoutCompany"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Company Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"street1\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"street1\"]")).sendKeys(readProperty("checkoutStreet1"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Street 1 Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"street2\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"street2\"]")).sendKeys(readProperty("checkoutStreet2"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Street 2 Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"city\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"city\"]")).sendKeys(readProperty("checkoutCity"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping City Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.xpath("//input[@data-testid=\"postal_code\"]")).clear();
                    driver.findElement(By.xpath("//input[@data-testid=\"postal_code\"]")).sendKeys(readProperty("checkoutPostalCode"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Postal Code Field as it's not Displayed");
                }
                try{
                    driver.findElement(By.name("email")).clear();
                    driver.findElement(By.name("email")).sendKeys(readProperty("GuestEmail"));
                } catch (NoSuchElementException e){
                    System.out.println("Skipping Email Field as it's not Displayed");
                }
                driver.findElement(By.xpath("//button[@data-testid=\"continueAddAddress\"]")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            } catch (NoSuchElementException | TimeoutException e){
                System.out.println("Checkout Details are not required!");
            }
            js.executeScript("window.scrollBy(0,2000)", "");
            System.out.println("TC_07: For Guest Order: ");
            Thread.sleep(2000);
            // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
            try{
                wait = new WebDriverWait(driver, 3);
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
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label=\"Select Custom Tip\"]"))).click();
            driver.findElement(By.name("customAmount1")).sendKeys(readProperty("CustomTipAmount"));
            driver.findElement(By.xpath("//button[@data-testid=\"tip1CustomSubmit\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));

            if(Objects.equals(readProperty("useCoupon"),"yes")){
                try{
                    boolean couponPreApplied = driver.findElement(By.xpath("//h6[contains(@data-testid, 'Coupon')]")).isDisplayed();
                    if (couponPreApplied){
                        System.out.println(driver.findElement(By.xpath("//h6[contains(@data-testid, 'Coupon')]")).getAttribute("data-testid") + " is Already Applied.");
                        driver.findElement(By.xpath("//button[@class=\"coupon__remove text-xs font-semibold text-red-600 bg-red-100 py-0.5 px-2 hover:bg-app-gray-100 transition-all duration-300 rounded-full\"]")).click();
                        System.out.println("Removing Pre-Applied Coupon!");
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
                        try{
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid, 'Coupon')]")));
                            System.out.println("ERROR! Unable to Remove Pre-Applied Coupon");
                        } catch (NoSuchElementException | TimeoutException e){
                            System.out.println("Pre-Applied Coupon was Removed! Proceeding!");
                        }
                    }
                } catch (NoSuchElementException ignored){}

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
                String couponsXpath = "//span[@class=\"flex-shrink-0 py-2 px-4 text-sm text-black font-semibold rounded-xl truncate max-w-[150px]\"]";
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(couponsXpath)));
                List<WebElement> couponsDisplayed = driver.findElements(By.xpath(couponsXpath));
                for(WebElement e : couponsDisplayed){
                    if(e.getText().equalsIgnoreCase(readProperty("couponCodeToApply"))){
                        System.out.println("Applying Coupon " + e.getText());
                        e.click();
                        break;
                    }
                }
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"coupon__remove text-xs font-semibold text-red-600 bg-red-100 py-0.5 px-2 hover:bg-app-gray-100 transition-all duration-300 rounded-full\"]")));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid, 'Coupon')]")));
                    System.out.println("PASS: Coupon has been Applied for the Order");
                } catch (NoSuchElementException | TimeoutException e){
                    System.out.println("FAIL: Coupon application was Unsuccessful");
                }
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();

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
}

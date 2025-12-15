package org.example.modules.basic;

import org.example.core.browserSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loyaltyOrder extends browserSetup {
    boolean loggedIn = true;

    public void loyaltyCart(boolean exhaustPoints) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + readProperty("loginLocation") + "']"))).click();
        System.out.println("Selected Location : " + readProperty("loginLocation"));
        js.executeScript("window.scrollBy(0,2000)", "");
        try {
            wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"Yes\"]")));
            //For Some reason even after Completed, We get Cart reset Popup, Handle it with Yes for now.
            driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Continuing to Menu");
        }
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        driver.findElement(By.id("asapbtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")));
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
        //h5 is being used for Superb List View & h4 is being used for Superb
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"item_title_html\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class=\"item_title_html\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"item__text h-full\"]")));
        if(exhaustPoints){
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//h4[normalize-space()='Meal Deal 3']")));
            //h5 is being used for Superb List View & h4 is being used for Superb
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='Gyro Sandwich']")));
            driver.findElement(By.xpath("//h4[normalize-space()='Gyro Sandwich']")).click();
            wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"addToCart\"]")));
            driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        } else {
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//h4[normalize-space()='Meal Deal 3']")));
            //h5 is being used for Superb List View & h4 is being used for Superb
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='Meal Deal 3']")));
            driver.findElement(By.xpath("//h4[normalize-space()='Meal Deal 3']")).click();
            wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"addToCart\"]")));
            driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
            Thread.sleep(5000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"item_title_html\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class=\"item_title_html\"]")));

            try{
                if(driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).isDisplayed()){
                    driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
                }
            } catch (NoSuchElementException e){
                System.out.println("Cart Header did not Intercept! Proceeding Ahead!");
            }
            js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//h4[normalize-space()='Meal Deal 4']")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='Meal Deal 4']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"addToCart\"]")));
            driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        }
        Thread.sleep(5000);
        js.executeScript("window.scrollBy(0,10)", "");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'close inline-flex flex-shrink-0 justify-center items-center h-8 w-8 p-2 text-white shadow-md rounded-full')]"))).click();
            System.out.println("Closed Promotional Banner");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("No Promotional Banner was Displayed!");
        }
        try {
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
            System.out.println("Cart Popup Appeared Automatically. Proceeding to Checkout");
        }
        catch (NoSuchElementException | TimeoutException e){
            driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
            System.out.println("Proceeding to Checkout");
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label=\"Select Payment Methods\"]")));
    }

    public int collectPointsOrder() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        driver.navigate().to(readProperty("storeURL"));
        try{
            System.out.println("User is not Logged in. Attempting Login!");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Login\"]//span"))).click();
            driver.findElement(By.id("email")).sendKeys(readProperty("loyaltyUserEmail"));
            driver.findElement(By.id("password")).sendKeys(readProperty("loyaltyUserPassword"));
            driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        } catch (NoSuchElementException | TimeoutException e){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'user_login_btn rounded-md px-1 text-base text-colorTitle font-bold uppercase cursor-pointer')]")));
            System.out.println("User is Already Logged In!");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Dine In']")));
        driver.findElement(By.xpath("//button[@aria-label='Dine In']")).click();
        loyaltyCart(false);

        wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
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
            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("Skipping Email Field as it wasn't Displayed!");
            }
            driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
            // Stale Element Exception if Trying Implicit Wait
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\"//button[@class=\\\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\\\"]\"")));
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Customer Details Prompt wasn't Displayed! Proceeding to Checkout!");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        js.executeScript("window.scrollBy(0,2000)", "");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]"))).click();
            System.out.println("Saving Details in Save Address Popup!");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Skipping Save Address Popup as it wasn't displayed!");
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"" + readProperty("CODPaymentMode") + "\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
        Thread.sleep(5000);
        try {
            wait = new WebDriverWait(driver, 10);
            if (driver.findElement(By.id("policy")).isSelected()) {
                System.out.println("Privacy Policy and Terms & Conditions are Already Accepted");
            } else {
                driver.findElement(By.id("policy")).click();
                System.out.println("Privacy Policy and Terms & Conditions Accepted");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\"" + readProperty("CODPaymentMode") + "\"]")).click();

        String subtotalDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@data-testid=\"Subtotal\"]"))).getText();

        Pattern pattern = Pattern.compile("\\$(\\d+)\\.");
        Matcher matcher = pattern.matcher(subtotalDisplayed);
        int expectedPoints;
        int trimmedsubtotal = 0;
        if (matcher.find()) {
            trimmedsubtotal = Integer.parseInt(matcher.group(1));
        }
        expectedPoints = trimmedsubtotal*Integer.parseInt(readProperty("loyaltyAllotmentFactor"))/100;
        System.out.println("Expected Points to be Allotted are "+expectedPoints);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        try {
            String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
            System.out.println("Order to Collect Points Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Order to Collect Points was not Placed in Time!");
        }
        return expectedPoints;
    }

    public void usePoints(int expectedPoints) throws InterruptedException {
        String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
        driver.findElement(By.xpath(restartOrderButtonXpath)).click();
        loyaltyCart(false);
        wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
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
            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("Skipping Email Field as it wasn't Displayed!");
            }
            driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
            // Stale Element Exception if Trying Implicit Wait
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\"//button[@class=\\\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\\\"]\"")));
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Customer Details Prompt wasn't Displayed! Proceeding to Checkout!");
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        js.executeScript("window.scrollBy(0,2000)", "");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]"))).click();
            System.out.println("Saving Details in Save Address Popup!");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Skipping Save Address Popup as it wasn't displayed!");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"" + readProperty("CODPaymentMode") + "\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
        Thread.sleep(5000);
        try {
            wait = new WebDriverWait(driver, 10);
            if (driver.findElement(By.id("policy")).isSelected()) {
                System.out.println("Privacy Policy and Terms & Conditions are Already Accepted");
            } else {
                driver.findElement(By.id("policy")).click();
                System.out.println("Privacy Policy and Terms & Conditions Accepted");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\"" + readProperty("CODPaymentMode") + "\"]")).click();
        try{
            String redeemablePoints = driver.findElement(By.xpath("//span[@class=\"text-base font-bold leading-4 md:leading-5\"]")).getText();
            if(expectedPoints == Integer.parseInt(redeemablePoints)){
                System.out.println("PASS: Loyalty Points Collected based on Previous Order are " + redeemablePoints);
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"openLoyaltyPopUp\"]"))).click();
            //Directly Apply the All Available Points as they should be less than Amount Payable
            driver.findElement(By.xpath("//button[@data-testid=\"loyaltySubmit\"]")).click();
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"tip__remove text-xs font-semibold text-red-600 bg-red-100 py-0.5 px-2 hover:bg-app-gray-100 transition-all duration-300 rounded-full ng-star-inserted\"]")));
                String appliedLoyaltyPoints = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid, 'Loyalty Points')]"))).getAttribute("data-testid");
                System.out.println(appliedLoyaltyPoints + " has been applied for the Order");
            } catch (NoSuchElementException | TimeoutException e){
                System.out.println("ERROR! Loyalty Points application was Unsuccessful");
            }
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("FAIL: Loyalty: Points Collection based on Previous Order Failed!");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
            System.out.println("Loyalty Points Redemption Order was Successful");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Loyalty Points Redemption wasn't Posted in Time. Please Check the Case Manually");
        }
    }

    public void checkPointUsage() throws InterruptedException {
        String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
        driver.findElement(By.xpath(restartOrderButtonXpath)).click();
        loyaltyCart(true);
        wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
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
            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("Skipping Email Field as it wasn't Displayed!");
            }
            driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
            // Stale Element Exception if Trying Implicit Wait
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\"//button[@class=\\\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\\\"]\"")));
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Customer Details Prompt wasn't Displayed! Proceeding to Checkout!");
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        js.executeScript("window.scrollBy(0,2000)", "");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]"))).click();
            System.out.println("Saving Details in Save Address Popup!");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Skipping Save Address Popup as it wasn't displayed!");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"" + readProperty("CODPaymentMode") + "\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
        Thread.sleep(5000);
        try {
            wait = new WebDriverWait(driver, 10);
            if (driver.findElement(By.id("policy")).isSelected()) {
                System.out.println("Privacy Policy and Terms & Conditions are Already Accepted");
            } else {
                driver.findElement(By.id("policy")).click();
                System.out.println("Privacy Policy and Terms & Conditions Accepted");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\"" + readProperty("CODPaymentMode") + "\"]")).click();
        try{
            String redeemablePoints = driver.findElement(By.xpath("//span[@class=\"text-base font-bold leading-4 md:leading-5\"]")).getText();
            System.out.println("Loyalty: Collected Points available to Redeem are " + redeemablePoints);
        } catch (NoSuchElementException e){
            System.out.println("No Points are Available to Redeem!");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"openLoyaltyPopUp\"]"))).click();
        //Directly Apply the All Available Points as they should be less than Amount Payable
        driver.findElement(By.xpath("//button[@data-testid=\"loyaltySubmit\"]")).click();
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"tip__remove text-xs font-semibold text-red-600 bg-red-100 py-0.5 px-2 hover:bg-app-gray-100 transition-all duration-300 rounded-full ng-star-inserted\"]")));
            String appliedLoyaltyPoints = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid, 'Loyalty Points')]"))).getAttribute("data-testid");
            System.out.println(appliedLoyaltyPoints + " has been applied for the Order");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("ERROR! Loyalty Points application was Unsuccessful");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\"" + readProperty("CODPaymentMode") + "\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
            System.out.println("Order to Exhaust Points Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Order to Exhaust Points was not Placed in Time!");
        }
    }

    public loyaltyOrder() throws InterruptedException {
        int expectedPoints = collectPointsOrder();
        usePoints(expectedPoints);
        checkPointUsage();
    }
}

package org.example.web.commonUtils.payment;

import org.example.core.browserSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class guestOrder extends browserSetup {
    public guestOrder() throws InterruptedException {
        boolean loggedIn = false;
        driver.navigate().to("https://" + readProperty("shortcode") + "." + System.getProperty("server"));
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).click();
        new createCart(readProperty("GuestLocation"),readProperty("guestOrderItem"),loggedIn);
        wait = new WebDriverWait(driver, 30);
        System.out.println("Entering Customer Details");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"first_name\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys(readProperty("GuestFirstName"));
        driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys(readProperty("GuestLastName"));
        driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys(readProperty("GuestPhoneNumber"));
        driver.findElement(By.name("email")).sendKeys(readProperty("GuestEmail"));
        driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
        // Stale Element Exception if Trying Implicit Wait
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\"//button[@class=\\\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\\\"]\"")));
        wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        System.out.println("TC_07: For Guest Order: ");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@placeholder='Note here...']"))).sendKeys(readProperty("guestOrderComment"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
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
        wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
        String selectedPaymentMode = driver.findElement(By.xpath("//input[contains(@aria-label,'Selected')]")).getAttribute("data-testid");
        System.out.println("Selected Payment Mode is " + selectedPaymentMode);
        String checkoutOrderTotal = driver.findElement(By.xpath("//h5[@data-testid=\"orderTotal\"]")).getText();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();

        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@data-testid=\"BillFirst name\"])[2]")));
        driver.findElement(By.xpath("(//input[@data-testid=\"BillFirst name\"])[2]")).sendKeys(readProperty("guestBillingFirstName"));
        driver.findElement(By.xpath("(//input[@data-testid=\"BillLast name\"])[2]")).sendKeys(readProperty("guestBillingLastName"));
        driver.findElement(By.xpath("(//input[@data-testid=\"BillPhone number\"])[2]")).sendKeys(readProperty("guestBillingPhoneNumber"));
        driver.findElement(By.xpath("//button[@data-testid=\"SubmitBillingDetails\"]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(//input[@data-testid=\"BillPhone number\"])[2]")));
        // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
        matchAmount matchAmount = getModule.currentModuleClass("matchAmount",org.example.web.commonUtils.payment.matchAmount.class);
        matchAmount.matchAmount(checkoutOrderTotal);
        checkSavedOrNew checkSavedOrNew = getModule.currentModuleClass("checkSavedOrNew",org.example.web.commonUtils.payment.checkSavedOrNew.class);
        checkSavedOrNew.checkSavedOrNew(readProperty("guestNewCardNumber"),loggedIn);
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("TC_12: PASS - Payment Successful by a New Card");
            System.out.println("TC_20: PASS - Payment Gateway is working for a Single Location");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC_12: FAIL - Payment Failed by a New Card");
            System.out.println("TC_20: FAIL - Payment Gateway Not working for a Single Location");
        }
        new browserBackPostOrder();
        paymentNavigation paymentNavigation = getModule.currentModuleClass("paymentNavigation", org.example.web.commonUtils.payment.paymentNavigation.class);
        paymentNavigation.paymentNavigation(loggedIn);
        spamPay spamPay = getModule.currentModuleClass("spamPay", org.example.web.commonUtils.payment.spamPay.class);
        spamPay.spamPay();
        Thread.sleep(3000);
        new pageBackPostOrder();
        paymentNavigation.paymentNavigation(loggedIn);
        sharedURLPayment sharedURLPayment = getModule.currentModuleClass("sharedURLPayment", org.example.web.commonUtils.payment.sharedURLPayment.class);
        sharedURLPayment.sharedURLPayment();
        if (Objects.equals(readProperty("tokenized"),"no")) {
            gatewayNavigation gatewayNavigation = getModule.currentModuleClass("gatewayNavigation", org.example.web.commonUtils.payment.gatewayNavigation.class);
            gatewayNavigation.gatewayNavigation(loggedIn);
            paymentsHelper sharedURLGateway = getModule.currentModuleClass("sharedURLGateway", paymentsHelper.class);
            sharedURLGateway.sharedURLGateway(loggedIn);
        }
    }
}

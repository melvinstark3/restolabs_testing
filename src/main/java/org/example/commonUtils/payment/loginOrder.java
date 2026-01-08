package org.example.commonUtils.payment;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class loginOrder extends browserSetup{

    public loginOrder() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean loggedIn = true;
        driver.navigate().to("https://" + readProperty("shortcode") + "." + System.getProperty("server"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Login\"]"))).click();
        driver.findElement(By.id("email")).sendKeys(readProperty("loginUserEmail"));
        driver.findElement(By.id("password")).sendKeys(readProperty("loginUserPassword"));
        driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).click();
        System.out.println("Creating Cart");
        new createCart(readProperty("loginLocation"),readProperty("loginOrderItem"),loggedIn);
        wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        js.executeScript("window.scrollBy(0,2000)", "");
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).clear();
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys("Test Order Comment");
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
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        String checkoutOrderTotal = driver.findElement(By.xpath("//h5[@data-testid=\"orderTotal\"]")).getText();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        wait = new WebDriverWait(driver, 15);
        // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
        Thread.sleep(5000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"closeBillAddressDialog\"])[2]"))).click();
        }catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Pre-Saved Billing Dialog wasn't Displayed. Proceeding to Gateway");
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
        System.out.print("For Logged In Order: ");
        paymentPageCancellation paymentPageCancellation = getModule.currentModuleClass("paymentPageCancellation", org.example.commonUtils.payment.paymentPageCancellation.class);
        paymentPageCancellation.paymentPageCancellation();
        paymentNavigation paymentNavigation = getModule.currentModuleClass("paymentNavigation", org.example.commonUtils.payment.paymentNavigation.class);
        paymentNavigation.paymentNavigation(loggedIn);
        new gatewayNameInURL();
        System.out.println("Checking Hypertext Protocol for Payment Page");
        checkHttps checkHttps = getModule.currentModuleClass("checkHttps", org.example.commonUtils.payment.checkHttps.class);
        checkHttps.checkHttps();
        if (Objects.equals(readProperty("tokenized"),"no")){
            gatewayNavigation gatewayNavigation = getModule.currentModuleClass("gatewayNavigation", org.example.commonUtils.payment.gatewayNavigation.class);
            gatewayNavigation.gatewayNavigation(loggedIn);
            System.out.println("Checking Gateway Name in Gateway Page URL");
            new gatewayNameInURL();
            System.out.println("Checking Hypertext Protocol for Gateway Page");
            checkHttps.checkHttps();
            // To Save Time after checking the Protocol, instead of new Order we are going back to valid URL & then Gateway
            driver.navigate().back();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-button"))).click();
            paymentsHelper gatewayPageCancellation = getModule.currentModuleClass("gatewayPageCancellation", paymentsHelper.class);
            gatewayPageCancellation.gatewayPageCancellation();
        }
        paymentNavigation.paymentNavigation(loggedIn);
        checkSavedOrNew checkSavedOrNew = getModule.currentModuleClass("checkSavedOrNew",org.example.commonUtils.payment.checkSavedOrNew.class);
        checkSavedOrNew.checkSavedOrNew(readProperty("loginNewCardNumber"),loggedIn);
        new restartOrderWithData(loggedIn);
        wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
        paymentNavigation.paymentNavigation(loggedIn);
        checkSavedOrNew.checkSavedOrNew(readProperty("loginNewCardNumber"),loggedIn);
        wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
        String orderIDWithHash = driver.findElement(By.xpath("//span[@class='pl-1']")).getText();
        String OrderID = orderIDWithHash.replace("#", "");
        System.out.println("TC_06: PASS - Order placed by Logged In User.");
        System.out.println("TC_20: PASS - Payment Gateway is working for a Single Location");

//        new checkTransactionID(OrderID);
    }

}

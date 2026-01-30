package org.example.web.modules.basic;

import org.example.web.commonUtils.payment.checkSavedOrNew;
import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class guestOrder extends browserSetup {
    public guestOrder(String orderMode) throws InterruptedException {
        boolean loggedIn = false;
        driver.navigate().to("https://" + readProperty("shortcode") + "." + System.getProperty("server"));
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\""+ orderMode +"\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\""+ orderMode +"\"]")).click();
        new createCart(orderMode, readProperty("GuestLocation"),loggedIn,"later",readProperty("preOrderTime"),readProperty("onlineOrderItem"));
        new checkout(orderMode, loggedIn);
        try {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@data-testid=\"BillFirst name\"])[2]")));
            driver.findElement(By.xpath("(//input[@data-testid=\"BillFirst name\"])[2]")).sendKeys(readProperty("guestBillingFirstName"));
            driver.findElement(By.xpath("(//input[@data-testid=\"BillLast name\"])[2]")).sendKeys(readProperty("guestBillingLastName"));
            driver.findElement(By.xpath("(//input[@data-testid=\"BillPhone number\"])[2]")).sendKeys(readProperty("guestBillingPhoneNumber"));
            driver.findElement(By.xpath("//button[@data-testid=\"SubmitBillingDetails\"]")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(//input[@data-testid=\"BillPhone number\"])[2]")));
            // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
            Thread.sleep(5000);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Billing Details Page was not displayed");
        }
        checkSavedOrNew checkSavedOrNew = getModule.currentModuleClass("checkSavedOrNew",org.example.web.commonUtils.payment.checkSavedOrNew.class);
        checkSavedOrNew.checkSavedOrNew(readProperty("guestNewCardNumber"),loggedIn);
        try {
            wait = new WebDriverWait(driver, 60);
            String orderIDwithHash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']"))).getText();
            String OrderID = orderIDwithHash.replace("#", "");
            System.out.println("Case 1: PASS: Place a guest order. Order ID : " + OrderID);
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Case 1: FAIL: Guest Order Failed. Please Check the Case Manually");
        }
    }
}

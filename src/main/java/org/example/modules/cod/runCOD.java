package org.example.modules.cod;

import org.example.commonUtils.payment.createCart;
import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.example.core.browserSetup.readProperty;

public class runCOD extends browserSetup{
    public runCOD() throws InterruptedException {
        boolean loggedIn = false;
        driver.navigate().to(readProperty("storeURL"));
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        System.out.println("For Guest Order: ");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        Thread.sleep(5000);
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("PASS: Cash On Delivery Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("FAIL: Cash On Delivery Order Failed");
        }

    }
}
